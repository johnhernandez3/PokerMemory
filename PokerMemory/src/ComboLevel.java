import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class ComboLevel extends EqualPairLevel {
	
	private int successfulTurn = 0;

	// COMBO LEVEL: The goal is to find five cards with same suit, five cards in
	// ladder sequence or a Full House.

	ArrayList<Integer> scores = new ArrayList<Integer>(); //Stores the scores obtained on each turn.

	protected ComboLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(validTurnTime, mainFrame);
		super.turnsTakenCounter.setDifficultyModeLabel("Combo Level");
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
		if (this.turnedCardsBuffer.size() == getCardsToTurnUp()) {
			// We are uncovering the last card in this turn
			// Record the player's turn
			this.turnsTakenCounter.increment();
			// get the other card (which was already turned up)
			Card otherCard1 = (Card) this.turnedCardsBuffer.get(0);
			Card otherCard2 = (Card) this.turnedCardsBuffer.get(1);
			Card otherCard3 = (Card) this.turnedCardsBuffer.get(2);
			Card otherCard4 = (Card) this.turnedCardsBuffer.get(3);

			card.showLastCard();
			
			int[] cardsSelected = { card.getRankInt(), otherCard1.getRankInt(),
					otherCard2.getRankInt(), otherCard3.getRankInt(),
					otherCard4.getRankInt() };//Cards choosen by the user are stored (only their ranks) on an int array.

			Arrays.sort(cardsSelected); //Used an insertion sort provided by the Java API.

			boolean isNone = true; // To determine whether there the user obtained a winning hand

			if ((cardsSelected[0] + 1 == cardsSelected[1]
					&& cardsSelected[1] + 1 == cardsSelected[2]
					&& cardsSelected[2] + 1 == cardsSelected[3] && cardsSelected[3] + 1 == cardsSelected[4])
					&& !(card.getSuit().equals(otherCard1.getSuit())
							&& otherCard1.getSuit()
									.equals(otherCard2.getSuit())
							&& otherCard2.getSuit()
									.equals(otherCard3.getSuit()) && otherCard3
							.getSuit().equals(otherCard4.getSuit()))) {
				// The five cards are in sequence with at least two different
				// suits
				isNone = false; //There is a winning hand.
				MemoryGame.score.addToButtons("STRAIGHT: "
						+ (1000 + 100 * cardsSelected[4]) + " points"); //Add a string STRAIGHT for the button
				scores.add(1000 + 100 * cardsSelected[4]); //Add 1000 points plus 100 times the highest rank
			}
			if ((card.getSuit().equals(otherCard1.getSuit()))
					&& (card.getSuit().equals(otherCard2.getSuit()))
					&& (card.getSuit().equals(otherCard3.getSuit()))
					&& (card.getSuit().equals(otherCard4.getSuit()))) {
				// Five cards with same suit, so remove them from the list (they
				// will remain face up)
				isNone = false; //There is a winning hand.
				MemoryGame.score.addToButtons("FLUSH: "
						+ (700 + card.getRankInt() + otherCard1.getRankInt()
								+ otherCard2.getRankInt()
								+ otherCard3.getRankInt() + otherCard4
									.getRankInt()) + " points"); //Add a string FLUSH for the button
				scores.add(700 + card.getRankInt() + otherCard1.getRankInt()
						+ otherCard2.getRankInt() + otherCard3.getRankInt()
						+ otherCard4.getRankInt()); //Add 700 points plus the sum of each rank
			}
			if (((cardsSelected[0] == cardsSelected[1]) && (cardsSelected[2] == cardsSelected[3] && cardsSelected[3] == cardsSelected[4]))
					|| ((cardsSelected[3] == cardsSelected[4]) && (cardsSelected[0] == cardsSelected[1] && cardsSelected[1] == cardsSelected[2]))) {
				// A pair and a trio -> a FULL HOUSE! 
				isNone = false; //There is a winning hand. 
				MemoryGame.score
						.addToButtons("FULL HOUSE: "
								+ (1000 + card.getRankInt()
										* otherCard1.getRankInt() + otherCard2
										.getRankInt()
										* otherCard3.getRankInt()
										* otherCard4.getRankInt()) + " points"); //Add a string FULL HOUSE for the button
				scores.add(1000 + card.getRankInt() * otherCard1.getRankInt()*otherCard2.getRankInt() * otherCard3.getRankInt()
						* otherCard4.getRankInt()); //Add 1000 points plus the product of all ranks
			}
			if ((cardsSelected[0] == cardsSelected[1])
					&& (cardsSelected[1] == cardsSelected[2])
					|| (cardsSelected[1] == cardsSelected[2])
					&& (cardsSelected[2] == cardsSelected[3]) || (cardsSelected[2] == cardsSelected[3])
					&& (cardsSelected[3] == cardsSelected[4])) {
				//Three cards match -> a TRIO!
				isNone = false; //There is a winning hand.
				MemoryGame.score.addToButtons("TRIO: " + 100 + " points"); //Add a string TRIO for the button
				scores.add(100); //Add 100 points 
			}
			if ((cardsSelected[0] == cardsSelected[1])
					|| (cardsSelected[1] == cardsSelected[2])
					|| (cardsSelected[2] == cardsSelected[3])
					|| (cardsSelected[3] == cardsSelected[4])) {
				//Two cards match -> a PAIR!
				isNone = false; //There is a winning hand.
				MemoryGame.score.addToButtons("PAIR: " + 50 + " points");  //Add a string PAIR for the button
				scores.add(50); //Add 50 points 
			}

			if (isNone) {
				MemoryGame.score.updateScoreByAmount(-5); //If the user didn't obtain a winning hand, remove 5 points
				this.turnDownTimer.start();
			} else {
				
				int oldScore = MemoryGame.score.score; //Sets oldScore equal to actual score

				MemoryGame.score.addToButtons("PASS");
				scores.add(0);

				MemoryGame.score.updateScoreByAmount(scores
						.get(MemoryGame.score.buttonFrame()));

				if (oldScore == MemoryGame.score.score) {
					// The score remains the same because the user choose to
					// PASS
					this.turnDownTimer.start();
				} else {
					successfulTurn++;
					if(successfulTurn == 15){
						card.showLastCard();
						MemoryGame.score.gameOver();
					}
					this.turnedCardsBuffer.clear();
				}

				scores.clear();
				MemoryGame.score.buttons.clear();
				isNone = true;
				this.turnDownTimer.start();
			}
		}
		return true;
	}

	public void pass() {

	}
}
