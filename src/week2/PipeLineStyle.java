package week2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

public class PipeLineStyle
{

	public static void main(String[] args) throws FileNotFoundException
	{
		printFrequencies(removeStopWords(createStopWordList(convertInputFileToLists(args))));
	}
	
	private static List<java.lang.String> removeStopWords(ArrayList<ArrayList<java.lang.String>> listOfLists) throws FileNotFoundException
	{
		 listOfLists.get(0).removeAll(listOfLists.get(1));
		 return listOfLists.get(0);
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
	
	private static ArrayList<java.lang.String> convertInputFileToLists(String[] args)
			throws FileNotFoundException 
	{
		

		String inp;
		ArrayList<String> input = new ArrayList<String>();
		File inputFile = new File(args[0]);
		Scanner in = new Scanner(inputFile);
		while(in.hasNextLine())
		{
			inp = in.nextLine();
			String alphaOnly = inp.replaceAll("[^a-zA-Z]+"," ");
			StringTokenizer st = new StringTokenizer(alphaOnly);
			while(st.hasMoreTokens())
			{
				String a = st.nextToken();
				
				if(!(a.equalsIgnoreCase(" ")||a.length()==1))
					input.add(a.toLowerCase());	
			}
			
		}
		input.add(args[1]);
		return input;
	}
	
		private static ArrayList<ArrayList<String>> createStopWordList(ArrayList<String> input) throws FileNotFoundException
		
		{
		ArrayList<ArrayList<String>> listOfLists = new ArrayList<ArrayList<String>>();	
		File stopFileName = new File(input.get(input.size()-1));
		input.remove(input.size()-1);
		Scanner in = new Scanner(stopFileName);
		String stopWords = in.nextLine();
		ArrayList<String> words = new ArrayList<String>(Arrays.asList(stopWords.split(",")));
		listOfLists.add(input);
		listOfLists.add(words);
		return listOfLists;
	}

}
