/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.cscore.CameraServerCvJNI;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ChangeIntakeSpeed;
import frc.robot.commands.ChangeMotorMultiplier;
import frc.robot.commands.FireSolenoid;
import frc.robot.commands.InvertClimber;
import frc.robot.commands.RampUpMotor;
import frc.robot.commands.RetractSolenoid;
import frc.robot.commands.Reverse;
import frc.robot.commands.RotateByAngle;
import frc.robot.commands.StopCompressor;
import frc.robot.commands.SwitchDriveMode;
import frc.robot.commands.ToggleTwisty;
import frc.robot.subsystems.ShooterMotor;
import com.kauailabs.navx.frc.*;

public class Robot extends TimedRobot {
  private Subsystem driveTrain;
  private RobotContainer m_robotContainer;
  public Subsystem IntakeMotor;

  private VictorSPX frontLeft;
  private VictorSPX backLeft;
  private VictorSPX frontRight;
  private VictorSPX backRight;
  private Talon elevator;
  private VictorSP intakeMotor;
  private TalonSRX shooterMotor;

  private Joystick primaryJoystick;
  private Joystick secondaryJoystick;
  private XboxController xBoxController;

  private VictorSP conveyorMotor;
  private Talon innerIntake1;
  private Talon innerIntake2;

  private Encoder encoder;
  private Compressor compressor;
  private DoubleSolenoid solenoid;

  private UsbCamera camera;
  private MjpegServer server;
  public static AHRS ahrs;
  private DifferentialDrive drive;
  private PIDController turnController;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer. This will perform all our button bindings,
    // and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    IntakeMotor = new ShooterMotor();

    // Instantiate all motors
    frontLeft = new VictorSPX(Constants.frontLeftMotor);
    Constants.frontLeft = frontLeft;
    backLeft = new VictorSPX(Constants.backLeftMotor);
    Constants.backLeft = backLeft;
    frontRight = new VictorSPX(Constants.frontRightMotor);
    Constants.frontRight = frontRight;
    backRight = new VictorSPX(Constants.backRightMotor);
    Constants.backRight = backRight;
    elevator = new Talon(Constants.elevatorMotor);
    Constants.elevator = elevator;
    intakeMotor = new VictorSP(Constants.intakeMotorPort);
    Constants.intakeMotor = intakeMotor;
    shooterMotor = new TalonSRX(Constants.shooterMotorPort);
    Constants.shooterMotor = shooterMotor;
    innerIntake1 = new Talon(Constants.innerIntake1Port);
    Constants.innerIntake1 = innerIntake1;
    innerIntake2 = new Talon(Constants.innerIntake2Port);
    Constants.innerIntake2 = innerIntake2;
    conveyorMotor = new VictorSP(Constants.conveyorMotorPort);
    Constants.conveyorMotor = conveyorMotor;


    CameraServer.getInstance().startAutomaticCapture();
    //drive = new DifferentialDrive(new SpeedControllerGroup((SpeedController)frontLeft, (SpeedController)backLeft), new SpeedControllerGroup((SpeedController)backLeft, (SpeedController)backRight));

    innerIntake1.setInverted(true);
    conveyorMotor.setInverted(true);

    ahrs = new AHRS(SerialPort.Port.kMXP);

    // Instantiate joysticks/controllers
    primaryJoystick = new Joystick(Constants.primaryJoystick);
    Constants.joystickPrimary = primaryJoystick;
    secondaryJoystick = new Joystick(Constants.secondaryJoystick);
    Constants.joystickSecondary = secondaryJoystick;
    xBoxController = new XboxController(Constants.xBoxControllerPort);
    Constants.xBoxController = xBoxController;

    // Instantiate buttons
    final JoystickButton switchDriveMode = new JoystickButton(primaryJoystick, Constants.tankToArcade);
    final JoystickButton reverseButton = new JoystickButton(primaryJoystick, Constants.reverseButton);
    final JoystickButton reverseButton2 = new JoystickButton(secondaryJoystick, Constants.reverseButton);
    final JoystickButton toggleTwisty = new JoystickButton(primaryJoystick, Constants.toggleTwisty);
    final JoystickButton increaseSpeed = new JoystickButton(primaryJoystick, Constants.increaseSpeed);
    final JoystickButton decreaseSpeed = new JoystickButton(primaryJoystick, Constants.decreaseSpeed);
    final JoystickButton rampUpShooter = new JoystickButton(xBoxController, Constants.rampUpShooter);
    final JoystickButton rampDownShooter = new JoystickButton(xBoxController, Constants.rampDownShooter);

