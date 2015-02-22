package week7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;


public class TFFirstAlternative implements TFInterface
{

	@Override
	public ArrayList<String> extractWords(String fileName)
	{
		ArrayList<String> tokens = new ArrayList<String>();
		String inp;
		File file = new File(fileName);
		Scanner in = null;
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
		file = new File("stop_words.txt");
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String stopWordsInLine = in.nextLine();
		ArrayList<String> stopWords = new ArrayList<String>(Arrays.asList(stopWordsInLine.split(",")));
		tokens.removeAll(stopWords);
		return tokens;
	}

	@Override
	public ArrayList<Pairs> getFrequencies(ArrayList<String> tokens)
	{
		ArrayList<Pairs> pairList  = new ArrayList<Pairs>();
		HashMap<String, Integer> tokenMap = new HashMap<String, Integer>();
		for(String word : tokens)
		{
			if (tokenMap.containsKey(word)) 
            {
                tokenMap.put(word, tokenMap.get(word) + 1);
            }
            else 
            {
                tokenMap.put(word, 1);
            }
		}
		for (Map.Entry<String, Integer> entry :  tokenMap.entrySet()) 
        {
            pairList.add(new Pairs(entry.getKey(), entry.getValue()));
        }
        Collections.sort(pairList);
		return pairList;
	}

	@Override
	public void printFrequencies(ArrayList<Pairs> p)
	{
		for (int i = 0; i < 25 ; i++) 
        {
            System.out.println(p.get(i).str + " - " + (p.get(i).num));
        }
	}

}
