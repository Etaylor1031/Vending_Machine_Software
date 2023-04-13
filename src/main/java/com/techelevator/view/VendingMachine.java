package com.techelevator.view;

import com.techelevator.components.MoneyHandler;
import com.techelevator.components.ProductRack;
import com.techelevator.products.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class VendingMachine{
    private MoneyHandler moneyHandler;
    private ProductRack productRack;



    public VendingMachine() {

        // Create a File object using its path name
        String fileName = "vendingmachine.csv";
        File dataFile = new File(fileName);

        /*
         Read from the file
         Create a product object from the data in the file
         Put this product along with its slot location in a Map
         */
        final int SLOT_LOCATION = 0;
        Map<String, Product> freshRack = new TreeMap<String, Product>();

        try(Scanner fileInput = new Scanner(dataFile)) {
            while(fileInput.hasNextLine()) {
                String lineOfText = fileInput.nextLine();
                String [] productDetails = lineOfText.split("\\|");
                Product product = new Product(productDetails);
                freshRack.put(productDetails[SLOT_LOCATION], product);
            }
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        this.productRack = new ProductRack(freshRack);
    }

    /*
    Displays the Vending Machine to the User
     */
    public String displayVendingMachine() {
        return productRack.toString();
    }

    /*
    Ask the Customer for Product Choice(A1-A5 or B1-B5 or C1-C5 or D1-D5)
    If the product choice(slot location) does not exist, tell the customer
    If the product is sold out, tell the customer
     */
    public String takeOrderFromCustomer(){
        // Get a product choice from the user
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter product shelf to purchase:");
        String choice = sc.nextLine();

        // If the product code, doesn't exist,
        // the vending machine informs the customer
        Product productVM = vendingMachine.get(choice);
        if(productVM == null) {
            System.out.printf("%s product code doesn't exist\n", choice);
            return null;
        }

        // If the product is sold out, tell the customer
        if(productVM.getProductCount() == 0) {
            System.out.printf("%s product sold out!\n", productVM.getName());
            return null;
        }

        // Print what the customer chose with name, price and product count
        System.out.printf("You chose %s with Price:$%.2f and Item Remaining:%d \n", productVM.getName(), productVM.getPrice(), productVM.getProductCount());
        return choice;
    }

    public void selectProduct() {
        // Display the Vending Machine Products
        displayVendingMachine();

        // If the choice doesn't exist, return
        String productChoice = takeOrderFromCustomer();
        if(productChoice == null)
            return;

        // Dispense the product
        productRack.dispenseProduct(productChoice);
    }

    public void finishTransaction() {
        moneyHandler.returnChange();
    }

    public void purchaseProduct() {

    }

    public BigDecimal readBalance() {
        return moneyHandler.getBalance();
    }

    public void addMoney() {
        moneyHandler.feedMoney();
    }
}
