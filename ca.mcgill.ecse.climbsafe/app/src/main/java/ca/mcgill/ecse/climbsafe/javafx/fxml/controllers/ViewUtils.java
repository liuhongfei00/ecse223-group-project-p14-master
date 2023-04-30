package ca.mcgill.ecse.climbsafe.javafx.fxml.controllers;

import ca.mcgill.ecse.climbsafe.controller.InvalidInputException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewUtils {

	/**
	 * Calls controller methods and returns true if it worked, otherwise outputs error messsage
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 * @param executable controller function that is called
	 */
	public static boolean callController(Executable executable) {
		try {
			executable.execute();
			return true;
		} catch (Exception e) {
			showError(e.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return false;
	}

	/**
	 * Returns whether or not the controller method called worked without issue
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 * @param controllerCall controller method to be called
	 * @return boolean describing whether or not the method worked without issue
	 */
	public static boolean successful(Executable controllerCall) {
		return callController(controllerCall);
	}

	/**
	 * Creates a popup window.
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 * @param title popup window header
	 * @param message popup window message
	 */
	public static void makePopupWindow(String title, String message) {
		//create pop up
		Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		VBox popUp = new VBox();
		popUp.setStyle("-fx-background-color: #ffffc7;");

		//create ok button
		Text text = new Text(message);
		Button okButton = new Button("OK");
		okButton.setStyle("-fx-background-color: #548687;");
		okButton.setOnAction(a -> dialog.close());

		//edit style and add popup to screen
		popUp.setSpacing(15);
		popUp.setAlignment(Pos.CENTER);
		popUp.setPadding(new Insets(15, 15, 15, 15));
		popUp.getChildren().addAll(text, okButton);
		Scene dialogScene = new Scene(popUp, 100 + 5 * message.length(), 100);
		dialog.setScene(dialogScene);
		dialog.setTitle(title);
		dialog.show();
	}

	/**
	 * Creates a popup window titled Issue.
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 * @param message popup window message
	 */
	public static void showError(String message) {
		makePopupWindow("Issue", message);
	}
}

@FunctionalInterface
interface Executable {
	public void execute() throws Throwable;
}
