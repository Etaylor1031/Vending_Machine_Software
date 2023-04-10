package com.techelevator.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class VendingMachine{
    private Map<String, Product> vendingMachine;

    public VendingMachine() {
        this.vendingMachine = new TreeMap<>();
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
