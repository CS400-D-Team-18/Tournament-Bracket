package application;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	ArrayList<String> nameList;
	ArrayList<Challenger> challangers;
	ArrayList<Round> rounds;	

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

	double challengerCellWidth = 100;
	double challengerCellHeight = 25;
	double challengerScoreCellWidth = 50;
	double challengerScoreCellHeight = 25;
	double gameButtonWidth = 60 ;
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
	Pane root;	

	//Computing layout
	private void computeChallengerColumnArray(){
		//TO DO: handle numChallengers = 0

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

	private void gameManagement16()
	{
		Challenger c1 = new Challenger("Challenger 1");
		Challenger c2 = new Challenger("Challenger 2");
		Challenger c3 = new Challenger("Challenger 3");
		Challenger c4 = new Challenger("Challenger 4");
		Challenger c5 = new Challenger("Challenger 5");
		Challenger c6 = new Challenger("Challenger 6");
		Challenger c7 = new Challenger("Challenger 7");
		Challenger c8 = new Challenger("Challenger 8");
		Challenger c9 = new Challenger("Challenger 9");
		Challenger c10 = new Challenger("Challenger 10");
		Challenger c11 = new Challenger("Challenger 11");
		Challenger c12 = new Challenger("Challenger 12");
		Challenger c13 = new Challenger("Challenger 13");
		Challenger c14 = new Challenger("Challenger 14");
		Challenger c15 = new Challenger("Challenger 15");
		Challenger c16 = new Challenger("Challenger 16");

		Game g1 = new Game(1);
		Game g2 = new Game(2);
		Game g3 = new Game(3);
		Game g4 = new Game(4);		
		Game g5 = new Game(5);
		Game g6 = new Game(6);		
		Game g7 = new Game(7);
		Game g8 = new Game(8);
		Game g9 = new Game(9);
		Game g10 = new Game(10);
		Game g11 = new Game(11);
		Game g12 = new Game(12);		
		Game g13 = new Game(13);
		Game g14 = new Game(14);		
		Game g15 = new Game(15);

		g1.updateGameInfo(c1, c2, g9, 1);
		g2.updateGameInfo(c3, c4, g9, 2);
		g3.updateGameInfo(c5, c6, g10, 1);
		g4.updateGameInfo(c7, c8, g10, 2);
		g5.updateGameInfo(c9, c10, g11, 1);
		g6.updateGameInfo(c11, c12, g11, 2);
		g7.updateGameInfo(c13, c14, g12, 1);
		g8.updateGameInfo(c15, c16, g12, 2);

		g9.updateGameInfo(null, null, g13, 1);
		g10.updateGameInfo(null, null, g13, 2);
		g11.updateGameInfo(null, null, g14, 1);
		g12.updateGameInfo(null, null, g14, 2);

		g13.updateGameInfo(null, null, g15, 1);
		g14.updateGameInfo(null, null, g15, 2);

		g15.updateGameInfo(null, null, null, 0);

		Round r1 = new Round(1,"1ST ROUND");
		Round r2 = new Round(2,"QUARTER-FINAL");
		Round r3 = new Round(3,"SEMI-FINAL");
		Round r4 = new Round(4,"FINAL");

		r1.addGame(g1);
		r1.addGame(g2);
		r1.addGame(g3);
		r1.addGame(g4);
		r1.addGame(g5);
		r1.addGame(g6);
		r1.addGame(g7);
		r1.addGame(g8);

		r2.addGame(g9);
		r2.addGame(g10);
		r2.addGame(g11);
		r2.addGame(g12);

		r3.addGame(g13);
		r3.addGame(g14);

		r4.addGame(g15);

		rounds = new ArrayList<Round>();

		rounds.add(r1);
		rounds.add(r2);
		rounds.add(r3);
		rounds.add(r4);
	}

	private void gameManagement8()
	{
		Challenger c1 = new Challenger("Challenger 1");
		Challenger c2 = new Challenger("Challenger 2");
		Challenger c3 = new Challenger("Challenger 3");
		Challenger c4 = new Challenger("Challenger 4");
		Challenger c5 = new Challenger("Challenger 5");
		Challenger c6 = new Challenger("Challenger 6");
		Challenger c7 = new Challenger("Challenger 7");
		Challenger c8 = new Challenger("Challenger 8");

		Game g1 = new Game(1);
		Game g2 = new Game(2);
		Game g3 = new Game(3);
		Game g4 = new Game(4);		
		Game g5 = new Game(5);
		Game g6 = new Game(6);		
		Game g7 = new Game(7);

		g1.updateGameInfo(c1, c2, g5, 1);
		g2.updateGameInfo(c3, c4, g5, 2);
		g3.updateGameInfo(c5, c6, g6, 1);
		g4.updateGameInfo(c7, c8, g6, 2);

		g5.updateGameInfo(null, null, g7, 1);
		g6.updateGameInfo(null, null, g7, 2);

		g7.updateGameInfo(null, null, null, 0);

		Round r1 = new Round(1,"QUARTER-FINAL");
		Round r2 = new Round(2,"SEMI-FINAL");
		Round r3 = new Round(3,"FINAL");

		r1.addGame(g1);
		r1.addGame(g2);
		r1.addGame(g3);
		r1.addGame(g4);

		r2.addGame(g5);
		r2.addGame(g6);

		r3.addGame(g7);

		rounds = new ArrayList<Round>();
		rounds.add(r1);
		rounds.add(r2);
		rounds.add(r3);
	}

	private void gameManagement4()
	{
		Challenger c1 = new Challenger("Challenger 1");
		Challenger c2 = new Challenger("Challenger 2");
		Challenger c3 = new Challenger("Challenger 3");
		Challenger c4 = new Challenger("Challenger 4");

		Game g1 = new Game(1);
		Game g2 = new Game(2);
		Game g3 = new Game(3);

		g1.updateGameInfo(c1, c2, g3, 1);
		g2.updateGameInfo(c3, c4, g3, 2);

		g3.updateGameInfo(null, null, null, 0);

		Round r1 = new Round(1,"SEMI-FINAL");
		Round r2 = new Round(2,"FINAL");

		r1.addGame(g1);
		r1.addGame(g2);
		r2.addGame(g3);

		rounds = new ArrayList<Round>();
		rounds.add(r1);
		rounds.add(r2);
	}

	private void gameManagement2()
	{
		Challenger c1 = new Challenger("Challenger 1");
		Challenger c2 = new Challenger("Challenger 2");

		Game g1 = new Game(1);

		g1.updateGameInfo(c1, c2, null, 0);

		Round r1 = new Round(1,"FINAL");

		r1.addGame(g1);

		rounds = new ArrayList<Round>();
		rounds.add(r1);
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
	public void addLine(int x, int y, int linetype, int length)
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

		int numGames = 0;
		if (rounds.size() < 1) {
			return;
		} else if (rounds.size()  == 1) {
			x = roundXGap;
			y = challengerBoxYGap;
			currentGame = rounds.get(0).games.get(0);
			addChallengerBox("Challenger "+currentGame.gameNumber, x, y, 2, currentGame.challenger1);
			x = sceneWidth - roundXGap - (challengerCellWidth + challengerScoreCellWidth);
			y = challengerBoxYGap;
			addChallengerBox("Challenger "+currentGame.gameNumber, x, y, 2, currentGame.challenger1);
		} else {

			for(Round r:rounds) {		
				numGames =  r.games.size();
				if (r.roundNumber == 1) {
					x = roundXGap;
					y = challengerBoxYGap;
					for( i = 0; i<numGames/2; i++) {
						currentGame = r.games.get(i);
						addChallengerBox("Challenger "+currentGame.gameNumber, x, y, 2, currentGame.challenger1);
						//System.out.println("Challenger "+currentGame.gameNumber + " X = " + x + " Y = " + y);
						y = y + challengerCellHeight + challengerBoxYGap;
						addChallengerBox("Challenger "+currentGame.gameNumber, x, y, 2, currentGame.challenger2);
						//System.out.println("Challenger "+currentGame.gameNumber + " X = " + x + " Y = " + y);
						y = y + challengerCellHeight + challengerBoxYGap;
						currentGame.updateChildGameChallengerCoordinates(true, roundXGap);
						
						
					}
					x = sceneWidth - roundXGap - (challengerCellWidth + challengerScoreCellWidth);
					y = challengerBoxYGap;				
					for(i = numGames/2; i<numGames; i++) {
						currentGame = r.games.get(i);
						addChallengerBox("Challenger "+currentGame.gameNumber, x, y, 2, currentGame.challenger1);
						//System.out.println("Challenger "+currentGame.gameNumber + " X = " + x + " Y = " + y);
						y = y + challengerCellHeight + challengerBoxYGap;
						addChallengerBox("Challenger "+currentGame.gameNumber, x, y, 2, currentGame.challenger2);
						//System.out.println("Challenger "+currentGame.gameNumber + " X = " + x + " Y = " + y);
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
						addChallengerBox("Challenger "+currentGame.gameNumber, currentGame.challenger1.startX, currentGame.challenger1.startY, 2, currentGame.challenger1);
						//System.out.println("Challenger "+currentGame.gameNumber + " X = " + currentGame.challenger1.startX + " Y = " + currentGame.challenger1.startY);
						currentGame.updateChildGameChallengerCoordinates(isLeft, roundXGap);
						addChallengerBox("Challenger "+currentGame.gameNumber, currentGame.challenger2.startX, currentGame.challenger2.startY, 2, currentGame.challenger2);
						//System.out.println("Challenger "+currentGame.gameNumber + " X = " + currentGame.challenger2.startX + " Y = " + currentGame.challenger2.startY);
						currentGame.updateChildGameChallengerCoordinates(isLeft, roundXGap);
					}
				}
			}
			for(Round r:rounds) {		
				numGames =  r.games.size();
				if (numGames > 1 ) {
					for( i = 0; i<numGames;i++) {
						currentGame = r.games.get(i);
						buttonStartX = currentGame.challenger1.startX + ((challengerCellWidth + challengerScoreCellWidth)/2) - gameButtonWidth/2;
						buttonStartY = currentGame.challenger1.startY +(currentGame.challenger2.startY  - currentGame.challenger1.startY )/2 +challengerCellHeight	- gameButtonHeight;
						addGameScoreButton(currentGame.gameNumber,buttonStartX,buttonStartY,currentGame);
					} 
				}else if(numGames == 1 ) {
					currentGame = r.games.get(0);
					buttonStartX = ((sceneWidth/2) - gameButtonWidth/2);
					buttonStartY =  sceneHeight/2 +  30;
					addGameScoreButton(currentGame.gameNumber,buttonStartX,buttonStartY,currentGame);
				}
			}
		}
		addChallengerBox("Challenger W", ((sceneWidth/2)-((challengerCellWidth + challengerScoreCellWidth)/2)), ((sceneHeight/2)-(challengerCellHeight/2)), 1, null);
	
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
			this.numChallengers = 16;
			
			
			//6.call the game managemnet
			//can call the method as gameManagement
			
			this.gameManagement16();
			
			//this.numChallengers = 8;
			//this.gameManagement8();
			
			//this.numChallengers = 4;
			//this.gameManagement4();
			
			//this.numChallengers = 2;
			//this.gameManagement2();

			this.computeChallengerColumnArray();
			this.computeSceneWidth();
			this.drawBracket();

			Scene scene = new Scene(root,sceneWidth,sceneHeight+sceneAdditionalHeight);
			root.setBackground(new Background(new BackgroundFill(sceneBGColor, CornerRadii.EMPTY, Insets.EMPTY)));
			primaryStage.setTitle("Tournament Bracket");
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// 1. Code for reading file from the argument passed and store it in an arraylist of string- global --> nameList
		
		
		launch(args);
	}
}
