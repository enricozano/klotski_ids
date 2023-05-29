package klotski_ids.controllers.frameMenu;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import klotski_ids.models.MyAlerts;
import klotski_ids.models.Components;
import klotski_ids.models.Level;
import klotski_ids.models.Helper;


import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    private static List<List<Components>> hystoryRectanglesMovements = new ArrayList<>();

    /**
     * An instance of the Helper class.
     */
    private final Helper helper = new Helper();

    /**
     * An instance of the myAlerts class.
     */
    MyAlerts resetAlert = new MyAlerts("Reset");

    /**
     * name of the level
     */
    private String levelName;

    private String levelTitle;



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
        setLevelTitle(text);
        titlelabel.setText(text);
    }

    public void setLevelTitle(String levelTitle){
        this.levelTitle = levelTitle;
    }

    public void setnMosse(String mosse) {
        nMosse.setText(mosse);
    }

    public void setLevelName(String level) {
        levelName = level;
    }

    public void setHystoryRectanglesMovements(List<Components> componentsList) {
        hystoryRectanglesMovements.add(helper.copyComponentsList(componentsList));
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
            System.out.println("RECTANGLE ID: " + rectangle.getId());
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

                if ((newCol <= maxCol && newRow <= maxRow) && helper.isMoveValid(rectangle, newCol, newRow) && !helper.overlaps(gridPane, rectangle, newCol, newRow)) {

                    int deltaCol = Math.abs(GridPane.getColumnIndex(rectangle) - newCol);
                    int deltaRow = Math.abs(GridPane.getRowIndex(rectangle) - newRow);

                    numMosse += (deltaRow + deltaCol);

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
                    setHystoryRectanglesMovements(updateList);

                }

            }
        });
    }

    private void setMouseReleased(Rectangle rectangle) {
        rectangle.setOnMouseReleased(event -> {
            rectangle.setCursor(Cursor.DEFAULT);
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


    public String getLevelTitle (){
        return levelTitle;
    }


    /*******************************************************************************
     *                              FXML BUTTON FUNCTIONS                           *
     *******************************************************************************/

    @FXML
    private void reset(ActionEvent actionEvent) throws IOException {
        if (resetAlert.confermationAlert()) {
            System.out.println("Action confirmed");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/klotski_ids/views/frameMenu/game.fxml"));
            Parent root = loader.load();

            GameController gameController = loader.getController();
            Level level = helper.readJson(getLevelName());
            gameController.initialize(level, getLevelTitle());

            Scene scene = gridPane.getScene();
            scene.setRoot(root);
        } else {
            System.out.println("Action canceled");
        }
    }
    private void resetVariables() {
        numMosse = 0;
        components = new ArrayList<>();
        rectangles = new ArrayList<>();
        hystoryRectanglesMovements = new ArrayList<>();
    }

    @FXML
    public void save(ActionEvent actionEvent) throws IOException {
        Gson gson = new Gson();
        int maxWidth = 100;
        int maxHeight = 100;
        int minWidth = 50;
        int minHeight = 50;
        int countedMoves = numMosse;
        String levelTitle = getLevelTitle();

        Level save = new Level(getComponents(), maxWidth, maxHeight, minWidth, minHeight, countedMoves, levelTitle);
        String json = gson.toJson(save);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salva file");
        fileChooser.setInitialFileName("level_SAVE.json");
        File selectedFile = fileChooser.showSaveDialog(null);

        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();

            try (FileWriter writer = new FileWriter(selectedFile)) {
                writer.write(json);
                System.out.println("File salvato con successo.");
            } catch (IOException e) {
                System.err.println("Errore durante la scrittura del file: " + e.getMessage());
            }
        } else {
            System.out.println("Operazione di salvataggio annullata.");
        }
    }


    //TODO implementare next best move
    @FXML
    public void nextBestMove(ActionEvent actionEvent) {
        System.out.println("BESTMOVE");
    }

    @FXML
    public void undo(ActionEvent actionEvent) {
        int lastIndex = hystoryRectanglesMovements.size() - 1;

        if (lastIndex >= 1) {
            hystoryRectanglesMovements.remove(lastIndex);
            lastIndex--;

            if (numMosse > 0) {
                numMosse--;
                setnMosse(Integer.toString(numMosse));
            }

            List<Components> componentsList = new ArrayList<>(hystoryRectanglesMovements.get(lastIndex));
            gridPane.getChildren().clear();
            helper.setGridPaneElements(gridPane, componentsList, rectangles);

            setComponents(helper.copyComponentsList(componentsList));
        }
    }

    /*******************************************************************************
     *                              INITIALIZE FUNCTION                            *
     *******************************************************************************/

    public void initialize(Level level, String levelTitle) throws IOException {
        resetVariables();

        setLevelName(levelName);
        setTitle(levelTitle);
        numMosse = level.getCountedMoves();
        if (level == null) {
            System.err.println("Errore durante la lettura del file JSON");
            return;
        }

        setComponents(level.getRectangles());
        setRectangles(helper.createRectangle(components));

        setHystoryRectanglesMovements(components);

        helper.setGridPaneElements(gridPane, components, rectangles);
        setMouseEvent(gridPane, rectangles);
    }


}
