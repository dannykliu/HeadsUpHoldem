import java.util.List;


public class FlushPokerHand extends PokerHand {

	public FlushPokerHand(List<Card> cards) {
		super(5, cards);
	}

	@Override
	public boolean matches() {
		int counter = 0;
		for(int i =0; i<4; i++)
		{
			if(_cardsF.get(i).getSuit() == _cardsF.get(i+1).getSuit())
			{
				counter++;
			}
		}
		if (counter == 4)
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
		for(int i = 4; i>= 0; i--)
		{
			if (_cardsF.get(i).getValue() > other._cardsF.get(i).getValue())
			{
				return 1;
			}
			else if(_cardsF.get(i).getValue() < other._cardsF.get(i).getValue())
			{
				return -1;
			}
			
			if(i == 0 && _cardsF.get(i).getValue() == other._cardsF.get(i).getValue())
			{
				return 0;
			}
		}		
		return 0;
	}

}
