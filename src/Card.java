import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Card implements Comparable{
	private int _suit;
	private int _value;
	private BufferedImage _cardImage;
	
	static BufferedImage[][] _cards = new BufferedImage[4][13];

	public Card(int suit, int value) throws IOException
	{	
		if (_cards[0][0] == null) //check if it's null because you only want to declare the buffered images the first time you make a card
		{
			for(int s =0; s<4; s++)
			{
				for(int v =0; v<13; v++)
				{
					_cards[s][v] = ImageIO.read(getClass().getResource("Card[" + s +"][" + v +"].png"));
				}
			}
		}
		
		_suit = suit;	
		_value = value;
		
	}
	
	public int getValue()
	{
		return _value;
	}
	
	public int getSuit()
	{
		return _suit;
	}
	
	public BufferedImage getBuffImg()
	{
		return _cards[getSuit()][getValue()];
	}

	@Override
	public int compareTo(Object o) 
	{
		Card c = (Card) o;
		
		if(this.getValue() > c.getValue())
		{
			return 1;
		}
		else if(this.getValue() < c.getValue())
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}


	public String toString()
	{
		return getSuitString() + " " + _value;
	}
	

	public String getSuitString()
	{
		switch(_suit)
		{
		case 0: 
			return "DIAMOND";
		case 1: 
			return "HEART";
		case 2: 
			return "CLUB";
		case 3: 
			return "SPADE";
				
		default:
			return null;
		
		}
	}

		
	
}
