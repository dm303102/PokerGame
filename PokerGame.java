// Filename: PokerGame.java
import java.io.*;
import java.util.function.Function;

/**
 * PokerGame class is the main driver class for the PokerHand class. It creates two hands for each input
 * string in the form PlayerName: card1, card2, card3, card4, card5 where the cards are in the for 2H, for 2
 * of hearts for example. checkHands determines the winner of the current hands and prints to standard out.
 *
 * @author Doug Morgan
 * @version 1.0
 * @since 2018-05-13
 *
 */
public class PokerGame {

 public static void main(String[] args) throws IOException {
	//Setup input reader
	BufferedReader reader;

	try {
	 	if (args.length != 0){
			FileReader in = new FileReader(args[0]);
	 		reader = new BufferedReader(in);
		}
		else {
			InputStreamReader in = new InputStreamReader(System.in);
	 		reader = new BufferedReader(in);
		}

	 	String line = reader.readLine(); 

	 	//Loop while poker hand input lines exist
	 	while(line != null) {
			//Init poker Hands
			h1 = new PokerHand(line.substring(0,21));
			h2 = new PokerHand(line.substring(23));
	
			//Debug info
			h1.print();
			h2.print();

			checkHands();

			line = reader.readLine();
		}
	}
	catch(IOException e) {
		System.out.println("Input error: " + e.getMessage());
	}
 }

  /**
   * Looks at PokerHand members h1 and h2. If they are the same hand, the rank or high card is used to 
   * determine the winner. Otherwise, both hands are checked for each possible poker hand in descending order
   * and once found, handWins or handWinsHigh prints out the winner or tie.
   *
   */
  public static void checkHands() {
	 //same hands
	 if(h1.hand == h2.hand && h1.hand != PokerHand.Hand.HIGH_CARD) {
		 	if(h1.rank > h2.rank) {
				handWinsHigh(h1);
			}
			else if(h1.rank < h2.rank) {
				handWinsHigh(h2);
			}
			else {	//hands are equal; determine high card if needed
				findHighCards();
				if(h1.highCard > h2.highCard) {
					handWinsHigh(h1);
				}
				else if(h1.highCard < h2.highCard) {
					handWinsHigh(h2);
				}
				else {
					System.out.println("Tie");
				}
			}
	 }
	 //Check for different hands
	else if(h1.hand == PokerHand.Hand.STRAIGHT_FLUSH) {
		handWins(h1, h2);
	}
	else if(h2.hand == PokerHand.Hand.STRAIGHT_FLUSH) {
		handWins(h2, h1);
	}
	else if(h1.hand == PokerHand.Hand.FOUR_KIND) {
		handWins(h1, h2);
	}
	else if(h2.hand == PokerHand.Hand.FOUR_KIND) {
		handWins(h2, h1);
	}
	else if(h1.hand == PokerHand.Hand.FULL_HOUSE) {
		handWins(h1, h2);
	}
	else if(h2.hand == PokerHand.Hand.FULL_HOUSE) {
		handWins(h2, h1);
	}
	else if(h1.hand == PokerHand.Hand.FLUSH) {
		handWins(h1, h2);
	}
	else if(h2.hand == PokerHand.Hand.FLUSH) {
		handWins(h2, h1);
	}
	else if(h1.hand == PokerHand.Hand.STRAIGHT) {
		handWins(h1, h2);
	}
	else if(h2.hand == PokerHand.Hand.STRAIGHT) {
		handWins(h2, h1);
	}
	else if(h1.hand == PokerHand.Hand.THREE_KIND) {
		handWins(h1, h2);
	}
	else if(h2.hand == PokerHand.Hand.THREE_KIND) {
		handWins(h2, h1);
	}
	else if(h1.hand == PokerHand.Hand.TWO_PAIR) {
		findHighCards();
		handWins(h1, h2);
	}
	else if(h2.hand == PokerHand.Hand.TWO_PAIR) {
		findHighCards();
		handWins(h2, h1);
	}
	else if(h1.hand == PokerHand.Hand.PAIR) {
		findHighCards();
		handWins(h1, h2);
	}
	else if(h2.hand == PokerHand.Hand.PAIR) {
		findHighCards();
		handWins(h2, h1);
	}
	else if(h1.hand == PokerHand.Hand.HIGH_CARD && h2.hand == PokerHand.Hand.HIGH_CARD) {
		boolean found = false;
		for(int i=h1.cardValues.length-1; i >= 0; i--) {
			if(h1.sortedValues[i] > h2.sortedValues[i] && !found) {
				h1.highCard = h1.sortedValues[i];
				handWinsHigh(h1);
				found = true;
			}
			else if(h1.sortedValues[i] < h2.sortedValues[i] && !found) {
				h2.highCard = h2.sortedValues[i];
				handWinsHigh(h2);
				found = true;
			}
		}
		if(!found) {
			System.out.println("Tie");
		}
	}
 }

