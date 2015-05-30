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

public class Board extends JPanel implements MouseListener, KeyListener
{
	BufferedImage imgBackground;
	BufferedImage imgNameTag;
	BufferedImage imgDealerChip;
	BufferedImage imgPot;
	BufferedImage imgFold;
	BufferedImage imgCall;
	BufferedImage imgBet;
	BufferedImage imgCheck;
	BufferedImage imgRaise;
	BufferedImage imgAdjust;
	BufferedImage imgCardBack;
	BufferedImage imgFlip;

	int p1Stack;
	int p2Stack;
	boolean playerIsDealer;
	static int potSize = 0;
	String p1Name;
	String p2Name;
	Card p1Card1;
	Card p1Card2;
	Card p2Card1;
	Card p2Card2;
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
	boolean showdownState = false;
	volatile boolean _isPlayerTurn = false;
	boolean someoneFolded = false;
	boolean handEnd = false;
	boolean gameEnd = false;
	boolean oppCheck = false;
	boolean playerCheck = false;
	boolean oppBet = false;
	// boolean oppRaise = false; //SIMPLIFY GAME. Betting and raising will be
	// the same
	boolean playerBet = false;
	// boolean playerRaise = false;
	int oppBetSize = 0;
	int playerBetSize = 0;
	int oppRaiseRange = 0;
	int playerRaiseRange = 0;
	int initialOppBet = 0;
	int initialCount = 0;
	int initialPlayerBet = 0;
	int numReRaise = 0;
	int dealerFirstBetCount = 0;
	boolean didDealerCall = false;
	boolean messedUpOppCall = false;
	boolean hidePlayerCards = true;
	boolean hideOppCards = true;

	Board(Player p1, Player p2, Deck deck) throws IOException
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
		playerIsDealer = p1.isPlayerDealer();
		/*
		 * System.err.println(p1Card1); System.err.println(p1Card2);
		 * System.err.println(p2Card1); System.err.println(p2Card2);
		 * System.err.println(flop1); System.err.println(flop2);
		 * System.err.println(flop3); System.err.println(turn);
		 * System.err.println(river);
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
		imgURL = getClass().getResource("Adjust.png");
		imgAdjust = ImageIO.read(imgURL);
		imgURL = getClass().getResource("Flip.png");
		imgFlip = ImageIO.read(imgURL);
		imgURL = getClass().getResource("CardBack.png");
		imgCardBack = ImageIO.read(imgURL);

		if (playerIsDealer)
		{
			oppBetSize = 1;
		}
		else
		{
			playerBetSize = 1;
		}
		// playerIsDealer = false;
		// playerBetSize = 1;

		setPlayerTurn(playerIsDealer);
		_winner = Showdown.showdown(p1, p2, this);
		_winningHand = Showdown.findWinningHand(p1, p2, this);

		// Enable listeners
		addMouseListener(this);
		addKeyListener(this);

		// add cards back to deck when finished //I probably don't need this.
		// I'd rather construct a new board then try to reset everything
		/*
		 * if(handEnd) { deck.addCard(p1Card1); deck.addCard(p1Card2);
		 * deck.addCard(p2Card1); deck.addCard(p2Card2); deck.addCard(flop1);
		 * deck.addCard(flop2); deck.addCard(flop3); deck.addCard(turn);
		 * deck.addCard(river); deck.shuffle(); }
		 */
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
		g.drawImage(imgAdjust, 100, 133, this);
		g.drawImage(imgAdjust, 100, 735, this);
		g.drawImage(imgFlip, 280, 165, this);
		g.drawImage(imgFlip, 280, 560, this);

		if (hidePlayerCards)
		{
			g.drawImage(imgCardBack, 280, 630, this);
			g.drawImage(imgCardBack, 400, 630, this);
		}
		if (hideOppCards)
		{
			g.drawImage(imgCardBack, 280, 20, this);
			g.drawImage(imgCardBack, 400, 20, this);
		}
		if (goToFlop)
		{
			g.drawImage(flop1.getBuffImg(), 280, 325, this);
			g.drawImage(flop2.getBuffImg(), 400, 325, this);
			g.drawImage(flop3.getBuffImg(), 520, 325, this);
		}
		if (goToTurn)
		{
			g.drawImage(turn.getBuffImg(), 640, 325, this);
		}
		if (goToRiver)
		{
			g.drawImage(river.getBuffImg(), 760, 325, this);
		}
		if (playerIsDealer)
		{
			g.drawImage(imgDealerChip, 100, 625, this);
		}
		else
		{
			g.drawImage(imgDealerChip, 100, 28, this);
		}
		if (playerTurn() && oppCall && preFlopState)
		{
			g.drawImage(imgFold, 520, 660, this);
			g.drawImage(imgCheck, 660, 660, this);
			g.drawImage(imgBet, 800, 660, this);
		}
		else if (playerTurn() && !oppCall && preFlopState)
		{
			g.drawImage(imgFold, 520, 660, this);
			g.drawImage(imgCall, 660, 660, this);
			g.drawImage(imgRaise, 800, 660, this);
		}

