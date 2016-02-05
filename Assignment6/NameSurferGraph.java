/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;

import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {

	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() {
		addComponentListener(this);
		update();
	}
	
	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {
		entryList.clear();
		update();
	}
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {
		for(NameSurferEntry ownEntry : entryList){
			if(ownEntry == entry) return;
		}
		entryList.add(entry);
		update();
	}
	
	
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		removeAll();
		
		//set up gird.
		int applicationHeight = getHeight();
		int applicationWidth = getWidth();
		int widthOfEachDecade = getWidth()/NDECADES;
		for(int i = 0; i < NDECADES; i++){
			add(new GLine(widthOfEachDecade*i,0,widthOfEachDecade*i,applicationHeight));	
		}
		add(new GLine(0,GRAPH_MARGIN_SIZE,applicationWidth,GRAPH_MARGIN_SIZE));
		add(new GLine(0,applicationHeight-GRAPH_MARGIN_SIZE,applicationWidth,applicationHeight-GRAPH_MARGIN_SIZE));
		for(int i = 1900 ; i< 1900+NDECADES; i++){
			GLabel label = new GLabel(String.valueOf(i));
			label.setLocation(widthOfEachDecade*(i-1900), applicationHeight-GRAPH_MARGIN_SIZE+label.getAscent());
			add(label);
		}
		
		
		for(int i = 0; i< entryList.size();i++){
			
			//Give the height of valid displaying area.
			int graphRankSize = getHeight()-GRAPH_MARGIN_SIZE;
			
			//setup entry that called for display and it's name.
			NameSurferEntry entry = entryList.get(i);
			String entryName = entry.getName();
			
			for(int j = 1900; j < 1900+NDECADES-1;j++){
				
				// Add lines.
				int lastYearRank = GRAPH_MARGIN_SIZE+entry.getRank(j)*graphRankSize/MAX_RANK;
				int thisYearRank = GRAPH_MARGIN_SIZE+entry.getRank(j+1)*graphRankSize/MAX_RANK;
				if(entry.getRank(j) == 0) lastYearRank = applicationHeight-GRAPH_MARGIN_SIZE;
				if(entry.getRank(j+1) == 0) thisYearRank = applicationHeight-GRAPH_MARGIN_SIZE;
				int numOfLastYear = j-1900;
				int numOfThisYear = j-1900+1;
				
				GLine line = new GLine( widthOfEachDecade*(numOfLastYear) , lastYearRank , widthOfEachDecade*(numOfThisYear) , thisYearRank );
				line.setColor(entryColor(i));
				add(line);
				

				
				//Add label for each year
				entryName = entry.getName();
				if( entry.getRank(j+1) == 0 ) {
					entryName = entryName + "*";
				} else{
					entryName = entryName + " " + String.valueOf(entry.getRank(j+1));
				}
				

				GLabel label = new GLabel(entryName, widthOfEachDecade*(numOfThisYear) , thisYearRank );
				label.setColor(entryColor(i));
				add(label);
				
				
				
				//Special code for fist line.
				if(j == 1900){
					
					//Draw the first line.
					line = new GLine(0,0,0, entry.getRank(j));
					line.setColor(entryColor(i));
					add(line);
					
					//Initialize the first label
					entryName = entry.getName();
					if(entry.getRank(1900) == 0 ) {
						entryName = entryName + "*";
					} else{
						entryName = entryName + " " + String.valueOf(entry.getRank(j));
					}
					
					//LastYearRank in this state means 1900.
					if(entry.getRank(1900) == 0) lastYearRank = applicationHeight-GRAPH_MARGIN_SIZE;
					label = new GLabel(entryName,0,lastYearRank);
					label.setColor(entryColor(i));
					add(label);
				}
			}
		}
	}
	
	
	
	
	private Color entryColor(int i) {
		Color c = Color.black;
		switch((i+4)%4){
			case 0 : c = Color.black;break;
			case 1 : c = Color.red;break;
			case 2 : c = Color.green;break;
			case 3 : c = Color.blue;break;
		}
		return c;
	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
	
	private ArrayList<NameSurferEntry> entryList = new ArrayList<NameSurferEntry>();
}
