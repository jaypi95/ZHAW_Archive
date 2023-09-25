package ch.zhaw.texteditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Manages Input / Output actions on the console
 * It's the VIEW from MVC Pattern
 * Validates all user input
 *
 * @author Silvan Baach
 * @version 2020.10.21
 */
public class IOClass {

    private StorageBucket storageBucket;
    private DataProcessing dataProcessing;
    private boolean terminateApplication;
    private Scanner scanner;

    /**
     * Creates an ch.zhaw.texteditor.classes.IOClass object
     */
    public IOClass() {
        storageBucket = StorageBucket.getInstance();
        dataProcessing = new DataProcessing();
        terminateApplication = false;
        scanner = new Scanner(System.in);
    }

    /**
     * This methods reads the user input
     */
    public void scanUserInput() {
        System.out.println(">>> Enter a command:");
        String userInput = scanner.nextLine();
        processUserInput(userInput);
    }

    /**
     * Displays a welcome message on the Console
     */
    public void displayWelcomeMessage() {
        System.out.println("Welcome to the texteditor \"VIM v2.0\"!");
        System.out.println("--------------------------------------");
        displayHelp();
    }

    /**
     * Displays a overview over all commands and their usage
     */
    private void displayHelp() {
        System.out.println("Use these commands to edit your text:");
        System.out.printf("%-15s %s%n", "ADD [n]:", "Adds a new paragraph after [n].");
        System.out.printf("%-15s %s%n", "DEL [n]:", "Deletes a an existing paragraph at [n].");
        System.out.printf("%-15s %s%n", "DUMMY [n]:", "Adds a new paragraph with a dummy text as content after [n].");
        System.out.printf("%-15s %s%n", "EXIT:", "Quits the application.");
        System.out.printf("%-15s %s%n", "FORMAT RAW:", "Sets the console output width to default.");
        System.out.printf("%-15s %s%n", "FORMAT FIX <b>:", "Sets the console output width to the value b.");
        System.out.printf("%-15s %s%n", "INDEX:", "Prints a word index of all paragraphs.");
        System.out.printf("%-15s %s%n", "PRINT:", "Prints all paragraphs.");
        System.out.printf("%-15s %s%n", "REPLACE [n]:", "Replaces words in paragraphs at [n]");
        System.out.printf("%-15s %s%n", "HELP:", "Displays this overview.");
        System.out.printf("%-15s %s%n", "[n]", "Optional parameter. Defaults to last paragraph.");
        System.out.println("------------------------------------------------------------------");
    }

    /**
     * validates all input that the user gives
     *
     * @param input from the user
     */
    public void processUserInput(String input) { //Expect an input in the Format "<Command> <Param>"
        boolean error = false; //Stores if an Error occurred in the method
        boolean validMainCommand;
        String mainCommand = "";
        String firstParam;
        int convertedParam = -3;

        //If input is empty -> error
        if (input.isEmpty()) {
            System.err.println("Error - Input is empty");
            error = true;
        } else {
            //Split the main command off the input
            input = input.trim();
            String[] mainCommandArr = input.split("[\\d+]");
            mainCommand = mainCommandArr[0];
            mainCommand = mainCommand.trim();

            //Check if the mainCommand is valid
            mainCommand = mainCommand.toUpperCase(); //Just in case...
            validMainCommand = dataProcessing.checkMainCommand(mainCommand);

            if (!validMainCommand) {
                System.err.println("Error - " + mainCommand + " is an unknown command");
                error = true;
            } else {
                //Is there more to validate in the input?
                //Grab the last part of the input
                if (input.contains(" ")) {
                    firstParam = input.substring(input.lastIndexOf(" "));
                    firstParam = firstParam.trim();

                    //Validate firstParam
                    convertedParam = dataProcessing.checkFirstParam(mainCommand, firstParam);
                    if (convertedParam == -1) {
                        System.err.println("Error - " + firstParam + " is an invalid parameter");
                        error = true;
                    } else if (convertedParam == -2) {
                        System.err.println("Error - The command FORMAT FIX needs a parameter");
                        error = true;
                    }
                }
            }
        }

        //Streamline the command FORMAT FIX and FORMAT RAW
        if (mainCommand.contains("FORMAT")) {
            mainCommand = "FORMAT";
        }

        //End of validation -> call the Methods
        if (!error) {
            executeCommand(mainCommand, convertedParam);
        }
    }

