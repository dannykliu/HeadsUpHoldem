import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/*
 * Next steps: Implement mouse/key listener
 * Have method call to bring onto next "turn" of game
 */
public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		Player p1 = new Player("Player");
		Player p2 = new Player("Opponent");
		Deck deck = new Deck();
				
		//Create new JFrame
        JFrame myFrame = new JFrame();
        //Close panel upon exist
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //Make the frame visible
        myFrame.setVisible(true);
        //Sets size of frame
        myFrame.setSize(1000, 800);
        //Sets name of game
        myFrame.setTitle("Heads-Up Turbo");
        //Makes sure that the player cannot change the dimensions
        myFrame.setResizable(false);
        //Makes my program always on top of other screens

        //Set dimensions of board
        Board board = new Board(p1, p2, deck);
        board.setSize(1000,800);
        board.setVisible(true);
        myFrame.setContentPane(board);
        
        //Get focus into your game panel
        board.setFocusable(true);
        board.requestFocus();
        
        //run the program 
        board.run();
        //pause the thread so that you can see your hand for 5 seconds
        Thread.sleep(5000);
        //Adding cards from the players hand back to the original deck.
        //REMEMBER WE ONLY EVER CREATE ONE DECK
      	deck.addCard(p1.getCard1());
    	deck.addCard(p1.getCard2());
    	deck.addCard(p2.getCard1());
    	deck.addCard(p2.getCard2());
        myFrame.dispose();
        while(!board.gameEnd)
        {
        	Hand p1Hand = new Hand();
        	Hand p2Hand = new Hand();
    		p1.setCards(p1Hand.getHand1(), p1Hand.getHand2());
    		p2.setCards(p2Hand.getHand1(), p2Hand.getHand2());
        	myFrame = new JFrame();
            myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            myFrame.setVisible(true);
            myFrame.setSize(1000, 800);
            myFrame.setTitle("Heads-Up Turbo");
            myFrame.setResizable(false);
            p1.setStack(board.p1Stack);
            p2.setStack(board.p2Stack);
            p1.changeDealer(!board.playerIsDealer);
            board = new Board(p1, p2, deck);
            board.setSize(1000,800);
            board.setVisible(true);
            myFrame.setContentPane(board);
            board.setFocusable(true);
            board.requestFocus();
            board.run();
            Thread.sleep(5000);
            deck.addCard(p1.getCard1());
        	deck.addCard(p1.getCard2());
        	deck.addCard(p2.getCard1());
        	deck.addCard(p2.getCard2());
            myFrame.dispose();
        }
        
        
	}
	
	

}
