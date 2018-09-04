package org.geom.swing;


//~~~~~~~~~~~~~~ CSVReader ~~~~~~~~~~~~~~//


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class CSVReader {

    public String[][] cities = {{"", "", "", "", ""}};				// erstellt Array {Stadtname, x Koordinate, y Koordinate, Level}
	
	public CSVReader(String region, int level) throws ClassNotFoundException {

    	InputStream csvFile;															
    	switch (region) {																	// Switch f�r Kartenauswahl
	        case " - Deutschland ":															// CSV-Datei mit Namen+Koordinaten+Level GER
	        	csvFile = SwingMain.class.getResourceAsStream("KoorGER.csv"); 		
	        	break;
	        case " - Europa ":																// CSV-Datei mit Namen+Koordinaten+Level EU
	        	csvFile = SwingMain.class.getResourceAsStream("KoorEUR.csv");		
	        	break;
	        default :
	        	throw new ClassNotFoundException("Selected CSV-content not found");
        }
    	
        String line = "";
        String cvsSplitBy = ";";														// Benutzt ";" als Seperator
        String[][] cities = {{"", "", "", "", ""}};											// erstellt Array {Stadtname, x Koordinate, y Koordinate, Level, Verhälnis px:km}

       
        
        
        
        try (
             InputStreamReader isr = new InputStreamReader(csvFile, StandardCharsets.ISO_8859_1);	//read bitstream as (one) string with encoding-standard ISO...
        	 BufferedReader br = new BufferedReader(isr)) {	//split string line by line (see ) 
        	//catch (IOException e) {System.out.println("CSV error");};
        	
        	int counter = 0;
            while ((line = br.readLine()) != null) {
            	
               
                String[] city = line.split(cvsSplitBy);
                System.out.println("");
                if (city[0] != "Stadt") {													// Filtert Kopfzeile aus Tabelle heraus
                	System.out.println("Stadt: >" + city[0] + "<");
                	System.out.println("Level: >" + level + "<");
                	System.out.println("Level (Ort): >" + city[2] + "<");
                	if (level >= Integer.parseInt(city[2])) {
	                	if (counter == 0) {													// Counter zur dynamischen Anpassung der L�nge des Arrays
	                    	cities[counter][0] = city[0];									// Zeile aus der CSV wird �bernommen: StadtName
	                    	cities[counter][1] = city[5];									// Zeile aus der CSV wird �bernommen: x
	                    	cities[counter][2] = city[6];									// Zeile aus der CSV wird �bernommen: y
	                    	cities[counter][3] = city[2];									// Zeile aus der CSV wird �bernommen: Level
	                    	cities[counter][4] = city[7];									// Zeile aus der CSV wird �bernommen: px:km
	                    } else {
	                    	int arraySize = counter;
	                    	int newArraySize= counter + 1;									// Zeilenanzahl Array wird erh�ht
	                    	String[][] tempArray = new String[ newArraySize ][ 5 ];			// tempArray als Zwischenspeicher 
	                    	for (int i=0; i < arraySize; i++) {
	                    		tempArray[i][0] = cities[i][0];
	                    		tempArray[i][1] = cities[i][1];
	                    		tempArray[i][2] = cities[i][2];
	                    		tempArray[i][3] = cities[i][3];
	                    		tempArray[i][4] = cities[i][4];
	                    	}
	                    	cities = tempArray;												// Array �bernommen aus Zwischenspeicher
	                    	cities[counter][0] = city[0];
	                    	cities[counter][1] = city[5];
	                    	cities[counter][2] = city[6];
	                    	cities[counter][3] = city[2];
	                    	cities[counter][4] = city[7];
	                    }
	                counter++;																// Counter wird erh�ht um in die n�chste Zeile zu kommen
	                }
                }

            }
            this.cities = cities;															// Werte werden �bergeben

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
