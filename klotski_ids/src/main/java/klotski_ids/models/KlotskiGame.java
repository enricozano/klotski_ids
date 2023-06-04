package klotski_ids.models;

import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

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
     * Counter for the number of times the next best move has been selected.
     */
    public static int nextBestMoveCounter;

    /**
     * Iterator for the next move in the list of best moves.
     */
    private int nextMoveIterator;


    /**
     * Flag for the win condition
     */
    private boolean win;

    /**
     * Sets the X-coordinate of the starting position.
     *
     * @param startX The X-coordinate of the starting position.
     */
    public void setStartX(double startX) {
        this.startX = startX;
    }

    /**
     * Sets the Y-coordinate of the starting position.
     *
     * @param startY The Y-coordinate of the starting position.
     */
    public void setStartY(double startY) {
        this.startY = startY;
    }

    /**
     * Sets the X translation of the starting position.
     *
     * @param startTranslateX The X translation of the starting position.
     */
    public void setStartTranslateX(double startTranslateX) {
        this.startTranslateX = startTranslateX;
    }

    /**
     * Sets the Y translation of the starting position.
     *
     * @param startTranslateY The Y translation of the starting position.
     */
    public void setStartTranslateY(double startTranslateY) {
        this.startTranslateY = startTranslateY;
    }

    /**
     * Sets the X-coordinate of the mouse position when dragging starts.
     *
     * @param startMouseX The X-coordinate of the mouse position when dragging starts.
     */
    public void setStartMouseX(double startMouseX) {
        this.startMouseX = startMouseX;
    }

    /**
     * Sets the Y-coordinate of the mouse position when dragging starts.
     *
     * @param startMouseY The Y-coordinate of the mouse position when dragging starts.
     */
    public void setStartMouseY(double startMouseY) {
        this.startMouseY = startMouseY;
    }

    /**
     * Sets the column index.
     *
     * @param col The column index.
     */
    public void setCol(double col) {
        this.col = col;
    }

    /**
     * Sets the row index.
     *
     * @param row The row index.
     */
    public void setRow(double row) {
        this.row = row;
    }

    /**
     * Sets the number of moves.
     *
     * @param numMosse The number of moves.
     */
    public void setNumMosse(int numMosse) {
        this.numMosse = numMosse;
    }

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
        defaultComponentsList = Helper.copyComponentsList(componentsList);
    }

    /**
     * Sets the Python next best move components lists.
     *
     * @param componentsList The Python next best move components lists.
     */
    public static void setPythonNextBestMoveComponentsLists(List<Components> componentsList) {
        pythonNextBestMoveComponentsLists = Helper.copyComponentsList(componentsList);
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
        historyRectanglesMovements.add(Helper.copyComponentsList(componentsList));
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
     * Sets the counter for the next best move.
     *
     * @param nextBestMoveCounter The counter for the next best move.
     */
    public static void setNextBestMoveCounter(int nextBestMoveCounter) {
        KlotskiGame.nextBestMoveCounter = nextBestMoveCounter;
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
     * Returns the X-coordinate of the starting position.
     *
     * @return The X-coordinate of the starting position.
     */
    public double getStartX() {
        return startX;
    }

    /**
     * Returns the Y-coordinate of the starting position.
     *
     * @return The Y-coordinate of the starting position.
     */
    public double getStartY() {
        return startY;
    }

    /**
     * Returns the X translation of the starting position.
     *
     * @return The X translation of the starting position.
     */
    public double getStartTranslateX() {
        return startTranslateX;
    }

    /**
     * Returns the Y translation of the starting position.
     *
     * @return The Y translation of the starting position.
     */
    public double getStartTranslateY() {
        return startTranslateY;
    }

    /**
     * Returns the X-coordinate of the mouse position when dragging starts.
     *
     * @return The X-coordinate of the mouse position when dragging starts.
     */
    public double getStartMouseX() {
        return startMouseX;
    }

    /**
     * Returns the Y-coordinate of the mouse position when dragging starts.
     *
     * @return The Y-coordinate of the mouse position when dragging starts.
     */
    public double getStartMouseY() {
        return startMouseY;
    }

    /**
     * Returns the column index.
     *
     * @return The column index.
     */
    public double getCol() {
        return col;
    }

    /**
     * Returns the row index.
     *
     * @return The row index.
     */
    public double getRow() {
        return row;
    }

    /**
     * Returns the number of moves.
     *
     * @return The number of moves.
     */
    public int getNumMosse() {
        return numMosse;
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
     * Returns the counter for the next best move.
     *
     * @return The counter for the next best move.
     */
    public static int getNextBestMoveCounter() {
        return nextBestMoveCounter;
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
        setNumMosse(level.getCountedMoves());
        setComponentsAndRectangles();
    }

    /**
     * Resets the variables to their initial values.
     */
    public void resetVariables() {
        nextBestMoveCounter = 0;
        numMosse = 0;
        components = new ArrayList<>();
        rectangles = new ArrayList<>();
        historyRectanglesMovements = new ArrayList<>();
        defaultComponentsList = new ArrayList<>();
        pythonNextBestMoveComponentsLists = new ArrayList<>();
        nextMoveIterator = 0;
    }


    /**
     * Handles the default layout for the next best move.
     */
    public void handleDefaultLayout() {
        if (Helper.isSameComponentsList(defaultComponentsList, components) && nextBestMoveCounter != 0) {
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
     */
    public void handlePythonSolver() {
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

}
