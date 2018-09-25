package org.geom.swing;


import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import java.util.Random;


/**SwingGame class, generates a game frame based on the game options (map, difficulty) selected in the main menu. One game including 10 questions. After the final question the player gets a game result and returns to the main menu.
 * @author martin.walter
 * @since 1.0.0
 */
public class SwingGame extends javax.swing.JFrame implements MouseListener {
	private static final long serialVersionUID = 9069118554859464282L;
	//public  Toolkit t;
	public int  x = 0 , y = 0 , width = 1400, height = 900;				// window variables
	public int numberOfRounds = 10;
	public int currentRound = 1;
	public int xKoor = 0;												// Variable x for calculation
	public int yKoor = 0;												// Variable y for calculation
	public int deltax = 0;
	public int deltay =0;												// help-variables for distance calculation (px)
	public int  distancepx = 0;											// variables for distance calculation (px)
	public int  distancekm = 0;											// variables for distance calculation (km)
	public int score = 0;												// score of one round
	public int scoreTotal = 0;											// sum of each single round
	public int scoreMax = 0;											// sum of maximal reachable points
	public int tolerance = 2; 											// tolerance in px (Radius) for points calculation
	private int[] usedNumbers = new int[numberOfRounds];
	
	private boolean hasNotClicked = true;								// help-variable mouse click for calculation
	private boolean gameHasStarted = false;
	private boolean nextRound = true;
	
	public JLabel markerBlue = new JLabel(new ImageIcon(SwingMain.class.getResource("Marker_Blue.png")));		// search-Marker
	public JLabel markerRed = new JLabel(new ImageIcon(SwingMain.class.getResource("Marker_Red.png")));			// target-Marker
		
	public JButton mainmenuButton;										// "MainMenü" Button initialization
	public JButton closeButton;											// "Beenden" Button initialization
	public JButton checkButton;											// "Spiel" Button for different actions during game 
	
	public JTextPane gameDataQuestionNumber = new JTextPane();			// TextBox output question number
	public JTextPane gameDataQuestion = new JTextPane();				// TextBox output question
	public JTextPane gameDataEvaluationDistance = new JTextPane();		// TextBox output distance
	public JTextPane gameDataEvaluationScore = new JTextPane();			// TextBox output points
	
	public JOptionPane finalEvaluation = new JOptionPane();				// DialogWindow for final score
		
	Font font = new Font("Arial", Font.PLAIN, 16);
	   
