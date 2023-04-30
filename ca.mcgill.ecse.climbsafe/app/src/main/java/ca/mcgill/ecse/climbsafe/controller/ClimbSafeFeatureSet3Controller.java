package ca.mcgill.ecse.climbsafe.controller;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.model.BookedItem;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Guide;
import ca.mcgill.ecse.climbsafe.model.Member;
import ca.mcgill.ecse.climbsafe.model.User;
import ca.mcgill.ecse.climbsafe.persistance.ClimbSafePersistence;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ClimbSafeFeatureSet3Controller {

	/**
	 * @author Sabrina Mansour 
	 * RegisterGuide: adds a guide to the system
	 * 
	 * @param email
	 * @param password
	 * @param name
	 * @param emergencyContact
	 * @throws InvalidInputException
	 */
	public static void registerGuide(String email, String password, String name, String emergencyContact)
			throws InvalidInputException {

		ClimbSafe climbSafe = ClimbSafeApplication.getClimbSafe();

		User u = User.getWithEmail(email);
		
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

		var error = "";

		if (email.equals("admin@nmc.nt")) {

			error = "Email cannot be admin@nmc.nt";

		}

		if (!email.matches(regex)) { 
			error = "Invalid email";
		}
		
		if (u != null && u instanceof Guide) {

			error = "Email already linked to a guide account";

		}

		if (u != null && u instanceof Member) {

			error = "Email already linked to a member account";

		}

		if (email.contains(" ")) {

			error = "Email must not contain any spaces";

		}

		if (email == "") {

			error = "Email cannot be empty";

		}

		if (password == "") {

			error = "Password cannot be empty";

		}

		if (name == "") {

			error = "Name cannot be empty";

		}

		if (emergencyContact == "") {

			error = "Emergency contact cannot be empty";

		}

		if (!error.isEmpty()) {

			throw new InvalidInputException(error.trim());

		}

		Guide g = new Guide(email, password, name, emergencyContact, climbSafe);
		ClimbSafePersistence.save(climbSafe);

	}
	
	/**
	 * @author Sabrina Mansour
	 * UpdateGuide: updates specific information of an existing guide 
	 * 
	 * @param email
	 * @param newPassword
	 * @param newName
	 * @param newEmergencyContact
	 * @throws InvalidInputException
	 */
	public static void updateGuide(String email, String newPassword, String newName, String newEmergencyContact)

			throws InvalidInputException {

		var error = "";

		if (newPassword == "") {

			error = "Password cannot be empty";

		}

		if (newName == "") {

			error = "Name cannot be empty";

		}

		if (newEmergencyContact == "") {

			error = "Emergency contact cannot be empty";

		}

		if (!error.isEmpty()) {

			throw new InvalidInputException(error.trim());

		}

		User u = User.getWithEmail(email);

		if (u != null && u instanceof Guide) {

			Guide y = (Guide) u;

			y.setEmergencyContact(newEmergencyContact);

			y.setPassword(newPassword);

			y.setName(newName);

		}
		//ClimbSafePersistence.save();

	}
	public static List<String> getAllGuideNames() {
		ClimbSafe climbsafe = ClimbSafeApplication.getClimbSafe();
		List<String> names = new ArrayList<String>();
		var guides = climbsafe.getGuides();
		for (int i = 0; i < guides.size(); i++) {
			names.add(guides.get(i).getName());
		}
		return names;
	}
	
	public static TOGuide getGuide(String name) throws Exception {
		ClimbSafe climbsafe = ClimbSafeApplication.getClimbSafe();
		for (Guide m : climbsafe.getGuides()) {
			if (m.getName().equals(name)) {
				TOGuide to = new TOGuide(m.getName(), m.getEmail(),m.getPassword(), m.getEmergencyContact());
				return to; 
			}
		}
		throw new Exception("Guide does not exist in the system");

	}
	
}

	
	
