////////////////////////////////////////////////////////////////////////////
// Semester:         CS400 Spring 2018
// PROJECT:          JavaFX Tournament Bracket
// FILES:            Main.java
//                   Challenger.java
//                   Game.java
//                   Round.java
//
// THIS FILE:        Main.java
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


public class Main extends Application {

	//Game management variables
	static ArrayList<String> nameList;         //Change it to static by Joyce
	static ArrayList<Challenger> challengers;  //Change it to static by Joyce
	ArrayList<Challenger> challengerList;
	ArrayList<Round> rounds;
	ArrayList<Game> games;  // Added by Joyce
	int numRounds;          // Added by Joyce
	int numGames;           // Added by Joyce

	//Layout calculation variables
	int numChallengers;
	int[] roundChallengerCountArray;

	//Layout UI variables
	Color sceneBGColor = Color.DARKGRAY;
	Color challengerCellFGColor = Color.WHITE;
	Color challengerCellBGColor = Color.AQUAMARINE;
	Color winnerCellBGColor = Color.KHAKI;
	Color winnerCellTitleBGColor = Color.BURLYWOOD;
	Color challengerScoreCellFGColor = Color.AQUAMARINE;
	Color challengerScoreCellBGColor = Color.WHITE;
	Color lineColor = Color.WHITE;
	TextField challengerScoreCell;

	double challengerCellWidth = 90;
	double challengerCellHeight = 25;
	double challengerScoreCellWidth = 45;
	double challengerScoreCellHeight = 25;
	double gameButtonWidth = 70 ;
	double gameButtonHeight = 25;
	double lineThickness = 2;

	double sceneWidth;
	double sceneHeight;
	double sceneMinWidth = 650;
	double sceneMinHeight = 180;

	double sceneAdditionalHeight = 100;

	double challengerBoxYGap = 60;
	double roundXGap = 50;
	
	
	//Global variables to set the winner,firstRunnerUp & SecondRunnerUp
	Label winnerCell;
	Label firstRunnerUpCell;
	Label secondRunnerUpCell;
	Challenger winner;
	Challenger firstRunnerUp;
	Challenger secondRunnerUp;

	//Map a button to each game ,so as to identify the game on each button click
	HashMap<Integer, Button> buttonMap = new HashMap<Integer, Button>();

	//UI instances
	static Pane root;	

	/**
	 * (Added by Akhila_Jacob)
	 * Computes the number of challengers to be kept in each round -this information
	 * is then used to set the scene width and scene height of the UI
	 */
	private void computeChallengerColumnArray(){
		//TODO: handle numChallengers = 0

		int currentNum = numChallengers;
		int i = 0;
		int j = 0;
		int counter = 0;

		if(currentNum > 0) {
			while(currentNum > 0) {
				currentNum = currentNum /2;
				counter++;
			}
			roundChallengerCountArray = new int[counter];
			//roundNumberArray = new int[counter];
			currentNum = numChallengers;
			i = 0;
			while(currentNum > 1) {
				roundChallengerCountArray[i] = currentNum;
				//roundNumberArray[i] = i+1;
				currentNum = currentNum/2;
				i++;
			}
			roundChallengerCountArray[i] = 1;
			//roundNumberArray[i] = 0;
		}else {
			roundChallengerCountArray = new int[1];
			//roundNumberArray = new int[1];
		}
	}
	/**
	 * (Added by Akhila_Jacob)
	 * Calculates the scene width depending on the number of challengers 
	 * we need to add to UI
	 */
	private void computeSceneWidth(){
		int x = 2*roundChallengerCountArray.length - 1 ;
		sceneWidth = x*(challengerCellWidth+challengerScoreCellWidth) + (x+1)*roundXGap;
		if(sceneWidth < sceneMinWidth) {
			sceneWidth = sceneMinWidth;
		}
		x = roundChallengerCountArray[0]/2;
		sceneHeight = x*challengerCellHeight + (x+1)*challengerBoxYGap;
		if(sceneHeight < sceneMinHeight) {
			sceneHeight = sceneMinHeight;
		}
	}
	
	/**
	 * Show a error alert with header text when #challengers is over 16.
	 * (Added by Joyce)
	 */
	private void showAlertWhenCNumberIsNotValid() {
		Alert alertCNumber = new Alert(AlertType.ERROR);
		alertCNumber.setTitle("Error Message");
		alertCNumber.setHeaderText("Number of challengers is not valid.");
		alertCNumber.setContentText("Please provide 1-16 challengers here. It should be power of 2.");
		alertCNumber.showAndWait();
	}
	
