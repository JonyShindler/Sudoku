/*
 * Created by JFormDesigner on Fri Mar 10 20:40:30 GMT 2017
 */

package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import exectuer.SudokuSolver;
import logic.GridAccessService;
import logic.SolverType;

/**
 * @author Jonathan Shindler
 */
/**
 * @author Jony
 *
 */
public class BoxesWith3ButtonsForMultiEntry extends JFrame {

	private static final long serialVersionUID = 8062529169928860855L;
	
	JPanel panel = new JPanel();
	private JButton solveButton;
	private JLabel alertLabel;
	private static List<JTextField> listOfFields;
	private static int counter;
	private Map<Integer, Integer> boxLocations = new HashMap<Integer, Integer>();
	private JButton clearButton;
	private JButton enterPositionButton;
	private boolean coloursEntered = false;
	private String alert = "";
	private Map<Integer, Integer> inputValues = new HashMap<Integer, Integer>();
	private JCheckBox isPercentCheckbox = new JCheckBox();
	private SolverType solverType = SolverType.NORMAL;

	/**
	 * Initialised all the boxes and sets their colour to the alternating grey pattern.
	 */
	public BoxesWith3ButtonsForMultiEntry() {
		counter = 1;
		listOfFields = new ArrayList<JTextField>();
		
		for (int i = 1; i <= 82; i++) {
			JTextField textField = new JTextField();
			textField.getDocument().addDocumentListener(new ChangeColourDocumentListener(textField));
			
			//as we make them we want to define their colour...
			int currentBox = GridAccessService.findBoxNumber(i-1, solverType, boxLocations);
			if (currentBox == 1 || currentBox == 3 || currentBox == 5 || currentBox == 7 || currentBox == 9){
				textField.setBackground(Color.LIGHT_GRAY);
			}
			
			listOfFields.add(textField);
		}
		initComponents(panel);
		enterPositionButton.setVisible(false);
		add(panel);
	}

	
	/**
	 * Updates the colours of the UI boxes for the percent sudoku.
	 */
	class ChangeColourDocumentListener implements DocumentListener {
		private JTextField textField;
		ChangeColourDocumentListener(JTextField textField) { this.textField = textField; }

		@Override public void insertUpdate(DocumentEvent e) { changeColour(); }
		@Override public void removeUpdate(DocumentEvent e) {changeColour(); }
		@Override public void changedUpdate(DocumentEvent e) {changeColour(); }

		private void changeColour() {
			if (!coloursEntered && solverType == SolverType.PERCENT) {
				switch (textField.getText()) {
					case "1": textField.setBackground(Color.gray); break;
					case "2": textField.setBackground(Color.pink); break;
					case "3": textField.setBackground(Color.magenta); break;
					case "4": textField.setBackground(Color.green); break;
					case "5": textField.setBackground(Color.cyan); break;
					case "6": textField.setBackground(Color.yellow);break;
					case "7": textField.setBackground(Color.magenta);break;
					case "8": textField.setBackground(Color.GRAY); break;
					case "9": textField.setBackground(Color.PINK); break;
					default: textField.setBackground(Color.white); break;
				}
			}
		}
	}

	
	private void testTheUI() {
		if (solverType == SolverType.NORMAL) {
			inputValues.put(1, 7); inputValues.put(2, 9); inputValues.put(7, 3); inputValues.put(15, 6);
			inputValues.put(16, 9); inputValues.put(19, 8);  inputValues.put(23, 3); inputValues.put(26, 7);
			inputValues.put(27, 6); inputValues.put(33, 5); inputValues.put(36, 2); inputValues.put(39, 5);
			inputValues.put(40, 4); inputValues.put(41, 1); inputValues.put(42, 8); inputValues.put(43, 7);
			inputValues.put(46, 4); inputValues.put(49, 7); inputValues.put(55, 6); inputValues.put(56, 1);
			inputValues.put(59, 9); inputValues.put(63, 8); inputValues.put(66, 2); inputValues.put(67, 3);
			inputValues.put(75, 9); inputValues.put(80, 5); inputValues.put(81, 4);
		} else {
			inputValues.put(1, 1); inputValues.put(2, 6); inputValues.put(5, 7); inputValues.put(6, 9); inputValues.put(8, 8); inputValues.put(9, 4);
			inputValues.put(10, 9); inputValues.put(11, 3); inputValues.put(15, 6); inputValues.put(17, 1);
			// blank
			inputValues.put(28, 8); inputValues.put(30, 1); inputValues.put(34, 6); inputValues.put(36, 5); 
			inputValues.put(38, 7); inputValues.put(39, 3); inputValues.put(40, 1); inputValues.put(41, 5); inputValues.put(42, 8); inputValues.put(43, 4); inputValues.put(44, 9);
			inputValues.put(46, 5); inputValues.put(48, 6); inputValues.put(51, 1); inputValues.put(54, 9);
			inputValues.put(61, 1);
			inputValues.put(67, 5); inputValues.put(71, 4); inputValues.put(72, 1);
			inputValues.put(73, 3); inputValues.put(74, 1); inputValues.put(76, 9); inputValues.put(77, 4); inputValues.put(80, 6); inputValues.put(81, 8);	
		}
	}
	
	
	/**
	 * When the button is clicked it calls this method as this is its listener
	 * 
	 * <li>Stores all of the input values in a map</li>
	 * <li>Then solves the soduku using that map</li>
	 * <li>Then populates the GUI grid with the solution</li>
	 * 
	 * @author Jony
	 *
	 */
	class SolverListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			alert = "";
			alertLabel.setText(alert);
			int x = 0;

