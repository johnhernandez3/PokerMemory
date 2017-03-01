
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class StraightLevel extends EqualPairLevel {
	
	private int successfulTurn = 0; 
	
	// STRAIGHT LEVEL: The goal is to find five cards in ladder sequence, along with at least 2 different suits.
	
	protected StraightLevel(TurnsTakenCounterLabel validTurnTime,
			JFrame mainFrame) {
		super(validTurnTime, mainFrame);
		super.turnsTakenCounter.setDifficultyModeLabel("Straight Level");
		cardsToTurnUp = 5;
		cardsPerRow = 10;
		rowsPerGrid = 5;
	}

	@Override
	protected void makeDeck() {
		
		ImageIcon cardIcon[] = this.loadCardIcons();

		// back card
		ImageIcon backIcon = cardIcon[TotalCardsPerDeck];

		int cardsToAdd[] = new int[getRowsPerGrid() * getCardsPerRow()];
		for (int i = 0; i < (getRowsPerGrid() * getCardsPerRow()); i++) {
			cardsToAdd[i] = i;
		}

		// randomize the order of the deck
		this.randomizeIntArray(cardsToAdd);

		// make each card object
		for (int i = 0; i < cardsToAdd.length; i++) {
			// number of the card, randomized
			int num = cardsToAdd[i];
			// make the card object and add it to the panel
			String rank = cardNames[num].substring(0, 1);
			String suit = cardNames[num].substring(1, 2);
			this.grid.add(new Card(this, cardIcon[num], backIcon, num, rank,
					suit));
		}
	}

	@Override
	protected boolean addToTurnedCardsBuffer(Card card) {
		// add the card to the list
		this.turnedCardsBuffer.add(card);
		SoundClass.Flip();//Play the flip sound...
		if (this.turnedCardsBuffer.size() == getCardsToTurnUp()){
			// We are uncovering the last card in this turn
			// Record the player's turn
			this.turnsTakenCounter.increment();
			// get the other card (which was already turned up)
			Card otherCard1 = (Card) this.turnedCardsBuffer.get(0);
			Card otherCard2 = (Card) this.turnedCardsBuffer.get(1);
			Card otherCard3 = (Card) this.turnedCardsBuffer.get(2);
			Card otherCard4 = (Card) this.turnedCardsBuffer.get(3);
			
			int[] cardsSelected = { card.getRankInt(), otherCard1.getRankInt(),
					otherCard2.getRankInt(), otherCard3.getRankInt(),
					otherCard4.getRankInt() }; //Cards choosen by the user are stored (only their ranks) on an int array.

			Arrays.sort(cardsSelected); //Used an insertion sort provided by the Java API.

			if ((cardsSelected[0] + 1 == cardsSelected[1]
					&& cardsSelected[1] + 1 == cardsSelected[2]
					&& cardsSelected[2] + 1 == cardsSelected[3] && cardsSelected[3] + 1 == cardsSelected[4])
					&& !(card.getSuit().equals(otherCard1.getSuit())
							&& otherCard1.getSuit()
									.equals(otherCard2.getSuit())
							&& otherCard2.getSuit()
									.equals(otherCard3.getSuit()) && otherCard3
							.getSuit().equals(otherCard4.getSuit()))) {
				// The five cards are in sequence with at least two different suits
				MemoryGame.score.updateScoreByAmount(1000 + 100 * cardsSelected[4]); //add 1000 points plus 100 times the card with highest value
				successfulTurn++;
				if(successfulTurn == 8){
					card.showLastCard();
					MemoryGame.score.gameOver();
				}		
				this.turnedCardsBuffer.clear();
				
			} else {
				// The cards are not in sequence
				MemoryGame.score.updateScoreByAmount(-5); // remove -5 points to score
				this.turnDownTimer.start();
			}
		}
		return true;
	}
}
