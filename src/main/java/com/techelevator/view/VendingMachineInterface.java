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
        vendingMachine.displayVendingMachine();
    }

    public void purchase() {
        vendingMachine.purchaseProduct();
    }
}
