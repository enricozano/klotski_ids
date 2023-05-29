package klotski_ids.models;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Helper {
    private static final int CELL_WIDTH = 50;
    private static final int CELL_HEIGHT = 50;


    /**
     * Reads the JSON file from the specified path and returns a deserialized Level object.
     *
     * @param filePath The path of the JSON file to read
     * @return A Level object deserialized from the JSON file
     * @throws FileNotFoundException if the specified file does not exist
     * @throws IOException           if an error occurs while reading the JSON file
     * @throws JsonSyntaxException   if the JSON file is not formatted correctly
     */
    public Level readJson(String filePath) throws FileNotFoundException, IOException, JsonSyntaxException {
        System.out.println("file path: " + filePath);
        Level level = null;
        try (InputStream inputStream = getClass().getResourceAsStream(filePath)) {
            assert inputStream != null;
            try (InputStreamReader reader = new InputStreamReader(inputStream)) {
                Gson gson = new Gson();
                JsonReader jsonReader = new JsonReader(reader);
                jsonReader.setLenient(true);
                level = gson.fromJson(jsonReader, Level.class);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
            throw e;
        } catch (IOException | JsonSyntaxException e) {
            System.err.println("Error while reading JSON file: " + e.getMessage());
            throw e;
        }
        return level;
    }

    public Level readJsonAbsolutePath(String filePath) throws FileNotFoundException, IOException, JsonSyntaxException {
        Level level = null;
        try (InputStream inputStream = new FileInputStream(filePath)) {
            try (InputStreamReader reader = new InputStreamReader(inputStream)) {
                Gson gson = new Gson();
                JsonReader jsonReader = new JsonReader(reader);
                jsonReader.setLenient(true);
                level = gson.fromJson(jsonReader, Level.class);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
            throw e;
        } catch (IOException | JsonSyntaxException e) {
            System.err.println("Error while reading JSON file: " + e.getMessage());
            throw e;
        }
        return level;
    }




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
    public List<Point2D> getRectangleAreaPoints(Rectangle rectangle, int colIndex, int rowIndex) {
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
    public boolean isMoveValid(Rectangle rectangle, int col, int row) {
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
    public boolean overlaps(GridPane gridPane, Rectangle rectangle, int newCol, int newRow) {

        List<Point2D> newPoints = getRectangleAreaPoints(rectangle, newCol, newRow);

        for (Node x : gridPane.getChildren()) {
            int colIndex = GridPane.getColumnIndex(x);
            int rowIndex = GridPane.getRowIndex(x);

            List<Point2D> points = getRectangleAreaPoints((Rectangle) x, colIndex, rowIndex);

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
    public List<Rectangle> createRectangle(List<klotski_ids.models.Components> components) {
        List<Rectangle> rectangleList = new ArrayList<>();

        if (components == null) {
            throw new IllegalArgumentException("La lista components non può essere null.");
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
    public void setGridPaneElements(GridPane gridPane, List<klotski_ids.models.Components> components, List<Rectangle> rectangles) {
        gridPane.getChildren().clear();
        int size = Math.min(components.size(), rectangles.size());

        for (int i = 0; i < size; i++) {
            Components component = components.get(i);
            Rectangle rectangle = rectangles.get(i);
            GridPane.setConstraints(rectangle, component.getCol(), component.getRow(), component.getColSpan(), component.getRowSpan());
            gridPane.getChildren().add(rectangle);
        }
    }

    public List<Components> copyComponentsList(List<Components> originalList) {
        List<Components> copyList = new ArrayList<>();
        for (Components component : originalList) {
            Components copy = new Components(component.getId(), component.getRow(), component.getCol(), component.getColSpan(), component.getRowSpan());
            copyList.add(copy);
        }
        return copyList;
    }



}
