
package org.usfirst.frc.team687.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	// Need to tune these values
	double kP = 0;
	double kI = 0;
	double kD = 0;
	
	CANTalon shooter;
	Joystick joy;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		shooter = new CANTalon(1);
		
		//Pick the right sensor
		shooter.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		shooter.reverseSensor(false);
		
		//Pick the voltage for no power/full power
        shooter.configNominalOutputVoltage(0, 0);
        shooter.configPeakOutputVoltage(12, 0);	//Don't ever spin the motor backwards
		
		shooter.enableBrakeMode(false); // Let's not destroy the motor
		
		shooter.setPID(kP, kI, kD);
		shooter.changeControlMode(TalonControlMode.Speed);
		
		joy = new Joystick(0);
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
    	if(joy.getRawButton(1))	{
			shooter.set(6200); //Target speed is 6200 RPM
		}	else	{
			shooter.set(0);
		}
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
