public class testmain {
    public static void main(String[] args) {

        /**
         * Aufgabe 1
         */
        System.out.print("Aufgabe 1");
        System.out.print("\n");
        System.out.print("\n");
        System.out.println(-(8 + 1 / 3 + 5 * 4 + 8 * -2));
        System.out.println(1 + "x" + 9 / 10 + " is " + 10 / 5);
        System.out.println(12 % 9 * 12 % 9 * 12 % 9);
        System.out.println(2 - 4 / 8 * 4.0 + 12 / 5);
        System.out.println(0 + "1" + 3 + 5 + "7" + 7 * 5 + "3" + (1 + 0));
        System.out.println(0 > 7 || 12 < 10 + 4);




        /**
         * Aufgabe 2
         */
        System.out.print("\n");
        System.out.print("Aufgabe 2");
        System.out.print("\n");
        System.out.print("\n");

        int[] a1 = { 1, 1, 3 };
        method(a1);
        System.out.print("\n");
        for(int var:a1){
            System.out.print(var + ",");
        }

        int[] a2 = { 2, 1, 2, 4 };
        method(a2);
        System.out.print("\n");
        for(int var:a2){
            System.out.print(var + ",");
        }

        int[] a3 = { -1, 6, 3, 5, -3 };
        method(a3);
        System.out.print("\n");
        for(int var:a3){
            System.out.print(var + ",");
        }

        int[] a4 = { 7, 2, 3, 1, -3, 12 };
        method(a4);
        System.out.print("\n");
        for(int var:a4){
            System.out.print(var + ",");
        }


        /**
         * Aufgabe 4
         */
        System.out.print("\n");
        System.out.print("\n");
        System.out.print("Aufgabe 4");
        System.out.print("\n");
        System.out.print("\n");

        sonderbar(3,5);
        sonderbar(5,3);
        sonderbar(-3,6);
        sonderbar(2,12);

        /**
         * Aufgabe 5
         */
        System.out.print("\n");
        System.out.print("Aufgabe 5");
        System.out.print("\n");
        System.out.print("\n");

        Amphibien[] meinTerrarium = { new Amphibien(), new Lurche(),
                new Feuersalamander() };
        for (int i = 0; i < meinTerrarium.length; i++) {
            System.out.println(meinTerrarium[i]);
            meinTerrarium[i].info();
            System.out.println();
            meinTerrarium[i].status();
            System.out.println();
        }
        System.out.println("---");
        Lurche[] ihrTerrarium = { new SchwanzLurche(), new FroschLurche(),
                new Feuersalamander() };
        for (int i = 0; i < ihrTerrarium.length; i++) {
            System.out.println(ihrTerrarium[i]);
            ihrTerrarium[i].info();
            System.out.println();
            ihrTerrarium[i].zuerst();
        }

        /**
         * Aufgabe 6
         */
        System.out.print("\n");
        System.out.print("Aufgabe 6");
        System.out.print("\n");
        System.out.print("\n");

        One[] stock = { new Superior(5, 10), new Special(3, 6), new Deluxe(2, 8) };
        for (int i = 0; i < stock.length; i++) {
            System.out.println(stock[i].value(i));
        }


    }

    /**
     * Aufgabe 2
     * @param a
     */
    public static void method(int[] a) {
        for (int i = 0; i < a.length - 2; i++) {
            a[i+1] = (a[i] + a[i + 2]) / 2;
        }
    }

    /**
     * Aufgabe 4
     * @param i
     * @param j
     */
    public static void sonderbar(int i, int j) {
        int k = 0;
        while (i < j && k < j) {
            i = i + k;
            j--;
            k++;
            System.out.print(i + ", ");
        }
        System.out.println(k);
    }


}