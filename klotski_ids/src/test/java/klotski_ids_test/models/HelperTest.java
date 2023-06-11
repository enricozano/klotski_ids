package klotski_ids_test.models;

import javafx.geometry.Point2D;
import javafx.scene.layout.GridPane;
import klotski_ids.models.Helper;
import klotski_ids.models.Component;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class HelperTest {
    private static final int CELL_WIDTH = 50;
    private static final int CELL_HEIGHT = 50;


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
    @DisplayName("Test set Grid Pane elements")
    public void testSetGridPaneElements() {
        List<Component> components = new ArrayList<>();
        components.add(new Component("component1", 0, 0,1,1,CELL_WIDTH,CELL_HEIGHT));
        components.add(new Component("component2", 1, 1,1,1,CELL_WIDTH,CELL_HEIGHT));

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
            Component component = components.get(i);
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
        List<Component> components = new ArrayList<>();
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
        List<Component> components = new ArrayList<>();
        components.add(new Component("component1", 0, 0,1,1,CELL_WIDTH,CELL_HEIGHT));
        components.add(new Component("component2", 1, 1,1,1,CELL_WIDTH,CELL_HEIGHT));

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


}