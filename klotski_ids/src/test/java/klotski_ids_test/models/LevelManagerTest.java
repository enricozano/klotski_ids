package klotski_ids_test.models;


import klotski_ids.models.Level;
import klotski_ids.models.LevelManager;
import org.json.JSONPropertyIgnore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;

public class LevelManagerTest {

    @Test
    @DisplayName("Read json and parse it in a Level object")
    public void testGetLevel() throws IOException {
        Level expectedLevel = new Level();
        expectedLevel.setLevelTitle("Level 1");
        expectedLevel.setLevelFileName("level_1");

        String testFilePath = "/klotski_ids_test/dataTest/levelTest.json";

        Level actualLevel = LevelManager.getLevel(testFilePath);

        assertNotNull(actualLevel);
        assertEquals(expectedLevel.getLevelTitle(), actualLevel.getLevelTitle());
        assertEquals(expectedLevel.getMinWidth(), actualLevel.getMinWidth());
        assertEquals(expectedLevel.getMaxHeight(), actualLevel.getMaxHeight());
        assertEquals(expectedLevel.getMaxWidth(), actualLevel.getMaxWidth());
        assertEquals(expectedLevel.getMinHeight(), actualLevel.getMinHeight());
        assertEquals(expectedLevel.getCountedMoves(), actualLevel.getCountedMoves());
        assertEquals(expectedLevel.getLevelFileName(), actualLevel.getLevelFileName());

        assertEquals(actualLevel.getRectangles().get(0).getId(), "rect1");
        assertEquals(actualLevel.getRectangles().get(0).getCol(), 1);
        assertEquals(actualLevel.getRectangles().get(0).getRow(), 0);
        assertEquals(actualLevel.getRectangles().get(0).getHeight(), 100);
        assertEquals(actualLevel.getRectangles().get(0).getWidth(), 100);
        assertEquals(actualLevel.getRectangles().get(0).getColSpan(), 2);
        assertEquals(actualLevel.getRectangles().get(0).getRowSpan(), 2);
    }



}