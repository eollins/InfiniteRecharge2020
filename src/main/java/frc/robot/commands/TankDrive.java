/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class TankDrive extends Command {
  public TankDrive() {
    requires(Robot.driveTrain_subsystem);
  }

  @Override
  protected void initialize() {
    RobotMap.driveMode = 1;
  }

  @Override
  protected void execute() {
    Robot.driveTrain_subsystem.tankDrive(Robot.m_oi.getPrimaryYPosition(), Robot.m_oi.getSecondaryYPosition());
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
    end();
  }
}
