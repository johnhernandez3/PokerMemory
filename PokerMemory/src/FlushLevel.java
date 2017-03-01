import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class FlushLevel extends EqualPairLevel {
	
	private int successfulTurn = 0; //This variable is used as a counter. Once it reaches 8, it means that the game is over (8 Winning Hands). 

	// FLUSH LEVEL: The goal is to find, on each turn, five cards with the same suit

	protected FlushLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(validTurnTime, mainFrame);
		super.turnsTakenCounter.setDifficultyModeLabel("Flush Level");
		cardsToTurnUp = 5;
		cardsPerRow = 10;
		rowsPerGrid = 5;
	}

	@Override
	protected void makeDeck() {
		// In Trio level the grid consists of distinct cards, no repetitions
		ImageIcon cardIcon[] = this.loadCardIcons();

		//back card
		ImageIcon backIcon = cardIcon[TotalCardsPerDeck];

		int cardsToAdd[] = new int[getRowsPerGrid() * getCardsPerRow()];
		for(int i = 0; i < (getRowsPerGrid() * getCardsPerRow()); i++)
		{
			cardsToAdd[i] = i;
		}

		// randomize the order of the deck
		this.randomizeIntArray(cardsToAdd);

		// make each card object
		for(int i = 0; i < cardsToAdd.length; i++)
		{
			// number of the card, randomized
			int num = cardsToAdd[i];
			// make the card object and add it to the panel
			String rank = cardNames[num].substring(0, 1);
			String suit = cardNames[num].substring(1, 2);
			this.grid.add( new Card(this, cardIcon[num], backIcon, num, rank, suit));
		}
	}

	@Override
	protected boolean addToTurnedCardsBuffer(Card card) {
		// add the card to the list
		this.turnedCardsBuffer.add(card);
		SoundClass.Flip();//Play the flip sound...
		if(this.turnedCardsBuffer.size() == getCardsToTurnUp())
		{
			// We are uncovering the last card in this turn
			// Record the player's turn
			this.turnsTakenCounter.increment();
			// get the other card (which was already turned up)
			Card otherCard1 = (Card) this.turnedCardsBuffer.get(0);
			Card otherCard2 = (Card) this.turnedCardsBuffer.get(1);
			Card otherCard3 = (Card) this.turnedCardsBuffer.get(2);
			Card otherCard4 = (Card) this.turnedCardsBuffer.get(3);
			if((card.getSuit().equals(otherCard1.getSuit())) && (card.getSuit().equals(otherCard2.getSuit())) && (card.getSuit().equals(otherCard3.getSuit())) && (card.getSuit().equals(otherCard4.getSuit()))) {
				// Five cards match, so remove them from the list (they will remain face up)
				MemoryGame.score.updateScoreByAmount(700 + card.getRankInt() + otherCard1.getRankInt() + otherCard2.getRankInt() + otherCard3.getRankInt() + otherCard4.getRankInt()); //add 700 points to score plus the sum of the ranks of each card
				successfulTurn++;
				if(successfulTurn == 8){
					card.showLastCard();
					MemoryGame.score.gameOver(); //After the 8 winning hands, the game is over.
				}
				this.turnedCardsBuffer.clear();
			}
			else 
			{
				// The cards do not match, so start the timer to turn them down (Unsuccessful turn)
				MemoryGame.score.updateScoreByAmount(-5); //remove -5 points to score
				this.turnDownTimer.start();
			}
		}
		return true;
	}
}
