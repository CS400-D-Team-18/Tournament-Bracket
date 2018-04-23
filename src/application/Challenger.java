package application;

import javafx.scene.control.Label;

public class Challenger {
	
	//Populated by UI
	double startX;
	double startY;
	Label challengerCell;
	Label challengerScoreCell;
	
	//Populated by game management
	String name;
	double score;
	
	public Challenger(String name) {
		this.name = name;
		this.score = 0;
	}
	
	public void updateChallengerUIInfo(Label challengerCell, Label challengerScoreCell)	{
		this.challengerCell = challengerCell;
		this.challengerScoreCell = challengerScoreCell;
	}
	
}
