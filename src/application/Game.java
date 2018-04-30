////////////////////////////////////////////////////////////////////////////
// Semester:         CS400 Spring 2018
// PROJECT:          JavaFX Tournament Bracket
// FILES:            Main.java
//                   Challenger.java
//                   Game.java
//                   Round.java
//
// THIS FILE:        Game.java
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

import javafx.scene.control.Button;

public class Game {
	
	int gameNumber;
	Challenger challenger1;
	Challenger challenger2;
	//Variable indicates the child game to which the current game is pointing to
	Game childGame;
	//Variable indicates which challenger in the child game does the winner of the game corresponds to 
	int childGameChallengerNumber;
	Button gameScoreButton;
	//Indicates if the game is complete and winner has been declared
	boolean isComplete;
	
	public Game(int gameNumber) {
		this.gameNumber = gameNumber;
	}

	/**
	 * (Added by Akhila_Jacob)
	 * @param challenger1
	 * @param challenger2
	 * @param childGame
	 * @param childGameChallengerNumber
	 */
	public void updateGameInfo(Challenger challenger1, Challenger challenger2, Game childGame, int childGameChallengerNumber) {
		if (challenger1 != null) {
			this.challenger1 = challenger1;
		} else {
			this.challenger1 = new Challenger(null);
		}
		if (challenger2 != null) {
			this.challenger2 = challenger2;
		} else {
			this.challenger2 = new Challenger(null);
		}
		this.childGame = childGame;
		this.childGameChallengerNumber = childGameChallengerNumber;
	}
	
	/**
	 * @return
	 */
	public Button getGameScoreButton() {
		return gameScoreButton;
	}

	/**
	 * @param gameScoreButton
	 */
	public void setGameScoreButton(Button gameScoreButton) {
		this.gameScoreButton = gameScoreButton;
	}

	/**
	 * Method assists in calculating the coordinates of the child game each time we create  a parent game
	 * (Added by Akhila_Jacob)
	 * @param isLeft
	 * @param roundXGap
	 */
	public void updateChildGameChallengerCoordinates(boolean isLeft, double roundXGap)
	{
		Challenger childGameChallenger;
		double challengerCellWidth;
		double challengerCellHeight;
		if (childGame ==  null) {
			return;
		}
		if (childGameChallengerNumber == 1) {
			childGameChallenger = childGame.challenger1;
		} else {
			childGameChallenger = childGame.challenger2;
		}
		challengerCellWidth = challenger1.challengerCell.getMaxWidth()+challenger1.challengerScoreCell.getMaxWidth();
		challengerCellHeight = challenger1.challengerCell.getMaxHeight();
		if (isLeft) {
			childGameChallenger.startX = challenger1.startX + (challengerCellWidth + roundXGap);
		} else {
			childGameChallenger.startX = challenger1.startX - (challengerCellWidth + roundXGap);
		}
		childGameChallenger.startY = challenger1.startY + challengerCellHeight +  ((challenger2.startY - challenger1.startY - challengerCellHeight))/2 - (challengerCellHeight/2);
	}


	/**
	 * Get current game number
	 * (Added by Joyce)
	 * 
	 * @return gameNumber + 1
	 */
	public int getGameName() {
		return this.gameNumber+1;
	}
}
