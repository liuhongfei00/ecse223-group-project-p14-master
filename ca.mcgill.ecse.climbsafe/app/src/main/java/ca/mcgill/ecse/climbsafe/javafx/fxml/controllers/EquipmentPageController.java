package ca.mcgill.ecse.climbsafe.javafx.fxml.controllers;

import java.util.ArrayList;

import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet4Controller;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet6Controller;
import ca.mcgill.ecse.climbsafe.controller.TOEquipment;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Label;

/**
 * This class is the controller for everything on the equipment page
 * 
 * @author Ryan Reszetnik
 */

public class EquipmentPageController {
	TOEquipment equipment;
	ArrayList<Button> systemEqupmentLabels = new ArrayList<Button>();
	@FXML
	private AnchorPane systemItemArea;
	@FXML
	private Label selectedItemName;
	@FXML
	private TextField nameField;
	@FXML
	private TextField weightField;
	@FXML
	private TextField priceField;
	@FXML
	private Button cancelButton;
	@FXML
	private Button addToSystemButton;
	@FXML
	private Button removeItemButton;
	@FXML
	private ImageView logoImage;

	/**
	 * Loads the initial items onto the screen
	 * 
	 * @author Ryan Reszetnik
	 */
	@FXML
	public void initialize() {
		updateSystemItems();
		logoImage.setImage(new Image(getClass().getResource("climbsafeLogo.png").toExternalForm(),66,62,false,false));
	}

	/**
	 * Clears the inputs when cancel is pressed
	 * 
	 * @param event
	 * @author Ryan Reszetnik
	 */
	@FXML
	public void cancelClicked(ActionEvent event) {
		clearInputs();

	}

	/**
	 * Clears all of the inputs
	 * 
	 * @author Ryan Reszetnik
	 */
	private void clearInputs() {
		nameField.setText("");
		weightField.setText("");
		priceField.setText("");
		selectedItemName.setText("");
		equipment = null;
		addToSystemButton.setText("Add Item");
		removeItemButton.setVisible(false);

	}

	/**
	 * Tries to delete the selected equipment in the system
	 * 
	 * @param event
	 * 
	 * @author Ryan Reszetnik
	 */
	@FXML
	public void removeItem(ActionEvent event) {
		try {
			ClimbSafeFeatureSet6Controller.deleteEquipment(selectedItemName.getText());
			updateSystemItems();
			clearInputs();
		} catch (Exception e) {
			ViewUtils.showError(e.getMessage());
		}

	}

	/**
	 * Either updates current equipment or adds new equipment to system
	 * 
	 * @param event
	 * @author Ryan Reszetnik
	 */
	@FXML
	public void addToSystem(ActionEvent event) {
		int weight = 0;
		int price = 0;
		// shows errors if the price or weight are not numbers
		try {
			weight = Integer.valueOf(weightField.getText());
		} catch (Exception e) {
			ViewUtils.showError("Weight must be a whole number");
			return;
		}
		try {
			price = Integer.valueOf(priceField.getText());
		} catch (Exception e) {
			ViewUtils.showError("Price must be a whole number");
			return;
		}

		try {
			// if you have an item selected update it otherwise add a new one
			if (equipment != null) {
				ClimbSafeFeatureSet4Controller.updateEquipment(selectedItemName.getText(), nameField.getText(), weight,
						price);
			} else {

				ClimbSafeFeatureSet4Controller.addEquipment(nameField.getText(), Integer.valueOf(weightField.getText()),
						Integer.valueOf(priceField.getText()));

			}
			clearInputs();
		} catch (Exception e) {
			ViewUtils.showError(e.getMessage());
		}
		// refresh the screen
		updateSystemItems();

	}

	/**
	 * Updates the top area to show all of the equipment in the system
	 * 
	 * @author Ryan Reszetnik
	 */
	private void updateSystemItems() {
		// removes all labels currently shown
		systemItemArea.getChildren().removeAll(systemEqupmentLabels);
		systemEqupmentLabels.clear();
		// gets list of all equipment
		ArrayList<String> names = ClimbSafeFeatureSet4Controller.getAllEquipmentNames();
		for (int a = 0; a < names.size(); a++) {
			// creates a new button for each equipment item and formats it
			Button newBut = new Button(names.get(a));
			newBut.setStyle("-fx-background-color: #f0611a80; -fx-background-radius:15; -fx-cursor: hand");
			newBut.setTextFill(Color.WHITE);
			newBut.setPrefSize(130, 30);
			newBut.setAlignment(Pos.CENTER);
			newBut.setFont(new Font(16));
			newBut.setTranslateX(a % 4 * 140 + 10);
			newBut.setTranslateY(a / 4 * 35 + 5);
			systemEqupmentLabels.add(newBut);
			systemItemArea.getChildren().add(newBut);
			// adds the event handler to the button
			newBut.setOnAction(systemButtonPressed);
		}
		// formats how big the scroll area is
		int height = names.size() < 13 ? 120 : 120 + (names.size() - 9) / 4 * 35;
		systemItemArea.setMinHeight(height);
	}

	/**
	 * The event handler for any of the equipment buttons to select that equipment
	 * 
	 * @author Ryan Reszentik
	 */
	EventHandler<ActionEvent> systemButtonPressed = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			// gets the text of the button which should be the equipment name
			String buttonText = ((Button) (e.getSource())).getText();

			try {
				// gets the equipment and updates all of the fields
				equipment = ClimbSafeFeatureSet4Controller.getEquipment(buttonText);
				selectedItemName.setText(equipment.getName());
				nameField.setText(equipment.getName());
				weightField.setText(equipment.getWeight() + "");
				priceField.setText(equipment.getPrice() + "");
				addToSystemButton.setText("Update Item");
				removeItemButton.setVisible(true);
			} catch (Exception ex) {
				ViewUtils.showError(ex.getMessage());
			}
		}
	};

}
