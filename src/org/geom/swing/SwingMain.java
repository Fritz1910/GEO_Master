package org.geom.swing;


//~~~~~~~~~~~~~~ Start / Hauptmen� ~~~~~~~~~~~~~~//


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;



public class SwingMain extends javax.swing.JFrame {
	private static final long serialVersionUID = 9069118554859464281L;
	
	public static String userDir = System.getProperty("user.dir");
	
		
	public  static Toolkit toolkitForWindow;											// Toolkit f�r AWT, wird ben�tigt um Displayaufl�sung abzurufen 
	public int  x = 0 , y = 0 , width = 1400, height = 900;								// Fenster-Variablen

	private JButton startenButton;														// "Starten" Button Initialisierung
	public JButton beendenButton;														// "Beenden" Button Initialisierung
	
	public String comboBoxKarte[] = {" - Spielkarte ausw�hlen", " - Deutschland "," - Europa "};					//Kombobox Initialisierung zur Auswahl der Spielkarte
	public String comboBoxLevel[] = {" - Schwierigkeitsgrad ausw�hlen", " - leicht " , " - mittel ", " - schwer "};			//Kombobox Initialisierung zur Auswahl des Levels
	
	public JOptionPane noSelection = new JOptionPane();

	public static ImageIcon iconGeoMaster = new ImageIcon(SwingMain.class.getResource("Logo_v1.1_319x295.png"));
	public static ImageIcon iconGeoMasterDialog = new ImageIcon(SwingMain.class.getResource("Logo_v1.1_50x45.png"));
	
	
	public SwingMain() {
				
		SwingMain.toolkitForWindow = Toolkit.getDefaultToolkit();		
		Dimension d = toolkitForWindow.getScreenSize();									// Fenster wird zentriert ge�ffnet
		x = (int) ((d.getWidth() - width) / 2);	
		y = (int) ((d.getHeight() - height)  / 2);
		setTitle ("GEO Master");														// "FensterName"
	    setBounds(x, y, width, height);
		 
	    System.out.println(userDir);
	    
	    setLayout(new BorderLayout());									// Layout f�r Hintergrund			
		JLabel background=new JLabel(new ImageIcon(getClass().getResource("Background_1400x900.jpg")));
		add(background);												// Hintergrund einf�gen
		background.setLayout(null);										// Layout f�r weitere Elemente
		
		
		JComboBox<String> karteAuswahl = new JComboBox<>(comboBoxKarte);			// JComboBox Karte wird erstellt
		background.add(karteAuswahl);									// JComboBox Karte wird backround hinzugef�gt
		karteAuswahl.setBounds(590, 600, 220, 30);						// Position und Gr��e der Kartenauswahl
		
		JComboBox levelAuswahl = new JComboBox(comboBoxLevel);			// JComboBox Level wird erstellt
		background.add(levelAuswahl);									// JComboBox Level wird backround hinzugef�gt
		levelAuswahl.setBounds(590, 640, 220, 30);						// Position und Gr��e der Levelauswahl
		
		startenButton = new JButton("Starten");							// Button zum starten des Spiels
		startenButton.setBounds(590, 680, 220, 80);						// Position und Gr��e des Startbuttons
		background.add(startenButton);
		
		beendenButton = new JButton("Beenden");							// Button zum beenden des Spiels
		beendenButton.setBounds(1220, 800, 140, 50);					// Position und Gr��e des Beendenbuttons
		background.add(beendenButton);
		
		
		startenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {			// ActionListener Startenbutton
				String karte = (String) karteAuswahl.getSelectedItem();
				String level = (String) levelAuswahl.getSelectedItem();
				if (karte == " - Spielkarte ausw�hlen" || level == " - Schwierigkeitsgrad ausw�hlen") {
		      		
					noSelection.showMessageDialog(null, "Bitte Spielkarte und Level ausgew�hlen!", "Inkorrekte Auswahl", JOptionPane.INFORMATION_MESSAGE);
					System.out.println ("Bitte Spielkarte und Level ausgew�hlen!");
		      		
			    }  else {
			    	System.out.println (karte);
			    	System.out.println (level);
					
			    	int levelnr = 0;									// Umwandlung Level String in int 
			    	switch(level) {
			    		case " - leicht ":
			    			levelnr = 1;
			    			break;
			    		case " - mittel ":
			    			levelnr = 2;
			    			break;
			    		case " - schwer ":
			    			levelnr = 3;
			    			break;
			    	}
			    	
			    	System.out.println (levelnr);						// Kontrolle..
			    	
					SwingGame ga = null;
					try {
						ga = new SwingGame (karte, levelnr);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}		// Klasse des Spielfensters
					ga.setVisible(true);								// Fenster wird sichtbar
					dispose ();											// altes Fenster geschlossen					
			      	
			  	}
			}
		});
		
		
    	beendenButton.addActionListener(new ActionListener() {				// Beenden Button schliesst Spiel
			@Override
			public void actionPerformed(ActionEvent e) {
			dispose ();
			}
		});
	    
		 
		//setUndecorated(true);												// Rahmen verschwindet
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 					// Anwendung wird komplett beendet, nicht nur das Fenster geschlossen
	    setResizable(false);   												// Fenstergr��e fest
	    setVisible(true);
	    setIconImage(iconGeoMaster.getImage());								// Fenster Icon ge�ndert
	}
	
	
	public static void main(String [] args) {
		new SwingMain();
	}
		
}
