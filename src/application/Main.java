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
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


public class Main extends Application {

	//Game management variables
	static ArrayList<String> nameList;         //Change it to static by Joyce
	static ArrayList<Challenger> challengers;  //Change it to static by Joyce
	ArrayList<Round> rounds;
	ArrayList<Game> games;  // Added by Joyce
	int numRounds;          // Added by Joyce
	int numGames;           // Added by Joyce

	//Layout calculation variables
	int numChallengers;
	int[] roundChallengerCountArray;
	int[] roundNumberArray;

	//Layout UI variables
	Color sceneBGColor = Color.DARKGRAY;
	Color challengerCellFGColor = Color.WHITE;
	Color challengerCellBGColor = Color.BROWN;
	Color challengerScoreCellFGColor = Color.CADETBLUE;
	Color challengerScoreCellBGColor = Color.WHITE;
	Color lineColor = Color.WHITE;
	TextField challengerScoreCell;

	double challengerCellWidth = 100;
	double challengerCellHeight = 25;
	double challengerScoreCellWidth = 50;
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

	Label winnerCell;

	//Layout management variables


	//UI instances
	static Pane root;	

	//Computing layout
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
			roundNumberArray = new int[counter];

			currentNum = numChallengers;
			i = 0;
			while(currentNum > 1) {
				roundChallengerCountArray[i] = currentNum;
				roundNumberArray[i] = i+1;
				currentNum = currentNum/2;
				i++;
			}
			roundChallengerCountArray[i] = 1;
			roundNumberArray[i] = 0;
			//			j = i  ;
			//			for(;j>=0;j-- ,i++) {
			//				challengerColumn[i] = challengerColumn[j];
			//				roundArray[i] = roundArray[j];
			//			}

		}else {
			roundChallengerCountArray = new int[1];
			roundNumberArray = new int[1];
		}
		for(int k =0;k<roundNumberArray.length;k++) {
			System.out.print(" "+roundNumberArray[k]);
		}
	}

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
			games.get(0).updateGameInfo(challengers.get(0), challengers.get(1), games.get(8), 1);
			games.get(1).updateGameInfo(challengers.get(2), challengers.get(3), games.get(8), 2);
			games.get(2).updateGameInfo(challengers.get(4), challengers.get(5), games.get(9), 1);
			games.get(3).updateGameInfo(challengers.get(6), challengers.get(7), games.get(9), 2);
			games.get(4).updateGameInfo(challengers.get(8), challengers.get(9), games.get(10), 1);
			games.get(5).updateGameInfo(challengers.get(10), challengers.get(11), games.get(10), 2);
			games.get(6).updateGameInfo(challengers.get(12), challengers.get(13), games.get(11), 1);
			games.get(7).updateGameInfo(challengers.get(14), challengers.get(15), games.get(11), 2);
			
			games.get(8).updateGameInfo(null, null, games.get(12), 1);
			games.get(9).updateGameInfo(null, null, games.get(12), 2);
			games.get(10).updateGameInfo(null, null, games.get(13), 1);
			games.get(11).updateGameInfo(null, null, games.get(13), 2);
			
			games.get(12).updateGameInfo(null, null, games.get(14), 1);
			games.get(13).updateGameInfo(null, null, games.get(14), 2);
			
			games.get(14).updateGameInfo(null, null, null, 0);		
			
		} else if (this.numRounds == 3) {
			// gList = {4, 2, 1}
			games.get(0).updateGameInfo(challengers.get(0), challengers.get(1), games.get(4), 1);
			games.get(1).updateGameInfo(challengers.get(2), challengers.get(3), games.get(4), 2);
			games.get(2).updateGameInfo(challengers.get(4), challengers.get(5), games.get(5), 1);
			games.get(3).updateGameInfo(challengers.get(6), challengers.get(7), games.get(5), 2);
			
			games.get(4).updateGameInfo(null, null, games.get(6), 1);
			games.get(5).updateGameInfo(null, null, games.get(6), 2);
			
			games.get(6).updateGameInfo(null, null, null, 0);
			
		} else if (this.numRounds == 2 ) {
			// gList = {2, 1}
			games.get(0).updateGameInfo(challengers.get(0), challengers.get(1), games.get(2), 1);
			games.get(1).updateGameInfo(challengers.get(2), challengers.get(3), games.get(2), 2);
			
			games.get(2).updateGameInfo(null, null, null, 0);
		
		} else if (this.numRounds ==1 ) {
			// gList = {1}
			games.get(0).updateGameInfo(challengers.get(0), challengers.get(1), null, 0);
			
		} else if (this.numRounds == 0) {
			winner = challengers.get(0);
			return;
		} else {
			this.numRounds = -1;
			return;
		}
	}
	
	
	//UI draw helpers
	public void addChallengerBox(String name, double x, double y, int boxtype, Challenger c)
	{
		TextField challengerScoreCell = null;
		Label challengerCell = new Label();
		challengerCell.setBackground(new Background(new BackgroundFill(challengerCellBGColor, CornerRadii.EMPTY, Insets.EMPTY)));
		challengerCell.setTextFill(challengerCellFGColor);
		challengerCell.setAlignment(Pos.CENTER);
		challengerCell.setText(name);
		if (boxtype == 1)
		{
			challengerCell.setMinSize(challengerCellWidth+challengerScoreCellWidth, challengerCellHeight);
			challengerCell.setMaxSize(challengerCellWidth+challengerScoreCellWidth, challengerCellHeight);
		}
		else
		{
			challengerCell.setMinSize(challengerCellWidth, challengerCellHeight);
			challengerCell.setMaxSize(challengerCellWidth, challengerCellHeight);
			challengerScoreCell = new TextField();
			challengerScoreCell.setMinSize(challengerScoreCellWidth, challengerScoreCellHeight);
			challengerScoreCell.setMaxSize(challengerScoreCellWidth, challengerScoreCellHeight);
			challengerScoreCell.setBackground(new Background(new BackgroundFill(challengerScoreCellBGColor, CornerRadii.EMPTY, Insets.EMPTY)));
			//challengerScoreCell.setTextFill(challengerScoreCellFGColor);
			challengerScoreCell.setAlignment(Pos.CENTER);
			challengerScoreCell.setPromptText("Score");
		}
		challengerCell.setLayoutX(x);
		challengerCell.setLayoutY(y);	
		this.root.getChildren().add(challengerCell);
		if (challengerScoreCell != null)
		{
			challengerScoreCell.setLayoutX(x+challengerCellWidth);
			challengerScoreCell.setLayoutY(y);
			this.root.getChildren().add(challengerScoreCell);
		}
		if (c != null) {
			c.startX = x;
			c.startY = y;
			c.challengerCell = challengerCell;
			c.challengerScoreCell = challengerScoreCell;
		} else
		{
			this.winnerCell = challengerCell;
		}
	}
	//UI draw helpers
	public void addGameScoreButton(int gameNum,double x, double y,Game g){
		Button button = new Button();
		button.setText("Submit");
		button.setMinSize(gameButtonWidth, gameButtonHeight);
		button.setMaxSize(gameButtonWidth, gameButtonHeight);

		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				//System.out.println("The username is: " + usernameInput.getText());
				//System.out.println("The password is: " + passwordInput.getText());
			}
		});
		button.setLayoutX(x);
		button.setLayoutY(y);	
		g.setGameScoreButton(button);
		this.root.getChildren().add(button);

	}
	
	public void addLine(double x, double y, int linetype, double length)
	{
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
			return;
		}else if(rounds.size() == 0) {
			addChallengerBox(winner.name, ((sceneWidth/2)-((challengerCellWidth + challengerScoreCellWidth)/2)), ((sceneHeight/2)+(challengerCellHeight/2)), 1, null);
		} else if (rounds.size()  == 1) {
			x = roundXGap;
			y = challengerBoxYGap;
			currentGame = rounds.get(0).games.get(0);
			addChallengerBox(currentGame.challenger1.name, x, y, 2, currentGame.challenger1);
			x = sceneWidth - roundXGap - (challengerCellWidth + challengerScoreCellWidth);
			y = challengerBoxYGap;
			addChallengerBox(currentGame.challenger2.name, x, y, 2, currentGame.challenger2);
			lineStartX = currentGame.challenger1.startX + (challengerCellWidth + challengerScoreCellWidth);
			lineStartY = currentGame.challenger1.startY + challengerCellHeight/2;
			length = currentGame.challenger2.startX - currentGame.challenger1.startX - challengerCellWidth-challengerScoreCellWidth;
			addLine(lineStartX,lineStartY, 1,length);
			addLine(lineStartX + length/2, lineStartY, 2, challengerCellHeight/2+challengerBoxYGap);
		}else {

			for(Round r:rounds) {		
				numGames =  r.games.size();
				if (r.roundNumber == 1) {
					x = roundXGap;
					y = challengerBoxYGap;
					for( i = 0; i<numGames/2; i++) {
						currentGame = r.games.get(i);
						addChallengerBox(currentGame.challenger1.name, x, y, 2, currentGame.challenger1);
						y = y + challengerCellHeight + challengerBoxYGap;
						addChallengerBox(currentGame.challenger2.name, x, y, 2, currentGame.challenger2);
						y = y + challengerCellHeight + challengerBoxYGap;
						currentGame.updateChildGameChallengerCoordinates(true, roundXGap);		
					}
					x = sceneWidth - roundXGap - (challengerCellWidth + challengerScoreCellWidth);
					y = challengerBoxYGap;				
					for(i = numGames/2; i<numGames; i++) {
						currentGame = r.games.get(i);
						addChallengerBox(currentGame.challenger1.name, x, y, 2, currentGame.challenger1);
						y = y + challengerCellHeight + challengerBoxYGap;
						addChallengerBox(currentGame.challenger2.name, x, y, 2, currentGame.challenger2);
						y = y + challengerCellHeight + challengerBoxYGap;
						currentGame.updateChildGameChallengerCoordinates(false, roundXGap);
					}
				} else {
					for(i = 0; i<numGames; i++) {
						if (i < numGames/2) {
							isLeft = true;
						} else {
							isLeft = false;
						}						
						currentGame = r.games.get(i);
						addChallengerBox(null, currentGame.challenger1.startX, currentGame.challenger1.startY, 2, currentGame.challenger1);
						currentGame.updateChildGameChallengerCoordinates(isLeft, roundXGap);
						addChallengerBox(null, currentGame.challenger2.startX, currentGame.challenger2.startY, 2, currentGame.challenger2);
						currentGame.updateChildGameChallengerCoordinates(isLeft, roundXGap);
					}
				}
			}

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
						addGameScoreButton(currentGame.gameNumber,buttonStartX,buttonStartY,currentGame);

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
					addGameScoreButton(currentGame.gameNumber,buttonStartX,buttonStartY,currentGame);
					addLine(buttonStartX+gameButtonWidth/2, buttonStartY+gameButtonHeight, 2, challengerCellHeight/2+30);
				}
			}
		}
		addChallengerBox("Winner", ((sceneWidth/2)-((challengerCellWidth + challengerScoreCellWidth)/2)), ((sceneHeight/2)+(challengerCellHeight/2))+30, 1, null);

	}

	@Override
	public void start(Stage primaryStage) {
		try {

			// 2.check if it is to power of 2
			//validateNameList();

			//3.Rearrange them in the order as per the seating
			//reArrangeAccordingToRank();

			// 4.convert these namelist into an array;list of challenger objects
			//createChallengerList();



			root = new Pane();


			//5. set the num of challengers from the challenger object
			this.numChallengers = this.nameList.size();
			// TODO: After William's work, these code should be deleted
			for (int i = 0; i < this.numChallengers; i++) {
				Challenger c = new Challenger(nameList.get(i));
				challengers.add(c);
			}
			
			// 6.call the game management, set the 1st round in the beginning
			//can call the method as gameManagement
			gameManagement();
		
			for (int i = 0; i < this.numChallengers; i++) {
				System.out.print(challengers.get(i).getCName()+", ");
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
