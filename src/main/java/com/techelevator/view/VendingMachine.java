package com.techelevator.view;

import com.techelevator.components.MoneyHandler;
import com.techelevator.components.ProductRack;
import com.techelevator.products.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;

public class VendingMachine{
    private MoneyHandler moneyHandler = new MoneyHandler();
    private ProductRack productRack = new ProductRack();

    private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
    private static final String PURCHASE_MENU_OPTION_FEED_SELECT_PRODUCT = "Select Product";
    private static final String PURCHASE_MENU_OPTION_FEED_FINISH_TRANSACTION = "Finish Transaction";
    private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_FEED_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FEED_FINISH_TRANSACTION };

    public VendingMachine() {

    }

    /*
    Restock the Vending Machine
     */
    public void loadInventory() {
        // Create a File object using its path name
        String fileName = "vendingmachine.csv";
        File dataFile = new File(fileName);

        /*
         Read from the file
         Create a product object from the data in the file
         Put this product along with its slot location in a Map
         */
        try(Scanner fileInput = new Scanner(dataFile)) {
            while(fileInput.hasNextLine()) {
                String lineOfText = fileInput.nextLine();
                String [] strings = lineOfText.split("\\|");
                String slotLocation = strings[0];
                String productName = strings[1];
                String price = strings[2];
                String productType = strings[3];
                Product product = new Product(productName, new BigDecimal(price), productType, MAXIMUM_QUANTITY_AVAILABLE);
                vendingMachine.put(slotLocation, product);
            }
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /*
    Displays the Vending Machine to the User
     */
    public void displayVendingMachine() {
        // Iterate through the map to for printing
        for(Map.Entry<String, Product> productEntry : vendingMachine.entrySet()) {
            System.out.printf("%s %20s %10s %10s %10s\n",productEntry.getKey(), productEntry.getValue().getName(),
                    productEntry.getValue().getPrice(), productEntry.getValue().getProductType(), productEntry.getValue().getProductCount());
        }
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

    public void purchaseProduct() {
        Menu purchaseMenu = new Menu(System.in, System.out);
        while(true) {
            // Do Purchase
            System.out.printf("Current Money Provided: $%.2f\n", moneyHandler.getBalance());
            String purchaseChoice = (String) purchaseMenu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
            if(purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
                // Feed Money
                moneyHandler.feedMoney();
            } else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_SELECT_PRODUCT)) {
                // Select Product
                selectProduct();
            } else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_FINISH_TRANSACTION)) {
                // Finish Transaction
                moneyHandler.returnChange();
                //Return to main menu
                break;
            }
        }
    }
}
