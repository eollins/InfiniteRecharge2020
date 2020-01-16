package frc.robot;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class RobotMap {
  //Speed controller port mappings
  public static final int frontLeftMotor = 1;
  public static final int frontRightMotor = 2;
  public static final int backLeftMotor = 3;
  public static final int backRightMotor = 4;
  
  //Joystick port mappings
  public static final int primaryJoystick = 5;
  public static final int secondaryJoystick = 6;

  public static SpeedController frontLeft;
  public static SpeedController frontRight;
  public static SpeedController backLeft;
  public static SpeedController backRight;
}