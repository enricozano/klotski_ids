package klotski_ids_test.models;

import klotski_ids.models.Level;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LevelTest {
    @Test
    @DisplayName("Constructor with Parameters")
    public void testConstructorWithParameters() {
        List<klotski_ids.models.Components> rectangles = new ArrayList<>();
        rectangles.add(new klotski_ids.models.Components());

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
        List<klotski_ids.models.Components> rectangles = new ArrayList<>();
        rectangles.add(new klotski_ids.models.Components());

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
}