package ca.mcgill.ecse.climbsafe.controller;

import java.util.ArrayList;

import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.model.BookableItem;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Equipment;
import ca.mcgill.ecse.climbsafe.model.EquipmentBundle;
import ca.mcgill.ecse.climbsafe.persistance.ClimbSafePersistence;

public class ClimbSafeFeatureSet4Controller {
	/**
	 * Will add a new equipment if it is legal.
	 * 
	 * @param name
	 * @param weight
	 * @param pricePerWeek
	 * @throws InvalidInputException
	 * @author Zihan Zhang
	 */
	public static void addEquipment(String name, int weight, int pricePerWeek) throws InvalidInputException {
		ClimbSafe theClimbSafe = ClimbSafeApplication.getClimbSafe();
		if (name == null || name == "") {
			throw new InvalidInputException("The name must not be empty");
		}
		if (weight <= 0) {
			throw new InvalidInputException("The weight must be greater than 0");
		}
		if (pricePerWeek < 0) {
			throw new InvalidInputException("The price per week must be greater than or equal to 0");
		}
		for (EquipmentBundle cur : theClimbSafe.getBundles()) {
			if (cur.getName().equals(name)) {
				throw new InvalidInputException("The equipment bundle already exists");
			}
		}

		for (Equipment cur : theClimbSafe.getEquipment()) {
			if (cur.getName().equals(name)) {
				throw new InvalidInputException("The piece of equipment already exists");
			}
		}

		theClimbSafe.addEquipment(name, weight, pricePerWeek);
		ClimbSafePersistence.save(theClimbSafe);
	}

	/**
	 * Will update the information of a specific equipment if it exist.
	 * 
	 * @param oldName
	 * @param newName
	 * @param newWeight
	 * @param newPricePerWeek
	 * @throws InvalidInputException
	 * @author Zihan Zhang
	 */
	public static void updateEquipment(String oldName, String newName, int newWeight, int newPricePerWeek)
			throws InvalidInputException {
		ClimbSafe theClimbSafe = ClimbSafeApplication.getClimbSafe();

		if (!Equipment.hasWithName(oldName)) {
			throw new InvalidInputException("The piece of equipment does not exist");
		}
		if (newName == null || newName.isEmpty()) {
			throw new InvalidInputException("The name must not be empty");
		}
		if (newWeight <= 0) {
			throw new InvalidInputException("The weight must be greater than 0");
		}
		if (newPricePerWeek < 0) {
			throw new InvalidInputException("The price per week must be greater than or equal to 0");
		}

		if (oldName.equals(newName)) {
			BookableItem targetEquipment = Equipment.getWithName(oldName);
			targetEquipment.delete();
			new Equipment(oldName, newWeight, newPricePerWeek, theClimbSafe);
		} else {
			if (EquipmentBundle.hasWithName(newName)
					&& EquipmentBundle.getWithName(newName) instanceof EquipmentBundle) {
				throw new InvalidInputException("An equipment bundle with the same name already exists");
			}
			if (Equipment.hasWithName(newName) && Equipment.getWithName(newName) instanceof Equipment) {
				throw new InvalidInputException("The piece of equipment already exists");
			}
			Equipment targetEquipment = (Equipment) Equipment.getWithName(oldName);
			targetEquipment.setWeight(newWeight);
			targetEquipment.setPricePerWeek(newPricePerWeek);
			targetEquipment.setName(newName);

			targetEquipment = (Equipment) Equipment.getWithName(newName);
			ClimbSafePersistence.save(theClimbSafe);
		}
	}

	/**
	 * Get information about a equipment name
	 * 
	 * @param equipmentName the name to look for
	 * @return a transfer object with the name, weight and price
	 * @throws Exception if it does not exist in the system
	 * 
	 * @author Ryan Reszetnik
	 */
	public static TOEquipment getEquipment(String equipmentName) throws Exception {
		ClimbSafe theClimbSafe = ClimbSafeApplication.getClimbSafe();
		for (Equipment e : theClimbSafe.getEquipment()) {
			if (e.getName().equals(equipmentName)) {
				return new TOEquipment(e.getName(), e.getWeight(), e.getPricePerWeek());
			}
		}
		throw new Exception("Equipment does not exist in the system");
	}

	/**
	 * Gets a list of the equipment names in the system
	 * 
	 * @return the list of equipment names
	 * 
	 * @author Ryan Reszetnik
	 */
	public static ArrayList<String> getAllEquipmentNames() {
		ArrayList<String> names = new ArrayList<>();
		ClimbSafe theClimbSafe = ClimbSafeApplication.getClimbSafe();
		for (Equipment e : theClimbSafe.getEquipment()) {
			names.add(e.getName());
		}
		return names;
	}
}
