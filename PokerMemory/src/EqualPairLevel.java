import javax.swing.JFrame;

public class EqualPairLevel extends EasyLevel {

	private int successfulTurn = 0; // This variable is used as a counter. Once it reaches 8, it means that the game is over (8 pairs).

	protected EqualPairLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(validTurnTime, mainFrame);
		super.turnsTakenCounter.setDifficultyModeLabel("Medium Level");
	}

	@Override
	protected boolean addToTurnedCardsBuffer(Card card) {
		this.turnedCardsBuffer.add(card);
		SoundClass.Flip();//Play the flip sound...
		if(this.turnedCardsBuffer.size() == getCardsToTurnUp())
		{
			// there are two cards faced up
			// record the player's turn

			this.turnsTakenCounter.increment();
			// get the other card (which was already turned up)
			Card otherCard = (Card) this.turnedCardsBuffer.get(0);
			card.showLastCard();
			// the cards match, so remove them from the list (they will remain face up)
			if( otherCard.getNum() == card.getNum()){
				MemoryGame.score.updateScoreByAmount(50); //Add 50 points to the score
				successfulTurn++; // if an equal pair found, add 1 to counter.
				if(successfulTurn == 8){
					card.showLastCard(); // displays the last card picked by the user (avoids the delay).
					MemoryGame.score.gameOver(); // if counter equals 8, the game is over.
				}
				this.turnedCardsBuffer.clear();
			}
			// the cards do not match, so start the timer to turn them down
			else{
				this.turnDownTimer.start();
				MemoryGame.score.updateScoreByAmount(-5); //remove minus 5 points to the score
			}
		}
		return true;
	}

	@Override
	protected boolean turnUp(Card card) {
		// the card may be turned
		if(this.turnedCardsBuffer.size() < getCardsToTurnUp()) 
		{
			return this.addToTurnedCardsBuffer(card);
		}
		// there are already the number of EasyMode (two face up cards) in the turnedCardsBuffer
		return false;
	}
	
	@Override
	protected String getMode() {
		// TODO Auto-generated method stub
		return "MediumMode";
	}



}
