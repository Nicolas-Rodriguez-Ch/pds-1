/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author nicol
 */
public class Order {

    int orderId;
    String dishName;
    String category;
    int quantity;
    String deliveryMethod;
    String address;

    private static int nextId = 1;

    public Order(String dishName, String category, int quantity, String deliveryMethod, String address) {
        this.orderId = nextId++;
        this.dishName = dishName;
        this.category = category;
        this.quantity = quantity;
        this.deliveryMethod = deliveryMethod;
        this.address = address;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getDishName() {
        return dishName;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public String getAddress() {
        return address;
    }

    public static void resetId() {
        nextId = 1;
    }

    @Override
    public String toString() {
        return "Order{id=" + orderId + ", dish='" + dishName + "', category='" + category +
               "', qty=" + quantity + ", method='" + deliveryMethod + "', address='" + address + "'}";
    }
}
