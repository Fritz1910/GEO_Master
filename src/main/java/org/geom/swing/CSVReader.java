package org.geom.swing;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


/**CSV-Reader class, reading csv-files and supplies the data to the game session.
 * @author christoph.herrmann
 * @since 1.0.0
 */
public class CSVReader {

    public String[][] cities = {{"", "", "", "", ""}};									// creates array {City name, x coordinate, y coordinate, Level}
	
	public CSVReader(String region, int level) throws ClassNotFoundException {

    	InputStream csvFile;															
    	switch (region) {																// Switch for map selection
	        case " - Deutschland ":														// CSV-file with city names + coordinates + levels GER
	        	csvFile = SwingMain.class.getResourceAsStream("KoorGER.csv"); 		
	        	break;
	        case " - Europa ":															// CSV-file with city names + coordinates + levels EU
	        	csvFile = SwingMain.class.getResourceAsStream("KoorEUR.csv");		
	        	break;
	        default :
	        	throw new ClassNotFoundException("Selected CSV-content not found");
        }
    	
        String line = "";
        String cvsSplitBy = ";";														//  ";" as separator
        String[][] cities = {{"", "", "", "", ""}};										// creates array {city name, x coordinate, y coordinate, Level, relation px:km}

       
        
        
        
        try (
             InputStreamReader isr = new InputStreamReader(csvFile, StandardCharsets.UTF_8);	//read bitstream as (one) string with encoding-standard UTF_8
        	 BufferedReader br = new BufferedReader(isr)) {										//split string line by line (see ) 
        	
        	int counter = 0;
            while ((line = br.readLine()) != null) {
            	
               
                String[] city = line.split(cvsSplitBy);
                System.out.println("");
                if (city[0] != "Stadt") {													// filters headline from table
                	System.out.println("Stadt: >" + city[0] + "<");
                	System.out.println("Level: >" + level + "<");
                	System.out.println("Level (Ort): >" + city[2] + "<");
                	if (level >= Integer.parseInt(city[2])) {
	                	if (counter == 0) {													// Counter for dynamic adaption of the array length
	                    	cities[counter][0] = city[0];									// take line from the CSV: StadtName
	                    	cities[counter][1] = city[5];									// take line from the CSV:: x
	                    	cities[counter][2] = city[6];									// take line from the CSV:: y
	                    	cities[counter][3] = city[2];									// take line from the CSV:: Level
	                    	cities[counter][4] = city[7];									// take line from the CSV:: px:km
	                    } else {
	                    	int arraySize = counter;
	                    	int newArraySize= counter + 1;									// increment line number
	                    	String[][] tempArray = new String[ newArraySize ][ 5 ];			// tempArray as buffer 
	                    	for (int i=0; i < arraySize; i++) {
	                    		tempArray[i][0] = cities[i][0];
	                    		tempArray[i][1] = cities[i][1];
	                    		tempArray[i][2] = cities[i][2];
	                    		tempArray[i][3] = cities[i][3];
	                    		tempArray[i][4] = cities[i][4];
	                    	}
	                    	cities = tempArray;												// take array from buffer
	                    	cities[counter][0] = city[0];
	                    	cities[counter][1] = city[5];
	                    	cities[counter][2] = city[6];
	                    	cities[counter][3] = city[2];
	                    	cities[counter][4] = city[7];
	                    }
	                counter++;																// increment counter to go to the next line
	                }
                }

            }
            this.cities = cities;															// assume values

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
