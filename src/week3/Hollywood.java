/**
 * 
 * 
 * The rationale behind using the Framework Interface :  Unlike python and other languages, Java 1.7 does not have the functionality to pass functions as parameters. 
 * With the Framework inteferace, the objects are put into an arraylist. 
 * Load, Do work and end are registered using this. In the run method, each of the object is taken out from the list and execution starts. 
 * In this way, inversion of control is achieved. Since all classes are put in the same java file, when this is compiled, separate class files are formed. 
 * 
 * @author vaishakh
 * @since 01/24/2015
 */

package week3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.*;


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

public class Hollywood 
{
    private static WordFrequencyCounter wfCounter;

	interface Framework {
        public void load(String argument) throws IOException;

        public void run() throws IOException;
    }

    public static class WordFrequencyFrameWork {
        ArrayList<Framework> loaders = new ArrayList<Framework>();
        ArrayList<Framework> doWorker = new ArrayList<Framework>();
        ArrayList<Framework> endEvents = new ArrayList<Framework>();

        public void registerForLoadEvent(Framework handler) 
        {
            loaders.add(handler);
        }

        public void registerForDoworkEvent(Framework handler) 
        {
            doWorker.add(handler);
        }

        public void registerForEndEvent(Framework handler) 
        {
            endEvents.add(handler);
        }

        public void run(String pathToFile) throws IOException 
        {
            for (Framework h : loaders) 
            {
                h.load(pathToFile);
            }
            for (Framework h : doWorker) 
            {
                h.run();
            }
            for (Framework h : endEvents) 
            {
                h.run();
            }
        }
    }

    public static class DataStorage implements Framework 
    {

        private String data = "";
        private StopWordFilter stopWordFilter;
        private ArrayList<Framework> wordEventHandlers = new ArrayList<Framework>();
		private Scanner scanner;

        public DataStorage(WordFrequencyFrameWork wfapp, StopWordFilter other) 
        {
            this.stopWordFilter = other;
            wfapp.registerForLoadEvent(this);
            wfapp.registerForDoworkEvent(this);
        }

        @Override
        public void load(String pathToFile) throws IOException 
        {
        	scanner = new Scanner( new File(pathToFile) );
			this.data = scanner.useDelimiter("\\A").next();
            String regEx = "[^A-Za-z0-9]";
            this.data = this.data.replaceAll(regEx, " ").toLowerCase();
        }

        @Override
        public void run() throws IOException 
        {
            ArrayDeque<String> wordsList = new ArrayDeque<String>();
            StringTokenizer tokenizer;
            tokenizer = new StringTokenizer(this.data, "[ ]");
            while (tokenizer.hasMoreElements()) 
            {
                wordsList.add(tokenizer.nextToken());
            }
            for (String iter : wordsList) 
            {
                if (!this.stopWordFilter.isStopWord(iter)) 
                {
                    for (Framework h : this.wordEventHandlers) 
                    {
                        h.load(iter);
                    }
                }
            }
        }

        public void registerForWordEvent(Framework handler) 
        {
            this.wordEventHandlers.add(handler);
        }
    }

    public static class StopWordFilter implements Framework 
    {
        private ArrayList<String> stopWords;

        public StopWordFilter(WordFrequencyFrameWork wfapp) 
        {
            wfapp.registerForLoadEvent(this);
        }
        public boolean isStopWord(String word) 
        {
        	return(stopWords.contains(word));
        }

        @Override
        public void load(String pathToFile) throws IOException 
        {
        	File f = new File("stop_words.txt");
        	String alpahbets = "a b c d e f g h i j k l m n o p q r s t u v w x y z";
        	Scanner s = new Scanner(f);
        	String st = s.nextLine();
        	stopWords = new ArrayList<String>(Arrays.asList(st.split(",")));
        	stopWords.addAll(Arrays.asList(alpahbets.split(" ")));
        }

        @Override
        public void run() throws IOException 
        {
        	System.out.println("Unimplemented method. Implementing it because not implementing this will make the class abstract");
        }
    }

    public static class WordFrequencyCounter implements Framework 
    {
        private HashMap<String, Integer> word_freqs = new HashMap<String, Integer>();
        private ArrayList<Pair> pairs = new ArrayList<Pair>();

        public WordFrequencyCounter(WordFrequencyFrameWork wfapp, DataStorage dataStorage) 
        {
            dataStorage.registerForWordEvent(this);
            wfapp.registerForEndEvent(this);
        }

        @Override
        public void load(String word) throws IOException 
        {
            if (word_freqs.containsKey(word)) 
            {
                word_freqs.put(word, word_freqs.get(word) + 1);
            }
            else 
            {
                word_freqs.put(word, 1);
            }
        }

        @Override
        public void run() throws IOException 
        {
            for (Map.Entry<String, Integer> entry : word_freqs.entrySet()) 
            {
                pairs.add(new Pair(entry.getKey(), entry.getValue()));
            }
            Collections.sort(pairs);
            for (int i = 0; i < (Math.min(25, pairs.size())); i++) 
            {
                System.out.println(pairs.get(i).str + " - " + pairs.get(i).num);
            }
        }
    }
    
    public static void main(String[] args) throws IOException 
    {
        WordFrequencyFrameWork wfapp = new WordFrequencyFrameWork();
        StopWordFilter swFilter = new StopWordFilter(wfapp);
        DataStorage dtStorage = new DataStorage(wfapp, swFilter);
        wfCounter = new WordFrequencyCounter(wfapp, dtStorage);
        wfapp.run(args[0]);
    }
}