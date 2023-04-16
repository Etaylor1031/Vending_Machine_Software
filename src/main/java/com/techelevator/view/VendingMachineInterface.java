package com.techelevator.view;

import com.techelevator.products.Product;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Locale;
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
            out.flush();
            String purchaseChoice = (String) purchaseMenu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
            if(purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
                // Feed Money
                vendingMachine.addMoney();
            } else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_SELECT_PRODUCT)) {
                // Check if we have sufficient funds in the vending machine
                // If not, then return to the purchase menu to feed money
                if(vendingMachine.readBalance().compareTo(BigDecimal.valueOf(0)) == -1 ||
                        vendingMachine.readBalance().compareTo(BigDecimal.valueOf(0)) == 0) {
                    out.println("Insufficient funds. Please feed money first");
                    continue;
                }

                // Select Product
                display();
                String choice = takeOrderFromCustomer();
                if(choice == null)
                    continue;
                
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
        out.print("Please enter product shelf to purchase: ");
        out.flush();
        String choice = in.nextLine().toUpperCase();

        Product productVM = vendingMachine.selectProduct(choice);

        // If the product code, doesn't exist,
        // the vending machine informs the customer
        if(productVM == null) {
            out.printf("%s product code doesn't exist\n", choice);
            out.flush();
            return null;
        }

        // If the product is sold out, tell the customer
        if(productVM.getProductCount() == 0) {
            out.printf("%s product sold out!\n", productVM.getName());
            out.flush();
            return null;
        }

        /*
        // Print what the customer chose with name, price and product count
        out.printf("You chose %s with Price:$%.2f\n", productVM.getName(),
                productVM.getPrice());
        out.flush();
        */

        return choice;
    }
}
