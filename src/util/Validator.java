/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author nicol
 */
public class Validator {

    public static boolean validateDishName(String name) {
        return name.length() > 0 && name.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9 ]+");
    }
    
    public static boolean validateCategory(String category) {
        return category != null || ;
    }
}
