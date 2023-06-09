package klotski_ids.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
     * Keeps track of the number of moves performed.
     */
    private int countedMoves;
    /**
     * The level file name
     */
    private String levelFileName;
    /**
     * The level title
     */
    private String levelTitle;
    /**
     * The list of components (rectangles) that make up the level.
     */
    private final List<Component> componentsList;

    /**
     * Constructs a new Level object with default values.
     */
    public Level() {
        this.maxWidth = 100;
        this.maxHeight = 100;
        this.minWidth = 50;
        this.minHeight = 50;
        this.countedMoves = 0;
        this.levelFileName = "";
        this.levelTitle = "";
        this.componentsList = new ArrayList<>();
    }

    /**
     * Constructs a new Level object.
     *
     * @param rectangles   The list of components (rectangles) in the level.
     * @param maxWidth     The maximum width of the level.
     * @param maxHeight    The maximum height of the level.
     * @param minWidth     The minimum width of the level.
     * @param minHeight    The minimum height of the level.
     * @param countedMoves The number of moves counted for the level.
     * @param levelFileName The name of the file
     * @param levelTitle   The title of the level.
     */
    public Level(List<Component> rectangles, int maxWidth, int maxHeight, int minWidth, int minHeight, int countedMoves, String levelFileName, String levelTitle) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.componentsList = rectangles;
        this.countedMoves = countedMoves;
        this.levelFileName = levelFileName;
        this.levelTitle = levelTitle;
    }

    /**
     * Constructs a level with the given components, without specifying playing area dimensions.
     *
     * @param rectangles the list of components (rectangles) that make up the level
     */
    public Level(List<Component> rectangles) {
        this.componentsList = rectangles;
    }

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

    /**
     * Sets the level file name.
     *
     * @param levelName the level file name
     */
    public void setLevelFileName(String levelName) {
        this.levelFileName = levelName;
    }
    /**
     * Sets the title of the level.
     *
     * @param levelName the title of the level
     */
    public void setLevelTitle(String levelName) {
        this.levelTitle = levelName;
    }

    /**
     * Sets the number of counted moves.
     *
     * @param countedMoves the number of counted moves
     */
    public void setCountedMoves(int countedMoves) {
        this.countedMoves = countedMoves;
    }

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
    public List<Component> getRectangles() {
        return componentsList;
    }

    /**
     * Returns the level file name.
     *
     * @return the level file name
     */
    public String getLevelFileName() {
        return this.levelFileName;
    }
    /**
     * Returns the title of the level.
     *
     * @return the title of the level
     */
    public String getLevelTitle() {
        return this.levelTitle;
    }

    /**
     * Returns the number of counted moves.
     *
     * @return the number of counted moves
     */
    public int getCountedMoves() {
        return this.countedMoves;
    }
    /**
     * Converts the Level object to a JSONObject.
     *
     * @return the JSONObject representing the Level
     * @throws JSONException if there is an error during JSON conversion
     */
    public JSONObject toJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject(new LinkedHashMap<>());
        jsonObject.put("maxWidth", maxWidth);
        jsonObject.put("maxHeight", maxHeight);
        jsonObject.put("minWidth", minWidth);
        jsonObject.put("minHeight", minHeight);
        jsonObject.put("countedMoves", countedMoves);
        jsonObject.put("levelName", levelFileName);
        jsonObject.put("levelTitle", levelTitle);

        JSONArray rectanglesJsonArray = new JSONArray();
        for (Component rectangle : componentsList) {
            rectanglesJsonArray.put(rectangle.toJsonObject());
        }
        jsonObject.put("rectangles", rectanglesJsonArray);

        return jsonObject;
    }

}
