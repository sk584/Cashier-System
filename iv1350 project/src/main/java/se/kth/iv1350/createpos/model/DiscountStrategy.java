package se.kth.iv1350.createpos.model;

import java.util.ArrayList;

/**
 * This interface defines the contract for discount strategies.
 * Implementing classes must provide a way to calculate the discount amount
 * based on the final price and customer ID.
 */
public interface DiscountStrategy {

     /**
     * Calculates the amount of discount for a given customer and final price.
     *
     * @param finalPrice The total price before discount.
     * @param customerID The ID of the customer.
     * @return The amount to be discounted from the final price.
     */
    double amountOfDiscount (double finalPrice, int customerID, ArrayList<Item> itemBasket);
    
}
