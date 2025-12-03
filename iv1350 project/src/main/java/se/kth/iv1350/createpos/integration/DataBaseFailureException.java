package se.kth.iv1350.createpos.integration; 


/** 
 *  DataBaseFailureException is thrown when there is an issue with connceting to the DataBase server. 
 */
public class DataBaseFailureException extends RuntimeException {
  

    /**
     * Constructs an instance of DataBaseFailureException with a specified detail message.
     * @param message the detail message.
     */
    public DataBaseFailureException(String message) {
        super(message);
    }



}
