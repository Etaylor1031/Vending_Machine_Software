package com.techelevator.view;

public class VendingMachineInterface {
    private VendingMachine vendingMachine;

    public VendingMachineInterface() {
        this.vendingMachine = new VendingMachine();
    }

    public void load() {
        vendingMachine.loadInventory();
    }

    public void display() {
        vendingMachine.displayVendingMachine();
    }
}
