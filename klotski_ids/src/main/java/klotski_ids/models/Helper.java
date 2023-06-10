package klotski_ids.models;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.io.*;
import java.util.*;

/**
 * The Helper class provides utility methods for various operations.
 */
public class Helper {
    /**
     * The default grid cell width
     */
    private static final int CELL_WIDTH = 50;
    /**
     * The default grid cell height
     */
    private static final int CELL_HEIGHT = 50;


    /**
     * Returns a list of points representing the area covered by the specified rectangle at the given column and row index
     * <p>
     * in a grid pane.
     *
     * @param rectangle the rectangle for which to retrieve the area points
     * @param colIndex  the column index in the grid pane where the rectangle is located
     * @param rowIndex  the row index in the grid pane where the rectangle is located
     * @return a list of Point2D objects representing the area covered by the rectangle
     */
    public static List<Point2D> getRectangleAreaPoints(Rectangle rectangle, int colIndex, int rowIndex) {
        int height = (int) rectangle.getHeight();
        int width = (int) rectangle.getWidth();

        List<Point2D> points = new ArrayList<>();
        points.add(new Point2D(colIndex, rowIndex));

        if (height > CELL_HEIGHT && width == CELL_WIDTH) {
            points.add(new Point2D(colIndex, rowIndex + 1));
        } else if (height == CELL_HEIGHT && width > CELL_WIDTH) {
            points.add(new Point2D(colIndex + 1, rowIndex));
        } else if (height > CELL_HEIGHT && width > CELL_WIDTH) {
            points.add(new Point2D(colIndex + 1, rowIndex));
            points.add(new Point2D(colIndex, rowIndex + 1));
            points.add(new Point2D(colIndex + 1, rowIndex + 1));
        }

        return points;
    }

    /**
     * Checks if a move is valid for a given rectangle in the specified row and column.
     *
     * @param rectangle the rectangle to be moved
     * @param col       the new column to move the rectangle to
     * @param row       the new row to move the rectangle to
     * @return {@code true} if the move is valid, {@code false} otherwise
     */
    public static boolean isMoveValid(Rectangle rectangle, int col, int row) {
        int rectangleRow = GridPane.getRowIndex(rectangle);
        int rectangleCol = GridPane.getColumnIndex(rectangle);

        boolean isSameRow = rectangleRow == row;
        boolean isSameCol = rectangleCol == col;
        boolean isAdjacentRow = Math.abs(row - rectangleRow) == 1;
        boolean isAdjacentCol = Math.abs(col - rectangleCol) == 1;

        return (isSameRow && isAdjacentCol) || (isSameCol && isAdjacentRow);
    }


