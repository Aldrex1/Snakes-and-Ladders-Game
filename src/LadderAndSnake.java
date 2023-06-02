/*
 * ---------------------------
 * Assignment 1  Part I 
 * Written by: Robert CHEN 40241709 and Alexandru Ilie 40248696
 * COMP 249 Section S - Winter 2023
 * 3 February 2023
 * --------------------------- */


//Import Scanner
import java.util.Scanner;
//****Main class****


/**
 *	The class file holds everything to run a snake and ladder game. This simulation can only be played
 *	by 2 people; any numbers of players lower or higher than 2 shall either be ignored or the game will terminately end.
 *	This class file holds a 10x10 board to simulate all 100 squares, including the snakes and ladders.
 *	Each players start at position square "0" at the bottom left of the board and must reach the square labeled "100", located at the top right of the board before the other one.
 *	the players must then use a simulated dice roll that allows each player to move up on the board. A player's number of steps goes according to the number obtained
 *	on their dice when they roll it. One must trespass through all squares but may also be helped by the use of "imaginary" ladders located in some squares. If 
 *   a player lands on the bottom of a ladder, they may climb it up and reach a position square that is closer to 100. (ex: player 1 was on square 15, they move up to square 66)
 *	There are also "imaginary snakes" located in some squares. If a player lands on the head of the snake, they will slide down and reach a  lower position square.
 *	(ex: player 2 was on square 99, they slip on a snake's head  and fall down to square 1). 
 *	the game is locked through several restrictions: If two players land on the same position suqare, the last player to have arrived kicks the other player out
 *	and the exiled player is forced to return to square 0.
 * 
 * @author Robert CHEN 40241709 and ALexandru Ilie 40248696
 * @version 1.0
 *
 *
 */
public class LadderAndSnake {
	private int maxPlayers = 2;
	private String player1;
	private String player2;
	private int p1location = 0;
	private int p2location = 0;
	private String [][] board = new String[10][10];
	private int[][] boardNum = new int[10][10];
	private String details1 = "";
	private String details2 = "";
	private boolean hasWon = false;
	
	/**default constructor
	 * 
	 */
	public LadderAndSnake() {
		
	}
	
	/**copy constructor if two users wish to restart a new game
	 * 
	 * @param S another LadderAndSnake "game:
	 */
	public LadderAndSnake(LadderAndSnake S) {
		 player1 = S.player1;
		 player2= S.player2;
	}
	
	
	//Access values
	/** Method involves a Player's name
	 * 
	 * @return Player 1's name
	 */
	public String getName1() {
		String S1 = player1;
		return S1;
	}
	/**
	 * Method involves a player's name
	 * @return Player 2's name
	 */
	public String getName2() {
		String S2 = player2;
		return S2;
	}
	/**
	 * Method involves acquiring a player's position on the game board
	 * @return Player 1's location
	 */
	public int getP1location() {
		int P1 = p1location;
		return P1;
	}
	/**
	 * Method involves acquiring a player's position on the game board
	 * @return Player 2's location
	 */
	public int getP2location() {
		int P2 = p2location;
		return P2;
	}
	
	/**method simulates a dice roll. It gives us a number between 1 to 6.
	 * 
	 * @return the value of a roll dice
	 */
	public static int flipDice() {
		int x = (int)(Math.random()*6 + 1);
		return x;	
	}
	
