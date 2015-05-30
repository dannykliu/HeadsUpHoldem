import java.util.List;


public class StraightPokerHand extends PokerHand {
	private Card _c1;
	public StraightPokerHand(List<Card> cards) {
		super(4, cards);
	}

	@Override
	public boolean matches() {
		int counter = 0;
		for(int i = 0; i < 4; i++)
		{
			//Remember that A-5 is also a straight, so therefore we add a special case where Ace(Value 12) - five(Value 3) = 9 
			if(_cardsStraight.get(i+1).getValue() - _cardsStraight.get(i).getValue() == 1 
				|| (_cardsStraight.get(3).getValue() == 3 && _cardsStraight.get(4).getValue() - _cardsStraight.get(3).getValue() == 9))
			{
				counter++;
			}
		}
		if(counter == 4)
		{
			_c1 = _cardsStraight.get(3); //Compare the second last element and not the last element because A-5 < 2-6
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	protected int isHigher(PokerHand o) {
		StraightPokerHand other = (StraightPokerHand) o;
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
