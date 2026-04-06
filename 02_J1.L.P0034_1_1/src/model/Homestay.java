/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class Homestay {
    private String homeID;
    private String homeName;
    private int roomNumber;
    private String address;
    private int maximumCapacity;

    public Homestay() {
    }

    public Homestay(String homeID, String homeName, int roomNumber, String address, int maximumCapacity) {
        this.homeID = homeID;
        this.homeName = homeName;
        this.roomNumber = roomNumber;
        this.address = address;
        this.maximumCapacity = maximumCapacity;
    }

    public String getHomeID() {
        return homeID;
    }

    public void setHomeID(String homeID) {
        this.homeID = homeID;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMaximumCapacity() {
        return maximumCapacity;
    }

    public void setMaximumCapacity(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }

    @Override
    public String toString() {
//        return "Homestay{" + "homeID=" + homeID + ", homeName=" + homeName + ", roomNumber=" + roomNumber + ", address=" + address + ", maximumCapacity=" + maximumCapacity + '}';
        return String.format("%-8s %-20s %-6d %-30s %-5d", homeID, homeName, roomNumber, address, maximumCapacity);
    }

//    @Override
//    public String toFileFormat() {
////        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//        return String.format("%s,%s,%s,%s,%s", homeID, homeName, roomNumber, address, maximumCapacity);
//    }
}