	/**
	 * Show a error alert with header text when #challengers is 0 or less.
	 * (Added by Joyce)
	 */
	private void showAlertWhenNoChallenger() {
		Alert alertNoC = new Alert(AlertType.ERROR);
		alertNoC.setTitle("Error Message");
		alertNoC.setHeaderText("You don't provide any challenger.");
		alertNoC.setContentText("Please provide 1-16 challengers here. It should be power of 2.");
		alertNoC.showAndWait();
	}

	/**
	 * Show a error alert with header text when user type in same scores in current game.
	 * (Added by Joyce)
	 */
	private void showAlertWhenTie() {
		Alert alertTie = new Alert(AlertType.ERROR);
		alertTie.setTitle("Error Message");
		alertTie.setHeaderText("Tied game.");
		alertTie.setContentText("You typed in same scores for two challengers. Please type different scores here.");
		alertTie.showAndWait();
	}
	
	private void showAlertWhenScoreIsEmpty() {
		Alert alertScoreIsEmpty = new Alert(AlertType.ERROR);
		alertScoreIsEmpty.setTitle("Error Message");
		alertScoreIsEmpty.setHeaderText("Score is empty.");
		alertScoreIsEmpty.setContentText("Please enter scores for both challengers before submit. "
				+ "Also, please avoid tied game and negative numbers.");
		alertScoreIsEmpty.showAndWait();
	}
	
	private void showAlertWhenScoreNotValid() {
		Alert alertScoreNotValid = new Alert(AlertType.ERROR);
		alertScoreNotValid.setTitle("Error Message");
		alertScoreNotValid.setHeaderText("Score is not valid.");
		alertScoreNotValid.setContentText("Please enter only positive numbers for challenger score. "
				+ "Also, plase avoid tied game and negative numbers.");
		alertScoreNotValid.showAndWait();
	}
	
	
	// 2.check if it is to power of 2
	//validateNameList();
	public boolean validateNameList(ArrayList<String> list) {
		int s = list.size();
		if (s == 0)
            	return false;
         
		while (s != 1)
		{
		    if (s % 2 != 0)
			return false;
		    s = s / 2;
		}
		return true;
	}
	//3.Rearrange them in the order as per the seating
	//reArrangeAccordingToRank();
	public static void reArrangeAccordingToRank()
	{
		int TG = nameList.size();
		
        //Console.WriteLine("Number of games: {0}", TG);
        int[] ind = new int[TG];
        int[] playOrder = new int[TG+1];
        
        for (int i = 0; i <= (int)( Math.log((double)TG)/Math.log((double)2)); i++)       
        {
            for (int N = 1; N <= TG; ++N)
            {
                int myRank = (N - 1) / (int)Math.pow(2.0, (double)i) + 1;
                ind[N - 1] += ((myRank % 4) / 2) * (int)Math.pow(2.0, ((int)( Math.log((double)TG)/Math.log((double)2)) - i - 1));
            }
        }
        for (int N = 1; N <= TG; ++N)
        {
            playOrder[N]= ind[N - 1] + 1;
            //Console.WriteLine("Team {0} plays game {1}", N, ind[N - 1] + 1);
        }
        ArrayList<String> nameListArranged = new ArrayList();
        for(int i = 1; i < playOrder.length; i++)
        {
        		int index = searchPlayOrderRetArrayIndex( i, playOrder);
        		nameListArranged.add(nameList.get(index - 1));
        }
		
        nameList = nameListArranged;
	
	}
	private static int searchPlayOrderRetArrayIndex( final int PlayOrder, final int[] PlayOrderArr )
	{
		for(int i=1; i < PlayOrderArr.length; i++ )
		{
			if(PlayOrderArr[i] == PlayOrder)
				return i;
		}
		return 0;
	}
	
	// 4.convert these namelist into an array;list of challenger objects
	//createChallengerList();
	public ArrayList<Challenger> createChallengerList(ArrayList<String> list) {
		challengerList = new ArrayList<Challenger>();
		for (int i = 0; i < list.size(); i++) {
			Challenger c = new Challenger(list.get(i));
			this.challengerList.add(c);
		}
		return this.challengerList;
	}
	
