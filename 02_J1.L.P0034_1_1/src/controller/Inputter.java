/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.Acceptable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class Inputter {
    private Scanner sc;

    public Inputter() {
        this.sc=new Scanner(System.in);
    }
    
    public String getString(String mess) {
        System.out.println(mess);
        return this.sc.nextLine();
    }
    
    public int getInt(String mess) {
        int result = 0;
        String tam = getString(mess);
        if (Acceptable.isValid(tam, Acceptable.POSITIVE_INT_VALID)) {
            result = Integer.parseInt(tam);
        }
        return result;
    }
    
    public String inputAndLoop(String mess, String pattern) {
        String result="";
        boolean more=true;
        do {            
            result=getString(mess);
            more=!Acceptable.isValid(result, pattern);
            if (more && result.length()>0) {
                System.out.println("Data is invalid! Re-enter..");
            }
        } while (more);
        return result.trim();
    }
    
    public String getStringWithDefault(String mess, String defaultValue) {
        System.out.println(mess + " [" + defaultValue + "]: ");
        String input = sc.nextLine().trim();
        return input.isEmpty() ? defaultValue : input;
    }
    
    public String getStringWithDefault(String mess, String defaultValue, String pattern) {
        String result;
        do {
            System.out.println(mess + " [" + defaultValue + "]: ");
            result=sc.nextLine().trim();
            
            if (result.isEmpty()) {
                return defaultValue;
            }
            
            if (!Acceptable.isValid(result, pattern)) {
                System.out.println("Invalid format! Please try again.");
            }
        } while (!Acceptable.isValid(result, pattern));
        
        return result;
    }
    
    public int getIntWithDefault(String mess, int defaultValue) {
        String result;
        do {
            System.out.println(mess + " [" + defaultValue +"]: ");
            result = sc.nextLine().trim();

            if (result.isEmpty()) {
                return defaultValue;
            }

            if (!Acceptable.isValid(result, Acceptable.POSITIVE_INT_VALID)) {
                System.out.println("Invalid number! Please try again.");
            }
        } while (!Acceptable.isValid(result, Acceptable.POSITIVE_INT_VALID));           
            
        return Integer.parseInt(result);
    }
    
    public Date resetTime(Date date) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    public Date inputDate(String mess) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false); //strict parsing
        Date date = null;
        
        while (date==null) {
            try {
                String dateStr = getString(mess);
                date = sdf.parse(dateStr);
                date = resetTime(date);
            } catch (Exception e) {
                System.out.println("Invalid data format!");
                date = null;
            }
        }
        return date;
    }
    
    public Date inputDateWithDefault(String mess, Date defaultValue) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false); //strict parsing
        Date date = null;
        
        while (date==null) {
            try {
                System.out.println(mess + ": [" +sdf.format(defaultValue) +"]: ");
                String input = sc.nextLine().trim();

                if (input.isEmpty()) {
                    return defaultValue;
                }
                
                date = sdf.parse(input);
                date = resetTime(date);
                
            } catch (Exception e) {
                System.out.println("Invalid data format!");
                date = null;
            }
        }
        return date;
    }
    
    public Date inputEndDate(Date startDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false); //strict parsing
        Date date = null;
        Date startDateReset = resetTime(startDate);
        
        while (date==null) {
            try {
                String dateStr = getString("End date (dd/MM/yyyy): ");
                date = sdf.parse(dateStr);
                date = resetTime(date);
                
                if (date.before(startDateReset)) {
                    System.out.println("End date must be equal to or after the departure date!");
                    date = null;
                }
            } catch (Exception e) {
                System.out.println("Invalid data format!");
                date = null;
            }
        }
        return date;
    }
    
    public Date inputEndDateWithDefault(Date departureDate, Date defaultValue, Date newDepartureDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false); //strict parsing
        Date date = null;
        Date startDateReset = resetTime(newDepartureDate);
        
        while (date==null) {
            try {
                System.out.println("End date [" +sdf.format(defaultValue) +"]: ");
                String input = sc.nextLine().trim();

                if (input.isEmpty()) {
                    return defaultValue;
                }

                date = sdf.parse(input);
                date = resetTime(date);
                
                if (date.before(startDateReset)) {
                    System.out.println("End date must be equal to or after the departure date!");
                    date = null;
                }
            } catch (Exception e) {
                System.out.println("Invalid data format!");
                date = null;
            }
        }
        return date;
    }
}
