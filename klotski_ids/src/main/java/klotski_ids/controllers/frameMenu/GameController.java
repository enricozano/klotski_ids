package klotski_ids.controllers.frameMenu;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import klotski_ids.controllers.util.Components;
import klotski_ids.controllers.util.Level;
import klotski_ids.controllers.util.Helper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The GameController class controls the behavior of the game. It handles mouse events to allow
 * <p>
 * the user to drag and drop puzzle pieces, and updates the number of moves made.
 */
public class GameController {

    /*******************************************************************************
     *                              FXML VARIABLES                                 *
     *******************************************************************************/
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


    /*******************************************************************************
     *                              LOCAL VARIABLES                                *
     *******************************************************************************/
    /**
     * The starting X coordinate of the mouse event.
     */
    private double startX;

    /**
     * The starting Y coordinate of the mouse event.
     */
    private double startY;

    /**
     * The starting translate X value of the rectangle being dragged.
     */
    private double startTranslateX;

    /**
     * The starting translate Y value of the rectangle being dragged.
     */
    private double startTranslateY;

    /**
     * The starting X coordinate of the mouse event relative to the rectangle being dragged.
     */
    private double startMouseX;

    /**
     * The starting Y coordinate of the mouse event relative to the rectangle being dragged.
     */
    private double startMouseY;

    /**
     * The column index of the grid cell containing the top-left corner of the rectangle being dragged.
     */
    private double col;

    /**
     * The row index of the grid cell containing the top-left corner of the rectangle being dragged.
     */
    private double row;

    /**
     * The width of each cell in the grid.
     */
    private static final int CELL_WIDTH = 50;

    /**
     * The height of each cell in the grid.
     */
    private static final int CELL_HEIGHT = 50;

    /**
     * The number of columns in the grid.
     */
    private static final int NUM_COLS = 3;

    /**
     * The number of rows in the grid.
     */
    private static final int NUM_ROWS = 4;

    /**
     * The number of moves made by the player.
     */
    public int numMosse;

    /**
     * A list of the components (rectangles) currently in the grid.
     */
    private List<Components> components = new ArrayList<>();

    /**
     * A list of the rectangles currently in the grid.
     */
    private List<Rectangle> rectangles = new ArrayList<>();

    private List<List<Components>> hystoryRectanglesMovements = new ArrayList<>();

    /**
     * An instance of the Helper class.
     */
    private final Helper helper = new Helper();

    /**
     * name of the level
     */
    private String levelName;

    /*******************************************************************************
     *                              SETTERS FUNCTIONS                              *
     *******************************************************************************/
    public void setComponents(List<Components> components) {
        this.components = components;
    }

    public void setRectangles(List<Rectangle> rectangles) {
        this.rectangles = rectangles;
    }

    public void setTitle(String text) {
        titlelabel.setText(text);
    }

    public void setnMosse(String mosse) {
        nMosse.setText(mosse);
    }

    public void setLevelName(String level) {
        levelName = level;
    }

    public void setHystoryRectanglesMovements(List<Components> hystoryRectanglesMovements) {
        this.hystoryRectanglesMovements.add(hystoryRectanglesMovements);
    }

    private void setMousePressed(Rectangle rectangle) {
        rectangle.setOnMousePressed(event -> {
            startX = GridPane.getColumnIndex(rectangle);
            startY = GridPane.getRowIndex(rectangle);

            startMouseX = event.getSceneX();
            startMouseY = event.getSceneY();

            startTranslateX = rectangle.getTranslateX();
            startTranslateY = rectangle.getTranslateY();

            rectangle.setCursor(Cursor.CLOSED_HAND);
        });
    }

