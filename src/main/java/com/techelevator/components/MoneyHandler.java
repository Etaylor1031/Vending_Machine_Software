package com.techelevator.components;

import java.math.BigDecimal;
import java.util.Scanner;

public class MoneyHandler {
    private BigDecimal balance;
    private BigDecimal customerMoney;
    private static final BigDecimal DEFAULT_ZERO = BigDecimal.valueOf(0);

    public MoneyHandler() {
        this.balance = DEFAULT_ZERO;
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

    public double[] returnChange() {
        // array of denominations, in descending order
        double[] denominations = {0.25, 0.10, 0.05};

        // array to hold the number of each denomination to return as change
        double[] change = new double[denominations.length];

        // calculate the total amount of change to be returned
        double remainingMoney = getBalance().doubleValue();

        // print the total amount of change to be returned
        System.out.println("Change to be returned: " + remainingMoney);

        // loop through the denominations array and calculate the number of each denomination to return as change
        for (int i = 0; i < denominations.length && remainingMoney > 0; i++) {
            change[i] = Math.floor(remainingMoney / denominations[i]);
            remainingMoney -= change[i] * denominations[i];
        }

        // print out the number of each denomination to be returned as change
        System.out.println("Change denominations:");
        for (int i = 0; i < change.length; i++) {
            System.out.println(change[i]);
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
