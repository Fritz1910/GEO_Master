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
	
	public String comboBoxMap[] = {" - Spielkarte auswählen", " - Deutschland "," - Europa "};						//Combobox initialization to select game map
	public String comboBoxLevel[] = {" - Schwierigkeitsgrad auswählen", " - leicht " , " - mittel ", " - schwer "};	//Combobox initialization to select game level
	
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
		
		JComboBox levelSelection = new JComboBox(comboBoxLevel);		// JComboBox create Level
		background.add(levelSelection);									// JComboBox add Level to background
		levelSelection.setBounds(590, 640, 220, 30);					// Position and size of level-selection
		
		startButton = new JButton("Starten");							// Start button
		startButton.setBounds(590, 680, 220, 80);						// Position and size of start button
		background.add(startButton);
		
		closeButton = new JButton("Beenden");							// Button to close the game
		closeButton.setBounds(1220, 800, 140, 50);				  		// Position and size of closeButton
		background.add(closeButton);
		
		
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				// ActionListener start button
				String map = (String) mapSelection.getSelectedItem();
				String level = (String) levelSelection.getSelectedItem();
				if (map == " - Spielkarte auswählen" || level == " - Schwierigkeitsgrad auswählen") {
		      		
					noSelection.showMessageDialog(null, "Bitte Spielkarte und Level ausgewählen!", "Inkorrekte Auswahl", JOptionPane.INFORMATION_MESSAGE);
					System.out.println ("Bitte Spielkarte und Level ausgewählen!");
		      		
			    }  else {
			    	System.out.println (map);
			    	System.out.println (level);
					
			    	int levelnr = 0;									// Chancing Level-Type string to int
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
			    	
			    	System.out.println (levelnr);						// control
			    	
					SwingGame ga = null;
					try {
						ga = new SwingGame (map, levelnr);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}		
					ga.setVisible(true);								// make window visible
					dispose ();											// close old window				
			      	
			  	}
			}
		});
		
		
    	closeButton.addActionListener(new ActionListener() {			// closeButton closes the game
			@Override
			public void actionPerformed(ActionEvent e) {
			dispose ();
			}
		});
	    
		 
		
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 				// closing the complete application not just the window
	    setResizable(false);   											// constant window size
	    setVisible(true);
	    setIconImage(iconGeoMaster.getImage());							// window icon
	}
	
	
	/**Program origin, creates main menu frame (object) from class SwingMain when running. 
	 * @author christoph.herrmann
	 * @since 1.0.0
	 */
	public static void main(String [] args) {
		new SwingMain();
	}
		
}
