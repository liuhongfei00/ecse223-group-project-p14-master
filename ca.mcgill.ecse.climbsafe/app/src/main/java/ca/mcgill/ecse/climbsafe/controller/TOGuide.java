/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.climbsafe.controller;

// line 45 "../../../../../ClimbSafeTransferObjects.ump"
public class TOGuide
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOGuide Attributes
  private String name;
  private String email;
  private String password;
  private String emergContact;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOGuide(String aName, String aEmail, String aPassword, String aEmergContact)
  {
    name = aName;
    email = aEmail;
    password = aPassword;
    emergContact = aEmergContact;
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

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
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

  public boolean setEmergContact(String aEmergContact)
  {
    boolean wasSet = false;
    emergContact = aEmergContact;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public String getEmail()
  {
    return email;
  }

  public String getPassword()
  {
    return password;
  }

  public String getEmergContact()
  {
    return emergContact;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "email" + ":" + getEmail()+ "," +
            "password" + ":" + getPassword()+ "," +
            "emergContact" + ":" + getEmergContact()+ "]";
  }
}