 /** 
  * Prints winning message to standard output for non high card poker hands
  * @param hand1 Winning PokerHand 
  * @param hand2 Losing PokerHand
  */
 private static void handWins(PokerHand hand1, PokerHand hand2) {
	System.out.println(hand1.playerName + " wins. with " + hand1.getHandString() + ": " 
			+ hand1.getHighCardString() + " over " + hand2.getHighCardString());
 }

 /** 
  * Prints winning message to standard output for high card poker hands
  * @param hand1 Winning PokerHand
  */
 private static void handWinsHigh(PokerHand hand1) {
	System.out.println(hand1.playerName + " wins. with high card: " + hand1.getHighCardString());
						
 }

 /** 
  * sets high cards for Pair and Two Pair hands
  */
 private static void findHighCards() {
 	if(h1.hand == PokerHand.Hand.TWO_PAIR && h2.hand == PokerHand.Hand.TWO_PAIR) {
		if(h1.firstPair == h2.firstPair) {
			if(h1.secondPair == h2.secondPair) {
				h1.highCard = h2.nonpair;
				h2.highCard = h2.nonpair;
			}
			else {
				h1.highCard = h1.secondPair;
				h2.highCard = h2.secondPair;
			}
		}
		else {	
			h1.highCard = h1.firstPair;
			h2.highCard = h2.firstPair;
		}
	}
	else if(h1.hand == PokerHand.Hand.TWO_PAIR) {
		if(h1.firstPair > h1.secondPair) {
			h1.highCard = h1.firstPair;
		}
		else if(h1.firstPair < h1.secondPair) {
			h1.highCard = h1.secondPair;
		}
		else {
			h1.highCard = h1.nonpair;
			if(h1.highCard < h1.firstPair) {
				h1.highCard = h1.firstPair;
			}
		}
	}
	else if(h2.hand == PokerHand.Hand.TWO_PAIR) {
		if(h2.firstPair > h2.secondPair) {
			h2.highCard = h2.firstPair;
		}
		else if(h2.firstPair < h2.secondPair) {
			h2.highCard = h2.secondPair;
		}
		else {
			h2.highCard = h2.nonpair;
			if(h2.highCard < h2.firstPair) {
				h2.highCard = h2.firstPair;
			}
		}
	}
	if(h1.hand == PokerHand.Hand.PAIR && h2.hand == PokerHand.Hand.PAIR) {
		for(int i = h1.sortedValues.length-1; i > 0; i--) {
			int j=i, k=i;
			while(h1.cardCount[j] != 2 && j>=0) {
				j--;
			}
			while(h2.cardCount[k] !=2 && k>=0) {
				k--;
			}
			
			if(h1.sortedValues[j] != h2.sortedValues[k]) {
				h1.highCard = h1.sortedValues[j];
				h2.highCard = h2.sortedValues[k];
				break;
			}
		}
	}
	else if(h1.hand == PokerHand.Hand.PAIR) {
		boolean found = false;
		for(int i = h1.sortedValues.length-1; i >= 0; i--) {
			if(!found && h1.cardCount[i] == 2 ) {
				found = true;
				h1.highCard = h1.sortedValues[i];
			}
			
		}
	}
	else if(h2.hand == PokerHand.Hand.PAIR) {
		boolean found = false;
		for(int i = h2.sortedValues.length-1; i >= 0; i--) {
			if(!found && h2.cardCount[i] == 2 ) {
				found = true;
				h2.highCard = h2.sortedValues[i];
			}
		}
	}
 }
	public static PokerHand h1;	
	public static PokerHand h2;
} /* (Execute to see output) *///:~
