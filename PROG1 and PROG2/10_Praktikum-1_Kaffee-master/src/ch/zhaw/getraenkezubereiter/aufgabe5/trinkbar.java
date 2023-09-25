package ch.zhaw.getraenkezubereiter.aufgabe5;

public interface trinkbar {
    default void trinke(){
       System.out.println("Ich trinke einen " + getClass().getSimpleName());

    }
}
