package ch.zhaw.pm2.supercoolmarkdown.model;


import ch.zhaw.pm2.supercoolmarkdown.exceptions.DamagedFileException;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ch.zhaw.pm2.supercoolmarkdown.model.Tag.*;
import static java.lang.Integer.parseInt;

/**
 * This class reads the content of a file containing markdown tags and parses them to attributes which can be applied to a JTextPane
 *
 * @author peterju1 & baachsil
 * @version 2021-05-14
 */
public class MarkDownInterpreter {

    /**
     * Map that contains a pattern for each tag that has to be verified.
     * It is being used to validate the file
     */
    private final Map<Tag, String> MARKDOWN_VERIFICATION_PATTERNS = new HashMap<>() {{
        put(BOLD, "\\*\\*(.*?)\\*\\*");
        put(CURSIVE, "_(.*?)_");
        put(STRIKETHROUGH, "~~(.*?)~~");
        put(MONOSPACE, "```(.*?)```");
        put(UNDERLINESTART, "<ins>(.*?)</ins>"); //This has start in the name so we can use a correct ENUM as the key. But it contains the whole pattern here.
        put(FONTSTART, "<font face=(.*?) size=(.*?)>(.*?)<\\/font>"); //This has start in the name so we can use a correct ENUM as the key. But it contains the whole pattern here.
    }};

    /**
     * String which is being used to scan the document for tags and remove them
     * It looks complicated but the purpose is that you're able to escape a tag such as \* to use asterisks in your text without it being removed.
     */
    private final String MARKDOWN_REMOVAL_PATTERN = "(?<!\\\\)(?:\\\\\\\\)*(\\\\)|(?<!\\\\)(?:\\\\\\\\)*(_)|(?<!\\\\)(?:\\\\\\\\)*(\\*\\*)|(?<!\\\\)(?:\\\\\\\\)*(~~)|(?<!\\\\)(?:\\\\\\\\)*(```)|(?<!\\\\)(?:\\\\\\\\)*(<ins>)|(?<!\\\\)(?:\\\\\\\\)*(</ins>)|<font face=(.*?) size=(.*?)>|</font>";

    /**
     * This list contains ParsedContent Objects which in turn contain information about how to format the text in the JTextPane
     */
    private final List<ParsedContent> formattingInfo = new ArrayList<>();
    private String textToParse;
    private int ignoreSymbolsBefore = 0;

    /**
     * Runs the file validation and parses the plain text containing markdown tags to actual rich text
     *
     * @return a list of indices and formatting options to format the text on the display
     * @throws DamagedFileException if the markdown tags are not valid (e.g opening tag is there but closing tag is missing)
     */
    public List<ParsedContent> interpretMarkdown(String content) throws DamagedFileException {
        if (validateFile(content)) {
            parseToRichText(content);
        }
        return formattingInfo;
    }

    /**
     * Removes the markdown tags from the displayed text so the user doesn't see the tags itself and only the formatted rich text
     *
     * @param markdownContent text containing the plain text with all markdown tags
     * @return text with all markdown tags removed
     */
    public String removeAllMarkdownTags(String markdownContent) {
        return markdownContent.replaceAll(MARKDOWN_REMOVAL_PATTERN, "");
    }

