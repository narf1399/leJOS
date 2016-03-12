package leJOS;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.util.Delay;

public class FollowTheLine2 {

	public static final int SPEED_INCREMENT = 50;
	public static final int TARGET_SPEED = 200;
	public static final int LEFT = 0;
	public static final int RIGHT = 1;

	private int dark, bright, trigger;

	private LightSensor lightSensorRight, lightSensorLeft;

	public FollowTheLine2() {
		// Initialize the light sensor
		lightSensorRight = new LightSensor(SensorPort.S1);
		lightSensorLeft = new LightSensor(SensorPort.S2);

		Motor.A.setAcceleration(3000);
		Motor.B.setAcceleration(3000);
		Motor.A.setSpeed(TARGET_SPEED);
		Motor.B.setSpeed(TARGET_SPEED);

		// Start the calibration process
		calibrate();

		// Start moving
		Motor.A.forward();
		Motor.B.forward();

		// Control loop
		while (!Button.ESCAPE.isDown()) {
			int lightValRight = lightSensorRight.getLightValue();
			int lightValLeft = lightSensorLeft.getLightValue();
			// Check if we went off track
			if (lightValRight < trigger) {
				System.out.println("right light dark");
				Motor.A.stop();
				Motor.B.stop();
				Motor.A.backward();
				Motor.B.forward();
				while (lightSensorRight.getLightValue() < trigger) {
					
				}
				Delay.msDelay(500);
				Motor.A.forward();
				//Motor.B.setSpeed(Motor.B.getSpeed() + SPEED_INCREMENT);
			} else if (lightValLeft < trigger) {
				System.out.println("left light dark");
				Motor.A.stop();
				Motor.B.stop();
				Motor.B.backward();
				Motor.A.forward();
				while (lightSensorLeft.getLightValue() < trigger) {
					
				}
				Delay.msDelay(500);
				Motor.B.forward();
				
			} else {
				System.out.println("both bright");
				// Everything ok, try to make speed same
				Motor.A.setSpeed(TARGET_SPEED);
				Motor.B.setSpeed(TARGET_SPEED);
				Motor.A.forward();
				Motor.B.forward();
			}
				
			

			// Wait 100 ms
			Delay.msDelay(100);
		}
	}



	public void calibrate() {
		LCD.clear();
		System.out.println("Calibration process.");
		System.out.println("Please move the sensor to the dark part.");
		Button.waitForAnyPress();
		dark = lightSensorRight.getLightValue();
		LCD.clear();
		System.out.println("Please move the sensor to the bright part.");
		Button.waitForAnyPress();
		bright = lightSensorRight.getLightValue();

		trigger = (dark + bright) / 2;

		System.out.println("Dark = " + dark);
		System.out.println("Bright = " + bright);
		System.out.println("Trigger lvl = " + trigger);
		System.out.println("Continue...");
		Button.waitForAnyPress();
		LCD.clear();
	}

	public static void main(String[] args) {
		new FollowTheLine2();
	}

}
