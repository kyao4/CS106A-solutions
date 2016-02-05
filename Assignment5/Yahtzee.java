/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}

	private int[][] scoreForPlayers;
	private int chosenCategory;
	private int[][] preChosenCategoryForPlayers;
	private int[] upper_score_result;
	private int[] upper_bonus_result;
	private int[] lower_score_result;
	private int[] total_score_result;
	private int numOfPlayers;
	private int[] indexOfDice;
	
	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	/**Basic structure of game.*/
	private void playGame() {
		setupArray();
		paly();
		calculateFinal();
		dispalyFinal();
		dispalyWinner();
	}
		


	private void dispalyWinner() {
		int winnerScore = 0;
		int indexOfWinner = 0;
		for(int j = 0 ; j< nPlayers; j++){
			winnerScore = total_score_result[j];
			indexOfWinner = j;
			for(int i = j+1; i < nPlayers; i++){ 
				if(winnerScore < total_score_result[i]){
					winnerScore = total_score_result[i];
					indexOfWinner = i;
				}
			}
			if(indexOfWinner == j){
				break;
			}
		}
		display.printMessage("Congratulations, " + playerNames[indexOfWinner] +", you are the winner with a total score of " + winnerScore + ".");
		
	}

	/**Set up Array for keeping track of scores and categories.*/
	private void setupArray() {
			upper_score_result = new int[nPlayers];
			upper_bonus_result = new int[nPlayers];
			lower_score_result = new int[nPlayers];
			total_score_result = new int[nPlayers];
			scoreForPlayers = new int[17][nPlayers];
			preChosenCategoryForPlayers = new int[17][nPlayers];
		}
		
	private void calculateFinal() {
		for(int j = 1; j <= nPlayers; j++){
			
			//clear the value
			upper_score_result[j-1] = 0;
			upper_bonus_result[j-1] = 0;
			lower_score_result[j-1] = 0;
			
			//upperscore
			for(int i = ONES; i <= SIXES;i++){
				upper_score_result[j-1] += scoreForPlayers[i-1][j-1];
			}
			
			//Bonus
			if(upper_score_result[j-1] >= 63 ){
				//You have to assaign bonus value to score board because you did not calculate before.
				upper_bonus_result[j-1] = scoreForPlayers[UPPER_BONUS-1][j-1] = 35;
			}
			
			//lowerscore
			for(int i = UPPER_BONUS; i <= CHANCE;i++){
				lower_score_result[j-1] += scoreForPlayers[i-1][j-1];
			}
			
			//calculatetotal_score_result receive the num of palyers
			calculatetotal_score_result(j);
		}

	}


	/**Display the total scores and upper scores lower scores and upper bonus.*/
	private void dispalyFinal() {
		for(int j = 1; j <= nPlayers; j++){
			display.updateScorecard(UPPER_SCORE,j,upper_score_result[j-1]);
			
			display.updateScorecard(UPPER_BONUS,j,upper_bonus_result[j-1]);
			
			display.updateScorecard(LOWER_SCORE,j,lower_score_result[j-1]);
			
			display.updateScorecard(TOTAL,j,total_score_result[j-1]);
		}
		
	}

	/**Main frame structure for paly.*/
	private void paly() {
		for (int i = 1; i <= 13; i++){
			for(int j = 1; j <= nPlayers; j++){
				numOfPlayers = j;
				playForturns();
			}
		}
	}

	/**Each turn structures for paly.*/
	private void playForturns() {
		showNameMessage();
		display.waitForPlayerToClickRoll(numOfPlayers);
		rollDice();
		display.displayDice(indexOfDice);
		showWaitForSelectMessage();
		display.waitForPlayerToSelectDice();
		updateDice();
		showWaitForSelectMessage();
		display.waitForPlayerToSelectDice();
		updateDice();
		

		//Show waiting for selecting category message.
		display.printMessage("Select a category for this roll.");
		/*Check if players have chosen the correct category.*/
		if (checkForSelectCategory()){
			display.updateScorecard(chosenCategory, numOfPlayers, calculateforscore());
		}else{
			display.updateScorecard(chosenCategory, numOfPlayers, 0);
		}
		
		
		
		//Calculate and display total score, whatever the category is correct or not.
		calculatetotal_score_result(numOfPlayers);
		display.updateScorecard(TOTAL,numOfPlayers,total_score_result[numOfPlayers-1]);
		
		//Keep track of the category chosen at the end of every turn.
		preChosenCategoryForPlayers[chosenCategory-1][numOfPlayers-1] = chosenCategory;
		
		/**test
		calculateFinal();
		dispalyFinal();
		dispalyWinner();
		*/
		
		
	}


	private void showWaitForSelectMessage() {
		display.printMessage("Select the dice you wish to re-roll and click \"Roll Again\".");
		
	}

	private void showNameMessage() {
		display.printMessage( playerNames[numOfPlayers-1] + "'s turn. Click \"Roll Dice\" button to roll the dice.");
		
	}

	/**Calculate total scores. You have to clear the last score every time you calculate for a new total score.*/
	private void calculatetotal_score_result(int numOfPlayers) {
		total_score_result[numOfPlayers-1] =0;
		for(int j = ONES; j <= SIXES ; j++){
			total_score_result[numOfPlayers-1] += scoreForPlayers[j-1][numOfPlayers-1];
		}
		for(int j = UPPER_BONUS; j <= CHANCE ; j++){
			total_score_result[numOfPlayers-1] += scoreForPlayers[j-1][numOfPlayers-1];
		}
	}

	/**Calculate score for the category you have chosen.
	 * pre-condition: the categroy has been comfirmed correct for assaigning scores.
	 * @return score for the category you have chosen*/
	private int calculateforscore() {
		int result = 0;
		switch(chosenCategory){
			case 1: scoreForPlayers[chosenCategory-1][numOfPlayers-1] = result = calculateOneToSix();break;
			case 2: scoreForPlayers[chosenCategory-1][numOfPlayers-1] = result = calculateOneToSix();break;
			case 3: scoreForPlayers[chosenCategory-1][numOfPlayers-1] = result = calculateOneToSix();break;
			case 4: scoreForPlayers[chosenCategory-1][numOfPlayers-1] = result = calculateOneToSix();break;
			case 5: scoreForPlayers[chosenCategory-1][numOfPlayers-1] = result = calculateOneToSix();break;
			case 6: scoreForPlayers[chosenCategory-1][numOfPlayers-1] = result = calculateOneToSix();break;
		}
		
		switch(chosenCategory){
			case 9:scoreForPlayers[chosenCategory-1][numOfPlayers-1] = result = calculateKIND(THREES);break;
			case 10:scoreForPlayers[chosenCategory-1][numOfPlayers-1] = result = calculateKIND(FOURS);break;
			case 11:scoreForPlayers[chosenCategory-1][numOfPlayers-1] = result = calculateFULL_HOUSE();break;
			case 12:scoreForPlayers[chosenCategory-1][numOfPlayers-1] = result = calculateSMALL_STRAIGHT();break;
			case 13:scoreForPlayers[chosenCategory-1][numOfPlayers-1] = result = calculateLARGE_STRAIGHT();break;
			case 14:scoreForPlayers[chosenCategory-1][numOfPlayers-1] = result = calculateYAHTZEE();break;
			case 15:scoreForPlayers[chosenCategory-1][numOfPlayers-1] = result = calculateCHANCE();break;
		}

		return result;
	}


	private int calculateCHANCE() {
		int result = 0;
		for(int i = 0;i<N_DICE;i++){
			result += indexOfDice[i];
		}
		return result;
	}

	private int calculateYAHTZEE() {
		return 50;
	}

	private int calculateLARGE_STRAIGHT() {
		return 40;
	}

	private int calculateSMALL_STRAIGHT() {
		return 30;
	}

	private int calculateFULL_HOUSE() {
		return 25;
	}


	/**Three of a Kind. At least three of the dice must show the same value. 
	 * The score is equal to the sum of all of the values showing on the dice.
	 * Four of a Kind. At least four of the dice must show the same value. 
	 * The score is equal to the sum of all of the values showing on the dice.
	 * @param num_same the amount of same numbers in a kind.
	 * @return the score of kind.*/
	private int calculateKIND(int num_same) {
		int result = 0;
		int counter = 0;
		for(int j = 0; j <3;j++,counter = 0){
			int compare = indexOfDice[j];
			for(int i = 0; i<N_DICE; i++){
				if(indexOfDice[i] == compare){
					counter++;
				}
				if (counter >=num_same) {
					for(i = 0; i<N_DICE;i++){
						result += indexOfDice[i];
					}
					return result;
				}
				
			}			
		}
		return result;
	}

	/**Any dice configuration  is valid for these categories. 
	 * The score is equal to the sum 
	 * of the 2¡¯s, 3¡¯s, 4¡¯s, and so on, showing on the dice.*/
	private int calculateOneToSix() {
		int result = 0;
		for(int i = 0;i<N_DICE;i++){
			if(indexOfDice[i] == chosenCategory) result += chosenCategory;
		}
		
		return result;
	}

	/**Check if you dice num is suit for the category chosen.
	 * if you choose that category again, you will be asked for another category.*/
	private boolean checkForSelectCategory() {
		
		boolean p = false;
		chosenCategory = display.waitForPlayerToSelectCategory();
		
		
		//Prevent from select a category have chosen. You should also show selecting category message.
		while (chosenCategory == preChosenCategoryForPlayers[chosenCategory-1][numOfPlayers-1]){
			
			//Show waiting for selecting category message.
			display.printMessage("Select a category for this roll.");
			
			
			chosenCategory = display.waitForPlayerToSelectCategory();
		}
		
		/*My own check category method.*/
		switch(chosenCategory){
			case 1:p = checkCategory(indexOfDice, ONES);break;
			case 2:p = checkCategory(indexOfDice, TWOS);break;
			case 3:p = checkCategory(indexOfDice, THREES);break;
			case 4:p = checkCategory(indexOfDice, FOURS);break;
			case 5:p = checkCategory(indexOfDice, FIVES);break;
			case 6:p = checkCategory(indexOfDice, SIXES);break;
			case 9:p = checkCategory(indexOfDice, THREE_OF_A_KIND);break;
			case 10:p = checkCategory(indexOfDice, FOUR_OF_A_KIND);break;
			case 11:p = checkCategory(indexOfDice, FULL_HOUSE);break;
			case 12:p = checkCategory(indexOfDice, SMALL_STRAIGHT);break;
			case 13:p = checkCategory(indexOfDice, LARGE_STRAIGHT);break;
			case 14:p = checkCategory(indexOfDice, YAHTZEE);break;
			case 15:p = checkCategory(indexOfDice, CHANCE);break;
		}
		
		/**Provided check category method.
		switch(chosenCategory){
			case 1:p = YahtzeeMagicStub.checkCategory(indexOfDice, ONES);break;
			case 2:p = YahtzeeMagicStub.checkCategory(indexOfDice, TWOS);break;
			case 3:p = YahtzeeMagicStub.checkCategory(indexOfDice, THREES);break;
			case 4:p = YahtzeeMagicStub.checkCategory(indexOfDice, FOURS);break;
			case 5:p = YahtzeeMagicStub.checkCategory(indexOfDice, FIVES);break;
			case 6:p = YahtzeeMagicStub.checkCategory(indexOfDice, SIXES);break;
			case 9:p = YahtzeeMagicStub.checkCategory(indexOfDice, THREE_OF_A_KIND);break;
			case 10:p = YahtzeeMagicStub.checkCategory(indexOfDice, FOUR_OF_A_KIND);break;
			case 11:p = YahtzeeMagicStub.checkCategory(indexOfDice, FULL_HOUSE);break;
			case 12:p = YahtzeeMagicStub.checkCategory(indexOfDice, SMALL_STRAIGHT);break;
			case 13:p = YahtzeeMagicStub.checkCategory(indexOfDice, LARGE_STRAIGHT);break;
			case 14:p = YahtzeeMagicStub.checkCategory(indexOfDice, YAHTZEE);break;
			case 15:p = YahtzeeMagicStub.checkCategory(indexOfDice, CHANCE);break;
			
		}
		*/
		return p;
		
	}
	
	private boolean checkCategory(int[] indexOfDice, int categoryNumbers){
		boolean p = false;
		switch (categoryNumbers){
			case 1:p = checkOneToSix(indexOfDice);break;
			case 2:p = checkOneToSix(indexOfDice);break;
			case 3:p = checkOneToSix(indexOfDice);break;
			case 4:p = checkOneToSix(indexOfDice);break;
			case 5:p = checkOneToSix(indexOfDice);break;
			case 6:p = checkOneToSix(indexOfDice);break;
			case 9:p = checkKIND(indexOfDice,categoryNumbers);break;
			case 10:p = checkKIND(indexOfDice,categoryNumbers);break;
			case 11:p = checkFULL_HOUSE(indexOfDice);break;
			case 12:p = checkSTRAIGHT(indexOfDice,categoryNumbers);break;
			case 13:p = checkSTRAIGHT(indexOfDice,categoryNumbers);break;
			case 14:p = checkYAHTZEE(indexOfDice);break;
			case 15:p = checkCHANCE(indexOfDice);break;
		}
		return p;
	}



	/**Check for category players have chosen suit for the straight whatever small or large.*/
	private boolean checkSTRAIGHT(int[] indexOfDice, int categoryNumbers) {
		int failTimesCounterForDice = 0;
		int failTimesCounterForRound = 0;
		int numOfSTRAIGHT = categoryNumbers- 8;
		for(int i = 0;i <= 6-numOfSTRAIGHT;i++){
			for(int k = i+1; k <= i+numOfSTRAIGHT;k++,failTimesCounterForDice = 0){
				for(int j = 0;j < N_DICE;j++){
					if(indexOfDice[j] == k){
						break;
					}else{
						failTimesCounterForDice++;
					}
					if(failTimesCounterForDice == N_DICE){
						break;
					}
				}
				if(failTimesCounterForDice == N_DICE){
					failTimesCounterForRound++;
					break;
				}
			}
			
			/*For a straight of length of 5 you have to consider 2 different situations, which is  12345 23456.
			 * Same for length of 4,you have to consider 6 different situations.*/
			if(numOfSTRAIGHT == 5){
				if(failTimesCounterForRound == 2) {
					return false; 	
				}
			}else if(numOfSTRAIGHT == 4){
				if(failTimesCounterForRound == 3) {
					return false; 
				}
			}
			
		}
		return true;
	}

	private boolean checkCHANCE(int[] indexOfDice) {
		return true;
	}

	private boolean checkYAHTZEE(int[] indexOfDice) {
		for(int i = 0;i<N_DICE-1;i++){
			if(indexOfDice[i] == indexOfDice[i+1]){
				continue;
			}else {
				return false;
			}
		}
		return true;
	}

	private boolean checkFULL_HOUSE(int[] indexOfDice) {
		int[] tempArray = new int[N_DICE];
		int counter = 0;
		for(int j = 0;j < N_DICE;j++,counter = 0){
			int sameValue = indexOfDice[j];
			tempArray[j] = indexOfDice[j];
			for(int i = 0;i < N_DICE;i++){
				if (sameValue == indexOfDice[i]){
					tempArray[i] = indexOfDice[i];
					counter++;
				}
			}
			if(counter == 3){
				int[] twoValue = new int[2];
				for(int k = 0,counterFor2= 0;k < N_DICE;k++){
					if(tempArray[k] == 0){
						twoValue[counterFor2] = indexOfDice[k];
						counterFor2++;
					}
				}
				if(twoValue[0] == twoValue[1]){
					return true;
				}
				
			}else{
				tempArray[j] = 0;
			}
		}
		return false;
	}

	private boolean checkKIND(int[] indexOfDice, int categoryNumbers) {
		int counter = 0;
		for(int j = 0; j <3;j++,counter = 0){
			int compare = indexOfDice[j];
			for(int i = 0; i<N_DICE; i++){
				if(indexOfDice[i] == compare){
					counter++;
				}
				if (counter >= categoryNumbers-6) {
					return true;
				}
				
			}			
		}
		return false;
	}

	private boolean checkOneToSix(int[] indexOfDice2) {
		return true;
	}

	/**Update dices randomly after waitForPlayerToSelectDice() method, 
	 * which means click roll again button.*/
	private void updateDice() {
		
		for(int i = 0;i < N_DICE; i++){
			/**test for bonus
			if (display.isDieSelected(i)){
				if(counter > 4 ){
					for(i = 0;i < N_DICE; i++){
						indexOfDice[i] = 4;	
					}
					break;
				}
				indexOfDice[i] = 5;
			
			}
			*/
			/*normal*/
			if (display.isDieSelected(i)){
				indexOfDice[i] = rgen.nextInt(1, 6);
			}
			
		}
		display.displayDice(indexOfDice);
	}

	/**Roll dice randomly but do not display it.*/
	private void rollDice() {
		//rgen.setSeed(1);
		indexOfDice = new int[N_DICE];
		
		/**cheatMode
		indexOfDice[0] = 6;
		indexOfDice[1] = 6;
		indexOfDice[2] = 6;
		indexOfDice[3] = 6;
		indexOfDice[4] = 6;
		*/
		
		
		/*normalMode*/
		for(int i = 0; i<N_DICE;i++){
			indexOfDice[i] = rgen.nextInt(1, 6);
		}
		
	}


	/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	
	

}
