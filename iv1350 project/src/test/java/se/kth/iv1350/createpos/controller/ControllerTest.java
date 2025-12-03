package se.kth.iv1350.createpos.controller;


import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.createpos.dto.ItemDTO;
import se.kth.iv1350.createpos.dto.ReceiptDTO;
import se.kth.iv1350.createpos.integration.DataBaseFailureException;
import se.kth.iv1350.createpos.integration.DiscountDataBase;
import se.kth.iv1350.createpos.integration.Integration;
import se.kth.iv1350.createpos.integration.Printer;
import se.kth.iv1350.createpos.util.FileLogger; 

public class ControllerTest {
    private Controller controller;
    private Integration integration;
    private Printer printer;
    private ItemDTO item;
    private DiscountDataBase discountDataBase;
    private int quantity = 2; 

    @BeforeEach
    public void setUp() throws IOException, DataBaseFailureException, InvalidItemException {
        integration = new Integration();
        printer = new Printer();
        discountDataBase = DiscountDataBase.getInstance();
        FileLogger logger = new FileLogger();
        controller = new Controller(integration, printer, discountDataBase, logger);
        controller.initiateSale(1234);
        item = controller.scanItems("abc123", quantity);      


    }

    @AfterEach
    public void tearDown() {
        controller = null;
        integration = null;
        printer = null;
        item = null;
        discountDataBase = null;

    }
    
   @Test    
    public void testIfScannedItemsGivesCorrectItemID() {
        assertEquals("abc123", item.getItemID(), "Item ID returned should be equal to the scanned item ID");
        
    }

    @Test 
    public void testAddItems() {
        assertEquals(2, item.toItem(2).getQuantity(), "Item quantity should be the same as the quanitity scanned");}
 
    
    @Test
    public void testInvalidItemException() throws InvalidItemException, DataBaseFailureException { 
         try {
            controller.scanItems("h792", quantity); 
            fail("InvalidItemException was expected but not thrown");
        } catch (InvalidItemException e) {
            assertEquals("Item ID: h792 is invalid. Please check itemID and try again.", e.getMessage(), "Exception message should match");
        }
    } 

    @Test 
    public void testDataBaseFailureException() throws InvalidItemException {
        try {
            controller.scanItems("def456", quantity);
            fail("DataBaseFailureExecption needs to be thrown");
        } catch (DataBaseFailureException e) {
            assertEquals("Could not access DataBase for item: def456. Please try again later.", e.getMessage(), "Exception message should match");
        }
    }

    @Test public void testCalculateRunningTotal() throws InvalidItemException, DataBaseFailureException {
        double expectedTotal = item.getPrice()*quantity; 
        assertEquals(expectedTotal, controller.getCalculateRunningTotal(), "Running total should be equal to the item price times the quantity");
    }
 
    @Test
    public void testRegisterPayment() {
        controller.registerPayment(100);
        ReceiptDTO receiptTest = controller.getReceiptInformation();  
        assertEquals(100, receiptTest.getAmountPaid(), "Amount paid should be 100");
    }


    @Test
    public void testEndSale() {
        double expectedFinalPrice = controller.calculateFinalPrice(quantity);
        double finalPrice = controller.endSale(quantity);
        assertEquals(expectedFinalPrice, finalPrice, 0.01, "Final price should be calculated correctly");
    }

}



