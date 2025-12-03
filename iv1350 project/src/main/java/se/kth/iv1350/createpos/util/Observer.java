package se.kth.iv1350.createpos.util;

/**
 * Observer interface for receiving updates about revenue changes.
 * Classes that implement this interface can be notified when the total revenue is updated.
 */

public interface Observer {
    /**
     * Called when the amount of revenue is updated.
     * @param revenue The updated total revenue.
     */
    void updatedAmountOfRevenue (double revenue);
} 