    final JoystickButton angleOneLeft = new JoystickButton(primaryJoystick, Constants.angleOneLeft);
    final JoystickButton angleOneRight = new JoystickButton(primaryJoystick, Constants.angleOneRight);
    final JoystickButton angleTwoLeft = new JoystickButton(primaryJoystick, Constants.angleTwoLeft);
    final JoystickButton angleTwoRight = new JoystickButton(primaryJoystick, Constants.angleTwoRight);
    final JoystickButton angleThreeLeft = new JoystickButton(primaryJoystick, Constants.angleThreeLeft);
    final JoystickButton angleThreeRight = new JoystickButton(primaryJoystick, Constants.angleThreeRight);
    final JoystickButton invertClimber = new JoystickButton(primaryJoystick, Constants.invertClimber);
    final JoystickButton stopCompressor = new JoystickButton(xBoxController, Constants.stopCompressor);
    final JoystickButton fireSolenoid = new JoystickButton(xBoxController, Constants.fireSolenoid);
    final JoystickButton retractSolenoid = new JoystickButton(xBoxController, 3);

    switchDriveMode.whenPressed(new SwitchDriveMode());
    reverseButton.whenPressed(new Reverse());
    reverseButton2.whenPressed(new Reverse());
    toggleTwisty.whenPressed(new ToggleTwisty());
    increaseSpeed.whenPressed(new ChangeMotorMultiplier(Constants.motorMultiplier, true));
    decreaseSpeed.whenPressed(new ChangeMotorMultiplier(Constants.motorMultiplier, false));

    angleOneLeft.whenPressed(new RotateByAngle(Constants.angleOne, false));
    angleOneRight.whenPressed(new RotateByAngle(Constants.angleOne, true));
    angleTwoLeft.whenPressed(new RotateByAngle(Constants.angleTwo, false));
    angleTwoRight.whenPressed(new RotateByAngle(Constants.angleTwo, true));
    angleThreeLeft.whenPressed(new RotateByAngle(Constants.angleThree, false));
    angleThreeRight.whenPressed(new RotateByAngle(Constants.angleThree, true));
    rampUpShooter.whenPressed(new RampUpMotor(true));
    rampDownShooter.whenPressed(new RampUpMotor(false));
    invertClimber.whenPressed(new InvertClimber());
    stopCompressor.whenPressed(new StopCompressor());
    fireSolenoid.whenPressed(new FireSolenoid());
    retractSolenoid.whenPressed(new RetractSolenoid());

    encoder = new Encoder(Constants.encoderChannelA, Constants.encoderChannelB);
    Constants.encoder = encoder;
    encoder.setDistancePerPulse(4./256.);

