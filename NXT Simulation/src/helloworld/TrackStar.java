package helloworld;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;

public class TrackStar {
	
  public static void main (String[] args) {
    System.out.println("Ready");
    Sound.pause(1000);
    System.out.println("Steady");
    Sound.pause(1000);
    System.out.println("Go");
    
    LightSensor lightSensorLeft = new LightSensor(SensorPort.S1);
    LightSensor lightSensorRight = new LightSensor(SensorPort.S2);
    
    
    Motor.A.setSpeed(100);
	Motor.B.setSpeed(100);
    
    Motor.A.forward();
    Motor.B.forward();
    int laps = 0;
    while (laps < 3 && !Button.ESCAPE.isDown()) {
    	int leftLightValue = lightSensorLeft.getLightValue();
    	int rightLightValue = lightSensorRight.getLightValue();
    	
    	if (Math.abs(leftLightValue - rightLightValue) > 20) {
    		if (leftLightValue < rightLightValue) {
    			Motor.B.setSpeed(200);
    			Motor.A.setSpeed(100);
    		} else {
    			Motor.A.setSpeed(200);
    			Motor.B.setSpeed(100);
    		}
    	} else {
    		Motor.A.setSpeed(100);
    		Motor.B.setSpeed(100);
    		// Check if both sensors see light (Lap completed)
        	if (leftLightValue > 30 && rightLightValue > 30) {
        		Sound.beep();
        		laps++;
        	}
    	}
    	
    	
    	
    }
    //Sound.twoBeeps();
    Motor.A.stop();
    Motor.B.stop();
    Button.waitForAnyPress();
    
  }
  
}