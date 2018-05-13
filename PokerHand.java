import java.util.*;
import java.util.stream.IntStream;

public class PokerHand {
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
		for(int i=0; i<this.cardsStr.length; i++){
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
	
	public void print() {
		System.out.print(this.playerName + " : ");
		int j=0;
		for(int i=0; i<cardsStr.length; i++){
			System.out.print(cardsStr[i]);
			System.out.print(" ");
		}
		//System.out.println();
		/*System.out.print("Card Counts: ");
		for(int i=0; i<cardsStr.length; i++){
			System.out.print(cardCount[i]);
		}*/
		System.out.println();
	}

	public boolean isFlush(){
		//Check for cards with all the same suit
		for(int i=0; i<cardsStr.length; i++){
			//if not at end of cardsStr 
			if ((i<cardsStr.length-1)) {
				//if current and next suit are not equal
				if (!(cardsStr[i].substring(1).equals(cardsStr[i+1].substring(1)))) {
					return false;
				}
			}
		}
		this.rank =  this.sortedValues[this.sortedValues.length-1];
		this.highCard = rank;
		return true;
	}

	public boolean isStraight() {
		for(int i=0; i<this.sortedValues.length; i++){
			//if not at end of cardsStr
			if ((i<this.sortedValues.length-1)) {
				if (this.sortedValues[i]+1 != (this.sortedValues[i+1])) {
					return false;
				}
			}
		}
		this.rank =  this.sortedValues[this.sortedValues.length-1];
		this.highCard = rank;
		return true;
	}

	public boolean isFullHouse(){
		this.rank = 0;
		for(int i=0; i < cardCount.length; i++) {
			if(cardCount[i] == 3) {
				this.rank += cardValues[i];
				//this.highCard = cardValues[i];
			}
		}
		return (IntStream.of(this.cardCount).anyMatch(x -> x == 3) &&
		 IntStream.of(this.cardCount).anyMatch(x -> x == 2));
	}

	public boolean isTwoPairs() {
		int count = 0;
		int highCard = 0;
		boolean found = false;

		for(int i=0; i < cardCount.length; i++) {
			if(cardCount[i] == 2) {

				if(!found) {
					this.firstPair = cardValues[i];
					found = true;
				}
				else if(this.firstPair != cardValues[i]) {
					this.secondPair = cardValues[i];
				}

				if(this.secondPair == 0) {
					this.secondPair = this.firstPair;
				}

				count++;
			}
			else
			{
				this.nonpair = cardValues[i];
			}

			if(count >= 4) {
				return true;
			}
		}
		return false;
	}

	public boolean isFourOfAKind(){
		boolean found = false;
		this.rank = 0;
		for(int i=0; i < cardCount.length; i++) {
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
		for(int i=0; i < cardCount.length; i++) {
			if(cardCount[i] == 3) {
				this.rank += cardValues[i];
				this.highCard = cardValues[i];
			}
		}
		return IntStream.of(this.cardCount).anyMatch(x -> x == 3);
	}
	
	public boolean isPair(){
		this.rank=0;
		return IntStream.of(this.cardCount).anyMatch(x -> x == 2);
	}

	public boolean isStraightFlush() {
		if(isStraight() && isFlush()) {
			return true;
		}
		return false;
	}

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

	private void countCards() {
		for(int i=0; i<cardValues.length; i++) {
			cardCount[i] = 0;
			int currCard = cardValues[i];
			for(int j=0; j<cardValues.length; j++) {
				if(currCard == cardValues[j]) {
					cardCount[i]++;
				}
			}
		}
	}
	
	
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
		}
	}

	public String playerName;
	public String highCardStr;		//Highest card in String form
	public Hand hand;
	public int highCard;		//Highest card from cardValues[]
	public int rank;		//scoring rank for rounds with the same hands
	public int cardCount[];
	public int cardValues[];	//Array of hands cards as Int values
	public String cardsStr[]; 	//Array of hands cards as strings
	public int sortedValues[];
	public int firstPair;
	public int secondPair;
	public int nonpair;
	//public static boolean won;

}
