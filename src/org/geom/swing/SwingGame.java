package org.geom.swing;


//~~~~~~~~~~~~~~ Spielfenster ~~~~~~~~~~~~~~//


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

public class SwingGame extends javax.swing.JFrame implements MouseListener {
	private static final long serialVersionUID = 9069118554859464282L;
	//public  Toolkit t;
	public int  x = 0 , y = 0 , width = 1400, height = 900;				// Fenster-Variablen
	public int numberOfRounds = 10;
	public int currentRound = 1;
	public int xKoor = 0;												// Variable x für Berechnug
	public int yKoor = 0;												// Variable y für Berechnug
	public int deltax = 0;
	public int deltay =0;												// Hilfsvariablen zu Berechnung der Entfernung (px)
	public int  distancepx = 0;											// Variable zur Berechnung der Entfernung in px
	public int  distancekm = 0;											// Variable zur Berechnung der Entfernung in km
	public int score = 0;												// Punktzahl der einzelnen Runden
	public int scoreTotal = 0;											// Summe der Punkte der einzelnen Runden
	public int scoreMax = 0;											// Summe der maximal erreichbaren Punkte
	public int toleranz = 2; 											// Toleranzbereich in px (Radius) für Berechnung der Punkte
	private int[] usedNumbers = new int[numberOfRounds];
	
	private boolean hasNotClicked = true;								// Hilfsvariable Mausklick wurde für Berechnung
	private boolean gameHasStarted = false;
	private boolean nextRound = true;
	
	public JLabel markerBlue = new JLabel(new ImageIcon(SwingMain.userDir + "\\src\\org\\geom\\swing\\Marker_Blue.png"));		// Such-Marker
	public JLabel markerRed = new JLabel(new ImageIcon(SwingMain.userDir + "\\src\\org\\geom\\swing\\Marker_Red.png"));			// Ziel-Marker
		
	public JButton mainmenuButton;										// "MainMenü" Button Initialisierung
	public JButton beendenButton;										// "Beenden" Button Initialisierung
	public JButton checkButton;											// "Spiel" Button zu Durchführen verschiedener Aktionen im Spielverlauf 
	
	public JTextPane gameDataQuestionNumber = new JTextPane();			// TextBox Ausgabe Frage Nummer
	public JTextPane gameDataQuestion = new JTextPane();				// TextBox Ausgabe Frage
	public JTextPane gameDataEvaluationDistance = new JTextPane();		// TextBox Ausgabe Entfernung
	public JTextPane gameDataEvaluationScore = new JTextPane();			// TextBox Ausgabe Punkte
	
	public JOptionPane finalEvaluation = new JOptionPane();				// DialogFEnster für Gesamtauswertung
		
	Font font = new Font("Arial", Font.PLAIN, 16);
	   
