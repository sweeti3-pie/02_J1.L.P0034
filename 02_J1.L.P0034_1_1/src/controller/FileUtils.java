/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class FileUtils {
    public static List<String> readFile(String filename) throws FileNotFoundException {
        List<String> lines = new ArrayList<>();
        File file = new File(filename);
        
        if (!file.exists()) {
            System.out.println("File not found: " + filename);
            return lines;
        }
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"))) {
            String line;
            while ((line=br.readLine()) != null) {
                line = clearLine(line);
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
        return lines;
    }
    
    public static boolean writeFile(String filename, List<String> lines) throws FileNotFoundException {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error writing file: " + e.getMessage());
            return false;
        }
    }
    
    private static String clearLine(String line) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c=='\t') {
                sb.append('-');
            }
            //chỉ giữ ký tự printable (từ space trở lên)
            else if (c>=32 && c<=126) {
                sb.append(c);
            }
        }
        return sb.toString().trim();
    }
}
