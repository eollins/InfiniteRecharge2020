/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
//test
/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Subsystem driveTrain;
  private RobotContainer m_robotContainer;

  private VictorSPX frontLeft;
  private VictorSPX backLeft;
  private VictorSPX frontRight;
  private VictorSPX backRight;
  private VictorSP elevator;
  private VictorSP intakeMotor;

  private Joystick primaryJoystick;
  private Joystick secondaryJoystick;
  private XboxController xBoxController;

  private Encoder encoder;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    frontLeft = new VictorSPX(Constants.frontLeftMotor);
    Constants.frontLeft = frontLeft;
    backLeft = new VictorSPX(Constants.backLeftMotor);
    Constants.backLeft = backLeft;
    frontRight = new VictorSPX(Constants.frontRightMotor);
    Constants.frontRight = frontRight;
    backRight = new VictorSPX(Constants.backRightMotor);
    Constants.backRight = backRight;
    elevator = new VictorSP(Constants.elevatorMotor);
    Constants.elevator = elevator;
    intakeMotor = new VictorSP(Constants.intakeMotorPort);
    Constants.intakeMotor = intakeMotor;

    primaryJoystick = new Joystick(Constants.primaryJoystick);
    Constants.joystickPrimary = primaryJoystick;
    secondaryJoystick = new Joystick(Constants.secondaryJoystick);
    Constants.joystickSecondary = secondaryJoystick;
    xBoxController = new XboxController(Constants.xBoxControllerPort);
    Constants.xBoxController = xBoxController;

    JoystickButton intakeToggleForward = new JoystickButton(xBoxController, Constants.intakeForward);
    JoystickButton intakeToggleBackward = new JoystickButton(xBoxController, Constants.intakeBackward);
    intakeToggleForward.whenPressed(new ToggleIntake(true));
    intakeToggleBackward.whenPressed(new ToggleIntake(false));

    encoder = new Encoder(Constants.encoderChannelA, Constants.encoderChannelB);
    Constants.encoder = encoder;
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
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
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    //m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    // if (m_autonomousCommand != null) {
    //   m_autonomousCommand.schedule();
    // }
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
    VictorSPX frontLeft = Constants.frontLeft;
    VictorSPX frontRight = Constants.frontRight;
    VictorSPX backLeft = Constants.backLeft;
    VictorSPX backRight = Constants.backRight;
    VictorSP elevator = Constants.elevator;

    double primaryY = Constants.joystickPrimary.getRawAxis(1) * -1;
    double secondaryY = Constants.joystickSecondary.getRawAxis(1);
    
    double xBoxPosition = Constants.xBoxController.getTriggerAxis(Hand.kRight);
    double leftPosition = Constants.xBoxController.getTriggerAxis(Hand.kLeft);

    frontLeft.set(ControlMode.PercentOutput, primaryY);
    frontRight.set(ControlMode.PercentOutput, secondaryY);
    backLeft.set(ControlMode.PercentOutput, primaryY);
    backRight.set(ControlMode.PercentOutput, secondaryY);
    
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
