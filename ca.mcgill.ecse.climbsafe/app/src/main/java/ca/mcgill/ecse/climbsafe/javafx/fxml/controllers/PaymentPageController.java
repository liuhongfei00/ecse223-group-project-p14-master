package ca.mcgill.ecse.climbsafe.javafx.fxml.controllers;

import ca.mcgill.ecse.climbsafe.controller.AssignmentController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



/**
 * This class handles all logic for the payment page 
 *
 * @author Ryan Reszetnik
 */
public class PaymentPageController {
	@FXML 
	private TextField emailInput; 
	@FXML 
	private TextField codeInput; 
	@FXML 
	private Button payButton;

	@FXML private ImageView logoImage;

	/**
	 * Loads the initial items onto the screen
	 * 
	 * @author Ryan Reszentik
	 */
	@FXML
	public void initialize() {
		logoImage.setImage(new Image(getClass().getResource("climbsafeLogo.png").toExternalForm(),66,62,false,false));
	}
	/**
	 * Attempts to pay for a member's trip given the inputs provided
	 * 
	 * @param event
	 * 
	 * @author Ryan Reszentik
	 */
	@FXML 
	private void payForMember(ActionEvent event) { 
		try {
			AssignmentController.payForMemberTrip(emailInput.getText(), codeInput.getText());
			ViewUtils.makePopupWindow("Successful", "Payed for trip successfully");
			emailInput.setText("");
			codeInput.setText("");

		} catch (Exception e) {
			ViewUtils.showError(e.getMessage());
		} 
	}

}
