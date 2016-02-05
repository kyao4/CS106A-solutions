/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {



//Instance variables for a body.

private GOval head;

private GLine body;

private GLine leftArm_x;

private GLine leftArm_y;

private GLine rightArm_y;

private GLine rightArm_x;

private GLine rightLeg_y;

private GLine rightLeg_x;

private GLine leftLeg_y;

private GLine leftLeg_x;

private GLine rightFoot;

private GLine leftFoot;

/** Resets the display so that only the scaffold appears */
	public void reset() {
		wrong_counter = 0; //keep track of how many times guess wrong.
		removeAll();  //clear canvas.
		setupLetter();
		setupWord();
		GLine scaffold_y = new GLine(APPLICATION_WIDTH/4,APPLICATION_HEIGHT-OFFSET_LABEL, APPLICATION_WIDTH/4, APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT);
		GLine scaffold_x = new GLine(APPLICATION_WIDTH/4,APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT, APPLICATION_WIDTH/4+BEAM_LENGTH, APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT);
		GLine scaffold_rope = new GLine(APPLICATION_WIDTH/4+BEAM_LENGTH, APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT, APPLICATION_WIDTH/4+BEAM_LENGTH, APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT+ROPE_LENGTH);
		add(scaffold_y);
		add(scaffold_x);
		add(scaffold_rope);
		setupBody();
	}

	/**Create all the object a body need.
	 * Have not yet added it to canvas.*/
	private void setupBody() {
	 head = new GOval(APPLICATION_WIDTH/4+BEAM_LENGTH-HEAD_RADIUS, APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT+ROPE_LENGTH, 2*HEAD_RADIUS, 2*HEAD_RADIUS);
	 body = new GLine(APPLICATION_WIDTH/4+BEAM_LENGTH,APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT+ROPE_LENGTH+2*HEAD_RADIUS,APPLICATION_WIDTH/4+BEAM_LENGTH,APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT+ROPE_LENGTH+2*HEAD_RADIUS+BODY_LENGTH);
	 leftArm_x = new GLine(APPLICATION_WIDTH/4+BEAM_LENGTH,APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT+ROPE_LENGTH+2*HEAD_RADIUS+ARM_OFFSET_FROM_HEAD,APPLICATION_WIDTH/4+BEAM_LENGTH-UPPER_ARM_LENGTH,APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT+ROPE_LENGTH+2*HEAD_RADIUS+ARM_OFFSET_FROM_HEAD);
	 leftArm_y = new GLine(APPLICATION_WIDTH/4+BEAM_LENGTH-UPPER_ARM_LENGTH,APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT+ROPE_LENGTH+2*HEAD_RADIUS+ARM_OFFSET_FROM_HEAD,APPLICATION_WIDTH/4+BEAM_LENGTH-UPPER_ARM_LENGTH,APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT+ROPE_LENGTH+2*HEAD_RADIUS+ARM_OFFSET_FROM_HEAD+LOWER_ARM_LENGTH);
	 rightArm_x = new GLine(APPLICATION_WIDTH/4+BEAM_LENGTH,APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT+ROPE_LENGTH+2*HEAD_RADIUS+ARM_OFFSET_FROM_HEAD,APPLICATION_WIDTH/4+BEAM_LENGTH+UPPER_ARM_LENGTH,APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT+ROPE_LENGTH+2*HEAD_RADIUS+ARM_OFFSET_FROM_HEAD);
	 rightArm_y = new GLine(APPLICATION_WIDTH/4+BEAM_LENGTH+UPPER_ARM_LENGTH,APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT+ROPE_LENGTH+2*HEAD_RADIUS+ARM_OFFSET_FROM_HEAD,APPLICATION_WIDTH/4+BEAM_LENGTH+UPPER_ARM_LENGTH,APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT+ROPE_LENGTH+2*HEAD_RADIUS+ARM_OFFSET_FROM_HEAD+LOWER_ARM_LENGTH);
	 leftLeg_x = new GLine(APPLICATION_WIDTH/4+BEAM_LENGTH,APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT+ROPE_LENGTH+2*HEAD_RADIUS+BODY_LENGTH,APPLICATION_WIDTH/4+BEAM_LENGTH-HIP_WIDTH,APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT+ROPE_LENGTH+2*HEAD_RADIUS+BODY_LENGTH);
	 leftLeg_y = new GLine(APPLICATION_WIDTH/4+BEAM_LENGTH-HIP_WIDTH,APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT+ROPE_LENGTH+2*HEAD_RADIUS+BODY_LENGTH,APPLICATION_WIDTH/4+BEAM_LENGTH-HIP_WIDTH,APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT+ROPE_LENGTH+2*HEAD_RADIUS+BODY_LENGTH+LEG_LENGTH);
	 rightLeg_x = new GLine(APPLICATION_WIDTH/4+BEAM_LENGTH,APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT+ROPE_LENGTH+2*HEAD_RADIUS+BODY_LENGTH,APPLICATION_WIDTH/4+BEAM_LENGTH+HIP_WIDTH,APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT+ROPE_LENGTH+2*HEAD_RADIUS+BODY_LENGTH);
	 rightLeg_y = new GLine(APPLICATION_WIDTH/4+BEAM_LENGTH+HIP_WIDTH,APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT+ROPE_LENGTH+2*HEAD_RADIUS+BODY_LENGTH,APPLICATION_WIDTH/4+BEAM_LENGTH+HIP_WIDTH,APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT+ROPE_LENGTH+2*HEAD_RADIUS+BODY_LENGTH+LEG_LENGTH);
	 leftFoot = new GLine(APPLICATION_WIDTH/4+BEAM_LENGTH-HIP_WIDTH,APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT+ROPE_LENGTH+2*HEAD_RADIUS+BODY_LENGTH+LEG_LENGTH,APPLICATION_WIDTH/4+BEAM_LENGTH-HIP_WIDTH-FOOT_LENGTH,APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT+ROPE_LENGTH+2*HEAD_RADIUS+BODY_LENGTH+LEG_LENGTH);
	 rightFoot = new GLine(APPLICATION_WIDTH/4+BEAM_LENGTH+HIP_WIDTH,APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT+ROPE_LENGTH+2*HEAD_RADIUS+BODY_LENGTH+LEG_LENGTH,APPLICATION_WIDTH/4+BEAM_LENGTH+HIP_WIDTH+FOOT_LENGTH,APPLICATION_HEIGHT-OFFSET_LABEL-SCAFFOLD_HEIGHT+ROPE_LENGTH+2*HEAD_RADIUS+BODY_LENGTH+LEG_LENGTH);
	 
	 
}

	/**Create the label of the state of the Word you are guessing.*/
	private void setupWord() {
		new_dashes = "";
		dashes_state = new GLabel(new_dashes, APPLICATION_WIDTH/8, APPLICATION_HEIGHT-OFFSET_LABEL*2/4);
		dashes_state.setFont("New Times-50");
		add(dashes_state);
	
}

	/**Create the label of the letter you have guessed wrong.*/
	private void setupLetter() {
		wrong_str = "";
		wrong_letter = new GLabel(wrong_str, APPLICATION_WIDTH/8, APPLICATION_HEIGHT-OFFSET_LABEL/4);
		wrong_letter.setFont("New Times-25");
		add(wrong_letter);
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		dashes_state.setLabel(word);
		
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(char letter) {
		wrong_str += letter;
		wrong_letter.setLabel(wrong_str);
		oneStepToHangman(wrong_counter);
		wrong_counter++;
		
	}


	/**Add body's object step by step, according to wrong_counter.*/
	private void oneStepToHangman(int wrong_counter) {

		switch (wrong_counter){
			case 0: add(head);break;
			case 1: add(body);break;
			case 2: add(leftArm_x);add(leftArm_y);break;
			case 3: add(rightArm_x);add(rightArm_y);break;
			case 4: add(leftLeg_x);add(leftLeg_y);break;
			case 5: add(rightLeg_x);add(rightLeg_y);break;
			case 6: add(leftFoot);break;
			case 7: add(rightFoot);break;
			
		}
	}



	/* Constants for the simple version of the picture (in pixels) */
	

	private static final int OFFSET_LABEL = 150;
	
	private static final int APPLICATION_WIDTH = 400;
	
	private static final int APPLICATION_HEIGHT = 600;
	
	private static final int SCAFFOLD_HEIGHT = 360;
	
	private static final int BEAM_LENGTH = 144;
	
	private static final int ROPE_LENGTH = 18;
	
	private static final int HEAD_RADIUS = 36;
	
	private static final int BODY_LENGTH = 144;
	
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	
	private static final int UPPER_ARM_LENGTH = 72;
	
	private static final int LOWER_ARM_LENGTH = 44;
	
	private static final int HIP_WIDTH = 36;
	
	private static final int LEG_LENGTH = 108;
	
	private static final int FOOT_LENGTH = 28;
	
	/**To keep the wrong letters in a string.*/
	private String wrong_str;

	/**Label of the wrong letters you have guessed.*/
	private GLabel wrong_letter;

	/**Store the dashes state.*/
	private String new_dashes;
	
	/**Labels To keep the dashes state in.*/
	private GLabel dashes_state;
	
	/**keep track of the times you have guessed wrong.*/
	private int wrong_counter;




}
