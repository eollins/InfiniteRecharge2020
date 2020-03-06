/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ShooterMotor;

public class RampUpMotor extends CommandBase {
  /**
   * Creates a new RampUpMotor.
   */
  boolean directionToRamp;
  boolean finished = false;
  public RampUpMotor(boolean direction) {
    addRequirements(new ShooterMotor());
    directionToRamp = direction;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    ShooterMotor.RampMotor(directionToRamp);

    if (directionToRamp) {
      //Constants.solenoid.set(DoubleSolenoid.Value.kReverse);
    }
    else {
      //Constants.solenoid.set(DoubleSolenoid.Value.kForward);
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
