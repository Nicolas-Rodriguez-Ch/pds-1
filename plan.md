# 🍽️ Restaurant Order System — Project Plan

## What Are We Building?
A Java desktop application that simulates a restaurant food ordering system. It has a graphical interface (GUI) built with **Java Swing**, which is a built-in Java library for creating windows, buttons, forms, and tables. The app lets users place food orders, validate the input, store orders in memory, and view/manage them in a table.

---

## Tech Stack (No External Dependencies Needed)
- **Language:** Java (JDK 8 or higher)
- **GUI Library:** Swing (built into Java — no extra downloads)
- **IDE:** NetBeans (required by the assignment)
- **Build:** No Maven/Gradle needed — plain NetBeans project

---

## Project File Structure
```
RestaurantOrderSystem/
├── src/
│   └── restaurantordersystem/
│       ├── Main.java                  ← Entry point + automated tests
│       ├── model/
│       │   └── Order.java             ← Data model for a single order
│       ├── ui/
│       │   ├── OrderFormPanel.java    ← The order form (left/main panel)
│       │   └── OrderTableFrame.java   ← The orders table window
│       └── util/
│           └── Validator.java         ← All validation logic
└── (NetBeans project files auto-generated)
```

> **Why this structure?** Separating the model (data), ui (screens), and util (helpers) keeps code organized and easier to debug. Each file has one clear job.

---

## Step-by-Step Implementation Plan

---

### STEP 1 — Create the NetBeans Project

**What to do:**
1. Open NetBeans.
2. Go to `File → New Project → Java → Java Application`.
3. Name it `RestaurantOrderSystem`.
4. Uncheck "Create Main Class" — we will create it manually.
5. Inside `src/restaurantordersystem/`, create subfolders: `model`, `ui`, `util`.

**Why:** NetBeans organizes Java projects using packages (folders). Our subfolders map to Java packages like `restaurantordersystem.model`.

---

### STEP 2 — Create the Order Data Model (`Order.java`)

**File:** `src/restaurantordersystem/model/Order.java`

**What it does:** Represents one food order as a Java object. Think of it as a row in a spreadsheet — each field is a column.

**Fields to include:**
| Field | Java Type | Description |
|---|---|---|
| `orderId` | `int` | Auto-generated unique number |
| `dishName` | `String` | Name of the dish |
| `category` | `String` | "Entrante", "Plato Principal", or "Postre" |
| `quantity` | `int` | How many dishes |
| `deliveryMethod` | `String` | "Domicilio" or "Recogida" |
| `address` | `String` | Delivery address (empty if Recogida) |

**What to implement:**
- A constructor that takes all fields as parameters.
- Getter methods for every field (e.g., `getDishName()`, `getQuantity()`).
- A static counter variable `nextId` that auto-increments every time a new Order is created.
- A `toString()` method for easy printing during tests.

**Key concept — static counter:**
```java
private static int nextId = 1;
// In constructor:
this.orderId = nextId++;
```
This ensures every order gets a unique number automatically.

---

### STEP 3 — Create the Validator (`Validator.java`)

**File:** `src/restaurantordersystem/util/Validator.java`

**What it does:** Contains all validation rules in one place. The form will call these methods when the user clicks "Confirmar".

**Methods to implement:**

```
validateDishName(String name) → boolean
  - Must not be empty
  - Must only contain letters, numbers, and spaces
  - Hint: use name.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9 ]+")

validateCategory(String category) → boolean
  - Must not be null or the default placeholder value

validateQuantity(String quantityText) → boolean
  - Must be parseable as an integer: Integer.parseInt(quantityText)
  - Must be greater than 0
  - Wrap in try/catch for NumberFormatException

validateDeliveryMethod(boolean domicilioSelected, boolean recogidaSelected) → boolean
  - At least one must be selected

validateAddress(String address, boolean isDomicilio) → boolean
  - Only required if Domicilio is selected
  - Must not be empty if Domicilio is chosen
```

**Why a separate Validator class?** So the UI code stays clean. The form just calls `Validator.validateDishName(text)` and gets back true/false. Easy to test independently.

---

### STEP 4 — Build the Order Form (`OrderFormPanel.java`)

**File:** `src/restaurantordersystem/ui/OrderFormPanel.java`

**What it does:** This is the main form the user sees and interacts with. It extends `JPanel` and contains all form widgets.

