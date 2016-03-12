package helloworld;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;

public class LightValues {

	LightSensor light = new LightSensor(SensorPort.S1);
	
	public LightValues() {
		light.setFloodlight(false);
		while (!Button.ESCAPE.isDown()) {
			System.out.println(light.getLightValue());
		}
	}
	
	public static void main(String[] args) {
		new LightValues();
	}

}
