package klotski_ids.models;

import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * The KlotskiGame class represents a game of Klotski
 * It manages the game state and provides methods to interact with the game.
 */
public class KlotskiGame {
    /**
     * The number of columns in the grid.
     */
    private static final int NUM_COLS = 3;

    /**
     * The number of rows in the grid.
     */
    private static final int NUM_ROWS = 4;


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
    private static List<List<Components>> historyRectanglesMovements = new ArrayList<>();

    /**
     * The path of the file
     */
    private String levelFilePath;

    /**
     * The title of the level.
     */
    private String levelTitle;


    /**
     * Iterator for the next move in the list of best moves.
     */
    private int nextMoveIterator;


    /**
     * Flag for the win condition
     */
    private boolean win;


    /**
     * Sets the level.
     *
     * @param level The level.
     */
    public void setLevel(Level level) {
        this.level = level;
    }

    /**
     * Sets the list of components.
     *
     * @param components The list of components.
     */
    public void setComponents(List<Components> components) {
        this.components = components;
    }

    /**
     * Sets the default components list.
     *
     * @param componentsList The default components list.
     */
    public static void setDefaultComponentsList(List<Components> componentsList) {
        defaultComponentsList = copyComponentsList(componentsList);
    }

    /**
     * Sets the Python next best move components lists.
     *
     * @param componentsList The Python next best move components lists.
     */
    public static void setPythonNextBestMoveComponentsLists(List<Components> componentsList) {
        pythonNextBestMoveComponentsLists = copyComponentsList(componentsList);
    }

    /**
     * Sets the list of rectangles.
     *
     * @param rectangles The list of rectangles.
     */
    public void setRectangles(List<Rectangle> rectangles) {
        this.rectangles = rectangles;
    }

    /**
     * Sets the history of rectangle movements.
     *
     * @param componentsList The history of rectangle movements.
     */
    public void setHistoryRectanglesMovements(List<Components> componentsList) {
        historyRectanglesMovements.add(copyComponentsList(componentsList));
    }

    /**
     * Sets the level file path.
     *
     * @param levelFilePath The level file path.
     */
    public void setLevelFilePath(String levelFilePath) {
        this.levelFilePath = levelFilePath;
    }


    /**
     * Sets the flag indicating if the game has been won.
     *
     * @param win The flag indicating if the game has been won.
     */
    public void setWin(boolean win) {
        this.win = win;
    }

    /**
     * Sets the title of the level and updates the title label.
     *
     * @param text The title text to set.
     */
    public void setTitle(String text) {
        this.levelTitle = text;
    }

    /**
     * Returns the number of columns.
     *
     * @return The number of columns.
     */
    public int getNumCols() {
        return NUM_COLS;
    }

    /**
     * Returns the number of rows.
     *
     * @return The number of rows.
     */
    public int getNumRows() {
        return NUM_ROWS;
    }