		if (oppTurn() && playerCall && preFlopState)
		{
			g.drawImage(imgFold, 520, 28, this);
			g.drawImage(imgCheck, 660, 28, this);
			g.drawImage(imgBet, 800, 28, this);
		}
		else if (oppTurn() && !playerCall && preFlopState)
		{
			g.drawImage(imgFold, 520, 28, this);
			g.drawImage(imgCall, 660, 28, this);
			g.drawImage(imgRaise, 800, 28, this);
		}

		// We need this because the postflop state scenario is different than
		// the pre-flop state scenario
		if (playerTurn() && playerIsDealer && !preFlopState)
		{
			g.drawImage(imgFold, 520, 660, this);
			g.drawImage(imgCheck, 660, 660, this);
			g.drawImage(imgBet, 800, 660, this);
			if (oppBet)
			{
				g.drawImage(imgCall, 660, 660, this);
				g.drawImage(imgRaise, 800, 660, this);
			}
		}
		else if (playerTurn() && !playerIsDealer && !preFlopState)
		{
			g.drawImage(imgFold, 520, 660, this);
			g.drawImage(imgCheck, 660, 660, this);
			g.drawImage(imgBet, 800, 660, this);
			if (oppBet)
			{
				g.drawImage(imgCall, 660, 660, this);
				g.drawImage(imgRaise, 800, 660, this);
			}
		}

		if (oppTurn() && !playerIsDealer && !preFlopState)
		{
			g.drawImage(imgFold, 520, 28, this);
			g.drawImage(imgCheck, 660, 28, this);
			g.drawImage(imgBet, 800, 28, this);
			if (playerBet)
			{
				g.drawImage(imgCall, 660, 28, this);
				g.drawImage(imgRaise, 800, 28, this);
			}
		}
		else if (oppTurn() && playerIsDealer && !preFlopState)
		{
			g.drawImage(imgFold, 520, 28, this);
			g.drawImage(imgCheck, 660, 28, this);
			g.drawImage(imgBet, 800, 28, this);
			if (playerBet)
			{
				g.drawImage(imgCall, 660, 28, this);
				g.drawImage(imgRaise, 800, 28, this);
			}
		}

		Font myFont = new Font("Comic Sans MS", Font.BOLD, 20);
		g.setFont(myFont);
		g.drawString("Stack: " + p1Stack, 110, 695);
		g.drawString("Stack: " + p2Stack, 110, 100);
		g.drawString(p1Name, 110, 660);
		g.drawString(p2Name, 110, 60);
		updatePot();
		g.drawString("" + potSize, 100, 370);
		if (showdownState)
		{
			handEnd = true;
			if (handEnd == true && (p1Stack == 0 || p2Stack == 0))
			{
				gameEnd = true;
			}
			if (_winner != null)
			{
				if (_winner.equals(_p1))
				{
					oppFold();
				}
				else if (_winner.equals(_p2))
				{
					playerFold();
				}
			}
			g.drawString("" + _winningHand, 600, 250); // this also accounts for
														// a tie
		}

