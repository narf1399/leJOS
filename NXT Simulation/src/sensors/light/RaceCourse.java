package sensors.light;

import ch.aplu.robotsim.LegoRobot;
import ch.aplu.robotsim.LightListener;
import ch.aplu.robotsim.LightSensor;
import ch.aplu.robotsim.Motor;
import ch.aplu.robotsim.MotorPort;
import ch.aplu.robotsim.RobotContext;
import ch.aplu.robotsim.SensorPort;

/**
 * A simple LegoRobot simulation that counts stripes
 * 
 * @author Tim Metzler
 *
 */

public class RaceCourse {

	public static final int TRIGGER_LEVEL = 900;

	/**
	 * Static constructor to load the background image
	 */
	static {
		RobotContext.useBackground(System.getProperty("user.dir") + "/src/sprites/yellowpath.gif");
		RobotContext.setStartPosition(430, 240);
	}

	// How many laps we completed
	private int completedLaps = 0;

	public RaceCourse() {
		LegoRobot robot = new LegoRobot();

		// Create and add two motors
		Motor motA = new Motor(MotorPort.A);
		Motor motB = new Motor(MotorPort.B);

		robot.addPart(motA);
		robot.addPart(motB);

		motA.setSpeed(50);
		motB.setSpeed(50);

		// Create and add a light sensor
		// Then connect it to sensor port 1
		LightSensor light = new LightSensor(SensorPort.S1);
		robot.addPart(light);

		light.addLightListener(new LightListener() {

			@Override
			public void bright(SensorPort arg0, int arg1) {
				System.out.println("in bright = " + arg1);
				light.setTriggerLevel(900);
				if (arg1 <= 50) {
					System.out.println("Black");
					// We are crossing the finish line
					completedLaps++;
					// Check if we did 3 laps
					if (completedLaps > 2)
						robot.exit();
					light.setTriggerLevel(51);
				} else if (arg1 <= 600) {
					// We are in the green area
					motB.setSpeed(130);
				} else if (arg1 <= 800) {
					// We are in the blue area
					motA.setSpeed(130);
				} else {
					motA.setSpeed(50);
					motB.setSpeed(50);
				}
			}

			@Override
			public void dark(SensorPort arg0, int arg1) {
				System.out.println("in dark = " + arg1);
				light.setTriggerLevel(900);
				if (arg1 <= 50) {
					System.out.println("Black");
					// We are crossing the finish line
					completedLaps++;
					// Check if we did 3 laps
					if (completedLaps > 2)
						robot.exit();
					light.setTriggerLevel(51);
				} else if (arg1 <= 600) {
					// We are in the green area
					motB.setSpeed(130);
				} else {
					// We are in the blue area
					motA.setSpeed(130);
				}
			}


			
		}, TRIGGER_LEVEL);

		motA.forward();
		motB.forward();

		// Let the robot run for 10s
		// Tools.delay(30000);

		// robot.exit();
	}

	public static void main(String[] args) {
		new RaceCourse();
	}

}
