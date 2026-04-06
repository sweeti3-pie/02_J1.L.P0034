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
public class Booking extends Entity {
    private String fullName;
    private String tourID;
    private Date bookingDate;
    private String phone;

    public Booking() {
    }

    public Booking(String bookingID, String fullName, String tourID, Date bookingDate, String phone) {
        super(bookingID);
        this.fullName = fullName;
        this.tourID = tourID;
        this.bookingDate = bookingDate;
        this.phone = phone;
    }

    public String getBookingID() {
        return id;
    }

    public void setBookingID(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTourID() {
        return tourID;
    }

    public void setTourID(String tourID) {
        this.tourID = tourID;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String formatBookingDate() {
        return bookingDate != null ? Acceptable.DATE_FORMAT.format(bookingDate) : "N/A";
    }

    @Override
    public String toString() {
//        return "Booking{" + "bookingID=" + bookingID + ", fullName=" + fullName + ", tourID=" + tourID + ", bookingDate=" + bookingDate + ", phone=" + phone + '}';
        return String.format("%-8s %-30s %-8s %-12s %-12s", id, fullName, tourID, formatBookingDate(), phone);
    }

    @Override
    public String toFileFormat() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        return String.format("%s,%s,%s,%s,%s", id, fullName, tourID, formatBookingDate(), phone);
    }
}
