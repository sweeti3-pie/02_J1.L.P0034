/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.BookingManager;
import controller.HomestayManager;
import controller.Inputter;
import controller.TourManager;

/**
 *
 * @author Admin
 */
public class Menu {
    private Inputter ip;
    private HomestayManager homestayManager;
    private TourManager tourList;
    private BookingManager bookingList;

    public Menu() {
        this.ip = new Inputter();
        this.homestayManager = new HomestayManager();
        this.tourList = new TourManager(homestayManager, "./Tours");
        this.bookingList = new BookingManager(tourList);
    }
    
    
    
    public void menu() {
        int choice;
        boolean running = true;
        do {            
            choice=ip.getInt("-----------------------------------------------------\n"
                                + "1. Add a new Tour.\n"
                                + "2. Update a Tour by ID.\n"
                                + "3. List the Tours with departure dates earlier than the current date.\n"
                                + "4. List the total Booking amount for tours with departure dates later than the current date.\n"
                                + "5. Add a new Booking.\n"
                                + "6. Remove a Booking by bookingID.\n"
                                + "7. Update a Booking by bookingID.\n"
                                + "8. List all Booking by the fullName or a partial fullName.\n"
                                + "9. Statistics on the total number of tourists who have booked homestays\n"
                                + "10. Save and quit\n"
                                + "-----------------------------------------------------\n");
            switch (choice) {
                case 1:
                    tourList.addNew(null);
                    break;
                case 2:
                    tourList.update(null);
                    break;
                case 3:
                    tourList.showToursBeforeCurrentDate();
                    break;
                case 4:
                    tourList.listTotalBookingAmount();
                    break;
                case 5:
                    bookingList.addNew(null);
                    break;
                case 6:
                    bookingList.removeBooking();
                    break;
                case 7:
                    bookingList.update(null);
                    break;
                case 8:
                    bookingList.filterByName();
                    break;
                case 9:
                    homestayManager.statisticsTouristsByHomestay(tourList);
                    break;
                case 10:
                    if (!tourList.isSaved() || !bookingList.isSaved()) {
                        String confirm = ip.getString("You have unsaved changes. Do you want to save? (Y/N): ");
                        if (confirm.equalsIgnoreCase("Y")) {
                            saveData();
                        }
                    }
                    running = false;
                    System.out.println("Bye bye........");
                    break;
                default:
                    System.out.println("Invalid choice! Please enter 1-10.");
                    break;
            }
        } while (running == true);
    }
    
    private void saveData() {
        System.out.println("Save data to file");
        boolean tourSaved = false;
        boolean bookingSaved = false;
        if (!tourList.isSaved()) {
            tourSaved = tourList.saveToFile();
            if (tourSaved) {
                System.out.println("Tour data saved successfully to Tours.txt");
            } else {
                System.out.println("Failed to save tour data!");
            }
        } else {
            System.out.println("Tours already saved (no changes).");
            tourSaved = true;
        }
        
        if (!bookingList.isSaved()) {
            bookingSaved = bookingList.saveToFile();
            if (bookingSaved) {
                System.out.println("Booking data saved successfully to Bookings.txt");
            } else {
                System.out.println("Failed to save booking data!");
            }
        } else {
            System.out.println("Bookings already saved (no changes).");
            bookingSaved = true;
        }
    }
}
