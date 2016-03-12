package leJOS;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.util.Delay;

public class MotorTest {

	public static void main(String[] args) {
		System.out.println("Press any button to start.");
		Button.waitForAnyPress();
		System.out.println("Turning 360 deg forward.");
		Motor.A.rotate(180, true);
		Motor.B.rotate(-180);
		Button.waitForAnyPress();
		System.out.println("Turning 360 deg backwards.");
		Motor.A.rotate(-360, true);
		Motor.B.rotate(360);
		
		
		
		Button.waitForAnyPress();
		for (int i=0;i<12;i++) {
			Motor.A.rotate(30, true);
			Motor.B.rotate(-30);
		}
		
		System.out.println("Finished");
		Button.waitForAnyPress();
		
		
		
	}

}
