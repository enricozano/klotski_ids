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
import klotski_ids.models.*;
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
     * Flag for resumed games
     */
    private boolean isResumed;
    /**
     * Flag to track if any movement has occurred.
     */
    private boolean hasMoved = false;

    /**
     * The instance of KlotskiGame representing the current game.
     */
    private KlotskiGame klotskiGame;

    /**
     * Sets the elements in the GridPane using the components and rectangles from the KlotskiGame.
     */
    private void setGridPaneElements() {
        Helper.setGridPaneElements(gridPane, klotskiGame.getComponents(), klotskiGame.getRectangles());
    }

    /**
     * Checks if the KlotskiGame has reached a winning state. If so, loads the mainView.fxml and sets it as the scene.
     */
    private void isWinSet() {
        if (klotskiGame.isWin()) {
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
        setMouseEvent(gridPane, klotskiGame.getRectangles());
    }

    /**
     * Sets the mouse pressed event handler for the given rectangle.
     * Updates the initial values for mouse movement tracking and changes the cursor.
     *
     * @param rectangle The rectangle to set the mouse pressed event handler for.
     */
    private void setMousePressed(Rectangle rectangle) {

        rectangle.setOnMousePressed(event -> {

            klotskiGame.setStartX(GridPane.getColumnIndex(rectangle));
            klotskiGame.setStartY(GridPane.getRowIndex(rectangle));

            klotskiGame.setStartMouseX(event.getSceneX());
            klotskiGame.setStartMouseY(event.getSceneY());

            klotskiGame.setStartTranslateX(rectangle.getTranslateX());
            klotskiGame.setStartTranslateY(rectangle.getTranslateY());

            rectangle.setCursor(Cursor.CLOSED_HAND);
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
            double offsetX = event.getSceneX() - klotskiGame.getStartMouseX();
            double offsetY = event.getSceneY() - klotskiGame.getStartMouseY();

            double newTranslateX = klotskiGame.getStartTranslateX() + offsetX;
            double newTranslateY = klotskiGame.getStartTranslateY() + offsetY;

            klotskiGame.setCol((int) Math.round(newTranslateX / (gridPane.getWidth() / klotskiGame.getNumCols())));
            klotskiGame.setRow((int) Math.round(newTranslateY / (gridPane.getHeight() / klotskiGame.getNumRows())));

            int newCol = (int) (klotskiGame.getStartX() + klotskiGame.getCol());
            int newRow = (int) (klotskiGame.getStartY() + klotskiGame.getRow());

            int maxCol, maxRow;
            double rectangleWidth = rectangle.getWidth();
            double rectangleHeight = rectangle.getHeight();

            if (rectangleWidth <= klotskiGame.getLevel().getMinWidth() && rectangleHeight <= klotskiGame.getLevel().getMinHeight()) {
                maxCol = klotskiGame.getNumCols();
                maxRow = klotskiGame.getNumRows();
            } else if (rectangleWidth <= klotskiGame.getLevel().getMinWidth() && rectangleHeight > klotskiGame.getLevel().getMinHeight()) {
                maxCol = klotskiGame.getNumCols();
                maxRow = klotskiGame.getNumRows() - 1;
            } else if (rectangleWidth > klotskiGame.getLevel().getMinWidth() && rectangleHeight <= klotskiGame.getLevel().getMinHeight()) {
                maxCol = klotskiGame.getNumCols() - 1;
                maxRow = klotskiGame.getNumRows();
            } else {
                maxCol = klotskiGame.getNumCols() - 1;
                maxRow = klotskiGame.getNumRows() - 1;
            }

            if (newCol < 0 || newRow < 0 || newCol > maxCol || newRow > maxRow) {
                return;
            }

            if (!Helper.isMoveValid(rectangle, newCol, newRow) || Helper.overlaps(gridPane, rectangle, newCol, newRow)) {
                return;
            }

            int deltaCol = Math.abs(GridPane.getColumnIndex(rectangle) - newCol);
            int deltaRow = Math.abs(GridPane.getRowIndex(rectangle) - newRow);


            klotskiGame.numMosse += (deltaRow + deltaCol);

            KlotskiGame.setNextBestMoveCounter(klotskiGame.numMosse);
            GridPane.setRowIndex(rectangle, newRow);
            GridPane.setColumnIndex(rectangle, newCol);

            List<Components> updateList = klotskiGame.getComponents();

            for (Components component : updateList) {
                if (component.getId().equals(rectangle.getId())) {
                    component.setRow(newRow);
                    component.setCol(newCol);
                    break;
                }
            }

            hasMoved = !Helper.isSameComponentsList(KlotskiGame.getDefaultComponentsList(), klotskiGame.getComponents());

            try {
                if (hasMoved && !Helper.isSameComponentsList(KlotskiGame.getDefaultComponentsList(), klotskiGame.getComponents()) && !Helper.PythonInstallationChecker()) {
                    nextBestMove.setDisable(true);
                }
                if (Helper.isSameComponentsList(KlotskiGame.getDefaultComponentsList(), klotskiGame.getComponents()) && !isResumed) {
                    nextBestMove.setDisable(false);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            klotskiGame.setComponents(updateList);
            klotskiGame.setHistoryRectanglesMovements(updateList);
            klotskiGame.setWin(Helper.winCondition(updateList));

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
            nMosse.setText(Integer.toString(klotskiGame.getNumMosse()));
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
                Level level = Helper.readJson(klotskiGame.getLevelFilePath());
                assert level != null;
                gameController.initialize(level, klotskiGame.getLevelFilePath(), isResumed);
            } catch (NullPointerException e) {
                Level level = Helper.readJsonAbsolutePath(klotskiGame.getLevelFilePath());
                assert level != null;
                gameController.initialize(level, klotskiGame.getLevelFilePath(), isResumed);
            }

            Scene scene = gridPane.getScene();
            scene.setRoot(root);
        } else {
            System.out.println("Action canceled");
        }
    }


    /**
     * Saves the current game level to a JSON file.
     */
    @FXML
    private void save() {
        int maxWidth = 100;
        int maxHeight = 100;
        int minWidth = 50;
        int minHeight = 50;
        int countedMoves = klotskiGame.getNumMosse();
        String levelFileName = klotskiGame.getLevel().getLevelFileName();
        String levelTitle = klotskiGame.getLevel().getLevelTitle();

        System.out.println("Level name: " + levelFileName);

        Level save = new Level(klotskiGame.getComponents(), maxWidth, maxHeight, minWidth, minHeight, countedMoves, levelFileName, levelTitle);

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
    @FXML
    private void nextBestMove() throws IOException {

        List<Components> levelComponents = new ArrayList<>(klotskiGame.getComponents());

        if ((!hasMoved || Helper.isSameComponentsList(KlotskiGame.getDefaultComponentsList(), klotskiGame.getComponents())) && !isResumed) {
            klotskiGame.handleDefaultLayout();

        } else if (Helper.PythonInstallationChecker()) {
            String defaultLayoutPath = "src/main/resources/klotski_ids/data/levelSolutions/DefaultLayout.txt";
            if(Helper.writeToFile(defaultLayoutPath, Helper.levelToString(levelComponents))){
                nextBestMove.setDisable(true);
            }
            klotskiGame.handlePythonSolver();
        }

        KlotskiGame.nextBestMoveCounter++;
        nMosse.setText(String.valueOf(KlotskiGame.nextBestMoveCounter));
        klotskiGame.setNumMosse(KlotskiGame.getNextBestMoveCounter());

        gridPane.getChildren().clear();
        klotskiGame.setHistoryRectanglesMovements(klotskiGame.getComponents());
        KlotskiGame.setPythonNextBestMoveComponentsLists(klotskiGame.getComponents());

        Helper.setGridPaneElements(gridPane, klotskiGame.getComponents(), klotskiGame.getRectangles());
        klotskiGame.setWin(Helper.winCondition(klotskiGame.getComponents()));


        isWinSet();
    }


    /**
     * Reverts the last move and restores the previous game state.
     */
    @FXML
    private void undo() {
        int lastIndex = KlotskiGame.getHistoryRectanglesMovements().size() - 1;

        if (lastIndex >= 1) {
            KlotskiGame.getHistoryRectanglesMovements().remove(lastIndex);
            lastIndex--;


            if (KlotskiGame.nextBestMoveCounter > 0) {
                KlotskiGame.nextBestMoveCounter--;

                nMosse.setText(String.valueOf(KlotskiGame.nextBestMoveCounter));
            }

            if (klotskiGame.numMosse > 0) {
                klotskiGame.numMosse--;
                nMosse.setText(String.valueOf(klotskiGame.numMosse));
            }

            List<Components> componentsList = new ArrayList<>(KlotskiGame.getHistoryRectanglesMovements().get(lastIndex));

            gridPane.getChildren().clear();
            Helper.setGridPaneElements(gridPane, componentsList, klotskiGame.getRectangles());

            klotskiGame.setComponents(KlotskiGame.copyComponentsList(componentsList));
        } else {
            hasMoved = false;
        }
    }


    /**
     * Initializes the game with the specified level, file path, and resume flag.
     *
     * @param level     The level to initialize the game with.
     * @param filePath  The file path of the level.
     * @param isResumed A flag indicating if the game is being resumed.
     * @throws IOException If an error occurs during initialization.
     */

    public void initialize(Level level, String filePath, boolean isResumed) throws IOException {
        this.isResumed = isResumed;
        klotskiGame = new KlotskiGame(level, filePath);

        this.titlelabel.setText(klotskiGame.getLevelTitle());
        setGridPaneElements();
        setMouseEventHandlers();
    }


}
