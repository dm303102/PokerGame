//: control/WhileTest.java
// Poker hand simulator
import java.io.*;
import java.util.function.Function;

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

			line = reader.readLine();
		}
	catch(IOException e) {
		System.out.println("Input error");
	}
 }

  public static void checkHands() {
	 //same hands
	 if(h1.hand == h2.hand && h1.hand != Hand.HIGH_CARD) {
		 	if(h1.rank > h2.rank) {
				handWinsHigh(h1, h2);
			}
			else if(h1.rank < h2.rank) {
				handWinsHigh(h2, h1);
			}
			else {	//hands are equal; determine high card if needed
				findHighCards();
				if(h1.highCard > h2.highCard) {
					handWinsHigh(h1,h2);
				}
				else if(h1.highCard < h2.highCard) {
					handWinsHigh(h2, h1);
				}
				else {
					System.out.println("Tie");
				}
			}
	 }
	 //Check for different hands
	if(h1.hand == Hand.STRAIGHT_FLUSH) {
		handWins(h1, h2);
	}
	else if(h2.hand == Hand.STRAIGHT_FLUSH) {
		handWins(h2, h1,);
	}
	else if(h1.hand == Hand.FOUR_KIND) {
		handWins(h1, h2);
	}
	else if(h2.hand == Hand.FOUR_KIND) {
		handWins(h2, h1);
	}
	else if(h1.hand == Hand.FULL_HOUSE) {
		handWins(h1, h2);
	}
	else if(h2.hand == Hand.FULL_HOUSE) {
		handWins(h2, h1);
	}
	else if(h1.hand == Hand.FLUSH) {
		handWins(h1, h2);
	}
	else if(h2.hand == Hand.FLUSH) {
		handWins(h2, h1);
	}
	else if(h1.hand == Hand.STRAIGHT) {
		handWins(h1, h2);
	}
	else if(h2.hand == Hand.STRAIGHT) {
		handWins(h2, h1);
	}
	else if(h1.hand == Hand.THREE_KIND) {
		handWins(h1, h2);
	}
	else if(h2.hand == Hand.THREE_KIND) {
		handWins(h2, h1);
	}
	else if(h1.hand == Hand.TWO_PAIR) {
		findHighCards();
		handWins(h1, h2);
	}
	else if(h2.hand == Hand.TWO_PAIR) {
		findHighCards();
		handWins(h2, h1);
	}
	else if(h1.hand == Hand.PAIR) {
		findHighCards();
		handWins(h1, h2);
	}
	else if(h2.hand == Hand.PAIR) {
		findHighCards();
		handWins(h2, h1);
	}
	else if(h1.hand == Hand.HIGH_CARD && h2.hand == Hand.HIGH_CARD) {
		for(int i=cardValues.length; i > 0; i--) {
			if(h1.sortedValues[i] > h2.sortedValues[i]) {
				handWins(h1, h2);
			}
			else if(h1.sortedValues[i] < h2.sortedValues[i]) {
				handWins(h2, h1);
			}
			System.out.println("Tie");
		}	
	}
  }
 }

 private static void handWins(PokerHand hand1, PokerHand hand2) {
	System.out.println(hand1.playerName + " wins. with " + hand1.getHandString() + ": " 
			+ hand1.getHighCardString() + " over " + hand2.getHighCardString);
 }

 private static void handWinsHigh(PokerHand hand1, PokerHand hand2) {
	System.out.println(hand1.playerName + " wins. with high card: " + hand1.getHighCardString()
						+ " over " + hand2.getHighCardString());
 }

 private static void findHighCards() {
 	if(h1.hand == Hand.TWO_PAIR && h2.hand == Hand.TWO_PAIR) {
		if(h1.firstPair == h2.firstPair) {
			if(h1.secondPair == h2.secondPair) {
				if(h1.nonpair == h2.nonpair) {
					tie = true;
				}
				else if (h1.nonpair > h2.nonpair) {
					h1.highCard = h1.nonpair;
					h2.highCard = h2.nonpair;
				}
				else {
					h1.highCard = h1.nonpair;
					h2.highCard = h2.nonpair;
				}
			}
			else if(h1.secondPair > h2.secondPair) {
				h1.highCard = h1.secondPair;
				h2.highCard = h2.secondPair;
			}
			else {
				h1.highCard = h1.secondPair;
				h2.highCard = h2.secondPair;
			}
		}
		else if(h1.firstPair > h2.firstPair) {
			h1.highCard = h1.secondPair;
			h2.highCard = h2.secondPair;
		}
		else {	
			h1.highCard = h1.secondPair;
			h2.highCard = h2.secondPair;
		}
	}
	else if(h1.hand == Hand.TWO_PAIR) {
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
	else if(h2.hand == Hand.TWO_PAIR) {
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
	if(h1.hand == Hand.PAIR && h2.hand == Hand.PAIR) {
		for(int i = h1.sortedValues.length; i > 0; i--) {
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
	else if(h1.hand == Hand.PAIR) {
		for(int i = h1.sortedValues.length; i > 0; i--) {
			boolean found = false;
			if(!found && h1.cardCount[j] == 2 ) {
				found = true;
				h1.highCard = h1.sortedValues[i];
			}
			
		}
	}
	else if(h2.hand == Hand.PAIR) {
		for(int i = h2.sortedValues.length; i > 0; i--) {
			boolean found = false;
			if(!found && h2.cardCount[j] == 2 ) {
				found = true;
				h2.highCard = h2.sortedValues[i];
			}
			
		}
	}

 }		
	public static PokerHand h1;
	public static PokerHand h2;
	//public static boolean tie = false;

} /* (Execute to see output) *///:~

