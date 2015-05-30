import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Deck {
	private static List<Card> deck = new ArrayList<Card>(); //static so everyone shares one deck
	private static int _numSuits = 4;
	private static int _numValues = 13;
	// Need to initialize these suits and values to be a random card in the deck
	String suit;
	int value;

	// constructs a deck with 52 cards
	public Deck() throws IOException 
	{
		if (deck.size() == 0) //we only want to declare deck once
		{
			for (int suit = 0; suit < _numSuits; suit++) 
			{
				for (int value = 0; value < _numValues; value++) 
				{
					deck.add(new Card(suit, value));
				}
			}
		}
	


	}

	public void shuffle() throws IOException {
		for (int i = 0; i < deck.size(); i++) {
			int randomPosition = (int) (Math.random() * deck.size());
			Card temp = deck.get(randomPosition);
			deck.set(randomPosition, new Card(deck.get(i).getSuit(), deck
					.get(i).getValue()));
			deck.set(i, new Card(temp.getSuit(), temp.getValue()));
		}
	}

	public Card getNewCard() throws IOException {
		shuffle();
		// remove and return last element of the deck
		return deck.remove(deck.size() - 1);
	}
	
	//resets our deck after each game 
	public void addCard(Card card) throws IOException
	{
		deck.add(card);
	}

}
