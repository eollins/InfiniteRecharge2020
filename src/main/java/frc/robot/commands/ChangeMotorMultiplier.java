/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;

public class ChangeMotorMultiplier extends CommandBase {
  /**
   * Creates a new ChangeMotorMultiplier.
   */
  boolean finished = false;
  boolean increase = false;
  public ChangeMotorMultiplier(double currentSpeed, boolean increase) {
    this.increase = increase;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (increase && Constants.motorMultiplier < 1) {
      Constants.motorMultiplier += 0.1;
    }
    else if (!increase && Constants.motorMultiplier > 0) {
      Constants.motorMultiplier -= 0.1;
    }
    else {
      Joystick test = Constants.joystickPrimary;
      test.setRumble(RumbleType.kLeftRumble, 5);
    }
    finished = true;
    SmartDashboard.putNumber("Multiplier", Constants.motorMultiplier);
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
