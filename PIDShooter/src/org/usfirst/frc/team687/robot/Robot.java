
package org.usfirst.frc.team687.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Talon;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	private double m_kP = 0;
	private double m_kI = 0;
	private double m_kD = 0;
	
	private double error;
	private double speed;
	private double current;
	private double lastError = 0;
	private double sumError = 0;
	
	private double desired = 6200;
	
	Encoder enc = new Encoder(0,1);
	Talon shooter = new Talon(2);
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {

    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {

    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	current = enc.getRate();
    	error = desired - current;
    	sumError += error;
    	
    	
    	speed = m_kP*error + m_kI*sumError + m_kD*(error-lastError);
    	if (speed > 1) {
    		speed = 1;
    	} else if (speed < -1) {
    		speed = -1;
    	}
    	shooter.set(speed);
    	
    	lastError = error;
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
