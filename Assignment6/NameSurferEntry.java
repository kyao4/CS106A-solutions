/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import java.util.*;


public class NameSurferEntry implements NameSurferConstants {

/* Constructor: NameSurferEntry(line) */
/**
 * Creates a new NameSurferEntry from a data line as it appears
 * in the data file.  Each line begins with the name, which is
 * followed by integers giving the rank of that name for each
 * decade.
 */
	public NameSurferEntry(String line) {
		name = line.substring(0,line.indexOf(' '));
		int firstIndexOfSpace = line.indexOf(" ");
		int indexOfSpace = line.indexOf(" ", firstIndexOfSpace+1);
		rankList.add(Integer.parseInt(line.substring(firstIndexOfSpace+1,indexOfSpace)));
		
		int lastIndexOfspace = indexOfSpace;;
		while(true){
			indexOfSpace = line.indexOf(" ", lastIndexOfspace+1);
			if(indexOfSpace == -1){
				rankList.add(Integer.parseInt(line.substring(lastIndexOfspace+1)));
				break;
			}
			rankList.add(Integer.parseInt(line.substring(lastIndexOfspace+1,indexOfSpace)));
			lastIndexOfspace = indexOfSpace;
		}
		
	}

/* Method: getName() */
/**
 * Returns the name associated with this entry.
 */
	public String getName() {
		return name;
	}

/* Method: getRank(decade) */
/**
 * Returns the rank associated with an entry for a particular
 * decade.  The decade value is an integer indicating how many
 * decades have passed since the first year in the database,
 * which is given by the constant START_DECADE.  If a name does
 * not appear in a decade, the rank value is 0.
 */
	public int getRank(int decade) {
		return rankList.get(decade-START_DECADE);
	}

/* Method: toString() */
/**
 * Returns a string that makes it easy to see the value of a
 * NameSurferEntry.
 */
	public String toString() {
		String rank = String.valueOf(rankList.get(0));
		for(int i = 1; i < rankList.size(); i++){
			rank = rank + " " + String.valueOf(rankList.get(i));
		}
		return name + " " + "[" + rank + "]";
	}
	
	private String name;
	/*
	 *  ArrayList can not be static, if so the whole array will 
	 *  increase with the number of entries increase. 
	 *  but not set up a new Array for each one of them.
	 */
	private ArrayList<Integer> rankList = new ArrayList<Integer>();
}

