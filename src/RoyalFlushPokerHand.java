import java.util.List;


public class RoyalFlushPokerHand extends PokerHand {
	private Card _c1; //this will hold the "Ace" of the Royal Flush

	public RoyalFlushPokerHand(List<Card> cards) {
		super(9, cards);
	}

	@Override
	public boolean matches() {
		boolean straight = false;
		boolean flush = false;
		int straightCounter = 0;
		int flushCounter = 0;
		//checks for straight
		for(int i = 0; i < 4; i++)
		{
			if(_cardsRF.get(i+1).getValue() - _cardsRF.get(i).getValue() == 1)
			{
				straightCounter++;
			}
		}
		if(straightCounter == 4)
		{
			_c1 = _cardsRF.get(4);
			straight = true;
		}
		//checks for flush
		for(int i =0; i<4; i++)
		{
			if(_cardsRF.get(i).getSuit() == _cardsRF.get(i+1).getSuit())
			{
				flushCounter++;
			}
		}
		if (flushCounter == 4)
		{
			flush = true;
		}
		
		if(straight && flush && _c1.getValue() == 12)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	protected int isHigher(PokerHand other) {
		//nawww man, never gonna happen
		return 0;
	}

}
