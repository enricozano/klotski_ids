package klotski_ids.controllers.util;

import java.util.List;

/**
 * A representation of a Klotski level, consisting of a list of components (rectangles) and information on the minimum
 * <p>
 * and maximum width and height of the playing area.
 */
public class Level {

    /**
     * The maximum width of the playing area.
     */
    private int maxWidth;

    /**
     * The maximum height of the playing area.
     */
    private int maxHeight;

    /**
     * The minimum width of the playing area.
     */
    private int minWidth;

    /**
     * The minimum height of the playing area.
     */
    private int minHeight;

    /**
     * The list of components (rectangles) that make up the level.
     */
    private final List<Components> rectangles;

    /*******************************************************************************
     *                              Constructors                                   *
     *******************************************************************************/


    /**
     * Constructs a level with the given components and playing area dimensions.
     *
     * @param rectangles the list of components (rectangles) that make up the level
     * @param maxWidth   the maximum width of the playing area
     * @param maxHeight  the maximum height of the playing area
     * @param minWidth   the minimum width of the playing area
     * @param minHeight  the minimum height of the playing area
     */
    public Level(List<Components> rectangles, int maxWidth, int maxHeight, int minWidth, int minHeight) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.rectangles = rectangles;
    }

    /**
     * Constructs a level with the given components, without specifying playing area dimensions.
     *
     * @param rectangles the list of components (rectangles) that make up the level
     */
    public Level(List<Components> rectangles) {
        this.rectangles = rectangles;
    }



    /*******************************************************************************
     *                              Setter Functions                               *
     *******************************************************************************/

    /**
     * Sets the maximum height of the playing area.
     *
     * @param maxHeight the maximum height of the playing area
     */
    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    /**
     * Sets the maximum width of the playing area.
     *
     * @param maxWidth the maximum width of the playing area
     */
    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    /**
     * Sets the minimum height of the playing area.
     *
     * @param minHeight the minimum height of the playing area
     */
    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    /**
     * Sets the minimum width of the playing area.
     *
     * @param minWidth the minimum width of the playing area
     */
    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }


    /*******************************************************************************
     *                              Getter Functions                               *
     *******************************************************************************/

    /**
     * Returns the maximum height of the playing area.
     *
     * @return the maximum height of the playing area
     */
    public int getMaxHeight() {
        return maxHeight;
    }

    /**
     * Returns the maximum width of the playing area.
     *
     * @return the maximum width of the playing area
     */
    public int getMaxWidth() {
        return maxWidth;
    }

    /**
     * Returns the minimum height of the playing area.
     *
     * @return the minimum height of the playing area
     */
    public int getMinHeight() {
        return minHeight;
    }

    /**
     * Returns the minimum width of the playing area.
     *
     * @return the minimum width of the playing area
     */
    public int getMinWidth() {
        return minWidth;
    }

    /**
     * Returns the list of components (rectangles) that make up the level.
     *
     * @return the list of components (rectangles) that make up the level
     */
    public List<Components> getRectangles() {
        return rectangles;
    }


}