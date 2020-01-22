package frc.robot;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.RobotDrive;

public class RobotMap {
  //Speed controller port mappings
  public static final int frontLeftMotor = 1;
  public static final int frontRightMotor = 2;
  public static final int backLeftMotor = 3;
  public static final int backRightMotor = 4;
  
  //Joystick port mappings
  public static final int primaryJoystick = 5;
  public static final int secondaryJoystick = 6;

  //Motor instances and drive train
  public static RobotDrive robotDrive;
  public static SpeedController frontLeft;
  public static SpeedController frontRight;
  public static SpeedController backLeft;
  public static SpeedController backRight;

  //Joystick button mappings
  public static final int reverseButton = 1;
  public static final int arcadeToTank = 2;
  public static final int tankToArcade = 2;

  public static int driveMode = 0;
  public static int reverse = 0;

  public static void init() {
    frontLeft = new Talon(frontLeftMotor);
    frontRight = new Talon(frontRightMotor);
    backLeft = new Talon(backLeftMotor);
    backRight = new Talon(backRightMotor);

    robotDrive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
    frontLeft.setInverted(false);
    frontRight.setInverted(false);
    backLeft.setInverted(false);
    backRight.setInverted(false);
  }
}