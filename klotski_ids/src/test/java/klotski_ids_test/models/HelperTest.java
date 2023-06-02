package klotski_ids_test.models;

import klotski_ids.models.Helper;
import klotski_ids.models.Level;
import klotski_ids.models.Components;
import org.junit.jupiter.api.BeforeAll;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import static org.junit.jupiter.api.Assertions.*;

class HelperTest {
    @BeforeAll
    public static void setUp() {

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