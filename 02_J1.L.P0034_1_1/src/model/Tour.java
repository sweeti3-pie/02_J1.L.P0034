/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import controller.Acceptable;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class Tour extends Entity {
    private String tourName;
    private String time;
    private int price;
    private String homeID;
    private Date departureDate;
    private Date endDate;
    private int numberTourist;
    
    public Tour() { 
    }

    public Tour(String tourID, String tourName, String time, int price, String homeID, Date departureDate, Date endDate, int numberTourist) {
        super(tourID);
        this.tourName = tourName;
        this.time = time;
        this.price = price;
        this.homeID = homeID;
        this.departureDate = departureDate;
        this.endDate = endDate;
        this.numberTourist = numberTourist;
    }

    public String getTourID() {
        return id.toUpperCase();
    }

    public void setTourID(String id) {
        this.id = id.toUpperCase();
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getHomeID() {
        return homeID;
    }

    public void setHomeID(String homeID) {
        this.homeID = homeID;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getNumberTourist() {
        return numberTourist;
    }

    public void setNumberTourist(int numberTourist) {
        this.numberTourist = numberTourist;
    }

    public String formatDepartureDate() {
       return departureDate != null ? Acceptable.DATE_FORMAT.format(departureDate) : "N/A";
    }
    
    public String formatEndDate() {
        return endDate != null ? Acceptable.DATE_FORMAT.format(endDate) : "N/A";
    }
    
    @Override
    public String toString() {
//        return "Tour{" + "tourID=" + tourID + ", tourName=" + tourName + ", time=" + time + ", price=" + price + ", homeID=" + homeID + ", departureDate=" + departureDate + ", endDate=" + endDate + ", numberTourist=" + numberTourist + '}';
        return String.format("%-8s %-30s %-15s %,10d %-8s %-12s %-12s %8d", id, tourName, time, price, homeID, formatDepartureDate(), formatEndDate(), numberTourist);
    }

    @Override
    public String toFileFormat() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("%s,%s,%s,%d,%s,%s,%s,%d", id, tourName, time, price, homeID, formatDepartureDate(), formatEndDate(), numberTourist);
    }
}
