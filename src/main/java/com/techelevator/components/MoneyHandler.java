package com.techelevator.components;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Set;

public class MoneyHandler {
    private BigDecimal balance;
    private static final BigDecimal DEFAULT_ZERO = BigDecimal.valueOf(0);
    private static final Set<String> PAPER_BILLS = Set.of("1","2","5","10","20");
    private static final String[] COINS = {"Quarters", "Dimes", "Nickels"};
    private static final double[] DENOMINATIONS = {0.25, 0.10, 0.05};   // array of denominations, in descending order

    public MoneyHandler() {
        this.balance = DEFAULT_ZERO;
    }

    public boolean validateInputMoney(String sVal) {
        if(!PAPER_BILLS.contains(sVal)) {
            System.out.println("Please only put $1, 2, 5, 10, 20 bills.");
            return false;
        }

        return true;
    }

    /*
        Ask the customer to repeatedly feed money into the vending machine
         */
    public void feedMoney() {
        String answer = "Y";
        do {
            // Ask the user how much money to feed and print out the balance
            Scanner sc = new Scanner(System.in);
            System.out.print("How much money($1, 2, 5, 10, 20) you want to feed? ");
            String feedMoney = sc.nextLine();

            // If the feedMoney is not 1 or 2 or 5 or 10 or 20, ask the user again to feed money
            if(validateInputMoney(feedMoney) == false)
                continue;

            balance = balance.add(new BigDecimal(feedMoney));
            System.out.printf("Current Money Provided: $%.2f\n", balance);

            do {
                // Ask the user if he/she wants to continue feeding
                System.out.print("Continue feeding money?(Y/N) ");
                answer = sc.nextLine();
            } while(!answer.equalsIgnoreCase("Y") && !answer.equalsIgnoreCase("N"));
        } while(answer.equalsIgnoreCase("Y"));
    }

    public int[] returnChange() {
        // array to hold the number of each denomination to return as change
        int[] change = new int[DENOMINATIONS.length];

        // calculate the total amount of change to be returned
        double remainingMoney = getBalance().doubleValue();

        // print the total amount of change to be returned
        System.out.println("Change to be returned: $" + remainingMoney);

        // loop through the denominations array and calculate the number of each denomination to return as change
        for (int i = 0; i < DENOMINATIONS.length && remainingMoney > 0; i++) {
            change[i] = (int) Math.floor(remainingMoney / DENOMINATIONS[i]);
            remainingMoney -= change[i] * DENOMINATIONS[i];
        }

        // print out the number of each denomination to be returned as change
        System.out.println("Change denominations:");
        for (int i = 0; i < change.length; i++) {
            System.out.println(change[i] + " " + COINS[i]);
        }
        //reset the balance
        balance = DEFAULT_ZERO;
        // return the array of denominations
        return change;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
