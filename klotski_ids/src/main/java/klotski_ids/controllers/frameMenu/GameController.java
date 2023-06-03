package klotski_ids.controllers.frameMenu;

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
import javafx.stage.Stage;
import javafx.util.Pair;
import klotski_ids.models.MyAlerts;
import klotski_ids.models.Components;
import klotski_ids.models.Level;
import klotski_ids.models.Helper;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The GameController class controls the behavior of the game. It handles mouse events to allow
 * <p>
 * the user to drag and drop puzzle pieces, and updates the number of moves made.
 */
public class GameController {

    /**
     * The undo button for reversing the previous move.
     */
    @FXML
    public Button undo;

    /**
     * The button for executing the next best move.
     */
    @FXML
    public Button nextBestMove;

    /**
     * The button for resetting the game to its initial state.
     */
    @FXML
    public Button reset;

    /**
     * The button for saving the current game state.
     */
    @FXML
    public Button save;

    /**
     * The label for displaying the number of moves.
     * It keeps track of the number of moves performed.
     */
    @FXML
    public Label nMosse;

    /**
     * The GridPane that represents the game board.
     */
    @FXML
    private GridPane gridPane;

    /**
     * The label for displaying the title of the level.
     */
    @FXML
    private Label titlelabel;

    /**
     * The number of columns in the grid.
     */
    private static final int NUM_COLS = 3;

    /**
     * The number of rows in the grid.
     */
    private static final int NUM_ROWS = 4;


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
     * The number of moves made by the player.
     */
    public int numMosse;
    /**
     * The json values of the level
     */
    private Level level;

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
     * The path of the file
     */
    private String levelFilePath;

    /**
     * The title of the level.
     */
    private String levelTitle;

    /**
     * Counter for the number of times the next best move has been selected.
     */
    private static int nextBestMoveCounter;

    /**
     * Flag to track if any movement has occurred.
     */
    private boolean hasMoved = false;

    /**
     * Iterator for the next move in the list of best moves.
     */
    private int nextMoveIterator;

    /**
     * Flag for resumed games
     */
    private boolean isResumed;
    /**
     * Flag for the win condition
     */
    private boolean win;
    /**
     * Sets the number of moves for the level.
     *
     * @param numberOfMoves The number of moves to set.
     */
    private void setNumberOfMoves(int numberOfMoves) {
        numMosse = numberOfMoves;
    }

    /**
     * Sets the components and rectangles for the level.
     * The components are obtained from the level, and the rectangles are created using the Helper.createRectangle() method.
     */
    private void setComponentsAndRectangles() {
        components = level.getRectangles();
        rectangles = Helper.createRectangle(components);
    }

    /**
     * Sets the elements of the GridPane.
     * Uses the Helper.setGridPaneElements() method to set the elements of the GridPane using the components and rectangles.
     */
    private void setGridPaneElements() {
        Helper.setGridPaneElements(gridPane, components, rectangles);
    }

    /**
     * Sets the mouse event handlers for the GridPane.
     * If the Python installation check using Helper.PythonInstallationChecker() returns false and the game is resumed, disables the nextBestMove button.
     * Sets the mouse event handlers using the setMouseEvent() method for the GridPane and rectangles.
     *
     * @throws IOException If an input/output exception occurs while setting the mouse event handlers.
     */
    private void setMouseEventHandlers() throws IOException {
        if (!Helper.PythonInstallationChecker() && isResumed) {
            nextBestMove.setDisable(true);
        }
        setMouseEvent(gridPane, rectangles);
    }

