package org.geom.swing;



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



/**Main class, also delivering the main menu. In the main menu the player can select the game options (map, difficulty)
 * @author christoph.herrmann
 * @since 1.0.0
 */
public class SwingMain extends javax.swing.JFrame {
	private static final long serialVersionUID = 9069118554859464281L;
	
	public static String userDir = System.getProperty("user.dir");
	
		
	public  static Toolkit toolkitForWindow;										// Toolkit for AWT, needed to get monitor resolution
	public int  x = 0 , y = 0 , width = 1400, height = 900;							// screen scale variables

	private JButton startButton;													// "Starten" Button initialization
	public JButton closeButton;														// "Beenden" Button initialization
	
	public String comboBoxMap[] = {" - Spielkarte ausw�hlen", " - Deutschland "," - Europa "};						//Kombo box initialization to select game map
	public String comboBoxLevel[] = {" - Schwierigkeitsgrad ausw�hlen", " - leicht " , " - mittel ", " - schwer "};	//Kombobox initialization to select game level
	
	public JOptionPane noSelection = new JOptionPane();

	public static ImageIcon iconGeoMaster = new ImageIcon(SwingMain.class.getResource("Logo_v1.1_319x295.png"));
	public static ImageIcon iconGeoMasterDialog = new ImageIcon(SwingMain.class.getResource("Logo_v1.1_50x45.png"));
	
	
	
	/**Constructor of the class SwingMain, creates the main menu frame
	 * @author christoph.herrmann
	 * @since 1.0.0
	 */
	public SwingMain() {
				
		SwingMain.toolkitForWindow = Toolkit.getDefaultToolkit();		
		Dimension d = toolkitForWindow.getScreenSize();				// window is opened in centered position
		x = (int) ((d.getWidth() - width) / 2);	
		y = (int) ((d.getHeight() - height)  / 2);
		setTitle ("GEO Master");									// window name
	    setBounds(x, y, width, height);
		 
	    System.out.println(userDir);
	    
	    setLayout(new BorderLayout());									// Layout for background			
		JLabel background=new JLabel(new ImageIcon(getClass().getResource("Background_1400x900.jpg")));
		add(background);												// insert background image
		background.setLayout(null);										// Layout for further elements
		
		
		JComboBox<String> mapSelection = new JComboBox<>(comboBoxMap);	// JComboBox create map
		background.add(mapSelection);									// JComboBox map added to background
		mapSelection.setBounds(590, 600, 220, 30);						// position and size of map-selection
		
		JComboBox levelSelection = new JComboBox(comboBoxLevel);		// JComboBox Level wird erstellt
		background.add(levelSelection);									// JComboBox Level wird backround hinzugef�gt
		levelSelection.setBounds(590, 640, 220, 30);					// Position und Gr��e der Levelauswahl
		
		startButton = new JButton("Starten");							// Button zum starten des Spiels
		startButton.setBounds(590, 680, 220, 80);						// Position und Gr��e des Startbuttons
		background.add(startButton);
		
		closeButton = new JButton("Beenden");							// Button zum beenden des Spiels
		closeButton.setBounds(1220, 800, 140, 50);				  		// Position und Gr��e des Beendenbuttons
		background.add(closeButton);
		
		
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {			// ActionListener Startenbutton
				String map = (String) mapSelection.getSelectedItem();
				String level = (String) levelSelection.getSelectedItem();
				if (map == " - Spielkarte auswählen" || level == " - Schwierigkeitsgrad ausw�hlen") {
		      		
					noSelection.showMessageDialog(null, "Bitte Spielkarte und Level ausgew�hlen!", "Inkorrekte Auswahl", JOptionPane.INFORMATION_MESSAGE);
					System.out.println ("Bitte Spielkarte und Level ausgew�hlen!");
		      		
			    }  else {
			    	System.out.println (map);
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
						ga = new SwingGame (map, levelnr);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}		// Klasse des Spielfensters
					ga.setVisible(true);								// Fenster wird sichtbar
					dispose ();											// altes Fenster geschlossen					
			      	
			  	}
			}
		});
		
		
    	closeButton.addActionListener(new ActionListener() {			// Beenden Button schliesst Spiel
			@Override
			public void actionPerformed(ActionEvent e) {
			dispose ();
			}
		});
	    
		 
		//setUndecorated(true);											// Rahmen verschwindet
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 				// Anwendung wird komplett beendet, nicht nur das Fenster geschlossen
	    setResizable(false);   											// Fenstergr��e fest
	    setVisible(true);
	    setIconImage(iconGeoMaster.getImage());							// Fenster Icon ge�ndert
	}
	
	
	/**Program origin, creates main menu frame (object) from class SwingMain when running. 
	 * @author christoph.herrmann
	 * @since 1.0.0
	 */
	public static void main(String [] args) {
		new SwingMain();
	}
		
}
