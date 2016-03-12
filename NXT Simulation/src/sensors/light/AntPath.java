package sensors.light;

import ch.aplu.robotsim.LegoRobot;
import ch.aplu.robotsim.LightSensor;
import ch.aplu.robotsim.Motor;
import ch.aplu.robotsim.MotorPort;
import ch.aplu.robotsim.RobotContext;
import ch.aplu.robotsim.SensorPort;
import ch.aplu.robotsim.Tools;

public class AntPath {

	public static final int TRESHOLD = 20;
	public static final int BASE_SPEED = 100;

	/**
	 * Static constructor to load the background image
	 */
	static {
		RobotContext.useBackground(System.getProperty("user.dir") + "/src/sprites/antPath.gif");
		RobotContext.setStartPosition(50, 75);
		RobotContext.setStartDirection(0);
	}

	private Motor motA, motB;
	private LightSensor lightSensorRight, lightSensorLeft;

	public AntPath() {
		LegoRobot robot = new LegoRobot();

		// Create and add two motors
		motA = new Motor(MotorPort.A);
		motB = new Motor(MotorPort.B);

		robot.addPart(motA);
		robot.addPart(motB);

		motA.setSpeed(50);
		motB.setSpeed(50);

		// Create and add a light sensor on the right
		// Then connect it to sensor port 1
		lightSensorRight = new LightSensor(SensorPort.S1);
		robot.addPart(lightSensorRight);

		// Create and add a light sensor on the left
		// Then connect it to sensor port 1
		lightSensorLeft = new LightSensor(SensorPort.S2);
		robot.addPart(lightSensorLeft);

		// Move forward
		motA.forward();
		motB.forward();

		for (int i = 0; i < 1000; i++) {
			adjustSpeeds(lightSensorLeft.getValue(), lightSensorRight.getValue());
			Tools.delay(100);
		}

		// Tools.delay(3000);
		// Stop
		robot.exit();
	}

	public void adjustSpeeds(int lightValueRight, int lightValueLeft) {
		int difference = lightValueRight - lightValueLeft;

		int averageLightValue = (lightValueLeft + lightValueRight) / 2;
		if (averageLightValue < TRESHOLD) {
			System.out.println("Path lost");
			findPath();
			return;
		}
		// If the difference between the light sensors is greater than the
		// Threshold, adjust speeds
		if (Math.abs(difference) > TRESHOLD) {
			
			if (lightValueRight < lightValueLeft) {
				System.out.println("Move left");
				motA.setSpeed(100);
				motB.setSpeed(200);
				//Tools.delay(10000);
			} else {
				System.out.println("Move right");
				motA.setSpeed(200);
				motB.setSpeed(100);
				//Tools.delay(10000);
			}
			// Calculate the average light value
//			
//			System.out.println("Avg light val " + averageLightValue);
//			
//			double speedLeft = BASE_SPEED * ((double) averageLightValue) / lightValueRight;
//			double speedRight = BASE_SPEED * ((double) averageLightValue) / lightValueLeft;
//			// Adjust the speeds so they don't get too big
//			if (speedLeft > 2 * BASE_SPEED)
//				speedLeft = 2 * BASE_SPEED;
//			if (speedRight > 2 * BASE_SPEED)
//				speedRight = 2 * BASE_SPEED;
//			motA.setSpeed((int) speedLeft);
//			motB.setSpeed((int) speedRight);
		} else {
			motA.setSpeed(BASE_SPEED);
			motB.setSpeed(BASE_SPEED);
		}
	}
	
	public void findPath() {
		// Continue moving forward for a little bit
		Tools.delay(50);
		
		// Turn 90°
		motB.setSpeed(BASE_SPEED);
		motA.stop();
		Tools.delay(525);
		motB.stop();
		
		motA.setSpeed(BASE_SPEED);
		motB.setSpeed(BASE_SPEED/3);
		motA.forward();
		motB.forward();
		
		while (lightSensorRight.getValue() < TRESHOLD || lightSensorLeft.getValue() < TRESHOLD) {
			Tools.delay(50);
		}
//		motA.stop();
//		motB.stop();
		System.out.println("Path found.");
//		Tools.delay(100000);
	}

	public static void main(String[] args) {
		new AntPath();

	}

}
