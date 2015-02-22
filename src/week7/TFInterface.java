package week7;

import java.util.ArrayList;

public interface TFInterface
{
	ArrayList<String> extractWords(String fileName);
	ArrayList<Pairs> getFrequencies(ArrayList<String> tokens);
	void printFrequencies(ArrayList<Pairs> p);
}
