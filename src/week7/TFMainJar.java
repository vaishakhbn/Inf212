package week7;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
public class TFMainJar
{
	public void loadPlugins(String fileName) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		URLClassLoader classLoader = new URLClassLoader(new URL[] {new URL("file://./my.jar")},
				this.getClass().getClassLoader());
		ITFWords tfWords = (ITFWords) Class.forName(getOptionFromPropertiesString("words"),
				true, classLoader).newInstance();
		ITFFrequencies tfFrequencies = (ITFFrequencies)
				Class.forName(getOptionFromPropertiesString("frequencies"), true, classLoader).newInstance();
		ITFPrint tfPrint = (ITFPrint)
				Class.forName(getOptionFromPropertiesString("print"),true, classLoader).newInstance();
		tfPrint.printFrequencies(tfFrequencies.getFrequencies(tfWords.extractWords(fileName)));
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException,
	NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
	IllegalArgumentException, InvocationTargetException
	{
		TFMainJar tfMainJar = new TFMainJar();
		tfMainJar.loadPlugins(args[0]);
	}
	public String getOptionFromPropertiesString (String option) throws IOException
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
		return p.getProperty(option);
	}
}
