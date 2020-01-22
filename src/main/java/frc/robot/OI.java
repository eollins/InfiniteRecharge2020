package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.Reverse;
import frc.robot.commands.TankDrive;
import frc.robot.subsystems.DriveTrain;

public class OI {
  Joystick primaryJoystick;
  Joystick secondaryJoystick;

  JoystickButton reverseButton; 
  JoystickButton arcadeToTank;
  JoystickButton tankToArcade;

  public OI() {
    primaryJoystick = new Joystick(RobotMap.primaryJoystick);
    secondaryJoystick = new Joystick(RobotMap.secondaryJoystick);

    reverseButton = new JoystickButton(primaryJoystick, RobotMap.reverseButton);
    reverseButton.whenPressed(new Reverse());

    arcadeToTank = new JoystickButton(primaryJoystick, RobotMap.arcadeToTank);
    arcadeToTank.whenPressed(new ArcadeDrive());

    tankToArcade = new JoystickButton(secondaryJoystick, RobotMap.tankToArcade);
    tankToArcade.whenPressed(new TankDrive());
  }

  public Joystick getPrimaryJoystick() {
    return primaryJoystick;
  }

  public Joystick getSecondaryJoystick() {
    return secondaryJoystick;
  }

  public double getPrimaryXPosition() {
    return primaryJoystick.getRawAxis(0);
  }

  public double getPrimaryYPosition() {
    return primaryJoystick.getRawAxis(1);
  }

  public double getSecondaryXPosition() {
    return secondaryJoystick.getRawAxis(0);
  }

  public double getSecondaryYPosition() {
    return secondaryJoystick.getRawAxis(1);
  }
}
