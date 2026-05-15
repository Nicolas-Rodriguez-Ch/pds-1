package ui;

import model.Order;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class OrderTableFrame extends JFrame {

    private JTable orderTable;
    private DefaultTableModel tableModel;
    private JButton btnCancel;
    private JButton btnExport;
    private List<Order> orderList;

    public OrderTableFrame(List<Order> orderList) {
        this.orderList = orderList;
        setTitle("Pedidos Realizados");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        String[] columns = {"Nº Pedido", "Nombre del Plato", "Categoría", "Cantidad", "Método de Entrega", "Dirección"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        orderTable = new JTable(tableModel);

        btnCancel = new JButton("Cancelar Pedido");
        btnExport = new JButton("Exportar Pedidos");

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnCancel);
        btnPanel.add(btnExport);

        add(new JScrollPane(orderTable), java.awt.BorderLayout.CENTER);
        add(btnPanel, java.awt.BorderLayout.SOUTH);

        pack();
        setSize(700, 300);

        btnCancel.addActionListener(e -> {
            int row = orderTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un pedido para cancelar.");
                return;
            }
            int id = (int) tableModel.getValueAt(row, 0);
            orderList.removeIf(o -> o.getOrderId() == id);
            refreshTable();
        });

        btnExport.addActionListener(e -> {
            System.out.println("=== EXPORTANDO PEDIDOS ===");
            for (Order o : orderList) {
                System.out.println(o);
            }
            System.out.println("=========================");
            JOptionPane.showMessageDialog(this, "Pedidos exportados a consola.");
        });
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        for (Order o : orderList) {
            tableModel.addRow(new Object[]{
                o.getOrderId(), o.getDishName(), o.getCategory(),
                o.getQuantity(), o.getDeliveryMethod(), o.getAddress()
            });
        }
    }
}
