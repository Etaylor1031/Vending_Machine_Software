package com.techelevator.view;

import com.techelevator.products.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class VendingMachine{
    private Map<String, Product> vendingMachine;
    private BigDecimal balance;
    private BigDecimal customerMoney;
    private static final int  MAXIMUM_QUANTITY_AVAILABLE = 5;
    private static final BigDecimal DEFAULT_ZERO = BigDecimal.valueOf(0);
    private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
    private static final String PURCHASE_MENU_OPTION_FEED_SELECT_PRODUCT = "Select Product";
    private static final String PURCHASE_MENU_OPTION_FEED_FINISH_TRANSACTION = "Finish Transaction";
    private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_FEED_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FEED_FINISH_TRANSACTION };

    public VendingMachine() {
        this.vendingMachine = new TreeMap<>();
        this.balance = DEFAULT_ZERO;
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

    /*
    Ask the customer to repeatedly feed money into the vending machine
     */
    public void feedMoney() {
        String answer = "Y";
        do {
            // Ask the user how much money to feed and print out the balance
            Scanner sc = new Scanner(System.in);
            System.out.println("How much money you want to feed?");
            String feedMoney = sc.nextLine();
            balance = balance.add(new BigDecimal(feedMoney));
            System.out.printf("Current Money Provided: %.2f\n", balance);

            // Ask the user if he/she wants to continue feeding
            System.out.println("Continue feeding money?(Y/N)");
            answer = sc.nextLine();
        } while(answer.equals("Y"));
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

    public void selectProduct() {
        // Display the Vending Machine Products
        displayVendingMachine();

        // If the choice doesn't exist, return
        String productChoice = takeOrderFromCustomer();
        if(productChoice == null)
            return;

        // Dispense the product
        dispenseProduct(productChoice);
    }

    public double[] returnChange() {
        // array of denominations, in descending order
        double[] denominations = {100.00, 50.00, 20.00, 10.00, 5.00, 1.00, 0.25, 0.10, 0.05, 0.01};

        // array to hold the number of each denomination to return as change
        double[] change = new double[denominations.length];

        // calculate the total amount of change to be returned
        double totalChange = customerMoney.subtract(balance).doubleValue();

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

    public void finishTransaction() {
        // Return the change to the customer
        returnChange();

        // Set the balance to default
        balance = DEFAULT_ZERO;

        // Finish the transaction
        System.exit(0);
    }

    public void purchaseProduct() {
        Menu purchaseMenu = new Menu(System.in, System.out);
        while(true) {
            // Do Purchase
            System.out.printf("Current Money Provided: $%.2f\n", balance);
            String purchaseChoice = (String) purchaseMenu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
            if(purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
                // Feed Money
                feedMoney();
            } else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_SELECT_PRODUCT)) {
                // Select Product
                selectProduct();
            } else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_FINISH_TRANSACTION)) {
                // Finish Transaction
                finishTransaction();
            }
        }
    }
}
