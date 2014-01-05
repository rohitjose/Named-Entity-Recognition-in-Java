/** ProrpetyLoader has the methods to retrieve values 
 * from the properties file of the project 
 */

package com.rohit.ner;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {

	public static void setProperties() {
		// Sets the pivot file path value to the properties file
		Properties prop = new Properties();

		try {
			// set the properties value
			prop.setProperty("pivotfile", "/home/rohit/pivots.txt");

			// save properties to project root folder
			prop.store(new FileOutputStream("config.properties"), null);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static String loadPivotFilePath() {
		// Returns the Pivot file path from the properties file
		Properties prop = new Properties();
		String pivotPath = new String();

		try {
			// load a properties file
			prop.load(new FileInputStream("config.properties"));

			// get the property value and print it out
			pivotPath = prop.getProperty("pivotfile");

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return pivotPath;

	}

}
