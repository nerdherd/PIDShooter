
package org.usfirst.frc.team687.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	
	double current;
	double rpm;
	double voltage;
	long startTime;
	long time;
	
	Encoder enc;
	CANTalon shooter;
	Joystick joy;
	PowerDistributionPanel pdp;
	FileWriter fw;
	File csv;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	startTime = System.currentTimeMillis();
		shooter = new CANTalon(1);
		
		csv = new File("data.csv");
		try {
			fw = new FileWriter(csv);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
    	current = pdp.getCurrent(1);
    	time = System.currentTimeMillis()-startTime;
    	rpm = enc.getRate();
    	voltage = pdp.getVoltage();
    	
    	if (joy.getRawButton(7)) {
    		kP += 0.1;
    	}
    	if (joy.getRawButton(8)) {
    		kP -= 0.1;
    	}
    	if (joy.getRawButton(9)) {
    		kI += 0.1;
    	}
    	if (joy.getRawButton(10)) {
    		kI -= 0.1;
    	}
    	if (joy.getRawButton(11)) {
    		kD += 0.1;
    	}
    	if (joy.getRawButton(12)) {
    		kD -= 0.1;
    	}
    	SmartDashboard.putNumber("kP", kP);
    	SmartDashboard.putNumber("kI", kI);
    	SmartDashboard.putNumber("kD", kD);
    	
    	try {
			fw.write(Double.toString(current));
			fw.write(","+Double.toString(voltage));
			fw.write(","+Double.toString(rpm));
			fw.write(","+Long.toString(time)+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
