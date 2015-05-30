import java.util.List;


public class StraightFlushPokerHand extends PokerHand {
	private Card _c1; // contains the top card of the straight-flush
	public StraightFlushPokerHand(List<Card> cards) {
		super(8, cards);
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
			if(_cardsSF.get(i+1).getValue() - _cardsSF.get(i).getValue() == 1 
					|| (_cardsSF.get(3).getValue() == 3 && _cardsSF.get(i+1).getValue() - _cardsSF.get(i).getValue() == 9))
			{
				straightCounter++;
			}
		}
		if(straightCounter == 4)
		{
			_c1 = _cardsSF.get(3);
			straight = true;
		}
		//checks for flush
		for(int i =0; i<4; i++)
		{
			if(_cardsSF.get(i).getSuit() == _cardsSF.get(i+1).getSuit())
			{
				flushCounter++;
			}
		}
		if (flushCounter == 4)
		{
			flush = true;
		}
		
		if(straight && flush)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	protected int isHigher(PokerHand o) {
		StraightFlushPokerHand other = (StraightFlushPokerHand) o;
		if(_c1.getValue() > other._c1.getValue())
		{
			return 1;
		}
		else if(_c1.getValue() < other._c1.getValue())
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}

}
