package week1;

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

public class TermFrequencyBasedOnRubrics
{

	public static void main(String[] args) throws FileNotFoundException
	{
		String fileName = "pride-and-prejudice.txt";
		if(args.length>0)
		{
			fileName = args[0];
		}
		String stopFilename = "stop_words.txt";
		List<String> tokens = convertInputFileToList(fileName);
		HashSet<String> stopWords = convertStopWordsToHashSet(stopFilename);
		tokens.removeAll(stopWords);
		printFrequencies(tokens);
	}
	
	private static void printFrequencies(List<String> token)
	{
		LinkedHashSet<String> uniqueTokens = new LinkedHashSet<String>();
		uniqueTokens.addAll(token);
		java.util.Iterator<String> iter = uniqueTokens.iterator();
		LinkedHashMap<String, Integer> t = new LinkedHashMap<String, Integer>();
		while (iter.hasNext()) 
		{
			String a = iter.next();
			System.out.println(iter.next()+" " + iter.next());
			int occurrences = Collections.frequency(token, a);
			t.put(a,occurrences);
		}
		t = sortByValue(t);
		Set set = t.entrySet();
		Iterator i = set.iterator();
		int j =0;
	    while(i.hasNext()&&j<25) 
	    {
	       Map.Entry me = (Map.Entry)i.next();
	       System.out.print(me.getKey() + "- ");
	       System.out.println(me.getValue());
	       j++;
	    }
	}

	public static <String, Integer extends Comparable<? super Integer>> LinkedHashMap<String, Integer> 
    sortByValue( LinkedHashMap<String, Integer> map )
    {
		List<Map.Entry<String, Integer>> list =
				new LinkedList<>( map.entrySet() );
		Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
				{
			@Override
			public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
			{
				return (o2.getValue()).compareTo( o1.getValue() );
			}
				} );

		LinkedHashMap<String, Integer> result = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> entry : list)
		{
			result.put( entry.getKey(), entry.getValue() );
		}
		return result;
    }

	public static HashSet<String> convertStopWordsToHashSet(String filename) throws FileNotFoundException
	{
		
		File file = new File(filename);
		Scanner in = new Scanner(file);
		String stopWords = in.nextLine();
		HashSet<String> words = new HashSet<String>(Arrays.asList(stopWords.split(",")));
		return words;
	}
	
	private static List<String> convertInputFileToList(String fileName)
			throws FileNotFoundException 
	{
		String inp;
		List<String> input = new ArrayList<String>();
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
					input.add(a.toLowerCase());	
			}
		}
		return input;
	}

}
