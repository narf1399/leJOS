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

public class MazeRunner {

	/**
	 * Static constructor to load the background image
	 */
	static {
		//RobotContext.useBackground(System.getProperty("user.dir") + "/src/sprites/parcours.gif");
		RobotContext.useObstacle(System.getProperty("user.dir") + "/src/sprites/parcours.gif", 250, 250);
		RobotContext.setStartDirection(270);
		RobotContext.setStartPosition(350, 465);
	}
	
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	
	private Motor motA, motB;
	// Timer to know when the last collision was
	private long time = 0;

	public MazeRunner() {
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
				Tools.delay(450);
				
				// Get the time since last collision
				long lastCollision = System.currentTimeMillis() - time;
				
				// If the last collision was less than a second ago, turn 180°
				if (lastCollision < 1000) {
					turn(RIGHT);
					turn(RIGHT);
				} else {
					// If not, restart our timer and turn right once
					turn(RIGHT);
					time = System.currentTimeMillis();
				}
					
				// Run forward again
				motA.forward();
				motB.forward();
			}

			@Override
			public void released(SensorPort arg0) {
				// Nothing to do here
			}

		});
		robot.addPart(touchSensor);
		
		TouchSensor touchSensor2 = new TouchSensor(SensorPort.S4);
		robot.addPart(touchSensor2);

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
	
	public static int randomInt(int min, int max) {
		return (int) (Math.random()*(max-min+1))+min;
	}

	public static void main(String[] args) {
		new MazeRunner();
	}

}
