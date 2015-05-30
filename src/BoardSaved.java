
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class BoardSaved extends JPanel implements MouseListener, KeyListener{
	BufferedImage imgBackground;
	BufferedImage imgNameTag;
	BufferedImage imgDealerChip;
	BufferedImage imgPot;
	BufferedImage imgFold;
	BufferedImage imgCall;
	BufferedImage imgBet;
	BufferedImage imgCheck;
	BufferedImage imgRaise;

	int p1Stack;
	int p2Stack;
	String p1Name;
	String p2Name;
	Card p1Card1;
	Card p1Card2;
	Card p2Card1;
	Card p2Card2;
	boolean playerIsDealer;
	static int potSize = 0;
	public Card flop1;
	public Card flop2;
	public Card flop3;
	public Card turn;
	public Card river;
	boolean goToFlop = false;
	boolean goToTurn = false;
	boolean goToRiver = false;
	boolean p1Wins = false;
	boolean p2Wins = false;
	Player _p1;
	Player _p2;
	Player _winner;
	String _winningHand;
	boolean playerFold = false;
	boolean oppFold = false;
	boolean playerCall = false;
	boolean oppCall = false;
	volatile boolean preFlopState = true;
	boolean postFlopState = false;
	boolean turnState = false;
	boolean riverState = false;
	volatile boolean _isPlayerTurn = false;
	boolean handEnd = false;
	boolean oppCheck = false;
	boolean playerCheck = false;
	boolean oppBet = false;
	//boolean oppRaise = false; //SIMPLIFY GAME. Betting and raising will be the same
	boolean playerBet = false;
	//boolean playerRaise = false;
	int oppBetSize = 0;
	int playerBetSize = 0;
	
		
	BoardSaved(Player p1, Player p2, Deck deck) throws IOException
	{
		_p1 = p1;
		_p2 = p2;
		p1Stack = p1.getStack();
		p2Stack = p2.getStack();
		p1Name = p1.getName();
		p2Name = p2.getName();
		p1Card1 = p1.getCard1();
		p1Card2 = p1.getCard2();
		p2Card1 = p2.getCard1();
		p2Card2 = p2.getCard2();
		flop1 = deck.getNewCard();
		flop2 = deck.getNewCard();
		flop3 = deck.getNewCard();
		turn = deck.getNewCard();
		river = deck.getNewCard();
		/*
		System.err.println(p1Card1);
		System.err.println(p1Card2);
		System.err.println(p2Card1);
		System.err.println(p2Card2);
		System.err.println(flop1);
		System.err.println(flop2);
		System.err.println(flop3);
		System.err.println(turn);
		System.err.println(river);
		*/

		URL imgURL = getClass().getResource("Background.png");
        imgBackground = ImageIO.read(imgURL);
        imgURL = getClass().getResource("Nametag.png");
        imgNameTag = ImageIO.read(imgURL);
        imgURL = getClass().getResource("DealerChip.png");
        imgDealerChip = ImageIO.read(imgURL);
        imgURL = getClass().getResource("Pot.png");
        imgPot = ImageIO.read(imgURL);
        imgURL = getClass().getResource("Fold.png");
        imgFold = ImageIO.read(imgURL);
        imgURL = getClass().getResource("Bet.png");
        imgBet = ImageIO.read(imgURL);
        imgURL = getClass().getResource("Check.png");
        imgCheck = ImageIO.read(imgURL);
        imgURL = getClass().getResource("Raise.png");
        imgRaise = ImageIO.read(imgURL);
        imgURL = getClass().getResource("Call.png");
        imgCall = ImageIO.read(imgURL);

    	if(Math.random() < 0.5)
		{
			playerIsDealer = false;
			playerBetSize = 1;
		}
		else
		{
			playerIsDealer = true;
			oppBetSize = 1;
			
		} 
    	playerIsDealer = true;
    	oppBetSize = 1;

    	setPlayerTurn(playerIsDealer);
		//_winner = Showdown.showdown(p1, p2, this);
		//_winningHand = Showdown.findWinningHand(p1, p2, this); UNCOMMENT THIS 
    	
    	//Enable listeners 
        addMouseListener(this);
        addKeyListener(this);

	}
	

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(imgBackground, 0, 0, this);
		g.drawImage(imgNameTag, 100, 630, this);
		g.drawImage(imgNameTag, 100, 28, this);
		g.drawImage(p1Card1.getBuffImg(), 280, 630, this);
		g.drawImage(p1Card2.getBuffImg(), 400, 630, this);
		g.drawImage(p2Card1.getBuffImg(), 280, 20, this);
		g.drawImage(p2Card2.getBuffImg(), 400, 20, this);
		g.drawImage(imgPot, 50, 300, this);
		
		if(goToFlop)
		{
			g.drawImage(flop1.getBuffImg(), 280, 325, this);
			g.drawImage(flop2.getBuffImg(), 400, 325, this);
			g.drawImage(flop3.getBuffImg(), 520, 325, this);
		}		
		if(goToTurn)
		{
			g.drawImage(turn.getBuffImg(), 640, 325, this);
		}		
		if(goToRiver)
		{
			g.drawImage(river.getBuffImg(), 760, 325, this);
		}		
		if(playerIsDealer)
		{
			g.drawImage(imgDealerChip, 100, 625, this);
		}
		else
		{
			g.drawImage(imgDealerChip, 100, 28, this);
		}
		if(playerTurn() && preFlopState)
		{
			g.drawImage(imgFold, 520, 660, this);
			g.drawImage(imgCall, 660, 660, this);
			g.drawImage(imgRaise, 800, 660, this);
		}

		if(oppTurn() && preFlopState && playerCall)
		{
			g.drawImage(imgFold, 520, 28, this);
			g.drawImage(imgCheck, 660, 28, this);
			g.drawImage(imgBet, 800, 28, this);
		}
		else if (oppTurn() && preFlopState && !playerCall)
		{
			g.drawImage(imgFold, 520, 28, this);
			g.drawImage(imgCall, 660, 28, this);
			g.drawImage(imgRaise, 800, 28, this);
		}
		
		
	
		Font myFont = new Font("Comic Sans MS", Font.BOLD, 20);
        g.setFont(myFont);
		g.drawString("Stack: " + p1Stack, 110, 695);
		g.drawString("Stack: " + p2Stack, 110, 100);
		g.drawString(p1Name, 110, 660);
		g.drawString(p2Name, 110, 60);
		updatePot();
		g.drawString("" + potSize, 100, 370);
		//This _winner is currently the winner of the showdown, not necessarily the winner of the hand 
		/*
		if(_winner != null)
		{
			g.drawString("" + _winner.getName() + " wins!", 600, 200);
		}
		g.drawString("" + _winningHand, 600, 250);
		*/
		if(handEnd)
		{
			g.drawString("" + _winner.getName() + " wins!", 600, 200);
		}
	}
	
	public Card getFlop1()
	{
		return flop1;
	}	
	public Card getFlop2()
	{
		return flop2;
	}
	public Card getFlop3()
	{
		return flop3;
	}
	public Card getTurn()
	{
		return turn;
	}
	public Card getRiver()
	{
		return river;
	}
	public void updatePot()
	{
		potSize = 200 - p1Stack - p2Stack;
	}
	public static void resetPot()
	{
		potSize = 0;
	}
	public void goToFlop()
	{
		goToFlop = true;
	}
	public void goToTurn()
	{
		goToTurn = true;
	}
	public void goToRiver()
	{
		goToRiver = true;
	}
	public boolean playerTurn()
	{
		return _isPlayerTurn;
	}
	public boolean oppTurn()
	{
		return !_isPlayerTurn;
	}
	public void setPlayerTurn(boolean turn)
	{
		_isPlayerTurn = turn;
	}
	public void resetPlayerBet()
	{
		oppCheck = false;
		playerCheck = false;
		oppBet = false;
		playerBet = true;
		//oppBetSize = 0;
		setPlayerTurn(false);
	}
	public void resetOppBet()
	{
		oppCheck = false;
		playerCheck = false;
		playerBet = false;
		oppBet = true;
		//playerBetSize = 0;
		setPlayerTurn(true);
	}
	public void run() throws InterruptedException
	{
		//goToFlop();
		//goToTurn();
		//goToRiver();
		
		if(playerIsDealer)
		{
			p1Stack -=1;
			p2Stack -=2;
		}
		else 
		{
			p1Stack -=2;
			p2Stack-=1;
		}
		while(true)
		{
			while(preFlopState && playerTurn())
			{
				if(playerFold)
				{
					p2Stack += potSize;
					preFlopState = false;
					updatePot();
					handEnd = true;
					_winner = _p2;
				}				
				if(playerCall)
				{
					p1Stack -= oppBetSize;
					if(oppBetSize == 1) //First call, then pass play onto big blind
					{
						setPlayerTurn(false);
					}
					else
					{
						p1Stack += playerBetSize;
						setPlayerTurn(false);
						preFlopState = false;
						goToFlop();
					}
				}

				if (playerBet)
				{
					Scanner sc = new Scanner(System.in);
					playerBetSize = sc.nextInt();
					p1Stack -= playerBetSize;
			
					resetPlayerBet();
				}
				
				repaint();
			}

			while(preFlopState && oppTurn())
			{
				if(oppFold)
				{
					p1Stack += potSize;
					preFlopState = false;
					updatePot();
					handEnd = true;
					_winner = _p1;
				}
				if(oppCall)
				{
					p2Stack -= playerBetSize;
					if(oppBetSize !=1)
					{
						p2Stack += oppBetSize;
						preFlopState = false;
						goToFlop();
					}
					else
					{
						p2Stack +=1;
						preFlopState = false;
						goToFlop();
					}
					setPlayerTurn(true);
				}		
				if(oppCheck)
				{
					preFlopState = false;
					goToFlop();
				}
				if(oppBet)
				{
					Scanner sc = new Scanner(System.in);
					oppBetSize = sc.nextInt();
					p2Stack -= oppBetSize;
					setPlayerTurn(true);
					playerCall = false;
					resetOppBet();
				}
				
				repaint();
			}
			
			
			repaint();
			Thread.sleep(17);
		}
			
	}
	


	@Override
	public void keyTyped(KeyEvent e) {

	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}	@Override
	public void mouseClicked(MouseEvent e) {
			//Player fold
			if(e.getX() > 520 && e.getX() < 640 && e.getY() > 660 && e.getY() < 705 && preFlopState) //the last condition must be changed
			{
				playerFold = true;
			}
			//Player check/call
			if(e.getX() > 660 && e.getX() < 800 && e.getY() > 660 && e.getY() < 705 && preFlopState)
			{
				playerCall = true;
			}
			//PLAYER CHECK HERE 
			//Player bet. Simplified so that betting is the same as raising. No min bet
			if(e.getX() > 820 && e.getX() < 860 && e.getY() > 660 & e.getY() < 705 && preFlopState && (oppCall || oppBet || playerTurn()))
			{
				playerBet = true;
			}
			
			
			//Opponnet fold
			if(e.getX() > 520 && e.getX() < 640 && e.getY() > 28 && e.getY() < 73 && preFlopState)
			{
				oppFold = true;
			}
			//Opponent check/call
			if(e.getX() > 660 & e.getX() < 800 && e.getY() > 28 && e.getY() < 73 && preFlopState && !playerCall)
			{
				oppCall = true;
			}
			else if(e.getX() > 660 & e.getX() < 800 && e.getY() > 28 && e.getY() < 73 && preFlopState && playerCall)
			{
				oppCheck = true; 
			}		
			//Opponent bet/raise. Simplified so that betting is the same as raising. No min bet 
			if(e.getX() > 820 && e.getX() < 860 && e.getY() > 28 && e.getY() < 73 && preFlopState && (playerCall || playerBet))
			{
				oppBet = true;
			}
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

