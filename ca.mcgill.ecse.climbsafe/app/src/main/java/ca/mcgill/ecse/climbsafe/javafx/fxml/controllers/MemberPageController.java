package ca.mcgill.ecse.climbsafe.javafx.fxml.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet2Controller;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet4Controller;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet1Controller;
import ca.mcgill.ecse.climbsafe.controller.InvalidInputException;
import ca.mcgill.ecse.climbsafe.controller.TOMember;
import ca.mcgill.ecse.climbsafe.javafx.fxml.ClimbSafeFxmlView;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class MemberPageController {
	// add variables
	TOMember member;
	@FXML
	private List<Button> memberInSystem = new ArrayList<Button>();
	@FXML
	private Button clearButton; 
	@FXML
	private TextField name;
	@FXML
	private TextField email;
	@FXML
	private TextField password;
	@FXML
	private TextField emergContact;
	@FXML
	private TextField equipmentType;
	@FXML
	private TextField equipQuantity;
	@FXML
	private CheckBox hotel;
	@FXML
	private CheckBox guide;
	@FXML
	private TextField numWeeks;
	@FXML
	private Button addMember;
	@FXML
	private Button updateMember;
	@FXML
	private Button deleteMember;
	@FXML
	private VBox memDisplay;
	@FXML
	private ImageView logoImage;
	/**
	 * Initializes the the system
	 * @author Matt MacDonald
	 */
	@FXML
	public void initialize() {
		updateSystemItems();
		logoImage.setImage(new Image(getClass().getResource("climbsafeLogo.png").toExternalForm(),66,62,false,false));
	}
	/**
	 * Clears all text fields
	 * @author MattMacDonald
	 */
	public void clear() {
		name.clear();
		email.clear();
		password.clear();
		emergContact.clear();
		numWeeks.clear();
		equipmentType.clear();
		equipQuantity.clear();
		hotel.setSelected(false);
		guide.setSelected(false);
		numWeeks.clear();
	}
	/**
	 * Handles the equipment text field and returns a list that can be passed to a member 
	 * @author Matt MacDonald
	 * @return List<String>
	 */
	public List<String> getEquipment() {
		if(equipmentType.getText() == "" || equipmentType.getText() == null ) {
			return new ArrayList<String>();
		}
		String noSpace = equipmentType.getText().replaceAll("\\s", "");
		List<String> equip = Arrays.asList(noSpace.split(","));
		return equip;

	}
    /**
     * Handles the eqiupment quantity text field a returns a list that can be passed to a member 
     * @return List<Integer>
     * @author Matt MacDonald
     */
	public List<Integer> getEquipmentQuantity() {
		if(equipQuantity.getText() == "" || equipQuantity.getText() == null ) {
			return new ArrayList<Integer>();
		}
		String noSpace = equipQuantity.getText().replaceAll("\\s", "");
		List<String> equip = Arrays.asList(noSpace.split(","));
		List<Integer> equipquant = new ArrayList<Integer>();
		for(String i : equip) {
			equipquant.add(Integer.valueOf(i));
		}
		return equipquant;

	}
	/**
	 * Addes member to system when addMember button is pressed
	 * @author Matt MacDonald
	 * @param e
	 */
	public void addMember(ActionEvent e) {
		String mName = name.getText();
		if (mName == null || mName.trim().isEmpty()) {
			ViewUtils.showError("Please enter a valid name");
			return;
		}
		String mEmail = email.getText();
		if (mEmail == null || mEmail.trim().isEmpty()) {
			ViewUtils.showError("Please enter a valid email");
			return;
		}
		String mPassword = password.getText();
		if (mPassword == null || mPassword.trim().isEmpty()) {
			ViewUtils.showError("Please enter a valid password");
			return;
		}
		String mEmergContact = emergContact.getText();
		if (mEmergContact == null || mEmergContact.trim().isEmpty()) {
			ViewUtils.showError("Please enter a valid emergency contact");
		}
		Boolean mHotel = hotel.isSelected();
		Boolean mGuide = guide.isSelected();
		int mNumWeeks = -1;
		try {
			int mNumWeek = Integer.parseInt(numWeeks.getText().toString());

			mNumWeeks = mNumWeek;

		} catch (NumberFormatException ex) {
			ViewUtils.showError("Please enter a valid number of weeks");
		}
		

		try {
			ClimbSafeFeatureSet2Controller.registerMember(mEmail, mPassword, mName, mEmergContact, mNumWeeks, mGuide,
					mHotel, getEquipment(), getEquipmentQuantity());
			updateSystemItems(); 
			clear();

		} catch (InvalidInputException e1) {
			ViewUtils.showError(e1.getMessage());
		}
	}
	/**
	 * Updates member information when the updateMember button is pressed
	 * @author Matt MacDonald
	 * @param e
	 */
	public void updateMember(ActionEvent e) {
		String mName = name.getText();
		if (mName == null || mName.trim().isEmpty()) {
			ViewUtils.showError("Please enter a valid name");
			return;
		}
		String mEmail = email.getText();
		if (mEmail == null || mEmail.trim().isEmpty()) {
			ViewUtils.showError("Please enter a valid email");
			return;
		}
		String mPassword = password.getText();
		if (mPassword == null || mPassword.trim().isEmpty()) {
			ViewUtils.showError("Please enter a valid password");
			return;
		}
		String mEmergContact = emergContact.getText();
		if (mEmergContact == null || mEmergContact.trim().isEmpty()) {
			ViewUtils.showError("Please enter a valid emergency contact");
			return;
		}
		Boolean mHotel = hotel.isSelected();
		Boolean mGuide = guide.isSelected();
		int mNumWeeks = -1;
		try {
			int mNumWeek = Integer.parseInt(numWeeks.getText().toString());

			mNumWeeks = mNumWeek;

		} catch (NumberFormatException ex) {
			ViewUtils.showError("Please enter a valid number of weeks");
		}

		try {
			ClimbSafeFeatureSet2Controller.updateMember(mEmail, mPassword, mName, mEmergContact, mNumWeeks, mGuide,
					mHotel, getEquipment(), getEquipmentQuantity());
			updateSystemItems();
			clear();

		} catch (InvalidInputException e1) {
			ViewUtils.showError(e1.getMessage());
		}
	}
	/**
	 * Deletes a member from the system when the deleteMember button is pressed
	 * @author Matt MacDonald
	 * @param e
	 */
	public void deleteMember(ActionEvent e) {
		String mEmail = email.getText();
		if (mEmail == null || mEmail.trim().isEmpty()) {
			ViewUtils.showError("Please enter a valid email");
		}

		try {
			ClimbSafeFeatureSet1Controller.deleteMember(mEmail);
			updateSystemItems();
			clear();
		} catch (InvalidInputException e1) {
			ViewUtils.showError(e1.getMessage());
		}
	}
	/**
	 * Updates the member in system and displays all the current members in the Vbox
	 * @author Matt MacDonald
	 */
	private void updateSystemItems() {
		// removes all labels currently shown
		memDisplay.getChildren().removeAll(memberInSystem);
		memberInSystem.clear();
		// gets list of all equipment
		List<String> names = ClimbSafeFeatureSet2Controller.getAllMemberNames();
		for (int a = 0; a < names.size(); a++) {
			// creates a new button for each equipment item and formats it
			Button newBut = new Button(names.get(a));
			newBut.setStyle("-fx-background-color:  #1F271B; -fx-background-radius:10; -fx-cursor: hand");
			newBut.setTextFill(Color.ORANGE);
			newBut.setPrefSize(100, 20);
			newBut.setAlignment(Pos.CENTER);
			newBut.setFont(new Font(12));

			
			memberInSystem.add(newBut);
			memDisplay.getChildren().add(newBut);
			// adds the event handler to the button
			newBut.setOnAction(systemButtonPressed);
		}

	}
	/**
	 * Event handler for the visible members in the system
	 * @author Matt MacDonald
	 */
	EventHandler<ActionEvent> systemButtonPressed = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			// gets the text of the button which should be the equipment name
			String buttonText = ((Button) (e.getSource())).getText();

			try {
				// gets the equipment and updates all of the fields
				member = ClimbSafeFeatureSet2Controller.getMember(buttonText);
				name.setText(member.getName());
				email.setText(member.getEmail());
				password.setText(member.getPassword());
				emergContact.setText(member.getEmergencyContact());
				numWeeks.setText(String.valueOf(member.getNrWeeks()));
				hotel.setSelected(member.getHotelReq());
				guide.setSelected(member.getGuideReq());
				String equiptype = "";
				for (String s : member.getEquip()) {
					String q = s + ", ";
					equiptype = equiptype+q; 
				}
				equipmentType.setText(equiptype);
				String equipquant = "";
				for (String s : member.getEquipQuant()) {
					String q = s + ", ";
					equipquant=equipquant+q;
				}
				equipQuantity.setText(equipquant);

			} catch (Exception e1) {
				ViewUtils.showError(e1.getMessage());
			}
		}
	};


}