    /**
     * Checks if a given rectangle overlaps with any other rectangles on the specified cell position on a GridPane.
     *
     * @param gridPane  The GridPane object to search for overlapping rectangles.
     * @param rectangle The Rectangle object to check for overlaps.
     * @param newCol    The column index of the new position to check for overlaps.
     * @param newRow    The row index of the new position to check for overlaps.
     * @return True if the given rectangle overlaps with any other rectangles on the specified cell position, false otherwise.
     */
    public static boolean overlaps(GridPane gridPane, Rectangle rectangle, int newCol, int newRow) {

        List<Point2D> newPoints = Helper.getRectangleAreaPoints(rectangle, newCol, newRow);

        for (Node x : gridPane.getChildren()) {
            int colIndex = GridPane.getColumnIndex(x);
            int rowIndex = GridPane.getRowIndex(x);

            List<Point2D> points = Helper.getRectangleAreaPoints((Rectangle) x, colIndex, rowIndex);

            if (!rectangle.getId().equals(x.getId())) {
                for (Point2D pRect : newPoints) {
                    for (Point2D allRectPoints : points) {
                        if (pRect.equals(allRectPoints)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Creates a list of rectangles based on the components passed as argument.
     *
     * @param components a list of Components objects from which to create the rectangles.
     * @return a list of Rectangle objects created from the Components objects.
     * @throws IllegalArgumentException if the components list is null.
     */
    public static List<Rectangle> createRectangle(List<klotski_ids.models.Components> components) {
        List<Rectangle> rectangleList = new ArrayList<>();

        if (components == null) {
            throw new IllegalArgumentException("Component list cant be null");
        }
        for (klotski_ids.models.Components element : components) {
            Rectangle rectangle = new Rectangle(element.getWidth(), element.getHeight());
            rectangle.setId(element.getId());
            rectangleList.add(rectangle);
        }
        return rectangleList;
    }


    /**
     * Adds to the grid the rectangles associated with the specified components, positioning them according to the information
     * contained in their respective Components objects. If the number of components or rectangles is greater than the other,
     * only the first n pairs will be considered, where n is the minimum between the two numbers.
     *
     * @param gridPane   the grid to which the rectangles are added
     * @param components the list of components
     * @param rectangles the list of rectangles
     */
    public static void setGridPaneElements(GridPane gridPane, List<klotski_ids.models.Components> components, List<Rectangle> rectangles) {
        gridPane.getChildren().clear();
        int size = Math.min(components.size(), rectangles.size());

        for (int i = 0; i < size; i++) {
            Components component = components.get(i);
            Rectangle rectangle = rectangles.get(i);
            GridPane.setConstraints(rectangle, component.getCol(), component.getRow(), component.getColSpan(), component.getRowSpan());
            gridPane.getChildren().add(rectangle);
        }
    }


    /**
     * Checks if two lists of Components are the same.
     *
     * @param componentsList1 the first list of Components
     * @param componentsList2 the second list of Components
     * @return true if the lists are the same, false otherwise
     */
    public static boolean isSameComponentsList(List<Components> componentsList1, List<Components> componentsList2) {
        if (componentsList1.size() != componentsList2.size()) {
            return false;
        }

        for (int i = 0; i < componentsList1.size(); i++) {
            Components comp1 = componentsList1.get(i);
            Components comp2 = componentsList2.get(i);

            if (!comp1.equals(comp2)) {
                return false;
            }
        }

        return true;

    }


    /**
     * Writes the given content to a file specified by the file path.
     *
     * @param filePath the path of the file to write
     * @param content  the content to write to the file
     * @return true if an error occurred while writing the file, false otherwise
     */
    public static boolean writeToFile(String filePath, String content) {
        MyAlerts error;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        } catch (Exception e) {
            error = new MyAlerts("File Not Found");
            error.errorAlert();
            return true;
        }
        return false;
    }





    /**
     * Generates the solution file name based on the level name.
     *
     * @param levelName the name of the level
     * @return the solution file name
     */
    public static String getSolutionFileName(String levelName) {
        return switch (levelName) {
            case "Level 1" -> "SolutionsLevel1.txt";
            case "Level 2" -> "SolutionsLevel2.txt";
            case "Level 3" -> "SolutionsLevel3.txt";
            case "Level 4" -> "SolutionsLevel4.txt";
            default -> "";
        };
    }



    /**
     * Performs a move action on a list of Components.
     *
     * @param move       the move action to perform
     * @param components the list of Components
     * @return the updated list of Components after the move
     */
    public static List<Components> performMoveAction(Pair<Integer, String> move, List<Components> components) {
        int rectangleNumber = move.getKey();
        String action = move.getValue();
        List<Components> componentsList = new ArrayList<>(components);
        Components component = componentsList.get(rectangleNumber);
        int currentRow = component.getRow();
        int currentCol = component.getCol();

        switch (action) {
            case "UP" -> component.setRow(currentRow - 1);
            case "DOWN" -> component.setRow(currentRow + 1);
            case "RIGHT" -> component.setCol(currentCol + 1);
            case "LEFT" -> component.setCol(currentCol - 1);
        }

        return componentsList;
    }

}