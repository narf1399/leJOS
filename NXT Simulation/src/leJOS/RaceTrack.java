package leJOS;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.util.Delay;

public class RaceTrack {

	public static final int BRIGHT = 1000;
	public static final int DARK = 0;

	private int laps = 0;

	public RaceTrack() {

		// Initialize the light sensor
		LightSensor light = new LightSensor(SensorPort.S1);
		// Run forward
		Motor.A.forward();
		Motor.B.forward();

		// Loop that reads the light sensor data every .1s
		while (laps < 3) {
			// Read light sensor data
			int lightValue = light.getLightValue();

			boolean pathIsLeft = false;
			boolean pathIsRight = false;

			if (lightValue >= BRIGHT) {
				// Stop movement
				Motor.A.stop();
				Motor.B.stop();

				// Scan left and right
				// First move to the left
				Motor.A.backward();
				Motor.B.forward();

				long tStart = System.currentTimeMillis();

				while (light.getLightValue() > DARK && (System.currentTimeMillis() - tStart) < 1000) {

				}

				Motor.A.stop();
				Motor.B.stop();

				// Check if we found the path
				if (light.getLightValue() <= DARK) {
					pathIsLeft = true;
					Motor.A.forward();
					Motor.B.forward();
					continue;
				}

				Motor.A.forward();
				Motor.B.backward();

				tStart = System.currentTimeMillis();

				while (light.getLightValue() > DARK && (System.currentTimeMillis() - tStart) < 1000) {

				}

				Motor.A.stop();
				Motor.B.stop();

				// Check if we found the path
				if (light.getLightValue() <= DARK) {
					pathIsRight = true;
					Motor.A.forward();
					Motor.B.forward();
					continue;
				}

				// Move forward again
				Motor.A.forward();
				Motor.B.forward();
			}

			Delay.msDelay(100);
		}

	}

	public static void scan() {
		
		Button.waitForAnyPress();
		LightSensor light = new LightSensor(SensorPort.S1);

		Motor.A.stop();
		Motor.B.stop();

		Motor.A.resetTachoCount();
		Motor.B.resetTachoCount();

		LCD.clear();

		Motor.A.forward();
		Motor.B.backward();

		int minLight = Integer.MAX_VALUE;
		int minTacho = 0;

		while (Motor.A.getTachoCount() < 180) {
			LCD.clear();
			int lightVal = light.getLightValue();
			System.out.println(Motor.A.getTachoCount());
			System.out.println(Motor.B.getTachoCount());
			System.out.println("Light = " + lightVal);
			if (lightVal < minLight) {
				minLight = lightVal;
				minTacho = Motor.A.getTachoCount();
			}
		}
		
		Motor.A.stop();
		Motor.B.stop();

		Button.waitForAnyPress();
		LCD.clear();
		System.out.println("MinTacho = " + minTacho);
		System.out.println("MinLight = " + minLight);
		Button.waitForAnyPress();
	}

	public static void main(String[] args) {

		scan();
		// new RaceTrack();
	}

}
