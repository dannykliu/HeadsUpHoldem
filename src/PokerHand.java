import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public abstract class PokerHand {
	private int _value;
	//Means we can access in subclass
	// TODO: All of these should be replaceable by just _cards
	// Have _cards = clone(cards) in the PokerHand constructor
	protected List<Card> _cardsRF;
	protected List<Card> _cardsSF;
	protected List<Card> _cardsQuads;
	protected List<Card> _cardsFH;
	protected List<Card> _cardsF;
	protected List<Card> _cardsStraight;
	protected List<Card> _cardsSet;
	protected List<Card> _cards2P;
	protected List<Card> _cardsP;
	protected List<Card> _cardsHC;

	
	public PokerHand(int value, List<Card> cards)
	{
		//if things get wacky, consider copying _cards because we are removing elements from cards as we go along
		//but it shouldn't be a problem because we're breaking as soon as we find our top hand 
		_value = value;
		
		//God damnit, things did get wacky
		_cardsRF = clone(cards);
		_cardsSF = clone(cards);
		_cardsQuads = clone(cards);
		_cardsFH = clone(cards);
		_cardsF = clone(cards);
		_cardsStraight = clone(cards);
		_cardsSet = clone(cards);
		_cards2P = clone(cards);
		_cardsP = clone(cards);
		_cardsHC = clone(cards);
		
		Collections.sort(_cardsRF);
		Collections.sort(_cardsSF);
		Collections.sort(_cardsQuads);
		Collections.sort(_cardsFH);
		Collections.sort(_cardsF);
		Collections.sort(_cardsStraight);
		Collections.sort(_cardsSet);
		Collections.sort(_cards2P);
		Collections.sort(_cardsP);
		Collections.sort(_cardsHC);
		
		

	}
	
	public List<Card> clone(List<Card> list)
	{
		// return new ArrayList<Card>(list);

		List<Card> cards = new ArrayList<Card>();
		for(Card c: list)
		{
			cards.add(c);
		}
		return cards;
	}
	
	//TODO: Implement toString in each subclass. Then you can do System.out.println(pokerhand)
	public String getHand()
	{
		String _winningHand = "";
		switch(_value)
		{	
		case 0: _winningHand = "High Card";
		break;
		case 1: _winningHand = "Pair";
		break;
		case 2: _winningHand = "Two Pair";
		break;
		case 3: _winningHand = "Set";
		break;
		case 4: _winningHand = "Straight";
		break;
		case 5: _winningHand = "Flush";
		break;
		case 6: _winningHand = "Full House";
		break;
		case 7: _winningHand = "Quads";
		break;
		case 8: _winningHand = "Straight Flush";
		break;
		case 9: _winningHand = "Royal Flush";
		break;
		}				
		
		return _winningHand;
	}
	
	public int compareTo(PokerHand other)
	{
		if(this._value <other._value)
		{
			return -1;
		}
		else if(this._value > other._value)
		{
			return 1;
		}
		else
		{
			return isHigher(other);
		}
		
	}
	
	//checks to see if the PokerHand is of type pair, two pair, or set, etc...
	public abstract boolean matches();
	
	//other is garaunteed to be the same dynamic type (like calling two straight flushes)... 
	protected abstract int isHigher(PokerHand other);
}