**Swing components to declare:**
```java
JTextField txtDishName;       // Dish name input
JComboBox<String> cbCategory; // Category dropdown
JTextField txtQuantity;       // Quantity input
JRadioButton rbDomicilio;     // Home delivery option
JRadioButton rbRecogida;      // Pickup option
ButtonGroup deliveryGroup;    // Groups the radio buttons (only one selectable)
JTextField txtAddress;        // Address input (shown/hidden based on selection)
JButton btnClear;             // Clear button
JButton btnConfirm;           // Confirm button
JLabel lblAddressError;       // Red error labels next to each field
// ... (one error label per field)
```

**Layout approach:** Use `GridBagLayout` or `GroupLayout` for a clean form appearance. Alternatively, nest multiple `JPanel`s with `FlowLayout` or `BorderLayout` for simplicity.

**JComboBox setup:**
```java
cbCategory = new JComboBox<>(new String[]{
    "-- Selecciona --", "Entrante", "Plato Principal", "Postre"
});
```

**Radio button logic:**
- Add an `ActionListener` to `rbDomicilio` and `rbRecogida`.
- When `rbDomicilio` is selected → enable and show `txtAddress`.
- When `rbRecogida` is selected → disable and hide `txtAddress`.

**"Borrar" button logic:**
```
- Clear all JTextField fields (.setText(""))
- Reset JComboBox to index 0
- Clear both radio buttons (deliveryGroup.clearSelection())
- Disable the address field
- Hide all error labels
```

**"Confirmar" button logic:**
1. Read all field values.
2. Call each `Validator` method.
3. If any validation fails → show the corresponding error label in red, stop.
4. If all pass → create a new `Order` object, add it to the shared order list, refresh the orders table window, clear the form.

**Error highlighting:** Give each field an associated `JLabel` (initially invisible). On error, call `label.setVisible(true)` and `label.setText("⚠ Error message here")`. On success, hide them again.

---

### STEP 5 — Build the Orders Table Window (`OrderTableFrame.java`)

**File:** `src/restaurantordersystem/ui/OrderTableFrame.java`

**What it does:** A separate window (`JFrame`) that displays all confirmed orders in a table. It refreshes whenever a new order is confirmed.

**Swing components:**
```java
JTable orderTable;
DefaultTableModel tableModel;   // Holds the table data in memory
JButton btnCancel;              // Cancel selected order
JButton btnExport;              // Export orders
```

**Table columns:**
```java
String[] columns = {
    "Nº Pedido", "Nombre del Plato", "Categoría",
    "Cantidad", "Método de Entrega", "Dirección"
};
tableModel = new DefaultTableModel(columns, 0); // 0 = start with no rows
```

**How `DefaultTableModel` works:** It's like an in-memory spreadsheet. You add rows with:
```java
tableModel.addRow(new Object[]{
    order.getOrderId(),
    order.getDishName(),
    order.getCategory(),
    order.getQuantity(),
    order.getDeliveryMethod(),
    order.getAddress()
});
```

**`refreshTable(List<Order> orders)` method:**
- Called every time a new order is added.
- Clears all rows: `tableModel.setRowCount(0)`
- Re-adds all orders from the list.

**"Cancelar Pedido" button logic:**
1. Get the selected row index: `orderTable.getSelectedRow()`
2. If no row selected → show a dialog: `JOptionPane.showMessageDialog(...)`
3. Remove the order from the shared list by matching `orderId`.
4. Call `refreshTable()`.

**"Exportar Pedidos" button logic:**
```java
System.out.println("=== EXPORTANDO PEDIDOS ===");
for (Order o : orders) {
    System.out.println(o.toString());
}
System.out.println("=========================");
JOptionPane.showMessageDialog(this, "Pedidos exportados a consola.");
```

---

### STEP 6 — Create the Main Window and Wire Everything Together (`Main.java`)

**File:** `src/restaurantordersystem/Main.java`

**What it does:** Two responsibilities:
1. Launch the application (the real GUI).
2. Run automated tests using `main()`.

