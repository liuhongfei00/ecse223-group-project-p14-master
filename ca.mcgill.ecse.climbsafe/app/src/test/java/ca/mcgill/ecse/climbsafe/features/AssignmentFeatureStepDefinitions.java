package ca.mcgill.ecse.climbsafe.features;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;

import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.controller.AssignmentController;
import ca.mcgill.ecse.climbsafe.controller.InvalidInputException;
import ca.mcgill.ecse.climbsafe.model.Assignment;
import ca.mcgill.ecse.climbsafe.model.BookableItem;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Guide;
import ca.mcgill.ecse.climbsafe.model.Member;
import ca.mcgill.ecse.climbsafe.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class AssignmentFeatureStepDefinitions {
	private ClimbSafe climbSafe;
	private String error;

	/**
	 * The step definition that instantiates the ClimbSafe system
	 * @param dataTable the data table containing the attributes required to create a climbSafe system
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 */
	@Given("the following ClimbSafe system exists:")
	public void the_following_climb_safe_system_exists(io.cucumber.datatable.DataTable dataTable) {

		climbSafe = ClimbSafeApplication.getClimbSafe();

		List<Map<String, String>> rows = dataTable.asMaps();
		for (Map<String, String> row : rows) { // set the attributes of the climbSafe system
			climbSafe.setStartDate(Date.valueOf(row.get("startDate")));
			climbSafe.setNrWeeks(Integer.parseInt(row.get("nrWeeks")));
			climbSafe.setPriceOfGuidePerWeek(Integer.parseInt(row.get("priceOfGuidePerWeek")));
		}

	}
	/**
	 * The step definition that instantiates the equipment pieces in the ClimbSafe system
	 * @param dataTable the data table containing the different pieces of equipment and their attributes to be added
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 */
	@Given("the following pieces of equipment exist in the system:")
	public void the_following_pieces_of_equipment_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {

		List<Map<String, String>> rows = dataTable.asMaps();
		for (Map<String, String> cur : rows) {
			climbSafe.addEquipment(cur.get("name"), Integer.parseInt(cur.get("weight")),
					Integer.parseInt(cur.get("pricePerWeek")));
		}

	}

	/**
	 * The step definition that instantiates the equipment bundles in the ClimbSafe system
	 * @param dataTable the data table containing the different equipment bundles and discounts to be added
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 */
	@Given("the following equipment bundles exist in the system:")
	public void the_following_equipment_bundles_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {

		List<Map<String, String>> equipmentBundles = dataTable.asMaps();
		for (Map<String, String> e : equipmentBundles) {
			climbSafe.addBundle(e.get("name"), Integer.parseInt(e.get("discount")));

		}
	}

	/**
	 * The step definition that instantiates the guides in the ClimbSafe system
	 * @param dataTable the data table containing the guides to be added and their attributes
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 */
	@Given("the following guides exist in the system:")
	public void the_following_guides_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
		List<Map<String, String>> guideList = dataTable.asMaps(String.class, String.class);
		for (Map<String, String> g : guideList) {
			climbSafe.addGuide(g.get("email"), g.get("password"), g.get("name"), g.get("emergencyContact"));
		}
	}

	/**
	 * The step definition that instantiates the members in the ClimbSafe system
	 * @param dataTable the data table containing the members to be added and their attributes
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 */
	@Given("the following members exist in the system:")
	public void the_following_members_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
		List<Map<String, String>> rows = dataTable.asMaps();

		for (Map<String, String> columns : rows) {

			Member m = climbSafe.addMember(columns.get("email"), columns.get("password"), columns.get("name"),
					columns.get("emergencyContact"), Integer.parseInt(columns.get("nrWeeks")),
					Boolean.parseBoolean(columns.get("guideRequired")),
					Boolean.parseBoolean(columns.get("hotelRequired")));
			String[] itemStrings = columns.get("bookedItems").split(",");
			String[] itemQuantity = columns.get("bookedItemQuantities").split(",");
			for (int i = 0; i < itemStrings.length; i++) {
				m.addBookedItem(Integer.parseInt(itemQuantity[i]), climbSafe, BookableItem.getWithName(itemStrings[i]));
			}
		}
	}

	/**
	 * The step definition for when members are assigned
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 */
	@When("the administrator attempts to initiate the assignment process")
	public void the_administrator_attempts_to_initiate_the_assignment_process() {
		try {
		AssignmentController.assignMembers();
		} catch(RuntimeException e) {
			error = e.getMessage();
		}
	}

	/**
	 * The step definition that verifies that the correct assignments exist in the system
	 * @param dataTable the data table containing the assignments that should exist
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 */
	@Then("the following assignments shall exist in the system:")
	public void the_following_assignments_shall_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
		List<Map<String, String>> rows = dataTable.asMaps();

		for (Assignment a1: climbSafe.getAssignments()) {
			String guideName = "null";
			if (a1.getGuide() != null) {
				guideName = a1.getGuide().getName();
			}
		}
		for (Map<String, String> columns : rows) {
			boolean exists = false;
			Member m = (Member) User.getWithEmail(columns.get("memberEmail"));
			Guide g = (Guide) User.getWithEmail(columns.get("guideEmail"));
			int start = Integer.parseInt(columns.get("startWeek"));
			int end = Integer.parseInt(columns.get("endWeek"));
			Assignment a = m.getAssignment();
			if (a != null && a.getStartWeek() == start && a.getEndWeek() == end) {
				exists = true;
				if (g != null) {
					if (!a.getGuide().equals(g)) {
						exists = false;
					}
				Assertions.assertTrue(exists);
				}
			
			}
			Assertions.assertTrue(exists);
		}
		
	}

	/**
	 * The step definition that verifies that the assignment for a specific member has the correct assignmentStatus
	 * @param email the email of the member whose assignmentStatus we are verifying
	 * 		  status the expected status of the member's assignment
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 */
	@Then("the assignment for {string} shall be marked as {string}")
	public void the_assignment_for_shall_be_marked_as(String email, String status) {
		Member m = (Member) User.getWithEmail(email);
		Assertions.assertEquals(status, m.getAssignment().getAssignmentStatus().toString());
	}

	/**
	 * The step definition that verifies that the correct number of assignments are in the system
	 * @param string the expected number of assignments in the system
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 */
	@Then("the number of assignments in the system shall be {string}")
	public void the_number_of_assignments_in_the_system_shall_be(String string) {
		Assertions.assertEquals(Integer.parseInt(string), climbSafe.numberOfAssignments());
		
	}

	/**
	 * The step definition that verifies that the correct error is raised
	 * @param string the expected error raised by the system
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 */
	@Then("the system shall raise the error {string}")
	public void the_system_shall_raise_the_error(String string) {
		Assertions.assertEquals(string, error);
	}

	/**
	 * The step definition that initiates the correct assignments in the system
	 * @param dataTable the data table containing the information for all the assignments to be added
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 */
	@Given("the following assignments exist in the system:")
	public void the_following_assignments_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
		List<Map<String, String>> rows = dataTable.asMaps();

		for (Map<String, String> columns : rows) {
			Member m = (Member) User.getWithEmail(columns.get("memberEmail"));
			Guide g = (Guide) User.getWithEmail(columns.get("guideEmail"));
			Assignment a = climbSafe.addAssignment(Integer.parseInt(columns.get("startWeek")), Integer.parseInt(columns.get("endWeek")), m);
			if (g != null) {
				g.addAssignment(a);
			}
		}
		
	}

	/**
	 * The step definition for when a specific member's trip attempts to be paid for
	 * @param email the email of the member whose trip is being paid for
	 * 		  code the authorization code for the payment 
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 */
	@When("the administrator attempts to confirm payment for {string} using authorization code {string}")
	public void the_administrator_attempts_to_confirm_payment_for_using_authorization_code(String email,
			String code) {
		try {
			AssignmentController.payForMemberTrip(email, code);
		} catch (Exception e) {
			error =e.getMessage();
		}
	}
	
	/**
	 * The step definition verifying that the assignment has recorded the authorization code
	 * @param email the email of the member whose trip is being paid for
	 * 		  code the authorization code for the payment 
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 */
	@Then("the assignment for {string} shall record the authorization code {string}")
	public void the_assignment_for_shall_record_the_authorization_code(String email, String code) {
		Member m = (Member) User.getWithEmail(email);
		Assert.assertEquals(code, m.getAssignment().getAuthCode());
	}

	/**
	 * The step definition verifying that the member with the given email doesn't exist
	 * @param email an email that does not belong to any member
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 */
	@Then("the member account with the email {string} does not exist")
	public void the_member_account_with_the_email_does_not_exist(String email) {
		Member u = (Member) User.getWithEmail(email);
		Assert.assertTrue(u == null || !(u instanceof Member));
	}

	/**
	 * The step definition verifying that the correct number of members exist in the system
	 * @param string the expected number of members in the system
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 */
	@Then("there are {string} members in the system")
	public void there_are_members_in_the_system(String string) {
		Assert.assertEquals(Integer.parseInt(string), climbSafe.numberOfMembers());
	}

	/**
	 * The step definition verifying that the correct error is raised
	 * @param string the expected error returned
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 */
	@Then("the error {string} shall be raised")
	public void the_error_shall_be_raised(String string) {
		Assertions.assertEquals(string, error);
	}

	/**
	 * The step definition for when a trip for a specific member is cancelled
	 * @param email the email of the member whose trip is being cancelled
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 */
	@When("the administrator attempts to cancel the trip for {string}")
	public void the_administrator_attempts_to_cancel_the_trip_for(String email) {
		try {
			AssignmentController.cancelMemberTrip(email);
		} catch (Exception e) {
			error=e.getMessage();
		}
	}

	/**
	 * The step definition that sets a member's assignment's assignmentStatus to Paid
	 * @param email the email of the member whose assignment will be set as Paid
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 */
	@Given("the member with {string} has paid for their trip")
	public void the_member_with_has_paid_for_their_trip(String email) {
		Member m = (Member) User.getWithEmail(email);
		m.getAssignment().payForTrip("Some Code");
	}

	/**
	 * The step definition that verifies that the member received the right percentage refund
	 * @param email the email of the member who is being refunded
	 * 		  refundPercent the percent refund the member is expected to have 
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 */
	@Then("the member with email address {string} shall receive a refund of {string} percent")
	public void the_member_with_email_address_shall_receive_a_refund_of_percent(String email, String refundPercent) {
		Member m = (Member) User.getWithEmail(email);
		Assertions.assertEquals(Integer.parseInt(refundPercent), m.getRefundPercent());
	}

	/**
	 * The step definition that sets a member's assignment's assignmentStatus to Started
	 * @param email the email of the member whose assignment will be set as Started
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 */
	@Given("the member with {string} has started their trip")
	public void the_member_with_has_started_their_trip(String email) {
		Member m = (Member) User.getWithEmail(email);
		Assignment a = m.getAssignment();
		a.payForTrip("Some code");
		a.startTrip();
	}

	/**
	 * The step definition for when a trip for a specific member is finished
	 * @param email the email of the member whose trip is being finished
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 */
	@When("the administrator attempts to finish the trip for the member with email {string}")
	public void the_administrator_attempts_to_finish_the_trip_for_the_member_with_email(String email) {
		try {
			AssignmentController.finishMemberTrip(email);
		} catch (Exception e) {
			error = e.getMessage();
		}
	}

	/**
	 * The step definition that sets a member's memberStatus to Banned
	 * @param email the email of the member who will be set as Banned
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 */
	@Given("the member with {string} is banned")
	public void the_member_with_is_banned(String string) {
		Member m = (Member) User.getWithEmail(string);
		m.banMember();
	}

	/**
	 * The step definition that verifies a member's status
	 * @param email the email of the member whose status is being verified
	 * 		  status the expected memberStatus of the given member
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 */
	@Then("the member with email {string} shall be {string}")
	public void the_member_with_email_shall_be(String email, String status) {
		Member m = (Member) User.getWithEmail(email);
		Assert.assertEquals(status, m.getMemberStatus().toString());
	}

	/**
	 * The step definition for when the trips in a specific week are started
	 * @param weekNr the week for which all trips are being started
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 */
	@When("the administrator attempts to start the trips for week {string}")
	public void the_administrator_attempts_to_start_the_trips_for_week(String weekNr) {
		try {
			AssignmentController.startTripsForWeek(Integer.parseInt(weekNr));
		} catch (NumberFormatException e) {
			error = e.getMessage();
		} catch (InvalidInputException e) {
			error = e.getMessage();
		} catch (Exception e) { 
			error = e.getMessage();
		}
		
	}

	/**
	 * The step definition that sets a member's assignment's assignmentStatus to Cancelled
	 * @param email the email of the member whose assignment will be set as Cancelled
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 */
	@Given("the member with {string} has cancelled their trip")
	public void the_member_with_has_cancelled_their_trip(String email) {
		Member m = (Member) User.getWithEmail(email);
		Assignment a = m.getAssignment();
		a.cancel();
	}
	
	/**
	 * The step definition that sets a member's assignment's assignmentStatus to Finished
	 * @param email the email of the member whose assignment will be set as Finished
	 * @author Hongfei Liu, Matthew MacDonald, Sabrina Mansour, Sehr Moosabhoy, Ryan Reszetnik, Zihan Zhang
	 */
	@Given("the member with {string} has finished their trip")
	public void the_member_with_has_finished_their_trip(String email) {
		Member m = (Member) User.getWithEmail(email);
		Assignment a = m.getAssignment();
		a.payForTrip("Some Code");
		a.startTrip();
		a.endTrip();
	}
}
