package week7;

import java.util.ArrayList;

public class TFPrint implements ITFPrint
{
	@Override
	public void printFrequencies(ArrayList<Pairs> p)
	{
		{
			for (int i = 0; i < 25 ; i++) 
	        {
	            System.out.println(p.get(i).str + " - " + (p.get(i).num));
	        }
		}

	}
}
