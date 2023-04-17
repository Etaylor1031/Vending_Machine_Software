package com.techelevator.components;

import com.techelevator.products.Product;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public class ProductRackTest {

    @Test
    public void dispenseProductReturnsBalance(){
        String testChoice = "A1";
        BigDecimal balance = new BigDecimal(3.00);
        BigDecimal expected = new BigDecimal(0.50);
        Product testProduct = new Product("Test Product", new BigDecimal(2.50), "Chips", 5);
        Map<String, Product> inventory = new TreeMap<String, Product>();
        inventory.put(testChoice, testProduct);
        ProductRack testRack = new ProductRack(inventory);

        BigDecimal result = testRack.dispenseProduct(testChoice, balance);

        Assert.assertEquals(0, result.compareTo(expected));
    }

    @Test
    public void dispenseProductDeclinesInsufficientBalance(){
        String testChoice = "A1";
        BigDecimal balance = new BigDecimal(0.25);
        BigDecimal expected = new BigDecimal(0.25);
        Product testProduct = new Product("Test Product", new BigDecimal(2.50), "Chips", 5);
        Map<String, Product> inventory = new TreeMap<String, Product>();
        inventory.put(testChoice, testProduct);
        ProductRack testRack = new ProductRack(inventory);

        BigDecimal result = testRack.dispenseProduct(testChoice, balance);

        Assert.assertEquals(0, result.compareTo(expected));
    }
}
