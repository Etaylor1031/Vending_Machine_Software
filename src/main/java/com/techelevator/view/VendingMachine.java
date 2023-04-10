package com.techelevator.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class VendingMachine{
    private double balanceDue = 5.00;
    private double customerMoney;
    private Map<String, Product> vendingMachine;

    public VendingMachine() {
        this.vendingMachine = new TreeMap<>();
    }

    private void createTransaction() {
        // Set the balance due to the item cost
        this.balanceDue = balanceDue;

        // Prompt the user for the amount tendered
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the amount tendered: ");

        // Read in the amount tendered from the user
        customerMoney = scanner.nextDouble();
    }

    public double[] returnChange() {
        // array of denominations, in descending order
        double[] denominations = {100.0, 50.0, 20.0, 10.0, 5.0, 2.0, 1.0, 0.5, 0.2, 0.1, 0.05, 0.02, 0.01};

        // array to hold the number of each denomination to return as change
        double[] change = new double[denominations.length];

        // calculate the total amount of change to be returned
        double totalChange = customerMoney - balanceDue;

        // print the total amount of change to be returned
        System.out.println("Change to be returned: " + totalChange);

        // loop through the denominations array and calculate the number of each denomination to return as change
        for (int i = 0; i < denominations.length && totalChange > 0; i++) {
            change[i] = Math.floor(totalChange / denominations[i]);
            totalChange -= change[i] * denominations[i];
        }
        // print out the number of each denomination to be returned as change
        System.out.println("Change denominations:");
        for (int i = 0; i < change.length; i++) {
            System.out.println(change[i]);
        }
        // return the array of denominations
        return change;
    }







    public void loadInventory() {
        // Create a File object using its path name
        String fileName = "vendingmachine.csv";
        File dataFile = new File(fileName);

        try(Scanner fileInput = new Scanner(dataFile)) {
            while(fileInput.hasNextLine()) {
                String lineOfText = fileInput.nextLine();
                String [] strings = lineOfText.split("\\|");
                String slotLocation = strings[0];
                String productName = strings[1];
                String price = strings[2];
                String productType = strings[3];
                int maxCount = 5;
                Product product = new Product(productName, new BigDecimal(price), productType, maxCount);
                vendingMachine.put(slotLocation, product);
            }
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void displayVendingMachine() {
        for(Map.Entry<String, Product> productEntry : vendingMachine.entrySet()) {
            System.out.printf("%s %20s %10s %10s %10s\n",productEntry.getKey(), productEntry.getValue().getName(),
                    productEntry.getValue().getPrice(), productEntry.getValue().getProductType(), productEntry.getValue().getProductCount());
        }
    }
}
