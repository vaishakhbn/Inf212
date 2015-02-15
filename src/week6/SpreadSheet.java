package week6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;



class Pairs implements Comparable<Pairs> 
{
    public String str = "";
    public int num = -1;

    public Pairs(String et1, int et2) 
    {
        this.str = et1;
        this.num = et2;
    }

    @Override
    public int compareTo(Pairs o) 
    {
        return -(this.num - o.num);
    }
}

public class SpreadSheet
{
	public static void main(String args[]) throws FileNotFoundException
	{
		AllWords aw = new AllWords();
		StopWords sw = new StopWords();
		aw.execute("pride-and-prejudice.txt");
		sw.execute();
		update(aw, sw);
	}

	private static void update(AllWords aw, StopWords sw)
			throws FileNotFoundException
	{
		NonStopWords nsw = new NonStopWords();
		UniqueWords uw = new UniqueWords();
		Counts c = new Counts();
		SortedData sd = new SortedData();
		nsw.execute(aw, sw);
		uw.execute(nsw);
		c.execute(uw, nsw);
		sd.execute(c, uw);
	}
	

}
class  AllWords
{
	ArrayList<String> words;
	void execute(String fileName) throws FileNotFoundException
	{
		words = new ArrayList<String>();
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
					words.add(a.toLowerCase());	
			}
		}
	}
}
class StopWords
{
	ArrayList<String> stopWords;
	void execute() throws FileNotFoundException
	{
		stopWords = new ArrayList<String>();
		File file = new File("stop_words.txt");
		Scanner in = new Scanner(file);
		String stopWordsInLine = in.nextLine();
		stopWords = new ArrayList<String>(Arrays.asList(stopWordsInLine.split(",")));
	}
}
class NonStopWords
{
	ArrayList<String> nonStopWords;
	void execute(AllWords aw, StopWords sw) throws FileNotFoundException
	{
		nonStopWords =  new ArrayList<String>();
		nonStopWords = aw.words;
		nonStopWords.removeAll(sw.stopWords);
		
	}
}
class UniqueWords
{
	HashSet<String> uniqueWordsHashSet;
	ArrayList<String> uniqueWordsArrayList;
	void execute(NonStopWords nsw)
	{
		uniqueWordsHashSet = new HashSet<String>();
		uniqueWordsArrayList = new ArrayList<String>();
		uniqueWordsHashSet.addAll(nsw.nonStopWords);
		uniqueWordsArrayList.addAll(uniqueWordsHashSet);
	}
}
class Counts
{
	ArrayList<Integer> counts;
	void execute(UniqueWords uw, NonStopWords nsw)
	{
		counts = new ArrayList<Integer>();
		java.util.Iterator<String> iter = uw.uniqueWordsArrayList.iterator();
		while(iter.hasNext())
		{
			String a = iter.next();
			int occurrences = Collections.frequency(nsw.nonStopWords, a);
			counts.add(occurrences);
		}
	}
}
class SortedData
{
	HashMap<String, Integer> wordFreqs;
	void execute(Counts c, UniqueWords uw)
	{
		int i = 0;
		wordFreqs = new HashMap<String, Integer>();
		while(i<uw.uniqueWordsArrayList.size())
		{
			wordFreqs.put(uw.uniqueWordsArrayList.get(i), c.counts.get(i));
			i++;
		}
		ArrayList<Pairs> wf = new ArrayList<Pairs>();
        for (Map.Entry<String, Integer> entry : wordFreqs.entrySet()) 
        {
            wf.add(new Pairs(entry.getKey(), entry.getValue()));
        }
        Collections.sort(wf);
        for (int j = 0; j < (Math.min(25, wf.size())); j++) 
		{
			System.out.println(wf.get(j).str + " - "
                    + wf.get(j).num);
        }
	}
}