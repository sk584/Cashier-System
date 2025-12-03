
package se.kth.iv1350.createpos.controller;

import java.util.ArrayList;
import java.util.List;
import se.kth.iv1350.createpos.dto.ItemDTO;
import se.kth.iv1350.createpos.dto.ReceiptDTO;
import se.kth.iv1350.createpos.dto.SaleDTO;
import se.kth.iv1350.createpos.integration.Integration;
import se.kth.iv1350.createpos.integration.Printer;
import se.kth.iv1350.createpos.model.CompliedDiscountStrategy;
import se.kth.iv1350.createpos.model.CustomerDiscountStrategy;
import se.kth.iv1350.createpos.model.DiscountStrategy;
import se.kth.iv1350.createpos.model.Item;
import se.kth.iv1350.createpos.model.ItemDiscountStrategy;
import se.kth.iv1350.createpos.model.Payment;
import se.kth.iv1350.createpos.model.Sale;
import se.kth.iv1350.createpos.model.TotalCostDiscountStrategy;
import se.kth.iv1350.createpos.integration.DataBaseFailureException;
import se.kth.iv1350.createpos.integration.DiscountDataBase;
import se.kth.iv1350.createpos.integration.ExternalInventorySystem;
import se.kth.iv1350.createpos.util.FileLogger;
import se.kth.iv1350.createpos.util.Observer;



/**
 * The Controller class is responsible for handling the interaction between the
 * view, model, and integration layers of the application. It processes user
 * input, manages the sale process, and communicates with external systems.
 */

public class Controller {
    private Integration integration;
    private Sale sale;
    private SaleDTO saleDTO;
    private Printer printer;
    private DiscountDataBase discountDataBase;
    private FileLogger logger;
    private List <Observer> observers = new ArrayList<>();

    /**
     * This is the constructor for the Controller class.
     * @param integration is responsible for handling the integration with external systems.
     * @param printer is responsible for printing receipts.
     * @param discountDataBase  is responsible for managing discount information.
     * @param logger 
     */
    
    public Controller(Integration integration, Printer printer, DiscountDataBase discountDataBase, FileLogger logger) {
        this.integration = integration;
        this.printer = printer; 
        this.discountDataBase = discountDataBase;
        this.logger = logger;
    }

    /**
     * Registers an observer to be notified of revenue updates.
     * @param observer The observer to add.
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Initiates a new sale by creating a new Sale object and registering all observers. 
     * @param customrID The ID of the customer for this sale.
     */
   public void initiateSale (int customerID) {
    CompliedDiscountStrategy compliedDiscountStrategy = new CompliedDiscountStrategy();
    compliedDiscountStrategy.addStrategy(new ItemDiscountStrategy(discountDataBase));
    compliedDiscountStrategy.addStrategy(new TotalCostDiscountStrategy(discountDataBase));
    compliedDiscountStrategy.addStrategy(new CustomerDiscountStrategy(discountDataBase));
       sale = new Sale(compliedDiscountStrategy, customerID);
       for (Observer obs : observers){
        sale.addObserver(obs);
       }
   }
   
   /**
    * Scans an item with the given item ID and quantity, adds it to the current sale, and calculates
    the running total.
    * @param itemID The ID of the item to be scanned.
    * @param quantity The quantity of the item to be scanned.
    * @throws InvalidItemException If the item ID is invalid.
    * @throws DataBaseFailureException If there is a database failure.
    * @return The scanned Item object.
    */
   public ItemDTO scanItems(String itemID, int quantity) throws InvalidItemException, DataBaseFailureException {
  
    try{
    
     ItemDTO itemDTO = ExternalInventorySystem.getItemID(itemID);
     Item item = itemDTO.toItem(quantity);
     sale.addItems(item, quantity);
     sale.calculateRunningTotal();
     return itemDTO;
    }

   catch (InvalidItemException e) {
        logger.log(e);
        throw new InvalidItemException("Item ID: " + itemID + " is invalid. Please check itemID and try again.");
    } catch (DataBaseFailureException e) {
         logger.log(e);
        throw new DataBaseFailureException("Could not access DataBase for item: " + itemID + ". Please try again later.");
    }
}
   /**
     * Sets the discount strategy for the current sale.
     * @param strategy The discount strategy to use.
     */
 public void setDiscountStrategy(DiscountStrategy strategy){
    sale.setDiscountStrategy(strategy);
}


    /**
     * Registers the payment for the current sale, updates the external systems and prints receipt.
     * @param amount The amount paid by the customer.
     */

   public void registerPayment(double amount) {
    Payment payment = new Payment(amount);
    sale.setPayment(payment);
    saleDTO = sale.toDTO(); 
    integration.updatesSystems(saleDTO);
    ReceiptDTO receiptDTO = sale.getReceiptInformation();
    printer.printReceipt(receiptDTO); 
   }
   
   /**
    * Ends the sale and calculates the final price containing amount of discount.
    * @param customerID The ID of the customer.
    * @return the final price of the sale.
    */
   public double endSale(int customerID){
       return sale.calculateFinalPrice(customerID);
   }

   /**
    * Gets the receipt information for the current sale.
    * @return The ReceiptDTO object containing receipt information.
    */

   public ReceiptDTO getReceiptInformation(){
       return sale.getReceiptInformation();
   }

   /**
    * Gets the total price for all items in the sale.
    * @return The total price for all items.
    */
    public double getCalculateRunningTotal() {
        return sale.calculateRunningTotal();
    }

    /**
     * Gets the amount of VAT for the current sale.
     * @return The amount of VAT for the current sale.
     */
    public double getAmountOfVat(){
        return sale.amountOfVAT();
    }

    /**
     * Gets the discount database instance.
     * @return The DiscountDataBase instance.
     */
    public DiscountDataBase getDiscountDataBase() {
        return this.discountDataBase;
    }

    /**
     * Calculates the final price for the current sale, including discount.
     * @param customerID The ID of the customer.
     * @return The final price after discount.
     */
    public double calculateFinalPrice(int customerID){
        return sale.calculateFinalPrice(customerID);
    }

}
