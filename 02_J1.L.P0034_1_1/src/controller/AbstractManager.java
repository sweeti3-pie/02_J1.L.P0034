/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import model.Entity;

/**
 *
 * @author Admin
 * @param <T>
 */
public abstract class AbstractManager<T extends Entity> implements Workable<T> {
    protected TreeMap<String, T> entityMap;
    protected String filePath;
    protected boolean saved;
    protected Inputter ip;

    public AbstractManager(String filePath) {
        this.entityMap = new TreeMap<>();
        this.filePath = filePath;
        this.saved = true;
        this.ip = new Inputter();
    }

    public boolean isSaved() {
        return saved;
    }
    
    public boolean saveToFile() {
        try {
            List <String> lines = new ArrayList<>();
            //Corvert Map -> List<String>
            for (T entity : entityMap.values()) {
                lines.add(entity.toFileFormat());
            }
            //Write to file
            FileUtils.writeFile(filePath, lines);
            this.saved = true;
//            System.out.println("Saved to " + filePath);
            return true;
        } catch (IOException e) {
            System.out.println("Error saving " + e.getMessage());
            return false;
        }
    }
    
    public void loadFromFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("No existing file data found. Starting fresh.");
            return;
        }
    }
    
    protected abstract void printHeader();
}
