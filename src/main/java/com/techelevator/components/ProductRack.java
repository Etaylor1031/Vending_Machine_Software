package com.techelevator.components;

import com.techelevator.products.Product;

import java.util.Map;
import java.util.TreeMap;

public class ProductRack {
    private Map<String, Product> vendingMachine;
    private static final int  MAXIMUM_QUANTITY_AVAILABLE = 5;

    public ProductRack() {
        this.vendingMachine = new TreeMap<>();
    }

    /*
        Dispense a product will update the balance and productCount
        It will also print the item name, price, and balance
        Dispensing also returns a message:
            All chip items print "Crunch Crunch, Yum!"
            All candy items print "Munch Munch, Yum!"
            All drink items print "Glug Glug, Yum!"
            All gum items print "Chew Chew, Yum!"
         */
    public String dispenseProduct(String productChoice) {
        // Update balance
        Product productVM = vendingMachine.get(productChoice);
        balance = balance.subtract(productVM.getPrice());

        // Update productCount
        productVM.decrementProductCount();

        // Print the item name, price, and balance
        System.out.printf("Item Name:%s Price:%.2f Balance:%.2f\n", productVM.getName(), productVM.getPrice(), balance);

        String message = "";
        // Assign the message of productType(Chip, Candy, Drink, Gum)
        if(productVM.getProductType().equals("Chip")) {
            message = "Crunch Crunch, Yum!";
        } else if(productVM.getProductType().equals("Candy")) {
            message = "Munch Munch, Yum!";
        } else if(productVM.getProductType().equals("Drink")) {
            message = "Glug glug, Yum!";
        } else if(productVM.getProductType().equals("Gum")) {
            message = "Chew chew, Yum!";
        }

        System.out.println(message);
        return message;
    }
}