    /**
     * Checks if the win condition is set and opens the main view if it is true.
     */
    private void isWinSet() {
        if (win) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/klotski_ids/mainView.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) gridPane.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Sets the title of the level and updates the title label.
     *
     * @param text The title text to set.
     */
    public void setTitle(String text) {
        this.levelTitle = text;
        titlelabel.setText(text);
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
     * Returns the list of components.
     *
     * @return The list of components.
     */
    public List<Components> getComponents() {
        return components;
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
     * @param gridPane  The grid pane that contains the rectangle.
     * @param rectangle The rectangle to set the mouse dragged event handler for.
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

            int maxCol, maxRow;
            double rectangleWidth = rectangle.getWidth();
            double rectangleHeight = rectangle.getHeight();

            if (rectangleWidth <= level.getMinWidth() && rectangleHeight <= level.getMinHeight()) {
                maxCol = NUM_COLS;
                maxRow = NUM_ROWS;
            } else if (rectangleWidth <= level.getMinWidth() && rectangleHeight > level.getMinHeight()) {
                maxCol = NUM_COLS;
                maxRow = NUM_ROWS - 1;
            } else if (rectangleWidth > level.getMinWidth() && rectangleHeight <= level.getMinHeight()) {
                maxCol = NUM_COLS - 1;
                maxRow = NUM_ROWS;
            } else {
                maxCol = NUM_COLS - 1;
                maxRow = NUM_ROWS - 1;
            }

            if (newCol < 0 || newRow < 0 || newCol > maxCol || newRow > maxRow) {
                return;
            }

            if (!Helper.isMoveValid(rectangle, newCol, newRow) || Helper.overlaps(gridPane, rectangle, newCol, newRow)) {
                return;
            }

            int deltaCol = Math.abs(GridPane.getColumnIndex(rectangle) - newCol);
            int deltaRow = Math.abs(GridPane.getRowIndex(rectangle) - newRow);

            numMosse += (deltaRow + deltaCol);
            nextBestMoveCounter = numMosse;
            GridPane.setRowIndex(rectangle, newRow);
            GridPane.setColumnIndex(rectangle, newCol);

            List<Components> updateList = getComponents();

            for (Components component : updateList) {
                if (component.getId().equals(rectangle.getId())) {
                    component.setRow(newRow);
                    component.setCol(newCol);
                    break;
                }
            }

            hasMoved = !Helper.isSameComponentsList(defaultComponentsList, getComponents());

            try {
                if (hasMoved && !Helper.isSameComponentsList(defaultComponentsList, getComponents()) && !Helper.PythonInstallationChecker()) {
                    nextBestMove.setDisable(true);
                }
                if (Helper.isSameComponentsList(defaultComponentsList, getComponents()) && !isResumed) {
                    nextBestMove.setDisable(false);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            components = updateList;
            setHystoryRectanglesMovements(updateList);
            win = Helper.winCondition(updateList);
            isWinSet();
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
            nMosse.setText(Integer.toString(numMosse));
        });

    }


    /**
     * Sets the mouse event handlers for the given grid pane and rectangle list.
     *
     * @param gridPane      The grid pane that contains the rectangles.
     * @param rectangleList The list of rectangles to set the mouse event handlers for.
     */
    private void setMouseEvent(GridPane gridPane, List<Rectangle> rectangleList) {

        for (Rectangle rectangle : rectangleList) {
            setMousePressed(rectangle);
            setMouseDragged(gridPane, rectangle);
            setMouseReleased(rectangle);
        }

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
        defaultComponentsList = new ArrayList<>();
        pythonNextBestMoveComponentsLists = new ArrayList<>();
        nextMoveIterator = 0;
    }

    /**
     * Resets the game state and navigates to the game screen.
     *
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    private void reset() throws IOException {
        MyAlerts resetAlert = new MyAlerts("Reset");
        if (resetAlert.confirmationAlert()) {
            System.out.println("Action confirmed");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/klotski_ids/views/frameMenu/game.fxml"));
            Parent root = loader.load();

            GameController gameController = loader.getController();
            try {
                Level level = Helper.readJson(levelFilePath);
                assert level != null;
                gameController.initialize(level, levelFilePath, isResumed);
            } catch (NullPointerException e) {
                Level level = Helper.readJsonAbsolutePath(levelFilePath);
                assert level != null;
                gameController.initialize(level, levelFilePath, isResumed);
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
        int maxWidth = 100;
        int maxHeight = 100;
        int minWidth = 50;
        int minHeight = 50;
        int countedMoves = numMosse;
        String levelFileName = level.getLevelFileName();
        String levelTitle = level.getLevelTitle();

        System.out.println("Level name: " + levelFileName);

        Level save = new Level(getComponents(), maxWidth, maxHeight, minWidth, minHeight, countedMoves, levelFileName, levelTitle);

        JSONObject jsonObject = Helper.createJSONObjectFromLevel(save);
        String json = jsonObject.toString();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salva file");
        fileChooser.setInitialFileName("level_SAVE.json");
        File selectedFile = fileChooser.showSaveDialog(null);

        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();

            try (FileWriter writer = new FileWriter(filePath)) {
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
        String defaultLayoutPath = "src/main/resources/klotski_ids/data/levelSolutions/DefaultLayout.txt";
        Helper.writeToFile(defaultLayoutPath, Helper.levelToString(levelComponents));

        if ((!hasMoved || Helper.isSameComponentsList(defaultComponentsList, getComponents())) && !isResumed) {
            handleDefaultLayout();
        } else if (Helper.PythonInstallationChecker()) {
            handlePythonSolver();
        }

        nextBestMoveCounter++;
        nMosse.setText(String.valueOf(nextBestMoveCounter));
        numMosse = nextBestMoveCounter;

        gridPane.getChildren().clear();
        setHystoryRectanglesMovements(getComponents());
        setPythonNextBestMoveComponentsLists(getComponents());
        Helper.setGridPaneElements(gridPane, getComponents(), rectangles);
        win = Helper.winCondition(getComponents());
        isWinSet();
    }

    /**
     * Handles the default layout for the next best move.
     *
     * @throws IOException if an I/O error occurs.
     */
    private void handleDefaultLayout() throws IOException {
        if (Helper.isSameComponentsList(defaultComponentsList, getComponents()) && nextBestMoveCounter != 0) {
            nextBestMoveCounter = 0;
        }

        String levelName = levelTitle;
        String solutionFileName = Helper.getSolutionFileName(levelName);
        List<Pair<Integer, String>> separatedMoves = Helper.getSeparatedMoves(solutionFileName);
        int moveListSize = separatedMoves.size();

        if (nextBestMoveCounter < moveListSize) {
            components = Helper.performMoveAction(separatedMoves.get(nextBestMoveCounter), getComponents());
        }
    }

    /**
     * Handles the Python solver for the next best move.
     *
     * @throws IOException if an I/O error occurs.
     */
    private void handlePythonSolver() throws IOException {
        if (!Helper.isSameComponentsList(pythonNextBestMoveComponentsLists, getComponents())) {
            String pythonScriptPath = "src/main/resources/klotski_ids/pythonKlotskiSolver/Main.py";
            Helper.executePythonProcess(pythonScriptPath);
            nextMoveIterator = 0;
        }

        String solutionFileName = "Solutions.txt";
        List<Pair<Integer, String>> separatedMoves = Helper.getSeparatedMoves(solutionFileName);
        int moveListSize = separatedMoves.size();

        if (nextMoveIterator < moveListSize) {
            components = Helper.performMoveAction(separatedMoves.get(nextMoveIterator), getComponents());
            nextMoveIterator++;
        }
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
                nMosse.setText(Integer.toString(nextBestMoveCounter));
            }

            if (numMosse > 0) {
                numMosse--;
                nMosse.setText(Integer.toString(numMosse));
            }

            List<Components> componentsList = new ArrayList<>(hystoryRectanglesMovements.get(lastIndex));

            gridPane.getChildren().clear();
            Helper.setGridPaneElements(gridPane, componentsList, rectangles);

            components = Helper.copyComponentsList(componentsList);
        }
    }



    /**
     * Initializes the game level with the specified parameters.
     *
     * @param filePath  The file path of the game level data.
     * @param isResumed Indicates whether the game is being resumed from a saved state.
     * @throws IOException If an error occurs during initialization.
     */

    public void initialize(Level level, String filePath, boolean isResumed) throws IOException {
        // Check if it's a default level or resumed level
        this.isResumed = isResumed;

        // Reset variables
        resetVariables();

        // Set level and file path
        this.level = level;
        levelFilePath = filePath;

        // Set UI elements
        setTitle(level.getLevelTitle());
        setNumberOfMoves(level.getCountedMoves());
        setComponentsAndRectangles();
        setHystoryRectanglesMovements(components);
        setDefaultComponentsList(components);
        setGridPaneElements();
        setMouseEventHandlers();
    }


}
