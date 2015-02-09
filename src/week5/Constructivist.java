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

public class Constructivist	
{
	static HashSet<String> stopWords = new HashSet<String>();
	static ArrayList<String> tokens = new ArrayList<String>();

	public static void main(String[] args) throws FileNotFoundException
	{
		String fileName = new String();
		if(args.length == 0)
		{
			System.out.println("token file not Inputted. Will compute for existing text file");
			fileName = "test.txt";
		}
		else
			fileName = args[0];
		tokens = convertInputFileToList(fileName);
		tokens = convertStopWordsToHashSet(tokens);
		printFrequencies(tokens);
	}
	
	public static void printFrequencies(List<String> token)
	{
		HashMap<String, Integer> word_freqs = new HashMap<String, Integer>();
        ArrayList<Pairs> pairs = new ArrayList<Pairs>();
        for (String iter : token) {
            if (word_freqs.containsKey(iter)) {
                word_freqs.put(iter, word_freqs.get(iter) + 1);
            } else {
                word_freqs.put(iter, 1);
            }
        }
        for (Map.Entry<String, Integer> entry : word_freqs.entrySet()) 
        {
            pairs.add(new Pairs(entry.getKey(), entry.getValue()));
        }
        Collections.sort(pairs);
        for (int i = 0; i < (Math.min(25, pairs.size())); i++) 
        {
            System.out.println(pairs.get(i).str + " - " + pairs.get(i).num);
        }
    }
	public static ArrayList<String> convertStopWordsToHashSet(ArrayList<String> tokens2) throws FileNotFoundException
	{
			File file = new File("../stop_words.txt");
			if(!file.exists())
			{
				System.out.println("Could not find stop words. Returning tokens back");
				return tokens2;
			}
			Scanner in = new Scanner(file);
			String stopWordsInLine = in.nextLine();
			HashSet<String> stopWordTokens =  new HashSet<String>(Arrays.asList(stopWordsInLine.split(",")));
			tokens2.removeAll(stopWordTokens);
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
			{
				System.out.println("File does not exist. Will test for test.txt");
				file = new File("test.txt"); 
			}
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
			return tok;
		}
		catch(FileNotFoundException e) 
		{
			System.out.println("Either file not found or File not inputted"+e);
			return new ArrayList<String>();
		}
	}
}
