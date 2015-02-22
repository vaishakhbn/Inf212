package week7_19_3;

public class TFWordFactory
{
	public ITFWords getTFWordObject(int option)
	{
		if(option == 1)
			return new TFWordsOptionOne();
		else if(option == 2)
			return new TFWordsOptionOne();
		else
			return null;
	}
}
