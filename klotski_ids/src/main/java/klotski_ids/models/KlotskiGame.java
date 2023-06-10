package klotski_ids.models;

import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private List<Component> components = new ArrayList<>();
    /**
     * A list of default components.
     */
    private static List<Component> defaultComponentsList = new ArrayList<>();
    /**
     * A list of components used for next best move.
     */
    private static List<Component> pythonNextBestMoveComponentsLists = new ArrayList<>();
    /**
     * A list of the rectangles currently in the grid.
     */
    private List<Rectangle> rectangles = new ArrayList<>();

    /**
     * A list to store the movement history of the rectangles.
     */
    private static List<List<Component>> historyRectanglesMovements = new ArrayList<>();

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
    public void setComponents(List<Component> components) {
        this.components = components;
    }

    /**
     * Sets the default components list.
     *
     * @param componentsList The default components list.
     */
    public static void setDefaultComponentsList(List<Component> componentsList) {
        defaultComponentsList = copyComponentsList(componentsList);
    }

    /**
     * Sets the Python next best move components lists.
     *
     * @param componentsList The Python next best move components lists.
     */
    public static void setPythonNextBestMoveComponentsLists(List<Component> componentsList) {
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
    public void setHistoryRectanglesMovements(List<Component> componentsList) {
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
    public List<Component> getComponents() {
        return components;
    }

    /**
     * Returns the default components list.
     *
     * @return The default components list.
     */
    public static List<Component> getDefaultComponentsList() {
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
    public static List<List<Component>> getHistoryRectanglesMovements() {
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
        rectangles = components.stream().map(Component::toRectangle).collect(Collectors.toList());
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
    public static List<Component> copyComponentsList(List<Component> originalList) {
        if (originalList == null) {
            throw new IllegalArgumentException("The original list cannot be null.");
        }

        List<Component> copyList = new ArrayList<>();
        for (Component component : originalList) {
            Component copy = new Component(component.getId(), component.getRow(), component.getCol(), component.getColSpan(), component.getRowSpan(), component.getWidth(), component.getHeight());
            copyList.add(copy);
        }
        return copyList;
    }

    /**
     * Performs a move action on a list of Components.
     *
     * @param move       the move action to perform
     * @param components the list of Components
     * @return the updated list of Components after the move
     */
    private static List<Component> performMoveAction(Pair<Integer, String> move, List<Component> components) {
        int rectangleNumber = move.getKey();
        String action = move.getValue();
        List<Component> componentsList = new ArrayList<>(components);
        Component component = componentsList.get(rectangleNumber);
        int currentRow = component.getRow();
        int currentCol = component.getCol();

        switch (action) {
            case "UP" -> component.setRow(currentRow - 1);
            case "DOWN" -> component.setRow(currentRow + 1);
            case "RIGHT" -> component.setCol(currentCol + 1);
            case "LEFT" -> component.setCol(currentCol - 1);
        }

        return componentsList;
    }

    /**

     Checks if the given component is the big orange rectangle.
     @param element the component to check
     @return true if the component is the big orange rectangle, false otherwise
     */
    private static boolean isTheBigOrangeRectangle(Component element) {
        return element.getWidth() == 100 && element.getHeight() == 100;
    }

    /**
     * Checks if the win condition is met based on the provided list of components.
     *
     * @param componentsList the list of components to check for the win condition
     * @return true if the win condition is met, false otherwise
     */
    public boolean winCondition(List<Component> componentsList) {
        return componentsList.stream()
                .anyMatch(elem -> isTheBigOrangeRectangle(elem)
                        && elem.getCol() == 1 && elem.getRow() == 3);
    }

    /**
     * Handles the default layout by performing the next best move action.
     *
     * @param nextBestMoveCounter the counter for the next best move
     */
    public void handleDefaultLayout(int nextBestMoveCounter) {


        List<Pair<Integer, String>> separatedMoves = SolutionHandler.readSolutions(levelTitle);

        int moveListSize = separatedMoves.size();

        if (nextBestMoveCounter < moveListSize) {
            components = performMoveAction(separatedMoves.get(nextBestMoveCounter), getComponents());
        }

    }

    /**
     * Handles the Python solver for the next best move.
     */
    public void handlePythonSolver() {
        if (!Objects.equals(pythonNextBestMoveComponentsLists, getComponents())) {
            String pythonScriptPath = "src/main/resources/klotski_ids/pythonKlotskiSolver/Main.py";
            PythonHandler.runPythonScript(pythonScriptPath);
            nextMoveIterator = 0;
        }

        String solutionFileName = "Solutions.txt";
        List<Pair<Integer, String>> separatedMoves = SolutionHandler.readSolutions(solutionFileName);
        int moveListSize = separatedMoves.size();


        if (nextMoveIterator < moveListSize) {
            components = performMoveAction(separatedMoves.get(nextMoveIterator), getComponents());
            nextMoveIterator++;
        }
    }

}
