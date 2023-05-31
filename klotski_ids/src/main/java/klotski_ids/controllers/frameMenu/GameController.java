package klotski_ids.controllers.frameMenu;

import com.google.gson.Gson;
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
import javafx.stage.FileChooser;
import javafx.util.Pair;
import klotski_ids.models.MyAlerts;
import klotski_ids.models.Components;
import klotski_ids.models.Level;
import klotski_ids.models.Helper;

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
     * A list of default components.
     */
    private static List<Components> defaultComponentsList = new ArrayList<>();
    /**
     * A list of components used for next best move.
     */
    private static List<Components> pythonNextBestMoveComponentsLists = new ArrayList<>();
    /**
     * A list of the rectangles currently in the grid.
     */
    private List<Rectangle> rectangles = new ArrayList<>();

    /**
     * A list to store the movement history of the rectangles.
     */
    private static List<List<Components>> hystoryRectanglesMovements = new ArrayList<>();

    /**
     * An instance of the MyAlerts class for displaying alerts.
     */
    MyAlerts resetAlert = new MyAlerts("Reset");

    /**
     * The name of the level.
     */
    private String levelName;

    /**
     * The title of the level.
     */
    private String levelTitle;

    /**
     * Counter for the number of times the next best move has been selected.
     */
    private static int nextBestMoveCounter = 0;

    /**
     * Flag to track if any movement has occurred.
     */
    private boolean hasMoved = false;

    /**
     * Iterator for the next move in the list of best moves.
     */
    private int nextMoveIterator;

    /**
     * Sets the components list.
     *
     * @param components The components list to set.
     */
    public void setComponents(List<Components> components) {
        this.components = components;
    }

    /**
     * Sets the rectangles list.
     *
     * @param rectangles The rectangles list to set.
     */
    public void setRectangles(List<Rectangle> rectangles) {
        this.rectangles = rectangles;
    }

    /**
     * Sets the title of the level and updates the title label.
     *
     * @param text The title text to set.
     */
    public void setTitle(String text) {
        setLevelTitle(text);
        titlelabel.setText(text);
    }

    /**
     * Sets the level title.
     *
     * @param levelTitle The level title to set.
     */
    public void setLevelTitle(String levelTitle) {
        this.levelTitle = levelTitle;
    }

    /**
     * Sets the number of moves.
     *
     * @param mosse The number of moves to set.
     */
    public void setnMosse(String mosse) {
        this.nMosse.setText(mosse);
    }

    /**
     * Sets the level file path.
     *
     * @param level The level file path to set.
     */
    public void setLevelFilePath(String level) {
        this.levelName = level;
    }

    /**
     * Sets the history of rectangle movements.
     *
     * @param componentsList The components list representing the history of rectangle movements to set.
     */
    public void setHystoryRectanglesMovements(List<Components> componentsList) {
        hystoryRectanglesMovements.add(Helper.copyComponentsList(componentsList));
    }

    /**
     * Sets the default components list.
     *
     * @param componentsList The components list to set as the default components list.
     */
    public void setDefaultComponentsList(List<Components> componentsList) {
        defaultComponentsList = Helper.copyComponentsList(componentsList);
    }

    /**
     * Sets the Python next best move components list.
     *
     * @param componentsList The components list to set as the Python next best move components list.
     */
    public void setPythonNextBestMoveComponentsLists(List<Components> componentsList) {
        pythonNextBestMoveComponentsLists = Helper.copyComponentsList(componentsList);
    }

    /**
     * Sets the mouse pressed event handler for the given rectangle.
     * Updates the initial values for mouse movement tracking and changes the cursor.
     *
     * @param rectangle The rectangle to set the mouse pressed event handler for.
     */
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
    /**
     * Sets the mouse dragged event handler for the given grid pane and rectangle.
     * Handles the movement of the rectangle during mouse dragging.
     *
     * @param gridPane   The grid pane that contains the rectangle.
     * @param rectangle  The rectangle to set the mouse dragged event handler for.
     */
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

                if ((newCol <= maxCol && newRow <= maxRow) && Helper.isMoveValid(rectangle, newCol, newRow) && !Helper.overlaps(gridPane, rectangle, newCol, newRow)) {

                    int deltaCol = Math.abs(GridPane.getColumnIndex(rectangle) - newCol);
                    int deltaRow = Math.abs(GridPane.getRowIndex(rectangle) - newRow);

                    numMosse += (deltaRow + deltaCol);
                    nextBestMoveCounter = numMosse;
                    GridPane.setRowIndex(rectangle, newRow);
                    GridPane.setColumnIndex(rectangle, newCol);

                    List<Components> updateList = getComponents();

                    for (Components x : updateList) {
                        if (x.getId().equals(rectangle.getId())) {
                            x.setRow(newRow);
                            x.setCol(newCol);
                        }
                    }
                    hasMoved = !Helper.isSameComponentsList(defaultComponentsList, getComponents());

                    setComponents(updateList);
                    setHystoryRectanglesMovements(updateList);

                }

            }
        });
    }
    /**
     * Sets the mouse released event handler for the given rectangle.
     * Resets the cursor and updates the number of moves.
     *
     * @param rectangle The rectangle to set the mouse released event handler for.
     */
    private void setMouseReleased(Rectangle rectangle) {
        rectangle.setOnMouseReleased(event -> {
            rectangle.setCursor(Cursor.DEFAULT);
            setnMosse(Integer.toString(numMosse));
        });
    }
    /**
     * Sets the mouse event handlers for the given grid pane and rectangle list.
     *
     * @param gridPane        The grid pane that contains the rectangles.
     * @param rectangleList   The list of rectangles to set the mouse event handlers for.
     */
    private void setMouseEvent(GridPane gridPane, List<Rectangle> rectangleList) {
        for (Rectangle rectangle : rectangleList) {
            setMousePressed(rectangle);
            setMouseDragged(gridPane, rectangle);
            setMouseReleased(rectangle);
        }
    }

    /**
     * Returns the list of components.
     *
     * @return The list of components.
     */
    public List<Components> getComponents() {
        return components;
    }

    /**
     * Returns the list of rectangles.
     *
     * @return The list of rectangles.
     */
    public List<Rectangle> getRectangles() {
        return rectangles;
    }

    /**
     * Returns the level file path.
     *
     * @return The level file path.
     */
    public String getLevelFilePath() {
        return levelName;
    }

    /**
     * Returns the history of rectangle movements.
     *
     * @return The history of rectangle movements.
     */
    public List<List<Components>> getHystoryRectanglesMovements() {
        return hystoryRectanglesMovements;
    }

    /**
     * Returns the level title.
     *
     * @return The level title.
     */
    public String getLevelTitle() {
        return levelTitle;
    }


    /**
     * Resets the variables to their initial values.
     */
    private void resetVariables() {
        nextBestMoveCounter = 0;
        numMosse = 0;
        components = new ArrayList<>();
        rectangles = new ArrayList<>();
        hystoryRectanglesMovements = new ArrayList<>();
    }
    /**
     * Resets the game state and navigates to the game screen.
     *
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    private void reset() throws IOException {
        if (resetAlert.confirmationAlert()) {
            System.out.println("Action confirmed");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/klotski_ids/views/frameMenu/game.fxml"));
            Parent root = loader.load();

            GameController gameController = loader.getController();
            try {
                Level level = Helper.readJson(getLevelFilePath());
                gameController.initialize(level, getLevelTitle(), getLevelFilePath());
            } catch (NullPointerException e) {
                Level level = Helper.readJsonAbsolutePath(getLevelFilePath());
                gameController.initialize(level, getLevelTitle(), getLevelFilePath());
            }

            Scene scene = gridPane.getScene();
            scene.setRoot(root);
        } else {
            System.out.println("Action canceled");
        }
    }


    /**
     * Saves the current game level to a JSON file.
     *
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    public void save() throws IOException {
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

    /**
     * Performs the next best move in the game.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void nextBestMove() throws IOException {
        List<Components> levelComponents = new ArrayList<>(getComponents());
        Helper.writeToFile("src/main/resources/klotski_ids/data/levelSolutions/DefaultLayout.txt", Helper.levelToString(levelComponents));

        if (!hasMoved || Helper.isSameComponentsList(defaultComponentsList, getComponents())) {
            if (Helper.isSameComponentsList(defaultComponentsList, getComponents()) && nextBestMoveCounter != 0) {
                nextBestMoveCounter = 0;
            }

            String levelName = getLevelTitle();
            String solutionFileName = Helper.getSolutionFileName(levelName);
            List<Pair<Integer, String>> separatedMoves = Helper.getSeparatedMoves(solutionFileName);

            if (nextBestMoveCounter < separatedMoves.size()) {
                setComponents(Helper.performMoveAction(separatedMoves.get(nextBestMoveCounter), getComponents()));
            }

        } else {
            if (!Helper.isSameComponentsList(pythonNextBestMoveComponentsLists, getComponents())) {
                Helper.executePythonProcess("src/main/resources/klotski_ids/pythonKlotskiSolver/Main.py");
                nextMoveIterator = 0;
            }

            String solutionFileName = "Solutions.txt";
            List<Pair<Integer, String>> separatedMoves = Helper.getSeparatedMoves(solutionFileName);

            if (nextMoveIterator < separatedMoves.size()) {
                setComponents(Helper.performMoveAction(separatedMoves.get(nextMoveIterator), getComponents()));
                nextMoveIterator++;
            }
        }

        nextBestMoveCounter++;
        setnMosse(Integer.toString(nextBestMoveCounter));
        numMosse = nextBestMoveCounter;

        gridPane.getChildren().clear();
        setHystoryRectanglesMovements(getComponents());
        setPythonNextBestMoveComponentsLists(getComponents());
        Helper.setGridPaneElements(gridPane, getComponents(), rectangles);
    }

    /**
     * Reverts the last move and restores the previous game state.
     */
    @FXML
    public void undo() {
        int lastIndex = hystoryRectanglesMovements.size() - 1;

        if (lastIndex >= 1) {
            hystoryRectanglesMovements.remove(lastIndex);
            lastIndex--;

            if (nextBestMoveCounter > 0) {
                nextBestMoveCounter--;
                setnMosse(Integer.toString(nextBestMoveCounter));
            }

            if (numMosse > 0) {
                numMosse--;
                setnMosse(Integer.toString(numMosse));
            }

            List<Components> componentsList = new ArrayList<>(hystoryRectanglesMovements.get(lastIndex));

            gridPane.getChildren().clear();
            Helper.setGridPaneElements(gridPane, componentsList, rectangles);

            setComponents(Helper.copyComponentsList(componentsList));
        }
    }

    /**
     * Initializes the game with the provided level information.
     *
     * @param level      the Level object containing the level information.
     * @param levelTitle the title of the level.
     * @param filePath   the file path of the level.
     * @throws IOException if an I/O error occurs.
     */
    public void initialize(Level level, String levelTitle, String filePath) throws IOException {
        // Reset variables
        resetVariables();

        // Set level file path
        setLevelFilePath(filePath);

        // Set title
        setTitle(levelTitle);

        // Set number of moves
        numMosse = level.getCountedMoves();

        // Set components and rectangles
        setComponents(level.getRectangles());
        setRectangles(Helper.createRectangle(components));

        // Set history of rectangle movements
        setHystoryRectanglesMovements(components);

        // Set default components list
        setDefaultComponentsList(components);

        // Set grid pane elements
        Helper.setGridPaneElements(gridPane, components, rectangles);

        // Set mouse event handlers
        setMouseEvent(gridPane, rectangles);
    }

    //TODO controllare funzione nexbest move e undo
    //TODO aggiungere win condition
    //TODO controllare iteratori e counter mosse
    //TODO aggiungere test junit

}
