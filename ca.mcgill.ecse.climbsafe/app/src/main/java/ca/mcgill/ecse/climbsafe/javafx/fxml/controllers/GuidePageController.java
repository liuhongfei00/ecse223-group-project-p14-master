package ca.mcgill.ecse.climbsafe.javafx.fxml.controllers;

import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet3Controller;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet1Controller;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet2Controller;
import ca.mcgill.ecse.climbsafe.controller.InvalidInputException;
import ca.mcgill.ecse.climbsafe.controller.TOGuide;
import ca.mcgill.ecse.climbsafe.javafx.fxml.ClimbSafeFxmlView;
import ca.mcgill.ecse.climbsafe.javafx.fxml.controllers.ViewUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArrayBase;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.ChoiceBox;

public class GuidePageController {
	TOGuide guide;
	@FXML
	private Button clearButton;
	@FXML
	private List<Button> guideInSystem = new ArrayList<Button>();
	@FXML
	private TextField NameGField;
	@FXML
	private TextField EmailGField;
	@FXML
	private TextField PasswordGField;
	@FXML
	private TextField emergContact;
	@FXML
	private Button addNewGuideButton;
	@FXML
	private Button updateGuideButton;
	@FXML
	private Button deleteGuideButton;
	@FXML
	private VBox guideDisplay;
	@FXML
	private ImageView logoImage;
	
	@FXML
	public void initialize() {
		updateSystemItems();
		logoImage.setImage(new Image(getClass().getResource("climbsafeLogo.png").toExternalForm(),66,62,false,false));
	}
	
	public void clear() {
		NameGField.clear();
		EmailGField.clear();
		PasswordGField.clear();
		emergContact.clear();
	}

	// Event Listener on Button[#addNewGuideButton].onAction
	@FXML
	private void addGuideButtonClicked(ActionEvent event) {
		String newName = NameGField.getText();
		String newEmail = EmailGField.getText();
		String newPassword = PasswordGField.getText();
		String newContact = emergContact.getText();

		if (newName == null || newName.trim().isEmpty()) {
			ViewUtils.showError("Please input a valid guide name");
		} else if (newEmail == null || newEmail.trim().isEmpty()) {
			ViewUtils.showError("Please input a valid email");
		} else if (newPassword == null || newPassword.trim().isEmpty()) {
			ViewUtils.showError("Please input a valid password");
		} else if (newContact == null || newContact.trim().isEmpty()) {
			ViewUtils.showError("Please input a valid emergency contact");
		} else {
			if (ViewUtils.successful(
					() -> ClimbSafeFeatureSet3Controller.registerGuide(newEmail, newPassword, newName, newContact))) {
				updateSystemItems();
				clear();
			}
		}

	}

	@FXML
	private void updateGuideButtonClicked(ActionEvent event) {
		String updateName = NameGField.getText();
		String updateEmail = EmailGField.getText();
		String updatePassword = PasswordGField.getText();
		String updateContact = emergContact.getText();

		if (updateName == null || updateName.trim().isEmpty()) {
			ViewUtils.showError("Please input a valid guide name");
		} else if (updateEmail == null || updateEmail.trim().isEmpty()) {
			ViewUtils.showError("Please input a valid email");
		} else if (updatePassword == null || updatePassword.trim().isEmpty()) {
			ViewUtils.showError("Please input a valid password");
		} else if (updateContact == null || updateContact.trim().isEmpty()) {
			ViewUtils.showError("Please input a valid emergency contact");
		} else {
			if (ViewUtils.successful(() -> ClimbSafeFeatureSet3Controller.updateGuide(updateEmail, updatePassword,
					updateName, updateContact))) {
				updateSystemItems();
				clear();
			}
		}

	}

	@FXML
	private void deleteButtonClicked(ActionEvent event) {
		String deleteEmail = EmailGField.getText();

		 if (deleteEmail == null || deleteEmail.trim().isEmpty()) {
		ViewUtils.showError("Please input a valid email");
		}
		 else {
			 if (ViewUtils.successful(() ->
		 ClimbSafeFeatureSet1Controller.deleteGuideCheck(deleteEmail))) {
		 ClimbSafeFeatureSet1Controller.deleteGuide(deleteEmail);
		 updateSystemItems();
		 clear();
		 }
		 }

	}

	private void updateSystemItems() {
		// removes all labels currently shown
		guideDisplay.getChildren().removeAll(guideInSystem);
		guideInSystem.clear();
		// gets list of all equipment
		List<String> names = ClimbSafeFeatureSet3Controller.getAllGuideNames();
		for (int a = 0; a < names.size(); a++) {
			// creates a new button for each equipment item and formats it
			Button newBut = new Button(names.get(a));
			newBut.setStyle("-fx-background-color:  #1F271B; -fx-background-radius:10; -fx-cursor: hand");
			newBut.setTextFill(Color.ORANGE);
			newBut.setPrefSize(100, 20);
			newBut.setAlignment(Pos.CENTER);
			newBut.setFont(new Font(12));

			guideInSystem.add(newBut);
			guideDisplay.getChildren().add(newBut);
			// adds the event handler to the button
			newBut.setOnAction(systemButtonPressed);
		}
	}
		
		EventHandler<ActionEvent> systemButtonPressed = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				// gets the text of the button which should be the equipment name
				String buttonText = ((Button) (e.getSource())).getText();

				try {
					
					guide = ClimbSafeFeatureSet3Controller.getGuide(buttonText);
					NameGField.setText(guide.getName());
					EmailGField.setText(guide.getEmail());
					PasswordGField.setText(guide.getPassword());
					emergContact.setText(guide.getEmergContact());
					

				} catch (Exception e1) {
					ViewUtils.showError(e1.getMessage());
				}
			}
		};

}

