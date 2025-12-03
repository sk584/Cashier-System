
package se.kth.iv1350.createpos.integration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import se.kth.iv1350.createpos.model.Item;

/**
 * This class represents a discount database that stores customer IDs and their corresponding discounts.
 */

public class DiscountDataBase {
    private int customerID;
    private static DiscountDataBase instance; 
    private Map<Integer, Double> discountDataBase = new HashMap<>();


    /**
     * Constructor for DiscountDataBase class.
     * Initializes the discount database with a customer ID and its corresponding discount.
     * @param customerID  the ID of the customer.
     * @param discount  the discount for the customer.
     */

private DiscountDataBase() {
        discountDataBase = new HashMap<>();
        discountDataBase.put(8769, 0.00); 
        discountDataBase.put(1234, 0.10); 
    }
    
    /**
   * Returns the singleton instance of the DiscountDataBase.
   * If the instance does not exist, it is created.
   * @return The singleton instance of DiscountDataBase.
   */
 public static DiscountDataBase getInstance(){
    if (instance == null) {
        instance = new DiscountDataBase();
    }
    return instance;
}

    /**
     * Fetches the discount for a given customer ID.
     * @param customerID
     * @return the amount of discount for the customer with the given ID, or 0.0 if no discount is found.
     */
  public double fetchCustomerDiscount(int customerID) {
        if (discountDataBase.containsKey(customerID)) {
            return discountDataBase.get(customerID);
        } else {
            return 0.0; // No discount for this customer
        }
    }    

   
    /**
     * Fetches the discount based on the number of items in the basket. 
     * 50 SEK disount for more than 20 items. 
     * @param itemBasket The list of items in the basket.
     * @return The discount percentage to be applied.
     */
    public double fetchItemDiscount(ArrayList<Item> itemBasket){
        int quantityOfItems = 0;
        for (Item item : itemBasket){
            quantityOfItems += item.getQuantity();
        }
        if (quantityOfItems > 20)
            return 50; 

        return 0;
    }

    /**
     * Fetches a total cost discount based on the total cost of the items in the basket.   
     * @param totalCost The total cost of the items in the basket.
     * @return The discount percentage to be applied, or 0.0 if no discount is applicable.
     */

     public double fetchTotalCostDiscount(double totalCost){
        if (totalCost > 20){
            return 0.15;
        }
        return 0.0;
     }
    
    /**
    * Gets the customer ID.
    * @return the customer ID.
    */
  public int getcustomerID(){
    return this.customerID;
    }

}


