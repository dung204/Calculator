package com.calculator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
	@FXML private GridPane buttonPane;
	@FXML private TextField monitor;
	@FXML private TextField operatorPane;

	private final String DEFAULT_NUMBER = "0";
	private String queue;
	private String currentAns;

	private boolean isAddition;
	private boolean isSubtraction;
	private boolean isMultiplication;
	private boolean isDivision;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		monitor.setFocusTraversable(false);
		operatorPane.setFocusTraversable(false);

		currentAns = "0";
		queue = "";

		isAddition = false;
		isSubtraction = false;
		isMultiplication = false;
		isDivision = false;
	}

	public void displayNumber(ActionEvent event) {
		String number = ((Button) event.getSource()).getText();
		monitor.setText((monitor.getText().equals(DEFAULT_NUMBER) || monitor.getText().equals("Ans") ||
				(monitor.getText().matches("\\d+") && number.equals("Ans")) || !operatorPane.getText().equals(""))
				? number : monitor.getText() + number);
		queue += (!(operatorPane.getText().equals("") || operatorPane.getText().equals("=")))
				? (" " + number) : number;
		operatorPane.setText("");
	}

	public void functionalButton(ActionEvent event) {
		Button button = (Button) event.getSource();
		switch (button.getText()) {
			case "OFF": {
				Platform.exit();
				System.exit(0);
				break;
			}
			case "C": {
				monitor.setText(DEFAULT_NUMBER);
				operatorPane.setText("");
				queue = "";
				switchOffAllOperators();
				break;
			}
			case "DEL": {
				if(!operatorPane.getText().equals("=")) {
					StringBuilder text = new StringBuilder(monitor.getText());
					monitor.setText((text.length() == 1 || text.toString().equals("Ans")) ? DEFAULT_NUMBER :
							text.deleteCharAt(text.length() - 1).toString());
					queue = new StringBuilder(queue).deleteCharAt(queue.length() - 1).toString();
				}
				break;
			}
		}
	}

	public void operatorButton(ActionEvent event) {
		Button button = (Button) event.getSource();

		operatorPane.setText(button.getText());
		switch (button.getText()) {
			case "+": {
				continousCalculation();
				switchOffAllOperators();
				isAddition = true;
				break;
			}
			case "-": {
				continousCalculation();
				switchOffAllOperators();
				isSubtraction = true;
				break;
			}
			case "×": {
				continousCalculation();
				switchOffAllOperators();
				isMultiplication = true;
				break;
			}
			case "÷": {
				continousCalculation();
				switchOffAllOperators();
				isDivision = true;
				break;
			}
			case "=": {
				if(queue.indexOf(' ') >= 0) {
					handleQueueAndCalculate();
					switchOffAllOperators();
				}
				else {
					queue = queue.replaceAll("(Ans)", currentAns);
					currentAns = queue;
					monitor.setText(currentAns);
				}
				break;
			}
		}

	}

	public void switchOffAllOperators() {
		isAddition = false;
		isSubtraction = false;
		isMultiplication = false;
		isDivision = false;
	}

	public void handleQueueAndCalculate() {
		if(queue.equals(""))
			queue = "0";
		else {
			StringBuilder queueBuilder = new StringBuilder(queue);
			while(queueBuilder.charAt(0) == '0')
				queueBuilder.deleteCharAt(0);
			while(queueBuilder.indexOf("Ans") == 0 && queueBuilder.charAt(queueBuilder.indexOf("Ans") + 3) != ' ')
				queueBuilder.delete(0, 3);
			queue = queueBuilder.toString();
			queue = queue.replaceAll("(Ans)", currentAns);
		}
		BigDecimal firstNumber = new BigDecimal(queue.substring(0, queue.indexOf(' ')));
		BigDecimal secondNumber = new BigDecimal(queue.substring(queue.indexOf(' ') + 1));
		if(isAddition)
			currentAns = firstNumber.add(secondNumber).toString();
		if(isSubtraction)
			currentAns = firstNumber.subtract(secondNumber).toString();
		if(isMultiplication)
			currentAns = firstNumber.multiply(secondNumber).toString();
		if(isDivision)
			currentAns = firstNumber.divide(secondNumber, RoundingMode.HALF_UP).toString();
		queue = "Ans";
		monitor.setText(currentAns);
	}

	public void continousCalculation() {
		if(queue.contains(" ")) {
			handleQueueAndCalculate();
			queue = "Ans";
		}
	}

	public void mouseEntered(MouseEvent event) {
		Button button = (Button) event.getSource();
		switch (button.getText()) {
			case "OFF": {
				button.setStyle("-fx-background-color: rgba(255, 0, 0, 0.7);" +
								"-fx-text-fill: white;" +
								"-fx-background-border: 0px");
				break;
			}
			case "=": {
				button.setStyle("-fx-background-color: rgba(29, 230, 245, 0.7);" +
								"-fx-text-fill: white;" +
								"-fx-background-border: 0px");
				break;
			}
			default: {
				button.setStyle("-fx-background-color: rgba(191, 191, 191, 0.8);" +
								"-fx-text-fill: white;" +
								"-fx-background-border: 0px");
				break;
			}
		}
	}

	public void mouseExited(MouseEvent event) {
		Button button = (Button) event.getSource();
		button.setStyle("-fx-background-color: black;" +
						"-fx-text-fill: white;" +
						"-fx-background-border: 0px");
	}

}
