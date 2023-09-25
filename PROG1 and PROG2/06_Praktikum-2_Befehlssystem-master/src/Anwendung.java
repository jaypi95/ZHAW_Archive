import java.util.Scanner;
public class Anwendung {

    private static Scanner scanner;
    private static boolean run;
    private Kontroller kontroller = new Kontroller();

    public Anwendung() {
        scanner = new Scanner(System.in);
        run = true;

    }
    public static void main(String[] args){

        Anwendung anwendung = new Anwendung();
        while (run) {
            String input = scanner.nextLine();
            anwendung.start(input);
        }
    }
    private void start(String input){
        String hauptBefehl = "";
        String befehl2 = "";
        input = input.trim();
        String[] input1 = input.split("[\\d+]");

        hauptBefehl = input1[0];
        hauptBefehl = hauptBefehl.trim();

        if(input.contains(" ")){
            befehl2 = input.substring(input.lastIndexOf(" "));
            befehl2 = befehl2.trim();
        }
        Befehl befehl = new Befehl(hauptBefehl,befehl2);


        run = kontroller.verarbeiteBefehl(befehl);


    }

}
