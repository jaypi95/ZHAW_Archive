package ch.zhaw.pm2.supercoolmarkdown.model;

import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This class is responsible for converting the text written in the document into markdown syntax.
 *
 * @author Outitbad
 * @version 11.05.2021
 */
public class MarkDownWriter {

    private final List<Tag> activeTags;
    private final List<Tag> tagsBeforeChar;
    private String activeFont;

    /**
     * Create a MarkDownWriter object
     */
    public MarkDownWriter() {
        activeTags = new ArrayList<>();
        tagsBeforeChar = new ArrayList<>();
        activeFont = "";
    }

    /**
     * This method takes the text of the document and changes the syntax to markdown
     *
     * @return The text with markdown syntax
     */
    public String getMarkDownText(StyledDocument document) {
        activeTags.clear();
        tagsBeforeChar.clear();
        activeFont = "";

        StringBuilder markDownText = new StringBuilder();
        for (int character = 0; character < document.getLength(); character++) {
            //If the char is a space just write it in the markdown text
            try {
                if (" ".equals(document.getText(character, 1))) {
                    markDownText.append(document.getText(character, 1));
                } else {
                    addActiveTags(document.getCharacterElement(character));
                    markDownText.append(getTagsBeforeCharAsString()).append(document.getText(character, 1));
                    tagsBeforeChar.clear();
                }
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
        //Add Tags at the end
        return markDownText.append(addEndTags()).toString();
    }

    /**
     * This methods adds and removes tags depending on the status of the lists.
     * <p>
     * In case of the font this class checks if the new font is the same as the active, if it is not it will add the syntax to close the old font and add the new tag
     *
     * @param element The element to check
     */
    private void addActiveTags(Element element) {
        Map<Tag, Boolean> booleanForTags = checkIfTheCharGotAStyle(element);
        for (Tag tag : booleanForTags.keySet()) {
            if (tag == Tag.FONTSTART && !"".equals(activeFont) && booleanForTags.get(tag)) {
                if (!tag.getChangedFormatTag().equals(activeFont)) {
                    if (element.getStartOffset() != 0) {
                        tagsBeforeChar.add(tag.getReferencedTag());
                    }
                    tagsBeforeChar.add(tag);
                    activeFont = tag.getChangedFormatTag();
                }
            }
            //If the element has a certain style that is not active yet
            else if (booleanForTags.get(tag) && !activeTags.contains(tag)) {
                tagsBeforeChar.add(tag);
                activeTags.add(tag);
                if (tag == Tag.FONTSTART) {
                    activeFont = tag.getChangedFormatTag();
                }
            }
            //If the element does not have a certain style that is active
            else if (!booleanForTags.get(tag) && activeTags.contains(tag) && tag != Tag.FONTSTART) {
                tagsBeforeChar.add(tag.getReferencedTag());
                activeTags.remove(tag);
            }

        }
    }

    /**
     * This method checks with which style the element is styled and returns a map with the answers for each tag.
     * <p>
     * In case of the font this method sets the font of the current element as a changedFormatTag
     *
     * @param element The element that will be checked
     * @return A map with the Tags. The Tag is true, when the element is styled with the tag.
     */
    private Map<Tag, Boolean> checkIfTheCharGotAStyle(Element element) {
        Map<Tag, Boolean> booleanForTags = new HashMap<>();
        for (Tag tag : Tag.values()) {
            switch (tag) {
                case BOLD:
                    booleanForTags.put(tag, StyleConstants.isBold(element.getAttributes()));
                    break;
                case CURSIVE:
                    booleanForTags.put(tag, StyleConstants.isItalic(element.getAttributes()));
                    break;
                case STRIKETHROUGH:
                    booleanForTags.put(tag, StyleConstants.isStrikeThrough(element.getAttributes()));
                    break;
                case MONOSPACE:
                    booleanForTags.put(tag, checkIfFontIsMonospaced(StyleConstants.getFontFamily(element.getAttributes())));
                    break;
                case UNDERLINESTART:
                    booleanForTags.put(tag, StyleConstants.isUnderline(element.getAttributes()));
                    break;
                case FONTSTART:
                    if (booleanForTags.get(Tag.MONOSPACE)) {
                        booleanForTags.put(Tag.FONTSTART, false);
                    } else {
                        setFont(getCharFontFamily(element), getCharFontSize(element).toString());
                        booleanForTags.put(Tag.FONTSTART, true);
                    }
            }
        }
        return booleanForTags;
    }

    /**
     * This method takes all tags from the tagsBeforeChar list and returns them as a string
     *
     * @return The tags as String
     */
    private String getTagsBeforeCharAsString() {
        StringBuilder tagsBeforeChar = new StringBuilder();
        Optional<String> tagAsString;
        ArrayList<Integer> indexesOfTags = getStructuredTagIndexes(this.tagsBeforeChar);

        for (Integer indexesOfTag : indexesOfTags) {
            //Check if the Tag has a changedFormat
            tagAsString = Optional.ofNullable(this.tagsBeforeChar.get(indexesOfTag).getChangedFormatTag());
            //If there is no changedFormat, add the Tag
            if (tagAsString.isEmpty()) {
                if (this.tagsBeforeChar.get(indexesOfTag).toString().startsWith("</")) {
                    tagsBeforeChar.insert(0, this.tagsBeforeChar.get(indexesOfTag).toString());
                } else {
                    tagsBeforeChar.append(this.tagsBeforeChar.get(indexesOfTag).toString());
                }
                //If there is a changedFormat, add the changedTag
            } else {
                //This if/else is used, so that for example the font gets closed before a new font gets opened
                if (this.tagsBeforeChar.get(indexesOfTag).toString().startsWith("</")) {
                    tagsBeforeChar.insert(0, this.tagsBeforeChar.get(indexesOfTag).getChangedFormatTag());
                } else {
                    tagsBeforeChar.append(this.tagsBeforeChar.get(indexesOfTag).getChangedFormatTag());
                }
            }
        }
        return tagsBeforeChar.toString();
    }

    /**
     * This method checks if the fontFamily is monospaced
     *
     * @param fontFamily The fontFamily
     * @return True if the font is monospaced
     */
    private boolean checkIfFontIsMonospaced(String fontFamily) {
        return fontFamily.equals("Monospaced");
    }

    /**
     * Get the fontFamily of the element
     *
     * @param element The element
     * @return The fontFamily
     */
    private String getCharFontFamily(Element element) {
        return StyleConstants.getFontFamily(element.getAttributes());
    }

    /**
     * Get the fontSize of the element
     *
     * @param element The element
     * @return The fontSize
     */
    private Integer getCharFontSize(Element element) {
        return StyleConstants.getFontSize(element.getAttributes());
    }

    /**
     * Set the font as a changedFormatTag
     *
     * @param fontFamily The family
     * @param fontSize   The size
     */
    private void setFont(String fontFamily, String fontSize) {
        Tag.FONTSTART.setChangedFormatTag(Tag.FONTSTART.toString().replace("[PLACEHOLDER_FAMILY]", fontFamily));
        Tag.FONTSTART.setChangedFormatTag(Tag.FONTSTART.getChangedFormatTag().replace("[PLACEHOLDER_SIZE]", fontSize));
    }

    /**
     * This method returns the Tags to close the markdown text.
     *
     * @return The tags at the end of the markDownText
     */
    private String addEndTags() {
        StringBuilder endTags = new StringBuilder();
        ArrayList<Integer> indexesOfTags = getStructuredTagIndexes(activeTags);
        //If there was one active font a endFont will be added
        for (Integer indexesOfTag : indexesOfTags) {
            //This if/else is used, so that closing html tags get written before the other tags
            if (activeTags.get(indexesOfTag).getReferencedTag().toString().startsWith("</")) {
                endTags.insert(0, activeTags.get(indexesOfTag).getReferencedTag());
            } else {
                endTags.append(activeTags.get(indexesOfTag).getReferencedTag());
            }
        }
        return endTags.toString();
    }

    /**
     * This method takes a list of tags and returns a list with structured indexes of the given list
     * The structure is based on the Tag class
     *
     * @param list The list that will be sorted
     * @return A list with the structured indexes
     */
    private ArrayList<Integer> getStructuredTagIndexes(List<Tag> list) {
        ArrayList<Integer> indexesOfTags = new ArrayList<>();
        //Make a list with the indexes of the tag, so the tags are always in the same order
        for (Tag tag : Tag.values()) {
            if (list.contains(tag)) {
                indexesOfTags.add(list.indexOf(tag));
            }
        }
        return indexesOfTags;
    }

}