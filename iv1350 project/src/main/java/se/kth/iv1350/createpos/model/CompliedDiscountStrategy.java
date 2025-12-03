package se.kth.iv1350.createpos.model;

import java.util.ArrayList;

/**
 *  CompliedDiscountStrategy is a composite strategy that aggregates multiple discount strategies.
 * 
 */

public class CompliedDiscountStrategy implements DiscountStrategy {
    private ArrayList<DiscountStrategy> discountStrategies;

    /**
     * Creates a new instance of CompliedDiscountStrategy.
     * Initializes the list of discount strategies.
     */

    public CompliedDiscountStrategy() {
       this.discountStrategies = new ArrayList<>();
    }

    /**
     * Adds a discount strategy to the list of strategies.
     * @param discountStrategy The discount strategy to be added.
     */
    
    public void addStrategy(DiscountStrategy discountStrategy) {
            discountStrategies.add(discountStrategy);
    }

    /**
     * Calculates the total amount of discount by applying all registered discount strategies.
     * @param finalPrice The total price before discount.
     * @param customerID The ID of the customer.
     * @param itemBasket The list of items in the basket.
     * @return The total amount of discount calculated from all strategies.
     */
    @Override
    public double amountOfDiscount(double finalPrice, int customerID, ArrayList<Item> itemBasket) {
        double totalDiscount = 0;
        for (DiscountStrategy strategy : discountStrategies) {
            totalDiscount += strategy.amountOfDiscount(finalPrice, customerID, itemBasket);
        }
        return totalDiscount;
    }
}