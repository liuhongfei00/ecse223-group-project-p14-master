
package ca.mcgill.ecse.climbsafe.javafx.fxml.controllers;

import ca.mcgill.ecse.climbsafe.controller.AssignmentController;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet2Controller;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet6Controller;
import ca.mcgill.ecse.climbsafe.controller.TOAssignment;
import ca.mcgill.ecse.climbsafe.controller.TOMember;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Assignments page setup
 * 
 * @author Hongfei Liu
 */
public class AssignmentsController {
	@FXML
	private TextField membernamefield;
	@FXML
	private TextField memberemailfield;
	@FXML
	private TextField guidenamefield;
	@FXML
	private TextField guideemailfield;
	@FXML
	private TextField guideECfield;
	@FXML
	private TextField equipmentcostfield;
	@FXML
	private TextField guidecostfield;
	@FXML
	private TextField startingweek;
	@FXML
	private TextField numofweeks;
	@FXML
	private TextField endingweek;
	@FXML
	private Label statusField;
	@FXML
	private Label authorizationField;
	@FXML
	private Label refundField;
	@FXML
	private Button viewassignmentbutton;
	@FXML
	private Button initializebutton;
	@FXML
	private ImageView logoImage;

	// if (climbSafe.)
	@FXML
	public void initialize() {
		logoImage.setImage(
				new Image(getClass().getResource("climbsafeLogo.png").toExternalForm(), 66, 62, false, false));
		guidenamefield.setEditable(false);
		guideemailfield.setEditable(false);
		guideECfield.setEditable(false);
		numofweeks.setEditable(false);
		startingweek.setEditable(false);
		endingweek.setEditable(false);
		equipmentcostfield.setEditable(false);
		guidecostfield.setEditable(false);
	}

	/**
	 * Initialize assignments in the system
	 * 
	 * @author Hongfei Liu
	 */
	@FXML
	private void initializebuttonClicked(ActionEvent event) {
		if (ViewUtils.successful(() -> AssignmentController.assignMembers())) {
			ViewUtils.makePopupWindow("Message", "Initialization completed");
		}
	}

	/**
	 * Assignments page user interface
	 * 
	 * @author Hongfei Liu
	 */
	@FXML
	private void viewassignmentClicked(ActionEvent event) {
		guidenamefield.setText("");
		guideemailfield.setText("");
		guideECfield.setText("");
		numofweeks.setText("");
		startingweek.setText("");
		endingweek.setText("");
		equipmentcostfield.setText("");
		guidecostfield.setText("");
		statusField.setText("");
		authorizationField.setText("");
		refundField.setText("");
		String memberName = membernamefield.getText();
		String memberEmail = memberemailfield.getText();
		if (memberName == null || memberName.trim().isEmpty()) {
			ViewUtils.showError("Please input a valid member name");
		} else if (memberEmail == null || memberEmail.trim().isEmpty()) {
			ViewUtils.showError("Please input a valid email");
		} else {
			try {
				List<TOAssignment> assignmentsList = new ArrayList<TOAssignment>();
				if (ViewUtils.successful(() -> ClimbSafeFeatureSet6Controller.getAssignments())) {
					assignmentsList = ClimbSafeFeatureSet6Controller.getAssignments();
					for (TOAssignment cur : assignmentsList) {
						if (cur.getMemberName().equals(memberName)) {
							if (cur.getMemberEmail().equals(memberEmail)) {
								equipmentcostfield.setText(String.valueOf(cur.getTotalCostForEquipment()));
								guidecostfield.setText(String.valueOf(cur.getTotalCostForGuide()));
								if (cur.getGuideName() != null) {
									guidenamefield.setText(cur.getGuideName());
									guideemailfield.setText(cur.getGuideEmail());
									guideECfield.setText(
											ClimbSafeFeatureSet2Controller.getMember(memberName).getEmergencyContact());
								}
								;
								String assignmentStatus = AssignmentController
										.getAssignmentStatus(cur.getMemberEmail());
								numofweeks.setText(String.valueOf(cur.getEndWeek() - cur.getStartWeek() + 1));
								startingweek.setText(String.valueOf(cur.getStartWeek()));
								endingweek.setText(String.valueOf(cur.getEndWeek()));
								statusField.setText(assignmentStatus);
								TOMember theMem = ClimbSafeFeatureSet2Controller.getMember(memberName);

								authorizationField.setText(cur.getAuthCode());
								refundField.setText(theMem.getRefundPercent() + "%");

								return;
							}
						}
					}

					ViewUtils.showError("Member name does not match email");
				}
			} catch (Exception e) {
				ViewUtils.showError(e.getMessage());
			}

		}
	}

}
