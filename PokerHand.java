// Filename : PokerHand.java
import java.util.*;
import java.util.stream.IntStream;

/**
 * The PokerHand class takes a String as a constructor in the form PlayerName: Card1, Card2, Card3, 
 * Card 4, Card 5 and determines the poker hand the player has. The checkHand method runs all the 
 * boolean 'is' methods to determine the current poker hand and sets the enum member hand to an enum 
 * Hand value and sets the high card and rank unless the hand is a Pair or Two Pair then they are 
 * determined in the calling class. 
 *  
 * @author Doug Morgan
 * @version 1.0
 *
 * @since 2018-5-13
 */

public class PokerHand {
	public static final int HAND_SIZE = 5;
	public static enum Hand {
		STRAIGHT_FLUSH,
		FOUR_KIND,
		FULL_HOUSE,
		FLUSH,
		STRAIGHT,
		THREE_KIND,
		TWO_PAIR,
		PAIR,
		HIGH_CARD
	}

	/**
	 * PokerHand constructor takes a String as a constructor in the form PlayerName: Card1, Card2, 
	 * Card3, Card4, Card5. It also sets the player name, fills a String array and int array for 
	 * the name and values of each card, counts alike cards in the hand, and calls checkHand to 
	 * determine the hand.
	 *
	 * @param input String constructor for 2 poker hands
	 * Example - Black: 2H 3H 4H 5C 9S White: 3C 8D 2D AC KS
	 */
	public PokerHand(String input) {
		String[] inputArr = input.split(" ");	
		//set 5 card hand to string array
		this.cardsStr = Arrays.copyOfRange(inputArr, 1, 6); 
		this.cardValues = new int[5];
		this.cardCount = new int[5];
		this.sortedValues = new int[5];
		//set playerName to first word in Input arrary(minus last colon char)
		this.playerName = inputArr[0].substring(0,inputArr[0].length()-1);

		//Parse card values into cardValues array
		for(int i=0; i<HAND_SIZE; i++){
			//System.out.println("Debug " + this.cardsStr[i]);
			char s = this.cardsStr[i].charAt(0);
			switch(s) {
				case 'T':	this.cardValues[i] = 10;
						break;
				case 'J':	this.cardValues[i] = 11;
						break;
				case 'Q':	this.cardValues[i] = 12;
						break;
				case 'K':	this.cardValues[i] = 13;
						break;
				case 'A':	this.cardValues[i] = 14;
						break;
				default:	this.cardValues[i] = 
						Integer.parseInt(this.cardsStr[i].substring(0,1));
						break;
			}
		}

		//Fill cardCount array
		countCards();

		//Fill sortedValues array
		this.sortedValues = this.cardValues;
		Arrays.sort(this.sortedValues);

		checkHand();
	}
	/**
	 * Print function used for debugging
	 */
	public void print() {
		System.out.print(this.playerName + " : ");
		int j=0;
		for(int i=0; i<HAND_SIZE; i++){
			System.out.print(cardsStr[i]);
			System.out.print(" ");
		}
		//System.out.println();
		/*System.out.print("Card Counts: ");
		for(int i=0; i<HAND_SIZE; i++){
			System.out.print(cardCount[i]);
		}*/
		System.out.println();
	}

	public boolean isFlush(){
		//Check for cards with all the same suit
		for(int i=0; i<HAND_SIZE; i++){
			//if not at end of cardsStr 
			if ((i<HAND_SIZE-1)) {
				//if current and next suit are not equal
				if (!(cardsStr[i].substring(1).equals(cardsStr[i+1].substring(1)))) {
					return false;
				}
			}
		}
		this.rank =  this.sortedValues[HAND_SIZE-1];
		this.highCard = rank;
		return true;
	}

	public boolean isStraight() {
		for(int i=0; i<HAND_SIZE; i++){
			//if not at end of cardsStr
			if ((i<HAND_SIZE-1)) {
				if (this.sortedValues[i]+1 != (this.sortedValues[i+1])) {
					return false;
				}
			}
		}
		this.rank =  this.sortedValues[HAND_SIZE-1];
		this.highCard = rank;
		return true;
	}

	public boolean isFullHouse(){
		rank = 0;
		highCard = 0;
		for(int i=0; i < HAND_SIZE; i++) {
			if(cardCount[i] == 3 || cardCount[i] == 2) {
				this.rank += cardValues[i];
				if(highCard < cardValues[i]) {
					highCard = cardValues[i];
				}
			}
		}
		return (IntStream.of(this.cardCount).anyMatch(x -> x == 3) &&
		 IntStream.of(this.cardCount).anyMatch(x -> x == 2));
	}

