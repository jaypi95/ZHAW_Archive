package ch.zhaw.texteditor;

import java.util.*;
import java.util.regex.Pattern;

/**
 * This class executes all commands from the Console.
 * Its the CONTROLLER from the MVC Pattern
 *
 * @author Badr O & Julian P
 * @version 2020-11-05
 */
public class DataProcessing {

    private StorageBucket storageBucket;
    private Random randomNumberGenerator;
    private ArrayList<String> listOfDummyWords;

    /**
     * Create an object of ch.zhaw.texteditor.classes.DataProcessing and fill the Array with dummy words.
     */
    public DataProcessing() {
        storageBucket = StorageBucket.getInstance();
        randomNumberGenerator = new Random();
        listOfDummyWords = new ArrayList<>();
        fillArrayWithDummyParagraphs();
    }

    /**
     * Add a paragraph after a specific Index
     *
     * @param content        The content of the paragraph
     * @param indexParagraph The Paragraph before our new Paragraph
     */
    public void addParagraph(String content, int indexParagraph) {
        if (indexParagraph >= 0) {
            storageBucket.addNewParagraph(content, indexParagraph);
        }
    }

    /**
     * This method filters the userInput and removes all sorts of special chars
     *
     * @param input input that the user makes when calling the ADD command
     * @return the filtered userInput
     */
    public String filterParagraphInput(String input) {
        //Replaces all special chars - allowed punctuation marks are listed here: https://de.wikipedia.org/wiki/Satzzeichen
        return input.replaceAll("[^\\p{L}0-9.:;?!()'*\" -]", "");
    }

    /**
     * Delete a specific paragraph
     *
     * @param indexParagraph The specific paragraph that is targeted
     */
    public void deleteParagraph(int indexParagraph) {
        indexParagraph--;
        if (indexParagraph >= 0) {
            storageBucket.deleteParagraph(indexParagraph);
        }
    }

    /**
     * Create a Paragraph with Dummy words
     *
     * @param indexParagraph The Paragraph before our new Paragraph
     */
    public void createNewDummyParagraph(int indexParagraph) {
        //Create a random index and assign a dummyParagraph with that value
        int randomIndex = randomNumberGenerator.nextInt(listOfDummyWords.size());
        String randomDummyParagraph = listOfDummyWords.get(randomIndex);
        addParagraph(randomDummyParagraph, indexParagraph);
    }

    /**
     * Set the format width for the paragraphs
     *
     * @param width The width of the Paragraphs
     */
    public void setFormat(int width) {
        if (width >= 0) {
            storageBucket.setOutputWidth(width);
        }
    }

    /**
     * Replace a specific String with a new one
     *
     * @param searchTerm     The old String that gets replaced
     * @param replaceTerm    The new String that will replace the old String
     * @param indexParagraph The specific paragraph that is targeted
     */
    public void replace(String searchTerm, String replaceTerm, int indexParagraph) {
        indexParagraph--;
        String copyOfParagraph = storageBucket.getParagraph(indexParagraph);
        //Checks if the paragraph contains the word to replace and is not ""
        if (copyOfParagraph.contains(searchTerm) && !"".equals(searchTerm)) {
            //Create a new paragraph with the replaced word
            String newParagraph = copyOfParagraph.replaceAll(filterParagraphInput(searchTerm), filterParagraphInput(replaceTerm));
            storageBucket.updateParagraph(newParagraph, indexParagraph);
        }
    }

    /**
     * If the paragraphs are wider than the width of the console,
     * cut the paragraphs so they fit into the width of the console
     * PageBreaks happen at Whitespaces if possible
     *
     * @param indexParagraph The specific Paragraph that needs to be cut
     * @return Return a ArrayList with the cutted Strings
     */
    public ArrayList<String> pageBreak(int indexParagraph) {
        ArrayList<String> splittedParagraphs = new ArrayList<String>();
        int width = storageBucket.getOutputWidth();
        String copyOfParagraph = storageBucket.getParagraph(indexParagraph);
        String subParagraph = "";

        //Loops while the copyOfParagraph is not empty
        while (!copyOfParagraph.isEmpty()) {
            copyOfParagraph = copyOfParagraph.trim();
            //If the String has place in the width of the Paragraph
            if (copyOfParagraph.length() <= width) {
                splittedParagraphs.add(copyOfParagraph);
                copyOfParagraph = "";
            } else {
                //Checks if there isn't a whitespace to split the String
                //If there is no whitespace, cut the String at the length of the console width
                if (copyOfParagraph.lastIndexOf(" ", width - 1) == -1) {
                    subParagraph = copyOfParagraph.substring(0, width);
                    copyOfParagraph = copyOfParagraph.substring(width, copyOfParagraph.length());
                }
                //Cuts the String at the last possible whitespace that is still in the range of the console width
                else {
                    int index = copyOfParagraph.lastIndexOf(" ", width - 1);
                    subParagraph = copyOfParagraph.substring(0, index);
                    copyOfParagraph = copyOfParagraph.substring(index, copyOfParagraph.length());
                }
                splittedParagraphs.add(subParagraph);
            }
        }
        return splittedParagraphs;
    }


    /**
     * Fills the Array with dummy words
     * The dummy words are taken from the StorageBucket
     */
    private void fillArrayWithDummyParagraphs() {
        String dummyParagraphs = storageBucket.getDefaultText();
        //Split the String with the dummy paragraphs at every "."
        String[] temporaryArray = dummyParagraphs.split("[.]");
        //Add the splitted paragraphs in the ArrayList this.listOfDummyWords
        for (String dummyParagraph : temporaryArray) {
            listOfDummyWords.add(dummyParagraph.trim());
        }
    }