	public void gameManagement() {
		// (1)Set numRound, numGame depends on numChallenger 
		setNumberInfo();

		// (2)Set each game
		games = new ArrayList<Game>();
		ArrayList<Integer> gList = new ArrayList<Integer>(); // compute each round has how many games
		int g = this.numChallengers;
		for (int i = 0; i < this.numRounds; i++) {
			g = g / 2;
			gList.add(g);
		}
		// (3)Add each game to ArrayList games
		for (int i = 0; i < this.numGames; i++) {
			Game gn = new Game(i);
			games.add(gn);
		}
		setInitialGame(gList);

		// (4)Set each round name and add each round to rounds
		String[] roundNameList = { "CHAMPIONSHIP", "SEMI-FINAL", "QUARTER-FINAL", "1ST ROUND" };
		rounds = new ArrayList<Round>();
		int max = this.numRounds;
		for (int i = this.numRounds; i > 0; i--) {
			int r = max - i + 1;
			Round rn = new Round(r, roundNameList[i - 1]);
			rounds.add(rn);
		}
		
		// (5)Add each game to correspond round
		int gameIdx = 0;
		for (int i = 0; i < this.numRounds; i++) {
			for (int j = 0 ; j < gList.get(i); j++) {
				
				rounds.get(i).addGame(games.get(gameIdx + j));
				
			}
			gameIdx += gList.get(i);
		}
	}
	
	/**
	 * Number of rounds = log2(numChallengers)
	 * 
	 * @param n Number of Challengers
	 * @param base Always use 2 here
	 * @return x Number of rounds
	 */
	private int log(int n, int base) {
		int x = (int)(Math.log(n) / Math.log(base));
		return x;
	}
	
	/**
	 * Set the numRounds, numGames depends on numChallengers
	 */
	private void setNumberInfo() {	
		if (this.numChallengers > 16 ) {
			System.out.println("We don't provide Tournament Bracket over 16 challengers.");
		} else {
			this.numRounds = log(this.numChallengers, 2);
			this.numGames = this.numChallengers -1;
			System.out.println("numRounds =" + this.numRounds + ", numGames = "+ this.numGames);
		}
	}
	
