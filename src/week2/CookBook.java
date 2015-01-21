package week2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class CookBook
{
	static HashSet<String> stopWords = new HashSet<String>();
	static List<String> tokens = new ArrayList<String>();

	public static void main(String[] args) throws FileNotFoundException
	{
		
		
		String fileName = "pride-and-prejudice.txt";
		if(args.length>0)
		{
			fileName = args[0];
		}
		String stopFilename = "stop_words.txt";
		convertInputFileToList(fileName, tokens);
		convertStopWordsToHashSet(stopFilename);
		tokens.removeAll(stopWords);
		printFrequencies(tokens);
	}
	
	private static void printFrequencies(List<String> token)
	{
		LinkedHashSet<String> uniqueTokens = new LinkedHashSet<String>();
		uniqueTokens.addAll(token);
		java.util.Iterator<String> iter = uniqueTokens.iterator();
		LinkedHashMap<String, Integer> t = new LinkedHashMap<String, Integer>();
		LinkedHashMap<Integer, String> k = new LinkedHashMap<Integer,String>();
		while (iter.hasNext()) 
		{
			String a = iter.next();
			int occurrences = Collections.frequency(token, a);
			t.put(a,occurrences);
			k.put(occurrences, a);
		}
		ArrayList<Integer> valueList = new ArrayList<Integer>(t.values());
		Collections.sort(valueList, Collections.reverseOrder());
		for(int i = 0; i<25; i++)
		{
			System.out.println(k.get(valueList.get(i))+"  -  "+valueList.get(i));
		}

	}

	public static void convertStopWordsToHashSet(String filename) throws FileNotFoundException
	{
		
		File file = new File(filename);
		Scanner in = new Scanner(file);
		String stopWordsInLine = in.nextLine();
		stopWords = new HashSet<String>(Arrays.asList(stopWordsInLine.split(",")));
	}
	
	private static void convertInputFileToList(String fileName, List<java.lang.String> tokens)
			throws FileNotFoundException 
	{
		String inp;
		File file = new File(fileName);
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
					tokens.add(a.toLowerCase());	
			}
		}
		
	}

}