    /**
     * Checks if the mainCommand is valid
     *
     * @param mainCommand first part of the user input
     * @return if the mainCommand is a valid command
     */
    public boolean checkMainCommand(String mainCommand) {
        String allCommands = ";ADD;DEL;DUMMY;FORMAT FIX;EXIT;INDEX;PRINT;REPLACE;FORMAT RAW;HELP;";
        allCommands = allCommands + ";";
        int index = allCommands.indexOf(mainCommand);

        //Test if the mainCommand is in allCommands
        return index > 0;
    }

    /**
     * Checks if the first param after the mainCommand is valid
     * This depends on what mainCommand the user has given
     *
     * @param mainCommand main command the user wants to execute
     * @param firstParam  parameter which should be validated
     * @return if the firstParam is valid
     */
    public int checkFirstParam(String mainCommand, String firstParam) {
        int convertedFirstParam = 0;

        //If firstParam = FIX -> error because there must be a param
        if ("FIX".equalsIgnoreCase(firstParam)) {
            convertedFirstParam = -2;
        }

        //is the firstParam not a number?
        if (firstParam.matches("\\d+")) {
            convertedFirstParam = Integer.parseInt(firstParam);
            if (convertedFirstParam < 0) {
                convertedFirstParam = -1; //Error value can't be below zero
            } else {
                //In this case firstParam can be 0 or max. paragraph list.length
                if ("ADD".equalsIgnoreCase(mainCommand) | "DUMMY".equalsIgnoreCase(mainCommand)) {
                    if (convertedFirstParam > storageBucket.getParagraphCount()) {
                        convertedFirstParam = -1;
                    }
                } else if ("DEL".equalsIgnoreCase(mainCommand) | "REPLACE".equalsIgnoreCase(mainCommand)) {
                    //In this case firstparam must be 1 or max. paragraph list.length
                    if (convertedFirstParam == 0 | convertedFirstParam > storageBucket.getParagraphCount()) {
                        convertedFirstParam = -1;
                    }
                } else if ("FORMAT RAW".equalsIgnoreCase(mainCommand)) {
                    //When main Command is format Raw there cant be a param
                    convertedFirstParam = -1;
                }
            }
        } else {
            //There are only 2 possibilities RAW and FIX
            if (!"FIX".equalsIgnoreCase(firstParam) && !"RAW".equalsIgnoreCase(firstParam)) {
                convertedFirstParam = -1;
            }
        }
        return convertedFirstParam;
    }

    /**
     * The index outputs a list of all words which appear more than three times over all paragraphs.
     * After each word it lists the number of each paragraph in which this word appears.
     * List means a list like a non-programmer would understand it.
     *
     * @return Map of all words and in what paragraph they occur.
     */
    public HashMap<String, String> index() {
        ArrayList<String> content = storageBucket.getParagraphList();
        ArrayList<String[]> splitWords = new ArrayList<>();
        ArrayList<String> listOfWords = new ArrayList<>();
        HashMap<String, Integer> occurrence = new HashMap<>();
        HashMap<String, String> indexMap = new HashMap<>();

        //Removes all special characters and splits the words
        for (String paragraphs : content) {
            //Regex1: Removes all special characters and numbers. Regex2: Removes spaces. Takes 2 Regex to be able to distinguish each word.
            splitWords.add(paragraphs.replaceAll("[^\\p{L}0-9 ]", "").split("\\s+"));
        }

        //Creates list out of returned array
        for (String[] words : splitWords) {
            listOfWords.addAll(Arrays.asList(words));
        }

        //Counts each word and puts them in a HashMap with the word as a key and the occurrence as the value
        for (String words : listOfWords) {
            Integer occurs = occurrence.get(words);
            occurrence.put(words, (occurs == null) ? 1 : occurs + 1);
        }

        //Removes all words that occur less than 4 times and don't start with a capital
        for (Iterator<Map.Entry<String, Integer>> it = occurrence.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Integer> map = it.next();
            char[] charArray = map.getKey().toCharArray();
            if (map.getValue() < 4) {
                it.remove();
            } else if (!Character.isUpperCase(charArray[0])) {
                it.remove();
            }

        }

        //Searches for all remaining words in the text and puts their paragraph in a HashMap
        for (Map.Entry<String, Integer> words : occurrence.entrySet()) {
            ArrayList<Integer> indexes = new ArrayList<>();
            StringBuilder strBuilder = new StringBuilder();
            //Loops through the paragraphs and adds the paragraph number of each occurrence to a list
            for (int i = 0; i < content.size(); i++) {
                String paragraphs = content.get(i);
                //Regex: Checks only whole words (so: Random gets counted as Random. Randomize does not).
                if (paragraphs.matches("(^\\b|.*\\b)" + Pattern.quote(words.getKey()) + "\\b.*")) {
                    indexes.add(i + 1);
                }
            }
            //Iterates through the previously filled list to turn it into a comma delimited StringBuilder
            Iterator<Integer> it = indexes.iterator();
            while (it.hasNext()) {
                strBuilder.append(it.next());
                if (it.hasNext()) {
                    strBuilder.append(", ");
                }
            }
            //Fills HashMap with word as Value and comma delimited String for paragraph number
            indexMap.put(words.getKey(), String.join(",", strBuilder.toString()));

        }
        //Output if no words where added to the map.
        if (indexMap.isEmpty()) {
            indexMap.put("This index does not contain any words.", "");
        }
        return indexMap;
    }
}

