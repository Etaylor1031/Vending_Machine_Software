package com.techelevator;

import com.techelevator.view.Menu;
import com.techelevator.view.VendingMachineInterface;
import com.techelevator.view.VendingMachineSalesReport;

import java.io.IOException;

public class VendingMachineCLI {
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String MAIN_MENU_OPTION_SALES_REPORT = "Sales Report";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_SALES_REPORT };

	private Menu menu;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() throws IOException {
		// Restock the Vending Machine
		VendingMachineInterface vendingMachine = new VendingMachineInterface(System.in, System.out);
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				vendingMachine.display();
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				vendingMachine.purchase();
			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				break;
			} else if (choice.equals(MAIN_MENU_OPTION_SALES_REPORT)) {

			}

		}
	}

	public static void main(String[] args) throws IOException {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
