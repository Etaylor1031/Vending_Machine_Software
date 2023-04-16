package com.techelevator.view;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.math.BigDecimal;

public class VendingMachineSalesReport {

    //declare variable
    private VendingMachine vendingMachine;
    private Map<String, Integer> salesReportInventory = new LinkedHashMap<>();
    BigDecimal totalSales = BigDecimal.valueOf(0) ; // the initial total sale is zero

    /*
         vending machine's will use Log.txt file to update
        the sales report Map with correct sales numbers. Then generates
        the "sales report .txt"  file from the sales report Map.
        */

    // Map from the vending machine's inventory Map to keep track of sales report numbers.
    public VendingMachineSalesReport(VendingMachine currentVendingMachine) {

        this.vendingMachine = currentVendingMachine;

        for (String itemCode : vendingMachine.getInventory().keySet()) {
            salesReportInventory.put(vendingMachine.getInventory().get(itemCode).getName(), 0);
        }

    }// above block  should be updated if vending machine is updated

    // this method will generate the sales report ! If file created is not found will
    // throw a filenotfound exception
    public Map<String, Integer> generateSalesReport() throws FileNotFoundException {

        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter correctDateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd_hhmmss");
        String localDateAndTimeNow = correctDateTimeFormat.format(localDateTime);

        File vendingMachineLog = new File("Log.txt");
        new File("sales-reports").mkdir();
        File vendingMachineSalesReport = new File("sales-reports/" +
                localDateAndTimeNow + "_SALES_REPORT.txt");





        try (Scanner vendingMachineLogReader = new Scanner(vendingMachineLog);
             PrintWriter salesReport = new PrintWriter(vendingMachineSalesReport)) {

            salesReport.println("****SALES REPORT****");
            salesReport.println("Generated on: " + localDateAndTimeNow);
            salesReport.println();

            while (vendingMachineLogReader.hasNextLine()) {
                String currentLine = vendingMachineLogReader.nextLine();
                Pattern itemCodePattern = Pattern.compile("[A-Z]\\d"); // Searches each line of Log.txt for item codes.
                Matcher itemCodePatternMatcher = itemCodePattern.matcher(currentLine);
                if (itemCodePatternMatcher.find()) {
                    String itemCode = itemCodePatternMatcher.group();
                    String itemName = vendingMachine.getInventory().get(itemCode).getName();
                    if (salesReportInventory.containsKey(itemName)) {
                        int quantitySold = salesReportInventory.get(itemName);
                        salesReportInventory.put(itemName, quantitySold + 1);
                        totalSales += vendingMachine.getInventory().get(itemCode).getPrice();
                    }
                }
            }
            // per the requirement "The output sales report file is also pipe-delimited for consistency"
            for (String itemName : salesReportInventory.keySet()) {
                salesReport.println(itemName + "|" + salesReportInventory.get(itemName));
            }
            //formats the total sales ouput
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            String totalSalesAsString = formatter.format(totalSales);

            salesReport.println();
            salesReport.println("Total Sales to Date: " + totalSalesAsString);
        }
        return salesReportInventory;
    }
}