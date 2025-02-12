package com.company;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

// The InventoryModel class handles the data and operations on the items
class InventoryModel {

    private ArrayList<Item> itemList; // A list of items in the inventory
    private Stack<String> historyStack; // A stack of actions performed on the inventory
    private Queue<Item> lowStockQueue; // A queue of items with low stock
    private LinkedList<Item> highPriceList; // A list of items with high price

    public InventoryModel() {
        itemList = new ArrayList<Item>();
        historyStack = new Stack<String>();
        lowStockQueue = new LinkedList<Item>();
        highPriceList = new LinkedList<Item>();

        // Adding some sample items to the inventory
        addItem(new Item("Laptop", 500.0, 20, "Electronics"));
        addItem(new Item("Shirt", 20.0, 50, "Clothing"));
        addItem(new Item("Book", 10.0, 100, "Stationery"));
        addItem(new Item("Phone", 300.0, 30, "Electronics"));
        addItem(new Item("Pants", 30.0, 40, "Clothing"));
        addItem(new Item("Pen", 1.0, 200, "Stationery"));
    }

    // A method to add an item to the inventory
    public void addItem(Item item) {

        itemList.add(item);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = now.format(formatter);

        historyStack.push("Added " + item.getName() + " to the inventory at " + timestamp);

        if (item.getQuantity() < 10) {
            lowStockQueue.offer(item);
        }
        if (item.getPrice() > 100) {
            highPriceList.add(item);
        }
    }

    // A method to edit an item in the inventory
    public void editItem(Item item, int index) {

        Item oldItem = itemList.get(index);

        itemList.set(index, item);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = now.format(formatter);

        historyStack.push("Edited " + oldItem.getName() + " in the inventory at " + timestamp);

        if (oldItem.getQuantity() < 10) {
            lowStockQueue.remove(oldItem);
        }
        if (oldItem.getPrice() > 100) {
            highPriceList.remove(oldItem);
        }

        if (item.getQuantity() < 10) {
            lowStockQueue.offer(item);
        }
        if (item.getPrice() > 100) {
            highPriceList.add(item);
        }
    }

    // A method to delete an item from the inventory
    public void deleteItem(int index) {

        Item item = itemList.get(index);

        itemList.remove(index);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = now.format(formatter);

        historyStack.push("Deleted " + item.getName() + " from the inventory at " + timestamp);

        if (item.getQuantity() < 10) {
            lowStockQueue.remove(item);
        }
        if (item.getPrice() > 100) {
            highPriceList.remove(item);
        }
    }
    // A method to sort the items in the queue using merge sort
    public void sortQueue() {
        // Convert the queue to an array
        Item[] array = lowStockQueue.toArray(new Item[lowStockQueue.size()]);
        // Call the mergeSort method on the array
        mergeSort(array, 0, array.length - 1);
        // Clear the queue
        lowStockQueue.clear();
        // Add the sorted elements back to the queue
        for (Item item : array) {
            lowStockQueue.offer(item);
        }
    }

    // A method to perform merge sort on an array of items
    public void mergeSort(Item[] array, int left, int right) {
        // Base case: if the subarray has only one element, return
        if (left >= right) {
            return;
        }
        // Find the middle index of the subarray
        int mid = (left + right) / 2;
        // Recursively sort the left and right halves of the subarray
        mergeSort(array, left, mid);
        mergeSort(array, mid + 1, right);
        // Merge the two sorted halves into one sorted subarray
        merge(array, left, mid, right);
    }

    // A method to merge two sorted subarrays into one sorted subarray
    public void merge(Item[] array, int left, int mid, int right) {
        // Find the sizes of the two subarrays
        int n1 = mid - left + 1;
        int n2 = right - mid;
        // Create temporary arrays to store the elements of the subarrays
        Item[] L = new Item[n1];
        Item[] R = new Item[n2];
        // Copy the elements of the subarrays into the temporary arrays
        for (int i = 0; i < n1; i++) {
            L[i] = array[left + i];
        }
        for (int j = 0; j < n2; j++) {
            R[j] = array[mid + 1 + j];
        }
        // Initialize indices for the temporary arrays and the original array
        int i = 0;
        int j = 0;
        int k = left;
        // Compare the elements of the temporary arrays and copy the smaller one to the original array
        while (i < n1 && j < n2) {
            if (L[i].getQuantity() <= R[j].getQuantity()) {
                array[k] = L[i];
                i++;
            }
            else {
                array[k] = R[j];
                j++;
            }
            k++;
        }
        // Copy any remaining elements of the left subarray to the original array
        while (i < n1) {
            array[k] = L[i];
            i++;
            k++;
        }
        // Copy any remaining elements of the right subarray to the original array
        while (j < n2) {
            array[k] = R[j];
            j++;
            k++;
        }
    }





