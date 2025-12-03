package se.kth.iv1350.createpos.view;

import se.kth.iv1350.createpos.util.Observer;

/**
 * This class observes revenue updates and displays the total revenue on the console.
 * Each time the revenue is updated, the new total is printed to the standard output.
 */
public class TotalRevenueView implements Observer{
    private double totalSumOfRevenue = 0;

    /**
     * Called when the amount of revenue is updated.
     * Adds the new revenue to the total and prints it to the console.
     * @param revenue The revenue from the latest sale.
     */
    @Override
    public void updatedAmountOfRevenue (double revenue) {
        totalSumOfRevenue += revenue; 
        System.out.printf("Amount of Revenue: %.2f SEK%n", totalSumOfRevenue);
        System.out.println();
    }

}