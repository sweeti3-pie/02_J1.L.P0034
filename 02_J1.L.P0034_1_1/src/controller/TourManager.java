/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Homestay;
import model.Tour;

/**
 *
 * @author Admin
 */
public class TourManager extends AbstractManagerImpl<Tour> {

    private HomestayManager homestayManager;

    public TourManager(HomestayManager homestayManager, String filePath) {
        super("./Tours.txt");
        this.homestayManager = homestayManager;
        loadFromFile();
    }
    
    @Override
    public void loadFromFile() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//        File file = new File(filePath);
//        if (!file.exists()) {
//            System.out.println("No existing tour data found. Starting fresh.");
//            return;
//        }
        try {
            List<String> lines = FileUtils.readFile(filePath);
            
            for (String line : lines) {
                try {
                    String[] parts = line.split(",");
                    if (parts.length >= 8) {
                        String tourID = parts[0].trim();
                        String tourName = parts[1].trim();
                        String time = parts[2].trim();
                        int price = Integer.parseInt(parts[3].trim());
                        String homeID = parts[4].trim();
                        Date departureDate = Acceptable.DATE_FORMAT.parse(parts[5].trim());
                        Date endDate = Acceptable.DATE_FORMAT.parse(parts[6].trim());
                        int numberTourist = Integer.parseInt(parts[7].trim());

                        if (entityMap.containsKey(tourID)) {
                            continue;
                        }
                        Tour tour = new Tour(tourID, tourName, time, price, homeID, departureDate, endDate, numberTourist/*, saved*/);
                        System.out.println(tour);
                        entityMap.put(tourID.toUpperCase(), tour);
                    }
                } catch (Exception e) {
                    System.out.println("Error parsing tour: " + line);
                }
            }
            System.out.println("Loaded " + entityMap.size() + " tours.");
            
        } catch (FileNotFoundException e) {
            System.out.println("Tours.txt not found. Starting with empty map.");
        } catch (Exception e) {
            System.out.println("Error loading tours: " + e.getMessage());
        }
    }

    @Override
    protected void printHeader() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        System.out.println(String.format("%-8s %-30s %-15s %-10s %-8s %-12s %-12s %8s", "Tour ID", "Tour Name", "Time", "Price", "Home ID", "Departure Date", "End Date", "Number Tourist"));
    }
    
    @Override
    public void addNew(Tour x) {
        boolean valid = false;
        if (x==null) {
            do {            
                x=inputTour();
                if (x==null || x.getTourID()==null || x.getTourID().isEmpty()) {
                    System.out.println("Add tour cancelled!");
                    return;
                }
                valid = validateTour(x, false);
                if (!valid) {
                    System.out.println("Please re-enter the tour information");
                }
            } while (!valid);
            entityMap.put(x.getTourID(), x);
            this.saved=false;
            System.out.println("Tour added successfully!");
        }
    }
    
    @Override
    public void update(Tour x) {
        String tourID = ip.inputAndLoop("Enter tour ID to update: ", Acceptable.TOUR_ID);
        Tour existingTour = entityMap.get(tourID.toUpperCase());
        
        if (existingTour==null) {
            System.out.println("Tour ID not found!");
            return;
        }
        
        System.out.println("Current tour info: ");
        System.out.println(existingTour);
        System.out.println("Update: ");
        Tour updatedTour = updateTour(existingTour);
        if (!validateTour(updatedTour, true)) {
            System.out.println("Update failed due to validation errors.");
            return;
        }
        entityMap.put(tourID, updatedTour);
        this.saved = false;
        System.out.println("Tour updated successfully!");
    }
    
    @Override
    public Tour searchById(String id) {
        return entityMap.get(id.toUpperCase());
    }
    
    private Tour inputTour() {
        String id = ip.inputAndLoop("Tour ID: ", Acceptable.TOUR_ID);
        String name = ip.inputAndLoop("Tour Name: ", Acceptable.TOUR_NAME);
        String time = ip.inputAndLoop("Time: ", Acceptable.TIME);
        int price = ip.getInt("Price: ");
        String homeID = ip.inputAndLoop("Home ID: ", Acceptable.HOME_ID);
        Date departureDate = ip.inputDate("Departure date: ");
        Date endDate = ip.inputEndDate(departureDate);
        int numberTourist = ip.getInt("Number of tourists: ");
        return new Tour(id.toUpperCase(), name, time, price, homeID.toUpperCase(), departureDate, endDate, numberTourist);
    }
    
    public Tour updateTour(Tour existingTour) {
        String tourID = existingTour.getTourID();
        String name = ip.getStringWithDefault("Tour Name: ", existingTour.getTourName(), Acceptable.TOUR_NAME);
        String time = ip.getStringWithDefault("Time: ", existingTour.getTime(), Acceptable.TIME);
        int price = ip.getIntWithDefault("Price ", existingTour.getPrice());
        String homeID = ip.getStringWithDefault("Home ID: ", existingTour.getHomeID(), Acceptable.HOME_ID);
        Date departureDate = ip.inputDateWithDefault("Departure date", existingTour.getDepartureDate());
        Date endDate = ip.inputEndDateWithDefault(existingTour.getDepartureDate(), existingTour.getEndDate(), departureDate);
        int numberTourist = ip.getIntWithDefault("Number of tourists: ", existingTour.getNumberTourist());
        return new Tour(tourID.toUpperCase(), name, time, price, homeID.toUpperCase(), departureDate, endDate, numberTourist);
    }
    
    private boolean validateTour(Tour tour, boolean isUpdate) {
        // 1. check if tour ID exist
        if (!isUpdate) {
            if (existsById(tour.getTourID())) {
                System.out.println("Tour ID [" + tour.getTourID() + "] already exists!");
                return false;
            }
        }
        // 2. numberTourist <= maxCapacity cua homestay?
        Homestay homestay = homestayManager.getById(tour.getHomeID().toUpperCase());
        if (tour.getNumberTourist() > homestay.getMaximumCapacity()) {
            System.out.println("Number of tourist (" + tour.getNumberTourist() +") exceeds homestay capacity (" +homestay.getMaximumCapacity() +")");
            return false;
        }
        // 3. check if duplicate both day and homeID
        if (hasScheduleConflict(tour)) {
            System.out.println("Schedule conflict! This homestay is already booked for overlapping dates.");
            return false;
        }
        return true;
    }
    
    private boolean hasScheduleConflict(Tour newTour) {
        for (Tour existingTour : entityMap.values()) {
            //chi check cac tour cung homestay
            if (!existingTour.getHomeID().equals(newTour.getHomeID())) {
                continue;
            }
            //skip neu dang update chinh tour nay
            if (existingTour.getTourID().equals(newTour.getTourID())) {
                continue;
            }
            //kiem tra overlap
            //tour moi bat dau truoc khi tour cu ket thuc 
            //va tour moi ket thuc sau khi tour cu bat dau
            boolean overlap = !newTour.getDepartureDate().after(existingTour.getEndDate()) && !newTour.getEndDate().before(existingTour.getDepartureDate());
            if (overlap) {
                return true;
            }
        }
        return false;
    }
    
    public void showToursBeforeCurrentDate() {
        Date today = ip.resetTime(new Date());
        System.out.println("Tours with departure dates earlier than current date");
        showAll(tour -> tour.getDepartureDate().before(today));
    }
    
    public void listTotalBookingAmount() {
        Date today = ip.resetTime(new Date());
        List<Tour> futureTours = new ArrayList<>();
        for (Tour tour : entityMap.values()) {
            if (tour.getDepartureDate().after(today)) {
                futureTours.add(tour);
            }
        }
        if (futureTours.isEmpty()) {
            System.out.println("No future tours available.");
            return;
        }
        futureTours.sort((t1, t2) -> {
            double amount1 = t1.getPrice()*t1.getNumberTourist();
            double amount2 = t2.getPrice()*t2.getNumberTourist();
            return Double.compare(amount2, amount1); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/LambdaBody
        });
        System.out.println("Future tours - Total booking amount");
        System.out.printf("%-8s %-15s %-10s %16s\n", "Tour ID", "Tour Name", "Departure Date", "Total Amount");
        double grandTotal = 0;
        for (Tour futureTour : futureTours) {
            double totalAmount = futureTour.getPrice()*futureTour.getNumberTourist();
            grandTotal += totalAmount;
            System.out.printf("%-8s %-15s %-10s %,16f\n", futureTour.getTourID(), futureTour.getTourName(), futureTour.formatDepartureDate(), totalAmount);
        }
        System.out.printf("%-18s %,16f\n", "Grand total", grandTotal);
    }
}
