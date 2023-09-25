
/**
 * This class creates artists with their name and gage
 *
 * @peterju1
 * @2020100101
 */
public class Artists
{
    // instance variables - replace the example below with your own
    private int gage;
    private String name;

    /**
     * Constructor for objects of class Artists
     */
    public Artists(String name, int gage)
    {
        // initialise instance variables
        this.name = name;
        this.gage = gage;
    }

    /**
     * Creates new artist
     *
     *
     *
     */
    public void createArtist(int gage, String name)
    {
        // put your code here
        if(name.equals("NULL") || gage < 1){
        System.out.println("Bitte geben Sie gültige Werte ein.");
        }
        else {
            this.name = name;
            this.gage = gage;
        }
    }
    /**
     * Method that returns name of artist
     */
    public String getName(){
        return name;
    }
    /**
     * Method that returns gage of artist
     */
    public int getGage(){
        return gage;
    }
    
    /**
     * Method to set name of artist
     */
    public void setName(String name){
        name = this.name;
    }
    /**
     * Method to set gage of artist
     */
    public void setGage(int gage){
        this.gage = gage;
    }
}
