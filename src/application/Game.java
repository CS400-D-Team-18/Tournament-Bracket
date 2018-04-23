package application;

public class Game {
	
	int gameNumber;
	Challenger challenger1;
	Challenger challenger2;
	Game childGame;
	int childGameChallengerNumber;
	
	public Game(int gameNumber) {
		this.gameNumber = gameNumber;
	}

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
}
