package ch.zhaw.catan;

/**
 * This class functions as container for annotations that gets displayed
 * on the hexboard
 * @author Silvan
 */
public class Annotation {
    private String annotationContent;

    /**
     * Creates an annotation object with content
     * @param annotationContent content to be stored
     */
    public Annotation(String annotationContent){
        this.annotationContent = annotationContent;
    }

    /**
     * Updates the annotation content
     * @param annotationContent content
     */
    public void setAnnotationContent(String annotationContent){
        this.annotationContent = annotationContent;
    }

    /**
     * Prints the object
     * @return the annotation content
     */
    @Override
    public String toString() {
        return this.annotationContent;
    }
}
