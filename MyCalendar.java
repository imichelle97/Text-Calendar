/**Program Assignment #2: Hello Calendar
 * Author: Michelle Luong
 * Copyright (C) 2017 Michelle Luong. All Rights Reserved.
 * Version: 1.01 3/11/2017
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.io.*;

public class MyCalendar implements Serializable {
	
	ArrayList<Event> listOfEvent;
	GregorianCalendar selectedDate;
	GregorianCalendar todayDate;
	Boolean[]hasEvent;
	
	/**
	 * Constructs MyCalendar
	 */
	public MyCalendar() {
		listOfEvent = new ArrayList<Event>();
		selectedDate = new GregorianCalendar();
		todayDate = new GregorianCalendar();
		hasEvent = new Boolean[32];
		for(int i = 0; i < 32; i++) {
			hasEvent[i] = false;
		}
		//hasEvent[selectedDate.get(Calendar.DAY_OF_MONTH)] = true;
	}
	
	/**
	 * Method to add event to an Event List
	 * @param title Title of event
	 * @param date Date of event (MM/DD/YYYY)
	 * @param startTime Start time of event (HH:MM)
	 * @param endTime End time of event (HH:MM)
	 * @param hasEndTime True is event has an end time, false if event does not
	 */
	public void addEvent(String title, String date, String startTime, String endTime, Boolean hasEndTime) {
		String[]date_a = date.split("/");
		int month = Integer.parseInt(date_a[0]);
		int day = Integer.parseInt(date_a[1]);
		int year = Integer.parseInt(date_a[2]);
		
		String[]startTime_a = startTime.split(":");
		int sHour = Integer.parseInt(startTime_a[0]);
		int sMin = Integer.parseInt(startTime_a[1]);
		
		if(hasEndTime) {
			String[]endTime_a = endTime.split(":");
			int eHour = Integer.parseInt(endTime_a[0]);
			int eMin = Integer.parseInt(endTime_a[1]);
			
			listOfEvent.add(new Event(title, month, day, year, sHour, sMin, eHour, eMin, true));
		}
		else {
			listOfEvent.add(new Event(title, month, day, year, sHour, sMin, 0, 0, false));
		}
		
		Comparator<Event>comp = new
				Comparator<Event>()
				{
					public int compare(Event event1, Event event2) {
						if(event1.getStartTime().after(event2.getStartTime()))
							return 1;
						else if(event2.getStartTime().after(event1.getStartTime()))
							return -1;
						else
							return 0;
					}
			
				};
				
				Collections.sort(listOfEvent, comp);
	}
	
	/**
	 * Parses the String date into Month, Day, and Year 
	 * @param date Event's date
	 */
	public void setSelectedDate(String date) {
		String[]date_a = date.split("/");
		int month = Integer.parseInt(date_a[0]);
		int day = Integer.parseInt(date_a[1]);
		int year = Integer.parseInt(date_a[2]);
		
		selectedDate.set(Calendar.YEAR, year);
		selectedDate.set(Calendar.MONTH, month - 1);
		selectedDate.set(Calendar.DAY_OF_MONTH, day);
		
	}
	
	/**
	 * Method to check if there is an event on specified date
	 * @param year Event's date - YEAR
	 * @param month Event's date - MONTH
	 * @param day Event's date - DAY
	 * @return
	 */
	public boolean has_Event(int year, int month, int day) {
		for(Event e : listOfEvent) {
			if((e.getYear() == year) &&
				(e.getMonth() == month) &&
				(e.getDay() == day) ) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Fund the index of event in the listOfEvent array of specified date
	 * @param year Event's date - YEAR
	 * @param month Event's date - MONTH
	 * @param day Event's date - DAY
	 * @return
	 */
	public int eventIndex(int year, int month, int day) {
		for(int i = 0; i < listOfEvent.size(); i++) {
			//System.out.println("Year " + listOfEvent.get(i).getYear());
			//System.out.println("Month " + listOfEvent.get(i).getMonth());
			//System.out.println("Day " + listOfEvent.get(i).getDay());
			if((listOfEvent.get(i).getYear() == year) &&
				((listOfEvent.get(i).getMonth()) == month) &&
				(listOfEvent.get(i).getDay() == day)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Method to delete event of specified date
	 * @param date Date of event that wants to be deleted
	 */
	public void deleteEvent_DAY(String date) {
		String[]data_a = date.split("/");
		int month = Integer.parseInt(data_a[0]);
		int day = Integer.parseInt(data_a[1]);
		int year = Integer.parseInt(data_a[2]);
		
		int i;
		while((i = eventIndex(year, month, day)) >= 0 ) {
			listOfEvent.remove(i);
		}
	}
	
	/**
	 * Method to delete all events in the Event List
	 */
	public void deleteEvent_ALL() {
		listOfEvent.clear();
	}
	
	/**
	 * Method to display event of the day
	 * @param year Event's date - YEAR
	 * @param month Event's date - MONTH
	 * @param day Event's date - DAY
	 * @return
	 */
	//To display event of the day
	public ArrayList<Event> getEventOfDay(int month, int day, int year) {
		ArrayList<Event> event_r = new ArrayList<Event>();
		int i,j;
		if((i = eventIndex(year, month, day)) >= 0) {
			event_r.add(listOfEvent.get(i));
			
			for(j = i+1; j < listOfEvent.size(); j++) {
				if((listOfEvent.get(j).getYear() == year) &&
					(listOfEvent.get(j).getMonth() == month) &&
					(listOfEvent.get(j).getDay() == day)) {
					event_r.add(listOfEvent.get(j));
				}
			}
			return event_r;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Method to get the events of the month
	 * @param month Month of event
	 * @param year Year of event
	 */
	public void getEventOfMonth(int month, int year) {
		for(int i = 0; i < 32; i++) {
			hasEvent[i] = false;
		}
		if(todayDate.get(Calendar.MONTH) == selectedDate.get(Calendar.MONTH)) {
			hasEvent[todayDate.get(Calendar.DAY_OF_MONTH)] = true;
		}
		for(Event e : listOfEvent) {
			if((e.getMonth() == month) && (e.getYear() == year)) {
				hasEvent[e.getDay()] = true;
			}
		}
	}
	
	/*
	 * String representaion of the date and event information being displayed in day view
	 */
	public String displayDayView() {
		ArrayList<Event> e = getEventOfDay(selectedDate.get(Calendar.MONTH) + 1, selectedDate.get(Calendar.DAY_OF_MONTH), selectedDate.get(Calendar.YEAR));
		
		if(e == null) {
			SimpleDateFormat format1 = new SimpleDateFormat("EEEE, M, d, yyyy");
			return format1.format(selectedDate.getTime());
		}
		else if(e.size() == 1) {
			return e.get(0).toString();
		}
		else {
			String r = e.get(0).toString();
			for(int i = 1; i < e.size(); i++) {
				r = r + e.get(i).printTime();
			}
			return r;
		}
	}
	
	/*
	 * String representaion of the date and event information being displayed in month view
	 * If there is an event on a certain day, brackets will be placed around that event's day
	 * in the month view.
	 */
	public String displayMonthView() {
		SimpleDateFormat format1 = new SimpleDateFormat("MMMM yyyy");
		GregorianCalendar firstDay = new GregorianCalendar(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), 1);
		
		int startingDay = firstDay.get(Calendar.DAY_OF_WEEK) - 1;
		int numOfDayInMonth = selectedDate.getActualMaximum(Calendar.DAY_OF_MONTH);

		getEventOfMonth(selectedDate.get(Calendar.MONTH) + 1, selectedDate.get(Calendar.YEAR));
		String r = "";
		r += format1.format(selectedDate.getTime());
		r += "\n Su Mo Tu We Th Fr Sa\n";
		
		//Fill in space before first of month
		for(int i = 0; i < startingDay; i++) {
			r += "   "; 
		}
		
		for(int i = 1; i <= numOfDayInMonth; i++) {
			if(hasEvent[i]) {
				r += "[" + i + "]";
			}
			else if(i < 10) {
				r += "  " + i;
			}
			else {
				r += " " + i;
			}			
			
			if((i + startingDay)%7 == 0 || (i == numOfDayInMonth)) {
				r += "\n";
			}
		}
		return r;		
	}
	
	/*
	 * Goes to the next day
	 */
	public void goNextDay() {
		selectedDate.add(Calendar.DATE,1);
	}
	
	/*
	 * Goes to the previous day
	 */
	public void goPreviousDay() {
		selectedDate.add(Calendar.DATE, -1);
	}
	
	/*
	 * Goes to the next month
	 */
	public void goNextMonth() {
		selectedDate.add(Calendar.MONTH, 1);
	}
	
	/*
	 * Goes to the previous month
	 */
	public void goPreviousMonth() {
		selectedDate.add(Calendar.MONTH, -1);
	}
	
	/*
	 * String representation to display all the events created
	 */
	public String displayAllEvents() {
		String r = "";
		for(Event e : listOfEvent) {
			r += e.displayOneLine();
		}
		return r;
	}
	
}
	
