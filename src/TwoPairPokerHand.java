import java.util.List;


public class TwoPairPokerHand extends PokerHand {
	private Card _c1;
	private Card _c2;
	int _higherPair;

	public TwoPairPokerHand(List<Card> cards) {
		super(2, cards);
	}

	@Override
	public boolean matches() {
		boolean firstPair = false;
		boolean secondPair = false;
	
		//Whoaaaa I'm naming my loops and breaking out of them inside another loop. Das cool!
		A: for (int i = 0; i<4; i++)
		{
			if(_cards2P.get(i+1).getValue() == _cards2P.get(i).getValue())
			{
				_c1 = _cards2P.get(i);
				_cards2P.remove(i+1);
				_cards2P.remove(i);
				firstPair = true;
				break A;
			}
		}
		
		B: for(int i =0; i<2; i++)
		{
			if(_cards2P.get(i+1).getValue() == _cards2P.get(i).getValue())
			{
				_c2 = _cards2P.get(i);
				_cards2P.remove(i+1);
				_cards2P.remove(i);
				secondPair = true;
				break B;
			}
		}
		
		
		if(firstPair && secondPair)
		{
			_higherPair = Math.max(_c1.getValue(), _c2.getValue());
			return true;
		}
		else
		{
			return false;
		}
	}
	


	@Override
	protected int isHigher(PokerHand o) {
		TwoPairPokerHand other = (TwoPairPokerHand) o;
		if(_higherPair > other._higherPair)
		{
			return 1;
		}
		else if(_higherPair < other._higherPair)
		{
			return -1;
		}
		//Top pair is the same, check for second pair
		else if (_c2.getValue() > other._c2.getValue())
		{
			return 1;
		}
		else if(_c2.getValue() < other._c2.getValue())
		{
			return -1;
		}
		//second pair is also the same, check for last card
		else if(_cards2P.get(0).getValue() > other._cards2P.get(0).getValue())
		{
			return 1;
		}
		else if(_cards2P.get(0).getValue() < other._cards2P.get(0).getValue())
		{
			return -1;
		}
		//The last card is also the same... return tie!
		else
		{
			return 0;
		}
	}

}
