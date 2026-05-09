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
        return category != null && !category.equals("-- Selecciona --");
    }

    public static boolean validateQuantity(String quantityText) {
        try {
            int quantity = Integer.parseInt(quantityText);
            return quantity > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validateDeliveryMethod(boolean deliverySelected, boolean pickupSelected) {
        return deliverySelected || pickupSelected;
    }

    public static boolean validateAddress(String address, boolean isDelivery) {
        if (isDelivery) {
            return address.length() > 3;
        } else {
            return true;
        }
    }
}
