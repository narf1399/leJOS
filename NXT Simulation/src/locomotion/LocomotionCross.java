package locomotion;
import ch.aplu.robotsim.Tools;
import ch.aplu.robotsim.TurtleRobot;

public class LocomotionCross {

	public LocomotionCross() {
		TurtleRobot robot = new TurtleRobot();

		// Move the robot front and back, then turn 90 degrees
		for (int i = 0; i < 4; i++) {
			// Move front for 1s, then move back for 1s
			robot.forward();
			Tools.delay(1500);
			robot.backward();
			Tools.delay(1500);

			// Turn right 90 degrees
			robot.right(90);
		}

		robot.exit();
	}

	public static void main(String[] args) {
		new LocomotionCross();
	}

}
