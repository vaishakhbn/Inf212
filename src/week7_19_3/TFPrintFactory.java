package week7_19_3;

public class TFPrintFactory
{
	public ITFPrint getTFPrintObject(int option)
	{
		if(option == 1)
			return new TFPrintOptionOne();
		else if(option == 2)
			return new TFPrintOptionOne();
		else
			return null;
	}
}
