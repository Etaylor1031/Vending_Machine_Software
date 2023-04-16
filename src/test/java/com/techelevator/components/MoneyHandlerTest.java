package com.techelevator.components;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Scanner;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MoneyHandlerTest {
    @Test
    public void testReturnChange() {
        MoneyHandler moneyHandler = new MoneyHandler();
        moneyHandler.setBalance(BigDecimal.valueOf(1.25));
        int[] expectedChange = {5, 0, 0};
        int[] actualChange = moneyHandler.returnChange();
        assertArrayEquals(expectedChange, actualChange);
    }

    @Test
    public void feedMoneyTest() {
        String input = "5\n\nN";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        MoneyHandler moneyHandler = new MoneyHandler();
        BigDecimal initialBalance = moneyHandler.getBalance();
        BigDecimal expectedBalance = initialBalance.add(new BigDecimal("5"));

        moneyHandler.feedMoney();

        assertEquals(expectedBalance, moneyHandler.getBalance());
    }
}

