package gui;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class Outputter {

	public static void outputtToPanel(int frameXLocation, int frameYLocation, int [][] solutionGrid){
		
		  JFrame frame = new BoxesWithoutButtons(solutionGrid);
		  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  frame.setVisible(true);
		  frame.setSize(220,320);
		  frame.setLocation(frameXLocation, frameYLocation);
		  
	}
	
}
