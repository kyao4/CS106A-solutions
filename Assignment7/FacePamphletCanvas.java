/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		// You fill this in
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		remove(message);
		message = new GLabel(msg);
		double x = (getWidth() - message.getWidth())/2;
		double y = getHeight() - BOTTOM_MESSAGE_MARGIN;
		message.setFont(MESSAGE_FONT);
		add(message,x,y);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		name = new GLabel(profile.getName(),LEFT_MARGIN,TOP_MARGIN+name.getAscent());
		name.setColor(Color.blue);
		name.setFont(PROFILE_NAME_FONT);
		add(name);
		
		rect = new GRect(IMAGE_MARGIN,name.getY() + IMAGE_MARGIN, IMAGE_WIDTH,IMAGE_HEIGHT);
		add(rect);
		
		imageText = new GLabel("No Image");
		imageText.setFont(PROFILE_IMAGE_FONT);
		add(imageText,rect.getX() + (rect.getWidth() - imageText.getWidth())/2,rect.getY() + rect.getHeight()/2);
		
		if(profile.getImage() != null) {
			image = profile.getImage();
			image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(image,rect.getX(),rect.getY());
		}
		
		
		if(profile.getStatus().equals("")) {
			status = new GLabel("No current status");	
		} else {
			status = new GLabel(profile.getName() + " is " + profile.getStatus());
		}
		status.setFont(PROFILE_STATUS_FONT);
		add(status,LEFT_MARGIN,rect.getY() + rect.getHeight() + STATUS_MARGIN + status.getAscent());
		
		friendsHeader = new GLabel("Friends:",getWidth()/2,rect.getY());
		friendsHeader.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friendsHeader);
		
		Iterator<String> friendsList = profile.getFriends();
		int i = 1;
		while(friendsList.hasNext()) {
			friends = new GLabel(friendsList.next(),getWidth()/2,friendsHeader.getY() + (i++) * friendsHeader.getHeight());
			friends.setFont(PROFILE_FRIEND_FONT);
			add(friends);
		}
	}

	private GLabel message = new GLabel("");
	private GLabel name = new GLabel("");
	private GRect rect;
	private GLabel imageText = new GLabel("");
	private GImage image;
	private GLabel status = new GLabel("");
	private GLabel friendsHeader = new GLabel("");
	private GLabel friends = new GLabel("");
	
}
