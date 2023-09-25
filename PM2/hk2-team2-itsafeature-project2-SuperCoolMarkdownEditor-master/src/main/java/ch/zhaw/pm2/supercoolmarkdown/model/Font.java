package ch.zhaw.pm2.supercoolmarkdown.model;

import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class sets the font families and fontSizes.
 *
 * @author Baachsil
 * @version 11.05.2021
 */
public class Font {

    private final List<Integer> fontSizeList;
    private final List<String> fontFamilyList;

    /**
     * Constructor for the Font class
     *
     * It fills the fontSizeList and fontFamilyList
     */
    public Font() {
        fontSizeList = new ArrayList<>();
        fontSizeList.add(8);
        fontSizeList.add(10);
        fontSizeList.add(12);
        fontSizeList.add(14);
        fontSizeList.add(16);
        fontSizeList.add(20);
        fontSizeList.add(24);
        fontSizeList.add(26);
        fontSizeList.add(32);
        fontSizeList.add(48);
        fontSizeList.add(64);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontFamilies = ge.getAvailableFontFamilyNames();
        fontFamilyList = Arrays.asList(fontFamilies);
    }

    /**
     * This method returns a list with the fontSizes
     *
     * @return The fontSizes
     */
    public List<Integer> getFontSizeList() {
        return fontSizeList;
    }

    /**
     * This method returns a list with the fontFamilies
     *
     * @return The fontFamilies
     */
    public List<String> getFontFamilyList() {
        return fontFamilyList;
    }
}