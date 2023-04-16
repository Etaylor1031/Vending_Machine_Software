package com.techelevator.view;

import com.techelevator.products.Product;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class VendingMachineInterface {
    private VendingMachine vendingMachine;
    private Scanner in;
    private PrintWriter out;

    public VendingMachineInterface(InputStream input, OutputStream output) throws IOException {
        this.vendingMachine = new VendingMachine();
        this.in = new Scanner(input);
        this.out = new PrintWriter(output);
    }

    public void display() {
        out.print(vendingMachine.displayInventory());
        out.flush();
    }

    public void purchase() {
        final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
        final String PURCHASE_MENU_OPTION_FEED_SELECT_PRODUCT = "Select Product";
        final String PURCHASE_MENU_OPTION_FEED_FINISH_TRANSACTION = "Finish Transaction";
        final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_FEED_SELECT_PRODUCT,
                PURCHASE_MENU_OPTION_FEED_FINISH_TRANSACTION };
        Menu purchaseMenu = new Menu(in, out);
        while(true) {
            // Do Purchase
            out.printf("Current Money Provided: $%.2f\n", vendingMachine.readBalance());
            String purchaseChoice = (String) purchaseMenu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
            if(purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
                // Feed Money
                vendingMachine.addMoney();
            } else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_SELECT_PRODUCT)) {
                // Select Product
                String choice;
                do {
                    display();
                    choice = takeOrderFromCustomer();
                } while (choice == null);
                vendingMachine.purchaseProduct(choice);
            } else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_FINISH_TRANSACTION)) {
                // Finish Transaction
                vendingMachine.finishTransaction();
                //Return to main menu
                break;
            }
        }
    }

    /*
    Ask the Customer for Product Choice(A1-A5 or B1-B5 or C1-C5 or D1-D5)
    If the product choice(slot location) does not exist, tell the customer
    If the product is sold out, tell the customer
     */
    private String takeOrderFromCustomer(){
        // Get a product choice from the user
        out.println("Please enter product shelf to purchase:");
        String choice = in.nextLine();

        // If the product code, doesn't exist,
        // the vending machine informs the customer
        Product productVM = vendingMachine.selectProduct(choice);
        if(productVM == null) {
            out.printf("%s product code doesn't exist\n", choice);
            return null;
        }

        // If the product is sold out, tell the customer
        if(productVM.getProductCount() == 0) {
            out.printf("%s product sold out!\n", productVM.getName());
            return null;
        }

        // Print what the customer chose with name, price and product count
        out.printf("You chose %s with Price:$%.2f\n", productVM.getName(),
                productVM.getPrice());
        return choice;
    }
}
