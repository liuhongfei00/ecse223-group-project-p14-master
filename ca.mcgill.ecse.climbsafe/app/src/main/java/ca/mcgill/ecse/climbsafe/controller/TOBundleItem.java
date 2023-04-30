/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.climbsafe.controller;

// line 39 "../../../../../ClimbSafeTransferObjects.ump"
public class TOBundleItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOBundleItem Attributes
  private int quantity;
  private String equipment;

  //TOBundleItem Associations
  private TOEquipmentBundle bundle;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOBundleItem(int aQuantity, String aEquipment, TOEquipmentBundle aBundle)
  {
    quantity = aQuantity;
    equipment = aEquipment;
    boolean didAddBundle = setBundle(aBundle);
    if (!didAddBundle)
    {
      throw new RuntimeException("Unable to create tOBundleItem due to bundle. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setQuantity(int aQuantity)
  {
    boolean wasSet = false;
    quantity = aQuantity;
    wasSet = true;
    return wasSet;
  }

  public boolean setEquipment(String aEquipment)
  {
    boolean wasSet = false;
    equipment = aEquipment;
    wasSet = true;
    return wasSet;
  }

  public int getQuantity()
  {
    return quantity;
  }

  public String getEquipment()
  {
    return equipment;
  }
  /* Code from template association_GetOne */
  public TOEquipmentBundle getBundle()
  {
    return bundle;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBundle(TOEquipmentBundle aBundle)
  {
    boolean wasSet = false;
    if (aBundle == null)
    {
      return wasSet;
    }

    TOEquipmentBundle existingBundle = bundle;
    bundle = aBundle;
    if (existingBundle != null && !existingBundle.equals(aBundle))
    {
      existingBundle.removeTOBundleItem(this);
    }
    bundle.addTOBundleItem(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    TOEquipmentBundle placeholderBundle = bundle;
    this.bundle = null;
    if(placeholderBundle != null)
    {
      placeholderBundle.removeTOBundleItem(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "quantity" + ":" + getQuantity()+ "," +
            "equipment" + ":" + getEquipment()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "bundle = "+(getBundle()!=null?Integer.toHexString(System.identityHashCode(getBundle())):"null");
  }
}