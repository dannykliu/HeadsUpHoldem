import java.util.List;


public class SetPokerHand extends PokerHand {
	private Card _c1;

	public SetPokerHand(List<Card> cards) {
		super(3, cards);
	}

	@Override
	public boolean matches() {
		for (int i =0; i<3; i++)
		{
			if(_cardsSet.get(i).getValue() == _cardsSet.get(i+1).getValue() && _cardsSet.get(i).getValue() == _cardsSet.get(i+2).getValue())
			{
				_c1 = _cardsSet.get(i);
				_cardsSet.remove(i+2);
				_cardsSet.remove(i+1);
				_cardsSet.remove(i);
		
				return true;
			}
		}
		return false;
	}

	@Override
	protected int isHigher(PokerHand o) {
		SetPokerHand other = (SetPokerHand) o;
		if(_c1.getValue() > other._c1.getValue())
		{
			return 1;
		}
		else if(_c1.getValue() < other._c1.getValue())
		{
			return -1;
		}
		// sets are the same, look at kicker
		else
		{
			for (int i=1; i>= 0; i--)
			{
				if (_cardsSet.get(i).getValue() > other._cardsSet.get(i).getValue())
				{
					return 1;
				}
				else if(_cardsSet.get(i).getValue() < other._cardsSet.get(i).getValue())
				{
					return -1;
				}
				//only occurs on the last loop
				if(i == 0 && _cardsSet.get(i).getValue() == other._cardsSet.get(i).getValue())
				{
					return 0;
				}
			}
			
			return 0;
		}
			
	}

}
