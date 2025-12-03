package se.kth.iv1350.createpos.view;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import se.kth.iv1350.createpos.util.Observer;  

/**
 * This class observes revenue updates and writes the total revenue to a file.
 * Each time the revenue is updated, the new total is appended to "totalRevenue.txt".
 */

public class TotalRevenueFileOutput implements Observer {
    private double totalSumOfRevenue = 0;
    private static final String FILE_STRING = "totalRevenue.txt";
    private  PrintWriter writer;

    /**
     * Creates a new TotalRevenueFileOutput that writes to the specified file.
     * @throws IOException if the file cannot be created or opened.
     */
    public TotalRevenueFileOutput() throws IOException {
            writer = new PrintWriter(new FileWriter(FILE_STRING, true), true);
        }

    /**
     * Called when the amount of revenue is updated.
     * Adds the new revenue to the total and writes it to the file.
     * @param revenue The revenue from the latest sale.
     */
    @Override
   public void updatedAmountOfRevenue(double revenue){
     totalSumOfRevenue += revenue;
     writer.printf("Total Revenue for this sale: %.2f SEK%n", totalSumOfRevenue);
   }


 
}

