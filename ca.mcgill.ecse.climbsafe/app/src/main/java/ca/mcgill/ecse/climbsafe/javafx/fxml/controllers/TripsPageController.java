package ca.mcgill.ecse.climbsafe.javafx.fxml.controllers;

import ca.mcgill.ecse.climbsafe.controller.AssignmentController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * This class handles all logic for the trips page 
 * @author Sehr Moosabhoy
 */
public class TripsPageController {
	@FXML 
	private TextField memberEmail; 
	@FXML 
	private TextField weekNumber; 
	@FXML 
	private Button startTripButton;
	@FXML 
	private Button finishTripButton;
	@FXML 
	private Button cancelTripButton;
	@FXML
	private ImageView logoImage;
	
	/**
	 * This method initializes the logo image when the page is run
	 * @author Sehr Moosabhoy
	 */
	@FXML
	public void initialize() {
		logoImage.setImage(new Image(getClass().getResource("climbsafeLogo.png").toExternalForm(),66,62,false,false));
	}

	/**
	 * this method starts trips in a certain week using the controller method
	 * @author Sehr Moosabhoy
	 * @param event action from startTripsButton
	 */
	//Event Listener on Button[#startTripsButton].onAction
	@FXML
	private void startTrips(ActionEvent event) {
		int weekNr;
		try {
			weekNr = Integer.parseInt(weekNumber.getText());

			if(ViewUtils.successful(() -> AssignmentController.startTripsForWeek(weekNr))) {
				weekNumber.setText("");
				ViewUtils.makePopupWindow("Successful", "Started trips for week " + weekNr);
			}
		}catch(NumberFormatException e){
			ViewUtils.showError("Please input a valid week number");
		}

	}

	/**
	 * this method finishes a member trip using the controller method
	 * @author Sehr Moosabhoy
	 * @param event action from finishTripButton
	 */
	//Event Listener on Button[#finishTripButton].onAction
	@FXML
	private void finishTrip(ActionEvent event) { 
		String member = memberEmail.getText();

		if(ViewUtils.successful(() -> AssignmentController.finishMemberTrip(member))) {
			memberEmail.setText("");
			ViewUtils.makePopupWindow("Successful", "Finished trip successfully");
		}
	}

	/**
	 * this method cancels a member trip using the controller method
	 * @author Sehr Moosabhoy
	 * @param event action from cancelTripButton
	 */
	//Event Listener on Button[#cancelTripButton].onAction
	@FXML
	private void cancelTrip(ActionEvent event) { 
		String member = memberEmail.getText();

		if(ViewUtils.successful(() -> AssignmentController.cancelMemberTrip(member))) {
			memberEmail.setText("");
			ViewUtils.makePopupWindow("Successful", "Cancelled trip successfully");
		}
	}

}
