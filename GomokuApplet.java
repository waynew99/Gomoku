// CS 201 Final Project - Gomoku
// Yichen Yang / Wayne Wang

// Extends Applet and Implements ActionListener
// Draws the applet containing all buttons, labels and the central canvas

import java.applet.*;
import java.awt.*;
import java.awt.event.*;


public class GomokuApplet extends Applet implements ActionListener{

	// Instance variables
	BoardCanvas c;
	Button newGameButton, hintButton, helpButton, blackButton, whiteButton;
	Color dblue = new Color(154, 188, 255);
	Color dblue2 = new Color(0, 128, 255);
	Color dgreen = new Color(102, 204, 0);
	Color dyellow = new Color(255, 255, 102);
	Color darkGrey = new Color(108, 108, 108);
	Color lightGrey = new Color(192, 192, 192);
	Color grey = new Color(204, 229, 255);

	
	// Constructor
	public void init() {
		// font to use in the buttons
		setFont(new Font("TimesRoman", Font.BOLD, 30));

		setLayout(new BorderLayout());
		// initialize newGameButton
		newGameButton = new Button("New Game");
		newGameButton.setBackground(dgreen);
		newGameButton.addActionListener(this);
		// initialize hintButton
		hintButton = new Button("Hint!");
		hintButton.setBackground(dyellow);
		hintButton.addActionListener(this);
		// initialize helpButton
		helpButton = new Button("How to play");
		helpButton.setBackground(dblue2);
		helpButton.addActionListener(this);
		// toggle black AI
		blackButton = new Button("Toggle Black AI");
		blackButton.setBackground(darkGrey);
		blackButton.addActionListener(this);
		// toggle white AI
		whiteButton = new Button("Toggle White AI");
		whiteButton.setBackground(lightGrey);
		whiteButton.addActionListener(this);
		
		// put five buttons in a panel
		Panel button1 = new Panel();
		button1.setBackground(Color.white);
		button1.add(newGameButton);
		Panel button2 = new Panel();
		button2.setBackground(Color.white);
		button2.add(hintButton);
		Panel button3 = new Panel();
		button3.setBackground(Color.white);
		button3.add(helpButton);
		Panel button4 = new Panel();
		button4.setBackground(Color.white);
		button4.add(blackButton);
		Panel button5 = new Panel();
		button5.setBackground(Color.white);
		button5.add(whiteButton);
		
		Panel buttons = new Panel();
		buttons.setBackground(Color.white);
		buttons.setLayout(new GridLayout(5,1));
		buttons.add(button1);
		buttons.add(button2);
		buttons.add(button3);
		buttons.add(button4);
		buttons.add(button5);
		
		// initialize center canvas
		c = new BoardCanvas();
		c.addMouseListener(c);
		c.addMouseMotionListener(c);
		c.setBackground(dblue);

		// put everything in the boarderLayout
		add("North", makeTitleLabel());
		add("Center", c);
		add("East", buttons);
	}


	// implement actionListener
	// direct button presses to button functions
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == newGameButton) {
			c.blackWhite = true;
			c.victory = false;
			c.blackAi = false;
			c.whiteAi = false;
			c.clear();
			System.out.println("newGameButton pressed");
		} else if (e.getSource() == hintButton){
			c.hintPressed();
        	System.out.println("hintButton pressed");
		} else if (e.getSource() == helpButton) {
        	c.helpPressed();
        	System.out.println("helpButton pressed");
		} else if (e.getSource() == blackButton) {
			c.blackAiPressed();
			System.out.println("Black AI toggled");
		} else if (e.getSource() == whiteButton) {
			c.whiteAiPressed();
			System.out.println("White AI toggled");
		}
	}


	// creates the "Gomoku" title at the top of the screen
	private Label makeTitleLabel() {
		Label titleLabel = new Label("GOMOKU!");
        titleLabel.setAlignment(Label.CENTER);
        titleLabel.setBackground(Color.black);
        titleLabel.setForeground(Color.white);
        return titleLabel;
	}
}
