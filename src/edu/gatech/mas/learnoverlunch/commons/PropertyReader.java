package edu.gatech.mas.learnoverlunch.commons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
	public static PropertyReader instance = new PropertyReader();
	Properties props = null;
	
	public PropertyReader() {
		props = new Properties();
		try {
			props.load(new FileInputStream(Constants.PROP_FILE_NAME));
		} catch (FileNotFoundException e) {
			System.err.println("Properties File ("+ Constants.PROP_FILE_NAME +") missing !! ");
		} catch (IOException e) {
			System.err.println(Constants.PROP_FILE_NAME + " : " + e.getMessage());
		}
	}
	
	public String getProperty(String propName)
	{
		return props.getProperty(propName);
	}
}