	public boolean isTwoPairs() {
		int count = 0;
		highCard = 0;
		boolean found = false;

		//loops through cards of pairs and sets members first and second pair and nonpair card
		for(int i=0; i < HAND_SIZE; i++) {
			if(cardCount[i] == 2) {

				if(!found) {
					this.firstPair = cardValues[i];
					found = true;
				}
				else if(this.firstPair != cardValues[i]) {
					this.secondPair = cardValues[i];
				}
				
				//pairs with same values
				if(this.secondPair == 0) {
					this.secondPair = this.firstPair;
				}


				count++;
			}
			else
			{
				this.nonpair = cardValues[i];
			}

			//find highest card
			if(firstPair < secondPair) {
				highCard = secondPair;
			}
			else if (firstPair > secondPair){
				highCard = firstPair;
			}

			if(highCard < nonpair) {
				highCard = nonpair;
			}

			//two pairs found
			if(count >= 4) {
				return true;
			}
		}
		return false;
	}

	public boolean isFourOfAKind(){
		boolean found = false;
		this.rank = 0;
		for(int i=0; i < HAND_SIZE; i++) {
			if(cardCount[i] == 4) {
				this.rank += cardValues[i];
				this.highCard = cardValues[i];
				found = true;
			}
		}
		return found;
	}

	public boolean isThreeOfAKind(){
		this.rank=0;
		for(int i=0; i < HAND_SIZE; i++) {
			if(cardCount[i] == 3) {
				this.rank += cardValues[i];
				this.highCard = cardValues[i];
			}
		}
		return IntStream.of(this.cardCount).anyMatch(x -> x == 3);
	}
	
	public boolean isPair(){
		rank=0;
		highCard = 0;
		for(int i=0; i < HAND_SIZE; i++) {
			if(cardCount[i] == 2) {
				rank += cardValues[i];
				if(highCard < cardValues[i]) {
					highCard = cardValues[i];
				}
			}
		}

		return IntStream.of(this.cardCount).anyMatch(x -> x == 2);
	}

	public boolean isStraightFlush() {
		if(isStraight() && isFlush()) {
			return true;
		}
		return false;
	}

	/**
	 * reads highCard member int value and returning corresponding String
	 *
	 * @return String String form of hands high card
	 */
	public String getHighCardString() {
			switch(this.highCard) {
				//case 10:	return "Ten";
				//		break;
				case 11:	return "Jack";
				case 12:	return "Queen";
				case 13:	return "King";
				case 14:	return "Ace";
				default:	return Integer.toString(this.highCard);
			}
	}

	/**
	 * reads hand enum member and returning corresponding hand String
	 *
	 * @return String String form of hand
	 */
	public String getHandString() {
		if(this.hand == Hand.STRAIGHT_FLUSH) {
			return "straight flush";
		}
		else if(this.hand == Hand.FOUR_KIND) {
			return "four of a kind";
		}
		else if(this.hand == Hand.FULL_HOUSE) {
			return "full house";
		}
		else if(this.hand == Hand.FLUSH) {
			return "flush";
		}
		else if(this.hand == Hand.STRAIGHT) {
			return "straight";
		}
		else if(this.hand == Hand.THREE_KIND) {
			return "three of a kind";
		}
		else if(this.hand == Hand.TWO_PAIR) {
			return "two pair";
		}
		else if(this.hand == Hand.PAIR) {
			return "pair";
		}
		else {
			return " ";
		}
	}

	/**
	 * Double loop to count alike cards in hand
	 */ 
	private void countCards() {
		for(int i=0; i<HAND_SIZE; i++) {
			cardCount[i] = 0;
			int currCard = cardValues[i];
			for(int j=0; j<HAND_SIZE; j++) {
				if(currCard == cardValues[j]) {
					cardCount[i]++;
				}
			}
		}
	}
	
	/**
	 * Used by constructor to call boolean member functions to determine the current pokerhand
	 */
	private void checkHand() {
		if(isStraightFlush()) {
			hand = Hand.STRAIGHT_FLUSH;
		}
		else if(isFourOfAKind()) {
			hand = Hand.FOUR_KIND;
		}
		else if(isFullHouse()) {
			hand = Hand.FULL_HOUSE;
		}
		else if(isFlush()) {
			hand = Hand.FLUSH;
		}
		else if(isStraight()) {
			hand = Hand.STRAIGHT;
		}
		else if(isThreeOfAKind()) {
			hand = Hand.THREE_KIND;
		}
		else if(isTwoPairs()) {
			hand = Hand.TWO_PAIR;
		}
		else if(isPair()) {
			hand = Hand.PAIR;
		}
		else {
			hand = Hand.HIGH_CARD;
			highCard = sortedValues[HAND_SIZE-1];
		}
	}

	public String playerName;
	public String highCardStr;		//Highest card in String form
	public Hand hand;			//Enumerated Hand value
	public int highCard;			//Highest card for current hand
	public int rank;			//scoring rank for rounds with the same hands
	public int cardCount[];			//counts of alike cards
	public int cardValues[];		//Array of hands cards as Int values
	public String cardsStr[]; 		//Array of hands cards as strings
	public int sortedValues[];		//Array of sorted values corresponding to cards
	public int firstPair;			//first value in a Two Pair hand
	public int secondPair;			//second value in a Two Pair hand
	public int nonpair;			//nonpair card for Two Pair hand
}