	private void fillUsedNumberArrayWithDefault() {
		for (int index = 0; index < this.numberOfRounds; index++) {
			this.usedNumbers[index] = -1; 								// Array for used cities form list
		}
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {							// Method mouse was clicked 
			if (e.getX() < 1000) {										// Marker placement just outside the game menu
				int x = e.getX();
				int y = e.getY();
				System.out.println("Mouse Clicked at X: " + x + " - Y: " + y);
				this.xKoor = x;													// transfer values
				this.yKoor = y;
				if (checkButton.getText() != "N�chste Runde") {					// check round progress
					if (hasNotClicked) {									// avoid multiple clicking
						this.hasNotClicked = false;								
						if ( gameHasStarted) {								// check if game is already started
							this.markerBlue.setVisible(true);					
							this.markerBlue.setLocation(x - 13, y - 43);	// put search-Marker on clicked position
						}
					} else {
						this.markerBlue.setLocation(x - 13, y - 43);		// put search-Marker on clicked position
					}
				}
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) { 							// Method move mouse in client area
		
	}
	@Override
	public void mouseExited(MouseEvent e) { 							// Method mouse leaves the client area
       
	}
	@Override
	public void mousePressed(MouseEvent e) { 							// Method mouse button was pressed
		
	}
	@Override
	public void mouseReleased(MouseEvent e) { 							// Method mouse button was released
		
	}
        
	
	/**Method, creating the question-content for each round
	 * @param cities
	 */
	public void gameAction(String[][] cities) {
		if (currentRound == 1) {
			gameHasStarted = true;
		}
		String roundString =  Integer.toString(currentRound);
		
		gameDataQuestionNumber.setText(" \n Frage " + roundString + ": ");
		gameDataQuestion.setText(" Wo befindet sich " + cities[usedNumbers[currentRound - 1]][0] + "?");
		
		System.out.println("Frage " + roundString + ": ");
		System.out.println("Wo befindet sich " + cities[usedNumbers[currentRound - 1]][0] + "?");
		System.out.println("Bitte Position wählen!");
		
		hasNotClicked = true;
	}
	
	/**Method calculating distances and points
	 * @param xTarget: x-coordinate target
	 * @param yTarget: x-coordinate target
	 * @param pxKmMulti: calculation factor
	 */
	public void calcScore(int xTarget, int yTarget, int pxKmMulti) {
		
		System.out.println(xTarget);
		System.out.println(yTarget);
		System.out.println(xKoor);
		System.out.println(yKoor);
		
		markerRed.setVisible(true);								
		markerRed.setLocation(xTarget - 13, yTarget - 43);			// Put target-Marker on target position
		
		// Calculation distances
		deltax = Math.abs(xTarget - xKoor);
		deltay = Math.abs(yTarget - yKoor);
		distancepx = (int) Math.sqrt(Math.pow(deltax, 2) + Math.pow(deltay, 2));	// calculation distance in pixels (Pythagoras)		
		distancekm = distancepx * pxKmMulti;										// recalculation in km
		
		// Calculation points
		if (distancepx <= tolerance) {					// Including tolerance in score calculation
			score = 100;								// maximal score is not  because of tolerance			
		} else {
			score = 100 + tolerance - distancepx;
		}
		
		if (score < 0) {								// avoid negative scores
			score = 0;
		}
		scoreTotal = scoreTotal + score;				// calculation final score
		scoreMax = scoreMax + 100;						// calculation final possible score
		
		// Output distances and points in text box
		gameDataEvaluationDistance.setText("\n Die Entfernung beträgt: " + distancekm + " km");
		gameDataEvaluationScore.setText(" Diese Runde: " + score + " / 100 Punkte \n Insgesammt:  " + scoreTotal + " / " + scoreMax + " Punkte");
		
		System.out.println(distancepx + " px");		
		System.out.println(distancekm + " km");
		System.out.println(score + "/100 Punkte");
		System.out.println(scoreTotal + "/" + scoreMax + " Punkte");
	}
	
	/**Method, creating final game result (reached points/possible points). Results are visible in a dialoge box. Afterwards the program returns to the main menu.
	 * 
	 */
	public void finalResult() {										
		
		gameHasStarted = false;										// =false: game is finished
		markerBlue.setVisible(false);								// set Markers invisible
		markerRed.setVisible(false);
		
		// Ausgabe Dialog Spielauswertung 
		finalEvaluation.showMessageDialog(null, "\n Spiel beendet! " + scoreTotal + " von " + scoreMax + " möglichen Punkten \n ", "Auswertung", JOptionPane.INFORMATION_MESSAGE, SwingMain.iconGeoMasterDialog);
		System.out.println("Spiel beendet! \" + scoreTotal + \" von \" + scoreMax + \" möglichen Punkten");
		
		// zur�ck zum Hauptmen�
		final SwingMain swingMain = new SwingMain ();				// class of MainMenu
		swingMain.setVisible(true);									// class not visible
		dispose ();													// close current window
	}
	
	
	/**Method, select data sets from CityArray randomly
	 * @param round: current game round
	 * @author martin.walter
	 * @since 1.0.0
	 */
	public void setEntryNumber(int round, String[][] cities) {
		
		Random rNumber = new Random(); 								// new random object
		int randomNumber = rNumber.nextInt(cities.length); 			// random int between 0 & the array length
		
		boolean wasNotEqual = true;									// Variable "NotYetUsed"
		while (wasNotEqual) {
			randomNumber = rNumber.nextInt(cities.length); 			// random int between 0 & the array length
			
			for (int usedNumberIndex = 0; usedNumberIndex < numberOfRounds ; usedNumberIndex++) {
				if (usedNumbers[usedNumberIndex] == randomNumber) {
					wasNotEqual = false;
				}
			}
			if (wasNotEqual) {
				usedNumbers[round-1] = randomNumber;
				wasNotEqual = false;
			} else {
				wasNotEqual = true;
			}
		}
	}
			
		        
		
	/**Constructor, generates a gaming-window based on the options selected in the MainMenu (map, difficulty)
	 * @param map: in MainMenu selected map (GER/EU)
	 * @param level: in MainMenu selected difficulty
	 * @throws ClassNotFoundException
	 * @author christoph.herrmann
	 * @since 1.0.0
	 */
	public SwingGame(String map, int level) throws ClassNotFoundException {
		SwingMain.toolkitForWindow = Toolkit.getDefaultToolkit();		
		
		Dimension d =SwingMain.toolkitForWindow.getScreenSize();		// get screen resolution information
		x = (int) ((d.getWidth() - width) / 2);							// calculation: open window in central of window
		y = (int) ((d.getHeight() - height)  / 2);
		
		CSVReader daten = new CSVReader(map, level);					// take game-data from CSVReader
		// System.out.println(daten.cities[0][0]);
		
		fillUsedNumberArrayWithDefault();
		
		for (int round = 1; round <= this.numberOfRounds; round++) {
	    	 setEntryNumber(round, daten.cities);
	    }
		
		setTitle ("GEO Master");						// "Window name"
	    setBounds(x, y, width, height);					// window size and position

	    JLabel background = new JLabel();				// define background
	    setLayout(new BorderLayout());					// Layout for background		
		switch (map) {									// Switch for map selection, comparison with selection in ComboBox in MainMenu
			case " - Deutschland ":	
				background = new JLabel(new ImageIcon(getClass().getResource("GameBackroundGER_1400x870.png")));
				break;
			case " - Europa ":
				background = new JLabel(new ImageIcon(getClass().getResource("GameBackroundEUR_1400x870.png")));
				break;
		}
	    
		add(background);							// paste background
		background.setLayout(null);					// Layout for further elements "null", all elements must be placed manual
	    
		
		background.add(markerBlue);					// search-Marker
		markerBlue.setBounds(0,0,27,43);
		markerBlue.setVisible(false);				// not visible yet
		
		background.add(markerRed);					// target-Marker
		markerRed.setBounds(0,0,27,43);
		markerRed.setVisible(false);				// not visible yet
		
		gameDataQuestionNumber.setBounds(1060, 350, 300, 30);			// Text field  output question number - Position & size
		gameDataQuestionNumber.setText("");								// empty for the moment
		gameDataQuestionNumber.setFont(font);							// font & size as defined
		gameDataQuestionNumber.setBackground(getForeground());			// Background color of the text box
		gameDataQuestionNumber.setEditable(false);						// Box is not editable
		background.add(gameDataQuestionNumber);							
				
		gameDataQuestion.setBounds(1060, 380, 300, 50);					
		gameDataQuestion.setText("");									
		gameDataQuestion.setFont(font);									
		gameDataQuestion.setBackground(getForeground());				
		gameDataQuestion.setEditable(false);							
		background.add(gameDataQuestion);
		
		gameDataEvaluationDistance.setBounds(1060, 450, 300, 50);		
		gameDataEvaluationDistance.setText("");							
		gameDataEvaluationDistance.setFont(font);						
		gameDataEvaluationDistance.setBackground(getForeground());		
		gameDataEvaluationDistance.setEditable(false);					
		background.add(gameDataEvaluationDistance);
		
		gameDataEvaluationScore.setBounds(1060, 500, 300, 80);			
		gameDataEvaluationScore.setText("");							
		gameDataEvaluationScore.setFont(font);							
		gameDataEvaluationScore.setBackground(getForeground());			
		gameDataEvaluationScore.setEditable(false);						
		background.add(gameDataEvaluationScore);	
		
		
		
		
		
		mainmenuButton = new JButton("Hauptmenü");						// Button back to MainMenu
		mainmenuButton.setBounds(1060, 800, 140, 50);					// Position and size of Home bottom
		background.add(mainmenuButton);
		
		mainmenuButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {				// ActionListener MainMenu bottom
				final SwingMain sm = new SwingMain ();					// MainMenu class
				sm.setVisible(true);									// class getting visible
				dispose ();												// closing old window
				}
			});
		
		closeButton = new JButton("Beenden");							// Button to finish the game
		closeButton.setBounds(1220, 800, 140, 50);						// Position and size of closeBottom
		background.add(closeButton);
		
		closeButton.addActionListener(new ActionListener() {			// ActionListener, pressing the bottom ends the game
			@Override
			public void actionPerformed(ActionEvent e) {
			dispose ();
			}
		});
		
		checkButton = new JButton("Spiel starten!");					// Button for game steps
		checkButton.setBounds(1060, 600, 300, 100);						// Position & size of checkButton
		background.add(checkButton);
		
		checkButton.addActionListener(new ActionListener() {					// ActionListener: waiting for pressing of startButton
			@Override
			public void actionPerformed(ActionEvent e) {						// Press button
				if ((!hasNotClicked || currentRound == 1) && nextRound) {		// check if marker is placed  or game is started and if in final score or not
					markerBlue.setVisible(false);								// delete markers
					markerRed.setVisible(false);
					if (currentRound <= numberOfRounds) {						// check if game is finished
						checkButton.setText("Weiter...");						// change label of checkButton
						gameDataEvaluationDistance.setText("");					// clear text field and score
						gameDataEvaluationScore.setText("");
						gameAction(daten.cities);								
						gameDataQuestionNumber.setText("Frage " + currentRound + ": ");
						gameDataQuestion.setText("Wo befindet sich " + daten.cities[usedNumbers[currentRound - 1]][0] + "?");
						nextRound = false;										// Variable=false to start final calculation
						checkButton.setText("Wertung");
						
						currentRound++;
					} else {												// final result if game is finished
						finalResult();
					}
				} else {													// Marker not placed or not next round
					if (!nextRound && !hasNotClicked) {						// check if marker is not placed and if there is no next round started
						calcScore(Integer.parseInt(daten.cities[usedNumbers[currentRound - 2]][1]), Integer.parseInt(daten.cities[usedNumbers[currentRound - 2]][2]), Integer.parseInt(daten.cities[usedNumbers[currentRound - 2]][4]));
						nextRound = true;									// Variable= true to go to gameAction
						if (currentRound > numberOfRounds) {				// put Button Label based on game progress
							checkButton.setText("Auswertung");				// Game finished: final score, otherwise next round
						} else {
							checkButton.setText("Nächste Runde");						
						}
					}
				}
		   }
		});
		
		
		background.addMouseListener(this);								// Add MouseListener to background
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 				// Closing complete application
	    setResizable(false);   											// fixed window size
	    setVisible(true);												// window getting visible
	    setIconImage(SwingMain.iconGeoMaster.getImage());				// Chance window icon   
   	}
}
