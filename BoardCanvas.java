// CS 201 Final Project - Gomoku
// Yichen Yang / Wayne Wang

// Extends Canvas and Implements MouseListener, MouseMotionListener
// Draws the board interface on the central canvas, inputing mouse actions

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


// subclass BoardCanvas that draws a circle along the mouse, as well as the board background
public class BoardCanvas extends Canvas implements MouseListener, MouseMotionListener {

	Color dblue = new Color(154, 188, 255);
	int x = 0; 
	int y = 0;
	boolean blackWhite = true; // color of circle (true = black, false = white)
	Data current = new Data();
	boolean victory = false;
	boolean hintOn = false;
	boolean helpOn = false;
	boolean blackAi = false;
	boolean whiteAi = false;
	String helpDoc = "Two players put down pieces alternatively.";
	String helpDoc1 = "When pieces of one player line up in five";
	String helpDoc2 = " or more, the player wins.";
	String helpDoc3 = "You can call this helpDoc by pressing the";
	String helpDoc4 = "help button at anytime.";


	// This method is called by Java when the window is changed (e.g.,
	// uncovered or resized), or when "repaint()" is called.
	public void paint(Graphics g) {
		// draw the grid
		for(int i=1; i<=15; i++) {
			g.drawLine(40, 40*i, 600, 40*i); // horizontal lines
			g.drawLine(40*i, 40, 40*i, 600); // vertical lines
		}
		g.setColor(Color.black);
		g.fillOval(160-4, 160-4, 8, 8);
		g.fillOval(480-4, 480-4, 8, 8);
		g.fillOval(480-4, 160-4, 8, 8);
		g.fillOval(160-4, 480-4, 8, 8);
		g.fillOval(320-4, 320-4, 8, 8);

		// draw the current data(all of the played pieces)
		for (int i=0; i<15; i++) {
			for (int j=0; j<15; j++) {
				if (current.get(i, j) == 1) {
					g.setColor(Color.black);
					g.fillOval(GridToAct(i)-15, GridToAct(j)-15, 30, 30);
				} else if (current.get(i, j) == 2) {
					g.setColor(Color.white);
					g.fillOval(GridToAct(i)-15, GridToAct(j)-15, 30, 30);
				}
			}
		}

		// shows the position of the hint if hint is requested
		if (hintOn) {
			int[] hint = current.getHint(blackWhite);
			g.setColor(Color.red);
			g.fillOval(GridToAct(hint[0])-15, GridToAct(hint[1])-15, 30, 30);
		}

		// shows the game rules if the game rules(help/how to play) is requested
		if (helpOn) {
			g.setColor(Color.white);
			g.fillRect(40,120,560,400);
			g.setColor(Color.black);
			g.drawString(helpDoc, 60, 200);
			g.drawString(helpDoc1, 60, 250);
			g.drawString(helpDoc2, 60, 300);
			g.drawString(helpDoc3, 60, 350);
			g.drawString(helpDoc4, 60, 400);
		}

		// print the info section
		printInfo();

	}

	
	// prints the information panel that includes the next player and the status of the AIs
	public void printInfo() {
		Graphics g = getGraphics();
		if (victory) {
			// prints the winning player if the game is finished
			System.out.println("win");
			g.setColor(dblue);
			g.fillRect(200, 622, 250, 36);
			String currentColor = blackWhite ? "Black" : "White";
			if (blackWhite) g.setColor(Color.black);
			else g.setColor(Color.white);
			g.drawString(currentColor + " Wins!!", 250, 650);
		} else {
			// Prints the AIs' status (on/off)
			g.setFont(new Font("TimesRoman", Font.BOLD, 24));
			g.setColor(dblue);
			g.fillRect(0, 622, 900, 36);
			g.setColor(Color.black);
			g.drawString("Next Player: ", 225, 650);
			String blackAiStatus = blackAi ? "On" : "Off";
			String whiteAiStatus = whiteAi ? "On" : "Off";
			g.drawString("Black Ai: " + blackAiStatus, 25, 650);
			g.drawString("White Ai: " + whiteAiStatus, 460, 650);
			if (blackWhite) g.setColor(Color.black);
			else g.setColor(Color.white);
			g.fillOval(365, 625, 30, 30);
		}
	}



