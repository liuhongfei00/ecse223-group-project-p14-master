package ca.mcgill.ecse.climbsafe.controller;

import java.util.List;

import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.model.Assignment;
import ca.mcgill.ecse.climbsafe.model.Assignment.AssignmentStatus;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Guide;
import ca.mcgill.ecse.climbsafe.model.Member;
import ca.mcgill.ecse.climbsafe.model.Member.MemberStatus;
import ca.mcgill.ecse.climbsafe.model.User;

/**
 * This class contains controller methods for Assignments.
 * 
 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan
 *         Reszetnik, Zihan Zhang
 *
 */
public class AssignmentController {

	/**
	 * Assigns members to an assignment.
	 * 
	 * @throws RuntimeException
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan
	 *         Reszetnik, Zihan Zhang
	 */
	public static void assignMembers() throws RuntimeException {
		ClimbSafe climbSafe = ClimbSafeApplication.getClimbSafe();
		List<Guide> guides = climbSafe.getGuides();
		List<Member> members = climbSafe.getMembers();
		int numWeeks = climbSafe.getNrWeeks();
		for (Guide g : guides) {
			for (Member m : members) {
				if (!m.hasAssignment()) {// check assignment state machine instead
					if (m.isGuideRequired()) {
						List<Assignment> assignments = g.getAssignments();
						int numWeeksBooked = 0;
						for (Assignment a : assignments) {
							if (a.getEndWeek() > numWeeksBooked) {
								numWeeksBooked = a.getEndWeek();
							}
						}
						if (numWeeksBooked + m.getNrWeeks() <= numWeeks) {
							Assignment a = new Assignment(numWeeksBooked + 1, numWeeksBooked + m.getNrWeeks(), m,
									climbSafe);
							g.addAssignment(a);
							// set Assignment state machine
							continue;
						} // else will come back later to assign member to next guide
					} else {
						// set Assignment state machine

						new Assignment(1, m.getNrWeeks(), m, climbSafe);
					}
				}
			}
		}
		for (Member me : climbSafe.getMembers()) {
			if (me.getAssignment() == null)
				throw new RuntimeException("Assignments could not be completed for all members");
		}

	}

	/**
	 * Pays for the member trip using the member's email and authorization code.
	 * 
	 * @param email    the email belonging to the member who is is paying for their
	 *                 trip
	 * @param authCode the authorization code for the member's payment
	 * @throws InvalidInputException
	 * @throws RuntimeException
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan
	 *         Reszetnik, Zihan Zhang
	 */
	public static void payForMemberTrip(String email, String authCode) throws InvalidInputException, RuntimeException {
		User u = User.getWithEmail(email);
		if (u == null || !(u instanceof Member)) {
			throw new InvalidInputException("Member with email address " + email + " does not exist");
		}
		Member m = (Member) u;
		if (m.getMemberStatus().equals(MemberStatus.Banned)) {
			throw new RuntimeException("Cannot pay for the trip due to a ban");
		}
		m.getAssignment().payForTrip(authCode);

	}

	/**
	 * Starts the trips for all members leaving on the week number given.
	 * 
	 * @param weekNr the week for which all trips are being started
	 * @throws InvalidInputException
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan
	 *         Reszetnik, Zihan Zhang
	 */

	public static void startTripsForWeek(int weekNr) throws InvalidInputException {
		String error = "";
		ClimbSafe climbSafe = ClimbSafeApplication.getClimbSafe();
		if (weekNr <= 0 || weekNr > climbSafe.getNrWeeks()) {
			throw new InvalidInputException("Week not in season range");
		}
		for (Member m : climbSafe.getMembers()) {
			if (m.getAssignment() != null && m.getAssignment().getStartWeek() == weekNr) {
				// set trip to started
				if (m.getMemberStatus().equals(MemberStatus.Banned)) {
					error = "Cannot start the trip due to a ban";
				} else if (m.getAssignment().getAssignmentStatus().equals(AssignmentStatus.Cancelled)) {
					error = "Cannot start a trip which has been cancelled";
				} else {
					m.getAssignment().startTrip();
				}

			}
		}
		if (error != "") {
			throw new RuntimeException(error);
		}
	}

	/**
	 * Finishes a member's trip.
	 * 
	 * @param email the email for the member whose trip is being finished
	 * @throws InvalidInputException
	 * @throws RuntimeException
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan
	 *         Reszetnik, Zihan Zhang
	 */
	public static void finishMemberTrip(String email) throws InvalidInputException, RuntimeException {
		User u = User.getWithEmail(email);
		if (u == null || !(u instanceof Member)) {
			throw new InvalidInputException("Member with email address " + email + " does not exist");
		}
		Member m = (Member) u;
		if (m.getMemberStatus().equals(MemberStatus.Banned)) {
			throw new RuntimeException("Cannot finish the trip due to a ban");
		}
		m.getAssignment().endTrip();
	}

	/**
	 * Cancels a member's trip.
	 * 
	 * @param email the email for the member whose trip is being cancelled
	 * @throws InvalidInputException
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan
	 *         Reszetnik, Zihan Zhang
	 */
	public static void cancelMemberTrip(String email) throws InvalidInputException {
		User u = User.getWithEmail(email);
		if (u == null || !(u instanceof Member)) {
			throw new InvalidInputException("Member with email address " + email + " does not exist");
		}
		Member m = (Member) u;
		if (m.getMemberStatus().equals(MemberStatus.Banned)) {
			throw new RuntimeException("Cannot cancel the trip due to a ban");
		}
		// set status to cancelled
		m.getAssignment().cancel();

	}

	/**
	 * Returns status of the assignment to the view
	 * 
	 * @param email the email of the member's whose assignment status is being
	 *              returned
	 * @throws InvalidInputException
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan
	 *         Reszetnik, Zihan Zhang
	 */

	public static String getAssignmentStatus(String email) {

		User u = User.getWithEmail(email);
		Member m = (Member) u;
		if (m.getMemberStatus().equals(MemberStatus.Banned)) {
			return "Banned";
		} else {
			return (m.getAssignment().getAssignmentStatus().toString());
		}
	}

}
