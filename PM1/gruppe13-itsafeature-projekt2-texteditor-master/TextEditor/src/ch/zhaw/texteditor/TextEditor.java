package ch.zhaw.texteditor;

/**
 * This is the main class of the text editor
 * It contains the main program loop
 */
public class TextEditor {

    public static void main(String[] args) {
        //Define vars
        IOClass ioClass = new IOClass();
        boolean run = true;

        //Displays welcome message
        ioClass.displayWelcomeMessage();

        //Endless loop until a flag get set in the ch.zhaw.texteditor.classes.IOClass
        while (run) {
            ioClass.scanUserInput();
            run = !ioClass.getTerminateApplication(); //Invert because its more logic from the wording
        }
    }
}