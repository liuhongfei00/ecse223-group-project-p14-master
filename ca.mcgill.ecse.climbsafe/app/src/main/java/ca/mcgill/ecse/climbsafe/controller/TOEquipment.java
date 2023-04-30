/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.climbsafe.controller;

// line 15 "../../../../../ClimbSafeTransferObjects.ump"
public class TOEquipment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOEquipment Attributes
  private String name;
  private int weight;
  private int price;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOEquipment(String aName, int aWeight, int aPrice)
  {
    name = aName;
    weight = aWeight;
    price = aPrice;
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

  public boolean setWeight(int aWeight)
  {
    boolean wasSet = false;
    weight = aWeight;
    wasSet = true;
    return wasSet;
  }

  public boolean setPrice(int aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public int getWeight()
  {
    return weight;
  }

  public int getPrice()
  {
    return price;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "weight" + ":" + getWeight()+ "," +
            "price" + ":" + getPrice()+ "]";
  }
}