/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.climbsafe.model;
import java.io.Serializable;

// line 2 "../../../../../ClimbSafeStates.ump"
// line 43 "../../../../../ClimbSafePersistence.ump"
// line 101 "../../../../../ClimbSafe.ump"
public class Assignment implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Assignment Attributes
  private int startWeek;
  private int endWeek;
  private String authCode;

  //Assignment State Machines
  public enum AssignmentStatus { Assigned, Paid, Started, Cancelled, Finished }
  private AssignmentStatus assignmentStatus;

  //Assignment Associations
  private Member member;
  private Guide guide;
  private Hotel hotel;
  private ClimbSafe climbSafe;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Assignment(int aStartWeek, int aEndWeek, Member aMember, ClimbSafe aClimbSafe)
  {
    startWeek = aStartWeek;
    endWeek = aEndWeek;
    authCode = null;
    boolean didAddMember = setMember(aMember);
    if (!didAddMember)
    {
      throw new RuntimeException("Unable to create assignment due to member. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddClimbSafe = setClimbSafe(aClimbSafe);
    if (!didAddClimbSafe)
    {
      throw new RuntimeException("Unable to create assignment due to climbSafe. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    setAssignmentStatus(AssignmentStatus.Assigned);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStartWeek(int aStartWeek)
  {
    boolean wasSet = false;
    startWeek = aStartWeek;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndWeek(int aEndWeek)
  {
    boolean wasSet = false;
    endWeek = aEndWeek;
    wasSet = true;
    return wasSet;
  }

  public boolean setAuthCode(String aAuthCode)
  {
    boolean wasSet = false;
    authCode = aAuthCode;
    wasSet = true;
    return wasSet;
  }

  public int getStartWeek()
  {
    return startWeek;
  }

  public int getEndWeek()
  {
    return endWeek;
  }

  public String getAuthCode()
  {
    return authCode;
  }

  public String getAssignmentStatusFullName()
  {
    String answer = assignmentStatus.toString();
    return answer;
  }

  public AssignmentStatus getAssignmentStatus()
  {
    return assignmentStatus;
  }

  public boolean payForTrip(String authcode)
  {
    boolean wasEventProcessed = false;
    
    AssignmentStatus aAssignmentStatus = assignmentStatus;
    switch (aAssignmentStatus)
    {
      case Assigned:
        // line 5 "../../../../../ClimbSafeStates.ump"
        setCode(authcode);
        setAssignmentStatus(AssignmentStatus.Paid);
        wasEventProcessed = true;
        break;
      case Paid:
        // line 20 "../../../../../ClimbSafeStates.ump"
        rejectPayment(authcode, "Trip has already been paid for");
        setAssignmentStatus(AssignmentStatus.Paid);
        wasEventProcessed = true;
        break;
      case Started:
        // line 37 "../../../../../ClimbSafeStates.ump"
        rejectPayment(authcode, "Trip has already been paid for");
        setAssignmentStatus(AssignmentStatus.Started);
        wasEventProcessed = true;
        break;
      case Cancelled:
        // line 49 "../../../../../ClimbSafeStates.ump"
        rejectPayment(authcode, "Cannot pay for a trip which has been cancelled");
        setAssignmentStatus(AssignmentStatus.Cancelled);
        wasEventProcessed = true;
        break;
      case Finished:
        // line 64 "../../../../../ClimbSafeStates.ump"
        rejectPayment(authcode, "Cannot pay for a trip which has finished");
        setAssignmentStatus(AssignmentStatus.Finished);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean cancel()
  {
    boolean wasEventProcessed = false;
    
    AssignmentStatus aAssignmentStatus = assignmentStatus;
    switch (aAssignmentStatus)
    {
      case Assigned:
        // line 6 "../../../../../ClimbSafeStates.ump"
        refund(0);
        setAssignmentStatus(AssignmentStatus.Cancelled);
        wasEventProcessed = true;
        break;
      case Paid:
        // line 24 "../../../../../ClimbSafeStates.ump"
        refund(50);
        setAssignmentStatus(AssignmentStatus.Cancelled);
        wasEventProcessed = true;
        break;
      case Started:
        // line 44 "../../../../../ClimbSafeStates.ump"
        refund(10);
        setAssignmentStatus(AssignmentStatus.Cancelled);
        wasEventProcessed = true;
        break;
      case Finished:
        // line 60 "../../../../../ClimbSafeStates.ump"
        rejectCancelTrip(0, "Cannot cancel a trip which has finished");
        setAssignmentStatus(AssignmentStatus.Cancelled);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean startTrip()
  {
    boolean wasEventProcessed = false;
    
    AssignmentStatus aAssignmentStatus = assignmentStatus;
    switch (aAssignmentStatus)
    {
      case Assigned:
        // line 9 "../../../../../ClimbSafeStates.ump"
        banMember(this);
        setAssignmentStatus(AssignmentStatus.Assigned);
        wasEventProcessed = true;
        break;
      case Paid:
        // line 31 "../../../../../ClimbSafeStates.ump"
        doStartTrip();
        setAssignmentStatus(AssignmentStatus.Started);
        wasEventProcessed = true;
        break;
      case Finished:
        // line 68 "../../../../../ClimbSafeStates.ump"
        rejectStartTrip("Cannot start a trip which has finished");
        setAssignmentStatus(AssignmentStatus.Finished);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean endTrip()
  {
    boolean wasEventProcessed = false;
    
    AssignmentStatus aAssignmentStatus = assignmentStatus;
    switch (aAssignmentStatus)
    {
      case Assigned:
        // line 13 "../../../../../ClimbSafeStates.ump"
        rejectEndTrip("Cannot finish a trip which has not started");
        setAssignmentStatus(AssignmentStatus.Assigned);
        wasEventProcessed = true;
        break;
      case Paid:
        // line 27 "../../../../../ClimbSafeStates.ump"
        rejectEndTrip("Cannot finish a trip which has not started");
        setAssignmentStatus(AssignmentStatus.Paid);
        wasEventProcessed = true;
        break;
      case Started:
        // line 41 "../../../../../ClimbSafeStates.ump"
        doEndTrip();
        setAssignmentStatus(AssignmentStatus.Finished);
        wasEventProcessed = true;
        break;
      case Cancelled:
        // line 53 "../../../../../ClimbSafeStates.ump"
        rejectEndTrip("Cannot finish a trip which has been cancelled");
        setAssignmentStatus(AssignmentStatus.Cancelled);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setAssignmentStatus(AssignmentStatus aAssignmentStatus)
  {
    assignmentStatus = aAssignmentStatus;
  }
  /* Code from template association_GetOne */
  public Member getMember()
  {
    return member;
  }
  /* Code from template association_GetOne */
  public Guide getGuide()
  {
    return guide;
  }

  public boolean hasGuide()
  {
    boolean has = guide != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Hotel getHotel()
  {
    return hotel;
  }

  public boolean hasHotel()
  {
    boolean has = hotel != null;
    return has;
  }
  /* Code from template association_GetOne */
  public ClimbSafe getClimbSafe()
  {
    return climbSafe;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setMember(Member aNewMember)
  {
    boolean wasSet = false;
    if (aNewMember == null)
    {
      //Unable to setMember to null, as assignment must always be associated to a member
      return wasSet;
    }
    
    Assignment existingAssignment = aNewMember.getAssignment();
    if (existingAssignment != null && !equals(existingAssignment))
    {
      //Unable to setMember, the current member already has a assignment, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Member anOldMember = member;
    member = aNewMember;
    member.setAssignment(this);

    if (anOldMember != null)
    {
      anOldMember.setAssignment(null);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setGuide(Guide aGuide)
  {
    boolean wasSet = false;
    Guide existingGuide = guide;
    guide = aGuide;
    if (existingGuide != null && !existingGuide.equals(aGuide))
    {
      existingGuide.removeAssignment(this);
    }
    if (aGuide != null)
    {
      aGuide.addAssignment(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setHotel(Hotel aHotel)
  {
    boolean wasSet = false;
    Hotel existingHotel = hotel;
    hotel = aHotel;
    if (existingHotel != null && !existingHotel.equals(aHotel))
    {
      existingHotel.removeAssignment(this);
    }
    if (aHotel != null)
    {
      aHotel.addAssignment(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setClimbSafe(ClimbSafe aClimbSafe)
  {
    boolean wasSet = false;
    if (aClimbSafe == null)
    {
      return wasSet;
    }

    ClimbSafe existingClimbSafe = climbSafe;
    climbSafe = aClimbSafe;
    if (existingClimbSafe != null && !existingClimbSafe.equals(aClimbSafe))
    {
      existingClimbSafe.removeAssignment(this);
    }
    climbSafe.addAssignment(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Member existingMember = member;
    member = null;
    if (existingMember != null)
    {
      existingMember.setAssignment(null);
    }
    if (guide != null)
    {
      Guide placeholderGuide = guide;
      this.guide = null;
      placeholderGuide.removeAssignment(this);
    }
    if (hotel != null)
    {
      Hotel placeholderHotel = hotel;
      this.hotel = null;
      placeholderHotel.removeAssignment(this);
    }
    ClimbSafe placeholderClimbSafe = climbSafe;
    this.climbSafe = null;
    if(placeholderClimbSafe != null)
    {
      placeholderClimbSafe.removeAssignment(this);
    }
  }

  // line 76 "../../../../../ClimbSafeStates.ump"
   private void setCode(String authcode) throws RuntimeException{
    if(authcode==null || authcode.length()==0){
  		throw new RuntimeException("Invalid authorization code");
  	}
  	this.setAuthCode(authcode);
  }

  // line 83 "../../../../../ClimbSafeStates.ump"
   private void rejectPayment(String authCode, String message){
    throw new RuntimeException(message);
  }

  // line 87 "../../../../../ClimbSafeStates.ump"
   private void rejectEndTrip(String message){
    throw new RuntimeException(message);
  }

  // line 91 "../../../../../ClimbSafeStates.ump"
   private void rejectStartTrip(String message){
    throw new RuntimeException(message);
  }

  // line 95 "../../../../../ClimbSafeStates.ump"
   private void rejectCancelTrip(int percentDiscount, String message){
    refund(percentDiscount);
    throw new RuntimeException(message);
  }

  // line 99 "../../../../../ClimbSafeStates.ump"
   private void doStartTrip(){
    
  }

  // line 101 "../../../../../ClimbSafeStates.ump"
   private void doEndTrip(){
    
  }

  // line 104 "../../../../../ClimbSafeStates.ump"
   private void banMember(Assignment a){
    this.getMember().banMember();
  }

  // line 109 "../../../../../ClimbSafeStates.ump"
   private void refund(int percentDiscount){
    this.getMember().setRefundPercent(percentDiscount);
  }


  public String toString()
  {
    return super.toString() + "["+
            "startWeek" + ":" + getStartWeek()+ "," +
            "endWeek" + ":" + getEndWeek()+ "," +
            "authCode" + ":" + getAuthCode()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "member = "+(getMember()!=null?Integer.toHexString(System.identityHashCode(getMember())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "guide = "+(getGuide()!=null?Integer.toHexString(System.identityHashCode(getGuide())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "hotel = "+(getHotel()!=null?Integer.toHexString(System.identityHashCode(getHotel())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "climbSafe = "+(getClimbSafe()!=null?Integer.toHexString(System.identityHashCode(getClimbSafe())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 46 "../../../../../ClimbSafePersistence.ump"
  private static final long serialVersionUID = 8728735851617678210L ;

  
}