	/**
	 * Use updateGameInfo() to set initial game, that means add each challenger to correct game.
	 * 
	 * @param gList The list contains each round should have how many games.
	 */
	private void setInitialGame(ArrayList<Integer> gList) {	
		// TODO: Should use re-arranged challenger list instead of challengers!!! 
		if (this.numRounds == 4) {
			// 16 challengers, 15 games, 4 rounds
			// gList = {8, 4, 2, 1}
			games.get(0).updateGameInfo(challengerList.get(0), challengerList.get(1), games.get(8), 1);
			games.get(1).updateGameInfo(challengerList.get(2), challengerList.get(3), games.get(8), 2);
			games.get(2).updateGameInfo(challengerList.get(4), challengerList.get(5), games.get(9), 1);
			games.get(3).updateGameInfo(challengerList.get(6), challengerList.get(7), games.get(9), 2);
			games.get(4).updateGameInfo(challengerList.get(8), challengerList.get(9), games.get(10), 1);
			games.get(5).updateGameInfo(challengerList.get(10), challengerList.get(11), games.get(10), 2);
			games.get(6).updateGameInfo(challengerList.get(12), challengerList.get(13), games.get(11), 1);
			games.get(7).updateGameInfo(challengerList.get(14), challengerList.get(15), games.get(11), 2);
			
			games.get(8).updateGameInfo(null, null, games.get(12), 1);
			games.get(9).updateGameInfo(null, null, games.get(12), 2);
			games.get(10).updateGameInfo(null, null, games.get(13), 1);
			games.get(11).updateGameInfo(null, null, games.get(13), 2);
			
			games.get(12).updateGameInfo(null, null, games.get(14), 1);
			games.get(13).updateGameInfo(null, null, games.get(14), 2);
			
			games.get(14).updateGameInfo(null, null, null, 0);		
			
		} else if (this.numRounds == 3) {
			// gList = {4, 2, 1}
			games.get(0).updateGameInfo(challengerList.get(0), challengerList.get(1), games.get(4), 1);
			games.get(1).updateGameInfo(challengerList.get(2), challengerList.get(3), games.get(4), 2);
			games.get(2).updateGameInfo(challengerList.get(4), challengerList.get(5), games.get(5), 1);
			games.get(3).updateGameInfo(challengerList.get(6), challengerList.get(7), games.get(5), 2);
			
			games.get(4).updateGameInfo(null, null, games.get(6), 1);
			games.get(5).updateGameInfo(null, null, games.get(6), 2);
			
			games.get(6).updateGameInfo(null, null, null, 0);
			
		} else if (this.numRounds == 2 ) {
			// gList = {2, 1}
			games.get(0).updateGameInfo(challengerList.get(0), challengerList.get(1), games.get(2), 1);
			games.get(1).updateGameInfo(challengerList.get(2), challengerList.get(3), games.get(2), 2);
			
			games.get(2).updateGameInfo(null, null, null, 0);
		
		} else if (this.numRounds ==1 ) {
			// gList = {1}
			games.get(0).updateGameInfo(challengerList.get(0), challengerList.get(1), null, 0);
			
		} else if (this.numRounds == 0) {
			winner = challengerList.get(0);
			return;
		} else {
			this.numRounds = -1;
			return;
		}
	}
	
	
	//UI draw helpers
	/**
	 * (Added by Akhila_Jacob)
	 * Used to draw a challengerNameLabel and challengerScoreTextField
	 * @param name
	 * @param x
	 * @param y
	 * @param boxtype
	 * @param c
	 * @param type
	 */
	public void addChallengerBox(String name, double x, double y, int boxtype, Challenger c,int type){
		TextField challengerScoreCell = null;
		Label challengerCell = new Label();
		if(type == 2 || type == 4  || type == 5  || type == 6) {
			challengerCell.setBackground(new Background(new BackgroundFill(winnerCellBGColor, CornerRadii.EMPTY, Insets.EMPTY)));	
		}else if(type == 3){
			challengerCell.setBackground(new Background(new BackgroundFill(winnerCellTitleBGColor, CornerRadii.EMPTY, Insets.EMPTY)));	
		}else {
			challengerCell.setBackground(new Background(new BackgroundFill(challengerCellBGColor, CornerRadii.EMPTY, Insets.EMPTY)));
		}
		challengerCell.setAlignment(Pos.CENTER);
		challengerCell.setText(name);
		if (boxtype == 1){
			challengerCell.setMinSize(challengerCellWidth+challengerScoreCellWidth, challengerCellHeight);
			challengerCell.setMaxSize(challengerCellWidth+challengerScoreCellWidth, challengerCellHeight);
		}
		else{
			challengerCell.setMinSize(challengerCellWidth, challengerCellHeight);
			challengerCell.setMaxSize(challengerCellWidth, challengerCellHeight);
			challengerScoreCell = new TextField();
			challengerScoreCell.setMinSize(challengerScoreCellWidth, challengerScoreCellHeight);
			challengerScoreCell.setMaxSize(challengerScoreCellWidth, challengerScoreCellHeight);
			challengerScoreCell.setBackground(new Background(new BackgroundFill(challengerScoreCellBGColor, CornerRadii.EMPTY, Insets.EMPTY)));
			//challengerScoreCell.setTextFill(challengerScoreCellFGColor);
			challengerScoreCell.setAlignment(Pos.CENTER);
			challengerScoreCell.setPromptText("Score");
			if( name ==  null) {
				challengerScoreCell.setDisable(true);
			}
		}
		challengerCell.setLayoutX(x);
		challengerCell.setLayoutY(y);	
		this.root.getChildren().add(challengerCell);
		if (challengerScoreCell != null){
			challengerScoreCell.setLayoutX(x+challengerCellWidth);
			challengerScoreCell.setLayoutY(y);
			this.root.getChildren().add(challengerScoreCell);
		}
		if (c != null) {
			c.startX = x;
			c.startY = y;
			c.challengerCell = challengerCell;
			c.challengerScoreCell = challengerScoreCell;
		} 
		if(type == 4) {
		this.winnerCell = challengerCell;	
		}else if( type == 5) {
			this.firstRunnerUpCell = challengerCell;	
		}else if ( type == 6) {
			this.secondRunnerUpCell = challengerCell;	
		}
	}
	
	//Game propagation helpers
	
	
	/**
	 * (Added by Akhila_Jacob)
	 * Returns the game corresponding to Game Number
	 * @param gameNumber
	 * @return
	 */
	public Game getGameFromGameNumber(int gameNumber) {
		for(Round r : rounds) {
			for(Game game : r.games) {
				if (game.gameNumber == gameNumber) {
					return game;
				}
			}
		}
		return null;
	}
	
	/**
	 * (Added by Akhila_Jacob)
	 * Checks if a score has been entered for each challenger corresponding to a game and validates the value
	 * If valid then we consider the game to be complete.
	 * @param currentGame
	 * @return
	 */
	public boolean isGameComplete(Game currentGame) {
		//TO DO
		// + THIS SHOULD CHECK BOTH CHALLENGER SCORES AND THROW ERRORS
		// + IF NOT VALID, THEN RETURN FALSE
		// THROW ERROR IF CHALLENGER SCORES ARE THE SAME
		// IF VALID, THIS SHOULD UPDATE CHALLENGER SCORES AND RETURN TRUE
		String challenger1Score = currentGame.challenger1.challengerScoreCell.getText();
		String challenger2Score = currentGame.challenger2.challengerScoreCell.getText();
		if (challenger1Score == null || challenger2Score == null ||
				challenger1Score.isEmpty() || challenger2Score.isEmpty()) { 
			showAlertWhenScoreIsEmpty();
			return false;
			
		} else if (!challenger1Score.matches("[0-9]+") || !challenger2Score.matches("[0-9]+")) {// please type positive number
			showAlertWhenScoreNotValid();
			return false;
			
		} else if (Double.parseDouble(challenger1Score) == Double.parseDouble(challenger2Score)) {
			showAlertWhenTie();
			return false;

		}
		else {
			currentGame.challenger1.score = Double.parseDouble(challenger1Score);
			currentGame.challenger2.score = Double.parseDouble(challenger2Score);
			currentGame.isComplete = true;
			return true;
		}
	}
	
