package ch.zhaw.pm2.supercoolmarkdown.model;

/**
 * This enum represents the Tags. These tags represent Styles in markdownSyntax
 *
 * @author Outitbad
 * @version 12.05.2021
 */
public enum Tag {
    BOLD("**"),
    CURSIVE("_"),
    STRIKETHROUGH("~~"),
    MONOSPACE("```"),
    UNDERLINESTART("<ins>"),
    UNDERLINEEND("</ins>"),
    FONTSTART("<font face=[PLACEHOLDER_FAMILY] size=[PLACEHOLDER_SIZE]>"),
    FONTEND("</font>");

    private final String formatTags;
    private Tag referencedTag;
    private String regexString;
    private String changedFormatTag;

    /**
     * Constructor of this class
     *
     * @param formatTags the tag content
     */
    Tag(String formatTags) {
        this.formatTags = formatTags;
        referencedTag = null;
        regexString = "";
    }

    /**
     * Initialize all tags
     */
    public static void initTags() {
        UNDERLINESTART.setReferencedTag(UNDERLINEEND);
        BOLD.setReferencedTag(BOLD);
        BOLD.setRegexString("\\*\\*");
        CURSIVE.setReferencedTag(CURSIVE);
        STRIKETHROUGH.setReferencedTag(STRIKETHROUGH);
        MONOSPACE.setReferencedTag(MONOSPACE);
        UNDERLINESTART.setReferencedTag(UNDERLINEEND);
        UNDERLINEEND.setReferencedTag(UNDERLINESTART);
        FONTSTART.setReferencedTag(FONTEND);
        FONTSTART.setRegexString("<font face=(.*?) size=(.*?)>");
        FONTEND.setReferencedTag(FONTSTART);
    }

    /**
     * Get the regex string
     *
     * @return The regex string
     */
    public String getRegexString() {
        String returnValue = "";
        if (regexString.isBlank()) {
            returnValue = formatTags;
        } else {
            returnValue = regexString;
        }
        return returnValue;
    }

    /**
     * Set a regex string
     *
     * @param regexString The regex string
     */
    public void setRegexString(String regexString) {
        this.regexString = regexString;
    }

    /**
     * Get the reference Tag
     *
     * @return the referenced Tag
     */
    public Tag getReferencedTag() {

        return this.referencedTag;
    }

    /**
     * Set a tag as referenced tag
     *
     * @param tag The referenceTag
     */
    public void setReferencedTag(Tag tag) {
        this.referencedTag = tag;
    }

    /**
     * Get the changedFormatTag
     *
     * @return The changedFormatTag
     */
    public String getChangedFormatTag() {
        return changedFormatTag;
    }

    /**
     * Set the changedFormatTag
     *
     * @param changedFormatTag The new formatted tag
     */
    public void setChangedFormatTag(String changedFormatTag) {
        this.changedFormatTag = changedFormatTag;
    }

    /**
     * @return the String value of the Tag
     */
    @Override
    public String toString() {
        return formatTags;
    }
}