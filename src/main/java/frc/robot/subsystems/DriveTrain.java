/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

//Includes functions for both arcade drive and tank drive control.
public class DriveTrain extends Subsystem {
  @Override
  public void initDefaultCommand() {
    
  }

  public static void arcadeDrive(double x, double y) {
    RobotMap.robotDrive.arcadeDrive(x, y);
  }

  public static void tankDrive(double x, double y) {
    RobotMap.robotDrive.tankDrive(x, y);
  }
}
