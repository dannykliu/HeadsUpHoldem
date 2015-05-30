import java.util.Collections;
import java.util.List;


public class PairPokerHand extends PokerHand {
	private Card _c1;
	//Value of 1

	public PairPokerHand(List<Card> cards) 
	{
		super(1, cards);		
	}

	@Override
	//Returns true if PokerHand is of type PairPokerHand 
	public boolean matches() {
			for(int i =0; i<4; i++)
			{	
					if(_cardsP.get(i+1).getValue() == _cardsP.get(i).getValue())
					{
						_c1 = _cardsP.get(i);
						_cardsP.remove(i+1);
						_cardsP.remove(i);
						return true;
					}
					
			}
				
		return false;
	}

	@Override
	protected int isHigher(PokerHand o) {
		// o is garaunteed to be of type PairPokerHand, therefore we downcast
		PairPokerHand other = (PairPokerHand) o;
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
			for(int i = 2; i>= 0; i--)
			{
				if (_cardsP.get(i).getValue() > other._cardsP.get(i).getValue())
				{
					return 1;
				}
				else if(_cardsP.get(i).getValue() < other._cardsP.get(i).getValue())
				{
					return -1;
				}
				
				if(i == 0 && _cardsP.get(i).getValue() == other._cardsP.get(i).getValue())
				{
					return 0;
				}
			}
			
			return 0;
		}
	}

}
