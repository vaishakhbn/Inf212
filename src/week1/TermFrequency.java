package week1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class TermFrequency 
{
	public static void main(String[] args) throws IOException 
	{
		String filename = "pride-and-prejudice.txt";
		if(args.length>0)
		{
			filename = args[0];
		}
		String stopFilename = "stop_words.txt";
		List<String> words = new ArrayList<String>();
		List<String> input = new ArrayList<String>();
		List<Terms> terms = new ArrayList<Terms>();
        convertFileToList(filename, input);
        words = convertStopWordsToList(stopFilename);
        Collections.sort(input);
        Collections.sort(words);
        input.removeAll(words);
		LinkedHashSet<String> inputWithoutRepititon = new LinkedHashSet<String>();
		inputWithoutRepititon.addAll(input);
		java.util.Iterator<String> iter = inputWithoutRepititon.iterator();
		LinkedHashMap t = new LinkedHashMap();
		while (iter.hasNext()) 
		{
			
			String a = iter.next();
			int occurrences = Collections.frequency(input, a);
			t.put(a, occurrences);
			//Terms t = new Terms(occurrences,a);
			//terms.add(t);
		}
		TreeMap<String, Integer> a = new TreeMap<String, Integer>();
		a.putAll(t);
		//Collections.sort(terms);
		/*for(int i =0; i<25;i++)
		{
			System.out.println(terms.get(i).toString());
		}*/
		Set set = a.entrySet();
	      // Get an iterator
	      Iterator i = set.iterator();
	      // Display elements
	      while(i.hasNext()) {
	         Map.Entry me = (Map.Entry)i.next();
	         System.out.print(me.getKey() + ": ");
	         System.out.println(me.getValue());
	}
	}
	
	private static List<String> convertStopWordsToList(String filename) throws FileNotFoundException
	{
		List<String> words = new ArrayList<String>();
		File file = new File(filename);
		Scanner in = new Scanner(file);
		String stopWords = in.nextLine();
		words = Arrays.asList(stopWords.split(","));
		return words;
	}

	private static void convertFileToList(String filename, List<String> input)
			throws FileNotFoundException 
	{
		String inp;
		File file = new File(filename);
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
	}
}

class Terms implements Comparable<Terms>
{
	int occurance;
	String word;
	
	public Terms(int occurrences, String a) 
	{
		this.occurance = occurrences;
		this.word = a;
	}
	public int getOccurences()
	{
		return this.occurance;
	}
	@Override
	public int compareTo(Terms o) {
		int comparedSize = o.occurance;
		if (this.occurance < comparedSize) {
			return 1;
		} else if (this.occurance == comparedSize) {
			return 0;
		} else {
			return -1;
		}
	}
	@Override
	   public String toString() {
	        return (this.getWords() +" - " + this.getOccurences() );
	   }
	private String getWords() {
		return this.word;
	}
}