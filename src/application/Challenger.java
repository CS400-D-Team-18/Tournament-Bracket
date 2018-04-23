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
	
	public void updateChallengerUIInfo(Label challengerCell, TextField challengerScoreCell)	{
		this.challengerCell = challengerCell;
		this.challengerScoreCell = challengerScoreCell;
	}
	
}
