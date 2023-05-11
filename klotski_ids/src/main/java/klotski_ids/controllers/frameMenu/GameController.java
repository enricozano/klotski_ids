package klotski_ids.controllers.frameMenu;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import klotski_ids.controllers.util.Components;
import klotski_ids.controllers.util.Level;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class GameController {

    @FXML
    public Button undo;
    @FXML
    public Button nextBestMove;
    @FXML
    public Button reset;
    @FXML
    public Button save;
    @FXML
    public Label nMosse;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label titlelabel;

    /*******************************************************************************************************/
    private double startX, startY, startTranslateX, startTranslateY, startMouseX, startMouseY, col, row;
    private static final int CELL_WIDTH = 50;
    private static final int CELL_HEIGHT = 50;
    private static final int NUM_COLS = 3;
    private static final int NUM_ROWS = 4;

    private static int MAX_WIDTH = 100;
    private static int MAX_HEIGTH = 100;
    private static int MIN_WIDTH = 50;
    private static int MIN_HEIGTH = 50;

    private String levelName;

    public int numMosse;

    private final List<Rectangle> gridPaneList = new ArrayList<>();

    List<Components> components = new ArrayList<>();
    List<Rectangle> rectangles = new ArrayList<>();

    /*******************************************************************************************************/

    public void setComponents(List<Components> components) {
        this.components = components;
    }

    public void setRectangles(List<Rectangle> rectangles) {
        this.rectangles = rectangles;
    }

    public List<Components> getComponents() {
        return components;
    }

    public List<Rectangle> getRectangles() {
        return rectangles;
    }

    //Aggiungere metodo di controllo delle diagonali per il movimento
    //aggiungere controllo delle mosse per l'incremento delle mosse
    //Save su file json
    //Creare classe da passare al salvataggio su json

    public void setTitle(String text) {
        titlelabel.setText(text);
    }

    public void setnMosse(String mosse) {
        nMosse.setText(mosse);
    }

    @FXML
    private void reset(ActionEvent actionEvent) {

    }

    @FXML
    public void save(ActionEvent actionEvent) {
        Gson gson = new Gson();
        Level save = new Level(getComponents(), MAX_WIDTH, MAX_HEIGTH, MIN_WIDTH, MIN_HEIGTH);
        String json = gson.toJson(save);

        try (FileWriter writer = new FileWriter("src/main/resources/klotski_ids/data/resume/level_1_SAVE.json")) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void nextBestMove(ActionEvent actionEvent) {
        System.out.println("BESTMOVE");
    }

    @FXML
    public void undo(ActionEvent actionEvent) {
        System.out.println("UNDO");
    }


    private Level readJson(String dir) {
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


    private List<Rectangle> createRectangle(List<Components> components) {
        List<Rectangle> rectangleList = new ArrayList<>();

        if (components == null) {
            throw new IllegalArgumentException("La lista components non può essere null.");
        }
        for (Components element : components) {
            Rectangle rectangle = new Rectangle(element.getWidth(), element.getHeight());
            rectangle.setId(element.getId());
            rectangleList.add(rectangle);
        }
        return rectangleList;
    }

    private List<Point2D> getRectangleAreaPoints(Rectangle rectangle, int colIndex, int rowIndex) {
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
     * Aggiunge alla griglia i rettangoli associati ai componenti specificati, posizionandoli secondo le informazioni
     * contenute nei rispettivi oggetti Components. Se il numero di componenti o di rettangoli è maggiore rispetto all'altro,
     * verranno considerate solo le prime n coppie, dove n è il minimo tra i due numeri.
     *
     * @param gridPane   la griglia a cui aggiungere i rettangoli
     * @param components la lista di componenti
     * @param rectangles la lista di rettangoli
     */
    private void setGridPaneElements(GridPane gridPane, List<Components> components, List<Rectangle> rectangles) {
        int size = Math.min(components.size(), rectangles.size());

        for (int i = 0; i < size; i++) {
            Components component = components.get(i);
            Rectangle rectangle = rectangles.get(i);

            GridPane.setConstraints(rectangle, component.getCol(), component.getRow(), component.getColSpan(), component.getRowSpan());

            gridPane.getChildren().add(rectangle);
        }
    }

    private boolean overlaps(GridPane gridPane, Rectangle rectangle, int newCol, int newRow) {

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

    private boolean isMoveValid(Rectangle rectangle, int col, int row) {
        return GridPane.getRowIndex(rectangle) == row || GridPane.getColumnIndex(rectangle) == col;
    }

    //aggiungere funzione che non faccia andare i blocchi nelle diagonali

    private void setMouseEvent(GridPane gridPane, List<Rectangle> rectangleList) {
        int numRows = gridPane.getRowConstraints().size();
        int numCols = gridPane.getColumnConstraints().size();

        for (Rectangle rectangle : rectangleList) {
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

                    //Aggiungere controllo diagonale
                    if (newCol <= maxCol && newRow <= maxRow && !overlaps(gridPane, rectangle, newCol, newRow) && isMoveValid(rectangle, newCol, newRow)) {
                        GridPane.setRowIndex(rectangle, newRow);
                        GridPane.setColumnIndex(rectangle, newCol);

                        List<Components> updateList = getComponents();
                        for (Components x : updateList) {
                            if (x.getId().equals(rectangle.getId())) {
                                x.setRow(newRow);
                                x.setCol(newCol);
                            }
                        }

                        setComponents(updateList);

                    }

                }

            });

            rectangle.setOnMouseReleased(event -> {
                rectangle.setCursor(Cursor.DEFAULT);
                numMosse++;
                setnMosse(Integer.toString(numMosse));

            });

        }

    }

    public void initialize() {

        Level level_1 = readJson("/klotski_ids/data/level_1.json");

        if (level_1 == null) {
            System.err.println("Errore durante la lettura del file JSON");
            return;
        }

        setComponents(level_1.getRectangles());
        setRectangles(createRectangle(components));

        setGridPaneElements(gridPane, components, rectangles);
        setMouseEvent(gridPane, rectangles);

    }

}
