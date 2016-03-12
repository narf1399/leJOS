package locomotion;

import ch.aplu.robotsim.Tools;
import ch.aplu.robotsim.TurtleRobot;

public class LocomotionHalfCircle {

	public static final int ITERATIONS = 2;

	public LocomotionHalfCircle() {
		TurtleRobot robot = new TurtleRobot();

		for (int i = 0; i < ITERATIONS; i++) {
			for (int j = 0; j < 180; j++) {
				robot.right(1);
				robot.forward();
				Tools.delay(28);
			}
			robot.left(180);
		}
		

		robot.exit();
	}

	public static void main(String[] args) {
		new LocomotionHalfCircle();
	}

}
