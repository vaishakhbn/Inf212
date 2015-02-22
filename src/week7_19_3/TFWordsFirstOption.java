package week7_19_3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TFWordsFirstOption implements ITFWords
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
}

