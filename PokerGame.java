//: control/WhileTest.java
// Demonstrates the while loop.
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
	 
	//Init poker Hands
	
	 while(line != null) {
		h1 = new PokerHand(line.substring(0,21));
		h2 = new PokerHand(line.substring(23));
		h1.print();
		h2.print();
		line = reader.readLine();


		//Poker Hand Simulator
		//
		//Straight Flush
		checkHand(PokerHand::isStraightFlush, PokerHand::isStraightFlush, h1, h2, "Straight Flush");
		checkHand(PokerHand::isFourOfAKind, PokerHand::isFourOfAKind, h1, h2, "Four of a Kind");
		checkHand(PokerHand::isFullHouse, PokerHand::isFullHouse, h1, h2, "Full House");
		checkHand(PokerHand::isFlush, PokerHand::isFlush, h1, h2, "Flush");
		checkHand(PokerHand::isStraight, PokerHand::isStraight, h1, h2, "Straight");
		checkHand(PokerHand::isThreeOfAKind, PokerHand::isThreeOfAKind, h1, h2, "Three of a Kind");
		checkHand(PokerHand::isTwoPairs, PokerHand::isTwoPairs, h1, h2, "Two Pairs");
		checkHand(PokerHand::isPair, PokerHand::isPair, h1, h2, "Pair");
		checkHand(PokerHand::isPair, PokerHand::isPair, h1, h2, "High Card");

		//if(h1.highCard > h2.highCard) {
		//	handWinsHigh(h1, h2);
	//	}
	//	else if (h1.highCard < h2.highCard) {
	//		handWinsHigh(h2,h1);
	//	}
	//	else {
	//		System.out.println("Tie.");
	//	}
	 }

		//if(h1.isStraightFlush() && h2.isStraightFlush()) {
		//	System.out.println("High Cards: " + h1.highCard + ", " + h2.highCard);
		//}

		//Four of a kind
		//check


	}
	catch(IOException e) {
		System.out.println("Input error");
	}
 }

  public static void checkHand(Function<PokerHand, Boolean> hand1, Function<PokerHand, Boolean> hand2, 
		 PokerHand ph1, PokerHand ph2, String hand) {
	 //same hands
	 if(hand1.apply(ph1) && hand2.apply(ph2)) {
		 	if(ph1.rank > ph2.rank) {
				handWinsHigh(ph1, ph2);
			}
			else if(ph1.rank < ph2.rank) {
				handWinsHigh(ph2, ph1);
			}
			else {	//hands are equal or High card needs calculated
				findHighCard(hand);
				if(ph1.highCard > ph2.highCard) {
					handWinsHigh(ph1,ph2);
				}
				else if(ph1.highCard < ph2.highCard) {
					handWinsHigh(ph2, ph1);
				}
				else {
					System.out.println("Tie");
				}
			}
	 }
	else if (hand1.apply(ph1)) {
		findHighCard(hand);
		handWins(ph1, ph2, hand);
	}
	else if (hand2.apply(ph2)) {
		findHighCard(hand);
		handWins(ph2, ph1, hand);
	}
	else {
		return;
	}
 }


 private static void handWins(PokerHand hand1, PokerHand hand2, String hand) {
	System.out.println(hand1.playerName + " wins. with " + hand + ": " + hand1.highCard 
						+ " over " + hand2.highCard);
 }

 private static void handWinsHigh(PokerHand hand1, PokerHand hand2) {
	System.out.println(hand1.playerName + " wins. with high card: " + hand1.highCard 
						+ " over " + hand2.highCard);
 }

 private static void findHighCard(String hand) {
 	if(hand == "Two Pairs") {
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
	else if(hand == "Pair") {
		for(int i = h1.cardCount.length; i > 0; i--) {
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
	else if(hand == "High Card") {
		for(int i = h1.cardCount.length; i > 0; i--) {
			if(h1.sortedValues[i] != h2.sortedValues[i]) {
				h1.highCard = h1.sortedValues[i];
				h2.highCard = h2.sortedValues[i];
				break;
			}
		}
	}
 }		
	public static PokerHand h1;
	public static PokerHand h2;
	public static boolean tie = false;

} /* (Execute to see output) *///:~