	//public ImageIcon iconGeoMaster = new ImageIcon(SwingMain.userDir + "\\src\\org\\geom\\swing\\Logo_v1.1_50x45.png");
	
	
	private void fillUsedNumberArrayWithDefault() {
		for (int index = 0; index < this.numberOfRounds; index++) {
			this.usedNumbers[index] = -1; 								// Array für benutze Städte aus Liste 
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {							//  Methode Maus wurde gedrückt und wieder losgelassen
		int x = e.getX();
		int y = e.getY();
		System.out.println("Mouse Clicked at X: " + x + " - Y: " + y);
		this.xKoor = x;													// Werte werden übergeben
		this.yKoor = y;
		if (checkButton.getText() != "Nächste Runde") {					// prüft Rundenfortschritt
			if (hasNotClicked) {										// Verhinderung von Mehrfachdrücken
				this.hasNotClicked = false;								
				if ( gameHasStarted) {									// prüft ob Spiel gestartet
					this.markerBlue.setVisible(true);					
					this.markerBlue.setLocation(x - 13, y - 43);		// Such-Marker wird auf die geklickte Position gesetzt
				}
			} else {
				this.markerBlue.setLocation(x - 13, y - 43);			// Such-Marker wird auf die geklickte Position gesetzt
			}
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) { 							// Methode Maus in den Clientbereich bewegt
		//int x = e.getX();
		//int y = e.getY();
        //System.out.println("Mouse Entered at X: " + x + " - Y: " + y);
	}
	@Override
	public void mouseExited(MouseEvent e) { 							// Methode Maus verlässt den Clientbereich
        //int x = e.getX();
        //int y = e.getY();
        //System.out.println("Mouse Exited at X: " + x + " - Y: " + y);
	}
	@Override
	public void mousePressed(MouseEvent e) { 							// Methode Maustaste wurde gedrückt
		//int x = e.getX();
        //int y = e.getY();
		//System.out.println("Mouse Pressed at X: " + x + " - Y: " + y);
	}
	@Override
	public void mouseReleased(MouseEvent e) { 							// Methode Maustaste wurde losgelassen
		//int x = e.getX();
        //int y = e.getY();
		//System.out.println("Mouse Released at X: " + x + " - Y: " + y);
	}
        
	
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
	
	// Berechnung der Entfernung und der Punkte
	public void calcScore(int xTarget, int yTarget, int pxKmMulti) {
		
		System.out.println(xTarget);
		System.out.println(yTarget);
		System.out.println(xKoor);
		System.out.println(yKoor);
		
		markerRed.setVisible(true);								
		markerRed.setLocation(xTarget - 13, yTarget - 43);			// Ziel-Marker wird auf Zielposition gesetzt 
		
		// Berechnung Entfernung
		deltax = Math.abs(xTarget - xKoor);
		deltay = Math.abs(yTarget - yKoor);
		distancepx = (int) Math.sqrt(Math.pow(deltax, 2) + Math.pow(deltay, 2));	// Berechnung der Entfernug in Pixeln (Pythagoras)		
		distancekm = distancepx * pxKmMulti;										// Umrechnung in km
		
		// Berechnung Punkte
		if (distancepx <= toleranz) {					// Einbeziehung des Toleranzbereichs in die Punkteberechnung
			score = 100;								// maximal Punktzahl wird durch die Toleranz nicht erhöht				
		} else {
			score = 100 + toleranz - distancepx;
		}
		
		if (score < 0) {								// Es soll keine negativen Punkte geben
			score = 0;
		}
		scoreTotal = scoreTotal + score;				// Berechnung der Gesamtpunktzahl
		scoreMax = scoreMax + 100;						// Brechnung der maximal erreichbaren Punkte
		
		// Ausgabe der Entfernung & Punkte in Textbox 
		gameDataEvaluationDistance.setText("\n Die Entfernung beträgt: " + distancekm + " km");
		gameDataEvaluationScore.setText(" Diese Runde: " + score + " / 100 Punkte \n Insgesammt:  " + scoreTotal + " / " + scoreMax + " Punkte");
		
		System.out.println(distancepx + " px");		
		System.out.println(distancekm + " km");
		System.out.println(score + "/100 Punkte");
		System.out.println(scoreTotal + "/" + scoreMax + " Punkte");
			
		
	}
	
	// Gesamtauswertung am Ende der letzten Runde, Ausgabe über Dialog-Fenster, Wechsel auf Hauptmenü-Fenster
	public void auswertung() {										
		
		gameHasStarted = false;										// Variable wird auf faslch gesetzt, da das Spiel beendet ist
		markerBlue.setVisible(false);								// Marker sollen nicht mehr sichbar sein
		markerRed.setVisible(false);
		
		// Ausgabe Dialog Spielauswertung 
		finalEvaluation.showMessageDialog(null, "\n Spiel beendet! " + scoreTotal + " von " + scoreMax + " möglichen Punkten \n ", "Auswertung", JOptionPane.INFORMATION_MESSAGE, SwingMain.iconGeoMasterDialog);
		System.out.println("Spiel beendet! \" + scoreTotal + \" von \" + scoreMax + \" möglichen Punkten");
		
		// zurück zum Hauptmenü
		final SwingMain swingMain = new SwingMain ();				// Klasse des Hauptmenüs
		swingMain.setVisible(true);									// Klasse wird sichtbar gemacht
		dispose ();													// aktuelles Fenster wird geschlossen
	}
	
	// Datensätze aus CityArray zufällig auswählen  
	public void setEntryNumber(int round, String[][] cities) {
		
		Random rNumber = new Random(); 								// neues Random Objekt, namens zufall
		int randomNumber = rNumber.nextInt(cities.length); 			// Ganzahlige Zufallszahl zw. 0 & der Array Länge
		
		boolean wasNotEqual = true;									// Variable "NochNichtBenutzt"
		while (wasNotEqual) {
			randomNumber = rNumber.nextInt(cities.length); 			// Ganzahlige Zufallszahl zw. 0 & der Array Länge
			
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
			
		        
		
	public SwingGame(String karte, int level) {
		SwingMain.toolkitForWindow = Toolkit.getDefaultToolkit();		
		
		Dimension d =SwingMain.toolkitForWindow.getScreenSize();		// Informationen über Desktopauflösung werden aberufen
		x = (int) ((d.getWidth() - width) / 2);							// Berechnung: Fenster soll zentriertauf Desktop geöffnet werden
		y = (int) ((d.getHeight() - height)  / 2);
		
		CSVReader daten = new CSVReader(karte, level);					// Spieldaten vom CSVReader "abgerufen"
		// System.out.println(daten.cities[0][0]);
		
		fillUsedNumberArrayWithDefault();
		
		for (int round = 1; round <= this.numberOfRounds; round++) {
	    	 setEntryNumber(round, daten.cities);
	    }
		
		setTitle ("GEO Master");						// "FensterName"
	    setBounds(x, y, width, height);					// Fenstergröße & -position

	    JLabel background = new JLabel();				// Hintergrund wird definiert
	    setLayout(new BorderLayout());					// Layout für Hintergrund			
		switch (karte) {								// Switch für Kartenauswahl, Abgleich mit Auswahl in ComboBox im Hauptmenü
			case " - Deutschland ":	
				background = new JLabel(new ImageIcon(SwingMain.userDir + "\\src\\org\\geom\\swing\\GameBackroundGER_1400x870.png"));
				break;
			case " - Europa ":
				background = new JLabel(new ImageIcon(SwingMain.userDir + "\\src\\org\\geom\\swing\\GameBackroundEUR_1400x870.png"));
				break;
		}
	    
		add(background);							// Hintergrund einfügen
		background.setLayout(null);					// Layout für weitere Elemente "null", alle Elemente müssen manuell positioniert werden 
	    
		
		background.add(markerBlue);					// Such-Marker
		markerBlue.setBounds(0,0,27,43);
		markerBlue.setVisible(false);				// soll vorerst noch nicht sichtbar sein
		
		background.add(markerRed);					// Ziel-Marker
		markerRed.setBounds(0,0,27,43);
		markerRed.setVisible(false);				// soll vorerst noch nicht sichtbar sein
		
		gameDataQuestionNumber.setBounds(1060, 350, 300, 30);			// Textfeld Ausgabe FrageNummer - Position & Größe
		gameDataQuestionNumber.setText("");								// soll vorerst leer sein
		gameDataQuestionNumber.setFont(font);							// Schriftart & Größe wird aus bereits definierter font Variable übernommen
		gameDataQuestionNumber.setBackground(getForeground());			// Hintergrundfarbe der Textbox
		gameDataQuestionNumber.setEditable(false);						// Box ist nicht beschreibbar
		background.add(gameDataQuestionNumber);							
				
		gameDataQuestion.setBounds(1060, 380, 300, 50);					// Textfeld Ausgabe Frage - Position & Größe
		gameDataQuestion.setText("");									// soll vorerst leer sein
		gameDataQuestion.setFont(font);									// Schriftart & Größe wird aus bereits definierter font Variable übernommen
		gameDataQuestion.setBackground(getForeground());				// Hintergrundfarbe der Textbox
		gameDataQuestion.setEditable(false);							// Box ist nicht beschreibbar
		background.add(gameDataQuestion);
		
		gameDataEvaluationDistance.setBounds(1060, 450, 300, 50);		// Textfeld Ausgabe Entfernung - Position & Größe
		gameDataEvaluationDistance.setText("");							// soll vorerst leer sein
		gameDataEvaluationDistance.setFont(font);						// Schriftart & Größe wird aus bereits definierter font Variable übernommen
		gameDataEvaluationDistance.setBackground(getForeground());		// Hintergrundfarbe der Textbox
		gameDataEvaluationDistance.setEditable(false);					// Box ist nicht beschreibbar
		background.add(gameDataEvaluationDistance);
		
		gameDataEvaluationScore.setBounds(1060, 500, 300, 80);			// Textfeld Ausgabe Punkte - Position & Größe
		gameDataEvaluationScore.setText("");							// soll vorerst leer sein
		gameDataEvaluationScore.setFont(font);							// Schriftart & Größe wird aus bereits definierter font Variable übernommen
		gameDataEvaluationScore.setBackground(getForeground());			// Hintergrundfarbe der Textbox
		gameDataEvaluationScore.setEditable(false);						// Box ist nicht beschreibbar
		background.add(gameDataEvaluationScore);	
		
		
		
		
		
		mainmenuButton = new JButton("Hauptmenü");						// Button zurück zum Hauptmenü
		mainmenuButton.setBounds(1060, 800, 140, 50);					// Position und Größe des HMbuttons
		background.add(mainmenuButton);
		
		mainmenuButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {				// ActionListener Hauptmenübutton
				final SwingMain sm = new SwingMain ();					// Klasse des Hauptmenüs
				sm.setVisible(true);									// Klasse wird sichtbar gemacht
				dispose ();												// altes Fenster geschlossen
				}
			});
		
		beendenButton = new JButton("Beenden");							// Button zum beenden des Spiels
		beendenButton.setBounds(1220, 800, 140, 50);					// Position und Größe des Beendenbuttons
		background.add(beendenButton);
		
		beendenButton.addActionListener(new ActionListener() {			// ActionListener, drücken des Button beendet das Spiel
			@Override
			public void actionPerformed(ActionEvent e) {
			dispose ();
			}
		});
		
		checkButton = new JButton("Spiel starten!");					// Button für Spielschritte wird deklariert
		checkButton.setBounds(1060, 600, 300, 100);						// Position & Größe des Spielbuttons
		background.add(checkButton);
		
		checkButton.addActionListener(new ActionListener() {					// ActionListener: Wartet auf drücken des Spielbuttons
			@Override
			public void actionPerformed(ActionEvent e) {						// Button wird gedrückt
				if ((!hasNotClicked || currentRound == 1) && nextRound) {		// prüft ob, Marker gesetzt oder Spiel gerade startet und ob in der Auswertung oder nicht
					markerBlue.setVisible(false);								// Marker werden entfernt
					markerRed.setVisible(false);
					if (currentRound <= numberOfRounds) {						// prüfen ob Spiel beendet
						checkButton.setText("Weiter...");						// Spielbutton Label wird geändert
						gameDataEvaluationDistance.setText("");					// Textfeld Entfernung & Punkte werden gesäubert
						gameDataEvaluationScore.setText("");
						gameAction(daten.cities);								
						gameDataQuestionNumber.setText("Frage " + currentRound + ": ");
						gameDataQuestion.setText("Wo befindet sich " + daten.cities[usedNumbers[currentRound - 1]][0] + "?");
						nextRound = false;										// Variable wird false gesetzt, um zur Berechnung zu kommen
						checkButton.setText("Wertung");
						
						currentRound++;
					} else {												// Wenn Spiel beendet auswerten
						auswertung();
					}
				} else {													// Marker nicht gesetzt	oder nicht nächste Runde
					if (!nextRound && !hasNotClicked) {						// prüfen ob Marker gestzt und nicht nächste Runde gestartet
						calcScore(Integer.parseInt(daten.cities[usedNumbers[currentRound - 2]][1]), Integer.parseInt(daten.cities[usedNumbers[currentRound - 2]][2]), Integer.parseInt(daten.cities[usedNumbers[currentRound - 2]][4]));
						nextRound = true;									// Variable wird true gesetzt, um zu gameAction zu kommen
						if (currentRound > numberOfRounds) {				// setzte Button Label anhand des Spielfortschritts 
							checkButton.setText("Auswertung");				// Spiel vorbei: Auswertung, sonst nächste Runde
						} else {
							checkButton.setText("Nächste Runde");						
						}
					}
				}
		   }
		});
		
		
		background.addMouseListener(this);								// MouseListener wird der Benutzeroberfläche (Hintergrund) zugefügt

		//setUndecorated(true);											// Rahmen verschwindet
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 				// Anwendung wird komplett beendet, nicht nur das Fenster geschlossen
	    setResizable(false);   											// Fenstergröße fest
	    setVisible(true);												// Fenster wird sichbar
	    setIconImage(SwingMain.iconGeoMaster.getImage());				// Fenster Icon geändert	    
   	}
}
