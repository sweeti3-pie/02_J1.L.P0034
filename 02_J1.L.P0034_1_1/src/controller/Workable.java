/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package controller;

import java.util.function.Predicate;

/**
 *
 * @author Admin
 * @param <T>
 */
public interface Workable <T>{
    void addNew (T x);
    void update (T x);
    T searchById(String id);
    boolean existsById(String id);
    void showAll();
    void showAll(Predicate<T> filter);
}
