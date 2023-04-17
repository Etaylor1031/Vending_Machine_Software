package com.techelevator.view;

import com.techelevator.products.Product;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class VendingMachineInterface {
    private VendingMachine vendingMachine;
    private Scanner in;
    private PrintWriter out;
    private static PrintWriter writerSales = null;

    public VendingMachineInterface(InputStream input, OutputStream output){
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
                // Return to main menu
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

    public void salesReport() {
        if(writerSales == null) {
            try {
                String salesFileName = "sales/sales-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_hh-mm-ss_a")) + ".txt";
                File salesFile = new File(salesFileName);
                try {
                    if(!salesFile.exists())
                        salesFile.createNewFile();
                } catch(IOException e) {
                    throw new VendingMachineInterfaceException("Run Time Error: " + e.getMessage());
                }

                writerSales = new PrintWriter(new FileOutputStream(salesFileName));
            } catch(FileNotFoundException e) {
                throw new VendingMachineInterfaceException("Run Time Error: " + e.getMessage());
            }
        }

        try {
            final int MAXIMUM_QUANTITY_AVAILABLE = 5;
            for(Map.Entry<String, Product> productEntry : vendingMachine.getProductRack().getInventory().entrySet()) {
                writerSales.println(productEntry.getValue().getName() + "|" + (MAXIMUM_QUANTITY_AVAILABLE - productEntry.getValue().getProductCount()));
            }
            writerSales.printf("\n** TOTAL SALES** $%s\n\n", vendingMachine.getProductRack().getSales());
            writerSales.flush();
        } catch (VendingMachineInterfaceException e) {
            throw new VendingMachineInterfaceException("Run Time Error: " + e.getMessage());
        }
    }
}
