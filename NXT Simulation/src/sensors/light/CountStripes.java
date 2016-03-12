package sensors.light;

import ch.aplu.robotsim.LegoRobot;
import ch.aplu.robotsim.LightListener;
import ch.aplu.robotsim.LightSensor;
import ch.aplu.robotsim.Motor;
import ch.aplu.robotsim.MotorPort;
import ch.aplu.robotsim.RobotContext;
import ch.aplu.robotsim.SensorPort;
import ch.aplu.robotsim.Tools;

/**
 * A simple LegoRobot simulation that counts stripes
 * @author Tim Metzler
 *
 */

public class CountStripes {

	/**
	 * Static constructor to load the background image
	 */
	static {
		RobotContext.useBackground(System.getProperty("user.dir") + "/src/sprites/panel.gif");
		RobotContext.setStartDirection(0);
		RobotContext.setStartPosition(50, 250);
	}

	// How many stripes we found
	private int numberOfStripes = 0;

	public CountStripes() {
		LegoRobot robot = new LegoRobot();

		// Create and add two motors
		Motor motA = new Motor(MotorPort.A);
		Motor motB = new Motor(MotorPort.B);

		robot.addPart(motA);
		robot.addPart(motB);

	

		// Move forward
		motA.forward();
		motB.forward();

		// Create and add a light sensor
		// Then connect it to sensor port 1
		LightSensor light = new LightSensor(SensorPort.S1);

		light.addLightListener(new LightListener() {

			@Override
			public void bright(SensorPort arg0, int arg1) {
				// Nothing to do here

			}

			@Override
			public void dark(SensorPort arg0, int arg1) {
				// We are driving over a stripe
				numberOfStripes++;
				System.out.println("Number of stripes = " + numberOfStripes);
			}

		});
		robot.addPart(light);

		// Let the robot run for 10s
		Tools.delay(10000);

		

		robot.exit();
	}

	public static void main(String[] args) {
		new CountStripes();
	}

}
