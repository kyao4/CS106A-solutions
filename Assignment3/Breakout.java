/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {

		setSize(APPLICATION_WIDTH,APPLICATION_HEIGHT);
		
		/*setup for layout*/
		setup();
		paly();
		addMouseListeners();
		
		
	}
	
	/** setup for outlay. */
	private void setup() {
		turns = NTURNS;
		fillbricks();
		createpaddle();
		createlifelabel();
		createframe();
	
	
	}
	
	private void paly() {
		createball();
		while (true){
			ball.move(vx, vy);
			pause(15);
			if (ball.getX()<0 || ball.getX()+2*BALL_RADIUS>WIDTH ){
				vx=-vx;
			}
			if (ball.getY()<0){
				vy=-vy;
			}
			checkforcollision();
			checkforwin();
			if(checkforlose()== true) {
				break;
			}
			addMouseListeners();
		}
		remove(ball);
	
	
	}

	/**fill the whole bricks in the canvas.*/
	private void fillbricks (){
		//initialize bricks_num.
		bricks_num= NBRICKS_PER_ROW*NBRICK_ROWS;
		for(int i = 0; i< NBRICK_ROWS; i++ ){
			fillALine(WIDTH/2-(NBRICKS_PER_ROW*BRICK_WIDTH+(NBRICKS_PER_ROW - 1)*BRICK_SEP)/2,BRICK_Y_OFFSET+i*(BRICK_SEP+BRICK_HEIGHT)); //fill a line y=offset + space + height of a brick
		}
		
	}
	
	private void createpaddle() {
		paddle = new GRect(WIDTH/2,HEIGHT-PADDLE_Y_OFFSET,PADDLE_WIDTH,PADDLE_HEIGHT);
		paddle.setFilled(true);
		add(paddle);
	}
	
	private void createlifelabel() {
		lifelabel = new GLabel("You life remain:" +turns,WIDTH/2,BRICK_Y_OFFSET-BRICK_HEIGHT);
		add(lifelabel); 
		
	}
	
	private void createframe() {
		line_y = new GRect(WIDTH,0,1,HEIGHT);
		line_x = new GRect(0,HEIGHT,WIDTH,1);
		add(line_y);
		add(line_x);
		
	}
	
	private boolean checkforlose() {
		
		if (ball.getY()>HEIGHT){
			turns--;
			vy=-vy;
			lifelabel.setLabel("You life remain:" +turns);
		}
		if (turns==0){
			return true;
		}else {
			return false;
		}
		
	}

	private void checkforwin() {
		
		if (bricks_num==0){
			win_banner = new GLabel("You win!", WIDTH/2, HEIGHT/2);
			add(win_banner);
		}
		
	}

	private void checkforcollision() {
		GObject collider = getCollidingObject();
		boolean objectToReflect = collider == paddle ||collider == win_banner||collider == lifelabel;
		boolean objectToIgnore = collider == null||collider == line_x||collider == line_y;
		//when collider do not exist, you should continue the program.
		
		if (objectToIgnore) {
			return;
		}
		if (objectToReflect){
			vy = -vy;
			//to avoid the glue problem.
			boolean detectball = collider == paddle && ball.getY()+2*BALL_RADIUS>paddle.getY()&&ball.getY()+2*BALL_RADIUS<paddle.getY()+PADDLE_HEIGHT+2*BALL_RADIUS&&(ball.getX()<paddle.getX()||ball.getX()>paddle.getX()+PADDLE_WIDTH);
			if(detectball){
				vx = -vx;
			}
			
		}else{
			vy = -vy;
			remove(collider);
			bricks_num--;
		}
		
		
	}

	private GObject getCollidingObject() {
		GObject collider = getElementAt(ball.getX(),ball.getY());
		if (collider == null) {
			collider = getElementAt(ball.getX()+2*BALL_RADIUS,ball.getY());
		} 
		if(collider == null) {
			collider = getElementAt(ball.getX()+2*BALL_RADIUS,ball.getY()+2*BALL_RADIUS);
		} 
		if(collider == null) {
			collider = getElementAt(ball.getX(),ball.getY()+2*BALL_RADIUS);
		}
		return collider;
		
	}

	private void createball() {
		vx = rgen.nextDouble(1.0,3.0);
		if(rgen.nextBoolean(0.5)) {
			vx=-vx;
		}
		vy=3.0;
		ball = new GOval(WIDTH/2-BALL_RADIUS,HEIGHT/2-BALL_RADIUS,BALL_RADIUS,BALL_RADIUS);
		ball.setFilled(true);
		add(ball);
		
	}







	/** Called on mouse move to record the coordinates of the mouse */
	public void mouseMoved (MouseEvent e){
		if (e.getX()>0 && e.getX()<WIDTH){
			paddle.move(e.getX()-paddle.getX(), 0);
		
		}
	}

	/**fill a line of bricks in the canvas.*/
	private void fillALine(int start_x, int start_y) {
		for (int i = 0; i<NBRICKS_PER_ROW;i++){
			GRect Gr = new GRect(start_x+i*(BRICK_SEP+BRICK_WIDTH),start_y,BRICK_WIDTH,BRICK_HEIGHT);
			switch (start_y){
			case BRICK_Y_OFFSET:
			case BRICK_Y_OFFSET+1*(BRICK_SEP+BRICK_HEIGHT): Gr.setColor(Color.red );break;
			case BRICK_Y_OFFSET+2*(BRICK_SEP+BRICK_HEIGHT):
			case BRICK_Y_OFFSET+3*(BRICK_SEP+BRICK_HEIGHT): Gr.setColor(Color.ORANGE );break;
			case BRICK_Y_OFFSET+4*(BRICK_SEP+BRICK_HEIGHT):
			case BRICK_Y_OFFSET+5*(BRICK_SEP+BRICK_HEIGHT): Gr.setColor(Color.YELLOW );break;
			case BRICK_Y_OFFSET+6*(BRICK_SEP+BRICK_HEIGHT):
			case BRICK_Y_OFFSET+7*(BRICK_SEP+BRICK_HEIGHT): Gr.setColor(Color.GREEN );break;
			case BRICK_Y_OFFSET+8*(BRICK_SEP+BRICK_HEIGHT):
			case BRICK_Y_OFFSET+9*(BRICK_SEP+BRICK_HEIGHT): Gr.setColor(Color.CYAN );break;
			}
			Gr.setFilled(true);
			add(Gr);
			
		}
		
	}
	private GRect paddle;
	
	private GOval ball;
	
	/** Velocity of x direction and y direction.*/
	private double vx,vy;
	
	/** GLabel of "You win!".*/
	private GLabel win_banner;
	
	/**keep track of the number of bricks in order to know when to send message "You win!".*/
	private int bricks_num;
	
	/** Your remain lives.*/
	private int turns;
	
	private GLabel lifelabel;
	
	/** vertical line in the frame.*/
	private GRect line_y;
	
	/** horizontal line in the frame.*/
	private GRect line_x;
	
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
}
