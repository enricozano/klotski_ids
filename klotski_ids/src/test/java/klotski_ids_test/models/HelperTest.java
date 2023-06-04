package klotski_ids_test.models;


import javafx.geometry.Point2D;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import klotski_ids.models.Helper;
import klotski_ids.models.Level;
import klotski_ids.models.Components;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class HelperTest {
    private static Level expectedLevel;
    private static final int CELL_WIDTH = 50;
    private static final int CELL_HEIGHT = 50;


    @BeforeAll
    public static void setUp() {
        expectedLevel = new Level();
        expectedLevel.setLevelTitle("Level 1");
        expectedLevel.setLevelFileName("level_1");
    }

    @Test
    @DisplayName("Read json and parse it in a Level object")
    public void testReadJson() throws IOException, JSONException {
        String testFilePath = "/klotski_ids_test/dataTest/levelTest.json";

        Level actualLevel = Helper.readJson(testFilePath);

        Assertions.assertNotNull(actualLevel);
        Assertions.assertEquals(expectedLevel.getLevelTitle(), actualLevel.getLevelTitle());
        Assertions.assertEquals(expectedLevel.getMinWidth(), actualLevel.getMinWidth());
        Assertions.assertEquals(expectedLevel.getMaxHeight(), actualLevel.getMaxHeight());
        Assertions.assertEquals(expectedLevel.getMaxWidth(), actualLevel.getMaxWidth());
        Assertions.assertEquals(expectedLevel.getMinHeight(), actualLevel.getMinHeight());
        Assertions.assertEquals(expectedLevel.getCountedMoves(), actualLevel.getCountedMoves());
        Assertions.assertEquals(expectedLevel.getLevelFileName(), actualLevel.getLevelFileName());

        Assertions.assertEquals(actualLevel.getRectangles().get(0).getId(), "rect1");
        Assertions.assertEquals(actualLevel.getRectangles().get(0).getCol(), 1);
        Assertions.assertEquals(actualLevel.getRectangles().get(0).getRow(), 0);
        Assertions.assertEquals(actualLevel.getRectangles().get(0).getHeight(), 100);
        Assertions.assertEquals(actualLevel.getRectangles().get(0).getWidth(), 100);
        Assertions.assertEquals(actualLevel.getRectangles().get(0).getColSpan(), 2);
        Assertions.assertEquals(actualLevel.getRectangles().get(0).getRowSpan(), 2);
    }

    @Test
    @DisplayName("Test the expected area points of a given rectangle")
    public void testGetRectangleAreaPoints() {
        // Create a sample rectangle with x = 0 y = 0 width = 100 height = 100
        Rectangle rectangle = new Rectangle(0, 0, 2 * CELL_WIDTH, 2 * CELL_HEIGHT);
        int colIndex = 0;
        int rowIndex = 0;

        // Expected points
        List<Point2D> expectedPoints = new ArrayList<>();
        expectedPoints.add(new Point2D(colIndex, rowIndex));
        expectedPoints.add(new Point2D(colIndex + 1, rowIndex));
        expectedPoints.add(new Point2D(colIndex, rowIndex + 1));
        expectedPoints.add(new Point2D(colIndex + 1, rowIndex + 1));

        List<Point2D> actualPoints = Helper.getRectangleAreaPoints(rectangle, colIndex, rowIndex);

        assertEquals(expectedPoints.size(), actualPoints.size());
        assertTrue(expectedPoints.containsAll(actualPoints));
        assertTrue(actualPoints.containsAll(expectedPoints));
    }

    @Test
    @DisplayName("Test for the valid rectangle movements")
    public void testIsMoveValid() {
        // Create a sample rectangle
        Rectangle rectangle = new Rectangle();
        GridPane.setRowIndex(rectangle, 0);
        GridPane.setColumnIndex(rectangle, 0);

        // Test valid moves
        assertTrue(Helper.isMoveValid(rectangle, 0, 1)); // Same column, adjacent row
        assertTrue(Helper.isMoveValid(rectangle, 1, 0)); // Same row, adjacent column

        // Test invalid moves
        assertFalse(Helper.isMoveValid(rectangle, 0, 0)); // Same row and column
        assertFalse(Helper.isMoveValid(rectangle, 1, 1)); // Neither adjacent row nor column
        assertFalse(Helper.isMoveValid(rectangle, 2, 1)); // Different row and column

    }

    @Test
    @DisplayName("Test for the overlaps method")
    public void testOverlaps() {
        // Create a sample GridPane
        GridPane gridPane = new GridPane();

        // Create rectangles for testing
        Rectangle rectangle1 = new Rectangle();
        rectangle1.setId("rectangle1");
        GridPane.setColumnIndex(rectangle1, 0);
        GridPane.setRowIndex(rectangle1, 0);

        Rectangle rectangle2 = new Rectangle();
        rectangle2.setId("rectangle2");
        GridPane.setColumnIndex(rectangle2, 1);
        GridPane.setRowIndex(rectangle2, 1);

        // Add rectangles to the GridPane
        gridPane.getChildren().addAll(rectangle1, rectangle2);

        // Test overlapping scenario
        assertTrue(Helper.overlaps(gridPane, rectangle1, 1, 1)); // rectangle1 overlaps with rectangle2

        // Test non-overlapping scenario
        assertFalse(Helper.overlaps(gridPane, rectangle1, 2, 2)); // rectangle1 does not overlap with any existing rectangle

        // Test negative row and column values
        assertFalse(Helper.overlaps(gridPane, rectangle1, -1, 0)); // Negative row
        assertFalse(Helper.overlaps(gridPane, rectangle1, 0, -1)); // Negative column
    }

    @Test
    @DisplayName("Create rectangle test")
    public void testCreateRectangle() {
        List<Components> components = new ArrayList<>();
        components.add(new Components("component1", 0, 0,1,1,CELL_WIDTH,CELL_HEIGHT));
        components.add(new Components("component2", 1, 1,1,1,CELL_WIDTH,CELL_HEIGHT));
        List<Rectangle> rectangles = Helper.createRectangle(components);

        // Assert that the created rectangles match the expected number and properties
        assertEquals(components.size(), rectangles.size());

        for (int i = 0; i < components.size(); i++) {
            Components component = components.get(i);
            Rectangle rectangle = rectangles.get(i);

            assertEquals(component.getId(), rectangle.getId());
            assertEquals(component.getWidth(), rectangle.getWidth());
            assertEquals(component.getHeight(), rectangle.getHeight());
        }
    }

    @Test
    @DisplayName("Test create rectangle with empty component list")
    public void testCreateRectangleWithEmptyComponents() {
        List<Components> components = new ArrayList<>();

        List<Rectangle> rectangles = Helper.createRectangle(components);

        assertTrue(rectangles.isEmpty());
    }

    @Test
    @DisplayName("Test create rectangle with null component list")
    public void testCreateRectangleWithNullComponents() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Helper.createRectangle(null));

        assertEquals("Component list cant be null", exception.getMessage());
    }

    @Test
    @DisplayName("Test set Grid Pane elements")
    public void testSetGridPaneElements() {
        List<Components> components = new ArrayList<>();
        components.add(new Components("component1", 0, 0,1,1,CELL_WIDTH,CELL_HEIGHT));
        components.add(new Components("component2", 1, 1,1,1,CELL_WIDTH,CELL_HEIGHT));

        List<Rectangle> rectangles = new ArrayList<>();
        rectangles.add(new Rectangle(50, 50));
        rectangles.add(new Rectangle(50, 50));

        // Create a sample GridPane
        GridPane gridPane = new GridPane();

        // Invoke the method under test
        Helper.setGridPaneElements(gridPane, components, rectangles);

        // Assert that the GridPane contains the expected number of children
        assertEquals(components.size(), gridPane.getChildren().size());

        // Assert that the children of the GridPane match the expected components and rectangles
        for (int i = 0; i < components.size(); i++) {
            Components component = components.get(i);
            Rectangle rectangle = rectangles.get(i);

            assertTrue(gridPane.getChildren().contains(rectangle));
            assertEquals(component.getCol(), GridPane.getColumnIndex(rectangle));
            assertEquals(component.getRow(), GridPane.getRowIndex(rectangle));
            assertEquals(component.getColSpan(), GridPane.getColumnSpan(rectangle));
            assertEquals(component.getRowSpan(), GridPane.getRowSpan(rectangle));
        }
    }

    @Test
    @DisplayName("Test set Grid Pane elements with empty lists")
    public void testSetGridPaneElementsWithEmptyLists() {
        // Create empty lists of components and rectangles
        List<Components> components = new ArrayList<>();
        List<Rectangle> rectangles = new ArrayList<>();

        // Create a sample GridPane
        GridPane gridPane = new GridPane();

        // Invoke the method under test
        Helper.setGridPaneElements(gridPane, components, rectangles);

        // Assert that the GridPane does not contain any children
        assertTrue(gridPane.getChildren().isEmpty());
    }

    @Test
    @DisplayName("Test set Grid Pane elements with different list size")
    public void testSetGridPaneElementsWithMismatchedSizes() {
        List<Components> components = new ArrayList<>();
        components.add(new Components("component1", 0, 0,1,1,CELL_WIDTH,CELL_HEIGHT));
        components.add(new Components("component2", 1, 1,1,1,CELL_WIDTH,CELL_HEIGHT));

        List<Rectangle> rectangles = new ArrayList<>();
        rectangles.add(new Rectangle(50, 50));
        rectangles.add(new Rectangle(50, 50));
        rectangles.add(new Rectangle(100, 50));

        // Create a sample GridPane
        GridPane gridPane = new GridPane();

        // Invoke the method under test
        Helper.setGridPaneElements(gridPane, components, rectangles);

        // Assert that the GridPane contains the maximum number of children based on the smaller list size
        assertEquals(Math.min(components.size(), rectangles.size()), gridPane.getChildren().size());
    }



    @Test
    @DisplayName("Test if two components lists are the same")
    public void testIsSameComponentsList() {
        // Create sample components lists
        List<Components> componentsList1 = new ArrayList<>();
        componentsList1.add(new Components("component1", 0, 0, 1, 1, CELL_WIDTH, CELL_HEIGHT));
        componentsList1.add(new Components("component2", 1, 1, 1, 1, CELL_WIDTH, CELL_HEIGHT));


        List<Components> componentsList2 = new ArrayList<>();
        componentsList2.add(new Components("component1", 0, 0, 1, 1, CELL_WIDTH, CELL_HEIGHT));
        componentsList2.add(new Components("component2", 1, 1, 1, 1, CELL_WIDTH, CELL_HEIGHT));

        // Invoke the method under test
        boolean result = Helper.isSameComponentsList(componentsList1, componentsList2);

        // Assert that the two components lists are considered the same
        assertTrue(result);
    }

    @Test
    @DisplayName("Test if two components lists are different")
    public void testIsDifferentComponentsList() {
        List<Components> componentsList1 = new ArrayList<>();
        componentsList1.add(new Components("component1", 0, 0,1,1,CELL_WIDTH,CELL_HEIGHT));
        componentsList1.add(new Components("component2", 1, 1,1,1,CELL_WIDTH,CELL_HEIGHT));

        List<Components> componentsList2 = new ArrayList<>();
        componentsList2.add(new Components("component1", 0, 0, 2, 1, 2*CELL_WIDTH, CELL_HEIGHT));
        componentsList2.add(new Components("component3", 1, 1, 1, 1, CELL_WIDTH, CELL_HEIGHT));

        // Invoke the method under test
        boolean result = Helper.isSameComponentsList(componentsList1, componentsList2);

        // Assert that the two components lists are considered different
        assertFalse(result);
    }

    @Test
    @DisplayName("Test if components lists with different sizes are considered different")
    public void testIsDifferentSizeComponentsList() {
        // Create sample components lists with different sizes
        List<Components> componentsList1 = new ArrayList<>();
        componentsList1.add(new Components("component1", 0, 0, 1, 1, CELL_WIDTH, CELL_HEIGHT));

        List<Components> componentsList2 = new ArrayList<>();
        componentsList2.add(new Components("component1", 0, 0, 2, 2, 2*CELL_WIDTH, 2*CELL_HEIGHT));
        componentsList2.add(new Components("component2", 1, 1, 1, 1, CELL_WIDTH, CELL_HEIGHT));

        // Invoke the method under test
        boolean result = Helper.isSameComponentsList(componentsList1, componentsList2);

        // Assert that the two components lists with different sizes are considered different
        assertFalse(result);
    }

    @Test
    @DisplayName("Test converting level to string")
    public void testLevelToString() {
        List<Components> components = new ArrayList<>();
        components.add(new Components("component1", 0, 0,1,1,CELL_WIDTH,CELL_HEIGHT));
        components.add(new Components("component2", 1, 1,1,1,CELL_WIDTH,CELL_HEIGHT));

        // Expected string representation of the level
        String coordsComponent1 = "0" + " " + "0" + "\n";
        String expectedComponent1 = String.format("%s%n%s", "S", coordsComponent1);
        String coordsComponent2 = "1" + " " + "1" + "\n";
        String expectedComponent2 = String.format("%s%n%s", "S", coordsComponent2);

        String expected = expectedComponent1 + expectedComponent2;

        // Invoke the method under test
        String result = Helper.levelToString(components);

        // Assert the result matches the expected string
        assertEquals(expected, result);
    }
    @Test
    @DisplayName("Test converting coordinates to string")
    public void testCoordsToString() {
        // Create a sample Components object
        Components rectangle = new Components("A", 0, 0, 1, 1, CELL_WIDTH, CELL_HEIGHT);

        // Expected string representation of the coordinates
        String expected = "0 0\n";

        // Invoke the method under test
        String result = Helper.coordsToString(rectangle);

        // Assert the result matches the expected string
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Parsing a move string")
    public void testParseMove() {
        String moveString = "#6 DOWN";
        Pair<Integer, String> expectedPair = new Pair<>(6, "DOWN");

        Pair<Integer, String> actualPair = Helper.parseMove(moveString);

        assertEquals(expectedPair, actualPair, "Parsed move does not match expected result.");
    }

    @Test
    @DisplayName("Testing performMoveAction")
    public void testPerformMoveAction() {
        List<Components> components = new ArrayList<>();
        components.add(new Components("A", 0, 0, 1, 1, 100, 100));

        Pair<Integer, String> move = new Pair<>(0, "RIGHT");
        List<Components> expectedComponents = new ArrayList<>();
        expectedComponents.add(new Components("A", 0, 1, 1, 1, 100, 100));

        List<Components> actualComponents = Helper.performMoveAction(move, components);

        assertEquals(expectedComponents, actualComponents, "Components after move do not match expected result.");
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