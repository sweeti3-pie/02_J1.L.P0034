/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package controller;

import java.text.SimpleDateFormat;

/**
 *
 * @author Admin
 */
public interface Acceptable {
    int MAX_HS_ID = 6;
    
    public final String TOUR_ID = "[Tt]\\d{5}" ;
    public final String TOUR_NAME = "^[A-Za-z\\s]{2,25}-[A-Za-z\\s]{2,25}$";
    public final String TIME = "\\d+\\s+days\\s+\\d+\\s+nights";
    public final String HOME_ID = "[Hh][Ss]000[1-"+MAX_HS_ID+"]";
    public final String BOOKING_ID = "[Bb]\\d{5}";
    public final String FULL_NAME = "^.{2,25}";
    public final String PHONE_NUM = "^0\\d{9}";
    public final String POSITIVE_INT_VALID = "^[1-9]\\d*";
    public final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public static boolean isValid(String data, String pattern) {
        return data.matches(pattern);
    }
}
