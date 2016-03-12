package sensors.touch;

import ch.aplu.robotsim.LegoRobot;
import ch.aplu.robotsim.Motor;
import ch.aplu.robotsim.MotorPort;
import ch.aplu.robotsim.RobotContext;
import ch.aplu.robotsim.SensorPort;
import ch.aplu.robotsim.Tools;
import ch.aplu.robotsim.TouchListener;
import ch.aplu.robotsim.TouchSensor;

/**
 * A simple LegoRobot simulation that counts stripes
 * 
 * @author Tim Metzler
 *
 */

public class LawnMower {

	/**
	 * Static constructor to load the background image
	 */
	static {
		// Position our obstacles
		RobotContext.useObstacle(System.getProperty("user.dir") +
				 "/src/sprites/bar3.gif", 490, 250);
		RobotContext.useObstacle(System.getProperty("user.dir") +
				 "/src/sprites/bar3.gif", 10, 250);
		RobotContext.setStartDirection(0);
		RobotContext.setStartPosition(300, 80);
	}
	
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	
	private Motor motA, motB;
	private int nextTurn = RIGHT;

	public LawnMower() {
		LegoRobot robot = new LegoRobot();

		// Create and add two motors
		motA = new Motor(MotorPort.A);
		motB = new Motor(MotorPort.B);

		robot.addPart(motA);
		robot.addPart(motB);


		// Create and add a light sensor
		// Then connect it to sensor port 1
		TouchSensor touchSensor = new TouchSensor(SensorPort.S1);

		touchSensor.addTouchListener(new TouchListener() {

			@Override
			public void pressed(SensorPort arg0) {
				System.out.println("Touch sensor pressed.");
				// Stop, then run backwards a little bit
				motA.stop();
				motB.stop();
				motA.backward();
				motB.backward();
				Tools.delay(800);

				// Turn 
				turn(nextTurn);
				// Run forward a bit
				motA.forward();
				motB.forward();
				Tools.delay(400);
				// Turn
				turn(nextTurn);
				if (nextTurn == RIGHT)
					nextTurn = LEFT;
				else
					nextTurn = RIGHT;
				// Move forward
				motA.forward();
				motB.forward();
			}

			@Override
			public void released(SensorPort arg0) {
				// Nothing to do here
			}

		});
		robot.addPart(touchSensor);
		

		// Move forward
		motA.forward();
		motB.forward();

		// Let the robot run for 10s
		Tools.delay(100000);

		robot.exit();
	}
	
	public void turn(int direction) {
		motA.stop();
		motB.stop();
		if (direction == LEFT) {
			motA.setSpeed(100);
			motA.forward();
			Tools.delay(525);
			motA.stop();
			motB.setSpeed(100);
		} else {
			motB.setSpeed(100);
			motB.forward();
			Tools.delay(525);
			motB.stop();
			motA.setSpeed(100);
		}
	}

	public static void main(String[] args) {
		new LawnMower();
	}

}
