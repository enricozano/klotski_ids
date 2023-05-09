package klotski_ids.controllers.util;

import com.google.gson.Gson;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;

import java.io.*;
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

        System.out.println("NumRows: " + numRows);
        System.out.println("NumCol: " + numCols);

        gridPane.setGridLinesVisible(true);

        rectangle.setOnMousePressed(event -> {
            System.out.println();
            System.out.println();
            System.out.println("NUOVO CLICK");
            System.out.println();


            startX = GridPane.getColumnIndex(rectangle);
            startY = GridPane.getRowIndex(rectangle);


            startMouseX = event.getSceneX();
            startMouseY = event.getSceneY();

            System.out.println("startMouseX: " + startMouseX);
            System.out.println("startMouseY: " + startMouseY);
            System.out.println();


            startTranslateX = rectangle.getTranslateX();
            startTranslateY = rectangle.getTranslateY();

            System.out.println("startTranslateX: " + startTranslateX);
            System.out.println("startTranslateY: " + startTranslateY);
            System.out.println();


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


            System.out.println();
            System.out.println("ColonnaRettangolo: " + startX);
            System.out.println("RigaRettangolo: " + startY);
            System.out.println();

            System.out.println("Colonna: " + col);
            System.out.println("riga: " + row);
            System.out.println();

            System.out.println("Nuova Colonna: " + newCol);
            System.out.println("Nuova riga: " + newRow);
            System.out.println();

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

                if (newCol <= maxCol && newRow <= maxRow) {
                    System.out.println("dentro");
                    rectangle.setTranslateX(col * (gridPane.getWidth() / numCols));
                    rectangle.setTranslateY(row * (gridPane.getHeight() / numRows));
                }

            }

        });

        rectangle.setOnMouseReleased(event -> {
            rectangle.setCursor(Cursor.DEFAULT);
        });
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
