package leJOS;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

public class LightTest {

	public LightTest() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		LightSensor light = new LightSensor(SensorPort.S1);
		Button.waitForAnyPress();
		
		while (!Button.ESCAPE.isDown()) {
			LCD.clear();
			System.out.println(light.getLightValue());
		}
	}

}
