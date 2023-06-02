/**
 * ---------------------------
 * Assignment 1  Part I 
 * Written By:Robert CHEN 40241709 and Alexandru Ilie 40248696
 * COMP 249 Section S - Winter 2023
 * 3 February 2023
 * --------------------------- */




//Import Scanner 
import java.util.Scanner;


/**
 * This main file class shall be the start-up point of the players: 
 *	they may create or restart a game of snake and ladder
 * 
 * @author Robert  CHEN and ALexandru Ilie
 * @version 1.0
 *
 *
 */
public class PlayLadderAndSnake {

	public static void main(String[] args) {
		//Declare scanner
		Scanner keyb = new Scanner(System.in);
		
		//Declare the object required to start a game:
		LadderAndSnake game = new LadderAndSnake();
		
		System.out.println("Hello, welcome to the Snake And Ladder Game!");
		
		//Invoke play() method to simulate the game
		game.play();
		
		
		
		
		//Close scanner
		keyb.close();	
	}

}
