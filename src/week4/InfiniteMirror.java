package week4;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

class Pair implements Comparable<Pair> 
{
    public String str = "";
    public int num = -1;

    public Pair(String et1, int et2) 
    {
        this.str = et1;
        this.num = et2;
    }

    @Override
    public int compareTo(Pair o) 
    {
        return -(this.num - o.num);
    }
}

class InfiniteMirror
{
	private void count(ArrayDeque<String> wordList, HashMap<String, Integer> wordFrequencies, ArrayList<String> stopWords, int stepIterator) {
        if (wordList.isEmpty()) 
        {
            return;
        }
        
        if (stepIterator == 1) 
        {
            String word = wordList.pollFirst();
            if (word.length() > 1 && Collections.binarySearch(stopWords, word) < 0) 
            {
            	if(wordFrequencies.containsKey(word))
            	{
            		wordFrequencies.put(word,wordFrequencies.get(word)+1);
            	}
            	else
            	{
            		wordFrequencies.put(word,1);
            	}
            }
        }
        
        else 
        {
            count(wordList, wordFrequencies, stopWords, 1);
            count(wordList, wordFrequencies, stopWords, stepIterator - 1);
        }
    }
	
	private static void printFrequencies(List<Pair> pairs)
	{
		if(pairs.isEmpty())
			return;
		if(pairs.size()==1)
			System.out.println(pairs.get(0).str + " - "+ pairs.get(0).num);
		else
		{
			printFrequencies(pairs.subList(0, 1));
			printFrequencies(pairs.subList(1, pairs.size()));
		}
	}
	
	private  ArrayDeque<String> getWords(String filePath) throws IOException
	{
		ArrayDeque<String> tokens = new ArrayDeque<String>();
		String inp;
		List<String> input = new ArrayList<String>();
		File file = new File(filePath);
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
		return tokens;
	}
	private  ArrayList<String> getStopWords(String filePath) throws IOException
	{
		Scanner in = new Scanner(new File(filePath));
		String stopWords = in.nextLine();
		ArrayList<String> words = new ArrayList<String>(Arrays.asList(stopWords.split(",")));
		return words;
	}
	private static List<Pair> sorted(HashMap<String, Integer> wordFrequencies, int num)
	{
		 ArrayList<Pair> pairs = new ArrayList<Pair>();

			for (Map.Entry<String, Integer> entry : wordFrequencies.entrySet()) 
	        {
	            pairs.add(new Pair(entry.getKey(), entry.getValue()));
	        }
			Collections.sort(pairs);
			return pairs.subList(0, num);
	}
	
	public static void main(String[] args) throws IOException
	{
		InfiniteMirror infinitMirror = new InfiniteMirror();
		ArrayList<String> stopWords = infinitMirror.getStopWords("stop_words.txt");
		ArrayDeque<String> words = infinitMirror.getWords(args[0]);
		final int RECURSION_LIMIT = 200000;
		HashMap<String, Integer> wordFrequencies = new LinkedHashMap<String, Integer>();
		
		for(int i = 0; i<words.size();  i+=RECURSION_LIMIT)
		{
			infinitMirror.count(words, wordFrequencies, stopWords, RECURSION_LIMIT);
		}
		
		printFrequencies(sorted(wordFrequencies,25));
	}
}