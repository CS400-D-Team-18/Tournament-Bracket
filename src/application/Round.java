package application;
import java.util.ArrayList;

public class Round {	
	
	int roundNumber;
	String roundName;
	ArrayList<Game> games;
	
	public Round(int roundNumber, String roundName) {
		this.roundNumber = roundNumber;
		this.roundName = roundName;
		this.games = new ArrayList<Game>();
	}

	public void addGame(Game g)
	{
		games.add(g);
	}
	
}
