package leJOS;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.util.Delay;

public class LightCalib2 {

	private int dark, bright, trigger;
	private LightSensor lightSensorRight, lightSensorLeft;

	public LightCalib2() {
		lightSensorRight = new LightSensor(SensorPort.S1);
		lightSensorLeft = new LightSensor(SensorPort.S2);

		calibrate(lightSensorRight);
		
		Button.waitForAnyPress();
		calibrate(lightSensorLeft);
		
		Button.waitForAnyPress();
		
	
	}
	
	public void calibrate(LightSensor lightSensor) {
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
		new LightCalib2();
	}

}
