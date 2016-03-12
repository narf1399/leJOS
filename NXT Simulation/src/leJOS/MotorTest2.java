package leJOS;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.util.Delay;

public class MotorTest2 {

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println("Next speed = "+(50*i));
			Button.waitForAnyPress();
			Motor.A.setSpeed(50*i);
			Motor.B.setSpeed(50*i);
			Motor.A.forward();
			Motor.B.backward();
			Delay.msDelay(1000);
			Motor.A.stop();
			Motor.B.stop();
		}
		Button.waitForAnyPress();

	}

}
