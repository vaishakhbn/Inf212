package week5;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Tantrums	
{
	static HashSet<String> stopWords = new HashSet<String>();
	static ArrayList<String> tokens = new ArrayList<String>();

	public static void main(String[] args) throws FileNotFoundException
	{
		String fileName = new String();
		try
		{
			fileName = args[0];
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("No arguements given to the program : "+e);
			return;
		}
		tokens = convertInputFileToList(fileName);
		tokens = convertStopWordsToHashSet(tokens);
		printFrequencies(tokens);
		
	}
	
	public static void printFrequencies(List<String> token)
	{
		try
		{
			if(token.isEmpty())
				throw new Exception("Token list Empty");
			HashMap<String, Integer> wordFrequncies = new HashMap<String, Integer>();
			ArrayList<Pairs> pairs = new ArrayList<Pairs>();
			for (String iter : token) 
			{
				if (wordFrequncies.containsKey(iter)) 
				{
					wordFrequncies.put(iter, wordFrequncies.get(iter) + 1);
				} 
				else 
				{
					wordFrequncies.put(iter, 1);
				}
			}
			for (Map.Entry<String, Integer> entry : wordFrequncies.entrySet()) 
			{
				pairs.add(new Pairs(entry.getKey(), entry.getValue()));
			}
			Collections.sort(pairs);
			for (int i = 0; i < (Math.min(25, pairs.size())); i++) 
			{
				System.out.println(pairs.get(i).str + " - " + pairs.get(i).num);
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			System.exit(0);
		}
	}
	public static ArrayList<String> convertStopWordsToHashSet(ArrayList<String> tokens2) throws FileNotFoundException
	{
		try
		{
			File file = new File("stop_words.txt");
			
			if(!file.exists())
				throw new FileNotFoundException();
			if(tokens2.isEmpty())
				throw new Exception("Token String empty");
			Scanner in = new Scanner(file);
			String stopWordsInLine = in.nextLine();
			HashSet<String> stopWordTokens =  new HashSet<String>(Arrays.asList(stopWordsInLine.split(",")));
			tokens2.removeAll(stopWordTokens);
		}
			catch(FileNotFoundException e)
			{
				System.out.println("Stop words file not found. Exitting!");
				System.exit(0);
				
			} catch (Exception e) 
			{
				e.printStackTrace();
				System.exit(0);
			}
			return tokens2;
	}
	
	private static ArrayList<String> convertInputFileToList(String fileName)
			throws FileNotFoundException 
	{
		String inp;
		ArrayList<String> tok = new ArrayList<String>();
		try
		{
			File file = new File(fileName);
			if(!file.exists())
				throw new FileNotFoundException();
			Scanner in = new Scanner(file);
			while(in.hasNextLine())
			{
				inp = in.nextLine();
				String alphaOnly = inp.replaceAll("[^a-zA-Z]+"," ");
				StringTokenizer st = new StringTokenizer(alphaOnly);
				while(st.hasMoreTokens())
				{
					String a = st.nextToken();
					if(!(a.equalsIgnoreCase(" ")||a.length()==1))
						tok.add(a.toLowerCase());	
				}
			}
		}
		catch(FileNotFoundException e) 
		{
			System.out.println("File does not exist! Exitting"+e);
			System.exit(0);
		}
		return tok;
	}
}
