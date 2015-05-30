import java.util.List;


public class FullHousePokerHand extends PokerHand {
	private Card _c1; //one card of the set
	private Card _c2; //one card of the pair
	

	public FullHousePokerHand(List<Card> cards) {
		super(6, cards);
	}

	@Override
	public boolean matches() {
		boolean setExist = false;
		boolean pairExist = false;
		A: for (int i =0; i< 3; i++)
		{
			if(_cardsFH.get(i).getValue() == _cardsFH.get(i+1).getValue() && _cardsFH.get(i).getValue() == _cardsFH.get(i+2).getValue())
			{
				_c1 = _cardsFH.get(i);
				_cardsFH.remove(i+2);
				_cardsFH.remove(i+1);
				_cardsFH.remove(i);
				setExist = true;
				break A;
			}
		}
		
		if(setExist && _cardsFH.get(0).getValue() == _cardsFH.get(1).getValue())
		{
			_c2 = _cardsFH.get(0);
			pairExist = true;
		}
		
		if(setExist && pairExist)
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
		FullHousePokerHand other = (FullHousePokerHand) o;
		if(_c1.getValue() > other._c1.getValue())
		{
			return 1;
		}
		else if(_c1.getValue() < other._c1.getValue())
		{
			return -1;
		}
		else if(_c2.getValue() > other._c2.getValue())
		{
			return 1;
		}
		else if(_c2.getValue() < other._c2.getValue())
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}

}
