package week7;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class TFMain
{
	public static void main(String args[]) throws IOException
	{
		TFFactory factory = new TFFactory();
		//TFInterface tfObject =  factory.getTF(getOptionFromProperties());
		ITFFrequencies tfFrequencies = factory.getTF(option)
		ArrayList<String> tokens = tfObject.extractWords(args[0]);
		ArrayList<Pairs> pairs = tfObject.getFrequencies(tokens);
		tfObject.printFrequencies(pairs);
		
	}
	public static int getOptionFromProperties() throws IOException
	{
		Properties p = new Properties();
		String propFileName = "config.properties";
		InputStream inputStream = TFMain.class.getClassLoader().getResourceAsStream(propFileName);
		if (inputStream != null) 
		{
			p.load(inputStream);
		} else 
		{
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}
		String option = p.getProperty("option");
		return Integer.parseInt(option);
	}
}
