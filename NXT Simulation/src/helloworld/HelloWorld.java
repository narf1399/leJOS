package helloworld;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.Sound;

public class HelloWorld {
	
  public static void main (String[] args) {
    System.out.println("Ready");
    Sound.pause(1000);
    System.out.println("Steady");
    Sound.pause(1000);
    System.out.println("Go");
    
    Motor.A.forward();
    Motor.B.backward();
    //Sound.twoBeeps();
    Sound.pause(2000);
    Motor.A.stop();
    Motor.B.stop();
    Button.waitForAnyPress();
    
  }
  
}