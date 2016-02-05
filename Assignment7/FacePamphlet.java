/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;

import java.awt.event.*;
import java.util.Iterator;

import javax.swing.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {

	public static void main(String[] args) {
		new FacePamphlet().init();
	}
	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		setUp();
		addListeners();
	}
    
  
    private void addListeners() {
    	changeStatusTextField.addActionListener(this);
    	changePictureTextField.addActionListener(this);
    	addFriendTextField.addActionListener(this);
    	addActionListeners();
	}


	private void setUp() {
    	
    	setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
    	
    	//Initialize components.
    	nameLabel = new JLabel("Name");
    	nameTextField = new JTextField(TEXT_FIELD_SIZE);
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        lookUpButton = new JButton("Lookup");
       
        changeStatusTextField = new JTextField(TEXT_FIELD_SIZE);
        changeStatusTextField.setActionCommand("Change Status");//set TextField's name
        changeStatusButton = new JButton("Change Status");
        
        changePictureTextField = new JTextField(TEXT_FIELD_SIZE);
        changePictureTextField.setActionCommand("Change Picture");//set TextField's name
        changePictureButton = new JButton("Change Picture");
        
        addFriendTextField = new JTextField(TEXT_FIELD_SIZE);
        addFriendTextField.setActionCommand("Add Friend");//set TextField's name
        addFriendButton = new JButton("Add Friend");

        myCanvas = new FacePamphletCanvas();
        
        add(nameLabel,NORTH);
        add(nameTextField,NORTH);
        add(addButton,NORTH);
        add(deleteButton,NORTH);
        add(lookUpButton,NORTH);
        add(changeStatusTextField,WEST);
        add(changeStatusButton,WEST);
        add(new JLabel(EMPTY_LABEL_TEXT),WEST);
        add(changePictureTextField,WEST);
        add(changePictureButton,WEST);
        add(new JLabel(EMPTY_LABEL_TEXT),WEST);
        add(addFriendTextField,WEST);
        add(addFriendButton,WEST);
        
        add(myCanvas);
	}


	/**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
    	
    	String nameText = nameTextField.getText();
    	String statusText = changeStatusTextField.getText();
    	String pictureText = changePictureTextField.getText();
    	String addFriendText = addFriendTextField.getText();
    	
		if(e.getActionCommand() == "Add") {
			if(nameText.equals("")) return;
			if(myDatabase.containsProfile(nameText)) {
				currentProfile = myDatabase.getProfile(nameText);
				myCanvas.displayProfile(currentProfile);
				myCanvas.showMessage("A profile with the name  " +currentProfile.getName() + " already exists");
				
				println("Add: " + "profile with that name already exists: " + myDatabase.getProfile(nameText).toString());
				println("--> Current profile: " + currentProfile);
			} else {
				currentProfile = new FacePamphletProfile(nameText);
				myDatabase.addProfile(currentProfile);
				myCanvas.displayProfile(currentProfile);
				myCanvas.showMessage("Displaying " + currentProfile.getName());
				
				println("Add: " + "new profile: " + myDatabase.getProfile(nameText).toString());
				println("--> Current profile: " + currentProfile);
			}
			
		}
		
		if(e.getActionCommand() == "Delete") {
			if(nameText.equals("")) return;
			currentProfile = null;
			myCanvas.removeAll();
			if(myDatabase.containsProfile(nameText)) {
				myDatabase.deleteProfile(nameText);
				myCanvas.showMessage("Profile of " + nameText + " deleted");
				
				println("Delete: " + "profile of " + nameText + " deleted");
				println("--> No current profile");
			} else {
				myCanvas.showMessage("A profile with the name " + nameText + " does not exist");
				
				println("Delete: " + "profile with name " + nameText + " does not exist");	
			}
			
		}
		
		if(e.getActionCommand() == "Lookup") {
			if(nameText.equals("")) return;
			if(myDatabase.containsProfile(nameText)) {
				currentProfile = myDatabase.getProfile(nameText);
				myCanvas.displayProfile(currentProfile);
				myCanvas.showMessage("Displaying " + currentProfile.getName());
				
				println("Lookup: " + myDatabase.getProfile(nameText).toString());
				println("--> Current profile: " + currentProfile);
			} else {
				currentProfile = null;
				myCanvas.removeAll();
				myCanvas.showMessage("A profile with the name " + currentProfile.getName() + " does not exist");
				
				println("Lookup: " + "profile with name " + nameText + " does not exist");
			}
			
		}
		
		if(e.getActionCommand() == "Change Status") {
			if(statusText.equals("")) return;
			if (currentProfile != null) {
				myDatabase.getProfile(nameText).setStatus(statusText);
				myCanvas.displayProfile(currentProfile);
				myCanvas.showMessage("Status updated to " + statusText);
				
				println("Status updated to " + statusText);
				println("--> Current profile: " + currentProfile);
			} else {
				myCanvas.removeAll();
				myCanvas.showMessage("Please select a profile to change status");
				
				println("Please to select a profile to change status" + "\n--> No current profile");	
			}
		}
		
		if(e.getActionCommand() == "Change Picture") {
			if(pictureText.equals("")) return;
			
			if (currentProfile != null) {
				GImage image = null;
				try {
				image = new GImage(pictureText);
				myDatabase.getProfile(nameText).setImage(image);
				myCanvas.displayProfile(currentProfile);
				myCanvas.showMessage("Picture  updated");
				
				println("Picture updated");
				println("--> Current profile: " + currentProfile);
				} catch (ErrorException ex) {
					myCanvas.showMessage("Unable to open image file: " + pictureText);
					
					println("File does not exist.");
				}
				
			} else {
				//myCanvas.removeAll();
				myCanvas.showMessage("Please select a profile to change picture");
				
				println("Please to select a profile to change picture" + "\n--> No current profile");
			}
		}
		
		if(e.getActionCommand() == "Add Friend") {
			if(addFriendText.equals("")) return;
			
			if(addFriendText.equals(currentProfile.getName())) {
				myCanvas.showMessage("You can't add yourself as your friend.");
				return;
			}
			if (currentProfile != null) {
				if(!myDatabase.containsProfile(addFriendText)) {
					myCanvas.showMessage(addFriendText + " does not exist.");
					
					println( addFriendText + " does not exist.");
					return;
				}
				
				Iterator <String> friends = currentProfile.getFriends();	
				while(friends.hasNext()){
					if(friends.next() == addFriendText) {
						myCanvas.showMessage(currentProfile.getName() + " already has " + addFriendText + " as a friend.");
						
						println(addFriendText + " has already existed in the list.");
						return;
					}
				}
				
				currentProfile.addFriend(addFriendText);
				myDatabase.getProfile(addFriendText).addFriend(currentProfile.getName());
				myCanvas.displayProfile(currentProfile);
				myCanvas.showMessage(addFriendText + " added as a friend");
				
				println(addFriendText + " added as a friend");
				println("--> Current profile: " + currentProfile);
			} else {
				myCanvas.showMessage("Please select a profile to add friend");
				
				println("Please to select a profile to add friend" + "\n--> No current profile");	
			}
		}
	}

    
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JButton addButton;
    private JButton deleteButton;
    private JButton lookUpButton;
    private JTextField changeStatusTextField;
    private JButton changeStatusButton;
    private JTextField changePictureTextField;
    private JButton changePictureButton;
    private JTextField addFriendTextField;
    private JButton addFriendButton;
    
    private FacePamphletCanvas myCanvas;
    private FacePamphletDatabase myDatabase = new FacePamphletDatabase();
    private FacePamphletProfile currentProfile = null;
}
