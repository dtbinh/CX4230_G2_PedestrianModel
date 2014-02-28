package edu.gatech.cx4230.projectone.backend.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeHelper {

	/**
	 * 
	 * @return "MM/DD/YYYY HH:mm:SS"
	 */
	public static String getDateTime() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat(" MM-dd-yyyy HH:mm:ss");

		return dateFormat.format(date);


	}

}
