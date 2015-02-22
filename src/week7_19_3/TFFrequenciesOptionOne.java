package week7_19_3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TFFrequenciesOptionOne implements ITFFrequencies
{

	@Override
	public ArrayList<Pairs> getFrequencies(ArrayList<String> tokens)
	{
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

	}

}
