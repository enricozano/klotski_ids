package klotski_ids_test.models;

import com.google.gson.JsonSyntaxException;
import klotski_ids.models.Helper;
import klotski_ids.models.Level;
import klotski_ids.models.Components;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HelperTest {
    @BeforeAll
    public static void setUp() {

    }

    @Test
    public void testReadJson() throws IOException, JsonSyntaxException {
        // Test data
        String filePath = "/klotski_ids_test/dataTest/levelTest.json";
        // Test data
        // Call the method to be tested
        Level actualLevel = Helper.readJson(filePath);
        List<Components> componentsList = actualLevel.getRectangles();
        // Assertions
        Assertions.assertNotNull(actualLevel);

        Assertions.assertEquals("Level 1", actualLevel.getLevelTitle());
        Assertions.assertEquals("rect1", componentsList.get(0).getId());
    }
    @Test
    @DisplayName("Test getSolutionFileName")
    public void testGetSolutionFileName() {
        String level1FileName = Helper.getSolutionFileName("Level 1");
        assertEquals("SolutionsLevel1.txt", level1FileName);

        String level2FileName = Helper.getSolutionFileName("Level 2");
        assertEquals("SolutionsLevel2.txt", level2FileName);

        String level3FileName = Helper.getSolutionFileName("Level 3");
        assertEquals("SolutionsLevel3.txt", level3FileName);

        String level4FileName = Helper.getSolutionFileName("Level 4");
        assertEquals("SolutionsLevel4.txt", level4FileName);

        String defaultFileName = Helper.getSolutionFileName("Level 5");
        assertEquals("", defaultFileName);
    }

}