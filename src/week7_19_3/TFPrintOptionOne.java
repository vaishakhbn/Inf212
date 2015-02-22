package week7_19_3;

import java.util.ArrayList;


public class TFPrintOptionOne implements ITFPrint
{
	@Override
	public void printFrequencies(ArrayList<week7_19_3.Pairs> p)
	{
		for (int i = 0; i < 25 ; i++) 
        {
            System.out.println(p.get(i).str + " - " + (p.get(i).num));
        }
		
	}
}
