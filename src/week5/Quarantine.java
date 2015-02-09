package week5;
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
public class Quarantine 
{
	interface IFunction
	{
		Object call(Object arg) throws IOException;
	}
	interface IInputOutputWrapper 
	{
		public Object function() throws IOException; 
	}
	interface IGetInput extends IFunction{}
	interface IExtractWords extends IFunction{}
	interface IRemoveStopWords extends IFunction{}
	interface IFrequency extends IFunction{}
	interface ISort extends IFunction{}
	interface IPrintTopTwentyFive extends IFunction{}
	static class TFQuarantine
	{
		interface Callable
		{
			public Object guardCallable(Object arg) throws IOException;
		}
		ArrayList<IFunction> funcs = new ArrayList<IFunction>();
		public TFQuarantine(IFunction arg)
		{
			funcs.add(arg);
		}
		public TFQuarantine bind(IFunction arg)
		{
			funcs.add(arg);
			return this;
		}
		public void execute(String inputFile) throws IOException
		{
			Object obj = inputFile;
			Callable c = new Callable()
			{
				@Override
				public Object guardCallable(Object arg) throws IOException
				{
					if(arg instanceof IInputOutputWrapper)
						return (((IInputOutputWrapper) arg).function());
					else
						return arg;
				}
			};
			for(IFunction arg: funcs)
			{
				obj = arg.call(c.guardCallable(obj));
			}
			c.guardCallable(obj);
		}
	}
	
	public static void main(String args[])
	{
		TFQuarantine tfQuarantineInstance = new TFQuarantine(new IGetInput()
		{
			@Override
			public Object call(Object arg) throws IOException
			{
				IInputOutputWrapper ioWrapper = new IInputOutputWrapper()
				{
					@Override
					public Object function() throws IOException
					{
						return arg.toString();
					}
				};
				return ioWrapper;
			}
		});
		tfQuarantineInstance.bind(new IExtractWords()
		{
			@Override
			public Object call(final Object arg) throws IOException
			{
				IInputOutputWrapper ioWrapper = new IInputOutputWrapper()
				{
					
					@Override
					public Object function() throws IOException
					{
						Scanner scanner = new Scanner(new File(arg.toString()));
						String contents = scanner.useDelimiter("\\Z").next();
						String alphaOnly = contents.replaceAll("[^a-zA-Z]+"," ");
						ArrayDeque<String> tokens = new ArrayDeque<String>();
						StringTokenizer st = new StringTokenizer(alphaOnly);
						while(st.hasMoreTokens())
						{
							String a = st.nextToken();
							
							if(!(a.equalsIgnoreCase(" ")||a.length()==1))
								tokens.add(a);	
						}
						return tokens;
					}
				};
				return ioWrapper;
			}
		}).bind(new IRemoveStopWords()
		{
			@Override
			public Object call(final Object arg) throws IOException {
                IInputOutputWrapper ioWrapper = new IInputOutputWrapper()
				{
					@Override
					public Object function() throws IOException
					{
						Scanner in = new Scanner(new File("stop_words.txt"));
						String stopWords = in.nextLine();
						ArrayList<String> words = new ArrayList<String>(Arrays.asList(stopWords.split(",")));
						ArrayDeque<String> validWords = new ArrayDeque<String>();
				        for (String iter : ((ArrayDeque<String>) arg)) 
				        {
				            if (iter.length() > 1 && !(words.contains(iter))) 
				            {
				                validWords.add(iter);
				            }
				        }
				        return validWords;
					}
				}; 
			return ioWrapper;
		}
		}).bind(new IFrequency()
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
		}).bind(new ISort()
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
		}).bind(new IPrintTopTwentyFive()
		{
			
			@Override
			public Object call(Object arg) throws IOException
			{
				IInputOutputWrapper ioWrapper = new IInputOutputWrapper()
				{
					
					@Override
					public Object function() throws IOException
					{
						ArrayList<Pairs> arrayList = (ArrayList<Pairs>) arg;
						for (int i = 0; i < (Math.min(25, arrayList.size())); i++) 
						{
                            ArrayList<Pairs> arrayList2 = (ArrayList<Pairs>) arg;
							System.out.println(arrayList2.get(i).str + " - "
                                    + arrayList2.get(i).num);
                        }
						return null;
					}
				};
				return ioWrapper;
			}
		});
		try 
		{
			tfQuarantineInstance.execute(args[0]);
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