    /**
     * Counts each markdown tag it encounters. If the number of each different tag is even, the file is valid.
     *
     * @param content the plain content of the file with markdown tags
     * @return true if the file matches the pattern
     * @throws DamagedFileException if the markdown tags are not valid (e.g opening tag is there but closing tag is missing)
     */
    private boolean validateFile(String content) throws DamagedFileException {
        boolean fileValid = false;
        // Looping through all patterns to verify each tag individually
        for (Tag pattern : Tag.values()) {
            int numberOfMatches = 0;
            int numberOfSymmetricMatches = 0;
            int numberOfAsymmetricMatches = 0; //special variable to count asymmetric tags since they differ between start and end tag


            boolean tagIsAsymmetric = pattern.getRegexString().matches(UNDERLINESTART.getRegexString()) || pattern.getRegexString().matches(UNDERLINEEND.getRegexString()) || pattern.getRegexString().matches(FONTSTART.getRegexString()) || pattern.getRegexString().matches(FONTEND.getRegexString());
            Pattern validationPattern = Pattern.compile(pattern.getRegexString());
            Matcher patternMatcher = validationPattern.matcher(content);

            while (patternMatcher.find()) {
                //Special if for asymmetric tags to count the overall matches
                if (tagIsAsymmetric) {
                    numberOfAsymmetricMatches++;
                } else {
                    numberOfSymmetricMatches++;
                }
            }
            //look if there is the same number of corresponding end tags
            if (tagIsAsymmetric) {
                Pattern endTagPattern = Pattern.compile(pattern.getReferencedTag().getRegexString());
                Matcher endTagMatcher = endTagPattern.matcher(content);
                int numberOfEndTags = 0;

                while (endTagMatcher.find()) {
                    numberOfEndTags++;
                }
                //combine the two to get the number of overall tags. If the number is even, the file is valid.
                numberOfMatches = numberOfAsymmetricMatches + numberOfEndTags;
            } else {
                numberOfMatches = numberOfSymmetricMatches;
            }

            if (calculateErrorLevel(numberOfMatches) == 0) {
                fileValid = true;
            } else {
                throw new DamagedFileException("Markdown Syntax does not match expected pattern");
            }
        }
        return fileValid;
    }

    /**
     * This method calculates the errorLevel and returns it.
     * It does so based on the number of matches it gets from the validateFile method.
     * If the number of matches is not dividable by two it raises the errorLevel. A level higher than one indicates a broken file.
     *
     * @param numberOfMatches the number of symmetric tags that were found
     * @return a number indicating if there was an error or not
     */
    private int calculateErrorLevel(int numberOfMatches) {
        int validationErrorLevel = 0;

        if (numberOfMatches % 2 != 0) {
            validationErrorLevel++;
        }

        return validationErrorLevel;
    }

    /**
     * This method calls a method to set the correct formatting attributes for each tag
     *
     * @param content the plain content of the file with markdown tags
     */
    private void parseToRichText(String content) {

        if (content.matches("(\\s|\\S)*?" + BOLD.getRegexString() + "(\\s|\\S)*?")) {
            parseBold(content);
        }
        if (content.matches("(\\s|\\S)*?" + CURSIVE.getRegexString() + "(\\s|\\S)*?")) {
            parseCursive(content);
        }
        if (content.matches("(\\s|\\S)*?" + STRIKETHROUGH.getRegexString() + "(\\s|\\S)*?")) {
            parseStrikeThrough(content);
        }
        if (content.matches("(\\s|\\S)*?" + UNDERLINESTART.getRegexString() + "(\\s|\\S)*?")) {
            parseUnderline(content);
        }
        if (content.matches("(\\s|\\S)*?" + MONOSPACE.getRegexString() + "(\\s|\\S)*?")) {
            parseMonospace(content);
        }
        if (content.matches("(\\s|\\S)*?" + FONTSTART.getRegexString() + "(\\s|\\S)*?")) {
            parseFontAndSize(content);
        }

    }

    /**
     * Sets an attribute set for its tag and calls the matchFormattingPatterns method to scan for those tags
     *
     * @param text the plain content of the file with markdown tags
     */
    private void parseBold(String text) {
        MutableAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setBold(attributeSet, true);

        //Find text which is between bold tags
        matchFormattingPatterns(text, attributeSet, "BOLD");
    }

    /**
     * Sets an attribute set for its tag and calls the matchFormattingPatterns method to scan for those tags
     *
     * @param text the plain content of the file with markdown tags
     */
    private void parseStrikeThrough(String text) {
        MutableAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setStrikeThrough(attributeSet, true);

        //Find text which is between bold tags
        matchFormattingPatterns(text, attributeSet, "STRIKETHROUGH");
    }

