package ca.mcgill.ecse.climbsafe.javafx.fxml.controllers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet5Controller;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet6Controller;
import ca.mcgill.ecse.climbsafe.controller.TOBundleItem;
import ca.mcgill.ecse.climbsafe.controller.TOEquipmentBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;

/**
 * This class handles all logic for the bundle page 
 * @author Sehr Moosabhoy
 */
public class BundlePageController {

	@FXML private TextField newBundleName;
	@FXML private TextField guideDiscount;
	@FXML private TextField itemQuantity;
	@FXML private TextField equipmentToAdd;
	@FXML private ChoiceBox<String> bundleChoiceBox;
	@FXML private ChoiceBox<String> removeItemChoiceBox;
	@FXML private Button addItemButton;
	@FXML private Button addBundleButton;
	@FXML private Button clearItemsButton;
	@FXML private Button removeItemButton;
	@FXML private Button updateBundleButton;
	@FXML private Button deleteBundleButton;
	@FXML private ListView<String> alreadyAddedItems;
	@FXML private ImageView logoImage;
	private ObservableList<String> bundleNames = FXCollections.observableArrayList();
	private ObservableList<String> bundleEquipment = FXCollections.observableArrayList();
	private ObservableList<String> currentData  = FXCollections.observableArrayList();
	private List<Integer> quantities = new ArrayList<Integer>();




	@FXML
	/**
	 * this method sets up the page when it is loaded
	 * @author Sehr Moosabhoy
	 */
	public void initialize() {
		logoImage.setImage(new Image(getClass().getResource("climbsafeLogo.png").toExternalForm(),66,62,false,false));
		bundleNames.add("New Bundle");
		for (TOEquipmentBundle b: ClimbSafeFeatureSet5Controller.getBundles()) {
			bundleNames.add(b.getName());
		}
		bundleChoiceBox.setItems(bundleNames);
		bundleChoiceBox.setValue("New Bundle");

	}
	/**
	 * this method adds an item to the local list of equipment
	 * @author Sehr Moosabhoy
	 * @param event action from addItemButton
	 */
	//Event Listener on Button[#addItemButton].onAction
	@FXML
	public void addItemToNewBundle(ActionEvent event) {
		String eqName = equipmentToAdd.getText();
		if (eqName == null || eqName.trim().isEmpty() || bundleEquipment.contains(eqName)) {
			ViewUtils.showError("Please input a valid, unique equipment item name");
		}
		else {
			try {
				Integer quantity = Integer.parseInt(itemQuantity.getText());
				listItems(eqName, quantity);
			} catch (NumberFormatException e) {
				ViewUtils.showError("Please input a valid quantity");
			}
		}

		System.out.println(Arrays.toString(bundleEquipment.toArray())+" "+ Arrays.toString(quantities.toArray()));
	}
	
	/**
	 * this method reloads a bundles equipment from the controller
	 * @author Sehr Moosabhoy
	 */
	public void updateBox() {
		currentData.clear();
		bundleEquipment.clear();
		quantities.clear();
		String selectedBundle = bundleChoiceBox.getValue();
		if (selectedBundle != "New Bundle") {
			int index = bundleNames.indexOf(selectedBundle) - 1;
			TOEquipmentBundle b = ClimbSafeFeatureSet5Controller.getBundles().get(index);
			//currentData.add("discount: " + b.getDiscount());
			newBundleName.setText(selectedBundle);
			guideDiscount.setText(Integer.toString(b.getDiscount()));
			for (TOBundleItem item: b.getTOBundleItems()) {
				currentData.add(item.getEquipment() + " -> quantity: " + item.getQuantity());
				bundleEquipment.add(item.getEquipment());
				quantities.add(item.getQuantity());

			}
			addBundleButton.setVisible(false);
			updateBundleButton.setVisible(true);
			deleteBundleButton.setVisible(true);
		}
		else {
			addBundleButton.setVisible(true);
			updateBundleButton.setVisible(false);
			deleteBundleButton.setVisible(false);
			newBundleName.setText("");
			guideDiscount.setText("");
			itemQuantity.setText("");
			equipmentToAdd.setText("");
		}

		reloadItems();	
	}

	/**
	 * this method shows the data of an existing bundle when it is selected
	 * @author Sehr Moosabhoy
	 * @param event action from bundleChoiceBox
	 */
	//Event Listener on ChoiceBox[#bundleChoiceBox].onAction
	@FXML
	public void showItems(ActionEvent event) {
		updateBox();
	}

	/**
	 * this method adds a new entry to the local equipment and quantities lists
	 * @author Sehr Moosabhoy
	 * @param eqName name of equipment to add
	 * @param quantity quantity of equipment to add
	 */
	private void listItems(String eqName, int quantity) {
		bundleEquipment.add(eqName);
		quantities.add(quantity);
		currentData.add(eqName + " -> quantity: " + quantity);
		reloadItems();
	}