	// draw the current piece
	public void drawPiece(int i, int j) {
		Graphics g = getGraphics();
		if (blackWhite) {
			g.setColor(Color.black);
			g.fillOval(GridToAct(i)-15, GridToAct(j)-15, 30, 30);
		} else {
			g.setColor(Color.white);
			g.fillOval(GridToAct(i)-15, GridToAct(j)-15, 30, 30);
		}
	}


	// draws a piece in a way that has a flash of red when drawn
	public void flashDraw(int xCoor, int yCoor) {
		Graphics g = getGraphics();
		if (blackWhite) {
			for(int i=0; i<250; i++) {
				g.setColor(Color.red);
				g.fillOval(GridToAct(xCoor)-15, GridToAct(yCoor)-15, 30, 30);
				g.setColor(Color.black);
				g.fillOval(GridToAct(xCoor)-15, GridToAct(yCoor)-15, 30, 30);
			}
		} else {
			for (int i=0; i<250; i++) {
				g.setColor(Color.red);
				g.fillOval(GridToAct(xCoor)-15, GridToAct(yCoor)-15, 30, 30);
				g.setColor(Color.white);
				g.fillOval(GridToAct(xCoor)-15, GridToAct(yCoor)-15, 30, 30);
			}
		}
	}


	// draws color circle where mouse is pressed
	public void mousePressed(MouseEvent event) {
		System.out.println("mousepressed: " + x + y);
		Point p = event.getPoint();
		x = p.x;
		y = p.y;
		int i = ActToGrid(x);	// determine the grid coordinate of the piece
		int j = ActToGrid(y);
		// if the spot is empty and the game is not won, put the piece down at the spot and draw it
		if (inBoundary(i, j) && !victory && current.get(i, j) == 0) {
			current.set(i, j, blackWhite);
			drawPiece(i, j);
			victory = checkVictory();
			if (!victory) blackWhite = ! blackWhite;
			printInfo();
			if (hintOn) hintPressed();
			if (helpOn) helpPressed();

			// checks if there should be an auto play to follow and plays the next play if there is
			autoPlay();
		}
	}

	
	public void mouseReleased(MouseEvent event) { }
	public void mouseClicked(MouseEvent event) { }
	public void mouseEntered(MouseEvent event) { }
	public void mouseExited(MouseEvent event) { }
	public void mouseDragged(MouseEvent event)  { }
	public void mouseMoved(MouseEvent event) { }

	// help function that turn an actually coordinate into grid coordinate
	public int ActToGrid(int i) {
		return ((i - 20) / 40);
	}

	
	// and vice versa
	public int GridToAct(int i) {
		return (i * 40 + 40);
	}

	
	// check if a position is in the boundary of the grid
	public boolean inBoundary(int i, int j) {
		return i<15 && j<15;
	}

	
	// clean up the board and erase winning text, used for the new game button
	public void clear() {
		current.clear();
		repaint();
	}

	
	// check if one player has won the game
	public boolean checkVictory() {
		return current.checkVictory(blackWhite);
	}

	
	// toggles the hint status when the hint button is pressed
	public void hintPressed() {
		hintOn = ! hintOn;
		repaint();
	}

	
	// toggles the how to play texts when the hint is pressed
	public void helpPressed() {
		helpOn = ! helpOn;
		repaint();
	}

	
	// toggles the black AI when the black AI button is pressed
	public void blackAiPressed() {
		blackAi = !blackAi;
		repaint();
		printInfo();
		autoPlay();
	}

	
	// toggles the white AI when the white AI button is pressed
	public void whiteAiPressed() {
		whiteAi = !whiteAi;
		repaint();
		printInfo();
		autoPlay();
	}


	// a helper function that automatically plays the next step
	public void autoNext() {
		int[] nextPlay = current.getHint(blackWhite);
		int xCoor = nextPlay[0];
		int yCoor = nextPlay[1];
		current.set(xCoor, yCoor, blackWhite);
		flashDraw(xCoor, yCoor);
		victory = checkVictory();
		printInfo();
		blackWhite = !blackWhite;
	}


	// checks the status of the game and calls the autoNext function when needed to automatically play the game
	public void autoPlay() {
		while (!victory) {
			Graphics g = getGraphics();

			// checks if the black AI should play
			if ((current.searchColor(true) == current.searchColor(false)) && blackAi) {
				autoNext();
			}
			// checks if the white AI should play
			if ((current.searchColor(true) > current.searchColor(false)) && whiteAi) {
				autoNext();
			}

			if (!(blackAi && whiteAi)) return;
			if ((current.searchColor(true) + current.searchColor(false)) == 225) return;
		}
	}
}
