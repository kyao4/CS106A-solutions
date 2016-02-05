/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;

import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

	public static void main(String[] args) {
		new NameSurfer().init();
	}
/* Method: init() */
/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the bottom of the window.
 */
	public void init() {
		
		Canvas = new NameSurferGraph();
		add(Canvas);
		DataBase = new NameSurferDataBase(NAMES_DATA_FILE);
		
		JLabel name = new JLabel("name:");
		nameTextField = new JTextField(10);
		//TextField can not be listened at first time, you should add a listener to it manually.
		nameTextField.addActionListener(this);
		JButton drawButton = new JButton("Graph");
		JButton clearButton = new JButton("Clear");

		add(name,SOUTH);
		add(nameTextField,SOUTH);
		add(drawButton,SOUTH);
		add(clearButton,SOUTH);
		
		addActionListeners();
		
	}
	

/* Method: actionPerformed(e) */
/**
 * This class is responsible for detecting when the buttons are
 * clicked, so you will have to define a method to respond to
 * button actions.
 */
	/**/
	public void actionPerformed(ActionEvent e) {
		
		String commandName = e.getActionCommand();
		Object commandObject = e.getSource();
		if( commandName.equals("Clear")){
			Canvas.clear();
		}
		
		if(commandName.equals("Graph") || commandObject == nameTextField){
			NameSurferEntry entry = DataBase.findEntry(nameTextField.getText());
			if(entry == null) return;
			Canvas.addEntry(entry);
		}
	}
	
	private JTextField nameTextField;
	private NameSurferDataBase DataBase;
	private NameSurferGraph Canvas;
}
