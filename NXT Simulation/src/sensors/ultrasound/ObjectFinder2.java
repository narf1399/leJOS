package sensors.ultrasound;

import java.awt.Point;

import ch.aplu.robotsim.LegoRobot;
import ch.aplu.robotsim.Motor;
import ch.aplu.robotsim.MotorPort;
import ch.aplu.robotsim.Obstacle;
import ch.aplu.robotsim.RobotContext;
import ch.aplu.robotsim.SensorPort;
import ch.aplu.robotsim.Target;
import ch.aplu.robotsim.Tools;
import ch.aplu.robotsim.TouchListener;
import ch.aplu.robotsim.TouchSensor;
import ch.aplu.robotsim.UltrasonicSensor;

public class ObjectFinder2 {

	// Specify where the sprites are located
	public static final String filePath = System.getProperty("user.dir") + "/src/sprites/";
	public static final Target target1;
	public static final Obstacle obstacle1;

	static {
		// Create our target
		Point[] mesh = {new Point(-30,-30), new Point(-30,30), new Point(30,30), new Point(30, -30)};
		target1 = new Target(filePath + "squaretarget.gif", mesh);
		obstacle1 = new Obstacle(filePath + "squaretarget.gif");
		RobotContext.setStartPosition(100, 100);
		RobotContext.setStartDirection(0);
	}

	private Motor motA, motB;
	private TouchSensor touchSensor;
	private UltrasonicSensor ultraSoundSensor;

	public ObjectFinder2() {
		LegoRobot robot = new LegoRobot();
		motA = new Motor(MotorPort.A);
		motB = new Motor(MotorPort.B);

		robot.addPart(motA);
		robot.addPart(motB);

		touchSensor = new TouchSensor(SensorPort.S2);
		ultraSoundSensor = new UltrasonicSensor(SensorPort.S1);

		robot.addPart(touchSensor);
		robot.addPart(ultraSoundSensor);
		
		robot.addTarget(target1, 180, 180);
		robot.addObstacle(obstacle1, 180, 180);

		touchSensor.addTouchListener(new TouchListener() {

			@Override
			public void pressed(SensorPort arg0) {
				// We bumped into something
				motA.stop();
				motB.stop();
				robot.removeTarget(target1);
				robot.removeObstacle(obstacle1);
			}

			@Override
			public void released(SensorPort arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
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
					// Start moving towards target
					motA.forward();
					motB.forward();
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
		
		//Tools.delay(200);
		
	}

	public static void main(String[] args) {
		new ObjectFinder2();
	}

}
