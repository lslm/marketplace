package dev.lslm.marketplace.entities;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockTest {
    @Test
    public void increaseStockTest() {
        Product product = new Product(1, "Teste", new BigDecimal("3.99"));
        Stock stock = new Stock(1, product, 5);

        stock.increaseQuantity(2);

        assertEquals(7, stock.getQuantity());
    }
}
