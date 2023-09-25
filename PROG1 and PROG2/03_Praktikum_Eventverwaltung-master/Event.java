
/**
 * Write a description of class Event here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Event
{
    // instance variables - replace the example below with your own
    private Artists artist;
    private Tickets category1;
    private Tickets category2;
    private Tickets category3;
    

    /**
     * Constructor without parameter
     */
    public Event()
    {
        
    }
    /**
     * Constructor with parameter
     */
    public Event(Artists artist,Tickets category1, Tickets category2, Tickets category3){
        this.artist = artist;
        this.category1 = category1;
        this.category2 = category2;
        this.category3 = category3;
        
    }

    /**
     * Create a new artist
     *
     * @param name Name of the artist
     * @param gage Salary of the artist
     */
    public void createArtist(String name, int gage){
        this.artist = new Artists(name,gage);
    }
    
    /**
     * Create new ticket category
     * @param description: Name of the category
     * @param price
     * @param number: How many tickets should be available
     * @param id: ID of the category
     */
    public void createNewCategory(String description, int price, int number, int id){
        Tickets newCategory = new Tickets();
        newCategory.createCategory(description, price, number, id);
        if(id==1){
            this.category1 = newCategory;
        } else if(id==2){
            this.category2 = newCategory;
        } else if (id==3) {
            this.category3 = newCategory;
        } else {
            System.out.println("Sorry, this category number is not allowed.");
        }
    }
    public void sellTicket(int id, int number){
        if(id==1){
            category1.sellTicket(number);
        } else if(id==2){
            category2.sellTicket(number);
        } else if (id==3) {
            category3.sellTicket(number);
        } else {
            System.out.println("Sorry, these values are not allowed.");
        }
    }
    
    public int getProfit(){
        int cat1 = category1.getRevenue();
        int cat2 = category2.getRevenue();
        int cat3 = category3.getRevenue();
        int gage = artist.getGage();
        
        int profit = (cat1 + cat2 + cat3) - gage;
        return profit;
    }
    
    public int getTotal(){
        int cat1 = category1.getRevenue();
        int cat2 = category2.getRevenue();
        int cat3 = category3.getRevenue();
        
        int total = cat1 + cat2 + cat3;
        return total;
    }
    
    public void getEventInfo(){
        String cat1 = category1.getInfo();
        String cat2 = category2.getInfo();
        String cat3 = category3.getInfo();
        String artistName = artist.getName();
        int artistGage = artist.getGage();
        int profit = getProfit();
        int total = getTotal();
        
        
        System.out.println("Kuenstler: " + artistName + ", Gage: CHF " + artistGage);
        System.out.println(cat1);
        System.out.println(cat2);
        System.out.println(cat3);
        System.out.println("Gesamteinnahmen: CHF " + total);
        System.out.println("Gewinn: CHF " + profit);
        
    }
}
