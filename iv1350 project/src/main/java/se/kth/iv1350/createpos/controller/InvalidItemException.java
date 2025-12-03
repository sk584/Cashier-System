package se.kth.iv1350.createpos.controller;

/**
 * InvalidItemException is thrown when an item not present in the External Inventory System is scanned. 
 * It will stop the exectuion if the itemID is invalid.   
 */
public class InvalidItemException extends Exception {
    /**
     *  Creates an instance of InvalidItemException with an informative message. 
     * @param message this provides a desprciton as to why the execption occured. 
     */

    public InvalidItemException(String message) {
        super(message);
    }

}
