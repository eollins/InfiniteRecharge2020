/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
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
  public static final int frontLeftMotor = 1;
  public static final int frontRightMotor = 3;
  public static final int backLeftMotor = 2;
  public static final int backRightMotor = 4;
  public static final int elevatorMotor = 0;
  public static final int intakeMotorPort = 1;
  
  //Joystick port mappings
  public static final int primaryJoystick = 0;
  public static final int secondaryJoystick = 1;
  public static final int xBoxControllerPort = 2;

  //Other port mappings
  public static final int encoderChannelA = 7;
  public static final int encoderChannelB = 8;

  //Motor instances and drive train
  public static VictorSPX frontLeft;
  public static VictorSPX frontRight;
  public static VictorSPX backLeft;
  public static VictorSPX backRight;
  public static Talon elevator;
  public static VictorSP intakeMotor;
  public static Encoder encoder;

  //Joystick objects
  public static Joystick joystickPrimary;
  public static Joystick joystickSecondary;
  public static XboxController xBoxController;

  //Joystick button mappings
  public static final int reverseButton = 1;
  public static final int arcadeToTank = 2;
  public static final int tankToArcade = 2;
  public static final int intakeForward = 0;
  public static final int intakeBackward = 0;

  //Status/mode information
  public static int driveMode = 0;
  public static int reverse = 0;
  public static double intakePower = 1;
  public static boolean intakeStatus = false;
}
