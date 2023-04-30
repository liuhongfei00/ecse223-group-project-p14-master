package ca.mcgill.ecse.climbsafe.controller;

import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.model.*;
import ca.mcgill.ecse.climbsafe.persistance.*;

import java.util.ArrayList;
import java.util.List;

public class ClimbSafeFeatureSet2Controller {
	/**
	 * <p>
	 * This method registers a member to the Climbsafe system
	 * </p>
	 * 
	 * @param email            Desired email for the new member
	 * @param password         Desired password for the new member
	 * @param name             Name of member being registered
	 * @param emergencyContact Emergency contact for new member
	 * @param nrWeeks          Number of climbing weeks desired
	 * @param guideRequired    Whether or not the member is requesting a guide
	 * @param hotelRequired    Whether or not the member is requesting a hotel
	 * @param itemNames        Names of desired items
	 * @param itemQuantities   Quantities of desired items, in order of the item
	 *                         names given
	 * @throws InvalidInputException When inputs are not valid for registering a
	 *                               member
	 * @author Matthew MacDonald
	 */
	public static void registerMember(String email, String password, String name, String emergencyContact, int nrWeeks,
			boolean guideRequired, boolean hotelRequired, List<String> itemNames, List<Integer> itemQuantities)
			throws InvalidInputException {

		ClimbSafe climbSafe = ClimbSafeApplication.getClimbSafe();

		if (climbSafe.getNrWeeks() < nrWeeks | nrWeeks < 1) {
			throw new InvalidInputException(
					"The number of weeks must be greater than zero and less than or equal to the number of climbing weeks in the climbing season ");
		}
		if (email == null) {
			throw new InvalidInputException("Email cannot be empty");
		}
		if (password == null | password == "") {
			throw new InvalidInputException("The password cannot be empty");
		}
		if (name == null | name == "") {
			throw new InvalidInputException("The name cannot be empty");
		}
		if (emergencyContact == null | emergencyContact == "") {
			throw new InvalidInputException("The emergency contact cannot be empty");
		}
		if (itemNames == null) {
			throw new InvalidInputException("itemNames must be a valid List<String>");
		}
		if (itemQuantities == null) {
			throw new InvalidInputException("itemQuantities must be a valid List<Integer>");
		}
		if (itemNames.size() != itemQuantities.size()) {
			throw new InvalidInputException("itemNames and itemQuantities must have the same size");
		}
		if (User.getWithEmail(email) instanceof Guide) {
			throw new InvalidInputException("A guide with this email already exists");
		}
		if (User.getWithEmail(email) != null && User.getWithEmail(email) instanceof Member) {
			throw new InvalidInputException("A member with this email already exists");
		}

		for (int j = 0; j < itemNames.size(); j++) {

			if (!BookableItem.hasWithName(itemNames.get(j))) {
				throw new InvalidInputException("Requested item not found");
			}
		}
		if (email.contains(" ")) {
			throw new InvalidInputException("The email must not contain any spaces");
		}
		if (email.contains("admin@nmc.nt") && email.indexOf("a") == 0 && email.indexOf("@") == 5) {
			throw new InvalidInputException("The email entered is not allowed for members");
		}

		if (!email.contains("@") | !email.contains(".") | email.indexOf("@") < 1
				| !(email.indexOf("@") + 2 <= email.indexOf("."))
				| Math.abs(email.indexOf("@") - email.indexOf(".")) == 1
				| !(email.indexOf("@") == email.lastIndexOf("@"))) {
			throw new InvalidInputException("Invalid email");
		}

		Member m = climbSafe.addMember(email, password, name, emergencyContact, nrWeeks, guideRequired, hotelRequired);

		for (int i = 0; i < itemNames.size(); i++) {
			m.addBookedItem(itemQuantities.get(i), climbSafe, BookableItem.getWithName(itemNames.get(i)));
		}
		ClimbSafePersistence.save(climbSafe);

	}

	/**
	 * <p>
	 * This method updates a Member's information in the ClimbSafe system.
	 * </p>
	 * 
	 * @param email               The email of the member whose information is being
	 *                            updated
	 * @param newPassword         New password for the member account
	 * @param newName             New name for the member account
	 * @param newEmergencyContact New emergency contact for the member account
	 * @param newNrWeeks          New number of climbing weeks desired
	 * @param newGuideRequired    Whether or not the member wishes to have a guide
	 * @param newHotelRequired    Whether or not the member wishes to have a hotel
	 * @param newItemNames        New items requested by member
	 * @param newItemQuantities   New item quantities requested by member, in the
	 *                            same order as the item names
	 * @throws InvalidInputException When input information is not valid to update
	 *                               the member
	 * @author Matthew MacDonald
	 */

