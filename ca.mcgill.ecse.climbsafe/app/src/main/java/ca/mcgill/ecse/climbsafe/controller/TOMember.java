/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.climbsafe.controller;
import java.util.*;

// line 20 "../../../../../ClimbSafeTransferObjects.ump"
public class TOMember
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOMember Attributes
  private String name;
  private String password;
  private String email;
  private String emergencyContact;
  private int refundPercent;
  private List<String> equip;
  private List<String> equipQuant;
  private int nrWeeks;
  private boolean hotelReq;
  private boolean guideReq;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOMember(String aName, String aPassword, String aEmail, String aEmergencyContact, int aRefundPercent, int aNrWeeks, boolean aHotelReq, boolean aGuideReq)
  {
    name = aName;
    password = aPassword;
    email = aEmail;
    emergencyContact = aEmergencyContact;
    refundPercent = aRefundPercent;
    equip = new ArrayList<String>();
    equipQuant = new ArrayList<String>();
    nrWeeks = aNrWeeks;
    hotelReq = aHotelReq;
    guideReq = aGuideReq;
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

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmergencyContact(String aEmergencyContact)
  {
    boolean wasSet = false;
    emergencyContact = aEmergencyContact;
    wasSet = true;
    return wasSet;
  }

  public boolean setRefundPercent(int aRefundPercent)
  {
    boolean wasSet = false;
    refundPercent = aRefundPercent;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetMany */
  public boolean addEquip(String aEquip)
  {
    boolean wasAdded = false;
    wasAdded = equip.add(aEquip);
    return wasAdded;
  }

  public boolean removeEquip(String aEquip)
  {
    boolean wasRemoved = false;
    wasRemoved = equip.remove(aEquip);
    return wasRemoved;
  }
  /* Code from template attribute_SetMany */
  public boolean addEquipQuant(String aEquipQuant)
  {
    boolean wasAdded = false;
    wasAdded = equipQuant.add(aEquipQuant);
    return wasAdded;
  }

  public boolean removeEquipQuant(String aEquipQuant)
  {
    boolean wasRemoved = false;
    wasRemoved = equipQuant.remove(aEquipQuant);
    return wasRemoved;
  }

  public boolean setNrWeeks(int aNrWeeks)
  {
    boolean wasSet = false;
    nrWeeks = aNrWeeks;
    wasSet = true;
    return wasSet;
  }

  public boolean setHotelReq(boolean aHotelReq)
  {
    boolean wasSet = false;
    hotelReq = aHotelReq;
    wasSet = true;
    return wasSet;
  }

  public boolean setGuideReq(boolean aGuideReq)
  {
    boolean wasSet = false;
    guideReq = aGuideReq;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public String getPassword()
  {
    return password;
  }

  public String getEmail()
  {
    return email;
  }

  public String getEmergencyContact()
  {
    return emergencyContact;
  }

  public int getRefundPercent()
  {
    return refundPercent;
  }
  /* Code from template attribute_GetMany */
  public String getEquip(int index)
  {
    String aEquip = equip.get(index);
    return aEquip;
  }

  public String[] getEquip()
  {
    String[] newEquip = equip.toArray(new String[equip.size()]);
    return newEquip;
  }

  public int numberOfEquip()
  {
    int number = equip.size();
    return number;
  }

  public boolean hasEquip()
  {
    boolean has = equip.size() > 0;
    return has;
  }

  public int indexOfEquip(String aEquip)
  {
    int index = equip.indexOf(aEquip);
    return index;
  }
  /* Code from template attribute_GetMany */
  public String getEquipQuant(int index)
  {
    String aEquipQuant = equipQuant.get(index);
    return aEquipQuant;
  }

  public String[] getEquipQuant()
  {
    String[] newEquipQuant = equipQuant.toArray(new String[equipQuant.size()]);
    return newEquipQuant;
  }

  public int numberOfEquipQuant()
  {
    int number = equipQuant.size();
    return number;
  }

  public boolean hasEquipQuant()
  {
    boolean has = equipQuant.size() > 0;
    return has;
  }

  public int indexOfEquipQuant(String aEquipQuant)
  {
    int index = equipQuant.indexOf(aEquipQuant);
    return index;
  }

  public int getNrWeeks()
  {
    return nrWeeks;
  }

  public boolean getHotelReq()
  {
    return hotelReq;
  }

  public boolean getGuideReq()
  {
    return guideReq;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isHotelReq()
  {
    return hotelReq;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isGuideReq()
  {
    return guideReq;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "password" + ":" + getPassword()+ "," +
            "email" + ":" + getEmail()+ "," +
            "emergencyContact" + ":" + getEmergencyContact()+ "," +
            "refundPercent" + ":" + getRefundPercent()+ "," +
            "nrWeeks" + ":" + getNrWeeks()+ "," +
            "hotelReq" + ":" + getHotelReq()+ "," +
            "guideReq" + ":" + getGuideReq()+ "]";
  }
}