public class Person {
    private String name;
    private String id;

    protected Person() {
        name = null;
        id = null;
    }
    protected Person(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String gibInfo(){
        return name + ", ID" + id;
    }
}
