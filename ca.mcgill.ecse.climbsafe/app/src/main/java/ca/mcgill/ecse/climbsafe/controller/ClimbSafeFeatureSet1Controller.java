package ca.mcgill.ecse.climbsafe.controller;

import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Guide;
import ca.mcgill.ecse.climbsafe.model.Hotel;
import ca.mcgill.ecse.climbsafe.model.Member;
import ca.mcgill.ecse.climbsafe.model.User;
import ca.mcgill.ecse.climbsafe.persistance.ClimbSafePersistence;

import java.sql.Date;

public class ClimbSafeFeatureSet1Controller {
	/**
	 * This method initiates the Climb Safe application. It will ensure that the
	 * application exists and the global fields are set properly
	 * 
	 * @param startDate           the starting date of the season
	 * @param nrWeeks             the number of weeks for the season
	 * @param priceOfGuidePerWeek the price of a guide per week
	 * @throws InvalidInputException if any of the inputs are not valid
	 * @author Ryan Reszetnik
	 */
	public static void setup(Date startDate, int nrWeeks, int priceOfGuidePerWeek) throws InvalidInputException {
		if (nrWeeks <= 0) {
			throw new InvalidInputException("The number of climbing weeks must be greater than or equal to zero");
		}
		if (priceOfGuidePerWeek <= 0) {
			throw new InvalidInputException("The price of guide per week must be greater than or equal to zero");
		}
		if (startDate == null) {
			throw new InvalidInputException("Start date can not be null");
		}

		ClimbSafe climbSafe = ClimbSafeApplication.getClimbSafe();
		climbSafe.setStartDate(startDate);
		climbSafe.setNrWeeks(nrWeeks);
		climbSafe.setPriceOfGuidePerWeek(priceOfGuidePerWeek);
		ClimbSafePersistence.save(climbSafe);
	}

	/**
	 * Will delete the member with that specific email provided if it exists
	 * 
	 * @param email the email address of the member to delete
	 * @author Ryan Reszetnik
	 */
	public static void deleteMember(String email) throws InvalidInputException{
		ClimbSafe climbSafe = ClimbSafeApplication.getClimbSafe();
		User u = User.getWithEmail(email);
		if (u != null && u instanceof Member) {
			u.delete();
		}
		ClimbSafePersistence.save(climbSafe);
	}
	
	public static void deleteGuideCheck(String email) throws InvalidInputException { 
		var error = ""; 
		User u = User.getWithEmail(email);
		if (!(u instanceof Guide) ) { 
			error = "This guide does not exist in the system";
		}
		
		if (!error.isEmpty()) {

			throw new InvalidInputException(error.trim());

		}
		
	}

	/**
	 * Will delete the guide with that specific email provided if it exists
	 * 
	 * @param email the email address of the guide to delete
	 * @author Ryan Reszetnik
	 */
	public static void deleteGuide(String email) {
		ClimbSafe climbSafe = ClimbSafeApplication.getClimbSafe();
		User u = User.getWithEmail(email);
		if (u != null && u instanceof Guide) {
			u.delete();
		}
		ClimbSafePersistence.save(climbSafe);
	}

	/**
	 * Will delete the hotel with that specific name provide if it exists
	 * 
	 * @param name the name of the hotel to delete
	 * @author Zihan Zhang
	 */
	public static void deleteHotel(String name) {
		ClimbSafe climbSafe = ClimbSafeApplication.getClimbSafe();
		Hotel theHotel = Hotel.getWithName(name);
		if (theHotel != null) {
			theHotel.delete();
		}
		ClimbSafePersistence.save(climbSafe);
	}

}