	/**
	 * Method to display and modify the board for every user's actions. 
	 * Player 1 is detailed as X1 on the board and player 2 as X2.
	 */
	public void Board() {
		int counter = 100;
		//Create a 2D array for  Numbers only (to locate a user's position without modifying anything)
		for(int i = 0; i<10;i+=2) {
		
				for(int j = 0; j<10;j++) {
					boardNum[i][j] = counter;
					counter--;
					
					if (j== 9) {
						counter++;
						break;
					}
				}
			
			counter = counter-10;									
				for(int j = 0; j<10;j++) {
					boardNum[i+1][j] = counter;
					counter++;

					if (j == 9) {
						counter--;
						break;
						}
					}
			counter = counter-10;
		}
		//A 2D array that is modifiable and that can display a user's position
		for(int i = 0; i<10;i++) {
			for(int j = 0; j<10;j++) {
				board[i][j] = boardNum[i][j] + "";
			}
		}
		
		for(int i = 0; i<10;i++) {
			for(int j = 0; j<10;j++) {
				if(boardNum[i][j] == p1location || boardNum[i][j] == p2location) {
					if(boardNum[i][j] == p1location) {
						board[i][j] = "X1";
					} else if (boardNum[i][j] == p2location) {
						board[i][j] = "X2";
					}
					
				} 
			}
		}

		//Output the board and their modifications, along with simulating a board
		System.out.println("\n|---------------------------------------------------------------------------------------------------------------------------------------------------------------|");
		for(int i = 0; i<10;i++) {
			for(int j = 0; j<10;j++) {
				System.out.print("|\t" + board[i][j] + "\t");
				if(j==9) {
					System.out.print("|");
				}
			}
			System.out.print("\n|                                                                                                                                                               |");
			System.out.println("\n|---------------------------------------------------------------------------------------------------------------------------------------------------------------|");
		}
	}

	
	/** largest method: Allows the 2 players to have names and deciding the order on whoever goes first,
	   Where each players simulate the game and a player moves according to their dice number. 
	   Main method used to access and play the Snake and Ladder game.
	*/
	public void play() {
		//Declare Scanner and temporary variables
		Scanner keyb = new Scanner(System.in);
		int numPlayers;
		int p1Dice;
		int p2Dice;
		int counter = 1;

		//Allowing access to only 2 players maximum. if number lower than 2, game exits
		//Else, the number of players is modified to only 2.
		System.out.print("To begin, initialize the amount of players:");
		numPlayers = keyb.nextInt();
		if (numPlayers > maxPlayers) {
			System.out.println("Initialization was attempted for 2 member of players; however, this is only" 
				+	" expected for an extended version the game. Value will be set to 2");
			numPlayers = 2;
		}
		else if (numPlayers < maxPlayers) {
			System.out.println("Error: Cannot execute the game with less than 2 players! Will exit.");
			System.exit(0);
		}
		//Each Players may enter their respective name
		System.out.print("May Player 1 enter his name: ");
		player1 = keyb.next();
		System.out.print("And Player 2?: ");
		player2 = keyb.next();
		System.out.println("Now deciding which player will start playing:");
		
		//The order of roll dice (who goes first)
		while (true) {
			p1Dice = LadderAndSnake.flipDice();
			System.out.println("Player 1 got a dice value of "+p1Dice );
			p2Dice = LadderAndSnake.flipDice();
			System.out.println("Player 2 got a dice value of "+p2Dice );
			
			//Restart the picking order if both users receive the same dice number.
			if (p1Dice == p2Dice) {
				System.out.println("A tie was achieved between Player 1 and Player 2. "
						+ "Attempting to break the tie");
				counter++;
			}
		
			//player 1 goes first if his number is higher
			else if (p1Dice > p2Dice) {
				System.out.println(" Reached final decision on order of playing: "
						+ "Player 1 then Player 2. It took "+ counter + " attempts before a "
						+ "decision could be made. ");
				break;
			}
			//player 2 goes first if his number is higher
			else if (p1Dice < p2Dice) {
				System.out.println(" Reached final decision on order of playing: "
						+ "Player 2 then Player 1. It took "+ counter + " attempts before a "
						+ "decision could be made. ");
				break;
			}
		}
		
		//Display and create the board to be played on
		Board();
		
		System.out.println("Above is a representation o the board.");
		
		//Pause the game for a brief moment
		keyb.nextLine();
		System.out.println("The game shall start now:");
		//pause the game for a brief moment
		keyb.nextLine();
		//Declare new values
		int p1 = 0;
		int p2 = 0;
		

		//How the snake and ladder game is played
		//if player 1 moves first
		if (p1Dice > p2Dice) {
			while (hasWon == false) {
				p1 = LadderAndSnake.flipDice();
				p1location+=p1;
				details1="Player 1("+ player1 +") got a dice value of "+p1+"; now in square "+p1location;
				Ladder();
				snake();
				
				//Move the player backwards if they go over 100
				if(p1location > 100) {	
					int p1junk = p1location-100;
					p1location = 100 - p1junk;
				}
				//prevent both players to be on the same spot
				if(p1location == p2location) {
					p1location = 0;
					details1 = "Player 1("+ player1 +") got a dice value of "+p1+"; Player 1 on the same square as Player 2: position reset; now in square "+p1location;
				}
				//Additional differentiation ,such as snakes and ladders
				Ladder();
				snake();
				
				System.out.println(details1);
				
				//The same input for Player 2 like player 1
				p2 = LadderAndSnake.flipDice();
				p2location +=p2;
				details2 = "Player 2("+ player2 +") got a dice value of "+p2+"; now in square "+p2location;
				Ladder();
				snake();
				
				if (p2location > 100) {
					int p2junk = p2location-100;
					p2location = 100 - p2junk;
				}
				if(p2location == p1location) {
					p2location = 0;
					details2 = "Player 2("+ player2 +") got a dice value of "+p2+"; Player 2 on the same square as Player 1: position reset; now in square "+p2location;
				}
				
				//Additional differentiation, such as the ladders and snakes
				Ladder();
				snake();
				//output result
				System.out.println(details2);
				
				//if the player reaches exactly the position 100
				Victory();
				
				//Pause the game for a brief moment
				keyb.nextLine();
				//Re-display and re-modify the board according to the players' location
				Board();
				
				//Pause the game for a brief moment
				keyb.nextLine();

				// *****Continuous loop until the game ends****
				if(hasWon == false) {
					System.out.println("Game not over; flipping again\n");
					continue;
				}
				
				//Show the player's position on the board
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						if (p1location == boardNum[i][j]) {
							board[i][j] = "X1";
						}
						if (p2location == boardNum[i][j]) {
							board[i][j] = "X2";
						}
					}
				}
			}
			
		}
		//If player 2 moves first
		else if (p2Dice > p1Dice) {
			while (hasWon == false ) {
				p2 = LadderAndSnake.flipDice();
				p2location +=p2;
				details2 = "Player 2("+ player2 +") got a dice value of "+p2+"; now in square "+p2location;
				Ladder();
				snake();
				
				//Move the user backwards if they go over 100
				if(p2location > 100) {
					int p2junk = p2location-100;
					p2location = 100 - p2junk;
				}
				//prevent both players to be on the same spot
				if(p2location == p1location) {
					p2location = 0;
					details2 = "Player 2("+ player2 +") got a dice value of "+p2+"; Player 2 on the same square as Player 1: position reset; now in square "+p2location;
				}
				
				//Additional differentiation ,such as snakes and ladders
				Ladder();
				snake();
				System.out.println(details2);
				
				p1 = LadderAndSnake.flipDice();
				p1location+=p1;
				details1="Player 1("+ player1 +") got a dice value of "+p1+"; now in square "+p1location;
				Ladder();
				snake();
				
				if(p1location > 100) {
					int p1junk = p1location-100;
					p1location = 100 - p1junk;
				}
				if(p1location == p2location) {
					p1location = 0;
					details1 = "Player 1("+ player1 +") got a dice value of "+p1+"; Player 1 on the same square as Player 2: position reset; now in square "+p1location;
				}
				
				//Additional differentiation ,such as snakes and ladders
				Ladder();
				snake();
				//output result
				System.out.println(details1);		
				
				//if the player reaches exactly the position 100
				Victory();
				
				//Pause the game for a brief moment
				keyb.nextLine();
				//Re-display and re-modify the board according to the players' location
				Board();
				
				//Pause the game for a brief moment
				keyb.nextLine();

				 // Continuous loop until the game ends****
				if(hasWon == false) {
					System.out.println("Game not over; flipping again");
					continue;
				}
				
				//SHow the player's position on the board
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						
						if (p1location == boardNum[i][j]) {
							board[i][j] = "X1";
						}
						if (p2location == boardNum[i][j]) {
							board[i][j] = "X2";
						}
						
					}
				}
			}
		}
		//Close Scanner
		keyb.close();	
	}
	
	//New methods used to adjust the game depending on the user's roll dice count.
	/**
	 * Simulate all ladders to climb up the board.
	 */
	private void Ladder() {
		//temporary variables, 
		int p1junk = p1location;
		int p2junk = p2location;
	
		switch(p1junk) {
			case 1: 
				p1junk = 38;
				details1 +=" then up to square 38";
				break;
			case 4:
				p1junk = 14;
				details1 +=" then up to square 14";
				break;
			case 9:
				p1junk = 31;
				details1 +=" then up to square 31";
				break;
			case 21:
				p1junk = 42;
				details1 +=" then up to square 42";
				break;	
			case 28:
				p1junk = 84;
				details1 +=" then up to square 84";
				break;
			case 36:
				p1junk = 44;
				details1 +=" then up to square 44";
				break;
			case 51:
				p1junk = 67;
				details1 +=" then up to square 67";
				break;
			case 71:
				p1junk = 91;
				details1 +=" then up to square 91";
				break;
			case 80:
				p1junk = 100;
				details1 +=" then up to square 100";
				break;
		}
		//Re-assign new value
		p1location = p1junk;
		
		//player 2		
		switch(p2junk) {
			case 1: 
				p2junk = 38;
				details2 +=" then up to square 38";
				break;
			case 4:
				p2junk = 14;
				details2 +=" then up to square 14";
				break;
			case 9:
				p2junk = 31;
				details2 +=" then up to square 31";
				break;
			case 21:
				p2junk = 42;
				details2 +=" then up to square 42";
				break;	
			case 28:
				p2junk = 84;
				details2 +=" then up to square 84";
				break;
			case 36:
				p2junk = 44;
				details2 +=" then up to square 44";
				break;
			case 51:
				p2junk = 67;
				details2 +=" then up to square 67";
				break;
			case 71:
				p2junk = 91;
				details2 +=" then up to square 91";
				break;
			case 80:
				p2junk = 100;
				details2 +=" then up to square 100";
				break;
		}
		//Re-assign new value
		p2location = p2junk;
	}
	
	/**
	 * Simulate all snakes to bring the players down the board. (hindrance)
	 */
	private void snake() {
		//temporary variables
		int p1junk = p1location;
		int p2junk = p2location;
		
		//player 1
		switch(p1junk) {
		case 16: 
			p1junk = 6;
			details1 +=" then down to square 6";
			break;
		case 48:
			p1junk = 30;
			details1 +=" then down to square 30";
			break;
		case 64:
			p1junk = 60;
			details1 +=" then down to square 60";
			break;
		case 79:
			p1junk = 19;
			details1 +=" then down to square 19";
			break;	
		case 93:
			p1junk = 68;
			details1 +=" then down to square 68";
			break;
		case 95:
			p1junk = 24;
			details1 +=" then down to square 24";
			break;
		case 97:
			p1junk = 76;
			details1 +=" then down to square 76";
			break;
		case 98:
			p1junk = 78;
			details1 +=" then down to square 78";
			break;
		}
		//Re-assign new value to the players Location.
		p1location = p1junk;	
		
		//player 2
		switch(p2junk) {
		case 16: 
			p2junk = 6;
			details2 +=" then down to square 6";
			break;
		case 48:
			p2junk = 30;
			details2 +=" then down to square 30";
			break;
		case 64:
			p2junk = 60;
			details2 +=" then down to square 60";
			break;
		case 79:
			p2junk = 19;
			details2 +=" then down to square 19";
			break;	
		case 93:
			p2junk = 68;
			details2 +=" then down to square 68";
			break;
		case 95:
			p2junk = 24;
			details2 +=" then down to square 24";
			break;
		case 97:
			p2junk = 76;
			details2 +=" then down to square 76";
			break;
		case 98:
			p2junk = 78;
			details2 +=" then down to square 78";
			break;
		}
		//Re-assign new value to the players Location.
		p2location= p2junk;	
	}
		
	/**
	 * Method to detail when a player reaches 100.
	 */
	private void Victory() {
		if (p1location ==100 || p2location == 100) {
			//Output result
			if (p1location ==100) {
				board[0][0] = "X1";
				Board();
				System.out.println(player1+" has reached square 100");
				System.out.println("CONGRATULATIONS!! PLAYER 1 HAS WON!! \nC-E-L-E-B-R-A-T-E GOOD TIMES, COME ON!!");
				//***terminate program***
				System.exit(0);
				//used to exit from the continuous loop of  rolling dices
				hasWon = true;
			}
			else if (p2location ==100) {
				board[0][0] = "X2";
				Board();
				System.out.println(player2+" has reached square 100");
				System.out.println("CONGRATULATIONS!! PLAYER 2 HAS WON!! \nC-E-L-E-B-R-A-T-E GOOD TIMES, COME ON!!");
				//***terminate program***
				System.exit(0);
				//used to exit from the continuous loop of  rolling dices
				hasWon = true;
			}
		}
	}
}
