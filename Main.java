package application;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;


public class Main extends Application {
	static ArrayList<String>  nameList= new ArrayList<String>();
	ArrayList<Challenger> challengers = null;
	int nuberOfChallengers = 0;
	int numChallengers;
	int numRounds;
	int numGames;
	
	Color paneBGColor = Color.GRAY;
	Color challengerCellBGColor = Color.CADETBLUE;
	Color challengerCellFontColor = Color.WHITE;
	Color challengerScoreCellBGColor = Color.WHITE;
	Color challengerScoreCellFontColor = Color.PURPLE;
	Color lineColor = Color.WHITE;
	
	int challengerCellWidth = 100;
	int challengerCellHeight = 30;
	int challengerScoreCellWidth = 50;
	int centreOfPaneX = 750;
	int centreOfPaneY = 300;
	int offSetBetweenCells = 25;
	ArrayList<Label> challengerLabels = new ArrayList<Label>();
	ArrayList<TextField> challengerScoreFields = new ArrayList<TextField>();
	
	public void addChallengerBox(Pane root, int roundNum, int gameNum, String name, int score, int x, int y, int boxtype)
	{
		TextField challengerScoreField = null;
		Label challengerCell = new Label();
		challengerCell.setBackground(new Background(new BackgroundFill(challengerCellBGColor, CornerRadii.EMPTY, Insets.EMPTY)));
		challengerCell.setTextFill(challengerCellFontColor);
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
			challengerScoreField = new TextField();
			challengerScoreField.setMinSize(challengerScoreCellWidth, challengerCellHeight);
			challengerScoreField.setMaxSize(challengerScoreCellWidth, challengerCellHeight);
			challengerScoreField.setBackground(new Background(new BackgroundFill(challengerScoreCellBGColor, CornerRadii.EMPTY,Insets.EMPTY)));
			//challengerScoreField.setTextFill(challengerScoreCellFontColor);
			challengerScoreField.setAlignment(Pos.CENTER);
			challengerScoreField.setText(""+score);
		}
		challengerCell.setLayoutX(x);
		challengerCell.setLayoutY(y);		
		challengerLabels.add(challengerCell);
		root.getChildren().add(challengerCell);
		if (challengerScoreField != null)
		{
			challengerScoreField.setLayoutX(x+challengerCellWidth);
			challengerScoreField.setLayoutY(y);
			root.getChildren().add(challengerScoreField);
			challengerScoreFields.add(challengerScoreField);
		}
	}
	
	public void addLine(Pane root, double x, double y, int linetype, double length)
	{
		Line ln = new Line();
		ln.setStrokeWidth(2);
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
		root.getChildren().add(ln);
	}
	
	private void showError(Pane root,String errorMessage) {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error ");
		//alert.setHeaderText("Look, an Error Dialog");
		alert.setContentText(errorMessage);
		alert.showAndWait();
		
	}
	private void createLayoutForEachRound(Pane root, int roundNumber,ArrayList<Challenger> challengers,int xPos, int yPos,int offSetX) {
		int offsetY=0;
		if( roundNumber == 1) {
			addChallengerBox(root, 0, 0,"Gamer1" , 10, xPos, yPos, 2);	
			addChallengerBox(root, 0, 0,"Gamer2" , 10, xPos + challengerCellWidth+challengerScoreCellWidth+offSetX , yPos, 2);
			addLine(root,xPos +challengerCellWidth+challengerScoreCellWidth  ,yPos+challengerCellHeight/2,1,   offSetX);
		}else if(roundNumber == 2) {
			offsetY =challengerCellHeight;
			leftBoxAddition(root,1,xPos,yPos,offSetX,challengerCellHeight*3);
			rightBoxAddition(root,1,xPos,yPos,offSetX+offSetBetweenCells,challengerCellHeight*3);
			
			
		}else if(roundNumber == 3) {
			offsetY =challengerCellHeight;
			leftBoxAddition(root,2,xPos,yPos,offSetX,challengerCellHeight);
			rightBoxAddition(root,2,xPos,yPos,offSetX+offSetBetweenCells*2,challengerCellHeight);
		}else if(roundNumber == 4) {
			leftBoxAddition(root,4,xPos,yPos,offSetX,offsetY);
			rightBoxAddition(root,4,xPos,yPos,offSetX+offSetBetweenCells*3,offsetY);
		}
		
	}
	private void leftBoxAddition(Pane root,int numOfBoxes,int xPos,int yPos,int offSetX,int offSetY) {
		int shiftY =10;
		for(int i=1;i<=numOfBoxes;i++) {
			addChallengerBox(root, 0, 0,"GamerLU"+i , 10,xPos,yPos+shiftY+offSetY ,2);	
			shiftY = shiftY +challengerCellHeight +offSetBetweenCells + (2)*offSetY;
		}
		shiftY =10;
		for(int i=1;i<=numOfBoxes;i++) {
			addChallengerBox(root, 0, 0,"GamerL"+i , 10, xPos, yPos-shiftY-challengerCellHeight-offSetY, 2);
			shiftY = shiftY + challengerCellHeight+offSetBetweenCells + (2)*offSetY;	
		}
		
	}
	private void rightBoxAddition(Pane root,int numOfBoxes,int xPos,int yPos,int offSetX,int offSetY) {
		int shiftY =10;
		for(int i=1;i<=numOfBoxes;i++) {
			addChallengerBox(root, 0, 0,"GamerR"+i , 10,centreOfPaneX + offSetX +offSetBetweenCells ,yPos+shiftY+offSetY ,2);	
			shiftY = shiftY +challengerCellHeight +offSetBetweenCells + (2)*offSetY;
		}
		shiftY =10;
		for(int i=1;i<=numOfBoxes;i++) {
			addChallengerBox(root, 0, 0,"GamerR"+i , 10, centreOfPaneX + offSetX +offSetBetweenCells,yPos-shiftY-challengerCellHeight-offSetY, 2);
			shiftY = shiftY + challengerCellHeight+offSetBetweenCells + (2)*offSetY;
		}

	}
	private void addLinesForLayout(Pane root) {
		int noGames = challengerLabels.size();
		System.out.println(noGames);
		Label challengerLabel1;
		Label challengerLabel2;
		double length = 0.0;
		Integer[] indexes = {6,10,14,16,22,24};
		Integer[] rightIndexes = {4,5,10,11,12,13,22,23,24,25,26,27,28,29};
		ArrayList<Integer> indexesList = new ArrayList<>();
		for(int i:indexes) { indexesList.add( i);}
		ArrayList<Integer> rightIndexList = new ArrayList<>();
		for(int i:indexes) { rightIndexList.add( i);}
		
		if(noGames >= 2) {
			for(int j = 2; j< noGames-1;j=j+2 ) {
				challengerLabel1 = 	challengerLabels.get(j);
				challengerLabel2 = 	challengerLabels.get(j+1);

				if(!indexesList.contains(j)) {
					length = challengerLabel1.getLayoutY()- challengerLabel2.getLayoutY()-challengerCellHeight;
					addLine(root, challengerLabel2.getLayoutX()+ (challengerCellWidth+challengerScoreCellWidth)/2,challengerLabel2.getLayoutY()+challengerCellHeight, 2,length);
					if(!rightIndexList.contains(j)) {
						addLine(root, challengerLabel2.getLayoutX()+ (challengerCellWidth+challengerScoreCellWidth)/2,challengerLabel2.getLayoutY()+challengerCellHeight+length/2, 1,(challengerCellWidth+challengerScoreCellWidth)/2+offSetBetweenCells);			
					}else {
						addLine(root, challengerLabel2.getLayoutX()+ (challengerCellWidth+challengerScoreCellWidth),challengerLabel2.getLayoutY()+challengerCellHeight+length/2, 1,(challengerCellWidth+challengerScoreCellWidth)/2+offSetBetweenCells);			
						
					}
					
				}else {
					length = challengerLabel1.getLayoutY()- challengerLabel2.getLayoutY()+challengerCellHeight;
					addLine(root, challengerLabel2.getLayoutX()+ (challengerCellWidth+challengerScoreCellWidth)/2,challengerLabel2.getLayoutY(), 2,length);	
					addLine(root, challengerLabel2.getLayoutX()+ (challengerCellWidth+challengerScoreCellWidth)/2,challengerLabel2.getLayoutY()+length/2, 1,(challengerCellWidth+challengerScoreCellWidth)/2+offSetBetweenCells);
				}
				
			}	
		}
	}
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Pane root = new Pane();
			Scene scene = new Scene(root,1500,800);
			
			root.setBackground(new Background(new BackgroundFill(paneBGColor, CornerRadii.EMPTY, Insets.EMPTY)));
			
			nuberOfChallengers =16;
			numRounds = 4;
			if(nuberOfChallengers <=0) {
				showError(root,"No game and therefore no  winner as number of challengers is zero or less than zero.");	
			}else if(nuberOfChallengers == 1) {
				numRounds = 0;
				addChallengerBox(root, 0, 0,"Winner1" , 10, centreOfPaneX-(challengerCellWidth+challengerScoreCellWidth)/2, centreOfPaneY-challengerCellHeight/2, 1);	
			}else if (nuberOfChallengers >= 2 ) {
				int offSetX= offSetBetweenCells;
				int xPos=centreOfPaneX- (challengerCellWidth+challengerScoreCellWidth) + offSetX;
				int yPos=centreOfPaneY- challengerCellHeight/2;
				
				for(int i = 1;i <= numRounds ; i++) {
				createLayoutForEachRound(root,i,challengers,xPos,yPos,offSetX+5);	
				xPos = xPos - (challengerCellWidth+challengerScoreCellWidth+offSetBetweenCells);
				offSetX = challengerCellWidth+challengerScoreCellWidth+offSetX;
				} 
				addLinesForLayout(root);
			}
			primaryStage.setTitle("Tournament Bracket");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String fileName = args[0];
		File inputFile = null;
		Scanner sc = null;
		
		
		try {
			inputFile = new File(fileName);
			sc = new Scanner(inputFile);
			while(sc.hasNextLine()) {
				String name = sc.nextLine();
				nameList.add(name);
			}
			sc.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
		launch(args);
	}
	
	private void  validateChallengers(ArrayList<String> nameList) {
		
	}
	private void  calcNumberOfRounds(ArrayList<String> nameList) {
		
	}
}
