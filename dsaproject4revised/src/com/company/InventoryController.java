    package com.company;

    import javax.swing.*;
    import javax.swing.table.DefaultTableModel;
    import javax.swing.table.TableRowSorter;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.util.ArrayList;

    // The InventoryController class handles the communication between the model and the view
    class InventoryController {

        private InventoryModel model; // A reference to the model
        private InventoryView view; // A reference to the view
        private static InventoryController instance; // A singleton instance of the controller
        private TableRowSorter<DefaultTableModel> rowSorter;

        private InventoryController() {
            model = new InventoryModel();
            view = new InventoryView();
            rowSorter = new TableRowSorter<DefaultTableModel>(view.getTableModel());

            view.getItemTable().setRowSorter(rowSorter);
            // Registering the action listeners for the buttons
            view.getAddButton().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    handleAdd();
                }
            });

            view.getEditButton().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    handleEdit();
                }
            });

            view.getDeleteButton().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    handleDelete();
                }
            });

            view.getSortButton().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    handleSort();
                }
            });

            view.getFilterButton().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    handleFilter();
                }
            });

            view.getHistoryButton().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    handleHistory();
                }
            });

            view.getLowStockButton().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    handleLowStock();
                }
            });

            view.getClearButton().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    handleClear();
                }
            });

            view.getExportButton().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    handleExport();
                }
            });

            view.getImportButton().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    handleImport();
                }
            });
        }

        // A method to get the singleton instance of the controller
        public static InventoryController getInstance() {
            if (instance == null) {
                instance = new InventoryController();
            }
            return instance;
        }

        // A method to handle the add button action
        public void handleAdd() {

            JLabel[] labels = new JLabel[] {
                    new JLabel("Name:"),
                    new JLabel("Price:"),
                    new JLabel("Quantity:"),
                    new JLabel("Category:")
            };

            JTextField[] fields = new JTextField[] {
                    new JTextField(),
                    new JTextField(),
                    new JTextField(),
                    new JTextField()
            };

            Object[] message = new Object[labels.length + fields.length];

            for (int i = 0; i < labels.length; i++) {
                message[i * 2] = labels[i];
                message[i * 2 + 1] = fields[i];
            }

            int option = JOptionPane.showConfirmDialog(view, message, "Add a New Item", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                try {

                    String name = fields[0].getText();
                    double price = Double.parseDouble(fields[1].getText());
                    int quantity = Integer.parseInt(fields[2].getText());
                    String category = fields[3].getText();

                    Item item = new Item(name, price, quantity, category);

                    // Calling the model to add the item
                    model.addItem(item);

                    // Updating the view with the new item
                    view.getTableModel().addRow(new Object[] {item.getName(), item.getPrice(), item.getQuantity(), item.getCategory()});

                    JOptionPane.showMessageDialog(view, "Item added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(view, "Invalid input. Please enter valid values for price and quantity", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        // A method to handle the edit button action
        public void handleEdit() {

            int index = view.getItemTable().getSelectedRow();

            if (index != -1) {

                Item item = model.getItemList().get(index);

                JLabel[] labels = new JLabel[] {
                        new JLabel("Name:"),
                        new JLabel("Price:"),
                        new JLabel("Quantity:"),
                        new JLabel("Category:")
                };

                JTextField[] fields = new JTextField[] {
                        new JTextField(item.getName()),
                        new JTextField(String.valueOf(item.getPrice())),
                        new JTextField(String.valueOf(item.getQuantity())),
                        new JTextField(item.getCategory())
                };

                Object[] message = new Object[labels.length + fields.length];

                for (int i = 0; i < labels.length; i++) {
                    message[i * 2] = labels[i];
                    message[i * 2 + 1] = fields[i];
                }

                int option = JOptionPane.showConfirmDialog(view, message, "Edit an Existing Item", JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION) {
                    try {

                        String name = fields[0].getText();
                        double price = Double.parseDouble(fields[1].getText());
                        int quantity = Integer.parseInt(fields[2].getText());
                        String category = fields[3].getText();

                        Item newItem = new Item(name, price, quantity, category);

                        // Calling the model to edit the item
                        model.editItem(newItem, index);

                        // Updating the view with the edited item
                        view.getTableModel().setValueAt(newItem.getName(), index, 0);
                        view.getTableModel().setValueAt(newItem.getPrice(), index, 1);
                        view.getTableModel().setValueAt(newItem.getQuantity(), index, 2);
                        view.getTableModel().setValueAt(newItem.getCategory(), index, 3);

                        JOptionPane.showMessageDialog(view, "Item edited successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                    catch (NumberFormatException ex) {

                        JOptionPane.showMessageDialog(view, "Invalid input. Please enter valid values for price and quantity", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            else {

                JOptionPane.showMessageDialog(view, "Please select a row to edit", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
        // A method to handle the delete button action
        public void handleDelete() {

            int index = view.getItemTable().getSelectedRow();

            if (index != -1) {

                Item item = model.getItemList().get(index);

                int option = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete " + item.getName() + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {

                    // Calling the model to delete the item
                    model.deleteItem(index);

                    // Updating the view by removing the item
                    view.getTableModel().removeRow(index);

                    JOptionPane.showMessageDialog(view, "Item deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else {

                JOptionPane.showMessageDialog(view, "Please select a row to delete", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }

        // A method to handle the sort button action
        public void handleSort() {

            String[] fields = new String[] {"Name", "Price", "Quantity", "Category"};

            JComboBox<String> fieldBox = new JComboBox<String>(fields);
            fieldBox.setSelectedItem("Name");

            String[] orders = new String[] {"Ascending", "Descending"};

            ButtonGroup orderGroup = new ButtonGroup();
            JRadioButton ascendingButton = new JRadioButton("Ascending");
            ascendingButton.setSelected(true);
            JRadioButton descendingButton = new JRadioButton("Descending");
            orderGroup.add(ascendingButton);
            orderGroup.add(descendingButton);

            Object[] message = new Object[] {
                    new JLabel("Field:"),
                    fieldBox,
                    new JLabel("Order:"),
                    ascendingButton,
                    descendingButton
            };

            int option = JOptionPane.showConfirmDialog(view, message, "Sort Items by a Certain Field", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {

                String field = (String) fieldBox.getSelectedItem();
                String order = ascendingButton.isSelected() ? "Ascending" : "Descending";

                // Calling the model to sort the items
                model.sortItems(field, order);

                // Updating the view with the sorted items
                view.getTableModel().setRowCount(0);
                for (Item item : model.getItemList()) {
                    view.getTableModel().addRow(new Object[] {item.getName(), item.getPrice(), item.getQuantity(), item.getCategory()});
                }

                JOptionPane.showMessageDialog(view, "Items sorted successfully by " + field + " in " + order + " order", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        // A method to handle the filter button action
        // A method to handle the filter button action
        // A method to handle the filter button action
        public void handleFilter() {

            String[] criteria = new String[] {"Name", "Price", "Quantity", "Category"};

            JComboBox<String> criterionBox = new JComboBox<String>(criteria);
            criterionBox.setSelectedItem("Name");

            JTextField valueField = new JTextField();

            Object[] message = new Object[] {
                    new JLabel("Criterion:"),
                    criterionBox,
                    new JLabel("Value:"),
                    valueField
            };

            int option = JOptionPane.showConfirmDialog(view, message, "Filter Items by a Certain Criterion", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                try {

                    String criterion = (String) criterionBox.getSelectedItem();
                    String value = valueField.getText();
                    view.getItemTable().setModel(view.getFilteredModel());

                    // Calling the model to filter the items
                    model.filterItems(criterion, value);

                    // Set the row filter based on the criterion and value
                    switch (criterion) {
                        case "Name":
                            // Match any row that contains the value in column 0 (name)
                            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + value, 0));
                            break;
                        case "Price":
                            // Match any row that satisfies the comparison in column 1 (price)
                            double price = Double.parseDouble(value);
                            if (value.startsWith("=")) {
                                rowSorter.setRowFilter(RowFilter.numberFilter(RowFilter.ComparisonType.EQUAL, price, 1));
                            }
                            else if (value.startsWith("<")) {
                                rowSorter.setRowFilter(RowFilter.numberFilter(RowFilter.ComparisonType.BEFORE, price, 1));
                            }
                            else if (value.startsWith(">")) {
                                rowSorter.setRowFilter(RowFilter.numberFilter(RowFilter.ComparisonType.AFTER, price, 1));
                            }
                            break;
                        case "Quantity":
                            // Match any row that satisfies the comparison in column 2 (quantity)
                            int quantity = Integer.parseInt(value);
                            if (value.startsWith("=")) {
                                rowSorter.setRowFilter(RowFilter.numberFilter(RowFilter.ComparisonType.EQUAL, quantity, 2));
                            }
                            else if (value.startsWith("<")) {
                                rowSorter.setRowFilter(RowFilter.numberFilter(RowFilter.ComparisonType.BEFORE, quantity, 2));
                            }
                            else if (value.startsWith(">")) {
                                rowSorter.setRowFilter(RowFilter.numberFilter(RowFilter.ComparisonType.AFTER, quantity, 2));
                            }
                            break;
                        case "Category":
                            // Match any row that contains the value in column 3 (category)
                            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + value, 3));
                            break;
                        default:
                            // No filter
                            rowSorter.setRowFilter(null);
                    }

                    JOptionPane.showMessageDialog(view, "Items filtered successfully by " + criterion + " with value " + value, "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(view, "Invalid input. Please enter valid values for price and quantity", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                // If the user cancels the filter dialog, set the original model to the table
                view.getItemTable().setModel(view.getOriginalModel());
            }
        }





        // A method to handle the history button action
        public void handleHistory() {

            // Calling the model to view the history of changes
            model.viewHistory();

            // The view will be updated by the updateHistory method
        }

        // A method to handle the low stock button action
        public void handleLowStock() {

            StringBuilder lowStock = new StringBuilder();

            for (Item item : model.getLowStockQueue()) {
                lowStock.append(item.getName()).append(": ").append(item.getQuantity()).append("\n");
            }

            JTextArea lowStockArea = new JTextArea(lowStock.toString());
            lowStockArea.setEditable(false);

            JScrollPane lowStockPane = new JScrollPane(lowStockArea);
            lowStockPane.setPreferredSize(new Dimension(200, 100));

            JOptionPane.showMessageDialog(view, lowStockPane, "Items with Low Stock", JOptionPane.INFORMATION_MESSAGE);
        }

        // A method to handle the clear button action
        public void handleClear() {

            // Calling the model to clear the history of changes
            model.getHistoryStack().clear();

            JOptionPane.showMessageDialog(view, "History cleared successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        }

        // A method to handle the export button action
        public void handleExport() {

            JTextField fileNameField = new JTextField();

            Object[] message = new Object[] {
                    new JLabel("File Name:"),
                    fileNameField
            };

            int option = JOptionPane.showConfirmDialog(view, message, "Export Data to a .txt File", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {

                String fileName = fileNameField.getText();

                // Calling the model to export the data to a file
                model.exportData(fileName);

                // The view will be notified by the notifyExportSuccess or notifyExportError methods
            }
        }

        // A method to handle the import button action
        public void handleImport() {

            JTextField fileNameField = new JTextField();

            Object[] message = new Object[] {
                    new JLabel("File Name:"),
                    fileNameField
            };

            int option = JOptionPane.showConfirmDialog(view, message, "Import Data from a .txt File", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {

                String fileName = fileNameField.getText();

                // Calling the model to import the data from a file
                model.importData(fileName);

                // The view will be notified by the notifyImportSuccess or notifyImportError methods
            }
        }

        // A method to update the view with the filtered items
        public void updateFilter(ArrayList<Item> filteredList) {

            view.getTableModel().setRowCount(0);

            for (Item item : filteredList) {
                view.getTableModel().addRow(new Object[] {item.getName(), item.getPrice(), item.getQuantity(), item.getCategory()});
            }
        }

        // A method to update the view with the history of changes
        public void updateHistory(String history) {

            JTextArea historyArea = new JTextArea(history);
            historyArea.setEditable(false);

            JScrollPane historyPane = new JScrollPane(historyArea);
            historyPane.setPreferredSize(new Dimension(400, 200));

            JOptionPane.showMessageDialog(view, historyPane, "History of Changes", JOptionPane.INFORMATION_MESSAGE);
        }

        // A method to notify the view that the data was exported successfully
        public void notifyExportSuccess(String fileName) {

            JOptionPane.showMessageDialog(view, "Data exported successfully to " + fileName, "Success", JOptionPane.INFORMATION_MESSAGE);
        }

        // A method to notify the view that there was an error exporting data
        public void notifyExportError(String message) {

            JOptionPane.showMessageDialog(view, "Error exporting data: " + message, "Error", JOptionPane.ERROR_MESSAGE);
        }

        // A method to notify the view that the data was imported successfully
        public void notifyImportSuccess(String fileName) {

            JOptionPane.showMessageDialog(view, "Data imported successfully from " + fileName, "Success", JOptionPane.INFORMATION_MESSAGE);
        }

        // A method to notify the view that there was an error importing data
        public void notifyImportError(String message) {

            JOptionPane.showMessageDialog(view, "Error importing data: " + message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
