package ca.mcgill.ecse.climbsafe.controller;

import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.model.BookableItem;
import ca.mcgill.ecse.climbsafe.model.BundleItem;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Equipment;
import ca.mcgill.ecse.climbsafe.model.EquipmentBundle;
import ca.mcgill.ecse.climbsafe.persistance.ClimbSafePersistence;

import java.util.ArrayList;
import java.util.List;

public class ClimbSafeFeatureSet5Controller {

	/**
	 * This method adds an Equipment Bundle
	 * 
	 * @author Sehr Moosabhoy
	 * @param name                the chosen name for the new Equipment Bundle
	 * @param discount            the discount amount for the new Equipment Bundle
	 * @param equipmentNames      A list of Strings corresponding to the names of
	 *                            the Equipment to be included in the new bundle
	 * @param equipmentQuantities A list of Integers corresponding to the quantity
	 *                            of each Equipment to be included in the new bundle
	 * @throws InvalidInputException An exception to be thrown when inputs are not
	 */
	public static void addEquipmentBundle(String name, int discount, List<String> equipmentNames,
			List<Integer> equipmentQuantities) throws InvalidInputException {

		ClimbSafe climbSafe = ClimbSafeApplication.getClimbSafe();
		var error = "";

		BookableItem b_exists = (EquipmentBundle.getWithName(name));

		if (b_exists != null) {
			error = "A bookable item called " + name + " already exists";
		}

		if (name == "")

		{
			error = "Equipment bundle name cannot be empty";
		}

		if (discount < 0) {
			error = "Discount must be at least 0";
		}

		if (discount > 100) {
			error = "Discount must be no more than 100";
		}

		if (equipmentNames.size() < 2) {
			error = "Equipment bundle must contain at least two distinct types of equipment";
		}

		if (error == "") {
			EquipmentBundle b = new EquipmentBundle(name, discount, climbSafe);

			for (int i = 0; i < equipmentNames.size(); i++) {
				String eName = equipmentNames.get(i);
				Equipment eq = (Equipment) Equipment.getWithName(eName);
				if (eq == null) {
					b.delete();
					error = "Equipment " + eName + " does not exist";
					break;
				}

				for (int j = 0; j < i; j++)
					if (b.getBundleItem(j).getEquipment() == eq) { // EquipmentBundle.minimumNumberOfBundleItems()???
						b.delete();
						error = "Equipment bundle must contain at least two distinct types of equipment";
						break;
					}

				if (equipmentQuantities.get(i) <= 0) {
					b.delete();
					error = "Each bundle item must have quantity greater than or equal to 1";
					break;
				}
				b.addBundleItem(equipmentQuantities.get(i), climbSafe, eq);
			}
		}

		if (error != "") {
			throw new InvalidInputException(error);
		}
		ClimbSafePersistence.save(climbSafe);

	}

	/**
	 * This method updates Equipment Bundles
	 * 
	 * @author Sehr Moosabhoy
	 * @param oldName                the current name of the bundle that is being
	 *                               updated
	 * @param newName                the new name for the bundle being updated
	 * @param newDiscount            the new discount for the bundle to be updated
	 * @param newEquipmentNames      the names of all the new equipment that will be
	 *                               in the bundle
	 * @param newEquipmentQuantities the new quantities for all the equipment in the
	 *                               updated bundle
	 * @throws InvalidInputException an exception for when the given inputs are not
	 *                               allowed
	 */
	public static void updateEquipmentBundle(String oldName, String newName, int newDiscount,
			List<String> newEquipmentNames, List<Integer> newEquipmentQuantities) throws InvalidInputException {

		ClimbSafe climbSafe = ClimbSafeApplication.getClimbSafe();

		BookableItem item = BookableItem.getWithName(oldName);

		var error = "";

		if (item == null || !(item instanceof EquipmentBundle)) {
			throw new InvalidInputException("Equipment bundle " + oldName + " does not exist");
		}

		EquipmentBundle b = (EquipmentBundle) item;

		BookableItem newNameBundle = BookableItem.getWithName(newName);

		if (newNameBundle != null && !((BookableItem) b).equals(newNameBundle)) {
			error = "A bookable item called " + newName + " already exists";
		}

		if (newName == "") {
			error = "Equipment bundle name cannot be empty";
		}

		if (newDiscount < 0) {
			error = "Discount must be at least 0";
		}

		if (newDiscount > 100) {
			error = "Discount must be no more than 100";
		}
		if (newEquipmentNames.size() == 0 || newEquipmentNames.get(0).trim().isEmpty()) {
			error = "Equipment bundle must contain at least two distinct types of equipment";
			throw new InvalidInputException(error);
		}

		
		if (error == "") {

			for (int i = 0; i < newEquipmentNames.size(); i++) {
				String eName = newEquipmentNames.get(i);
				Equipment eq = (Equipment) Equipment.getWithName(eName);

				if (eq == null) {
					error = "Equipment " + eName + " does not exist";
					throw new InvalidInputException(error);
				}

				for (int j = 0; j < i; j++) {
					if (eq == (Equipment) Equipment.getWithName(newEquipmentNames.get(j))) {
						error = "Equipment bundle must contain at least two distinct types of equipment";
						break;
					}
				}
			}

			if (newEquipmentNames.size() == 1 && error=="") {
				error = "Equipment bundle must contain at least two distinct types of equipment";
			}

			for (int i = 0; i < newEquipmentQuantities.size(); i++) {
				if (newEquipmentQuantities.get(i) <= 0) {
					error = "Each bundle item must have quantity greater than or equal to 1";
					break;
				}
			}
		}

		if (error != "") {

			throw new InvalidInputException(error);
		}

		for (int i = 0; i < newEquipmentNames.size(); i++) {
			String eName = newEquipmentNames.get(i);
			Equipment eq = (Equipment) Equipment.getWithName(eName);

			boolean exists = false;

			for (int j = 0; j < b.getBundleItems().size(); j++) {
				if (b.getBundleItem(j).getEquipment() == eq) {
					exists = true;
					b.getBundleItem(j).setQuantity(newEquipmentQuantities.get(i));
				}

			}
			if (!exists) {
				b.addBundleItem(newEquipmentQuantities.get(i), climbSafe, eq);
			}
		}

		if (error != "") {

			throw new InvalidInputException(error);
		}

		b.setName(newName);
		b.setDiscount(newDiscount);
		ClimbSafePersistence.save(climbSafe);
	}

	/**
	 * This method returns information about the system bundles with
	 * TOEquipmentBUndles rather than just EquipmentBundles
	 * 
	 * @author Sehr Moosabhoy
	 * @return a list of TOEquipmentBundles with the necessary information about the
	 *         bundles in the system
	 */
	public static List<TOEquipmentBundle> getBundles() {
		ClimbSafe climbSafe = ClimbSafeApplication.getClimbSafe();
		var bundles = new ArrayList<TOEquipmentBundle>();

		for (EquipmentBundle bundle : climbSafe.getBundles()) {
			TOEquipmentBundle b = new TOEquipmentBundle(bundle.getName(), bundle.getDiscount());
			for (BundleItem item : bundle.getBundleItems()) {
				b.addTOBundleItem(item.getQuantity(), item.getEquipment().getName());
			}
			bundles.add(b);
		}
		return bundles;
	}
}
