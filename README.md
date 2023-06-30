# Vending Machine Software - Module 1 Capstone

## Project Overview

The Vending Machine Software is a project I developed as part of Module 1 Capstone. It is an application designed for the newest vending machine distributor, Umbrella Corp. The goal was to create a software solution for their latest vending machine, the Vendo-Matic 800. This vending machine is integrated with customers' bank accounts, allowing them to conveniently purchase products from their computers.

## Project Scope

The application has the following requirements:

- The vending machine dispenses beverages, candy, chips, and gum.
- Each vending machine item has a Name and a Price.
- A main menu must display when the software runs, presenting the following options:
  - (1) Display Vending Machine Items
  - (2) Purchase
  - (3) Exit
- The vending machine reads its inventory from an input file when the vending machine starts.
- The vending machine is automatically restocked each time the application runs.
- When the customer selects "(1) Display Vending Machine Items", they're presented with a list of all items in the vending machine with its quantity remaining.
- Each vending machine product has a slot identifier and a purchase price.
- Each slot in the vending machine has enough room for 5 of that product.
- Every product is initially stocked to the maximum amount.
- A product that has run out must indicate that it's SOLD OUT.
- When the customer selects "(2) Purchase", they're guided through the purchasing process menu.
- The purchase process flow includes options to feed money, select a product, and finish the transaction.
- The customer can repeatedly feed money into the machine in whole dollar amounts.
- The current money provided by the customer is tracked and displayed.
- The customer can select a product from the available options.
- The vending machine validates the product code and availability.
- The selected product is dispensed to the customer with a corresponding message.
- After the product is dispensed, the machine updates its balance and returns the customer to the Purchase menu.
- The customer can choose to finish the transaction and receive any remaining change.
- The machine returns the customer's money using nickels, dimes, and quarters.
- The machine's current balance updates to $0 remaining.
- All transactions are logged to prevent theft from the vending machine.
- Each purchase generates a line in a file called Log.txt.
- The application includes testable classes and limits console input/output to minimize dependencies.
- Optional - A "Hidden" menu option on the main menu allows generating a sales report showing the total sales since the machine started.
- The sales report file includes the date and time to ensure uniqueness.
- Unit tests are provided to demonstrate the correctness of the code.
