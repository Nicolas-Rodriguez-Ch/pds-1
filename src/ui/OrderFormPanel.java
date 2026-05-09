/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import model.Order;
import javax.swing.*;
import java.util.List;

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

    public OrderFormPanel(List<Order> orderList) {
        this.orderList = orderList;
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
            txtAddress.setEnabled(true);
            txtAddress.setText("");
            lblErrorAddress.setVisible(false);
        });
    }
}
