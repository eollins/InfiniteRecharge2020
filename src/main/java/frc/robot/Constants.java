/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.XboxController;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
  //Motor CAN address mappings
  public static final int frontLeftMotor = 1; //CAN
  public static final int frontRightMotor = 3; //CAN
  public static final int backLeftMotor = 2; //CAN
  public static final int backRightMotor = 4; //CAN
  public static final int elevatorMotor = 0; //PWM
  public static final int intakeMotorPort = 1; //PWM
  public static final int shooterMotorPort = 5; //CAN
  public static final int innerIntake1Port = 3; //PWM
  public static final int innerIntake2Port = 4; //PWM
  public static final int conveyorMotorPort = 2; //PWM
  public static final int compressorPort = 6; //CAN
  public static final int solenoid1 = 6;
  public static final int solenoid2 = 7;
  
  
  //Joystick port mappings
  public static final int primaryJoystick = 0;
  public static final int secondaryJoystick = 1;
  public static final int xBoxControllerPort = 2;

  //Other port mappings
  public static final int encoderChannelA = 0; //DIO
  public static final int encoderChannelB = 1; //DIO
  public static final int encoderChannelI = 2;
  public static final int encoderChannelABS = 3;

  //Motor instances and drive train
  public static VictorSPX frontLeft;
  public static VictorSPX frontRight;
  public static VictorSPX backLeft;
  public static VictorSPX backRight;
  public static Talon elevator;
  public static VictorSP intakeMotor;
  public static Encoder encoder;
  public static TalonSRX shooterMotor;
  public static VictorSP conveyorMotor;
  public static Talon innerIntake1;
  public static Talon innerIntake2;

  //Joystick objects
  public static Joystick joystickPrimary;
  public static Joystick joystickSecondary;
  public static XboxController xBoxController;

  //Joystick button mappings
  public static final int reverseButton = 3; //joy
  public static final int arcadeToTank = 2; //joy
  public static final int tankToArcade = 2; //joy
  public static final int intakeForward = 6; //xbox
  public static final int intakeBackward = 5; //xbox
  public static final int lockButton = 1; //joy
  public static final int toggleTwisty = 7; //joy
  public static final int increaseSpeed = 6; //joy
  public static final int decreaseSpeed = 4; //joy
  public static final int angleOneLeft = 7; //joy
  public static final int angleOneRight = 8; //joy
  public static final int angleTwoLeft = 9; //joy
  public static final int angleTwoRight = 10; //joy
  public static final int angleThreeLeft = 11; //joy
  public static final int angleThreeRight = 12; //joy
  public static final int rampUpShooter = 1; //xbox
  public static final int rampDownShooter = 2; //xbox
  public static final int invertClimber = 8; //xbox
  public static final int stopCompressor = 7; //xbox
  public static final int fireSolenoid = 4; //xbox
  public static final int retractSolenoid = 3; //xbox

  //Status/mode information
  public static int driveMode = 0;
  public static int reverse = 0;
  public static double intakePower = 1;
  public static boolean intakeStatus = false;
  public static double deadZone = 0.1;
  public static boolean twisty = false;
  public static double motorMultiplier = 1;
  public static double maximumIntakePower = 0.7;
  public static double increaseIntakeBy = 0.1;
  public static double startingDist = 0;

  public static double kP = 0.03;
  public static double kF = 0.00;
  public static double kI = 0.00;
  public static double kD = 0.00;

  public static double conveyorSpeed = 0.8;
  public static double intakeSpeed = 0.6;
  public static double innerSpeed = 0.5;
  public static double crawlSpeed = 0.2;
  public static double angleOne = 10;
  public static double angleTwo = 30;
  public static double angleThree = 90;

  public static Compressor compressor;
  public static DoubleSolenoid solenoid;
}
