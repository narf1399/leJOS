package linefollower;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;

public class FollowLinePID {

	public static final int TARGET_SPEED = 200;

	private int dark, bright, triggerRight, triggerLeft;

	private LightSensor lightSensorRight, lightSensorLeft;

	public FollowLinePID() {
		// Initialize the light sensor
		lightSensorRight = new LightSensor(SensorPort.S1);
		lightSensorLeft = new LightSensor(SensorPort.S2);

		Motor.A.setAcceleration(3000);
		Motor.B.setAcceleration(3000);
		Motor.A.setSpeed(TARGET_SPEED);
		Motor.B.setSpeed(TARGET_SPEED);

		// Start the calibration process
		calibrateRight();
		calibrateLeft();

		// Start moving
		Motor.A.forward();
		Motor.B.forward();
		// PID
		double kp = 300;
		double ki = 0;
		double kd = 200;
		int error = 0;
		int errorOld = 0;
		int errorSum = 0;

		int steps = 0;
		long tStart = System.currentTimeMillis();
		// Control loop
		while (!Button.ESCAPE.isDown()) {
			int lightValRight = lightSensorRight.getLightValue();
			int lightValLeft = lightSensorLeft.getLightValue();
			
			error = 0;
			if (lightValLeft < triggerLeft) {
				error = -1;
			}
			if (lightValRight < triggerRight) {
				error = 1;
			}
				
			errorSum += error;
			double correctionTerm = error*kp + errorSum*ki + (error-errorOld)*kd;
			
			System.out.println(error);
			System.out.println(lightValLeft + " " + lightValRight + " " + correctionTerm);
			
			float speedA = (float) (TARGET_SPEED - correctionTerm);
			float speedB = (float) (TARGET_SPEED + correctionTerm);
			
			Motor.A.setSpeed(Math.abs(speedA));
			if (speedA < 0) {
				Motor.A.backward();
			} else {
				Motor.A.forward();
			}
			
			Motor.B.setSpeed(Math.abs(speedB));
			if (speedB < 0) {
				Motor.B.backward();
			} else {
				Motor.B.forward();
			}
			
//			
//			Motor.A.setSpeed( (float) (TARGET_SPEED - correctionTerm));
//			Motor.B.setSpeed( (float) (TARGET_SPEED + correctionTerm));
			errorOld = error;

//			Motor.A.forward();
//			Motor.B.forward();
			// Wait 100 ms
			//Delay.msDelay(10);
			steps++;
		}
		
	}



	public void calibrateRight() {
		LCD.clear();
		System.out.println("Calibration process - Right Sensor.");
		System.out.println("Please move the sensor to the dark part.");
		Button.waitForAnyPress();
		dark = lightSensorRight.getLightValue();
		LCD.clear();
		System.out.println("Please move the sensor to the bright part.");
		Button.waitForAnyPress();
		bright = lightSensorRight.getLightValue();

		triggerRight = (dark + bright) / 2;

		System.out.println("Dark = " + dark);
		System.out.println("Bright = " + bright);
		System.out.println("Trigger lvl = " + triggerRight);
		System.out.println("Continue...");
		Button.waitForAnyPress();
		LCD.clear();
	}
	
	public void calibrateLeft() {
		LCD.clear();
		System.out.println("Calibration process - Left Sensor.");
		System.out.println("Please move the sensor to the dark part.");
		Button.waitForAnyPress();
		dark = lightSensorLeft.getLightValue();
		LCD.clear();
		System.out.println("Please move the sensor to the bright part.");
		Button.waitForAnyPress();
		bright = lightSensorLeft.getLightValue();

		triggerLeft = (dark + bright) / 2;

		System.out.println("Dark = " + dark);
		System.out.println("Bright = " + bright);
		System.out.println("Trigger lvl = " + triggerLeft);
		System.out.println("Continue...");
		Button.waitForAnyPress();
		LCD.clear();
	}

	public static void main(String[] args) {
		new FollowLinePID();
	}

}
