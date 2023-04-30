package ca.mcgill.ecse.climbsafe.controller;

import ca.mcgill.ecse.climbsafe.model.*;
import ca.mcgill.ecse.climbsafe.persistance.ClimbSafePersistence;

import java.util.ArrayList;
import java.util.List;

public class ClimbSafeFeatureSet6Controller {

  private static ClimbSafe climbSafe = ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication.getClimbSafe();

  /**
   * Will delete the given member's equipment
   * @author Hongfei Liu
   */
  public static void deleteEquipment(String name) throws InvalidInputException {
    var error = "The piece of equipment is in a bundle and cannot be deleted";
    List<EquipmentBundle> equipmentBundles = climbSafe.getBundles();
    for (EquipmentBundle cur1 : equipmentBundles) {
      List<BundleItem> bundleItems = cur1.getBundleItems();
      for (BundleItem item : bundleItems) {
        String itemName = item.getEquipment().getName();
        if (itemName.equals(name)) {
          throw new InvalidInputException(error);
        }
      }
    }
    List<Equipment> equipments = climbSafe.getEquipment();
    for (int i = 0; i < equipments.size(); i++) {
      Equipment equipmentItem = equipments.get(i);
      String equipmentName = equipmentItem.getName();
      if (equipmentName.equals(name)) {
        equipmentItem.delete();
      }
    }
    ClimbSafePersistence.save(climbSafe);

  }

  // this method does not need to be implemented by a team with five team members
  /**
   * Will delete the given member's equipmentbundle
   * @author Hongfei Liu
   */
  public static void deleteEquipmentBundle(String name) {
    List<EquipmentBundle> equipmentBundles = climbSafe.getBundles();
    for (int j = 0; j < equipmentBundles.size(); j++) {
      EquipmentBundle bundle = equipmentBundles.get(j);
      String BundleName = bundle.getName();
      if (BundleName.equals(name)) {
        bundle.delete();
      }
    }
    ClimbSafePersistence.save(climbSafe);
  }
  /**
   * Will calculate the members' bill
   * @return a TOAssignment
   * @author Hongfei Liu
   */
  public static List<TOAssignment> getAssignments() {
    List<Member> memberList = climbSafe.getMembers();
    List<TOAssignment> theToAssignments = new ArrayList<TOAssignment>();
    // go through all members
    for (Member curMember : memberList) {
      Assignment theAssignment = curMember.getAssignment();
      if(theAssignment!=null){
      TOAssignment target = new TOAssignment(curMember.getEmail(), curMember.getName(), null, null, null, theAssignment.getAuthCode(),
          theAssignment.getStartWeek(), theAssignment.getEndWeek(), 0, 0);

      // the case for the member who has guide
      if (curMember.isGuideRequired()) {
        Guide theGuide = theAssignment.getGuide();
        target.setGuideEmail(theGuide.getEmail());
        target.setGuideName(theGuide.getName());
        target.setTotalCostForGuide(climbSafe.getPriceOfGuidePerWeek() * curMember.getNrWeeks());
      }
      
      if (curMember.isHotelRequired() && theAssignment.getHotel() != null) {
          target.setHotelName(theAssignment.getHotel().getName());
        }

      // the case for the member who has items
      if (!curMember.getBookedItems().isEmpty()) {
        List<BookedItem> itemList = curMember.getBookedItems();

        // the case for the bundle which can apply discount on it
        if (curMember.isGuideRequired()) {
          Double total = 0.0;
          // go through all items
          for (BookedItem curItem : itemList) {
            // if the item is a bundle
            if (curItem.getItem() instanceof EquipmentBundle) {
              EquipmentBundle theBundle = (EquipmentBundle) curItem.getItem();
              Double discount = 1 - (theBundle.getDiscount() / 100.0);
              for (BundleItem curBundleItem : theBundle.getBundleItems()) {
                Equipment theEquipment = curBundleItem.getEquipment();
                total = total
                    + curBundleItem.getQuantity() * theEquipment.getPricePerWeek() * curMember.getNrWeeks() * discount*curItem.getQuantity();
              }
            }
            // if the item is a equipment
            else {
              Equipment theEquipment = (Equipment) curItem.getItem();
              total = total + curItem.getQuantity() * theEquipment.getPricePerWeek() * curMember.getNrWeeks();
            }
          }
          target.setTotalCostForEquipment(total.intValue());
        }

        // the case for the bundle which can not apply dicount on it
        else {
          int total = 0;
          for (BookedItem curItem : itemList) {
            if (curItem.getItem() instanceof EquipmentBundle) {
              EquipmentBundle theBundle = (EquipmentBundle) curItem.getItem();
              for (BundleItem curBundleItem : theBundle.getBundleItems()) {
                Equipment theEquipment = curBundleItem.getEquipment();
                total = total + (curBundleItem.getQuantity() * theEquipment.getPricePerWeek() * curMember.getNrWeeks())*curItem.getQuantity();
              }
            } else {
              Equipment theEquipment = (Equipment) curItem.getItem();
              total = total + (curItem.getQuantity() * theEquipment.getPricePerWeek() * curMember.getNrWeeks());
            }
            
          }
          target.setTotalCostForEquipment(total);
        }
      }
      theToAssignments.add(target);
    }
    }
    ClimbSafePersistence.save(climbSafe);
    return theToAssignments;

  }
}
