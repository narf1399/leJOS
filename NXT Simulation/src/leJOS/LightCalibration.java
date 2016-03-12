package leJOS;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.util.Delay;

public class LightCalibration {

	private int dark, bright, trigger;
	private LightSensor lightSensor;

	public LightCalibration() {
		lightSensor = new LightSensor(SensorPort.S1);

		calibrate();
		
		Button.waitForAnyPress();
		
		while (!Button.ESCAPE.isDown()) {
			int lightValue = lightSensor.getLightValue();
			LCD.clear();
			System.out.println("Light value = "+lightValue);
			
			if (lightValue < trigger) {
				System.out.println("On track!");
			} else {
				System.out.println("Off track!");
			}
			
			Delay.msDelay(500);
		}
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
		new LightCalibration();
	}

}
