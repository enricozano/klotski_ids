package klotski_ids_test.models;

import klotski_ids.models.Component;
import klotski_ids.models.Level;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LevelTest {
    @Test
    @DisplayName("Constructor with Parameters")
    public void testConstructorWithParameters() {
        List<Component> rectangles = new ArrayList<>();
        rectangles.add(new Component());

        int maxWidth = 200;
        int maxHeight = 200;
        int minWidth = 100;
        int minHeight = 100;
        int countedMoves = 10;
        String levelFileName = "level.txt";
        String levelTitle = "Level 1";

        Level level = new Level(rectangles, maxWidth, maxHeight, minWidth, minHeight, countedMoves, levelFileName, levelTitle);

        assertEquals(maxWidth, level.getMaxWidth());
        assertEquals(maxHeight, level.getMaxHeight());
        assertEquals(minWidth, level.getMinWidth());
        assertEquals(minHeight, level.getMinHeight());
        assertEquals(countedMoves, level.getCountedMoves());
        assertEquals(levelFileName, level.getLevelFileName());
        assertEquals(levelTitle, level.getLevelTitle());
        assertEquals(rectangles, level.getRectangles());
    }

    @Test
    @DisplayName("Constructor with Rectangles")
    public void testConstructorWithRectangles() {
        List<Component> rectangles = new ArrayList<>();
        rectangles.add(new Component());

        Level level = new Level(rectangles);

        assertEquals(rectangles, level.getRectangles());
    }

    @Test
    @DisplayName("Setters and Getters")
    public void testSettersAndGetters() {
        Level level = new Level(new ArrayList<>());

        int maxWidth = 200;
        int maxHeight = 200;
        int minWidth = 100;
        int minHeight = 100;
        int countedMoves = 10;
        String levelFileName = "level.txt";
        String levelTitle = "Level 1";

        level.setMaxWidth(maxWidth);
        level.setMaxHeight(maxHeight);
        level.setMinWidth(minWidth);
        level.setMinHeight(minHeight);
        level.setCountedMoves(countedMoves);
        level.setLevelFileName(levelFileName);
        level.setLevelTitle(levelTitle);

        assertEquals(maxWidth, level.getMaxWidth());
        assertEquals(maxHeight, level.getMaxHeight());
        assertEquals(minWidth, level.getMinWidth());
        assertEquals(minHeight, level.getMinHeight());
        assertEquals(countedMoves, level.getCountedMoves());
        assertEquals(levelFileName, level.getLevelFileName());
        assertEquals(levelTitle, level.getLevelTitle());
    }
    @Test
    @DisplayName("Converts Components to JSONObject")
    public void testToJsonObject() throws JSONException {
        List<Component> components = new ArrayList<>();
        Level level = new Level(components,100,100,50,50,0,"level 1","level_1");
        String actual = level.toJsonObject().toString();
        var expected = "{\"minHeight\":50,\"rectangles\":[],\"maxHeight\":100,\"minWidth\":50,\"levelName\":\"level 1\",\"countedMoves\":0,\"levelTitle\":\"level_1\",\"maxWidth\":100}";
        Assertions.assertEquals(expected,actual);
    }
}