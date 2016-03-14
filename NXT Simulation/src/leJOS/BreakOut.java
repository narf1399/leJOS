import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.util.Delay;

public class BreakOut {
	
	private LightSensor lightSensor = new LightSensor(SensorPort.S1);	
	private int triggerLevel;
	
	public BreakOut() {
		
		calibrateSensors();
		
		Motor.A.setSpeed(200);
		Motor.B.setSpeed(200);
		
		Motor.A.forward();
		Motor.B.forward();
		
		while (true) {
			// Read the light value
			int lightVal = lightSensor.getLightValue();
			
			if (lightVal < triggerLevel) {
				// We found a wall
				Motor.A.stop();
				Motor.B.stop();
				// Move back a bit
				Motor.A.backward();
				Motor.B.backward();
				
				Delay.msDelay(1000);
				Motor.A.stop();
				Motor.B.stop();
				
				// Turn in a random direction
				double rand = Math.random();
				if (rand < 0.5) {
					// Turn left
					Motor.A.forward();
					Motor.B.backward();
					Delay.msDelay(500);
				} else {
					// Turn right
					Motor.A.backward();
					Motor.B.forward();
					Delay.msDelay(500);
				}
				// Start moving again
				Motor.A.forward();
				Motor.B.forward();
			}
			
			
		}
		
	}
	
	/**
	 * Starts the calibration process for both sensors
	 */
	public void calibrateSensors() {
		int dark, bright;
		System.out.println("Calibration process.");
		System.out.println("Please move the sensor to the dark part.");
		Button.waitForAnyPress();

		// Get the dark part, the track
		dark = lightSensor.getLightValue();
		LCD.clear();
		System.out.println("Please move both sensors to the bright part.");
		Button.waitForAnyPress();
		LCD.clear();

		// Get the bright part
		bright = lightSensor.getLightValue();
		
		// Get the trigger level
		triggerLevel = (dark + bright) / 2;
		

		System.out.println("Trigger level...");
		System.out.println(triggerLevel);
		System.out.println("Calibration process finished.");
		System.out.println("Continue...");
		Button.waitForAnyPress();
		LCD.clear();
	}
	
	public static void main(String[] args) {
		new BreakOut();
	}

}
