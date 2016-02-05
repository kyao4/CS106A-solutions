/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import acm.util.*;

public class HangmanLexicon {
	
	public HangmanLexicon () {
		BufferedReader rd=createBufferedReader();		
		readInLine(rd);
	}
	

	/**Read in line the file you have buffered. You have to catch exception explicitly.*/
	private void readInLine(BufferedReader rd) {
		try{
			while(rd.readLine() != null){
				AList.add(rd.readLine());	
			}
		} catch(IOException ex) {
			throw new ErrorException(ex);
		}
				
	}


	/**Create a buffered reader in which you should assign file name correctly.Otherwise the program may stop in the period of initialization.*/
	private BufferedReader createBufferedReader() {
		try {
			BufferedReader rd = new BufferedReader(new FileReader(FILE_NAME));
			return rd;
		} catch (IOException ex) {
			throw new ErrorException(ex);
		}
	}


/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return AList.size();
	}

/** Returns the word at the specified index. */
	public String getWord(int index) {
		return AList.get(index);
		/*
		switch (index) {
			case 0: return "BUOY";
			case 1: return "COMPUTER";
			case 2: return "CONNOISSEUR";
			case 3: return "DEHYDRATE";
			case 4: return "FUZZY";
			case 5: return "HUBBUB";
			case 6: return "KEYHOLE";
			case 7: return "QUAGMIRE";
			case 8: return "SLITHER";
			case 9: return "ZIRCON";
			default: throw new ErrorException("getWord: Illegal index");
		}
		*/
	}
	
	private final static String FILE_NAME = "HangmanLexicon.txt";
	private ArrayList<String> AList = new ArrayList<String>();
}
