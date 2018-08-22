package org.geom.swing;


//~~~~~~~~~~~~~~ CSVReader ~~~~~~~~~~~~~~//


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {

    public String[][] cities = {{"", "", "", "", ""}};				// erstellt Array {Stadtname, x Koordinate, y Koordinate, Level}
	
	public CSVReader(String region, int level) {

    	String csvFile = "";															
    	switch (region) {																	// Switch f�r Kartenauswahl
	        case " - Deutschland ":															// CSV-Datei mit Namen+Koordinaten+Level GER
	        	csvFile = SwingMain.userDir +  "\\src\\org\\geom\\swing\\KoorGER.csv"; 		
	        	break;
	        case " - Europa ":																// CSV-Datei mit Namen+Koordinaten+Level EU
	        	csvFile = SwingMain.userDir + "\\src\\org\\geom\\swing\\KoorEUR.csv";		
	        	break;
        }
    	
        String line = "";
        String cvsSplitBy = ";";														// Benutzt ";" als Seperator
        String[][] cities = {{"", "", "", "", ""}};											// erstellt Array {Stadtname, x Koordinate, y Koordinate, Level, Verh�lnis px:km}

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
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