			// Take all the values from the each box and store them in a map if
			// a value exists in the box.
			for (JTextField field : listOfFields) {
				if (!field.getText().isEmpty()) {
					inputValues.put(x, Integer.parseInt(field.getText()));
				}
				x++;
			}

			testTheUI();
			
			// Check that the Sudoku isnt already complete
			if (inputValues.size() == 81) {
				alert = "The sudoku has already been solved.";
				alertLabel.setText(alert);
			} else {

				try {
					// Solve the sudoku
					List<Integer> solutionGrid = SudokuSolver.solveSudoku(inputValues, boxLocations, solverType);

					// Populate the GUI grid with the values from the solution
					// grid.
					int counter = 1;
					for (counter = 1 ; counter <=81 ; counter ++) {
						listOfFields.get(counter).setText(Integer.toString(solutionGrid.get(counter)));
					}
				} catch (Exception exception) {
					alert = "There was a problem processing your sudoku";
					exception.printStackTrace();
					alertLabel.setText(alert);
				}
			}
		}
	}

	
	
	/**
	 * Clears all of the values in the boxes and clears the alerts.
	 */
	class ClearListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			alert = "";
			alertLabel.setText(alert);
			
			// Take all the values from the each box and store them in a map if
			// a value exists in the box.
			for (JTextField field : listOfFields) {
				field.setText("");
			}
		}
	}

	
	/**
	 * When the solver type is toggled, changed the button on screen and colours in the boxes for regular sudoku. 
	 */
	class CheckBoxListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getSource() == isPercentCheckbox && isPercentCheckbox.isSelected()) {
				solverType = SolverType.PERCENT;
				enterPositionButton.setVisible(true);
				for (JTextField field : listOfFields) {
					field.setBackground(Color.WHITE);
				}

			} else {
				solverType = SolverType.NORMAL;
				int counter = 1;
				for (JTextField textField : listOfFields) {
					int currentBox = GridAccessService.findBoxNumber(counter-1, solverType, boxLocations);
					if (currentBox == 1 || currentBox == 3 || currentBox == 5 || currentBox == 7 || currentBox == 9) {
						textField.setBackground(Color.LIGHT_GRAY);
					}
				counter ++;	
				}
				enterPositionButton.setVisible(false);
			}
		}
	}

	
	/**
	 * Stores the passed in box locations, and validates them.
	 */
	class InputBoxesListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			alert = "";
			int x = 0;
			
			// For testing, take the hardcoded stuff and type the numbers into the boxes
			
			boxLocations.put(1,1);boxLocations.put(2,1);boxLocations.put(3,1);boxLocations.put(4,1);boxLocations.put(5,2);boxLocations.put(6,3);boxLocations.put(7,3);boxLocations.put(8,3);boxLocations.put(9,3);
			boxLocations.put(10,1);boxLocations.put(11,1);boxLocations.put(12,1);boxLocations.put(13,2);boxLocations.put(14,2);boxLocations.put(15,2);boxLocations.put(16,2);boxLocations.put(17,3);boxLocations.put(18,3);
			boxLocations.put(19,1);boxLocations.put(20,4);boxLocations.put(21,4);boxLocations.put(22,2);boxLocations.put(23,5);boxLocations.put(24,5);boxLocations.put(25,2);boxLocations.put(26,6);boxLocations.put(27,3);
			boxLocations.put(28,1);boxLocations.put(29,4);boxLocations.put(30,2);boxLocations.put(31,2);boxLocations.put(32,5);boxLocations.put(33,5);boxLocations.put(34,6);boxLocations.put(35,6);boxLocations.put(36,3);
			boxLocations.put(37,7);boxLocations.put(38,4);boxLocations.put(39,4);boxLocations.put(40,4);boxLocations.put(41,5);boxLocations.put(42,6);boxLocations.put(43,6);boxLocations.put(44,6);boxLocations.put(45,3);
			boxLocations.put(46,7);boxLocations.put(47,4);boxLocations.put(48,4);boxLocations.put(49,5);boxLocations.put(50,5);boxLocations.put(51,8);boxLocations.put(52,8);boxLocations.put(53,6);boxLocations.put(54,9);
			boxLocations.put(55,7);boxLocations.put(56,4);boxLocations.put(57,8);boxLocations.put(58,5);boxLocations.put(59,5);boxLocations.put(60,8);boxLocations.put(61,6);boxLocations.put(62,6);boxLocations.put(63,9);
			boxLocations.put(64,7);boxLocations.put(65,7);boxLocations.put(66,8);boxLocations.put(67,8);boxLocations.put(68,8);boxLocations.put(69,8);boxLocations.put(70,9);boxLocations.put(71,9);boxLocations.put(72,9);
			boxLocations.put(73,7);boxLocations.put(74,7);boxLocations.put(75,7);boxLocations.put(76,7);boxLocations.put(77,8);boxLocations.put(78,9);boxLocations.put(79,9);boxLocations.put(80,9);boxLocations.put(81,9);
			
			for (int counter = 1 ; counter <= 81; counter ++) {
				listOfFields.get(counter).setText(boxLocations.get(counter).toString());
			}
			
			// Take all the values from the each box and store them in a map if
			// a value exists in the box.
			for (JTextField field : listOfFields) {
				if (!field.getText().isEmpty()) {
					boxLocations.put(x, Integer.parseInt(field.getText()));
				}
				x++;
			}

			if (boxLocations.keySet().size() != 81) {
				alert = "You havent entered numbers in all the boxes!";
				alertLabel.setText(alert);
			}

			if (!checkThereAre9OfEachNumber()) {
				alert = "There are not 9 of each number!";
				alertLabel.setText(alert);
			}

			if (alert.equals("")) {
				coloursEntered = true;
			}

		}

		
		private boolean checkThereAre9OfEachNumber() {
			int counter = 0;
			Collection<Integer> values = boxLocations.values();
			for (Integer number : values) {
				counter = counter + number;
			}
			return counter == 405;
		}

	}

	
	/**
	 * Calculates the JTextField for each box when they are initialised from the
	 * list
	 * 
	 * @return JTextField
	 */
	private static JTextField returnTextField() {
		if (counter > 81) {
			counter -= 81;
		}

		JTextField field = listOfFields.get(counter);
		counter++;
		return field;
	}

	
	/**
	 * Builds 81 boxes and a solve button.
	 * 
	 * @param panel
	 *            the panel to apply the boxes to
	 */
	private void initComponents(JPanel panel) {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Jonathan Shindler

		solveButton = new JButton();
		clearButton = new JButton();
		enterPositionButton = new JButton();
		alertLabel = new JLabel();

		// ======== this ========
		Container contentPane = getContentPane();

		// ---- button1 ----
		solveButton.setText("Solve!");
		solveButton.addActionListener(new SolverListener());

		// ---- button2 ----
		clearButton.setText("Clear grid");
		clearButton.addActionListener(new ClearListener());

		// ---- button3 ----
		enterPositionButton.setText("Store grid positions");
		enterPositionButton.addActionListener(new InputBoxesListener());

		// ---- button3 ----
		alertLabel.setText(alert);

		isPercentCheckbox.setText("Percent Sudoku");
		isPercentCheckbox.addItemListener(new CheckBoxListener());

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(contentPaneLayout.createParallelGroup().addGroup(contentPaneLayout
				.createSequentialGroup().addContainerGap().addGroup(contentPaneLayout
						.createParallelGroup().addGroup(contentPaneLayout.createSequentialGroup()
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(returnTextField(),
										GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
						.addGroup(contentPaneLayout.createSequentialGroup()
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(returnTextField(),
										GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
						.addGroup(contentPaneLayout.createSequentialGroup()
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(returnTextField(),
										GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
						.addGroup(contentPaneLayout.createSequentialGroup()
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(returnTextField(),
										GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
						.addGroup(contentPaneLayout.createSequentialGroup()
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(returnTextField(),
										GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
						.addGroup(contentPaneLayout.createSequentialGroup()
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(returnTextField(),
										GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
						.addGroup(contentPaneLayout.createSequentialGroup()
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(returnTextField(),
										GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
						.addGroup(contentPaneLayout.createSequentialGroup()
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(returnTextField(),
										GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
						.addGroup(contentPaneLayout.createSequentialGroup()
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, 14,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(returnTextField(),
										GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
						.addComponent(solveButton, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)
						.addComponent(clearButton, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)
						.addComponent(enterPositionButton, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)
						.addComponent(alertLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(isPercentCheckbox)
				.addContainerGap(355, Short.MAX_VALUE)));
		
		contentPaneLayout.setVerticalGroup(contentPaneLayout.createParallelGroup().addGroup(contentPaneLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(contentPaneLayout.createParallelGroup()
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(contentPaneLayout.createParallelGroup()
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(contentPaneLayout.createParallelGroup()
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(contentPaneLayout.createParallelGroup()
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(contentPaneLayout.createParallelGroup()
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(contentPaneLayout.createParallelGroup()
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(contentPaneLayout.createParallelGroup()
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(contentPaneLayout.createParallelGroup()
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(contentPaneLayout.createParallelGroup()
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(solveButton).addComponent(isPercentCheckbox))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(clearButton)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(enterPositionButton).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(alertLabel, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
				.addContainerGap()));
		pack();
		setLocationRelativeTo(getOwner());
	}

}
