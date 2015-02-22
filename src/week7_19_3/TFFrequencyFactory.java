package week7_19_3;

public class TFFrequencyFactory
{
	public ITFFrequencies getTFFreqObject(int option)
	{
		if(option == 1)
			return new TFFrequenciesOptionOne();
		else if(option == 2)
			return new TFFrequenciesOptionOne();
		else
			return null;
	}

}
