/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;

public class Reverse extends CommandBase {
  /**
   * Creates a new Reverse.
   */
  boolean finished = false;
  public Reverse() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (Constants.reverse == 0) {
      Constants.reverse = 1;
      Constants.frontLeft.setInverted(true);
      Constants.frontRight.setInverted(true);
      Constants.backLeft.setInverted(true);
      Constants.backRight.setInverted(true);
    }
    else {
      Constants.reverse = 0;
      Constants.frontLeft.setInverted(false);
      Constants.frontRight.setInverted(false);
      Constants.backLeft.setInverted(false);
      Constants.backRight.setInverted(false);
    }
    finished = true;
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