    // A method to sort the items by a certain field and order
    public void sortItems(String field, String order) {

        Comparator<Item> comparator = new Comparator<Item>() {
            public int compare(Item i1, Item i2) {

                if (field.equals("Name")) {
                    return i1.getName().compareTo(i2.getName());
                }

                else if (field.equals("Price")) {
                    return Double.compare(i1.getPrice(), i2.getPrice());
                }

                else if (field.equals("Quantity")) {
                    return Integer.compare(i1.getQuantity(), i2.getQuantity());
                }

                else if (field.equals("Category")) {
                    return i1.getCategory().compareTo(i2.getCategory());
                }

                else {
                    return 0;
                }
            }
        };

        Collections.sort(itemList, comparator);

        if (order.equals("Descending")) {
            Collections.reverse(itemList);
        }


        historyStack.push("Sorted the inventory by " + field + " in " + order + " order");
    }
    // A method to filter the items by a certain criterion and value
    public void filterItems(String criterion, String value) {

        ArrayList<Item> filteredList = new ArrayList<Item>();

        for (Item item : itemList) {

            if (criterion.equals("Name") && item.getName().equals(value)) {

                filteredList.add(item);
            }
            else if (criterion.equals("Price")) {

                double price = Double.parseDouble(value);

                if (value.startsWith("=") && item.getPrice() == price) {

                    filteredList.add(item);
                }
                else if (value.startsWith("<") && item.getPrice() < price) {

                    filteredList.add(item);
                }
                else if (value.startsWith(">") && item.getPrice() > price) {

                    filteredList.add(item);
                }
            }
            else if (criterion.equals("Quantity")) {

                int quantity = Integer.parseInt(value);

                if (value.startsWith("=") && item.getQuantity() == quantity) {

                    filteredList.add(item);
                }
                else if (value.startsWith("<") && item.getQuantity() < quantity) {

                    filteredList.add(item);
                }
                else if (value.startsWith(">") && item.getQuantity() > quantity) {

                    filteredList.add(item);
                }
            }
            else if (criterion.equals("Category") && item.getCategory().equals(value)) {

                filteredList.add(item);
            }
        }

        historyStack.push("Filtered the inventory by " + criterion + " with value " + value);

        // Return the filtered list to the controller
        InventoryController.getInstance().updateFilter(filteredList);
    }

    // A method to view the history of changes
    public void viewHistory() {

        StringBuilder history = new StringBuilder();

        for (String action : historyStack) {

            history.append(action).append("\n");
        }

        // Return the history string to the controller
        InventoryController.getInstance().updateHistory(history.toString());
    }

    // A method to export the data to a .txt file
    public void exportData(String fileName) {
        try {

            File file = new File(fileName);

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for (Item item : itemList) {

                writer.write(item.getName() + "," + item.getPrice() + "," + item.getQuantity() + "," + item.getCategory() + "\n");
            }

            writer.close();

            // Notify the controller that the data was exported successfully
            InventoryController.getInstance().notifyExportSuccess(fileName);
        }
        catch (IOException ex) {

            // Notify the controller that there was an error exporting data
            InventoryController.getInstance().notifyExportError(ex.getMessage());
        }
    }

    // A method to import the data from a .txt file
    // A method to import the data from a .txt file
    public void importData(String fileName) {
        try {

            File file = new File(fileName);

            BufferedReader reader = new BufferedReader(new FileReader(file));

            itemList.clear();
            historyStack.clear();
            lowStockQueue.clear();
            highPriceList.clear();

            String line = reader.readLine();
            while (line != null) {

                String[] fields = line.split(",");

                String name = fields[0];
                double price = Double.parseDouble(fields[1]);
                int quantity = Integer.parseInt(fields[2]);
                String category = fields[3];

                Item item = new Item(name, price, quantity, category);

                addItem(item);

                line = reader.readLine();
            }

            reader.close();

            // Notify the controller that the data was imported successfully
            InventoryController.getInstance().notifyImportSuccess(fileName);

            // Update the view with the imported items
            InventoryController.getInstance().updateFilter(itemList);
        }
        catch (IOException ex) {

            // Notify the controller that there was an error importing data
            InventoryController.getInstance().notifyImportError(ex.getMessage());
        }
    }

    // A method to get the list of items with low stock
    public Queue<Item> getLowStockQueue() {
        return lowStockQueue;
    }

    // A method to get the list of items with high price
    public LinkedList<Item> getHighPriceList() {
        return highPriceList;
    }

    public Collection<Object> getHistoryStack() {
        return Collections.singleton(historyStack);

    }
    // A method to get the reference of the item list
    public ArrayList<Item> getItemList() {
        return itemList;
    }

    // A method to get the reference of the history stack

    }



