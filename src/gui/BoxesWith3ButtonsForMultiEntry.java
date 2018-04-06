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

import exectuer.Executor2;
import logic.GridAccessService;
import logic.SolverType;

/**
 * @author Jonathan Shindler
 */
public class BoxesWith3ButtonsForMultiEntry extends JFrame {

	private static final long serialVersionUID = 8062529169928860855L;
	
	JPanel panel = new JPanel();
	private JButton solveButton;
	private JLabel alertLabel;
	private static List<JTextField> listOfFields;
	private static int counter;
	private HashMap<Integer, Integer> mapOfBoxLocations;
	private JButton clearButton;
	private JButton enterPositionButton;
	private boolean coloursEntered = false;
	private String alert = "";
	private Map<Integer, Integer> mapOfInputValues = new HashMap<Integer, Integer>();
	private JCheckBox isPercentCheckbox = new JCheckBox();
	private SolverType solverType = SolverType.NORMAL;

	public BoxesWith3ButtonsForMultiEntry() {
		counter = 1;
		listOfFields = new ArrayList<JTextField>();
		GridAccessService gridAccessService = new GridAccessService(null, solverType);
		
		for (int i = 1; i <= 82; i++) {
			JTextField textField = new JTextField();
			textField.getDocument().addDocumentListener(new ChangeColourDocumentListener(textField));
			
			//as we make them we want to define their colour...
			int currentBox = gridAccessService.findBoxCurrentPositionIsIn(GridAccessService.calculateGridPositionOfGridNumber(i-1));
			if (currentBox == 1 || currentBox == 3 || currentBox == 5 || currentBox == 7 || currentBox == 9){
				textField.setBackground(Color.LIGHT_GRAY);
			}
			
			listOfFields.add(textField);
		}
		initComponents(panel);
		enterPositionButton.setVisible(false);
		add(panel);
	}

	
	class ChangeColourDocumentListener implements DocumentListener {

		private JTextField textField;

		ChangeColourDocumentListener(JTextField textField) {
			this.textField = textField;
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			changeColour();

		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			changeColour();

		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			changeColour();

		}

		private void changeColour() {
			if (!coloursEntered && solverType == SolverType.PERCENT) {

				switch (textField.getText()) {

				case "1":
					textField.setBackground(Color.gray);
					break;
				case "2":
					textField.setBackground(Color.pink);
					break;
				case "3":
					textField.setBackground(Color.magenta);
					break;
				case "4":
					textField.setBackground(Color.green);
					break;
				case "5":
					textField.setBackground(Color.cyan);
					break;
				case "6":
					textField.setBackground(Color.yellow);
					break;
				case "7":
					textField.setBackground(Color.magenta);
					break;
				case "8":
					textField.setBackground(Color.GRAY);
					break;
				case "9":
					textField.setBackground(Color.PINK);
					break;
				default:
					textField.setBackground(Color.white);
					break;
				}

			}

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
					mapOfInputValues.put(x, Integer.parseInt(field.getText()));
				}
				x++;
			}

			// check that the sudoku isnt already complete
			if (mapOfInputValues.size() == 81) {
				alert = "The sudoku has already been solved.";
				alertLabel.setText(alert);
			} else {

				try {
					// Solve the sudoku
					int[][] solutionGrid = Executor2.executeForPercentGrid(mapOfInputValues, mapOfBoxLocations,
							solverType);

					// Populate the GUI grid with the values from the solution
					// grid.
					int counter = 1;
					for (int i = 0; i <= 8; i++) {
						for (int j = 0; j <= 8; j++) {
							listOfFields.get(counter).setText(Integer.toString(solutionGrid[i][j]));
							counter++;
						}
					}
				} catch (Exception exception) {
					alert = "There was a problem processing your sudoku";
					alertLabel.setText(alert);
				}
			}
		}
	}

	
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

					GridAccessService gridAccessService = new GridAccessService(null, solverType);
					int currentBox = gridAccessService 
							.findBoxCurrentPositionIsIn(GridAccessService.calculateGridPositionOfGridNumber(counter - 1));
					if (currentBox == 1 || currentBox == 3 || currentBox == 5 || currentBox == 7 || currentBox == 9) {
						textField.setBackground(Color.LIGHT_GRAY);
					}
				counter ++;	
				}
				enterPositionButton.setVisible(false);

			}
		}
	}

	
	class InputBoxesListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			alert = "";
			mapOfBoxLocations = new HashMap<Integer, Integer>();
			int x = 0;

			// Take all the values from the each box and store them in a map if
			// a value exists in the box.
			for (JTextField field : listOfFields) {
				if (!field.getText().isEmpty()) {
					mapOfBoxLocations.put(x, Integer.parseInt(field.getText()));
				}
				x++;
			}

			if (mapOfBoxLocations.keySet().size() != 81) {
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
			Collection<Integer> values = mapOfBoxLocations.values();
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
