/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import model.Order;
import javax.swing.*;
import java.util.List;
import util.Validator;
import ui.OrderTableFrame;

/**
 *
 * @author nicol
 */
public class OrderFormPanel extends JPanel {

    private JTextField txtDishName;
    private JComboBox<String> cbCategory;
    private JTextField txtQuantity;
    private JRadioButton rbDelivery;
    private JRadioButton rbPickup;
    private ButtonGroup deliveryGroup;
    private JTextField txtAddress;
    private JButton btnClear;
    private JButton btnConfirm;

    private JLabel lblErrorDishName;
    private JLabel lblErrorCategory;
    private JLabel lblErrorQuantity;
    private JLabel lblErrorDelivery;
    private JLabel lblErrorAddress;

    private List<Order> orderList;
    private OrderTableFrame tableFrame;

    public OrderFormPanel(List<Order> orderList, OrderTableFrame tableFrame) {
        this.orderList = orderList;
        this.tableFrame = tableFrame;
        initComponents();
    }

    private void initComponents() {
        txtDishName = new JTextField(20);
        cbCategory = new JComboBox<>(new String[]{"-- Selecciona --", "Entrante", "Plato Principal", "Postre"});
        txtQuantity = new JTextField(5);
        rbDelivery = new JRadioButton("Domicilio");
        rbPickup = new JRadioButton("Recogida");
        deliveryGroup = new ButtonGroup();
        txtAddress = new JTextField(20);
        btnClear = new JButton("Borrar");
        btnConfirm = new JButton("Confirmar");

        deliveryGroup.add(rbDelivery);
        deliveryGroup.add(rbPickup);

        txtAddress.setEnabled(false);

        lblErrorDishName = makeErrorLabel();
        lblErrorCategory = makeErrorLabel();
        lblErrorQuantity = makeErrorLabel();
        lblErrorDelivery = makeErrorLabel();
        lblErrorAddress = makeErrorLabel();

        setLayout(new java.awt.GridLayout(0, 2, 10, 8));

        add(new JLabel("Nombre del plato"));
        add(txtDishName);
        add(new JLabel(""));
        add(lblErrorDishName);

        add(new JLabel("Categoría:"));
        add(cbCategory);
        add(new JLabel(""));
        add(lblErrorCategory);

        add(new JLabel("Cantidad:"));
        add(txtQuantity);
        add(new JLabel(""));
        add(lblErrorQuantity);

        add(new JLabel("Método de entrega:"));
        JPanel radioPanel = new JPanel();
        radioPanel.add(rbDelivery);
        radioPanel.add(rbPickup);
        add(radioPanel);
        add(new JLabel(""));
        add(lblErrorDelivery);

        add(new JLabel("Dirección:"));
        add(txtAddress);
        add(new JLabel(""));
        add(lblErrorAddress);

        add(btnClear);
        add(btnConfirm);

        setupListeners();
    }

    private JLabel makeErrorLabel() {
        JLabel lbl = new JLabel();
        lbl.setForeground(java.awt.Color.RED);
        lbl.setVisible(false);
        return lbl;
    }

    private void setupListeners() {
        rbDelivery.addActionListener(e -> {
            txtAddress.setEnabled(true);
        });

        rbPickup.addActionListener(e -> {
            txtAddress.setEnabled(false);
            txtAddress.setText("");
            lblErrorAddress.setVisible(false);
        });

        btnClear.addActionListener(e -> {
            txtDishName.setText("");
            txtDishName.setText("");
            cbCategory.setSelectedIndex(0);
            txtQuantity.setText("");
            deliveryGroup.clearSelection();
            txtAddress.setText("");
            txtAddress.setEnabled(false);

            lblErrorDishName.setVisible(false);
            lblErrorCategory.setVisible(false);
            lblErrorQuantity.setVisible(false);
            lblErrorDelivery.setVisible(false);
            lblErrorAddress.setVisible(false);
        });

        btnConfirm.addActionListener(e -> {

            String dishName = txtDishName.getText().trim();
            String category = (String) cbCategory.getSelectedItem();
            String quantityText = txtQuantity.getText().trim();
            boolean isDomicilio = rbDelivery.isSelected();
            boolean isRecogida = rbPickup.isSelected();
            String address = txtAddress.getText().trim();

            lblErrorDishName.setVisible(false);
            lblErrorCategory.setVisible(false);
            lblErrorQuantity.setVisible(false);
            lblErrorDelivery.setVisible(false);
            lblErrorAddress.setVisible(false);

            boolean valid = true;

            if (!Validator.validateDishName(dishName)) {
                lblErrorDishName.setText("⚠ Solo letras, números y espacios");
                lblErrorDishName.setVisible(true);
                valid = false;
            }
            if (!Validator.validateCategory(category)) {
                lblErrorCategory.setText("⚠ Selecciona una categoría");
                lblErrorCategory.setVisible(true);
                valid = false;
            }
            if (!Validator.validateQuantity(quantityText)) {
                lblErrorQuantity.setText("⚠ Introduce un número positivo");
                lblErrorQuantity.setVisible(true);
                valid = false;
            }
            if (!Validator.validateDeliveryMethod(isDomicilio, isRecogida)) {
                lblErrorDelivery.setText("⚠ Selecciona un método de entrega");
                lblErrorDelivery.setVisible(true);
                valid = false;
            }
            if (!Validator.validateAddress(address, isDomicilio)) {
                lblErrorAddress.setText("⚠ Ingresa una dirección válida");
                lblErrorAddress.setVisible(true);
                valid = false;
            }

            if (valid) {
                String deliveryMethod = isDomicilio ? "Domicilio" : "Recogida";
                Order newOrder = new Order(dishName, category, Integer.parseInt(quantityText), deliveryMethod, address);
                orderList.add(newOrder);
                tableFrame.refreshTable();
                System.out.println("Pedido añadido: " + newOrder.getOrderId());

                btnClear.doClick();
            }
        });
    }
}