**Application launch:**
```java
// The shared order list — passed to both panels so they share the same data
List<Order> sharedOrderList = new ArrayList<>();

// Create the orders table window
OrderTableFrame tableFrame = new OrderTableFrame(sharedOrderList);

// Create the main form window
JFrame mainFrame = new JFrame("Sistema de Pedidos - Restaurante");
OrderFormPanel formPanel = new OrderFormPanel(sharedOrderList, tableFrame);
mainFrame.add(formPanel);
mainFrame.pack();
mainFrame.setVisible(true);
tableFrame.setVisible(true);
```

**Why a shared list?** Both windows point to the same `List<Order>` object. When the form adds an order, the table window can see it immediately.

**Automated tests in `main()`:**
The assignment requires a `main` method that tests the system. Write tests that print results to console:

```java
// Test 1: Valid order creation
Order o1 = new Order("Paella", "Plato Principal", 2, "Domicilio", "Calle Mayor 5");
assert o1.getOrderId() == 1 : "FAIL: Order ID should be 1";
System.out.println("PASS: Order created with ID " + o1.getOrderId());

// Test 2: Validator - empty dish name
assert !Validator.validateDishName("") : "FAIL: Empty name should be invalid";
System.out.println("PASS: Empty dish name rejected");

// Test 3: Validator - valid dish name
assert Validator.validateDishName("Gazpacho") : "FAIL: Valid name rejected";
System.out.println("PASS: Valid dish name accepted");

// Test 4: Validator - invalid quantity
assert !Validator.validateQuantity("-1") : "FAIL: Negative quantity should be invalid";
System.out.println("PASS: Negative quantity rejected");

// Test 5: Validator - address required for Domicilio
assert !Validator.validateAddress("", true) : "FAIL: Empty address with Domicilio should fail";
System.out.println("PASS: Empty address with Domicilio rejected");

// Test 6: Validator - address not required for Recogida
assert Validator.validateAddress("", false) : "FAIL: Empty address with Recogida should pass";
System.out.println("PASS: Recogida with no address accepted");

// ... add more tests to cover edge cases

// Launch GUI after tests
SwingUtilities.invokeLater(() -> launchApp());
```

---

### STEP 7 — Polish and Edge Cases

**Things to double-check before considering it done:**

- [ ] The address field is disabled and blank when "Recogida" is selected.
- [ ] Pressing "Borrar" resets absolutely everything, including radio buttons.
- [ ] The order table updates immediately after confirming an order.
- [ ] Cancelling an order removes it from the list AND refreshes the table.
- [ ] Order IDs are sequential and never repeat, even after cancellations.
- [ ] The form window and table window can both be open simultaneously.
- [ ] Error labels disappear when the user starts correcting the field (optional improvement).
- [ ] Special characters like "ñ", "á", "é" are allowed in dish names.

---

### STEP 8 — Package and Deliver

**What the assignment expects:**
- A single compressed file (`.zip`) containing the complete NetBeans project.
- The `main()` method must run tests automatically.

**How to export from NetBeans:**
1. Close NetBeans.
2. Navigate to your project folder on disk.
3. Compress the entire folder (right-click → Send to → Compressed folder on Windows, or use zip on terminal).
4. The zip should contain: `src/`, `nbproject/`, `build.xml`, `manifest.mf`.

**Final check:** Unzip the file, open it in a fresh NetBeans, run it — it should work without any additional setup.

---

## Implementation Order Summary

| Order | File | Estimated Complexity |
|---|---|---|
| 1 | `Order.java` | ⭐ Easy |
| 2 | `Validator.java` | ⭐⭐ Medium |
| 3 | `OrderTableFrame.java` | ⭐⭐ Medium |
| 4 | `OrderFormPanel.java` | ⭐⭐⭐ Harder |
| 5 | `Main.java` | ⭐⭐ Medium |

Start with `Order.java` because every other file depends on it. Then `Validator.java` because you can test it without any UI. Then the table frame (simpler UI), then the form (complex UI), then wire everything in `Main.java`.

---

## Common Mistakes to Avoid

- **Don't extend `JFrame` for the form** — use `JPanel` so it can be embedded anywhere.
- **Don't use `null` layout** — Swing components won't display correctly across different screen sizes.
- **Always wrap GUI code in `SwingUtilities.invokeLater()`** — Swing is not thread-safe and must run on the Event Dispatch Thread.
- **Don't share a `DefaultTableModel` between constructors** — pass the same instance by reference.
- **Remember to call `pack()` and `setVisible(true)`** on every `JFrame` you create.
