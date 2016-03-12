package locomotion;

import ch.aplu.robotsim.Tools;
import ch.aplu.robotsim.TurtleRobot;

public class LocomotionWaveRectangular {

	public static final int TIME_DELAY_MS = 500;
	public static final int ITERATIONS = 5;

	public LocomotionWaveRectangular() {
		TurtleRobot robot = new TurtleRobot();

		for (int i = 0; i < ITERATIONS; i++) {
			// Move forward for 1s
			robot.forward();
			Tools.delay(TIME_DELAY_MS);
			// Turn right 90 degrees
			robot.right(90);

			// Move forward for 1s
			robot.forward();
			Tools.delay(TIME_DELAY_MS);
			// Turn right 90 degrees
			robot.right(90);

			// Move forward for 1s
			robot.forward();
			Tools.delay(TIME_DELAY_MS);
			// Turn left 90 degrees
			robot.left(90);
			// Move forward for 1s
			robot.forward();
			Tools.delay(TIME_DELAY_MS);
			// Turn left 90 degrees
			robot.left(90);
		}
		robot.exit();
	}

	public static void main(String[] args) {
		new LocomotionWaveRectangular();
	}

}
