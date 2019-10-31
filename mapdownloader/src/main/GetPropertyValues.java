package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
Class used to get properties from config.properties file
*/

public class GetPropertyValues {
	private Properties properties = new Properties();
	private InputStream inputStream;
	private final String PROP_FILE_NAME = "config.properties";
 
	public Properties getPropValues() throws IOException {
		try {
			inputStream = getClass().getClassLoader().getResourceAsStream(PROP_FILE_NAME);
			if (inputStream != null) {
				properties.load(inputStream);
			} else {
				throw new FileNotFoundException("Property file '" + PROP_FILE_NAME + "' not found in the classpath");
			}
 		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return properties;
	}
	
}