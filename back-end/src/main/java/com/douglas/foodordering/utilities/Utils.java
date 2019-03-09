package com.douglas.foodordering.utilities;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	public static boolean isExpiredRecord(Timestamp ts) {
		Timestamp current = new Timestamp(System.currentTimeMillis());
		return !ts.after(current);
	}
	
	public static Date parseStandardDate(String dateString) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.STANDARD_DATE_FORMAT);
		return dateFormat.parse(dateString);
	}
	
	public static Date parseDateWithFormat(String dateString, String format) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.parse(dateString);
	}
	
	public static String formatStandardDate(Date date) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.STANDARD_DATE_FORMAT);
		return dateFormat.format(date);
	}
	
	public static String formatDateWithFormat(Date date, String format) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	
	public static java.sql.Date parseStandardSQLDate(String dateString) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.STANDARD_DATE_FORMAT);
		return new java.sql.Date(dateFormat.parse(dateString).getTime());
	}
}
