/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {

	WPI_TalonSRX leftFrontMotor;
	WPI_TalonSRX leftBackMotor;
	WPI_TalonSRX rightFrontMotor;
	WPI_TalonSRX rightBackMotor;

	WPI_TalonSRX lift;
	WPI_TalonSRX flywheel;

	SpeedControllerGroup left;
	SpeedControllerGroup right;

	private DifferentialDrive m_robotDrive;

	private Joystick m_stick;
	private Timer m_timer;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		leftFrontMotor = new WPI_TalonSRX(0);
		leftBackMotor = new WPI_TalonSRX(1);
		rightFrontMotor = new WPI_TalonSRX(2);
		rightBackMotor = new WPI_TalonSRX(3);

		left = new SpeedControllerGroup(leftFrontMotor, leftBackMotor);
		right = new SpeedControllerGroup(rightFrontMotor, rightBackMotor);

		lift = new WPI_TalonSRX(4);
		flywheel = new WPI_TalonSRX(5);

		m_robotDrive = new DifferentialDrive(left, right);
		m_stick = new Joystick(0);
		m_timer = new Timer();
	}

	/**
	 * This function is run once each time the robot enters autonomous mode.
	 */
	@Override
	public void autonomousInit() {
		m_timer.reset();
		m_timer.start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		// Drive for 2 seconds
		if (m_timer.get() < 2.0) {
			m_robotDrive.arcadeDrive(0.5, 0.0); // drive forwards half speed
		} else {
			teleopPeriodic(); // give robot control to drivers
		}
	}

	/**
	 * This function is called once each time the robot enters teleoperated mode.
	 */
	@Override
	public void teleopInit() {
	}

	/**
	 * This function is called periodically during teleoperated mode.
	 */
	@Override
	public void teleopPeriodic() {
		m_robotDrive.arcadeDrive(m_stick.getY(), m_stick.getX());

		if (m_stick.getPOV() == 0) {
			lift.set(1);
		} else if (m_stick.getPOV() == 180) {
			lift.set(-1);
		} else {
			lift.set(0);
		}

		//TODO: FIX
		flywheel.set(m_stick.getRawAxis(2));
		flywheel.set(m_stick.getRawAxis(3));
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
