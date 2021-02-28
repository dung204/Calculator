package com.calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Frame.fxml"));
		primaryStage.setTitle("Calculator");
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image("file:calculator.png"));
		primaryStage.setScene(new Scene(root));

		primaryStage.show();
	}
}