	/**
	 * (Added by Akhila_Jacob)
	 * Method used to disable the Label,Button and textField corresponding to a round once
	 * the round is complete
	 * @param r
	 */
	public void disableRound(Round r) {
		for(Game g : r.games) {
			g.challenger1.challengerScoreCell.setDisable(true);
			g.challenger2.challengerScoreCell.setDisable(true);
			g.gameScoreButton.setDisable(true);
			//g.gameScoreButton.setVisible(false);
		}
	}
	
	/**
	 * (Added by Akhila_Jacob)
	 * Method used to enable the Label,Button and textField corresponding to the next round
	 * once the present round is complete
	 * @param r
	 */
	public void enableRound(Round r) {
		for(Game g : r.games) {
			g.challenger1.challengerScoreCell.setDisable(false);
			g.challenger2.challengerScoreCell.setDisable(false);
			g.gameScoreButton.setDisable(false);
			//g.gameScoreButton.setVisible(true);
		}
	}
	
	/**
	 * (Added by Akhila_Jacob)
	 * 1.Check if the current round is complete
	 * 2.If round not complete then no further steps to be done else
	 * 3.If the last round is the one that is completed currently then we need to update the champion,FirstRunnerUp and Second RunnerUp
	 * 4.Else we just need to disable present round and enable next round
	 */
	public void updateGameProgress() {
		boolean roundComplete;
		Round nextRound;
		int lastRoundNumber = 0;
		boolean lastRoundComplete = false;
		int totalRounds = rounds.size();
		Round tempRound;
		Game tmpGame ;
		for(Round r : rounds) {
			if (!r.isComplete) {
				roundComplete = true;
				for(Game g : r.games) {
					if (!g.isComplete) {
						roundComplete = false;
						break;
					}
				}
				if (roundComplete) {
					r.isComplete = true;
					disableRound(r);
					lastRoundNumber = r.roundNumber;
					lastRoundComplete = true;
					break;
				}
			}
		}
		if (!lastRoundComplete) {
			return;
		}
		if (lastRoundNumber == rounds.size()) {
			//UPDATE WINNER

			//Updating only the champion and first runnerup
			tempRound = rounds.get(totalRounds -1 );	
			tmpGame = tempRound.games.get(0);
			if(tmpGame.challenger1.score > tmpGame.challenger2.score) {
				winner = tmpGame.challenger1;	
				firstRunnerUp = tmpGame.challenger2;
			}else {
				winner = tmpGame.challenger2;	
				firstRunnerUp = tmpGame.challenger1;
			}
			//Case where we can also find the second runner up
			double maxScore = 0;
			if( totalRounds-2 >= 0) {
				tempRound = rounds.get(totalRounds -2 );	
				for(Game g : tempRound.games) {
					if(!winner.name.equals(g.challenger1.name)   && !firstRunnerUp.name.equals(g.challenger1.name)  ) {
						if(g.challenger1.score > maxScore) {
							maxScore = g.challenger1.score;	
							secondRunnerUp = g.challenger1;
						}
					}
					if(!winner.name.equals(g.challenger2.name)   && !firstRunnerUp.name.equals(g.challenger2.name)  ) {
						if(g.challenger2.score > maxScore) {
							maxScore = g.challenger2.score;	
							secondRunnerUp = g.challenger2;
						}
					}
				}
			}
			
			//set the textfeild 
			winnerCell.setText(winner.name);
			firstRunnerUpCell.setText(firstRunnerUp.name);
			if(secondRunnerUp != null) {
				secondRunnerUpCell.setText(secondRunnerUp.name);	
			}
		} else {
			nextRound = rounds.get(lastRoundNumber);
			enableRound(nextRound);
		}
	}
	/**
	 * (Added by Akhila_Jacob)
	 * 1.Check if the current game is complete
	 * 2.If complete then propogate the winner to the correct challenger of the 
	 * child game
	 */
	public void submitGameScore(int gameNumber) {
		Game currentGame = getGameFromGameNumber(gameNumber);
		Challenger childChallenger;
		Challenger winningChallenger;
		if (currentGame == null) {
			return;
		}
		if (!isGameComplete(currentGame)) {
			return;
		}
		
		if (currentGame.childGame != null) {
			if (currentGame.childGameChallengerNumber == 1) {
				childChallenger = currentGame.childGame.challenger1;
			} else {
				childChallenger = currentGame.childGame.challenger2;
			}
			if (currentGame.challenger1.score > currentGame.challenger2.score) {
				winningChallenger = currentGame.challenger1;
			} else {
				winningChallenger = currentGame.challenger2;
			}
			childChallenger.name = winningChallenger.name;
			childChallenger.challengerCell.setText(childChallenger.name);
		}
		
		updateGameProgress();
	}
	
