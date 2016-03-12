package sensors.light;

import ch.aplu.robotsim.LegoRobot;
import ch.aplu.robotsim.LightListener;
import ch.aplu.robotsim.LightSensor;
import ch.aplu.robotsim.Motor;
import ch.aplu.robotsim.MotorPort;
import ch.aplu.robotsim.RobotContext;
import ch.aplu.robotsim.SensorPort;
import ch.aplu.robotsim.Tools;

public class LightSensorTest {
	
	public static final int TRIGGER_LEVEL = 600;
	
	static {
		RobotContext.useBackground(System.getProperty("user.dir")+"/src/sprites/blackPanels.gif");
		
	}
	
	public LightSensorTest() {
		LegoRobot robot = new LegoRobot();
		
		// Create and add two motors
		Motor motA = new Motor(MotorPort.A);
		Motor motB = new Motor(MotorPort.B);
		
		robot.addPart(motA);
		robot.addPart(motB);
		
		// Move to starting position
		motA.forward();
		motB.backward();
		Tools.delay(700);
		motA.stop();
		motB.stop();
		
		// Move forward
		motA.forward();
		motB.forward();
		
		// Create and add a light sensor
		// Then connect it to sensor port 1
		LightSensor light = new LightSensor(SensorPort.S1);
		//light.setTriggerLevel(TRIGGER_LEVEL);
		
		light.addLightListener(new LightListener() {

			@Override
			public void bright(SensorPort arg0, int arg1) {
				System.out.println("In bright, arg1 = "+arg1);
				// Nothing to do if the sensor see light
				
			}

			@Override
			public void dark(SensorPort arg0, int arg1) {
				System.out.println("In dark, arg1 = "+arg1);
				motA.stop();
				motB.stop();
				
				motA.forward();
				motB.backward();
				Tools.delay(1350);
				motA.stop();
				motB.stop();
				
				motA.forward();
				motB.forward();
				
			}
			
		}, TRIGGER_LEVEL);
		robot.addPart(light);
		
		// Let the robot run for 20s
		Tools.delay(20000);
		
		//System.out.println(light.isActEnabled());
		
		robot.exit();		
	}
	
	public static void main(String[] args) {
		new LightSensorTest();
	}

}
