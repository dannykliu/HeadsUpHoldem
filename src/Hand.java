import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Hand{
	private List<Card> hand = new ArrayList<Card>();
	//hand has a 2-card deck
	Deck deck = new Deck();
	public static int handSize = 2;
		
	public Hand() throws IOException
	{
		for(int i=0; i<handSize; i++)
		{
			hand.add(deck.getNewCard());
		}
	}
	
	public Card getHand1()
	{
		return hand.get(0);
	}
	
	public Card getHand2()
	{
		return hand.get(1);
	}
	
}