    /**
     * Returns the level.
     *
     * @return The level.
     */
    public Level getLevel() {
        return level;
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
     * Returns the default components list.
     *
     * @return The default components list.
     */
    public static List<Components> getDefaultComponentsList() {
        return defaultComponentsList;
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
     * Returns the history of rectangle movements.
     *
     * @return The history of rectangle movements.
     */
    public static List<List<Components>> getHistoryRectanglesMovements() {
        return historyRectanglesMovements;
    }

    /**
     * Returns the level file path.
     *
     * @return The level file path.
     */
    public String getLevelFilePath() {
        return levelFilePath;
    }

    /**
     * Returns the title of the level.
     *
     * @return The title of the level.
     */
    public String getLevelTitle() {
        return levelTitle;
    }


    /**
     * Returns whether the game has been won.
     *
     * @return True if the game has been won, false otherwise.
     */
    public boolean isWin() {
        return win;
    }


    /**
     * Sets the components and rectangles for the level.
     * The components are obtained from the level, and the rectangles are created using the Helper.createRectangle() method.
     */
    public void setComponentsAndRectangles() {
        components = level.getRectangles();
        rectangles = Helper.createRectangle(components);
        setHistoryRectanglesMovements(components);
        setDefaultComponentsList(components);
    }


    /**
     * Constructs a new instance of the KlotskiGame class with default values.
     * This constructor is used to initialize variables.
     */
    public KlotskiGame() {
        resetVariables();
    }

    /**
     * Constructs a new instance of the KlotskiGame class with the specified level and level file path.
     *
     * @param level         The level to initialize the game with.
     * @param levelFilePath The file path of the level.
     */
    public KlotskiGame(Level level, String levelFilePath) {
        // Reset variables
        resetVariables();

        // Set level and file path
        setLevel(level);
        setLevelFilePath(levelFilePath);

        // Set UI elements
        setTitle(level.getLevelTitle());
        setComponentsAndRectangles();
    }

    /**
     * Resets the variables to their initial values.
     */
    public void resetVariables() {
        components = new ArrayList<>();
        rectangles = new ArrayList<>();
        historyRectanglesMovements = new ArrayList<>();
        defaultComponentsList = new ArrayList<>();
        pythonNextBestMoveComponentsLists = new ArrayList<>();
        nextMoveIterator = 0;
    }

    /**
     * Creates a deep copy of a list of Components.
     *
     * @param originalList the original list of Components
     * @return the copied list of Components
     */
    public static List<Components> copyComponentsList(List<Components> originalList) {
        if (originalList == null) {
            throw new IllegalArgumentException("The original list cannot be null.");
        }

        List<Components> copyList = new ArrayList<>();
        for (Components component : originalList) {
            Components copy = new Components(component.getId(), component.getRow(), component.getCol(), component.getColSpan(), component.getRowSpan(), component.getWidth(), component.getHeight());
            copyList.add(copy);
        }
        return copyList;
    }


    /**
     * Checks if the win condition is met based on the provided list of components.
     *
     * @param componentsList the list of components to check for the win condition
     * @return true if the win condition is met, false otherwise
     */
    public boolean winCondition(List<Components> componentsList) {
        for (Components elem : componentsList) {
            if (elem.getWidth() == 100 && elem.getHeight() == 100) {
                if (elem.getCol() == 1 && elem.getRow() == 3) {

                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Handles the default layout for the next best move.
     */
    public void handleDefaultLayout(int nextBestMoveCounter) {
        if (Helper.isSameComponentsList(defaultComponentsList, components) && nextBestMoveCounter != 0) {
            nextBestMoveCounter = 0;
        }

        String levelName = levelTitle;

        String solutionFileName = Helper.getSolutionFileName(levelName);
        List<Pair<Integer, String>> separatedMoves = SolutionHandler.readSolutions(solutionFileName);

        int moveListSize = separatedMoves.size();

        if (nextBestMoveCounter < moveListSize) {
            System.out.println(separatedMoves.get(nextBestMoveCounter) + "\n");
            components = Helper.performMoveAction(separatedMoves.get(nextBestMoveCounter), getComponents());
        }

    }

    /**
     * Handles the Python solver for the next best move.
     */
    public void handlePythonSolver() {
        if (!Helper.isSameComponentsList(pythonNextBestMoveComponentsLists, getComponents())) {
            String pythonScriptPath = "src/main/resources/klotski_ids/pythonKlotskiSolver/Main.py";
            PythonHandler.runPythonScript(pythonScriptPath);
            nextMoveIterator = 0;
        }

        String solutionFileName = "Solutions.txt";
        List<Pair<Integer, String>> separatedMoves = SolutionHandler.readSolutions(solutionFileName);
        int moveListSize = separatedMoves.size();


        if (nextMoveIterator < moveListSize) {
            components = Helper.performMoveAction(separatedMoves.get(nextMoveIterator), getComponents());
            nextMoveIterator++;
        }
    }

}
