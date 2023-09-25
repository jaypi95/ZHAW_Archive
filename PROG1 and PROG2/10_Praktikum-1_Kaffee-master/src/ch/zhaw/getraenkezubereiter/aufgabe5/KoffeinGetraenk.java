package ch.zhaw.getraenkezubereiter.aufgabe5;

public abstract class KoffeinGetraenk  implements trinkbar{



    public final void bereiteZu(){
        kocheWasser();
        braue();
        giesseInTasse();
        fuegeZutatenHinzu();

    }
    protected void kocheWasser() {
        // Implementieren Sie z.B. eine Ausgabe
        System.out.println("koch.....");
    }
    protected void giesseInTasse(){
        System.out.println("giess....");

    }
    protected void braue(){
    }

    protected void fuegeZutatenHinzu(){
    }
}
