package sensors.ultrasound;

import java.awt.Point;

import ch.aplu.robotsim.LegoRobot;
import ch.aplu.robotsim.Motor;
import ch.aplu.robotsim.MotorPort;
import ch.aplu.robotsim.RobotContext;
import ch.aplu.robotsim.SensorPort;
import ch.aplu.robotsim.Tools;
import ch.aplu.robotsim.TouchSensor;
import ch.aplu.robotsim.UltrasonicSensor;

public class ObjectFinder {

	// Specify where the sprites are located
	public static final String filePath = System.getProperty("user.dir") + "/src/sprites/";

	static {
		int xPosTarget = 180;
		int yPosTarget = 180;
		Point[] mesh = { new Point(xPosTarget - 30, yPosTarget - 30), new Point(xPosTarget - 30, yPosTarget + 30),
				new Point(xPosTarget + 30, yPosTarget + 30), new Point(xPosTarget + 30, yPosTarget - 30) };
		// RobotContext.useObstacle(filePath + "squaretarget.gif", xPosTarget,
		// yPosTarget);
		RobotContext.useTarget(filePath + "squaretarget.gif", mesh, xPosTarget, yPosTarget);
		// RobotContext.
		RobotContext.setStartPosition(100, 100);
		RobotContext.setStartDirection(0);
	}

	private Motor motA, motB;
	private TouchSensor touchSensor;
	private UltrasonicSensor ultraSoundSensor;

	public ObjectFinder() {
		LegoRobot robot = new LegoRobot();
		motA = new Motor(MotorPort.A);
		motB = new Motor(MotorPort.B);

		robot.addPart(motA);
		robot.addPart(motB);

		touchSensor = new TouchSensor(SensorPort.S2);
		ultraSoundSensor = new UltrasonicSensor(SensorPort.S1);

		robot.addPart(touchSensor);
		robot.addPart(ultraSoundSensor);

		scan();
	}

	public void scan() {
		Tools.delay(1000);
		System.out.println(ultraSoundSensor.getDistance());
		// motA.stop();
		// motB.stop();
		// motA.forward();
		// motB.backward();
		// Tools.delay(2700);

		int minDist = Integer.MAX_VALUE;

		for (int i = 0; i < 360; i++) {

			motA.stop();
			motB.stop();
			int dist = ultraSoundSensor.getDistance();
			if (dist != -1) {
				// Check if distance is decreasing
				if (dist > minDist) {
					// Stop turning
					System.out.println("Minum distance = " + minDist);
					System.out.println("Current distance = " + dist);
					// Turn backwards one step
					motB.forward();
					motA.backward();
					Tools.delay(40);
					motA.stop();
					motB.stop();
					System.out.println("Final distance = " + ultraSoundSensor.getDistance());
					return;
				} else {
					minDist = dist;
				}
			}
			// System.out.println(i + "\t" + ultraSoundSensor.getDistance());
			// Tools.delay(100);
			motA.forward();
			motB.backward();
			Tools.delay(7);
		}

		motA.stop();
		motB.stop();
	}

	public static void main(String[] args) {
		new ObjectFinder();
	}

}