	public static void updateMember(String email, String newPassword, String newName, String newEmergencyContact,
			int newNrWeeks, boolean newGuideRequired, boolean newHotelRequired, List<String> newItemNames,
			List<Integer> newItemQuantities) throws InvalidInputException {

		ClimbSafe climbSafe = ClimbSafeApplication.getClimbSafe();
		if (newNrWeeks < 1 | newNrWeeks > climbSafe.getNrWeeks()) {
			throw new InvalidInputException(
					"The number of weeks must be greater than zero and less than or equal to the number of climbing weeks in the climbing season");
		}
		if (email == null | email == "") {
			throw new InvalidInputException("The email cannot be empty");
		}
		if (newPassword == null | newPassword == "") {
			throw new InvalidInputException("The password cannot be empty");
		}
		if (newName == null | newName == "") {
			throw new InvalidInputException("The name cannot be empty");
		}
		if (newEmergencyContact == null | newEmergencyContact == "") {
			throw new InvalidInputException("The emergency contact cannot be empty"); // there is a typo in the test
		}
		if (newItemNames == null) {
			throw new InvalidInputException("itemNames must be a valid List<String>");
		}
		if (newItemQuantities == null) {
			throw new InvalidInputException("itemQuantities must be a valid List<Integer>");

		}

		for (int j = 0; j < newItemNames.size(); j++) {

			if (!BookableItem.hasWithName(newItemNames.get(j))) {
				throw new InvalidInputException("Requested item not found");
			}
		}

		if (newItemNames.size() != newItemQuantities.size()) {
			throw new InvalidInputException("itemNames and itemQuantities must have the same size");
		}
		if (!(User.getWithEmail(email) instanceof Member)) {
			throw new InvalidInputException("Member not found");
		}
		if (User.getWithEmail(email) == null) {
			throw new InvalidInputException("Member not found");
		}

		Member m = (Member) User.getWithEmail(email);
		m.setPassword(newPassword);
		m.setNrWeeks(newNrWeeks);
		m.setHotelRequired(newHotelRequired);
		m.setGuideRequired(newGuideRequired);
		m.setName(newName);
		m.setEmergencyContact(newEmergencyContact);
		List<BookedItem> bookedItems = new ArrayList<BookedItem>(m.getBookedItems());

		for (BookedItem b : bookedItems) {
			m.removeBookedItem(b);
			b.delete();
		}

		for (int i = 0; i < newItemNames.size(); i++) {
			m.addBookedItem(newItemQuantities.get(i), climbSafe, BookableItem.getWithName(newItemNames.get(i)));
		}

		ClimbSafePersistence.save(climbSafe);
	}
/**
 * Returns a list of all the member's names in the system
 * @author Matt MacDonald
 * @return List<String>
 */
	public static List<String> getAllMemberNames() {
		ClimbSafe climbsafe = ClimbSafeApplication.getClimbSafe();
		List<String> names = new ArrayList<String>();
		var members = climbsafe.getMembers();
		for (int i = 0; i < members.size(); i++) {
			names.add(members.get(i).getName());
		}
		return names;
	}
	/**
	 * Returns a TOMember object corresponding to the member name give 
	 * @author Matt MacDonald
	 * @param name
	 * @return TOMember
	 * @throws Exception
	 */
	public static TOMember getMember(String name) throws Exception {
		ClimbSafe climbsafe = ClimbSafeApplication.getClimbSafe();
		for (Member m : climbsafe.getMembers()) {
			if (m.getName().equals(name)) {
				TOMember to = new TOMember(m.getName(), m.getPassword(), m.getEmail(), m.getEmergencyContact(), m.getRefundPercent(),
						m.getNrWeeks(), m.getHotelRequired(), m.getGuideRequired());
				for(BookedItem e : m.getBookedItems()) {
					to.addEquip(e.getItem().getName());
					to.addEquipQuant(String.valueOf(e.getQuantity()));
				}
				return to; 
			}
		}
		throw new Exception("Member does not exist in the system");

	}
}
