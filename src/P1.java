import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;


public class P1 {
	public static void main(String[] args) {
				
		//Contains individual words from input file
		ArrayList<String> wordList = new ArrayList<String>();
		//<.*?>match a set of closed html brackets with unlimited content as a delimiter
		Pattern tagPattern = Pattern.compile("<.*?>");
		//"[^a-zA-z0-9']" sets any character NOT listed as a delimiter.
		Pattern wordPattern = Pattern.compile("[^a-zA-z0-9']+");
		//String containing file stripped of HTML tags
		String strippedString = "";
		
		
		try
		{
			
			File inFile = new File(args[0]);
			Scanner inStream = new Scanner (inFile);
			inStream.useDelimiter(tagPattern);
			
			/***Strip code segments from file and create new temporary string***/
			if(!inStream.hasNext())
			{
				System.err.println("Error: Empty File");
			}
			while(inStream.hasNext())
			{
				strippedString += (inStream.next() + "");
			}
			inStream.close();

			
			/*** Parse strippedString for words, add new instances to wordList ***/
			Scanner cleanScan = new Scanner(strippedString);
			cleanScan.useDelimiter(wordPattern);
			while(cleanScan.hasNext())
			{
				addUnique(wordList, cleanScan.next().toLowerCase());
			}
			cleanScan.close();
			
			/*** Write sorted wordList and header/footer to file ***/
			System.out.println("WORDS");
			for(String s : wordList)
			{
				System.out.println(s);
			}
			System.out.println();
			System.out.println("NUMBER OF WORDS: " + wordList.size());
		}
		catch(IOException e)
		{
			System.err.println("Error: " + e);
			System.err.println("Input file inaccessible.");
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.err.println("Error: " + e);
			System.err.println("No input arguments were supplied.");
		}

	}
	
	/*** Checks if word is in wordList already. Returns -(index) if word exists, index where word should be inserted otherwise ***/
	private static int binarySearch(ArrayList<String> wordList, String keyWord, int first,int last)
	{
		if(wordList.size() != 0)
		{
			int mid = (first+last)/2;
			int test = keyWord.compareTo((String) wordList.get(mid));
			if(test == 0)
			{
				return -1;
			}
			else if (first < last)
			{
				if(test < 0)
				{
					return binarySearch(wordList, keyWord, first, mid-1);
				}
				else
				{
					return binarySearch(wordList, keyWord, mid+1, last);
				}
			}
			else
			{
				if(test < 0)
				{
					return mid;
				}
				else
				{
					return mid+1;
				}
			}
		}
		return 0;
	}
	
	/*** Appends new word to list if first occurrence, discards otherwise ***/
	private static void addUnique(ArrayList<String> wordList, String newWord)
	{
		int i = binarySearch(wordList, newWord, 0, wordList.size()-1);
		if(i>=0)
		{
			wordList.add(i, newWord);
		}
	}
}
