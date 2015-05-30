import java.util.ArrayList;
import java.util.List;
// This is a new comment
public class Showdown {
	
	//We never call this function outside of the showdown class
	public static PokerHand getHand(Player p, Board b)
	{
		Card playerCard1 = p.getCard1();
		Card playerCard2 = p.getCard2();
		Card flop1 = b.getFlop1();
		Card flop2 = b.getFlop2();
		Card flop3 = b.getFlop3();
		Card turn = b.getTurn();
		Card river = b.getRiver();
		Card[] allSevenCards = {
				p.getCard1(),
				p.getCard2(),
				b.getFlop1(),
				b.getFlop2(),
				b.getFlop3(),
				b.getTurn(),
				b.getRiver()
				};
		
		Combination c = new Combination(7, 5);
		PokerHand bestHand = null;

		while (c.hasNext()) 
		{
			//indicies is the current combination 
			int[] indices = c.next();
			List<Card> cards = new ArrayList<Card>();
			for (int index: indices) 
			{
				//Something wrong here, cards only has 3 elements in the first run
				//adding to 5 cards to cards in the current combination 
				cards.add(allSevenCards[index]);
			}
			PokerHand currentBestHand = Showdown.findBestHand(cards);
			
			if((bestHand == null) || (bestHand.compareTo(currentBestHand) < 0))
			{
				bestHand = currentBestHand;
			}

		}
		
		return bestHand;
	
	}

	
	public static PokerHand findBestHand(List<Card> cards) {
		List<PokerHand> pokerHands = new ArrayList<PokerHand>();
		// This list is IN ORDER of value of poker hand
		pokerHands.add(new RoyalFlushPokerHand(cards));
		pokerHands.add(new StraightFlushPokerHand(cards));
		pokerHands.add(new QuadsPokerHand(cards));
		pokerHands.add(new FullHousePokerHand(cards));
		pokerHands.add(new FlushPokerHand(cards));
		pokerHands.add(new StraightPokerHand(cards));
		pokerHands.add(new SetPokerHand(cards));
		pokerHands.add(new TwoPairPokerHand(cards));
		pokerHands.add(new PairPokerHand(cards));
		pokerHands.add(new HighCardPokerHand(cards));
		
		PokerHand bestHand = null;
		for(PokerHand pokerHand: pokerHands) {
			//if the current pokerhand is of this type, then this is our best hand 
			if (pokerHand.matches()) 
			{
			  bestHand = pokerHand;
			  break;
			}
		}
		//Condition you know must be true 
		//Assert is a very powerful debug tool 
		assert (bestHand != null);
		return bestHand;	
	}
	
	
	public static Player showdown(Player a, Player b, Board board)
	{
		PokerHand aHand = getHand(a, board);
		PokerHand bHand = getHand(b, board);
		//return null; //get rid of this baby
		
		int value = aHand.compareTo(bHand);
		if(value > 0)
		{
			return a;
			
		}
		else if (value <0)
		{
			
			return b;
		}
		else
		{
			//if value == 0, this is a tie 
			return null;
		}
		
	}
	
	public static String findWinningHand(Player a, Player b, Board board)
	{
		PokerHand aHand = getHand(a, board);
		PokerHand bHand = getHand(b, board);
		int value = aHand.compareTo(bHand);
		
		if(value > 0)
		{
			return aHand.getHand();
		}
		else if (value <0)
		{			
			return bHand.getHand();
		}
		else
		{
			return "Tie!";
		}
	}
		
}