    /**
     * Sets an attribute set for its tag and calls the matchFormattingPatterns method to scan for those tags
     *
     * @param text the plain content of the file with markdown tags
     */
    private void parseUnderline(String text) {
        MutableAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setUnderline(attributeSet, true);

        //Find text which is between bold tags
        matchFormattingPatterns(text, attributeSet, "UNDERLINESTART");
    }

    /**
     * Sets an attribute set for its tag and calls the matchFormattingPatterns method to scan for those tags
     *
     * @param text the plain content of the file with markdown tags
     */
    private void parseCursive(String text) {
        MutableAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setItalic(attributeSet, true);

        //Find text which is between bold tags
        matchFormattingPatterns(text, attributeSet, "CURSIVE");
    }

    /**
     * Sets an attribute set for its tag and calls the matchFormattingPatterns method to scan for those tags
     *
     * @param text the plain content of the file with markdown tags
     */
    private void parseMonospace(String text) {
        MutableAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setFontFamily(attributeSet, "Monospaced");

        //Find text which is between bold tags
        matchFormattingPatterns(text, attributeSet, "MONOSPACE");
    }

    /**
     * Sets an attribute set for its tag and calls the matchFormattingPatterns method to scan for those tags
     *
     * @param text the plain content of the file with markdown tags
     */
    private void parseFontAndSize(String text) {
        Pattern regexPattern = Pattern.compile(FONTSTART.getRegexString(), Pattern.DOTALL);
        Matcher patternMatcher = regexPattern.matcher(text);

        //For fonts, this has to be done beforehand. Otherwise it wouldn't remove all unwanted tags.
        textToParse = removeMarkdownTagsWithException(text, FONTSTART.toString(), FONTEND.toString());

        while (patternMatcher.find()) {
            String formattingPattern = "";
            String fontFamily = "";
            int fontSize = 0;

            //this basically creates new tags on the fly so matchFormattingPatterns can search for them. Otherwise there would be issues with the indices.
            formattingPattern = "<font face=" + patternMatcher.group(1) + " size=" + patternMatcher.group(2) + ">(.*?)<\\/font>";
            fontFamily = patternMatcher.group(1);
            fontSize = parseInt(patternMatcher.group(2));

            //Set attribute to desired font and size
            MutableAttributeSet attributeSet = new SimpleAttributeSet();
            StyleConstants.setFontFamily(attributeSet, fontFamily);
            StyleConstants.setFontSize(attributeSet, fontSize);

            //Find text which is between bold tags
            matchFormattingPatterns(text, attributeSet, formattingPattern);
        }

    }

    /**
     * Scans the file for text that is between two markdown tags and adds an entry to the formattingInfo list for each match.
     * The entry consists of the content itself, the indices where it has been found, the attributeSet which contains the information on how to actually format the text and a boolean if previous formatting should be replaced or not
     *
     * @param text         the plain content of the file with markdown tags
     * @param attributeSet information on how to format the matched text
     * @param stringTag    the tag which should be searched for (e.g BOLD)
     */
    private void matchFormattingPatterns(String text, MutableAttributeSet attributeSet, String stringTag) {
        String startTag = "";
        String endTag = "";
        String verificationTag = "";
        ignoreSymbolsBefore = 0;
        boolean isFontTag = false;

        //Check if it's a font tag since they have to be treated specially
        if (stringTag.matches("(\\s|\\S)*?" + FONTSTART.getRegexString() + "(\\s|\\S)*?")) {
            startTag = stringTag.split(">")[0] + ">";
            endTag = FONTEND.toString();
            verificationTag = stringTag;
            isFontTag = true;

        } else {
            startTag = Tag.valueOf(stringTag).toString();
            endTag = Tag.valueOf(stringTag).getReferencedTag().toString();
            verificationTag = MARKDOWN_VERIFICATION_PATTERNS.get(valueOf(stringTag));
            //remove all unnecessary markdown tags
            textToParse = removeMarkdownTagsWithException(text, startTag, endTag);
        }
        Pattern regexPattern = Pattern.compile(verificationTag, Pattern.DOTALL);
        Matcher patternMatcher = regexPattern.matcher(textToParse);
        //match the pattern
        //for font tags, we only want to match the first tag so our calculations work correctly
        if (isFontTag) {
            if (patternMatcher.find()) {
                genericTagPatternMatcher(patternMatcher, startTag, endTag, stringTag, attributeSet);
            }
        } else {
            while (patternMatcher.find()) {
                genericTagPatternMatcher(patternMatcher, startTag, endTag, stringTag, attributeSet);
            }
        }
    }

