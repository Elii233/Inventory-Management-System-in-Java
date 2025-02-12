package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// The InventoryView class handles the user interface components
// The InventoryView class handles the user interface components
class InventoryView extends JFrame {

    private JLabel titleLabel; // A label for the title of the application
    private JTable itemTable; // A table to display the items
    private DefaultTableModel tableModel; // A table model to store the data of the items
    private JScrollPane tablePane; // A scroll pane to wrap the table
    private JButton addButton; // A button to add a new item
    private JButton editButton; // A button to edit an existing item
    private JButton deleteButton; // A button to delete an existing item
    private JButton sortButton; // A button to sort the items by a certain field and order
    private JButton filterButton; // A button to filter the items by a certain criterion and value
    private JButton historyButton; // A button to view the history of changes
    private JButton lowStockButton; // A button to view the items with low stock
    private JButton clearButton; // A button to clear the history of changes
    private JButton exportButton; // A button to export the data to a .txt file
    private JButton importButton; // A button to import the data from a .txt file
    private DefaultTableModel originalModel; // A table model to store the original data of the items
    private DefaultTableModel filteredModel; // A table model to store the filtered data of the items

    private JPanel mainPanel; // A panel to hold the title label and the table pane
    private JPanel buttonPanel; // A panel to hold the buttons

    public InventoryView() {

        titleLabel = new JLabel("Inventory Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(0, 0, 128)); // Dark blue color

        originalModel = new DefaultTableModel();
        originalModel.addColumn("Name");
        originalModel.addColumn("Price");
        originalModel.addColumn("Quantity");
        originalModel.addColumn("Category");

        filteredModel = new DefaultTableModel();
        filteredModel.addColumn("Name");
        filteredModel.addColumn("Price");
        filteredModel.addColumn("Quantity");
        filteredModel.addColumn("Category");

        itemTable = new JTable();
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Name");
        tableModel.addColumn("Price");
        tableModel.addColumn("Quantity");
        tableModel.addColumn("Category");
        itemTable.setModel(tableModel);
        itemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemTable.setAutoCreateRowSorter(true);

        tablePane = new JScrollPane(itemTable);
        tablePane.setPreferredSize(new Dimension(600, 300));

        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        sortButton = new JButton("Sort");
        filterButton = new JButton("Filter");
        exportButton = new JButton("Export");
        importButton = new JButton("Import");
        historyButton = new JButton("History");
        lowStockButton = new JButton("Low Stock");
        clearButton = new JButton("Clear");

        // Setting the font size and margins for the buttons
        Font buttonFont = new Font("Arial", Font.PLAIN, 16);
        Insets buttonMargin = new Insets(10, 10, 10, 10);

        addButton.setFont(buttonFont);
        addButton.setMargin(buttonMargin);

        editButton.setFont(buttonFont);
        editButton.setMargin(buttonMargin);

        deleteButton.setFont(buttonFont);
        deleteButton.setMargin(buttonMargin);

        sortButton.setFont(buttonFont);
        sortButton.setMargin(buttonMargin);

        filterButton.setFont(buttonFont);
        filterButton.setMargin(buttonMargin);

        exportButton.setFont(buttonFont);
        exportButton.setMargin(buttonMargin);

        importButton.setFont(buttonFont);
        importButton.setMargin(buttonMargin);

        historyButton.setFont(buttonFont);
        historyButton.setMargin(buttonMargin);

        lowStockButton.setFont(buttonFont);
        lowStockButton.setMargin(buttonMargin);

        clearButton.setFont(buttonFont);
        clearButton.setMargin(buttonMargin);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(tablePane, BorderLayout.CENTER);
        mainPanel.setBackground(new Color(211, 211, 211)); // Light gray color
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding around the panel

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 4));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(filterButton);
        buttonPanel.add(historyButton);
        buttonPanel.add(lowStockButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(importButton);


        this.setLayout(new BorderLayout());
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }




        // A method to get the reference of the add button
    public JButton getAddButton() {
        return addButton;
    }

    // A method to get the reference of the edit button
    public JButton getEditButton() {
        return editButton;
    }

    // A method to get the reference of the delete button
    public JButton getDeleteButton() {
        return deleteButton;
    }

    // A method to get the reference of the sort button
    public JButton getSortButton() {
        return sortButton;
    }

    // A method to get the reference of the filter button
    public JButton getFilterButton() {
        return filterButton;
    }

    // A method to get the reference of the history button
    public JButton getHistoryButton() {
        return historyButton;
    }

    // A method to get the reference of the low stock button
    public JButton getLowStockButton() {
        return lowStockButton;
    }

    // A method to get the reference of the clear button
    public JButton getClearButton() {
        return clearButton;
    }

    // A method to get the reference of the export button
    public JButton getExportButton() {
        return exportButton;
    }

    // A method to get the reference of the import button
    public JButton getImportButton() {
        return importButton;
    }

    // A method to get the reference of the table model
    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    // A method to get the reference of the item table
    public JTable getItemTable() {
        return itemTable;
    }
    // A method to get the reference of the table model
    public DefaultTableModel getFilteredModel() {
        return tableModel;
    }
    // A method to get the reference of the table model
    public DefaultTableModel getOriginalModel() {
        return tableModel;
    }
}

    class Item {

        private String name; // The name of the item
        private double price; // The price of the item
        private int quantity; // The quantity of the item
        private String category; // The category of the item

        public Item(String name, double price, int quantity, String category) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
            this.category = category;
        }

        // Getters and setters for the fields
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

    }




