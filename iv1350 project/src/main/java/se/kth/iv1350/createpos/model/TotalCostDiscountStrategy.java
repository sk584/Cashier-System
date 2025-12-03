package se.kth.iv1350.createpos.model;

import java.util.ArrayList;

import se.kth.iv1350.createpos.integration.DiscountDataBase;


/**
 * TotalCostDiscountStrategy applies a discount based on the total cost of the items in the basket.
 * The discount rate is fetched from the DiscountDataBase.
 */
public class TotalCostDiscountStrategy implements DiscountStrategy {
     private DiscountDataBase discountDataBase;


     /**
      * Creates a new instance of TotalCostDiscountStrategy using the specified discount database.
      * @param discountDataBase The database containing total cost discount information.
      */
     
     public TotalCostDiscountStrategy (DiscountDataBase discountDataBase){
        this.discountDataBase = discountDataBase; 
     }

/**
 * Calculates the amount of discount for a given total price.
 * The discount rate is fetched from the discount database based on the total price.
 * @param totalPrice The total price before discount.
 * @param customerID The ID of the customer.
 * @param itemBasket The list of items in the basket.
 * @return The amount to be discounted from the total price.
 */
@Override
public double amountOfDiscount(double totalPrice, int customerID, ArrayList<Item> itemBasket){
        double discountRate = discountDataBase.fetchTotalCostDiscount(totalPrice);
        return totalPrice * discountRate; 

}
}


