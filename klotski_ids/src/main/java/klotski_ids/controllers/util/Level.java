package klotski_ids.controllers.util;

import com.google.gson.Gson;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Cell;
import javafx.scene.effect.Light;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Level {
    private int maxWidth;
    private int maxHeight;
    private int minWidth;
    private int minHeight;


    /*********************************************/
    private double startX, startY, startTranslateX, startTranslateY, startMouseX, startMouseY, col, row;
    private static final int CELL_WIDTH = 50;
    private static final int CELL_HEIGHT = 50;
    private static final int NUM_COLS = 3;
    private static final int NUM_ROWS = 4;

    /*********************************************/

    private List<Components> rectangles;

    public List<Components> getRectangles() {
        return rectangles;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public int getMinWidth() {
        return minWidth;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }


    public Level readJson(String dir) {
        Level level = null;
        try {

            InputStream inputStream = getClass().getResourceAsStream(dir);
            if (inputStream == null) {
                throw new FileNotFoundException("File non trovato: " + dir);
            }

            Gson gson = new Gson();
            InputStreamReader reader = new InputStreamReader(inputStream);
            level = gson.fromJson(reader, Level.class);
        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file JSON: " + e.getMessage());
            e.printStackTrace();
        }
        return level;
    }

    private void setMouseEvent(Rectangle rectangle, GridPane gridPane) {
        int numRows = gridPane.getRowConstraints().size();
        int numCols = gridPane.getColumnConstraints().size();

        rectangle.setOnMousePressed(event -> {

            startX = GridPane.getColumnIndex(rectangle);
            startY = GridPane.getRowIndex(rectangle);

            startMouseX = event.getSceneX();
            startMouseY = event.getSceneY();

            startTranslateX = rectangle.getTranslateX();
            startTranslateY = rectangle.getTranslateY();

            rectangle.setCursor(Cursor.CLOSED_HAND);
        });

        rectangle.setOnMouseDragged(event -> {

            double offsetX = event.getSceneX() - startMouseX;
            double offsetY = event.getSceneY() - startMouseY;

            double newTranslateX = startTranslateX + offsetX;
            double newTranslateY = startTranslateY + offsetY;

            col = (int) Math.round(newTranslateX / (gridPane.getWidth() / numCols));
            row = (int) Math.round(newTranslateY / (gridPane.getHeight() / numRows));

            int newCol = (int) (startX + col);
            int newRow = (int) (startY + row);

            if (overlaps(gridPane, rectangle, newCol, newRow)) {
                System.out.println("DIOCAN");
            }
            if (newCol >= 0 && newRow >= 0) {

                int maxCol, maxRow;
                if (rectangle.getWidth() <= CELL_WIDTH && rectangle.getHeight() <= CELL_HEIGHT) {
                    maxCol = NUM_COLS;
                    maxRow = NUM_ROWS;
                } else if (rectangle.getWidth() <= CELL_WIDTH && rectangle.getHeight() > CELL_HEIGHT) {
                    maxCol = NUM_COLS;
                    maxRow = NUM_ROWS - 1;
                } else if (rectangle.getWidth() > CELL_WIDTH && rectangle.getHeight() <= CELL_HEIGHT) {
                    maxCol = NUM_COLS - 1;
                    maxRow = NUM_ROWS;
                } else {
                    maxCol = NUM_COLS - 1;
                    maxRow = NUM_ROWS - 1;
                }

                if (newCol <= maxCol && newRow <= maxRow && !overlaps(gridPane,rectangle,newCol,newRow)) {
                    GridPane.setRowIndex(rectangle, newRow);
                    GridPane.setColumnIndex(rectangle, newCol);
                }

            }

        });

        rectangle.setOnMouseReleased(event -> {
            rectangle.setCursor(Cursor.DEFAULT);

            System.out.println("COL INDEX DOPO: " + GridPane.getColumnIndex(rectangle));
            System.out.println("ROW INDEX DOPO: " + GridPane.getRowIndex(rectangle));

        });


    }

    private List<Point2D> getRectangleAreaPoints(Rectangle rectangle) {
        int height = (int) rectangle.getHeight();
        int width = (int) rectangle.getWidth();
        int colIndex = GridPane.getColumnIndex(rectangle);
        int rowIndex = GridPane.getRowIndex(rectangle);

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

    private boolean overlaps(GridPane gridPane, Rectangle rectangle, int newCol, int newRow) {

        Point2D newPoint = new Point2D(newCol, newRow);
        //aggiungere calcolo areea partendo da newCol e newRow, nuovo oggetto rectangole

        //List<Point2D> newPoints = getRectangleAreaPoints(newRect);

        for (Node x : gridPane.getChildren()) {

            List<Point2D> points = getRectangleAreaPoints((Rectangle) x);

            if (!rectangle.getId().equals(x.getId())) {
                for(int i = 0; i < points.size(); i++){
                    if(newPoint.equals(points.get(i))){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Rectangle createRectangle(Components component) {
        Rectangle rectangle = new Rectangle(component.getWidth(), component.getHeight());
        rectangle.setId(component.getId());
        return rectangle;
    }

    public void setGridPaneElements(GridPane gridPane, List<Components> components) {
        if (components == null) {
            throw new IllegalArgumentException("La lista components non può essere null.");
        }
        for (Components component : components) {
            Rectangle rectangle = createRectangle(component);
            GridPane.setConstraints(rectangle, component.getCol(), component.getRow(), component.getColSpan(), component.getRowSpan());
            setMouseEvent(rectangle, gridPane);
            gridPane.getChildren().add(rectangle);
        }
    }

}
