import java.util.ArrayList;
import java.util.Arrays;

public class Hand {
	private boolean isRoyal = false;
	private boolean isStraight = false;
	public static ArrayList cardCollectionList;
	public static Card[] cardCollection;

	public Hand() {
		/*if (c1.getRankInt() == c2.getRankInt()
				&& c1.getRankInt() == c3.getRankInt()
				&& c1.getRankInt() == c4.getRankInt()
				&& c1.getRankInt() == c5.getRankInt()) {
			if (c1.getSuit().equals(c2.getSuit())
					&& c1.getSuit().equals(c3.getSuit())
					&& c1.getSuit().equals(c4.getSuit())
					&& c1.getSuit().equals(c5.getSuit())) {
				this.isRoyal = true;
			} else {
				this.isStraight = true;
			}
		}*/
	}

	public boolean getRoyal() {
		return this.isRoyal;
	}

	public boolean getStraight() {
		return this.isStraight;
	}

	public void addCollection(ArrayList grid) {
		cardCollectionList = grid;
		cardCollection = (Card[]) cardCollectionList.toArray();
		Arrays.sort(cardCollection);
	}

	public void removeFromCollection(int index) {
		cardCollectionList.remove(index);
		cardCollection = (Card[]) cardCollectionList.toArray();
		Arrays.sort(cardCollection);
		//compareAllCardsStraight(cardCollection);
	}
	
	public static boolean isGameOver(Card[] c, Card c1, Card c2, Card c3, Card c4, Card c5){
		boolean over = false;
		for (int i = 0; i< c.length; i++){
			if ((c[i].getRankInt() ==  c1.getRankInt() || c[i].getRankInt() ==  c2.getRankInt() || c[i].getRankInt() ==  c3.getRankInt() || c[i].getRankInt() ==  c4.getRankInt() || c[i].getRankInt() ==  c5.getRankInt()) && !(c1.getSuit().equals(c2.getSuit())
					&& c1.getSuit().equals(c3.getSuit())
					&& c1.getSuit().equals(c4.getSuit())
					&& c1.getSuit().equals(c5.getSuit()))){
				over = false;
			}
			else{
				over = true;
			}
		}
		return over;
	}

	public static boolean compareAllCardsStraight(Card[] c) {
		for (int i1 = 0; i1 < c.length; i1++) {
			for (int i2 = 0; i2 < c.length; i2++) {
				for (int i3 = 0; i3 < c.length; i3++) {
					for (int i4 = 0; i4 < c.length; i4++) {
						for (int i5 = 0; i5 < c.length; i5++) {

							int[] orderArray = new int[] { c[i1].getRankInt(),
									c[i2].getRankInt(), c[i3].getRankInt(),
									c[i4].getRankInt(), c[i5].getRankInt() };
							Arrays.sort(orderArray);

							if (((orderArray[0] + 1 == orderArray[1])
									&& (orderArray[1] + 1 == orderArray[2])
									&& (orderArray[2] + 1 == orderArray[3]) && (orderArray[3] + 1 == orderArray[4]))
									&& !((c[i1].getSuit().equals(c[i2]
											.getSuit()))
											&& (c[i2].getSuit().equals(c[i3]
													.getSuit()))
											&& (c[i3].getSuit().equals(c[i4]
													.getSuit())) && (c[i4]
												.getSuit().equals(c[i5]
											.getSuit())))) {
								return true;
							}

						}
					}

				}
			}
		}
		return false;
	}

}