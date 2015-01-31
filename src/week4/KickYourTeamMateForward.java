package week4;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
class Pai implements Comparable<Pai> 
{
    public String str = "";
    public int num = -1;

    public Pai(String et1, int et2) 
    {
        this.str = et1;
        this.num = et2;
    }

    @Override
    public int compareTo(Pai o) 
    {
        return -(this.num - o.num);
    }
}

public class KickYourTeamMateForward
{
	interface IKickYourTeamMateForward
	{
		void filterCharacters(String strData, HashMap<String, Integer> wordFrequencies, ArrayList<Pai> wordFrequncyPair, IKickYourTeamMateForward iKickYourTeamMateForward) throws IOException;
	    void normalize(String strData, HashMap<String, Integer> wordFrequencies, ArrayList<Pai> wordFrequncyPair, IKickYourTeamMateForward iKickYourTeamMateForward) throws IOException;
	    void scan(String strData, HashMap<String, Integer> wordFrequencies, ArrayList<Pai> wordFrequncyPair, IKickYourTeamMateForward iKickYourTeamMateForward) throws IOException;
	    void removeStopWords(ArrayDeque<String> wordsList, HashMap<String, Integer> wordFrequencies, ArrayList<Pai> wordFrequncyPair, IKickYourTeamMateForward iKickYourTeamMateForward) throws IOException;
	    void frequencies(ArrayDeque<String> wordsList, HashMap<String, Integer> wordFrequencies, ArrayList<Pai> wordFrequncyPair, IKickYourTeamMateForward iKickYourTeamMateForward);
	    void sort(HashMap<String, Integer> wordFrequencies, ArrayList<Pai> wordFrequncyPair, IKickYourTeamMateForward iKickYourTeamMateForward);
	    void noOperation(IKickYourTeamMateForward iKickYourTeamMateForward);
	    void printFrequencies(ArrayList<Pai> wordFrequencyPair, IKickYourTeamMateForward iKickYourTeamMateForward);
	}
	public static void main(String args[]) throws IOException
	{
		HashMap<String, Integer> wordFrequencies = new HashMap<String, Integer>();
        ArrayList<Pai> wordFrequncyPair = new ArrayList<Pai>();
		KickYourTeamMateForward k = new KickYourTeamMateForward();
		k.readFile(args[0], wordFrequencies, wordFrequncyPair, new IKickYourTeamMateForward()
		{
			
			@Override
			public void sort(HashMap<String, Integer> wordFrequencies, ArrayList<Pai> wordFrequncyPair,
					IKickYourTeamMateForward iKickYourTeamMateForward)
			{
				for (Map.Entry<String, Integer> entry :  wordFrequencies.entrySet()) 
		        {
		            wordFrequncyPair.add(new Pai(entry.getKey(), entry.getValue()));
		        }
		        Collections.sort(wordFrequncyPair);
		        printFrequencies(wordFrequncyPair,iKickYourTeamMateForward);
			}
			
			@Override
			public void scan(String strData, HashMap<String, Integer> wordFrequencies,
					ArrayList<Pai> wordFrequncyPair, IKickYourTeamMateForward iKickYourTeamMateForward)
					throws IOException
			{
				ArrayDeque<String> tokens = new ArrayDeque<String>();
				StringTokenizer st = new StringTokenizer(strData);
				while(st.hasMoreTokens())
				{
					String a = st.nextToken();
					
					if(!(a.equalsIgnoreCase(" ")||a.length()==1))
						tokens.add(a);	
				}
				removeStopWords(tokens, wordFrequencies, wordFrequncyPair, iKickYourTeamMateForward);
			}
			
			@Override
			public void removeStopWords(ArrayDeque<String> wordsList,
					HashMap<String, Integer> wordFrequencies, ArrayList<Pai> wordFrequncyPair,
					IKickYourTeamMateForward iKickYourTeamMateForward) throws IOException
			{
				Scanner in = new Scanner(new File("stop_words.txt"));
				String stopWords = in.nextLine();
				ArrayList<String> words = new ArrayList<String>(Arrays.asList(stopWords.split(",")));
				ArrayDeque<String> validWordsList = new ArrayDeque<String>();
		        for (String iter : wordsList) 
		        {
		            if (iter.length() > 1 && !(words.contains(iter))) 
		            {
		                validWordsList.add(iter);
		            }
		        }
		        frequencies(validWordsList, wordFrequencies, wordFrequncyPair, iKickYourTeamMateForward);
			}
			
			@Override
			public void normalize(String strData, HashMap<String, Integer> wordFrequencies,
					ArrayList<Pai> wordFrequncyPair, IKickYourTeamMateForward iKickYourTeamMateForward)
					throws IOException
			{
				
				strData =  strData.toLowerCase();
				scan(strData,wordFrequencies,wordFrequncyPair,iKickYourTeamMateForward);
			}
			
			@Override
			public void noOperation(IKickYourTeamMateForward iKickYourTeamMateForward)
			{
				return;
			}
			
			@Override
			public void frequencies(ArrayDeque<String> wordsList,
					HashMap<String, Integer> wordFrequencies, ArrayList<Pai> wordFrequncyPair,
					IKickYourTeamMateForward iKickYourTeamMateForward)
			{
		        for (String iter :  wordsList) 
		        {
		            if (wordFrequencies.containsKey(iter)) 
		            {
		            	wordFrequencies.put(iter, wordFrequencies.get(iter) + 1);
		            }
		            else 
		            {
		            	wordFrequencies.put(iter, 1);
		            }
		        }
		        sort(wordFrequencies, wordFrequncyPair, iKickYourTeamMateForward);
			}
			
			@Override
			public void filterCharacters(String strData,
					HashMap<String, Integer> wordFrequencies, ArrayList<Pai> wordFrequncyPair,
					IKickYourTeamMateForward iKickYourTeamMateForward) throws IOException
			{
				String inp = strData;
				String alphaOnly = inp.replaceAll("[^a-zA-Z]+"," ");
				normalize(alphaOnly, wordFrequencies, wordFrequncyPair, iKickYourTeamMateForward);
				
			}



			@Override
			public void printFrequencies(ArrayList<Pai> wordFrequencyPair,
					IKickYourTeamMateForward iKickYourTeamMateForward)
			{
				while (wordFrequncyPair.size() > 25) 
		        {
		            wordFrequncyPair.remove(wordFrequncyPair.size() - 1);
		        }
				for (int i = 0; i < wordFrequncyPair.size(); i++) 
		        {
		            System.out.println(wordFrequncyPair.get(i).str + " - " + (wordFrequncyPair.get(i).num));
		        }
				noOperation(iKickYourTeamMateForward);
			}
		});
		
	}
	private void readFile(String string, HashMap<String, Integer> wordFrequencies, ArrayList<Pai> wordFrequncyPair, IKickYourTeamMateForward iKickYourTeamMateForward) throws IOException
	{
		String fileName = string;
		String contents = FileUtils.readFileToString(new File(fileName));
		iKickYourTeamMateForward.filterCharacters(contents, wordFrequencies, wordFrequncyPair, iKickYourTeamMateForward);
	}
	
}
