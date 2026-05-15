import model.Order;
import ui.OrderFormPanel;
import ui.OrderTableFrame;
import util.Validator;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // --- Automated Tests ---
        Order.resetId(); // reset for predictable test IDs

        // Test 1: Order creation and auto-ID
        Order o1 = new Order("Paella", "Plato Principal", 2, "Domicilio", "Calle Mayor 5");
        assert o1.getOrderId() == 1 : "FAIL: Order ID should be 1";
        System.out.println("PASS: Order created with ID " + o1.getOrderId());

        // Test 2: Second order gets next ID
        Order o2 = new Order("Gazpacho", "Entrante", 1, "Recogida", "");
        assert o2.getOrderId() == 2 : "FAIL: Order ID should be 2";
        System.out.println("PASS: Second order ID is " + o2.getOrderId());

        // Test 3: Empty dish name rejected
        assert !Validator.validateDishName("") : "FAIL: Empty name should be invalid";
        System.out.println("PASS: Empty dish name rejected");

        // Test 4: Valid dish name accepted
        assert Validator.validateDishName("Gazpacho") : "FAIL: Valid name rejected";
        System.out.println("PASS: Valid dish name accepted");

        // Test 5: Special chars allowed in dish name
        assert Validator.validateDishName("Crème brûlée") == false : "FAIL: accents outside range should fail";
        assert Validator.validateDishName("Paella Valenciana") : "FAIL: spaces should be allowed";
        System.out.println("PASS: Dish name with spaces accepted");

        // Test 6: Invalid category rejected
        assert !Validator.validateCategory("-- Selecciona --") : "FAIL: Placeholder should be invalid";
        System.out.println("PASS: Placeholder category rejected");

        // Test 7: Valid category accepted
        assert Validator.validateCategory("Postre") : "FAIL: Valid category rejected";
        System.out.println("PASS: Valid category accepted");

        // Test 8: Negative quantity rejected
        assert !Validator.validateQuantity("-1") : "FAIL: Negative quantity should be invalid";
        System.out.println("PASS: Negative quantity rejected");

        // Test 9: Zero quantity rejected
        assert !Validator.validateQuantity("0") : "FAIL: Zero quantity should be invalid";
        System.out.println("PASS: Zero quantity rejected");

        // Test 10: Non-numeric quantity rejected
        assert !Validator.validateQuantity("abc") : "FAIL: Non-numeric should be invalid";
        System.out.println("PASS: Non-numeric quantity rejected");

        // Test 11: Valid quantity accepted
        assert Validator.validateQuantity("3") : "FAIL: Valid quantity rejected";
        System.out.println("PASS: Valid quantity accepted");

        // Test 12: No delivery method selected
        assert !Validator.validateDeliveryMethod(false, false) : "FAIL: No selection should be invalid";
        System.out.println("PASS: No delivery method rejected");

        // Test 13: Delivery method selected
        assert Validator.validateDeliveryMethod(true, false) : "FAIL: Domicilio should be valid";
        System.out.println("PASS: Domicilio delivery method accepted");

        // Test 14: Empty address with Domicilio rejected
        assert !Validator.validateAddress("", true) : "FAIL: Empty address with Domicilio should fail";
        System.out.println("PASS: Empty address with Domicilio rejected");

        // Test 15: Empty address with Recogida accepted
        assert Validator.validateAddress("", false) : "FAIL: Empty address with Recogida should pass";
        System.out.println("PASS: Recogida with no address accepted");

        // Test 16: Valid address with Domicilio accepted
        assert Validator.validateAddress("Calle Mayor 5", true) : "FAIL: Valid address rejected";
        System.out.println("PASS: Valid address with Domicilio accepted");

        System.out.println("\nAll tests passed!");

        // --- Launch GUI ---
        SwingUtilities.invokeLater(() -> launchApp());
    }

    private static void launchApp() {
        List<Order> sharedOrderList = new ArrayList<>();
        OrderTableFrame tableFrame = new OrderTableFrame(sharedOrderList);
        OrderFormPanel formPanel = new OrderFormPanel(sharedOrderList, tableFrame);

        JFrame mainFrame = new JFrame("Sistema de Pedidos - Restaurante");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(formPanel);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        tableFrame.setLocationRelativeTo(mainFrame);
        tableFrame.setVisible(true);
    }
}