		if (someoneFolded)
		{
			handEnd = true;
			if (handEnd == true && (p1Stack == 0 || p2Stack == 0))
			{
				gameEnd = true;
			}
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

	public void playerFold()
	{
		p2Stack += potSize;
		preFlopState = false;
		postFlopState = false;
		turnState = false;
		riverState = false;
		updatePot();
		someoneFolded = true;
		_winner = _p2;
	}

	public void oppFold()
	{
		p1Stack += potSize;
		preFlopState = false;
		postFlopState = false;
		turnState = false;
		riverState = false;
		updatePot();
		someoneFolded = true;
		_winner = _p1;
	}

	public void preFlopPT()
	{
		if (playerFold)
		{
			playerFold();
		}
		if (playerCall)
		{
			if (messedUpOppCall == false && playerIsDealer == false)
			{
				p1Stack++;
			}
			if (playerIsDealer)
			{
				didDealerCall = true;
			}
			p1Stack -= oppBetSize;
			if (oppBetSize == 1) // First call, then pass play onto big blind
			{
				setPlayerTurn(false);
			}
			else
			{
				p1Stack += playerRaiseRange;
				if (numReRaise >= 2)
				{
					p1Stack -= initialOppBet;
				}
				setPlayerTurn(false);
				preFlopState = false;
				postFlopState = true;
				goToFlop();
				resetAllVariables();
			}
		}
		if (playerBet)
		{
			Scanner sc = new Scanner(System.in);
			playerBetSize = sc.nextInt();
			playerRaiseRange = playerBetSize;
			p1Stack -= playerBetSize;
			oppCall = false;
			resetPlayerBet();
			if (playerIsDealer && (didDealerCall == false))
			{
				dealerFirstBetCount++;
			}
		}
		repaint();
	}

	public void preFlopOT()
	{
		if (oppFold)
		{
			oppFold();
		}
		A: if (oppCall)
		{
			messedUpOppCall = true; // exists just for that one chip when
									// opponent raises preflop
			if (playerBetSize == 1)
			{
				p2Stack -= 1;
				setPlayerTurn(true);
				break A;
			}
			p2Stack -= playerBetSize;
			if (dealerFirstBetCount == 1)
			{
				p2Stack += 1;
			}
			p2Stack += oppRaiseRange;
			preFlopState = false;
			postFlopState = true;
			goToFlop();
			resetAllVariables();

			setPlayerTurn(true);
		}

		if (oppBet)
		{
			Scanner sc = new Scanner(System.in);
			oppBetSize = sc.nextInt();
			if (initialCount == 0)
			{
				initialOppBet = oppBetSize;
				initialCount++;
			}
			p2Stack -= oppBetSize;
			oppRaiseRange = oppBetSize;
			playerCall = false;
			resetOppBet();
			numReRaise++;
		}
		repaint();
	}

	public void postFlopPT()
	{
		if (playerFold)
		{
			playerFold();
		}
		if (playerCall && oppBet)
		{
			p1Stack -= oppBetSize;
			setPlayerTurn(false);
			postFlopState = false;
			turnState = true;
			goToTurn();
			resetAllVariables();
		}
		else if (playerCheck)
		{
			playerCheck = true;
			setPlayerTurn(false);
			if (!playerIsDealer)
			{
				postFlopState = false;
				turnState = true;
				goToTurn();
				resetAllVariables();
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

	public void postFlopOT()
	{
		if (oppFold)
		{
			oppFold();
		}
		if (oppCall && playerBet)
		{
			p2Stack -= playerBetSize;
			setPlayerTurn(true);
			postFlopState = false;
			turnState = true;
			goToTurn();
			resetAllVariables();
		}
		else if (oppCheck)
		{
			oppCheck = true;
			setPlayerTurn(true);
			if (playerIsDealer)
			{
				postFlopState = false;
				turnState = true;
				goToTurn();
				resetAllVariables();
			}
		}
		if (oppBet)
		{
			Scanner sc = new Scanner(System.in);
			oppBetSize = sc.nextInt();
			p2Stack -= oppBetSize;
			resetOppBet();
		}

		repaint();
	}

	public void turnStatePT()
	{
		if (playerFold)
		{
			playerFold();
		}
		if (playerCall && oppBet)
		{
			p1Stack -= oppBetSize;
			setPlayerTurn(false);
			turnState = false;
			riverState = true;
			goToRiver();
			resetAllVariables();
		}
		else if (playerCheck)
		{
			playerCheck = true;
			setPlayerTurn(false);
			if (!playerIsDealer)
			{
				turnState = false;
				riverState = true;
				goToRiver();
				resetAllVariables();
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

	public void turnStateOT()
	{
		if (oppFold)
		{
			oppFold();
		}
		if (oppCall && playerBet)
		{
			p2Stack -= playerBetSize;
			setPlayerTurn(true);
			turnState = false;
			riverState = true;
			goToRiver();
			resetAllVariables();
		}
		else if (oppCheck)
		{
			oppCheck = true;
			setPlayerTurn(true);
			if (playerIsDealer)
			{
				turnState = false;
				riverState = true;
				goToRiver();
				resetAllVariables();
			}
		}
		if (oppBet)
		{
			Scanner sc = new Scanner(System.in);
			oppBetSize = sc.nextInt();
			p2Stack -= oppBetSize;
			resetOppBet();
		}

		repaint();
	}

	public void riverStatePT()
	{
		if (playerFold)
		{
			playerFold();
		}
		if (playerCall && oppBet)
		{
			p1Stack -= oppBetSize;
			setPlayerTurn(false);
			riverState = false;
			showdownState = true;
			resetAllVariables();
		}
		else if (playerCheck)
		{
			playerCheck = true;
			setPlayerTurn(false);
			if (!playerIsDealer)
			{
				riverState = false;
				showdownState = true;
				resetAllVariables();
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

	public void riverStateOT()
	{
		if (oppFold)
		{
			oppFold();
		}
		if (oppCall && playerBet)
		{
			p2Stack -= playerBetSize;
			setPlayerTurn(true);
			riverState = false;
			showdownState = true;
			resetAllVariables();
		}
		else if (oppCheck)
		{
			oppCheck = true;
			setPlayerTurn(true);
			if (playerIsDealer)
			{
				riverState = false;
				showdownState = true;
				resetAllVariables();
			}
		}
		if (oppBet)
		{
			Scanner sc = new Scanner(System.in);
			oppBetSize = sc.nextInt();
			p2Stack -= oppBetSize;
			resetOppBet();
		}

		repaint();
	}

	public void resetAllVariables()
	{
		oppBetSize = 0;
		playerBetSize = 0;
		oppRaiseRange = 0;
		playerRaiseRange = 0;
		initialOppBet = 0;
		initialCount = 0;
		initialPlayerBet = 0;
		numReRaise = 0;
		dealerFirstBetCount = 0;
		oppBet = false;
		playerBet = false;
		oppCall = false;
		playerCall = false;
		oppCheck = false;
		playerCheck = false;
	}

	public void resetPlayerBet()
	{
		oppCheck = false;
		playerCheck = false;
		oppBet = false;
		playerBet = true;
		oppBetSize = 0;
		setPlayerTurn(false);
	}

	public void resetOppBet()
	{
		oppCheck = false;
		playerCheck = false;
		playerBet = false;
		oppBet = true;
		playerBetSize = 0;
		setPlayerTurn(true);
	}

	public void adjustOppPot(int n)
	{
		p2Stack += n;
	}

	public void adjustPlayerPot(int n)
	{
		p1Stack += n;
	}

	public void run() throws InterruptedException
	{

		if (playerIsDealer)
		{
			p1Stack -= 1;
			p2Stack -= 2;
		}
		else
		{
			p1Stack -= 2;
			p2Stack -= 1;
		}
		while (true)
		{
			while (preFlopState && playerTurn())
			{
				preFlopPT();
			}
			while (preFlopState && oppTurn())
			{
				preFlopOT();
			}
			while (postFlopState && playerTurn())
			{
				postFlopPT();
			}
			while (postFlopState && oppTurn())
			{
				postFlopOT();
			}
			while (turnState && playerTurn())
			{
				turnStatePT();
			}
			while (turnState && oppTurn())
			{
				turnStateOT();
			}
			while (riverState && playerTurn())
			{
				riverStatePT();
			}
			while (riverState && oppTurn())
			{
				riverStateOT();
			}
			repaint();
			Thread.sleep(17);
			if (handEnd)
			{
				break;
			}
		}

	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		// Player fold
		if (e.getX() > 520 && e.getX() < 640 && e.getY() > 660
				&& e.getY() < 705)
		{
			playerFold = true;
		}
		// Player check/call
		if (e.getX() > 660 && e.getX() < 800 && e.getY() > 660
				&& e.getY() < 705)
		{
			playerCall = true;
			playerCheck = true;
		}
		// Player bet. Simplified so that betting is the same as raising. No min
		// bet
		if (e.getX() > 820 && e.getX() < 860 && e.getY() > 660 & e.getY() < 705)
		{
			playerBet = true;
		}

		// Opponnet fold
		if (e.getX() > 520 && e.getX() < 640 && e.getY() > 28 && e.getY() < 73)
		{
			oppFold = true;
		}
		// Opponent check/call
		if (e.getX() > 660 & e.getX() < 800 && e.getY() > 28 && e.getY() < 73)
		{
			oppCall = true;
			oppCheck = true;
		}
		// Opponent bet/raise. Simplified so that betting is the same as
		// raising. No min bet
		if (e.getX() > 820 && e.getX() < 860 && e.getY() > 28 && e.getY() < 73)
		{
			oppBet = true;
		}

		// SAVE ME. I MADE A MISTAKE IN MY STATE. ADJUST THE POT SIZE
		if (e.getX() > 100 & e.getX() < 200 && e.getY() > 133 && e.getY() < 170)
		{
			Scanner sc = new Scanner(System.in);
			int adjust = sc.nextInt();
			adjustOppPot(adjust);
			repaint();
		}
		if (e.getX() > 100 && e.getX() < 200 && e.getY() > 735
				&& e.getY() < 772)
		{
			Scanner sc = new Scanner(System.in);
			int adjust = sc.nextInt();
			adjustPlayerPot(adjust);
			repaint();
		}

		// SHOW MA CARDS BUDDAY
		if (e.getX() > 280 && e.getX() < 430 && e.getY() > 165
				&& e.getY() < 229)
		{
			hideOppCards = !hideOppCards;
			repaint();
		}
		if (e.getX() > 280 && e.getX() < 430 && e.getY() > 560
				&& e.getY() < 624)
		{
			hidePlayerCards = !hidePlayerCards;
			repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

}
