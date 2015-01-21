package week2;
/**
* Question 6.1 
*/
import java.io.*;
import java.util.*;
import java.util.Map.*;
import org.apache.commons.io.*;
import org.apache.commons.lang3.text.*;
public class CodeGolf {
	public static void main(String[] args) throws IOException {
		List<String> wordList = new ArrayList<String>(Arrays.asList(new StrTokenizer(FileUtils.readFileToString(new File(args[0])).toLowerCase().replaceAll("[^a-zA-Z]+"," ")).getTokenArray()));
		wordList.removeAll(Arrays.asList(new StrTokenizer(FileUtils.readFileToString(new File("stop_words.txt")).replaceAll("[^a-zA-Z]+"," ")).getTokenArray()));
		HashSet<String> uniqueTokens = new HashSet<String>(wordList);
		TreeMap<Integer, String> t = new TreeMap<Integer, String>(Collections.reverseOrder());
		java.util.Iterator<String> iter = uniqueTokens.iterator();
		while (iter.hasNext()) {
			String a = iter.next();
			if(a.length()>1)
				t.put(Collections.frequency(wordList, a),a);
		}
		Iterator i = t.entrySet().iterator();
		int j =0;
	    while(i.hasNext()&&j++<25) {
	       Map.Entry me = (Map.Entry)i.next();
	       System.out.println(me.getValue() + "  -  "+me.getKey());
	    }}}