package week7;

public class TFFactory
{
	public TFInterface getTF(int option)
	{
		if(option == 1)
		{
			return new TFFirstAlternative();
		}
		else if(option == 2)
		{
			return new TFSecondAlternative();
		}
		else
		{
			return null;
		}
			
	}

}
