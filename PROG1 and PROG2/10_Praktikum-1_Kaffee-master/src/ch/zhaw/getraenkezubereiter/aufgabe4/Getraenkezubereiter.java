package ch.zhaw.getraenkezubereiter.aufgabe4;

import java.util.ArrayList;
import java.util.List;

public class Getraenkezubereiter {


    private static List<KoffeinGetraenk> getraenkeList;

    public Getraenkezubereiter(){

    }
    public static void main(String[] args) {
        KoffeinGetraenk tee1;
        KoffeinGetraenk tee2;
        KoffeinGetraenk tee3;
        KoffeinGetraenk tee4;

        KoffeinGetraenk kaffee1;
        KoffeinGetraenk kaffee2;
        KoffeinGetraenk kaffee3;
        KoffeinGetraenk kaffee4;

        tee1 = new Tee();
        tee2 = new Tee();
        tee3 = new Tee();
        tee4 = new Tee();

        kaffee1 = new Kaffee();
        kaffee2 = new Kaffee();
        kaffee3 = new Kaffee();
        kaffee4 = new Kaffee();



        getraenkeList = new ArrayList<KoffeinGetraenk>();

        getraenkeList.add(tee1);
        getraenkeList.add(tee2);
        getraenkeList.add(tee3);
        getraenkeList.add(tee4);

        getraenkeList.add(kaffee1);
        getraenkeList.add(kaffee2);
        getraenkeList.add(kaffee3);
        getraenkeList.add(kaffee4);

        Getraenkezubereiter.erstelleGetraenke();


    }
    private static void erstelleGetraenke(){

        for(int i = 0; i < getraenkeList.size(); i++){
            KoffeinGetraenk getraenk =  (KoffeinGetraenk) getraenkeList.get(i);
            if(getraenk instanceof Tee){
                ((Tee) getraenk).bereiteTeeZu();
            } else if(getraenk instanceof Kaffee) {
                ((Kaffee) getraenk).bereiteKaffeeZu();
            }

        }


    }

}
