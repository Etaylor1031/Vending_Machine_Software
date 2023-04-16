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
import javax.swing.text.NumberFormatter;
import java.io.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;


public class VendingMachine{
    private MoneyHandler moneyHandler;
    private ProductRack productRack;
    private Map<String, Product> inventory = new LinkedHashMap<>();



    public VendingMachine() throws IOException {

        //Date and time format for the log.txt file per the requirements in the readme
        DateTimeFormatter timeFormatterForLog = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String timeLogger = timeFormatterForLog.format(LocalDateTime.now());

        File log = new File("Log.txt");
        FileWriter fw= new FileWriter(log, true);
        PrintWriter logWriter = new PrintWriter(fw);// logWriter logs transactions and inventory


        // Create a File object using its path name
        String fileName = "vendingmachine.csv";
        File dataFile = new File(fileName);


        /*
         Read from the file
         Create a product object from the data in the file
         Put this product along with its slot location in a Map
         */
        final int SLOT_LOCATION = 0;
        Map<String, Product> rackInventory = new TreeMap<String, Product>();

        try(Scanner fileInput = new Scanner(dataFile)) {
            while(fileInput.hasNextLine()) {
                String lineOfText = fileInput.nextLine();
                String [] productDetails = lineOfText.split("\\|");
                Product product = new Product(productDetails);
                rackInventory.put(productDetails[SLOT_LOCATION], product);
            }
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        this.productRack = new ProductRack(rackInventory);
        this.moneyHandler = new MoneyHandler();
    }
    //This map is needed for the vendingmachineSalesreport file
    public Map<String, Product> getInventory() {
        return inventory;
    }

    /*
    Displays the Vending Machine to the User
     */
    public String displayInventory() {
        return productRack.toString();
    }

    public Product selectProduct(String choice) {
        return productRack.getPurchasedProduct(choice);
    }

    public void finishTransaction() {
        moneyHandler.returnChange();
    }

    public void purchaseProduct(String productChoice) {
        // Dispense the product
        moneyHandler.setBalance(productRack.dispenseProduct(productChoice, moneyHandler.getBalance()));
    }

    public BigDecimal readBalance() {
        return moneyHandler.getBalance();
    }

    public void addMoney() {
        moneyHandler.feedMoney();
    }
}
