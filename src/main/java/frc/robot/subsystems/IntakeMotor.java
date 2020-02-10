/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeMotor extends SubsystemBase {
  /**
   * Creates a new IntakeMotor.
   */
  public IntakeMotor() {

  }

  public static void SetMotor(boolean direction) {
    int multiplier = 1;
    if (direction == false) {
      multiplier = -1;
    }
    Constants.intakeMotor.set(Constants.intakePower * multiplier);
  }

  public static void StopMotor() {
    Constants.intakeMotor.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
