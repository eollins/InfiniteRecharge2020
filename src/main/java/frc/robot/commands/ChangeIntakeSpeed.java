/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;

public class ChangeIntakeSpeed extends CommandBase {
  /**
   * Creates a new ChangeIntakeSpeed.
   */
  boolean finished = false;
  int motorID = -1;
  boolean increase = false;
  public ChangeIntakeSpeed(int motorID, boolean increase) {
    this.motorID = motorID;
    this.increase = increase;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (increase) {
      switch (motorID) {
        case 0:
          Constants.conveyorSpeed += 0.1;
          break;
        case 1:
          Constants.intakeSpeed += 0.1;
          break;
        case 2:
          Constants.innerSpeed += 0.1;
          break;
      }
    }
    else {
      switch (motorID) {
        case 0:
          Constants.conveyorSpeed -= 0.1;
          break;
        case 1:
          Constants.intakeSpeed -= 0.1;
          break;
        case 2:
          Constants.innerSpeed -= 0.1;
          break;
      }
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
