package se.kth.iv1350.createpos.model;

import java.util.ArrayList;

import se.kth.iv1350.createpos.integration.DiscountDataBase;

/**
 * This class implements a discount strategy that applies a customer-specific discount
 * fetched from the DiscountDataBase.
 */
public class CustomerDiscountStrategy implements DiscountStrategy{
    private DiscountDataBase discountDataBase;

    /**
     * Creates a new instance of ApplyDiscountStrategy using the specified discount database.
     * @param discountDataBase The database containing customer discount information.
     */

    public CustomerDiscountStrategy (DiscountDataBase discountDataBase){
        this.discountDataBase = discountDataBase; 
    
}

/**
     * Calculates the amount of discount for a given customer and final price.
     * The discount rate is fetched from the discount database using the customer ID.
     * @param finalPrice The total price before discount.
     * @param customerID The ID of the customer.
     * @return The amount to be discounted from the final price.
     */
@Override
public double amountOfDiscount (double finalPrice, int customerID, ArrayList<Item> itemBasket){
    double amountOfDiscount = discountDataBase.fetchCustomerDiscount(customerID);
    return finalPrice * amountOfDiscount;
}

}

