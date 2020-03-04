/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.IOException;
import java.nio.file.Path;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ChangeIntakeSpeed;
import frc.robot.commands.ChangeMotorMultiplier;
import frc.robot.commands.RampUpMotor;
import frc.robot.commands.Reverse;
import frc.robot.commands.SwitchDriveMode;
import frc.robot.commands.ToggleTwisty;
import frc.robot.subsystems.ShooterMotor;

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
  private AHRS ahrs;

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

    innerIntake1.setInverted(true);
    conveyorMotor.setInverted(true);

    //ahrs = new AHRS()

    // Instantiate joysticks/controllers
    primaryJoystick = new Joystick(Constants.primaryJoystick);
    Constants.joystickPrimary = primaryJoystick;
    secondaryJoystick = new Joystick(Constants.secondaryJoystick);
    Constants.joystickSecondary = secondaryJoystick;
    xBoxController = new XboxController(Constants.xBoxControllerPort);
    Constants.xBoxController = xBoxController;

    // Instantiate buttons
    JoystickButton switchDriveMode = new JoystickButton(primaryJoystick, Constants.tankToArcade);
    JoystickButton reverseButton = new JoystickButton(primaryJoystick, Constants.reverseButton);
    JoystickButton reverseButton2 = new JoystickButton(secondaryJoystick, Constants.reverseButton);
    JoystickButton toggleTwisty = new JoystickButton(primaryJoystick, Constants.toggleTwisty);
    JoystickButton increaseSpeed = new JoystickButton(primaryJoystick, Constants.increaseSpeed);
    JoystickButton decreaseSpeed = new JoystickButton(primaryJoystick, Constants.decreaseSpeed);

    JoystickButton increaseConveyor = new JoystickButton(primaryJoystick, Constants.increaseConveyor);
    JoystickButton decreaseConveyor = new JoystickButton(primaryJoystick, Constants.decreaseConveyor);
    JoystickButton increaseIntake = new JoystickButton(primaryJoystick, Constants.increaseIntake);
    JoystickButton decreaseIntake = new JoystickButton(primaryJoystick, Constants.decreaseIntake);
    JoystickButton increaseInner = new JoystickButton(primaryJoystick, Constants.increaseInner);
    JoystickButton decreaseInner = new JoystickButton(primaryJoystick, Constants.decreaseInner);

    switchDriveMode.whenPressed(new SwitchDriveMode());
    reverseButton.whenPressed(new Reverse());
    reverseButton2.whenPressed(new Reverse());
    toggleTwisty.whenPressed(new ToggleTwisty());
    increaseSpeed.whenPressed(new ChangeMotorMultiplier(Constants.motorMultiplier, true));
    decreaseSpeed.whenPressed(new ChangeMotorMultiplier(Constants.motorMultiplier, false));

    increaseConveyor.whenPressed(new ChangeIntakeSpeed(0, true));
    decreaseConveyor.whenPressed(new ChangeIntakeSpeed(0, false));
    increaseIntake.whenPressed(new ChangeIntakeSpeed(1, true));
    decreaseIntake.whenPressed(new ChangeIntakeSpeed(1, false));
    increaseInner.whenPressed(new ChangeIntakeSpeed(2, true));
    decreaseInner.whenPressed(new ChangeIntakeSpeed(2, false));

    encoder = new Encoder(Constants.encoderChannelA, Constants.encoderChannelB);
    Constants.encoder = encoder;
    encoder.setDistancePerPulse(4./256.);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();

    double leftPOV = xBoxController.getPOV(0);
    if (leftPOV == 0) {
      RampUpMotor rampUp = new RampUpMotor(true);
      rampUp.initialize();
    }
    if (leftPOV == 180) {
      RampUpMotor rampDown = new RampUpMotor(false);
      rampDown.initialize();
    }

    SmartDashboard.putNumber("Left POV", leftPOV);
    SmartDashboard.putNumber("Motor multiplier", Constants.increaseIntakeBy);
    SmartDashboard.putNumber("Maximum motor power", Constants.intakePower);
    SmartDashboard.putNumber("Current speed", intakeMotor.getSpeed());

    System.out.println(encoder.getDistance());
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
    // Import autonomous paths
    String path = "paths/TestPath.path";
    Path importedPath = Filesystem.getDeployDirectory().toPath().resolve(path);
    try {
      Trajectory trajectory = TrajectoryUtil.fromPathweaverJson(importedPath);
      DifferentialDrive drive = new DifferentialDrive(new SpeedControllerGroup((SpeedController)frontLeft, (SpeedController)frontRight), new SpeedControllerGroup((SpeedController)frontRight, (SpeedController)backRight));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
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
  @Override
  public void teleopPeriodic() {
    //Get instances of motors
    VictorSPX frontLeft = Constants.frontLeft;
    VictorSPX frontRight = Constants.frontRight;
    VictorSPX backLeft = Constants.backLeft;
    VictorSPX backRight = Constants.backRight;
    Talon elevator = Constants.elevator;

    //Get joystick positions
    double primaryX = Constants.joystickPrimary.getRawAxis(0);
    double primaryY = Constants.joystickPrimary.getRawAxis(1);
    double primaryZ = Constants.joystickPrimary.getTwist();
    double secondaryY = Constants.joystickSecondary.getRawAxis(1);
    
    //Get XBox Controller status
    double xBoxPosition = Constants.xBoxController.getTriggerAxis(Hand.kRight);
    double leftPosition = Constants.xBoxController.getTriggerAxis(Hand.kLeft);

    if (xBoxController.getRawButton(5)) {
      Constants.intakeMotor.set(Constants.intakeSpeed);
      Constants.innerIntake1.set(Constants.innerSpeed);
      Constants.innerIntake2.set(Constants.innerSpeed);
      Constants.conveyorMotor.set(Constants.conveyorSpeed);
    }
    else if (xBoxController.getRawButton(6)) {
      Constants.intakeMotor.set(0);
      Constants.innerIntake1.set(0);
      Constants.innerIntake2.set(0);
      Constants.conveyorMotor.set(0);
    }

    if (Constants.driveMode == 0) {
      //Arcade drive
      double joyX = primaryJoystick.getRawAxis(0) * -1;
      double joyY = primaryJoystick.getRawAxis(1);
      double twist = primaryJoystick.getRawAxis(2);
      double slider = (primaryJoystick.getRawAxis(3) * 2) - 1;

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