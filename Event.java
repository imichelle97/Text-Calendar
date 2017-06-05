/**Program Assignment #2: Hello Calendar
 * Author: Michelle Luong
 * Copyright (C) 2017 Michelle Luong. All Rights Reserved.
 * Version: 1.01 3/11/2017
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.io.*;

public class Event implements Serializable {
	String title;
	GregorianCalendar startTime;
	GregorianCalendar endTime;
	Boolean hasEndTime;
	
	SimpleDateFormat format1 = new SimpleDateFormat("EEEE, M d, yyyy");
	SimpleDateFormat format2 = new SimpleDateFormat("h:m");
	
	/**
	 * Constructs Event with the following parameters:
	 * @param title Title of event
	 * @param month Month of event
	 * @param day Day of event
	 * @param year Year of event
	 * @param startHour Starting hour
	 * @param startMin Starting minute
	 * @param endHour Ending hour
	 * @param endMin Ending minute
	 * @param hasEnd True is event has an end time, false if event does not
	 */
	public Event(String title, int month, int day, int year, int startHour, int startMin, int endHour, int endMin, boolean hasEnd) {
		this.title = title;
		startTime = new GregorianCalendar(year, month - 1, day, startHour, startMin, 0);
		endTime = new GregorianCalendar(year, month - 1, day, endHour, endMin, 0);
		hasEndTime = hasEnd;
	}
	
	/**
	 * Get the year
	 * @return Calendar year
	 */
	public int getYear() {
		return startTime.get(Calendar.YEAR);
	}
	
	/**
	 * Get the month
	 * @return Calendar month 
	 */
	public int getMonth() {
		return startTime.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * Get the day
	 * @return Calendar day
	 */
	public int getDay() {
		return startTime.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * Get the start time of event
	 * @return startTime
	 */
	public GregorianCalendar getStartTime() {
		return startTime;
	}
	
	/**
	 * String representation of the time without the date of event
	 * @return Time with no date 
	 */
	//NO DATE
	public String printTime() {
		
		if(hasEndTime) 
			return title + " " + format2.format(startTime.getTime()) + " " + format2.format(endTime.getTime()) + "\n";
		else
			return title + " " + format2.format(startTime.getTime()) + "\n";		
	}
	
	/**
	 * String representation of the event and time information on one line
	 * @return Event title, date, and time in one line
	 */
	public String displayOneLine() {
		if(hasEndTime){
			return format1.format(startTime.getTime()) + " " 
					+ format2.format(startTime.getTime()) + " "
					+ format2.format(endTime.getTime()) + " "
					+ title + "\n";
		}
		else {
			return format1.format(startTime.getTime()) + " " 
					+ format2.format(startTime.getTime()) + " "
					+ title + "\n";
		}
	}
	
	/**
	 * String representation of event time
	 */
	public String toString() {
		return format1.format(startTime.getTime()) + "\n" 
				//+ title + " "
				+ printTime();
	}
	
}