	/**
	 * this method reloads the list views and choice boxes when a change has been made
	 * @author Sehr Moosabhoy
	 */
	private void reloadItems() {
		alreadyAddedItems.setItems(currentData);
		removeItemChoiceBox.setItems(bundleEquipment);
		removeItemChoiceBox.setValue(null);
	}



	/**
	 * this method adds a bundle through the controller method
	 * @author Sehr Moosabhoy
	 * @param event action from addBundleButton
	 */
	//Event Listener on Button[#addBundleButton].onAction
	@FXML
	public void addNewBundle(ActionEvent event) {
		String name = newBundleName.getText();
		String discountString = guideDiscount.getText();

		if (name == null || name.trim().isEmpty()) {
			ViewUtils.showError("Please input a valid bundle name");
		} else {
			try {
				int discount = Integer.parseInt(discountString);
				if (ViewUtils.successful(() -> ClimbSafeFeatureSet5Controller.addEquipmentBundle(name, discount, bundleEquipment,
						quantities))) {
					clearItemsButton.fire();
					newBundleName.setText("");
					guideDiscount.setText("");
					bundleNames.add(name);
					bundleChoiceBox.setItems(bundleNames);
				}

			} catch (NumberFormatException e) {
				ViewUtils.showError("Please input a valid discount");
			}
		}
	}

	/**
	 * this method updates a bundle through the controller method
	 * @author Sehr Moosabhoy
	 * @param event action from updateBundleButton
	 */
	//Event Listener on Button[#updateBundleButton].onAction
	@FXML
	public void updateBundle(ActionEvent event) {
		String selectedBundle = bundleChoiceBox.getValue();
		int index = bundleNames.indexOf(selectedBundle);
		TOEquipmentBundle b = ClimbSafeFeatureSet5Controller.getBundles().get(index - 1);
		String name;
		String discountString = guideDiscount.getText();
		int discount;

		if (newBundleName.getText() == null || newBundleName.getText().trim().isEmpty()) {
			name = b.getName();
		} 
		else { 
			name = newBundleName.getText();
		}

		try {
			if (discountString != null && !discountString.trim().isEmpty()) {
				discount = Integer.parseInt(discountString);
			}
			else {
				discount = b.getDiscount();
			}
			System.out.println(Arrays.toString(bundleEquipment.toArray())+" "+ Arrays.toString(quantities.toArray()));
			if (ViewUtils.successful(() -> ClimbSafeFeatureSet5Controller.updateEquipmentBundle(b.getName(), name, discount, bundleEquipment,
					quantities))) {
				newBundleName.setText("");
				itemQuantity.setText("");
				equipmentToAdd.setText("");
				guideDiscount.setText(Integer.toString(discount));
				bundleNames.remove(index);
				
				bundleNames.add(index, name);
				
				bundleChoiceBox.setValue(name);
				updateBox();
			}
		} catch (NumberFormatException e) {
			ViewUtils.showError("Please input a valid discount");
		}
	}


	/**
	 * this method deletes a bundle through the controller method
	 * @author Sehr Moosabhoy
	 * @param event action from deleteBundleButton
	 */
	//Event Listener on Button[#deleteBundleButton].onAction
	@FXML
	public void deleteBundle(ActionEvent event) {
		String selectedBundle = bundleChoiceBox.getValue();
		if (selectedBundle == null) {
			ViewUtils.showError("Please choose a bundle");
		} else {
			if (ViewUtils.successful(() -> ClimbSafeFeatureSet6Controller.deleteEquipmentBundle(selectedBundle))) {
				clearItemsButton.fire();
				newBundleName.setText("");
				guideDiscount.setText("");
				bundleChoiceBox.setValue("New Bundle");
				bundleNames.remove(selectedBundle);
				bundleChoiceBox.setItems(bundleNames);


			}
		}
	}

	/**
	 * this method clears the local equipment and quantity lists for a bundle
	 * @author Sehr Moosabhoy
	 * @param event action from clearItemsButton
	 */
	// Event Listener on Button[#clearItemsButton].onAction
	@FXML 
	public void clearBundleItems(ActionEvent event) {
		itemQuantity.setText("");
		equipmentToAdd.setText("");
		bundleEquipment.clear();
		currentData.clear();
		quantities.clear();


	}

	/**
	 * this method removes a piece of equipment from the local lists
	 * @author Sehr Moosabhoy
	 * @param event action from removeItemButton
	 */
	//Event Listener on Button[#removeItemButton].onAction
	@FXML
	public void removeItemFromBundle(ActionEvent event) {
		String eq = removeItemChoiceBox.getValue();
		if (eq == null) {
			ViewUtils.showError("Please select a valid equipment");
		} else {
			int index = bundleEquipment.indexOf(eq);
			currentData.remove(index);
			quantities.remove(index);
			bundleEquipment.remove(index);
			reloadItems();
		}

	}


}
