import java.util.List;


public class HighCardPokerHand extends PokerHand {

	public HighCardPokerHand(List<Card> cards) {
		super(0, cards);
	}

	@Override
	public boolean matches() {
		//If we're actually on this level, that means we're definitely only a high card
		return true;
	}

	@Override
	protected int isHigher(PokerHand o) {
		HighCardPokerHand other = (HighCardPokerHand) o;
		for(int i = 4; i>= 0; i--)
		{
			if (_cardsHC.get(i).getValue() > other._cardsHC.get(i).getValue())
			{
				return 1;
			}
			else if(_cardsHC.get(i).getValue() < other._cardsHC.get(i).getValue())
			{
				return -1;
			}
			
			if(i == 0 && _cardsHC.get(i).getValue() == other._cardsHC.get(i).getValue())
			{
				return 0;
			}
		}
		
		return 0;
		
	}

}
