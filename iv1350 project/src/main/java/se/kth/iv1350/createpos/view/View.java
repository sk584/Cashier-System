
package se.kth.iv1350.createpos.view;
import java.io.IOException;
import se.kth.iv1350.createpos.controller.Controller;
import se.kth.iv1350.createpos.dto.ItemDTO;
import se.kth.iv1350.createpos.integration.ExternalInventorySystem;
import se.kth.iv1350.createpos.model.CompliedDiscountStrategy;
import se.kth.iv1350.createpos.model.CustomerDiscountStrategy;
import se.kth.iv1350.createpos.model.ItemDiscountStrategy;
import se.kth.iv1350.createpos.model.TotalCostDiscountStrategy;
import se.kth.iv1350.createpos.integration.DataBaseFailureException;
import se.kth.iv1350.createpos.controller.InvalidItemException;


/**
 * This class represents the view of the program. It is responsible for displaying information to the user.
 */
public class View {
    private Controller contr;
    
    
    /**
     * Constructor for the View class.
     * It initializes the controller for the view.
     * @param contr The controller for the view.
     */
    public View(Controller contr){
        this.contr = contr;

    try {
         TotalRevenueView tRevView = new TotalRevenueView();
         TotalRevenueFileOutput tRevFile = new TotalRevenueFileOutput();
        contr.addObserver(tRevView); 
        contr.addObserver(tRevFile);
     }

     catch (IOException e) {
        System.out.println("something");
        e.printStackTrace();

     }
        
    }
    
    /**
     * This method simulates a new sale execution.
     * It initiates a sale, scans items, and registers payment.
     * It also displays information about the items and the total cost.
     * It prints the item information to the console.
     */
    public void newSaleExecution(){
        System.out.println("");
        contr.initiateSale(1234);
        System.out.println("Sale has been initiated ");

        String[] itemIDs = {"abc123", "abc123", "def456", "56ghy"};
            int [] quantities = {1, 1, 1, 1};

            for (int i = 0; i < itemIDs.length; i++){
      
        try{
            
            System.out.println();
            ItemDTO item = contr.scanItems(itemIDs[i], quantities[i]);   
            System.out.println("Add 1 item with item ID: " + item.getItemID());
            System.out.println("Item ID: " + item.getItemID());
            System.out.println("Item name: " + item.getName());
            System.out.printf("Item cost: %.2f SEK%n", item.getPrice());
            System.out.println("VAT:" + item.getVAT() + " %");
            System.out.println("Item Description: " + item.getDescription());
            System.out.println("");
            System.out.printf("Total cost (including VAT): %.2f SEK%n", contr.getCalculateRunningTotal());
            System.out.printf("Total VAT: %.2f SEK%n", contr.getAmountOfVat());
          }  catch (InvalidItemException e) {
                System.out.println("Error occurred: " + e.getMessage());
            } catch (DataBaseFailureException e) {
                System.out.println("Error occurred: " + e.getMessage());
                
            }
       
        }
        
        int customerID = 1234;
        System.out.println("End Sale: ");
        System.out.printf("Total cost (including VAT): %.2f SEK%n", contr.getCalculateRunningTotal());
        System.out.println("");
        CompliedDiscountStrategy compliedDiscountStrategy = new CompliedDiscountStrategy();
        compliedDiscountStrategy.addStrategy(new CustomerDiscountStrategy(contr.getDiscountDataBase()));
        compliedDiscountStrategy.addStrategy(new ItemDiscountStrategy(contr.getDiscountDataBase()));
        compliedDiscountStrategy.addStrategy(new TotalCostDiscountStrategy(contr.getDiscountDataBase()));
        contr.endSale(customerID); 
        System.out.printf("Final price after discount: %.2f SEK%n", contr.calculateFinalPrice(customerID));  
        contr.registerPayment(100); 

    }

    /**
     * This method prints the information of all items in the inventory system.
     * It prints the item ID, name, price, quantity, VAT, and total cost to the console.
     */

    public void printItemInformation(){
        ItemDTO[] items = ExternalInventorySystem.getInventorySystem();
        for (ItemDTO item : items){
            System.out.println("Item ID: " + item.getItemID());
            System.out.println("Item: " + item.getName());
            System.out.println("Price: " + item.getPrice());
            System.out.println("Quantity: " );
            System.out.println("VAT: " + item.getVAT());
            System.out.println("Total cost:  " + contr.getCalculateRunningTotal());
        }
    }
}