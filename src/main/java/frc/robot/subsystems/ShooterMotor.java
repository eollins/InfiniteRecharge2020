/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterMotor extends SubsystemBase {
  /**
   * Creates a new IntakeMotor.
   */
  public ShooterMotor() {

  }

  public static void SetMotor(boolean direction, double magnitude) {
    if (direction == false) {
      magnitude *= -1;
    }
    Constants.intakeMotor.set(magnitude);
  }

  public static void RampMotor(boolean direction) {
    TalonSRX intakeMotor = Constants.shooterMotor;
    double maximumIntakePower = Constants.maximumIntakePower;
    double increaseBy = Constants.increaseIntakeBy;

    double speed = intakeMotor.getMotorOutputPercent();

    if (direction) {
      if (speed > maximumIntakePower) {
        intakeMotor.set(ControlMode.PercentOutput, maximumIntakePower);
      }
      else if (maximumIntakePower - speed < increaseBy) {
        intakeMotor.set(ControlMode.PercentOutput, maximumIntakePower);
      }
      else {
        intakeMotor.set(ControlMode.PercentOutput, speed + increaseBy);
      }
    }
    else {
      if (speed < increaseBy) {
        intakeMotor.set(ControlMode.PercentOutput, 0);
      }
      else {
        intakeMotor.set(ControlMode.PercentOutput, speed - increaseBy);
      }
    }
  }

  public static void StopMotor() {
    Constants.intakeMotor.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
