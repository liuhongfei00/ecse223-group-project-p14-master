/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.climbsafe.controller;
import java.util.*;

// line 34 "../../../../../ClimbSafeTransferObjects.ump"
public class TOEquipmentBundle
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOEquipmentBundle Attributes
  private String name;
  private int discount;

  //TOEquipmentBundle Associations
  private List<TOBundleItem> tOBundleItems;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOEquipmentBundle(String aName, int aDiscount)
  {
    name = aName;
    discount = aDiscount;
    tOBundleItems = new ArrayList<TOBundleItem>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setDiscount(int aDiscount)
  {
    boolean wasSet = false;
    discount = aDiscount;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public int getDiscount()
  {
    return discount;
  }
  /* Code from template association_GetMany */
  public TOBundleItem getTOBundleItem(int index)
  {
    TOBundleItem aTOBundleItem = tOBundleItems.get(index);
    return aTOBundleItem;
  }

  public List<TOBundleItem> getTOBundleItems()
  {
    List<TOBundleItem> newTOBundleItems = Collections.unmodifiableList(tOBundleItems);
    return newTOBundleItems;
  }

  public int numberOfTOBundleItems()
  {
    int number = tOBundleItems.size();
    return number;
  }

  public boolean hasTOBundleItems()
  {
    boolean has = tOBundleItems.size() > 0;
    return has;
  }

  public int indexOfTOBundleItem(TOBundleItem aTOBundleItem)
  {
    int index = tOBundleItems.indexOf(aTOBundleItem);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTOBundleItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public TOBundleItem addTOBundleItem(int aQuantity, String aEquipment)
  {
    return new TOBundleItem(aQuantity, aEquipment, this);
  }

  public boolean addTOBundleItem(TOBundleItem aTOBundleItem)
  {
    boolean wasAdded = false;
    if (tOBundleItems.contains(aTOBundleItem)) { return false; }
    TOEquipmentBundle existingBundle = aTOBundleItem.getBundle();
    boolean isNewBundle = existingBundle != null && !this.equals(existingBundle);
    if (isNewBundle)
    {
      aTOBundleItem.setBundle(this);
    }
    else
    {
      tOBundleItems.add(aTOBundleItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTOBundleItem(TOBundleItem aTOBundleItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aTOBundleItem, as it must always have a bundle
    if (!this.equals(aTOBundleItem.getBundle()))
    {
      tOBundleItems.remove(aTOBundleItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTOBundleItemAt(TOBundleItem aTOBundleItem, int index)
  {  
    boolean wasAdded = false;
    if(addTOBundleItem(aTOBundleItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTOBundleItems()) { index = numberOfTOBundleItems() - 1; }
      tOBundleItems.remove(aTOBundleItem);
      tOBundleItems.add(index, aTOBundleItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTOBundleItemAt(TOBundleItem aTOBundleItem, int index)
  {
    boolean wasAdded = false;
    if(tOBundleItems.contains(aTOBundleItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTOBundleItems()) { index = numberOfTOBundleItems() - 1; }
      tOBundleItems.remove(aTOBundleItem);
      tOBundleItems.add(index, aTOBundleItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTOBundleItemAt(aTOBundleItem, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=tOBundleItems.size(); i > 0; i--)
    {
      TOBundleItem aTOBundleItem = tOBundleItems.get(i - 1);
      aTOBundleItem.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "discount" + ":" + getDiscount()+ "]";
  }
}