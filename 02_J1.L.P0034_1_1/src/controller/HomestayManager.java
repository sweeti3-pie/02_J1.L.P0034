/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import model.Homestay;
import model.Tour;

/**
 *
 * @author Admin
 */
public class HomestayManager {
    private TreeMap<String, Homestay> homestayMap;
    private final String FILE_PATH = "./Homestays.txt";

    public HomestayManager() {
        this.homestayMap = new TreeMap<>();
        loadFromFile();
    }
    
    public void loadFromFile() {
        try {
            List<String> lines = FileUtils.readFile(FILE_PATH);
    
//            System.out.println("Total lines: " + lines.size());
            
            for (String line : lines) {
                System.out.println(line);
                String[] parts = line.split("-");
                if (parts.length >= 5) {
                    try {
                        String homeID = parts[0].trim();
                        String homeName = parts[1].trim();
                        int roomNumber = Integer.parseInt(parts[2].trim());

                        StringBuilder addressBuilder = new StringBuilder();
                        for (int i=3; i<parts.length-1; i++) {
                            addressBuilder.append(parts[i].trim());
                            if (i<parts.length-2) {
                                addressBuilder.append("_");
                            }
                        }
                        String address = addressBuilder.toString();
                        int maxCapacity = Integer.parseInt(parts[parts.length-1].trim());
    //                    String address = parts[3].trim();
    //                    int maxCapacity = Integer.parseInt(parts[4].trim());

                        Homestay hs = new Homestay(homeID, homeName, roomNumber, address, maxCapacity);
                        homestayMap.put(homeID, hs);
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing number in line " + line);
                    }
                } else {
                    System.out.println("Skipped line (insufficient parts): " + line);
                }
            }
            
            System.out.println("Loaded " + homestayMap.size() + " homestays.");
        } catch (IOException e) {
            System.out.println("Homestays file not found. Starting with empty list.");
        } catch (Exception e) {
            System.out.println("Error loading homestays: " + e.getMessage());
        }
    }
    
    public boolean existsById(String homeID) {
//        return homestayList.stream().anyMatch(h -> h.getHomeID().equalsIgnoreCase(homeID));
        return homestayMap.containsKey(homeID);
    }
    
    public Homestay getById(String homeID) {
//        return homestayList.stream().filter(h -> h.getHomeID().equalsIgnoreCase(homeID)).findFirst().orElse(null);
        return homestayMap.get(homeID);
    }
    
    public TreeMap<String, Homestay> getAll() {
        return new TreeMap<>(homestayMap);
    }
    
    public void displayAll() {
        if (homestayMap.isEmpty()) {
            System.out.println("No homestays available.");
            return;
        }
        System.out.println("Homestay List");
        System.out.printf("%-8s %-20s %-6s %-30s %-5s", "ID", "Name", "Rooms", "Address", "Capacity");
    
        for (Homestay hs : homestayMap.values()) {
            System.out.println(hs);
        }
    }
    
    public void statisticsTouristsByHomestay(TourManager tourManager) {
        Map<String, Integer> touristStats = new HashMap<>();
        for (Tour tour : tourManager.getAll()) {
            String homeID = tour.getHomeID();
            int tourists = tour.getNumberTourist();
            
            touristStats.put(homeID, touristStats.getOrDefault(homeID, 0) + tourists);
        }
        if (touristStats.isEmpty()) {
            System.out.println("No statistics available.");
            return;
        }
        System.out.println("Statistics: total tourists by homestay");
        System.out.printf("%-10s %-30s %15s\n", "Home ID", "Homestay Name", "Total Tourists");
        int grandTotal = 0;
        for (Map.Entry<String, Integer> entry : touristStats.entrySet()) {
            String homeID = entry.getKey();
            int totalTourists = entry.getValue();
            Homestay homestay = getById(homeID);
            if (homestay != null) {
                System.out.printf("%-10s %-30s %,15d\n", homeID, homestay.getHomeName(), totalTourists);
                grandTotal += totalTourists;
            }
        }
        System.out.printf("%-18s %,16d", "Grand Total: ", grandTotal);
    }
}