    /**
     * Sets a flag so the ch.zhaw.texteditor.classes.TextEditor class can stop the endless loop
     */
    private void exit() {
        terminateApplication = true;
    }

    /**
     * @return if the application should be terminated
     */
    public boolean getTerminateApplication() {
        return terminateApplication;
    }

    /**
     * Prints all contents from the storage bucket to the console
     */
    private void print() {
        //Just displays the content of the ArrayList
        ArrayList<String> paragraphList = storageBucket.getParagraphList();

        //If list is empty print an info text
        if (paragraphList.size() == 0) {
            System.out.println("There are no paragraphs stored yet. Use ADD [n] to add an new one.");
        } else {
            for (int i = 0; i < paragraphList.size(); i++) {

                if (storageBucket.getOutputWidth() != 0) {
                    //Split every paragraph in a arrayList with max len = outputWidth
                    ArrayList<String> minimizedTextList = dataProcessing.pageBreak(i);

                    System.out.println("<" + (i + 1) + ">:");

                    //Concat the format String
                    String format = "%-" + storageBucket.getOutputWidth() + "s\n";

                    for (String s : minimizedTextList) {
                        System.out.printf(format, s);
                    }

                    System.out.println("");
                } else {
                    //Just print with println
                    System.out.println("<" + (i + 1) + ">: " + paragraphList.get(i));
                }
            }
        }
    }

    /**
     * Prints the Index
     */
    private void printIndex() {
        HashMap<String, String> index = dataProcessing.index();
        for (Map.Entry<String, String> entry : index.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }


    /**
     * This method executes the command the user has given
     *
     * @param command    main command the user wants to execute
     * @param firstParam param user gives
     */
    private void executeCommand(String command, int firstParam) {
        //Sadly, runtime calling of different methods is not allowed (need java.lang.reflection) in this project so we just use a switch case
        //a nice solution would have been a hashmap with key = command and value = method to call in dataProcessing
        if (firstParam == -3) {
            firstParam = storageBucket.getParagraphCount();
        }

        switch (command) {
            case "ADD":
                System.out.println("Please enter a new Paragraph:");
                String furtherUserInput = scanner.nextLine();
                furtherUserInput = dataProcessing.filterParagraphInput(furtherUserInput);
                storageBucket.addNewParagraph(furtherUserInput, firstParam);
                System.out.println("Paragraph added");
                break;
            case "DEL":
                dataProcessing.deleteParagraph(firstParam);
                System.out.println("Paragraph " + (firstParam) + " deleted");
                break;
            case "DUMMY":
                dataProcessing.createNewDummyParagraph(firstParam);
                System.out.println("Created a new Dummy Paragraph");
                break;
            case "EXIT":
                exit(); //Sets a flag for the main class to terminate the while loop
                break;
            case "FORMAT":
                dataProcessing.setFormat(firstParam);
                System.out.println("New format set!");
                break;
            case "INDEX":
                printIndex();
                break;
            case "PRINT":
                print();
                break;
            case "REPLACE":
                System.out.println("Please enter a search term: ");
                String searchTerm = scanner.nextLine();
                System.out.println("Please enter a replace term: ");
                String replaceTerm = scanner.nextLine();
                dataProcessing.replace(searchTerm, replaceTerm, firstParam);
                System.out.println("All occurrences have been replaced");
                break;
            case "HELP":
                displayHelp();
                break;
            default:
                System.err.println("Error - An unexpected error occurred");
                break;
        }
    }
}
