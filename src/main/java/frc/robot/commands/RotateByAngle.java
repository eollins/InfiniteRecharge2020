/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.lang.invoke.ConstantBootstraps;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class RotateByAngle extends CommandBase {
  /**
   * Creates a new RotateByAngle.
   */
  boolean finished = false;
  double degrees = 0;
  boolean direction = false;
  public RotateByAngle(double degrees, boolean direction) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.degrees = degrees;
    this.direction = direction;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // double currentAngle = Robot.ahrs.getAngle();
    // if (!direction) {
    //   degrees *= -1;
    // }
    // double targetAngle = currentAngle + degrees;
    
    // if (direction) {
    //   while (Robot.ahrs.getAngle() < targetAngle) {
    //     Constants.frontLeft.set(ControlMode.PercentOutput, 0.3);
    //     Constants.frontRight.set(ControlMode.PercentOutput, 0.3);
    //     Constants.backLeft.set(ControlMode.PercentOutput, 0.3);
    //     Constants.backRight.set(ControlMode.PercentOutput, 0.3);
    //   }
    // }
    // else {
    //   while (Robot.ahrs.getAngle() > targetAngle) {
    //     Constants.frontLeft.set(ControlMode.PercentOutput, -0.3);
    //     Constants.frontRight.set(ControlMode.PercentOutput, -0.3);
    //     Constants.backLeft.set(ControlMode.PercentOutput, -0.3);
    //     Constants.backRight.set(ControlMode.PercentOutput, -0.3);
    //   }
    // }

    // Constants.frontLeft.set(ControlMode.PercentOutput, 0);
    // Constants.frontRight.set(ControlMode.PercentOutput, 0);
    // Constants.backLeft.set(ControlMode.PercentOutput, 0);
    // Constants.backRight.set(ControlMode.PercentOutput, 0);

    // finished = true;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
  }
}
