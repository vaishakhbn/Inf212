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

public class Monolithic
{
	static List<String> tokens = new ArrayList<String>();
	static HashSet<String> stopWords = new HashSet<String>();
	public static void main(String args[]) throws FileNotFoundException
	{
		String fileName = "pride-and-prejudice.txt";
		if(args.length>0)
		{
			fileName = args[0];
		}
		String stopFilename = "stop_words.txt";
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
		file = new File(stopFilename);
		in = new Scanner(file);
		String stopWordsInALine = in.nextLine();
		stopWords = new HashSet<String>(Arrays.asList(stopWordsInALine.split(",")));
		tokens.removeAll(stopWords);
		LinkedHashSet<String> uniqueTokens = new LinkedHashSet<String>();
		uniqueTokens.addAll(tokens);
		java.util.Iterator<String> iter = uniqueTokens.iterator();
		LinkedHashMap<String, Integer> t = new LinkedHashMap<String, Integer>();
		LinkedHashMap<Integer, String> k = new LinkedHashMap<Integer,String>();
		while (iter.hasNext()) 
		{
			String a = iter.next();
			int occurrences = Collections.frequency(tokens, a);
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

}
