package ch.zhaw.ads;

/**
 Interface für Abstrakten Datentyp (ADT) experimentbox.test.ch.zhaw.ads.Stack
 */
public interface Stack {
    /**
     Legt eine neues Objekt auf den experimentbox.test.ch.zhaw.ads.Stack, falls noch nicht voll.
     @param x ist das Objekt, das dazugelegt wird.
     */
    void push (Object x)   throws StackOverflowError;

    /**
     Entfernt das oberste und damit das zuletzt eingefügte Objekt.
     Ist der experimentbox.test.ch.zhaw.ads.Stack leer, wird null zurückgegeben.
     @return Gibt das oberste Objekt zurück oder null, falls leer.
     */
    Object pop ();

    /**
     Testet, ob der experimentbox.test.ch.zhaw.ads.Stack leer ist.
     @return Gibt true zurück, falls der experimentbox.test.ch.zhaw.ads.Stack leer ist.
     */
    boolean isEmpty();
    /**
     Gibt das oberste Objekt zurück, ohne es zu entfernen.
     Ist der experimentbox.test.ch.zhaw.ads.Stack leer, wird null zurückgegeben.
     @return Gibt das oberste Objekt zurück oder null, falls leer.
     */
    Object peek ();

    /**
     Entfernt alle Objekte vom experimentbox.test.ch.zhaw.ads.Stack. Ein Aufruf von isEmpty()
     ergibt nachher mit Sicherheit true.
     */
    void removeAll ();

    /**
     Testet, ob der experimentbox.test.ch.zhaw.ads.Stack voll ist.
     @return Gibt true zurück, falls der experimentbox.test.ch.zhaw.ads.Stack voll ist.
     */
    boolean isFull();
}