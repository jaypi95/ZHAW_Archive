public class Dozent extends Person {
    private String buero;
    private String tel;

    protected Dozent(){
        super();
        buero = null;
        tel = null;
    }
    protected Dozent(String name, String id, String buero, String tel){
        super(name, id);
        this.buero = buero;
        this.tel = tel;
    }
    public String gibBuero(){
        return buero;
    }
    public String gibTelefonnummer(){
        return tel;
    }
}
