package se.kth.iv1350.createpos.model;

import java.util.ArrayList;

import se.kth.iv1350.createpos.integration.DiscountDataBase;
/**
 * This class implements a discount strategy that returns a fixed amount off if items in sale execeds a certain amount.
 */


public class ItemDiscountStrategy implements DiscountStrategy{
    private DiscountDataBase discountDataBase;
  
/**
 * Creates a new instance of ItemDiscountStrategy using the specified discount database.
 * @param discountDataBase The database containing item discount information.
 */

public ItemDiscountStrategy (DiscountDataBase discountDataBase){
        this.discountDataBase = discountDataBase; 
}

/**
 * Calculates the amount of discount for a given total price.
 * The discount rate is fetched from the discount database based on the items in the basket.
 * @param totalPrice The total price before discount.
 * @param customerID The ID of the customer.
 * @param itemBasket The list of items in the basket.
 * @return The amount to be discounted from the total price.
 */
@Override
public double amountOfDiscount(double totalPrice, int customerID, ArrayList<Item> itemBasket){
    
    return discountDataBase.fetchItemDiscount(itemBasket); 

}


}