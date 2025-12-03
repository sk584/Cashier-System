
package se.kth.iv1350.createpos.startup;

import java.io.IOException;

import se.kth.iv1350.createpos.controller.Controller;
import se.kth.iv1350.createpos.integration.Integration;
import se.kth.iv1350.createpos.view.View;
import se.kth.iv1350.createpos.util.FileLogger;

/**
 * The Main class is the entry point of the application. It initializes the necessary components and starts the program.
 */

public class Main {
    public static void main (String[] args) throws IOException{
      Integration integration = new Integration();
      FileLogger logger = new FileLogger();
      Controller contr = new Controller(integration, integration.getPrinter(), integration.getDiscountDB(), logger);
      View view = new View (contr);

      view.newSaleExecution();
    }
    
}
