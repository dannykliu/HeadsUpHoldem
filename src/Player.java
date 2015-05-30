import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Player {
	private int _stack = 100;
	private String _name;
	Hand _hand; 
	private Card _card1;
	private Card _card2;
	boolean _isPlayerDealer = true;
	boolean didIWin;
	//player has-a hand
	//In industry, you always want to define the instance variables within the constructors 
	
	public Player(String name) throws IOException
	{
		_name = name;
		_hand = new Hand();
		_card1 = _hand.getHand1();
		_card2 = _hand.getHand2();
	}
	
	public void setCards(Card c1, Card c2)
	{
		_card1 = c1;
		_card2 = c2;
	}
	
	public Card getCard1()
	{
		return _card1;
	}
	
	public Card getCard2() 
	{
		return _card2;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public int getStack()
	{
		return _stack;
	}
	
	public void setStack(int stack)
	{
		_stack = stack;
	}
	public boolean isPlayerDealer()
	{
		return _isPlayerDealer;
	}
	public void changeDealer(boolean dealer)
	{
		_isPlayerDealer = dealer;
	}
	/*
	public int getHandValue()
	{
		
		Board _board; //I need to access the board's flop Cards 
		Card flop1 = _board.getFlop1();
		Card flop2 = _board.getFlop2();
		Card flop3 = _board.getFlop3();
		Card turn = _board.getTurn();
		Card river = _board.getRiver();
		
		boolean HighCard; //0
		boolean Pair = false; //1
		boolean TwoPair = false; //2
		boolean Set = false; //3
		boolean Straight = false; //4
		boolean Flush = false; //5
		boolean FullHouse = false; //6
		boolean Quads = false; //7
		boolean StraightFlush = false; //8
		boolean RoyalFlush = false; //9
		int Card1Count = 0;
		int Card2Count = 0;
		int HandValue = 0;
		
		
		List<Card> board = new ArrayList<Card>();
		board.add(flop1);
		board.add(flop2);
		board.add(flop3);
		board.add(turn);
		board.add(river);
		
		List<Card> boardHand = new ArrayList<Card>();
		boardHand.add(_card1);
		boardHand.add(_card2);
		boardHand.add(flop1);
		boardHand.add(flop2);
		boardHand.add(flop3);
		boardHand.add(turn);
		boardHand.add(river);		
		Collections.sort(boardHand);
		int StraightCount = 0;
		int FlushCount = 0;
		
		//Counts if there's a straight
		for(int i =0; i<6; i++)
		{	
				if(boardHand.get(i+1).getValue() - boardHand.get(i).getValue() == 1)
				{
					StraightCount++;
				}
		}
		
		//Counts if there's a flush
		int flushTemp;
		for (int i =0; i<7; i++)
		{
			 flushTemp = 0; 

			for (int j=0; j<7;j++)
			{
				//counts if there's a flush. Updates flush counter to temp counter
				if(boardHand.get(i).getSuit() == boardHand.get(j).getSuit())
				{
					flushTemp++;
					if (flushTemp >= 6) //needs to be six because we're getting one extra count when the card is compared to itself
					{
						FlushCount = flushTemp;
						
					}
				}
			}
		}
		
	

		//Checks for full house and make a tempList to not disrupt the board
		int tempSet;
		int tempPair;
		int value;
		List<Card> tempList = new ArrayList<Card>();
		tempList.add(_card1);
		tempList.add(_card2);
		tempList.add(flop1);
		tempList.add(flop2);
		tempList.add(flop3);
		tempList.add(turn);
		tempList.add(river);
		for (int i =0; i<7; i++)
		{
			tempSet = 0;
			
			for (int j = 0; j<7; j++)
			{			
					if(tempList.get(i).getValue() == tempList.get(j).getValue())
					{
						tempSet++;
						//if there's a set...
						if(tempSet == 3)
						{
							//find value of the set
							value = tempList.get(j).getValue();
							//remove all elements of that value... i.e: remove that set from the list
							tempList.remove(j);
							tempList.remove(i);
							//remove the last element in the list that's still part of the set
							for (int lol = 0; lol<5; lol++)
							{
								if(tempList.get(lol).getValue() == value)
								{
									tempList.remove(lol);
								}
							}
							
							//now find if a pair still exists...
							for(int lol =0; lol<5; lol++)
							{
								tempPair = 0;
								for(int lol2=0; lol2<5; lol2++)
								{
									if(tempList.get(lol).getValue() == tempList.get(lol2).getValue())
									{
										tempPair++;
										if(tempPair == 2)
										{
											//THERE'S A FULL HOUSE IF A PAIR STILL EXISTS
											FullHouse = true;
											HandValue = 6;
										}
									}
								}
							}
							
							
						}
					}
			}
		}
		
		
		
		//assume the player only has a high card and update hand values accordingly 
		for(Card c: board)
		{
			if(_card1.getValue() == c.getValue())
			{
				Card1Count++;
			}
			
			if(_card2.getValue() == c.getValue())
			{
				Card2Count++;
			}
			
			//one pair
			if((Card1Count == 1 && Card2Count == 0) || (Card1Count == 0 && Card2Count == 1))
			{
				Pair = true;
				HandValue = 1;
			}
			
			//two pair and pocket pair case for set
			if(Card1Count == 1 && Card2Count == 1)
			{
				//broken if you have a pocket pair and board already has one pair
				TwoPair = true;
				HandValue = 2;
				if(_card1.getValue() == _card2.getValue())
				{
					TwoPair = false;
					Set = true;
					HandValue = 3;
				}
			}
			
			//two pair case if a pocket pair exists
			//broken if the board already has two pairs
			if(_card1.getValue() == _card2.getValue())
			{
				for (int i =0; i<7; i++)
				{
					int temp = 0;

					for (int j =0; j<7; j++)
					{
						if (board.get(i).getValue() == board.get(j).getValue())
						{
							temp++;
							if (temp == 2)
							{
								TwoPair = true;
								HandValue = 2;
							}
						}
					}
				}
			}
			
			
			//case for non-pocket pair sets
			if((Card1Count == 2 && Card2Count == 0) || (Card1Count == 0 & Card2Count == 2))
			{
				Set = true;
				HandValue = 3;
			}
			
			
			if(Card1Count == 3 || Card2Count == 3 || 
					(Card1Count + Card2Count == 4 && _card1.getValue() == _card2.getValue()))
			{
				Quads = true;
				HandValue = 7;
			}		
			
		}
		
		
		
		if(StraightCount >=5)
		{
			Straight = true;
			HandValue = 4;
		}
		
		if(FlushCount >=5)
		{
			Flush = true;
			HandValue = 5;
		}
		
		//straight flush and special case: royal flush
		if(Flush == true && Straight == true)
		{
			StraightFlush = true;
			HandValue = 8;
			if(board.get(2).getValue() >= 10)
			{
				RoyalFlush = true;
				HandValue = 9;
			}
		}
				return HandValue;
	}
	*/
	
	//also modify this to take no parameters
	public void winsHand(int potSize)
	{
		_stack += potSize;
	}

	public void raise(int raise)
	{
		_stack -= raise;
	}
	
	//we'll change this later to take no parameters
	public void call(int bet)
	{
		_stack -= bet;
	}
}
