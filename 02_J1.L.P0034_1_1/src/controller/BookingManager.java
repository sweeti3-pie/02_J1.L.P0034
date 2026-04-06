/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.Booking;
import model.Tour;

/**
 *
 * @author Admin
 */
public class BookingManager extends AbstractManagerImpl<Booking>{

    private TourManager tourManager;
    

    public BookingManager(TourManager tourManager) {
        super("./Bookings.txt");
        this.tourManager = tourManager;
        loadFromFile();
    }
    
    @Override
    public void loadFromFile() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//        File file = new File(filePath);
//        if (!file.exists()) {
//            System.out.println("No existing booking data found. Starting fresh.");
//            return;
//        }
        try {
            List<String> lines = FileUtils.readFile(filePath);
            
            for(String line : lines) {
                try {
                    String[] parts = line.split(" ");
                    if (parts.length >= 5) {
                        String bookingID = parts[0].trim();
                        String fullName = parts[1].trim();
                        String tourID = parts[2].trim();
                        Date bookingDate = Acceptable.DATE_FORMAT.parse(parts[3].trim());
                        String phone = parts[3].trim();

                        Booking booking = new Booking(bookingID, fullName, tourID, bookingDate, phone);
                        entityMap.put(bookingID.toUpperCase(), booking);
                    }
                } catch (Exception e) {
                    System.out.println("Error parsing booking: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Booking.txt not found. Starting with empty map.");
        } catch (Exception e) {
            System.out.println("Error loading tours: " + e.getMessage());
        }
    }

    @Override
    protected void printHeader() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        System.out.println(String.format("%-8s %-30s %-8s %-12s %-12s", "Booking ID", "Full Name", "Tour ID", "Booking Date", "Phone Number"));
    }

    @Override
    public void addNew(Booking x) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        boolean valid = false;
        if (x==null) {
            do {
                x=inputBooking();
                
                if (x==null || x.getBookingID()==null || x.getBookingID().isEmpty()) {
                    System.out.println("Booking cancelled!");
                    return;
                }
                valid = validateBooking(x, false);
                if (!valid) {
                    System.out.println("Please re-enter the booking information.");
                }
            } while (!valid);
        }
        entityMap.put(x.getBookingID(), x);
        this.saved = false;
        System.out.println("Booking added successfully!");
    }

    @Override
    public void update(Booking x) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        String bookingID = ip.inputAndLoop("Enter booking ID to update: ", Acceptable.BOOKING_ID);
        Booking existingBooking = entityMap.get(bookingID);
    
       if (existingBooking==null) {
           System.out.println("Booking ID not found!");
           return;
       }
        System.out.println("Current booking info: ");
        System.out.println(existingBooking);
        System.out.println("Update: ");
        Booking updateBooking = updateBooking(existingBooking);
        if (!validateBooking(updateBooking, true)) {
            System.out.println("Update failed due to validation errors.");
            return;
        }
        entityMap.put(bookingID, updateBooking);
        this.saved = false;
        System.out.println("Booking updated successfully!");
    }

    @Override
    public Booking searchById(String id) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        return entityMap.get(id.toUpperCase());
    }
    
    public Booking inputBooking() {
        String id=ip.inputAndLoop("Booking ID (B + 5 digits): ", Acceptable.BOOKING_ID);
        String name = ip.inputAndLoop("Full Name: ", Acceptable.FULL_NAME);
        String tourID = ip.inputAndLoop("Tour ID (T + 5 digits): ", Acceptable.TOUR_ID);
        Date bookingDate = ip.inputDate("Booking date: ");
        String phone = ip.inputAndLoop("Phone: ", Acceptable.PHONE_NUM);
        return new Booking(id.toUpperCase(), name, tourID.toUpperCase(), bookingDate, phone);
    }
    
    public Booking updateBooking(Booking existingBooking) {
        String bookingID = existingBooking.getBookingID();
        String name = ip.getStringWithDefault("Full Name: ", existingBooking.getFullName(), Acceptable.FULL_NAME);
        String tourID = ip.getStringWithDefault("Tour ID (T + 5 digits): ", existingBooking.getTourID(), Acceptable.TOUR_ID);
        Date bookingDate = ip.inputDateWithDefault("Booking date", existingBooking.getBookingDate());
        String phone = ip.getStringWithDefault("Phone: ", existingBooking.getPhone(), Acceptable.PHONE_NUM);
        return new Booking(bookingID.toUpperCase(), name, tourID.toUpperCase(), bookingDate, phone);
    }
    
    private boolean validateBooking(Booking booking, boolean isUpdate) {
        // 1. check if bookingID exist
        if (!isUpdate) {
//            if(bookingMap.containsKey(booking.getBookingID())) {
            if (existsById(booking.getBookingID())) { 
                System.out.println("Booking ID [" + booking.getBookingID() + "] already exists!");
                return false;
            }
        }

        // 2. check if tourID exist
        if (!tourManager.existsById(booking.getTourID())) {
            System.out.println("Tour ID [" + booking.getTourID() +"] does not exists!");
            return false;
        }
        // 3. booking_date < departure_date
        Tour tour = tourManager.searchById(booking.getTourID());
        if (tour != null) {
            if (!booking.getBookingDate().before(tour.getDepartureDate())) {
                System.out.println("Booking date must be before departure date!");
                System.out.println("    Departure date: " + new SimpleDateFormat("dd/MM/yyyy").format(tour.getDepartureDate()));
                return false;
            }
        }
        return true;
    }
    
//    public void removeBooking(String bookingID) {
//        bookingID = ip.inputAndLoop("Enter booking ID to remove: ", Acceptable.BOOKING_ID);
//        entityMap.remove(bookingID); //remove() nhận key (String), not object
//    }//sai
    
    public void removeBooking() {
        String bookingID = ip.inputAndLoop("Enter Booking ID to remove: ", Acceptable.BOOKING_ID);
        Booking removed = entityMap.remove(bookingID.toUpperCase());
        
        if (removed == null) {
            System.out.println("Booking ID not found!");
        } else {
            this.saved = false;
            System.out.println("Booking removed successfully!");
            System.out.println("Removed: " + removed.getFullName() + " - Tour ID: " + removed.getTourID());
        }
    }
    
    public void filterByName() {
        String searchName = ip.getString("Enter name to search: ");
        if (searchName == null) {
            System.out.println("");
            return;
        }
        showAll(booking -> booking.getFullName().toLowerCase().contains(searchName.toLowerCase()));
    }
    
}
