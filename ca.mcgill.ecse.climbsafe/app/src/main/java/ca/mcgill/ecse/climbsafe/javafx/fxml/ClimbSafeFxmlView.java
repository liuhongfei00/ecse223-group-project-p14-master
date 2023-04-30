package ca.mcgill.ecse.climbsafe.javafx.fxml;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class ClimbSafeFxmlView extends Application {

	/**
	 * Sets up the window for the application
	 * 
	 * @author: Hongfei Liu, Zihan Zhang, Matt MacDonald, Ryan Reszetnik, Sabrina
	 *          Mansour, Sehr Moosabhoy
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			// gets the main FXML page
			var root = (TabPane) FXMLLoader.load(getClass().getResource("MainPage.fxml"));
			// sets it to dark theme
			root.setStyle("-fx-base: rgba(20, 20, 20, 255);");
			// creates a scene and scales it by 1.5
			var scene = new Scene(root);
			Scale scale = new Scale(1.5, 1.5);
			scale.setPivotX(0);
			scale.setPivotY(0);
			scene.getRoot().getTransforms().setAll(scale);
			// sets the stage and size of the window
			primaryStage.setScene(scene);
			primaryStage.setMinWidth(900);
			primaryStage.setMinHeight(740);
			primaryStage.setResizable(false);
			primaryStage.setTitle("ClimbSafe");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	/**
	 * Entry point of the application
	 * 
	 * @param args
	 * 
	 * @author: Hongfei Liu, Zihan Zhang, Matt MacDonald, Ryan Reszetnik, Sabrina
	 *          Mansour, Sehr Moosabhoy
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
