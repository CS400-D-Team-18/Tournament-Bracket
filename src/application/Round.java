////////////////////////////////////////////////////////////////////////////
// Semester:         CS400 Spring 2018
// PROJECT:          JavaFX Tournament Bracket
// FILES:            Main.java
//                   Challenger.java
//                   Game.java
//                   Round.java
//
// THIS FILE:        Round.java
// USER:             YOU-REN(Joyce) FANG,      yfang57@wisc.edu 
//                   AKHILA JACOB,             ajacob5@wisc.edu 
//                   RENG-HUNG(William) SHIH,  rshih2@wisc.edu 
//
// Instructor:       Deb Deppeler (deppeler@cs.wisc.edu)
// Bugs:             no known bugs, but not complete either
//
// Due Date:         May 3 before 10 pm
//////////////////////////// 80 columns wide //////////////////////////////////
package application;
import java.util.ArrayList;

public class Round {	
	
	int roundNumber;
	String roundName;
	ArrayList<Game> games;
	//Indicates if all the games in the round are complete
	boolean isComplete;
	
	/**
	 * (Added by Akhila_Jacob)
	 * Assign roundNumber, roundName and create new list of game for this round
	 * @param roundNumber Number of this round
	 * @param roundName Name of this round
	 */
	public Round(int roundNumber, String roundName) {
		this.roundNumber = roundNumber;
		this.roundName = roundName;
		this.games = new ArrayList<Game>();
	}
	
	/**
	 * (Added by Akhila_Jacob)
	 * Add game g in game list
	 * @param g The game we want to add 
	 */
	public void addGame(Game g){
		games.add(g);
	}
	
	/**
	 * Get current round name.
	 * (Added by Joyce)
	 * 
	 * @return round name
	 */
	public String getRoundName() {
		return this.roundName;
	}
	
}

