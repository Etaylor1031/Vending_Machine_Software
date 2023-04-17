package com.techelevator.components;

import com.techelevator.products.Product;

import java.math.BigDecimal;
import java.util.Map;

public class ProductRack {
    private Map<String, Product> inventory;


    public ProductRack(Map<String, Product> rackInventory) {
        this.inventory = rackInventory;
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
    public BigDecimal dispenseProduct(String productChoice, BigDecimal balance) {
        // Update balance
        Product productVM = inventory.get(productChoice);
        BigDecimal newBalance = balance.subtract(productVM.getPrice());

        // If we don't have enough balance to make the purchase, then tell the customer he/she doesn't have enough funds
        // Return the balance and to the purchase
        if(newBalance.compareTo(BigDecimal.valueOf(0)) == 0 ||
           newBalance.compareTo(BigDecimal.valueOf(0)) == -1  ) {
            System.out.println("Insufficient funds. Please feed money first");
            return balance;
        }

        // Update productCount
        productVM.decrementProductCount();

        // Print the item name, price, and balance
        System.out.printf("Dispensed %s with a Price of $%.2f. Current Money Remaining is $%.2f\n", productVM.getName(),
                productVM.getPrice(), newBalance);
        productVM.printSound();

        return newBalance;
    }

    public Product getPurchasedProduct(String choice) {
        return inventory.get(choice);
    }

    @Override
    public String toString() {
        final String SLOT_LOCATION = "SLOT LOCATION";
        final String ITEM_NAME = "ITEM NAME";
        final String PRICE = "PRICE";
        final String QUANTITY_REMAINING = "QUANTITY REMAINING";

        String productListings = "--------------------------------------------------------------------\n";
        productListings += String.format("%-15s %-20s %10s %20s\n", SLOT_LOCATION, ITEM_NAME, PRICE, QUANTITY_REMAINING);
        productListings += "--------------------------------------------------------------------\n";
        // Iterate through the map and organize the data into a formatted String for displaying to user
        for(Map.Entry<String, Product> productEntry : inventory.entrySet()) {
            productListings += String.format("%-15s %-20s %10s %20s\n",productEntry.getKey(),
                    productEntry.getValue().getName(), productEntry.getValue().getPrice(),
                    productEntry.getValue().getProductCount() == 0 ? "SOLD OUT" : productEntry.getValue().getProductCount());
            // Prints SOLD OUT if productCount is 0
        }
        productListings += "--------------------------------------------------------------------\n";
        return productListings;
    }
}
