using System;
using System.Collections.Generic;
using System.IO;
using System.Collections;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

/*
* pardon my syntax. This is my first C# program.
*The regex has been adapted from another student's previous code gold assignment. 
*/

namespace LazyRivers
{
    class LazyRivers
    {
        public static void Main(string[] args)
        {
            foreach(Dictionary<string, int>  d in countAndSort(args[0]))
                foreach(KeyValuePair<string,int> kve in d.AsEnumerable().Take(25))
                    Console.WriteLine("{0} - {1}", kve.Key, kve.Value);
        }
        public static IEnumerable getLines(String fileName)
        {
            string line = null;
            System.IO.StreamReader file = new System.IO.StreamReader(fileName);
            while((line = file.ReadLine()) != null)
            {
                    yield return line;
            }
        }
        public static IEnumerable getWords(String fileName)
        {
            foreach(string line in getLines(fileName))
            {
                string[] wordArray = (Regex.Split(line, @"[^a-zA-Z]")).Where(word => word.Length != 1 && !String.IsNullOrWhiteSpace(word)).Select(word => word.ToLowerInvariant()).ToArray();
                foreach(string word in wordArray)
                {
                    yield return word;
                }
            }
        }
        public static IEnumerable getNonStopWords(String fileName)
        {
            string[] stopWordArray = Regex.Split(File.ReadAllText("../stop_words.txt"), @"[^a-zA-Z]");
            foreach(string normalWord in getWords(fileName))
                        if(!stopWordArray.Contains(normalWord))
                            yield return normalWord;
        }
        public static IEnumerable countAndSort(String fileName)
        {
            Dictionary<string,int> freqs=new Dictionary<string,int>();
            foreach(string w in getNonStopWords(fileName))
            {
                if(freqs.ContainsKey(w))
                    freqs[w] +=1;
                else
                {
                    freqs.Add(w,1);
                }
            }
            yield return freqs.OrderByDescending(x => x.Value).ToDictionary(x => x.Key, x => x.Value);        
        }
    }
}