    compressor = new Compressor(Constants.compressorPort);
    Constants.compressor = compressor;
    solenoid = new DoubleSolenoid(6, 6, 7);
    Constants.solenoid = solenoid;
    compressor.start();
  }

  public double angle = 0;
  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();

    if (ahrs.getAngle() - angle >= 5) {
      System.out.println(ahrs.getAngle());
    }

    angle = ahrs.getAngle();

    final double leftPOV = xBoxController.getPOV(0);
    if (leftPOV == 0) {
      conveyorMotor.set(Constants.conveyorSpeed);
    }
    if (leftPOV == 90) {
      intakeMotor.set(Constants.intakeSpeed);
      innerIntake1.set(Constants.innerSpeed);
      innerIntake2.set(Constants.innerSpeed);
    }
    if (leftPOV == 270) {
      intakeMotor.set(0);
      innerIntake1.set(0);
      innerIntake2.set(0);
    }
    if (leftPOV == 180) {
      conveyorMotor.set(0);
      compressor.start();
      compressor.setClosedLoopControl(true);
      Constants.solenoid.set(DoubleSolenoid.Value.kForward);
    }

    SmartDashboard.putNumber("Left POV", leftPOV);
    SmartDashboard.putNumber("Motor multiplier", Constants.increaseIntakeBy);
    SmartDashboard.putNumber("Maximum motor power", Constants.intakePower);
    SmartDashboard.putNumber("Current speed", intakeMotor.getSpeed());

    //System.out.println(encoder.getDistance());
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
    
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    //Move off of initiation line
    SmartDashboard.putNumber("First leg", 38);
    SmartDashboard.putNumber("Second leg", 45);

    frontLeft.setInverted(false);
    frontRight.setInverted(false);
    backLeft.setInverted(false);
    backRight.setInverted(false);

    double startingDist = encoder.getDistance();
    while (Math.abs(encoder.getDistance()) < startingDist + SmartDashboard.getNumber("First leg", 38)) {
      setMotor(0.3, -0.3, 0.3, -0.3);
      System.out.println("First leg: " + encoder.getDistance());
    }
    setMotor(0, 0, 0, 0);

    startingDist = encoder.getDistance();
    while (Math.abs(encoder.getDistance()) < startingDist + SmartDashboard.getNumber("Second leg", 62.5)) {
      setMotor(0.3, 0.3, 0.3, 0.3);
      System.out.println("Second leg: " + encoder.getDistance());
    }
    setMotor(0, 0, 0, 0);

    shooterMotor.set(ControlMode.PercentOutput, Constants.maximumIntakePower);
    conveyorMotor.set(Constants.conveyorSpeed);
    Timer.delay(1.5);
    innerIntake1.set(Constants.innerSpeed);
    innerIntake2.set(Constants.innerSpeed);
    intakeMotor.set(Constants.intakeSpeed);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  public void setMotor(final double frontLeft, final double frontRight, final double backLeft, final double backRight) {
    this.frontLeft.set(ControlMode.PercentOutput, frontLeft);
    this.frontRight.set(ControlMode.PercentOutput, frontRight);
    this.backLeft.set(ControlMode.PercentOutput, backLeft);
    this.backRight.set(ControlMode.PercentOutput, backRight);
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    // if (m_autonomousCommand != null) {
    //   m_autonomousCommand.cancel();
    // }
  }

  /**
   * This function is called periodically during operator control.
   */
  boolean intakePressed = false;
  @Override
  public void teleopPeriodic() {
    //Get instances of motors
    final VictorSPX frontLeft = Constants.frontLeft;
    final VictorSPX frontRight = Constants.frontRight;
    final VictorSPX backLeft = Constants.backLeft;
    final VictorSPX backRight = Constants.backRight;
    final Talon elevator = Constants.elevator;

    //Get joystick positions
    final double primaryX = Constants.joystickPrimary.getRawAxis(0);
    double primaryY = Constants.joystickPrimary.getRawAxis(1);
    final double primaryZ = Constants.joystickPrimary.getTwist();
    double secondaryY = Constants.joystickSecondary.getRawAxis(1);
    
    //Get XBox Controller status
    final double xBoxPosition = Constants.xBoxController.getTriggerAxis(Hand.kRight);
    final double leftPosition = Constants.xBoxController.getTriggerAxis(Hand.kLeft);

    //Hold down right button to run all intake systems
    if (xBoxController.getRawButton(Constants.intakeForward)) {
      if (intakePressed == false) {
        Constants.intakeMotor.set(Constants.intakeSpeed);
        Constants.innerIntake1.set(Constants.innerSpeed);
        Constants.innerIntake2.set(Constants.innerSpeed);
        Constants.conveyorMotor.set(Constants.conveyorSpeed);
        intakePressed = true;
      }
    }
    else {
      if (intakePressed == true) {
        Constants.intakeMotor.set(0);
        Constants.innerIntake1.set(0);
        Constants.innerIntake2.set(0);
        Constants.conveyorMotor.set(0);
        intakePressed = false;
      }
    }

    //Crawl speed with joystick POV
    final double primaryPOV = primaryJoystick.getPOV();
    if (primaryPOV == 0) {
      frontLeft.setInverted(false);
      frontRight.setInverted(false);
      backLeft.setInverted(false);
      backRight.setInverted(false);

      frontLeft.set(ControlMode.PercentOutput, Constants.crawlSpeed);
      frontRight.set(ControlMode.PercentOutput, Constants.crawlSpeed * -1);
      backLeft.set(ControlMode.PercentOutput, Constants.crawlSpeed);
      frontRight.set(ControlMode.PercentOutput, Constants.crawlSpeed * -1);
    }
    else if (primaryPOV == 90) {
      frontLeft.set(ControlMode.PercentOutput, Constants.crawlSpeed);
      backLeft.set(ControlMode.PercentOutput, Constants.crawlSpeed);
    }
    else if (primaryPOV == 180) {
      frontLeft.setInverted(true);
      frontRight.setInverted(true);
      backLeft.setInverted(true);
      backRight.setInverted(true);

      frontLeft.set(ControlMode.PercentOutput, Constants.crawlSpeed);
      frontRight.set(ControlMode.PercentOutput, Constants.crawlSpeed * -1);
      backLeft.set(ControlMode.PercentOutput, Constants.crawlSpeed);
      backRight.set(ControlMode.PercentOutput, Constants.crawlSpeed * -1);
    }
    else if (primaryPOV == 270) {
      frontRight.set(ControlMode.PercentOutput, Constants.crawlSpeed);
      backRight.set(ControlMode.PercentOutput, Constants.crawlSpeed);
    }
    else {
      frontLeft.set(ControlMode.PercentOutput, 0);
      frontRight.set(ControlMode.PercentOutput, 0);
      backLeft.set(ControlMode.PercentOutput, 0);
      backRight.set(ControlMode.PercentOutput, 0);
    }

    //Toggle all intake systems from the side button
    if (xBoxController.getRawButton(Constants.intakeBackward)) {
      if (intakeMotor.get() == Constants.intakeSpeed) {
        intakeMotor.set(0);
        innerIntake1.set(0);
        innerIntake2.set(0);
        conveyorMotor.set(0);
      }
      else {
        intakeMotor.set(Constants.intakeSpeed);
        innerIntake1.set(Constants.innerSpeed);
        innerIntake2.set(Constants.innerSpeed);
        conveyorMotor.set(Constants.conveyorSpeed);
      }
    }

    if (Constants.driveMode == 0) {
      //Arcade drive
      double joyX = primaryJoystick.getRawAxis(0) * -1;
      double joyY = primaryJoystick.getRawAxis(1);
      final double twist = primaryJoystick.getRawAxis(2);
      final double slider = (primaryJoystick.getRawAxis(3) * 2) - 1;

      if (joyX > (Constants.deadZone * -1) && joyX < Constants.deadZone) {
        joyX = 0;
      }
      if (joyY > (Constants.deadZone * -1) && joyY < Constants.deadZone) {
        joyY = 0;
      }

      if (primaryJoystick.getRawButton(5)) {
        joyX = slider;
      }

      double leftMotors;
      double rightMotors;

      if (!Constants.twisty) {
        leftMotors = joyY + joyX;
        rightMotors = joyY - joyX;
      }
      else {
        leftMotors = joyY + twist;
        rightMotors = joyY - twist;
      }

      rightMotors *= -1;

      leftMotors *= Constants.motorMultiplier;
      rightMotors *= Constants.motorMultiplier;

      frontLeft.set(ControlMode.PercentOutput, leftMotors);
      frontRight.set(ControlMode.PercentOutput, rightMotors);

      backLeft.set(ControlMode.PercentOutput, leftMotors);
      frontRight.set(ControlMode.PercentOutput, rightMotors);
    } 
    else {
      //Tank drive
      //primaryY = Math.pow(2, (primaryY - 1)) - 0.05;
      //secondaryY = Math.pow(2, (secondaryY - 1)) - 0.05;

      if (primaryY < Constants.deadZone && primaryY > (Constants.deadZone * -1)) {
        primaryY = 0;
      }
      if (secondaryY < Constants.deadZone && secondaryY > (Constants.deadZone * -1)) {
        secondaryY = 0;
      }

      primaryY *= Constants.motorMultiplier;
      secondaryY *= Constants.motorMultiplier;

      primaryY *= -1;
      frontLeft.set(ControlMode.PercentOutput, primaryY);
      frontRight.set(ControlMode.PercentOutput, secondaryY);
      backLeft.set(ControlMode.PercentOutput, primaryY);
      backRight.set(ControlMode.PercentOutput, secondaryY);
    }
    
    //Change elevator direction
    if (xBoxPosition > leftPosition) {
      elevator.set(xBoxPosition);
    }
    else {
      elevator.set(leftPosition * -1);
    }

    SmartDashboard.putString("Direction", String.valueOf(encoder.getDirection()));
    SmartDashboard.putString("Distance", String.valueOf(encoder.getDistance()));
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  
  }
}