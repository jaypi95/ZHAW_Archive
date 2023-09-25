
/**
 * Write a description of class Ticket here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Tickets
{
    // instance variables - replace the example below with your own
    private String description;
    private int price;
    private int number;
    private int openingStock;
    private int id;
    private int revenue;
    private int ticketsSold;

    /**
     * Constructor for objects of class Ticket
     */
    public Tickets()
    {
        // initialise instance variables
        //description = "NULL";
        //price = 0;
        //number = 0;
        //openingStock = 0;
        //id = 0;
    }

    /**
     * Creates new ticket category
     *
     * @param  description of category, price and number of tickets
     * 
     */
    public void createCategory(String description, int price, int number, int id)
    {
        if(description.equals("NULL") || number < 1 || price < 1){
        System.out.println("Bitte geben Sie gültige Werte ein.");
        }
        else {
            this.description = description;
            this.number = number;
            openingStock = number;
            this.price = price;
            this.id = id;
        }
    }
    /**
     * Sells a ticket
     */
    public void sellTicket(int sold) {
        if(number - sold < 0){
            System.out.println("Sorry, " + description + " ist ausverkauft.");
        }
        else {
            number = number - sold;
            revenue = (openingStock - number) * price;
            ticketsSold = openingStock - number;
        }
    }
    
    public int getTicketsSold(){
        return ticketsSold;
    }
    
    public int getRevenue(){
        return revenue;
    }
    public String getInfo(){
        return description + "-Tickets: " + ticketsSold + " von " + openingStock + " verkauft, Einnahmen: CHF " + revenue;
    }
}
