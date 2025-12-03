
package se.kth.iv1350.createpos.model;

import java.time.LocalTime;
import se.kth.iv1350.createpos.dto.SaleDTO;
import se.kth.iv1350.createpos.dto.ReceiptDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import se.kth.iv1350.createpos.util.Observer; 

/**
 * This class represents a sale in the system. It contains information about the items sold, the time of sale, and 
 * the payment. 
 */
public class Sale {
    private double amountOfDiscount;
    private LocalTime timeOfSale;
    private ArrayList<Item> itemBasket;
    private Payment payment;
    private List <Observer> observers = new ArrayList<>();
    private DiscountStrategy discountStrategy;
    private int customerID;

    /**
     * Constructor for Sale class.
     * It initializes the sale with the current time and an empty item basket.
     * @param discountStrategy 
     * @param customerID 
     */

    public Sale(DiscountStrategy discountStrategy, int customerID){
        this.timeOfSale = LocalTime.now();
        this.itemBasket = new ArrayList<>();
        this.discountStrategy = discountStrategy;
        this.customerID = customerID;
    }

   /**
     * Adds an observer to be notified when the revenue is updated.
     * @param observer The observer to add.
     */

    public void addObserver (Observer observer){
        observers.add(observer); 
    }

    /**
     * Notifies all registered observers of the updated revenue.
     */
    public void notifyObservers(){
        double totalSumOfRevenue = calculateFinalPrice();
        for (Observer obsv: observers) {
            try {
            obsv.updatedAmountOfRevenue(totalSumOfRevenue);
        }

        catch (Exception e){
            System.err.println("Observer Error: " + e.getMessage());
        }
    }
    }

     /**
     * Sets the payment and notifies observers. 
     * @param payment the payment to be set.
     */
      
    public void setPayment(Payment payment) {
        this.payment = payment;
        notifyObservers();
    }

     /**
     * Sets the discount strategy for this sale.
     * @param strategy The discount strategy to use.
     */
    public void setDiscountStrategy(DiscountStrategy strategy){
        this.discountStrategy = strategy;
    }

    
    /**
     * Calculates the final price after applying the discount for a specific customer.
     * @param customerID The ID of the customer.
     * @return The final price after applying the discount.
     */
    
    public double calculateFinalPrice(int customerID){
        double total = calculateRunningTotal();
        double discountRate = discountStrategy.amountOfDiscount(total, customerID, itemBasket);
        this.amountOfDiscount = discountRate;
        return total - discountRate;
    }

   

    /**
     * Gets the time of sale.
     * @return the time of sale.
     */
     
    public LocalTime getTimeOfSale(){
        timeOfSale = LocalTime.now();
        return timeOfSale;
    }

    /**
     * Adds an item to the item basket.
     * If the item is null, it does nothing.
     * If the item already exists in the basket, it updates the quantity.
     * If the item does not exist in the basket, it adds the item to the basket.
     * @param item  the item to be added.
     * @param quantity  the quantity of the item to be added.
     */

    public void addItems(Item item, int quantity){
            if (item == null)
                return;

            for (Item chosenItem : itemBasket){
                if (chosenItem.getItemID().equals(item.getItemID())){
                    chosenItem.increaseQuantity(quantity);
                    return;
                }
            } 
            itemBasket.add(item);
        } 
       
        
    /** 
    * Calculates the running total of the items in the basket
    * @return the running total of the items in the basket.
    */
    public double calculateRunningTotal(){
        double totalPrice = 0;
        for (Item item : itemBasket) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
        return totalPrice;
    }

    /**
     * Gets the receipt information.
     * @return the receipt information.
     */
     public ReceiptDTO getReceiptInformation() {
        double totalPrice = calculateRunningTotal();
        int timeOfSale = LocalTime.now().getHour() * 60 + LocalTime.now().getMinute();
        int date = LocalDate.now().getDayOfMonth();
        double VAT = amountOfVAT(); 
        int quantityOfItems = calculateNumberOfItems();
        double amountOfChange = calculateChange(); 
        double amountPaid = payment.getAmountPaid(); 

        ReceiptDTO receiptDTO = new ReceiptDTO(totalPrice, timeOfSale, date, VAT, quantityOfItems, amountOfChange, amountPaid, itemBasket, amountOfDiscount);
        return receiptDTO;
    }

    /**
     * Gets the receipt for the sale.
     * @return the receipt for the sale.
     */
    public Receipt getReceipt() {
        double totalPrice = calculateFinalPrice();
        int items = itemBasket.size();
        double VAT = amountOfVAT(); 
        int quantityOfItems = calculateNumberOfItems();

        ReceiptDTO receiptDTO = getReceiptInformation();
        return new Receipt(totalPrice, items, VAT, receiptDTO, quantityOfItems);
    }
    
    /**
    * Converts the sale to a SaleDTO object. 
    * @return the SaleDTO object representing the sale.
    */
    public SaleDTO toDTO(){
        return new SaleDTO(calculateNumberOfItems(), calculateFinalPrice(), getNameList());
    }

    
    private String[] getNameList() {
        String[] name = new String[itemBasket.size()];
        int i = 0;
        for (Item item : itemBasket) {
            name[i] = item.getName();
            i++;
        }
        return name;
    }
    
    
    private int calculateNumberOfItems() {
        int numberOfItems = 0;
        for (Item item : itemBasket) {
            numberOfItems += item.getQuantity();
        }
        return numberOfItems;
    }
 

    /**
     * Gets the amount of VAT for the items in the basket.
     * @return the amount of VAT for the items in the basket.
     */

    public double amountOfVAT(){
        double amountOfVAT = 0;
        for (Item item : itemBasket) {
            amountOfVAT += item.getPrice() * item.getQuantity() * (item.getVAT()/100); 
        }
         return amountOfVAT;
    }

   

    private double calculateChange() {
       double amountOfChange = payment.getAmountPaid() - calculateFinalPrice();
         return amountOfChange;
    }


    /**
     * Calculates the final price for the sale using the stored customer ID.
     * @return The final price after discount.
     */
    public double calculateFinalPrice(){
        return calculateFinalPrice(customerID);
    }
    
    /**
     * Gets the list of items in the basket.
     * @return the list of items in the basket.
     */ 
    public ArrayList<Item> itemBasket() {
        return itemBasket;
    }

}
