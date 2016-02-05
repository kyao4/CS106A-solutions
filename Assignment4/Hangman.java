/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;
import java.util.ArrayList;


public class Hangman extends ConsoleProgram {
    
    private static final int NUM_TURNS = 8;


    /**Create a instance of HangmanCanvas which takes up a half of the screen.*/
    public void init(){
    	canvas = new HangmanCanvas();
    	add(canvas);
    }

	public void run() {
		getAWord();
		showWelcome();
		setCanvas();
		while(true){
			checkForWord();
			if(showWordState()){
				break;
			}
		}

	}
	
	private void setCanvas() {
		canvas.reset();
		canvas.displayWord(new_dashes);
	}

	/**show the result of guess and turns left.
     * @return true means stop program because the result of game have given otherwise continue the game.*/
    private boolean showWordState() {
		println("The word now looks like this:" + new_dashes);
		canvas.displayWord(new_dashes); // show word state on canvas.
		println("You have "+ turns + " left.");
		//string can not use == to judge if equal.
		if (new_dashes.equals(chosen_word)){
			println("You win.");
			return true;
		}
		if (turns == 0){
			println("You are completely hung.");
			return true;
		}
		return false;
		
		
	}

    /**check the guess letter if it is in the chosen word and check if you have entered the same
     * letter for more than once, which should not count for a turn.*/
	private void checkForWord() {
		char guess_letter = readInALetter();
		println(chosen_word);
		for(int i = 0; i<chosen_word.length();i++){
			if(guess_letter == chosen_word.charAt(i)){
				new_dashes = new_dashes.substring(0, i) + guess_letter +new_dashes.substring(i+1); 
				if(checkForRepeatLetter(i)){
					continue;
				}
				println("You guess is correct.");
				index_correct_letter.add(i);
				break;
			}
		}
		//check if new_dashes is equal to the ---- or equal to the dashes have being filled.
		if (new_dashes == dashes|| new_dashes == last_dashes){
			println("There are no " + guess_letter + "'s in the word.");
			if(last_guess==guess_letter){
				return;
			}
			last_guess=guess_letter;
			turns--;
			canvas.noteIncorrectGuess(guess_letter); //show incorrect letter on the canvas.
		}
		last_dashes=new_dashes; // keep track of last dashes
		
		
	}


	/**Check for entering correct letter again, 
	 * if it is true ,you should continue the loop of checkforword method.
	 * because it may be two or more correct letter in a word.*/
	private boolean checkForRepeatLetter(int index) {
		for(int i = 0; i <index_correct_letter.size();i++){
			if(index == index_correct_letter.get(i)){
				return true;
			}
		}
		return false;
	}

	/**read in a letter (actually a string) and check if it is a alphebetical letter.*/
	private char readInALetter() {
		String guess_letter = "";
		// Do not forget that you have to repeat requesting the letter if it is not a letter.
		while (true){
			guess_letter = readLine("Your guess:");
			//The length of the letter can not be more than one or less than one!
			if(guess_letter.length()>1||guess_letter.length()<1){
				println("Not a Letter."); continue;
			//The letter can not be other than A to Z or a to z.
			} else if (guess_letter.charAt(0)-'A'<0 ||guess_letter.charAt(0)-'Z'>0 && guess_letter.charAt(0)-'a'<0 || guess_letter.charAt(0)-'z'>0 ){
				println("Not a Letter."); continue;
			} else {
				break;
			}
		}		
		//Character.toUpperCase() will change the char to uppercase. a static method.
		return Character.toUpperCase(guess_letter.charAt(0));
	}

	/**choose a random word.*/
	private void getAWord() {
		//rgen.setSeed(0);
		int num_word = lexicon.getWordCount();
		int num_index = rgen.nextInt(num_word);
		chosen_word = lexicon.getWord(num_index);
		
	}
	/**Print Welcome message.*/
	private void showWelcome() {
    	turns=NUM_TURNS;
    	createDahes();
		println("Welcome to Hangman!");
		println("The word now looks like this: " + new_dashes);
		println("You have "+ turns + " left.");
		
	}
    /**Create dashes according to the length of the word chosen.*/
	private void createDahes() {
    	new_dashes="";
    	//Get the length of the word and decide the length of the dashes.
    	for(int i = 0; i < chosen_word.length(); i++){
    		new_dashes += "-";
    	}
    	dashes=new_dashes;
	}

	/**Keep track of the last dashes in order to compare with new dashes guess.*/
	/*if they are same you guess wrong.
	  if they are not same you guess right and the new dashes has been assigned to last_dashes.*/
	private String last_dashes;

	/**Keep track of the last guess letter in order to know if you have guess the wrong letter more than once.*/
	private char last_guess;

	/**the state of dashes.*/
	private String new_dashes;

	/**Determined by the length of the word chosen.*/
	private String dashes;

	/**Chance remain.*/
	private int turns;

	/**Store the word has been chosen randomly.*/
	private String chosen_word;

	/**Canvas to create a body.*/
	private HangmanCanvas canvas;
    
	private HangmanLexicon lexicon = new HangmanLexicon();
	
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	private ArrayList<Integer> index_correct_letter = new ArrayList<Integer>();

}