    private void setMouseDragged(GridPane gridPane, Rectangle rectangle) {
        rectangle.setOnMouseDragged(event -> {
            double offsetX = event.getSceneX() - startMouseX;
            double offsetY = event.getSceneY() - startMouseY;

            double newTranslateX = startTranslateX + offsetX;
            double newTranslateY = startTranslateY + offsetY;

            col = (int) Math.round(newTranslateX / (gridPane.getWidth() / NUM_COLS));
            row = (int) Math.round(newTranslateY / (gridPane.getHeight() / NUM_ROWS));

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
                //TODO modificare movimento limitandolo a un solo blocco alla volta
                if ((newCol <= maxCol && newRow <= maxRow) && helper.isMoveValid(rectangle, newCol, newRow) && !helper.overlaps(gridPane, rectangle, newCol, newRow)) {
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
                    setHystoryRectanglesMovements(getComponents());
                }
            }
        });
    }

    //TODO: modificare conteggio delle mosse
    private void setMouseReleased(Rectangle rectangle) {
        rectangle.setOnMouseReleased(event -> {
            rectangle.setCursor(Cursor.DEFAULT);
            numMosse++;
            setnMosse(Integer.toString(numMosse));
        });
    }

    private void setMouseEvent(GridPane gridPane, List<Rectangle> rectangleList) {
        for (Rectangle rectangle : rectangleList) {
            setMousePressed(rectangle);
            setMouseDragged(gridPane, rectangle);
            setMouseReleased(rectangle);
        }
    }


    /*******************************************************************************
     *                              GETTERS FUNCTIONS                              *
     *******************************************************************************/
    public List<Components> getComponents() {
        return components;
    }

    public List<Rectangle> getRectangles() {
        return rectangles;
    }

    public String getLevelName() {
        return levelName;
    }

    public List<List<Components>> getHystoryRectanglesMovements() {
        return hystoryRectanglesMovements;
    }


    /*******************************************************************************
     *                              FXML BUTTON FUNCTIONS                           *
     *******************************************************************************/


    //TODO controllare come impostare nome label dopo reset
    @FXML
    private void reset(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/klotski_ids/views/frameMenu/game.fxml"));
        Parent root = loader.load();

        GameController gameController = loader.getController();
        gameController.initialize(getLevelName());

        Scene scene = gridPane.getScene();
        scene.setRoot(root);
    }

    //TODO controllare funzione di save
    @FXML
    public void save(ActionEvent actionEvent) throws IOException {
        Gson gson = new Gson();
        int maxWidth = 100;
        int maxHeight = 100;
        int minWidth = 50;
        int minHeight = 50;

        Level save = new Level(getComponents(), maxWidth, maxHeight, minWidth, minHeight);
        String json = gson.toJson(save);

        String filePath = "src/main/resources/klotski_ids/data/resume/level_1_SAVE.json";
        File file = new File(filePath);

        try {
            if (file.delete()) {
                System.out.println("File eliminato con successo.");
            } else {
                System.out.println("Impossibile eliminare il file.");
            }

            if (file.createNewFile()) {
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(json);
                    System.out.println("File salvato con successo.");
                } catch (IOException e) {
                    System.err.println("Errore durante la scrittura del file: " + e.getMessage());
                }
            } else {
                System.err.println("Impossibile creare il file " + filePath);
            }
        } catch (SecurityException e) {
            System.err.println("Accesso negato: " + e.getMessage());
        }
    }


    //TODO implementare next best move
    @FXML
    public void nextBestMove(ActionEvent actionEvent) {
        System.out.println("BESTMOVE");
    }

    //TODO implementare undo function, controllare come vengono salvati i dati su getHystoryREctanglesMovements
    @FXML
    public void undo(ActionEvent actionEvent) {
        System.out.println("all components moves");

        int size = getHystoryRectanglesMovements().size();

        helper.setGridPaneElements(gridPane, getHystoryRectanglesMovements().get(size - 1), rectangles);

        //for (List<Components> x : getHystoryRectanglesMovements()) {
          List<Components> x = getHystoryRectanglesMovements().get(0);
            for (Components y : x) {
                System.out.println("id: " + y.getId());
                System.out.println("col: " + y.getCol());
                System.out.println("row: " + y.getRow());
            }
        //}


    }


    /*******************************************************************************
     *                              INITIALIZE FUNCTION                            *
     *******************************************************************************/

    public void initialize(String levelName) throws IOException {
        setLevelName(levelName);
        Level level_1 = helper.readJson(levelName);

        if (level_1 == null) {
            System.err.println("Errore durante la lettura del file JSON");
            return;
        }

        setComponents(level_1.getRectangles());
        setRectangles(helper.createRectangle(components));
        setHystoryRectanglesMovements(getComponents());
        for (Components x : components) {
            System.out.println("ID: " + x.getId());
            System.out.println("COL: " + x.getCol());
            System.out.println("ROW: " + x.getRow());
        }

        helper.setGridPaneElements(gridPane, components, rectangles);
        setMouseEvent(gridPane, rectangles);

    }


}