	//UI draw helpers
	/**
	 * (Added by Akhila_Jacob)
	 * Method to add buttons for each game
	 * @param gameNum
	 * @param x
	 * @param y
	 * @param g
	 * @param roundNumber
	 */
	public void addGameScoreButton(int gameNum,double x, double y,Game g,int roundNumber){
		Button button = new Button();
		button.setText("Submit");
		button.setMinSize(gameButtonWidth, gameButtonHeight);
		button.setMaxSize(gameButtonWidth, gameButtonHeight);

		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int gameNumber = 0;
				for(Map.Entry<Integer, Button> entry :buttonMap.entrySet()) {
					if(entry.getValue() == event.getSource()) {
						gameNumber = entry.getKey();
						break;
					}	
				}
				submitGameScore(gameNumber);
			}
		});
		button.setLayoutX(x);
		button.setLayoutY(y);	
		if( roundNumber!= 1) {
			button.setDisable(true);	
		}
		g.setGameScoreButton(button);
		buttonMap.put(gameNum, button);
		this.root.getChildren().add(button);

	}

	/**
	 * (Added by Akhila_Jacob)
	 * Method to add lined to indicate game propogation
	 * @param x
	 * @param y
	 * @param linetype
	 * @param length
	 */
	public void addLine(double x, double y, int linetype, double length){
		Line ln = new Line();
		ln.setStrokeWidth(lineThickness);
		ln.setStroke(lineColor);
		ln.setStartX(x);
		ln.setStartY(y);
		if (linetype == 1)
		{
			ln.setEndX(x+length);
			ln.setEndY(y);
		}
		else
		{
			ln.setEndX(x);
			ln.setEndY(y+length);
		}
		this.root.getChildren().add(ln);
	}
	
	/**
	 * (Added by Akhila_Jacob)
	 * Method contains the logic for drawing the tournament bracket
	 */
	public void drawBracket(){
		double x;
		double y;
		Game currentGame;
		int i;
		boolean isLeft;
		double buttonStartX = 0;
		double buttonStartY = 0;
		double lineStartX = 0;
		double lineStartY = 0;
		double length = 0;

		int numGames = 0;
		if (rounds.size() < 0) {
			//Case when no input is passed from user  i.e no round
			return;
		}else if(rounds.size() == 0) {
			//Case when a single challenger is present ,so here the single person is winner
			x = ((sceneWidth/2)-((challengerCellWidth + challengerScoreCellWidth)/2));
			y =  ((sceneHeight/2)+(challengerCellHeight/2));
			addChallengerBox(winner.name,x,y, 1, null,4);
			addChallengerBox("Champion", x,y - challengerCellHeight, 1, winner,3);
		} else if (rounds.size()  == 1) {
			//Case when there is only two challengers present
			x = roundXGap;
			y = challengerBoxYGap;
			currentGame = rounds.get(0).games.get(0);
			addChallengerBox(currentGame.challenger1.name, x, y, 2, currentGame.challenger1,1);
			x = sceneWidth - roundXGap - (challengerCellWidth + challengerScoreCellWidth);
			y = challengerBoxYGap;
			addChallengerBox(currentGame.challenger2.name, x, y, 2, currentGame.challenger2,1);
			lineStartX = currentGame.challenger1.startX + (challengerCellWidth + challengerScoreCellWidth);
			lineStartY = currentGame.challenger1.startY + challengerCellHeight/2;
			length = currentGame.challenger2.startX - currentGame.challenger1.startX - challengerCellWidth-challengerScoreCellWidth;
			addLine(lineStartX,lineStartY, 1,length);
			buttonStartX = ((sceneWidth/2) - gameButtonWidth/2);
			buttonStartY =  lineStartY - gameButtonHeight/2;
			addGameScoreButton(currentGame.gameNumber,buttonStartX,buttonStartY,currentGame,1);
			addLine(lineStartX + length/2, buttonStartY +gameButtonHeight, 2, challengerCellHeight/2+challengerBoxYGap);
			
			x =  ((sceneWidth/2)-((challengerCellWidth + challengerScoreCellWidth)/2));
			y = ((sceneHeight/2)+(challengerCellHeight/2))+30;
			addChallengerBox("Champion", x,y, 1, winner,3);
			addChallengerBox(null, x ,y+challengerCellHeight, 1, winner,4);
			addChallengerBox("FirstRunnerUp",x - roundXGap -challengerCellWidth -challengerScoreCellWidth, y, 1, null,3);
			addChallengerBox(null, x - roundXGap-challengerCellWidth -challengerScoreCellWidth , y+challengerCellHeight, 1, firstRunnerUp,5);

		}else {
			//Case when there are multiple rounds
			for(Round r:rounds) {		
				numGames =  r.games.size();
				if (r.roundNumber == 1) {
					//Only for the first round we know the challenger names and that is drawn at the start
					//At the same the start locations of the child game is also computed 
					x = roundXGap;
					y = challengerBoxYGap;
					//Half of the game is drawn in the left
					for( i = 0; i<numGames/2; i++) {
						currentGame = r.games.get(i);
						addChallengerBox(currentGame.challenger1.name, x, y, 2, currentGame.challenger1,1);
						y = y + challengerCellHeight + challengerBoxYGap;
						addChallengerBox(currentGame.challenger2.name, x, y, 2, currentGame.challenger2,1);
						y = y + challengerCellHeight + challengerBoxYGap;
						currentGame.updateChildGameChallengerCoordinates(true, roundXGap);		
					}
					x = sceneWidth - roundXGap - (challengerCellWidth + challengerScoreCellWidth);
					y = challengerBoxYGap;	
					//The other half is drawn in the right
					for(i = numGames/2; i<numGames; i++) {
						currentGame = r.games.get(i);
						addChallengerBox(currentGame.challenger1.name, x, y, 2, currentGame.challenger1,1);
						y = y + challengerCellHeight + challengerBoxYGap;
						addChallengerBox(currentGame.challenger2.name, x, y, 2, currentGame.challenger2,1);
						y = y + challengerCellHeight + challengerBoxYGap;
						currentGame.updateChildGameChallengerCoordinates(false, roundXGap);
					}
				} else {
					//All round except the first round is drawn here
					for(i = 0; i<numGames; i++) {
						if (i < numGames/2) {
							isLeft = true;
						} else {
							isLeft = false;
						}						
						currentGame = r.games.get(i);
						addChallengerBox(null, currentGame.challenger1.startX, currentGame.challenger1.startY, 2, currentGame.challenger1,1);
						currentGame.updateChildGameChallengerCoordinates(isLeft, roundXGap);
						addChallengerBox(null, currentGame.challenger2.startX, currentGame.challenger2.startY, 2, currentGame.challenger2,1);
						currentGame.updateChildGameChallengerCoordinates(isLeft, roundXGap);
					}
				}
			}
			//Next we add the buttons corresponding to each game
			for(Round r:rounds) {		
				numGames =  r.games.size();
				if (numGames > 1 ) {
					for( i = 0; i<numGames;i++) {
						currentGame = r.games.get(i);
						lineStartX = currentGame.challenger1.startX + (challengerCellWidth + challengerScoreCellWidth)/2;
						lineStartY = currentGame.challenger1.startY + challengerCellHeight;
						addLine(lineStartX,lineStartY, 2,currentGame.challenger2.startY  - currentGame.challenger1.startY - challengerCellHeight);

						buttonStartX = currentGame.challenger1.startX + ((challengerCellWidth + challengerScoreCellWidth)/2) - gameButtonWidth/2;
						buttonStartY = currentGame.challenger1.startY +(currentGame.challenger2.startY  - currentGame.challenger1.startY )/2 +challengerCellHeight	- gameButtonHeight;
						addGameScoreButton(currentGame.gameNumber,buttonStartX,buttonStartY,currentGame,r.roundNumber);

						if( i < numGames/2) {
							addLine(buttonStartX + gameButtonWidth,buttonStartY + gameButtonHeight/2, 1,(challengerScoreCellWidth +challengerCellWidth)/2 +roundXGap-gameButtonWidth/2);
						}else {
							addLine(currentGame.challenger1.startX - roundXGap,buttonStartY + gameButtonHeight/2, 1,roundXGap + (challengerScoreCellWidth +challengerCellWidth)/2- gameButtonWidth/2 );

						}
					} 
				}else if(numGames == 1 ) {
					currentGame = r.games.get(0);

					lineStartX = currentGame.challenger1.startX + (challengerCellWidth + challengerScoreCellWidth);
					lineStartY = currentGame.challenger1.startY + challengerCellHeight/2;
					addLine(lineStartX,lineStartY, 1,currentGame.challenger2.startX - currentGame.challenger1.startX - challengerCellWidth-challengerScoreCellWidth);

					buttonStartX = ((sceneWidth/2) - gameButtonWidth/2);
					buttonStartY =  sceneHeight/2 - gameButtonHeight/2;
					addGameScoreButton(currentGame.gameNumber,buttonStartX,buttonStartY,currentGame,r.roundNumber);
					addLine(buttonStartX+gameButtonWidth/2, buttonStartY+gameButtonHeight, 2, challengerCellHeight/2+30);
				}
			}
			//Add the UI units to display the champion,firstRunnerUp and SeconRunnerUp
			x = ((sceneWidth/2)-((challengerCellWidth + challengerScoreCellWidth)/2));
			y = ((sceneHeight/2)+(challengerCellHeight/2))+30;
			addChallengerBox("Winner",x, y, 1, null,3);
			addChallengerBox(null, x , y+challengerCellHeight, 1, winner,4);
			if(rounds.size() >= 2) {
				addChallengerBox("FirstRunnerUp",x - roundXGap -challengerCellWidth -challengerScoreCellWidth, y, 1, null,3);
				addChallengerBox(null, x - roundXGap-challengerCellWidth -challengerScoreCellWidth , y+challengerCellHeight, 1, firstRunnerUp,5);
				addChallengerBox("SecondRunnerUp",x + roundXGap +challengerCellWidth +challengerScoreCellWidth, y, 1, null,3);
				addChallengerBox(null, x + roundXGap+challengerCellWidth +challengerScoreCellWidth , y+challengerCellHeight, 1, secondRunnerUp,6);
			}

		}
		
	}

	@Override
	public void start(Stage primaryStage) {
		try {

			// 2.check if it is to power of 2
			boolean valid = validateNameList(nameList);
			System.out.println(valid);

			//3.Rearrange them in the order as per the seating
			reArrangeAccordingToRank();

			// 4.convert these namelist into an array;list of challenger objects
			createChallengerList(nameList);

			root = new Pane();

			//5. set the num of challengers from the challenger object
			this.numChallengers = this.nameList.size();
			
			// 6.call the game management, set the 1st round in the beginning
			//can call the method as gameManagement
			// Show error message if challenger number is not valid.
			if (valid) {
				gameManagement();
			} else if (this.numChallengers > 16) {
				showAlertWhenCNumberIsNotValid();
				System.exit(-1);
			} else { // 0 challenger
				showAlertWhenNoChallenger();
				System.exit(-1);
			}
		
			for (int i = 0; i < this.numChallengers; i++) {
				System.out.print(challengerList.get(i).getCName()+", ");
			}
			System.out.println("");
			for (int i = 0; i < this.numGames; i++) {
				System.out.print("game"+games.get(i).getGameName()+", ");
			}
			System.out.println("");
			for (int i = 0; i < this.numRounds; i++) {
				System.out.print(rounds.get(i).getRoundName()+", ");
			}
			System.out.println("");	
			
			this.computeChallengerColumnArray();
			this.computeSceneWidth();
			this.drawBracket();

			Scene scene = new Scene(root,sceneWidth,sceneHeight+sceneAdditionalHeight);
			root.setBackground(new Background(new BackgroundFill(sceneBGColor, CornerRadii.EMPTY, Insets.EMPTY)));
			primaryStage.setTitle("Tournament Bracket");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
			
			

			// TODO: 7. get score input and decide who is champion
			// call event handler when user press submit button
			// get scores for two challengers 
			//  - if only one score, show error message
			//  - if tie, show error message
			// assign winner to next round until champion game
			// show top 3 challengers
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// 1. Code for reading file from the argument passed and store it in an arraylist of string- global --> nameList
		String fileName = args[0];
		File inputFile = null;
		Scanner sc = null;
		nameList = new ArrayList<String>();
		root = new Pane();
		challengers = new ArrayList<Challenger>();
		try {
			inputFile = new File(fileName);
			sc = new Scanner(inputFile);
			while(sc.hasNextLine()) {
				String name = sc.nextLine().trim().replace("\t", "");
				if ((!name.equals(null)) && (!name.equals(""))) {
					nameList.add(name);
				}
			}
			sc.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}

		launch(args);
	}
}

