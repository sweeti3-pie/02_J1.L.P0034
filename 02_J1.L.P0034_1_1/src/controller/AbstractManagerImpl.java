/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.Collection;
import java.util.function.Predicate;
import model.Entity;


public abstract class AbstractManagerImpl<T extends Entity> extends AbstractManager<T> {

    public AbstractManagerImpl(String filePath) {
        super(filePath);
    }
    
//    @Override
//    public void addNew(T x) {
////        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    @Override
//    public void update(T x) {
////        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }

    @Override
    public T searchById(String id) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        return entityMap.get(id);
    }

    @Override
    public boolean existsById(String id) {
        return entityMap.containsKey(id);
    }
    
    @Override
    public void showAll() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        showAll(null);
    }

    @Override
    public void showAll(Predicate<T> filter) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        if (entityMap.isEmpty()) {
            System.out.println("No data available");
            return;
        }
        boolean hasResult = false;
        for (T entity : entityMap.values()) {
            if (filter == null || filter.test(entity)) {
                System.out.println(entity);
                hasResult = true;
            }
        }
        if (!hasResult && filter != null) {
            System.out.println("No result match the criteria!");
        }
    }
    
    public Collection<T> getAll() {
        return entityMap.values();
    }
    
}
