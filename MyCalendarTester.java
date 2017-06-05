/**Program Assignment #2: Hello Calendar
 * Author: Michelle Luong
 * Copyright (C) 2017 Michelle Luong. All Rights Reserved.
 * Version: 1.01 3/11/2017
 */

import java.util.Scanner;
import java.io.*;

public class MyCalendarTester {
	
	static Scanner in;
	
	public static void main(String[]args) {
		in = new Scanner(System.in);		
		MyCalendar cal = new MyCalendar();
		
		System.out.print(cal.displayMonthView());
		
		String response = "";
		while(!((response.toLowerCase().equals("q") || response.toLowerCase().equals("quit")))) {
			System.out.print("Select one of the following options: ");
			System.out.print("\n[L]oad   [V]iew   [C]reate   [G]o to   [E]vent list   [D]elete   [Q]uit\n");
			response = in.nextLine();
			System.out.println(response);
			
			//Load
			if(response.toLowerCase().equals("l") || response.toLowerCase().equals("load")) {
				
				//DESERIALIZING AN OBJECT
				try{
					FileInputStream fileIn = new FileInputStream("events.txt");
					ObjectInputStream in_1 = new ObjectInputStream(fileIn);
					cal = (MyCalendar)in_1.readObject();
					in_1.close();
					fileIn.close();
				}
				catch(IOException i) {
					System.out.println("This is the first run, no existing file available");
				}
				catch(ClassNotFoundException c) {
					System.out.println("MyCalendar class not found");
				}
			}
			//Create
			else if(response.toLowerCase().equals("c") || response.toLowerCase().equals("create")) {
				System.out.println("Please enter title of event: ");
				String title = in.nextLine();
				System.out.println(title);
				
				System.out.println("Please enter date of event (MM/DD/YYYY): ");
				String date = in.nextLine();
				System.out.println(date);
				
				System.out.println("Please enter starting time of event: ");
				String startTime = in.nextLine();
				System.out.println(startTime);
				
				System.out.println("Please enter ending time of event: ");
				String endTime = in.nextLine();
				System.out.println(endTime);
				
				if(endTime.toLowerCase().equals("n")) {
					cal.addEvent(title, date, startTime, endTime, false);
				}
				else {
					cal.addEvent(title, date, startTime, endTime, true);
				}				
			}
			//Go to
			else if(response.toLowerCase().equals("g") || response.toLowerCase().equals("go to")) {
				System.out.println("Please enter a date (MM/DD/YYYY): ");
				String selectedDate = in.nextLine();
				System.out.println(selectedDate);
				
				cal.setSelectedDate(selectedDate);
				System.out.println(cal.displayDayView());
			}
			//Event List
			else if(response.toLowerCase().equals("e") || response.toLowerCase().equals("event list")) {
				System.out.println(cal.displayAllEvents());
			}
			//Delete
			else if(response.toLowerCase().equals("d") || response.toLowerCase().equals("delete")) {
				System.out.print("[S]elected or [A]ll?: ");
				String deleteResponse = in.nextLine();
				System.out.println(deleteResponse);
				
				if(deleteResponse.toLowerCase().equals("s") || deleteResponse.toLowerCase().equals("selected")) {
					System.out.println("Please enter the date: ");
					String deleteDate = in.nextLine();
					System.out.println(deleteDate);
					cal.deleteEvent_DAY(deleteDate);
				}
				else {
					cal.deleteEvent_ALL();
					System.out.println("All Events are Deleted");
				}
			}
			//View
			else if(response.toLowerCase().equals("v") || response.toLowerCase().equals("view")) {
				System.out.println("[D]ay view or [M]onth view? ");
				String viewResponse = in.nextLine();
				System.out.println(viewResponse);
				
				String mainResponse = "";
				while(!(mainResponse.toLowerCase().equals("m") || mainResponse.toLowerCase().equals("main menu"))) {
					
					if(viewResponse.toLowerCase().equals("d") || viewResponse.toLowerCase().equals("day view")) {
						//System.out.println(cal.displayDayView());
						if(mainResponse.toLowerCase().equals("p") || mainResponse.toLowerCase().equals("previous")) {
							cal.goPreviousDay();
						}
						else if(mainResponse.toLowerCase().equals("n") || mainResponse.toLowerCase().equals("next")) {
							cal.goNextDay();
						}
						System.out.println(cal.displayDayView());
					}
					else if(viewResponse.toLowerCase().equals("m") || viewResponse.toLowerCase().equals("month view")){
						//System.out.println(cal.displayMonthView());
						if(mainResponse.toLowerCase().equals("p") || mainResponse.toLowerCase().equals("previous")) {
							cal.goPreviousMonth();;
						}
						else if(mainResponse.toLowerCase().equals("n") || mainResponse.toLowerCase().equals("next")) {
							cal.goNextMonth();
						}
						System.out.println(cal.displayMonthView());
					}
					System.out.println("[P]revious or [N]ext or [M]ain menu? ");
					mainResponse = in.nextLine();
					System.out.println(mainResponse);
				}				
				
			}			

		} //end of while loop
		
		//SERIALIZING AN OBJECT
		try{
			File myFile = new File("events.txt");
			FileOutputStream fileOut = new FileOutputStream(myFile);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(cal);
			out.close();
			fileOut.close();
			System.out.println("Saved calendar object into events.txt");
		}catch(IOException i) {
			System.out.println("Cannot open events.txt for write");
			i.printStackTrace();
		}
		
		
	} //end of main method

	
	
} //end of class
