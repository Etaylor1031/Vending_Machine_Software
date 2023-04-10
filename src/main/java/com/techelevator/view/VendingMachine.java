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

    public void transaction() {
        createTransaction();
    }
    private void createTransaction() {
        this.balanceDue = balanceDue;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the amount tendered: ");
        customerMoney = scanner.nextDouble();
    }

    public double[] returnChange() {
        double[] denominations = {100.0, 50.0, 20.0, 10.0, 5.0, 2.0, 1.0, 0.5, 0.2, 0.1, 0.05, 0.02, 0.01};
        double[] change = new double[denominations.length];
        double totalChange = customerMoney - balanceDue;
        System.out.println("Change to be returned: " + totalChange);
        for (int i = 0; i < denominations.length && totalChange > 0; i++) {
            change[i] = Math.floor(totalChange / denominations[i]);
            totalChange -= change[i] * denominations[i];
        }
        System.out.println("Change denominations:");
        for (int i = 0; i < change.length; i++) {
            System.out.println(change[i]);
        }
        return change;
    }

    public static void main(String[] args) {
        VendingMachine vm = new VendingMachine();
        double[] change = vm.returnChange();
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
