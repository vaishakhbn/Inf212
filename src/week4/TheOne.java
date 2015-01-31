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
interface IFunction
{
	Object call(Object arg) throws IOException;
}
class ReadFile implements IFunction
{

	@Override
	public Object call(Object arg) throws IOException
	{
		String fileName = (String) arg;
		String contents = FileUtils.readFileToString(new File(fileName));	 
		return contents;
	}
	
}
class FilterCharacters implements IFunction
{

	@Override
	public Object call(Object arg) throws IOException
	{
		String inp = (String) arg;
		String alphaOnly = inp.replaceAll("[^a-zA-Z]+"," ");
		
		return alphaOnly;
	}
	
}
class Normalise implements IFunction
{

	@Override
	public Object call(Object arg) throws IOException
	{
		String inp = (String) arg;
		return inp.toLowerCase();
	}
		
}
class Scan implements IFunction
{

	@Override
	public Object call(Object arg) throws IOException
	{
		ArrayDeque<String> tokens = new ArrayDeque<String>();
		String inp = (String) arg;
		StringTokenizer st = new StringTokenizer(inp);
		while(st.hasMoreTokens())
		{
			String a = st.nextToken();
			
			if(!(a.equalsIgnoreCase(" ")||a.length()==1))
				tokens.add(a);	
		}
		return tokens;
	}
	
}
class RemoveStopWords implements IFunction
{

	@Override
	public Object call(Object arg) throws IOException
	{
		Scanner in = new Scanner(new File("stop_words.txt"));
		String stopWords = in.nextLine();
		ArrayList<String> words = new ArrayList<String>(Arrays.asList(stopWords.split(",")));
		ArrayDeque<String> validWordsList = new ArrayDeque<String>();
        for (String iter : ((ArrayDeque<String>) arg)) 
        {
            if (iter.length() > 1 && !(words.contains(iter))) 
            {
                validWordsList.add(iter);
            }
        }
        return validWordsList;
	}
}
class Frequencies implements IFunction
{

	@Override
	public Object call(Object arg) throws IOException
	{
		HashMap<String, Integer> wordFreqs = new HashMap<String, Integer>();
        for (String iter : ((ArrayDeque<String>) arg)) 
        {
            if (wordFreqs.containsKey(iter)) 
            {
                wordFreqs.put(iter, wordFreqs.get(iter) + 1);
            }
            else 
            {
                wordFreqs.put(iter, 1);
            }
        }
        return wordFreqs;
	}
}
class Sort implements IFunction
{

	@Override
	public Object call(Object arg) throws IOException
	{
		ArrayList<Pairs> wf = new ArrayList<Pairs>();
        for (Map.Entry<String, Integer> entry : ((HashMap<String, Integer>) arg).entrySet()) 
        {
            wf.add(new Pairs(entry.getKey(), entry.getValue()));
        }
        Collections.sort(wf);
        return wf;
	}
	
}
class TopTwentyFive implements IFunction
{
	@Override
	public Object call(Object arg) throws IOException
	{
            while (((ArrayList<Pairs>) arg).size() > 25) 
            {
                ((ArrayList<Pairs>) arg).remove(((ArrayList<Pairs>) arg).size() - 1);
            }
            return arg;
	}
}
class TheOne
{
	Object val;
	public TheOne(Object o)
	{
		this.val = o;
	}
	 public TheOne call(IFunction func) throws IOException {
         val = func.call(val);
         return this;
	 }
	 public void print() 
	 {
         if (val instanceof ArrayList) 
         {
             for (int i = 0; i < ((ArrayList<Pairs>) val).size(); i++) 
             {
                 System.out.println(((ArrayList<Pairs>) val).get(i).str + " - " + ((ArrayList<Pairs>) val).get(i).num);
             }
         }
     }
	public static void main(String args[]) throws IOException
	{
		TheOne i = new TheOne(args[0]);
		i.call(new ReadFile()).call(new FilterCharacters()).call(new Normalise()).call(new Scan()).call(new RemoveStopWords()).call(new Frequencies()).call(new Sort()).call(new TopTwentyFive()).print();
	}
	
}
