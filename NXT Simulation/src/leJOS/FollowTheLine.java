package leJOS;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.util.Delay;

public class FollowTheLine {

	public static final int TURN_TIME = 350;
	public static final int LEFT = 0;
	public static final int RIGHT = 1;

	private int dark, bright, trigger;
	private int lastDirection = LEFT;

	private LightSensor lightSensor;

	public FollowTheLine() {
		// Initialize the light sensor
		lightSensor = new LightSensor(SensorPort.S1);

		Motor.A.setSpeed(200);
		Motor.B.setSpeed(200);

		// Start the calibration process
		calibrate();

		// Start moving
		Motor.A.forward();
		Motor.B.forward();

		// Control loop
		while (!Button.ESCAPE.isDown()) {
			int lightVal = lightSensor.getLightValue();
			// Check if we went off track
			if (lightVal > trigger) {
				Motor.A.stop();
				Motor.B.stop();

				findTrack();
				//
				// LCD.clear();
				// System.out.println("We went off track.");
				// System.out.println("Put robot back on track.");
				// Button.ENTER.waitForPressAndRelease();
				// Delay.msDelay(1000);
				//
				// Motor.A.forward();
				// Motor.B.forward();

			}

			// Wait 100 ms
			Delay.msDelay(100);
		}
	}

	public boolean turn(int direction, int time) {
		boolean trackFound = false;

		if (direction == RIGHT) {
			Motor.A.backward();
			Motor.B.forward();
		} else {
			Motor.B.backward();
			Motor.A.forward();
		}

		long tStart = System.currentTimeMillis();
		while (!trackFound && (System.currentTimeMillis() - tStart) < time) {
			Delay.msDelay(100);
			int lightVal = lightSensor.getLightValue();
			if (lightVal < trigger) {
				trackFound = true;
			}

		}

		Motor.A.stop();
		Motor.B.stop();
		
		if (trackFound) {
			if (direction == RIGHT) {
				Motor.A.rotate(30, true);
				Motor.B.rotate(-30);
			} else {
				Motor.A.rotate(-30, true);
				Motor.B.rotate(30);
			}
		}

		return trackFound;
	}

	public void findTrack() {
		
		
		boolean trackFound = turn(lastDirection, TURN_TIME);
		
		if (trackFound) {
			Motor.A.forward();
			Motor.B.forward();
			return;
		} else {
			lastDirection = (lastDirection + 1)%2;
			trackFound = turn(lastDirection, 2*TURN_TIME);
		}
		
		if (trackFound) {
			Motor.A.forward();
			Motor.B.forward();
			return;
		}

		

		System.out.println("Not found");

	}

	public void calibrate() {
		LCD.clear();
		System.out.println("Calibration process.");
		System.out.println("Please move the sensor to the dark part.");
		Button.waitForAnyPress();
		dark = lightSensor.getLightValue();
		LCD.clear();
		System.out.println("Please move the sensor to the bright part.");
		Button.waitForAnyPress();
		bright = lightSensor.getLightValue();

		trigger = (dark + bright) / 2;

		System.out.println("Dark = " + dark);
		System.out.println("Bright = " + bright);
		System.out.println("Trigger lvl = " + trigger);
		System.out.println("Continue...");
		Button.waitForAnyPress();
		LCD.clear();
	}

	public static void main(String[] args) {
		new FollowTheLine();
	}

}
