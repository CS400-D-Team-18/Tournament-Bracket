////////////////////////////////////////////////////////////////////////////
// Semester:         CS400 Spring 2018
// PROJECT:          JavaFX Tournament Bracket
// FILES:            Main.java
//                   Challenger.java
//                   Game.java
//                   Round.java
//
// THIS FILE:        Challenger.java
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

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Challenger {
	
	//Populated by UI
	double startX;
	double startY;
	Label challengerCell;
	TextField challengerScoreCell;
	
	//Populated by game management
	String name;
	double score;
	
	public Challenger(String name) {
		this.name = name;
		this.score = 0;
	}
	
	/**
	 * Update the UI information for this challenger
	 * @param challengerCell of current challenger
	 * @param challengerScoreCell of current challenger
	 */
	public void updateChallengerUIInfo(Label challengerCell, TextField challengerScoreCell)	{
		this.challengerCell = challengerCell;
		this.challengerScoreCell = challengerScoreCell;
	}
	
	/**
	 * Get this challenger's score
	 * @param challenger Current challenger
	 * @return score of this challenger
	 */
	public double getScore(Challenger challenger) {
		return this.score;
	}
	
	/**
	 * Get current challenger name.
	 * (Added by Joyce)
	 * 
	 * @return Challenger name
	 */
	public String getCName() {
		return this.name;
	}
	
}
