public class Student extends Person{
    private int credits;


    protected Student(){
        super();
        credits = 0;

    }
    protected Student(String name, String id){
        super(name, id);
        credits = 0;

    }
    public int gibCredits(){
        return credits;
    }
    public void erhoeheCredits(int addition){
        credits = credits + addition;
    }
}