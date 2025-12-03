package se.kth.iv1350.createpos.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class IntegrationTest {
    private Integration integration;

    @BeforeEach
    public void setUp() {
        integration = new Integration();
    }
    @Test
    public void testGetPrinter() {
        Printer printer = integration.getPrinter();
        assertNotNull(printer, "Printer should not be null");
    }

    @Test
    public void testGetDiscountDB() {
        DiscountDataBase discountDB = integration.getDiscountDB();
        assertNotNull(discountDB, "Discount database should not be null");
    }

   
    
}