    /**
     * Matches the pattern and applies the attribute set accordingly.
     *
     * @param patternMatcher the matcher including the text which has to be parsed
     * @param startTag       the starting tag which should be searched for
     * @param endTag         the end tag which should be searched for
     * @param stringTag      the complete tag as it was given by the parseToSomething method
     * @param attributeSet   the attributeSet containing the attributes which should be applied
     */
    private void genericTagPatternMatcher(Matcher patternMatcher, String startTag, String endTag, String stringTag, MutableAttributeSet attributeSet) {
        //the tag blockLength -> just to simplify all the formulas
        int startTagLength = startTag.length();
        int endTagLength = endTag.length();

        //Content of the text which should be formatted
        String content = patternMatcher.group(1);

        //Count how many symbols should be removed from the startIndex
        //Formula: (2 * howManyFormattingBlocksAreAlreadyProcessed * startTagLength) - startTagLength (Because we need to ignore the end Tag from the last block)
        ignoreSymbolsBefore = ignoreSymbolsBefore + startTagLength;

        //The blockLength of this block
        int blockLength = patternMatcher.group().length() - startTagLength - endTagLength;

        //The start index of this block (adjusted)
        int startIndex = patternMatcher.start(1) - ignoreSymbolsBefore;

        formattingInfo.add(new ParsedContent(content, startIndex, blockLength, attributeSet, false));
        ignoreSymbolsBefore = ignoreSymbolsBefore + endTagLength;
        if (stringTag.startsWith("<")) {
            textToParse = textToParse.replaceFirst(startTag, "");
            textToParse = textToParse.replaceFirst(endTag, "");
        }
    }

    /**
     * Removes all markdown tags with the exception of the two desired tags
     *
     * @param markdownContent     the content
     * @param exceptionalTagStart the start tag which should be ignored
     * @param exceptionalTagEnd   the end tag which should be ignored (if there is non just use the same tag as the start)
     * @return The string without any markdown tags except for the tags we want to match later on
     */
    private String removeMarkdownTagsWithException(String markdownContent, String exceptionalTagStart, String exceptionalTagEnd) {
        for (Map.Entry<Tag, String> pair : MARKDOWN_VERIFICATION_PATTERNS.entrySet()) {
            if ((!pair.getKey().toString().equals(exceptionalTagStart) && !pair.getKey().toString().equals(exceptionalTagEnd)) && !pair.getKey().toString().equals(FONTSTART.toString())) {
                Tag tagToReplace = pair.getKey();
                markdownContent = markdownContent.replaceAll(tagToReplace.getRegexString(), "");
                markdownContent = markdownContent.replaceAll(tagToReplace.getReferencedTag().getRegexString(), "");
            }
        }
        if (!exceptionalTagEnd.equals(FONTEND.toString())) {
            markdownContent = markdownContent.replaceAll("<font face=(.*?) size=(.*?)>", "");
            markdownContent = markdownContent.replaceAll(FONTEND.toString(), "");
        }
        return markdownContent;
    }

}
