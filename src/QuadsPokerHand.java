import java.util.List;

public class QuadsPokerHand extends PokerHand {
	private Card _c1; //one card of the quad
	public QuadsPokerHand(List<Card> cards) {
		super(7, cards);
	}

	@Override
	public boolean matches() {
		boolean quadExist = false;
		for (int i = 0; i<2; i++)
		{
			if (_cardsQuads.get(i).getValue() == _cardsQuads.get(i+1).getValue() 
				&& _cardsQuads.get(i).getValue() == _cardsQuads.get(i+2).getValue()
				&& _cardsQuads.get(i).getValue() == _cardsQuads.get(i+3).getValue())
			{
				_c1 = _cardsQuads.get(i);
				_cardsQuads.remove(i+3);
				_cardsQuads.remove(i+2);
				_cardsQuads.remove(i+1);
				_cardsQuads.remove(i);
				quadExist = true;
				return quadExist;
			}
		}
		return quadExist; 
	}

	@Override
	protected int isHigher(PokerHand o) {
		QuadsPokerHand other = (QuadsPokerHand) o;
		if(_c1.getValue() > other._c1.getValue())
		{
			return 1;
		}
		else if (_c1.getValue() < other._c1.getValue())
		{
			return -1;
		}
		//Quads are all the same value, look at kicker
		else if(_cardsQuads.get(0).getValue() > other._cardsQuads.get(0).getValue())
		{
			return 1;
		}
		else if (_cardsQuads.get(0).getValue() < other._cardsQuads.get(0).getValue())
		{
			return -1;
		}
		else 
		{
			return 0;
		}
	
	}

}
