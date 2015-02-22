package week7_19_3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TFMain
{
	public static void main(String args[]) throws IOException
	{
		TFWordFactory wordFactory = new TFWordFactory();
		ITFWords tfWords = wordFactory.getTFWordObject(getOptionFromPropertiesString("word"));
		tfWords.extractWords(args[0]);
		TFFrequencyFactory frequencyFactory = new TFFrequencyFactory();
		ITFFrequencies tfFreq = frequencyFactory.getTFFreqObject(getOptionFromPropertiesString("frequency"));
		TFPrintFactory printFactory = new TFPrintFactory();
		ITFPrint tfPrint = printFactory.getTFPrintObject(getOptionFromPropertiesString("print"));
		tfPrint.printFrequencies(tfFreq.getFrequencies(tfWords.extractWords(args[0])));
		
	}
	public static int getOptionFromPropertiesString (String option) throws IOException
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
		return Integer.parseInt(p.getProperty(option));
	}
}

