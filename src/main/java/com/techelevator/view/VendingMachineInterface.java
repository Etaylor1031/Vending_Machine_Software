package com.techelevator.view;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class VendingMachineInterface {
    private VendingMachine vendingMachine;
    private Scanner in;
    private PrintWriter out;

    public VendingMachineInterface(InputStream input, OutputStream output) {
        this.vendingMachine = new VendingMachine();
        this.in = new Scanner(input);
        this.out = new PrintWriter(output);
    }

    public void display() {
        out.print(vendingMachine.displayVendingMachine());
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
                display();

                vendingMachine.selectProduct();
            } else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_FINISH_TRANSACTION)) {
                // Finish Transaction
                vendingMachine.finishTransaction();
                //Return to main menu
                break;
            }
        }
        vendingMachine.purchaseProduct();
    }
}
