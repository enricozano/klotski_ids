package klotski_ids.models;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.*;

/**
 * The Helper class provides utility methods for various operations.
 */
public class Helper {
    /**
     * The default grid cell width
     */
    private static final int CELL_WIDTH = 50;
    /**
     * The default grid cell height
     */
    private static final int CELL_HEIGHT = 50;
    /**
     * Parses a JSON string and creates a Level object.
     *
     * @param jsonString the JSON string to parse
     * @return the Level object created from the JSON string
     * @throws JSONException if there is an error while parsing the JSON
     */
    private static Level parseJsonString(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        Level level = new Level();
        level.setCountedMoves(jsonObject.getInt("countedMoves"));
        level.setLevelFileName(jsonObject.getString("levelName"));
        level.setLevelTitle(jsonObject.getString("levelTitle"));
        level.setMaxWidth(jsonObject.getInt("maxWidth"));
        level.setMaxHeight(jsonObject.getInt("maxHeight"));
        level.setMinWidth(jsonObject.getInt("minWidth"));
        level.setMinHeight(jsonObject.getInt("minHeight"));

        JSONArray rectanglesJsonArray = jsonObject.getJSONArray("rectangles");
        for (int i = 0; i < rectanglesJsonArray.length(); i++) {
            JSONObject rectangleJson = rectanglesJsonArray.getJSONObject(i);
            Components rectangle = new Components();
            rectangle.setId(rectangleJson.getString("id"));
            rectangle.setWidth(rectangleJson.getInt("width"));
            rectangle.setHeight(rectangleJson.getInt("height"));
            rectangle.setRow(rectangleJson.getInt("row"));
            rectangle.setCol(rectangleJson.getInt("col"));
            rectangle.setColSpan(rectangleJson.getInt("colSpan"));
            rectangle.setRowSpan(rectangleJson.getInt("rowSpan"));
            level.getRectangles().add(rectangle);
        }

        return level;
    }

    /**
     * Creates a JSONObject from a Level object.
     *
     * @param level the Level object to convert
     * @return the JSONObject created from the Level object
     * @throws JSONException if there is an error while creating the JSONObject
     */
    public static JSONObject createJSONObjectFromLevel(Level level) throws JSONException {
        JSONObject jsonObject = new JSONObject(new LinkedHashMap<>());
        jsonObject.put("maxWidth", level.getMaxWidth());
        jsonObject.put("maxHeight", level.getMaxHeight());
        jsonObject.put("minWidth", level.getMinWidth());
        jsonObject.put("minHeight", level.getMinHeight());
        jsonObject.put("countedMoves", level.getCountedMoves());
        jsonObject.put("levelName", level.getLevelFileName());
        jsonObject.put("levelTitle", level.getLevelTitle());

        JSONArray rectanglesJsonArray = new JSONArray();
        for (Components rectangle : level.getRectangles()) {
            JSONObject rectangleJson = new JSONObject(new LinkedHashMap<>());
            rectangleJson.put("id", rectangle.getId());
            rectangleJson.put("width", rectangle.getWidth());
            rectangleJson.put("height", rectangle.getHeight());
            rectangleJson.put("row", rectangle.getRow());
            rectangleJson.put("col", rectangle.getCol());
            rectangleJson.put("colSpan", rectangle.getColSpan());
            rectangleJson.put("rowSpan", rectangle.getRowSpan());
            rectanglesJsonArray.put(rectangleJson);
        }
        jsonObject.put("rectangles", rectanglesJsonArray);

        return jsonObject;
    }

    /**
     * Reads a JSON string from the input stream and converts it to a Level object.
     *
     * @param inputStream the input stream containing the JSON data
     * @return the deserialized Level object
     * @throws IOException   if an I/O error occurs while reading the stream
     * @throws JSONException if the JSON data is invalid
     */
    private static Level readJsonFromStream(InputStream inputStream) throws IOException, JSONException {
        Level level;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String jsonString = sb.toString();

            level = parseJsonString(jsonString);
        }
        return level;
    }

    /**
     * Reads a JSON file from the specified file path.
     *
     * @param filePath the file path of the JSON file
     * @return the deserialized Level object
     * @throws IOException           if an I/O error occurs while reading the file
     */
    public static Level readJson(String filePath) throws IOException, JSONException {
        System.out.println("file path: " + filePath);
        Level level;
        try (InputStream inputStream = Helper.class.getResourceAsStream(filePath)) {
            level = readJsonFromStream(inputStream);
        } catch (JSONException e){
            MyAlerts jsonError = new MyAlerts("Error reading Json");
            jsonError.errorAlert();
            return null;

        }

        return level;
    }


    /**
     * Reads a JSON file from the absolute file path.
     *
     * @param filePath the absolute file path of the JSON file
     * @return the deserialized Level object
     * @throws FileNotFoundException if the JSON file is not found
     * @throws IOException           if an I/O error occurs while reading the file
     */
    public static Level readJsonAbsolutePath(String filePath) throws FileNotFoundException, IOException, JSONException {
        System.out.println("file path: " + filePath);
        Level level;
        try (InputStream inputStream = new FileInputStream(filePath)) {
            level = readJsonFromStream(inputStream);
        }catch (JSONException e){
            MyAlerts jsonError = new MyAlerts("Error reading Json");
            jsonError.errorAlert();
            return null;
        }

        return level;
    }



    /**
     * Returns a list of points representing the area covered by the specified rectangle at the given column and row index
     * <p>
     * in a grid pane.
     *
     * @param rectangle the rectangle for which to retrieve the area points
     * @param colIndex  the column index in the grid pane where the rectangle is located
     * @param rowIndex  the row index in the grid pane where the rectangle is located
     * @return a list of Point2D objects representing the area covered by the rectangle
     */
    public static List<Point2D> getRectangleAreaPoints(Rectangle rectangle, int colIndex, int rowIndex) {
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
     * Checks if a move is valid for a given rectangle in the specified row and column.
     *
     * @param rectangle the rectangle to be moved
     * @param col       the new column to move the rectangle to
     * @param row       the new row to move the rectangle to
     * @return {@code true} if the move is valid, {@code false} otherwise
     */
    public static boolean isMoveValid(Rectangle rectangle, int col, int row) {
        int rectangleRow = GridPane.getRowIndex(rectangle);
        int rectangleCol = GridPane.getColumnIndex(rectangle);

        boolean isSameRow = rectangleRow == row;
        boolean isSameCol = rectangleCol == col;
        boolean isAdjacentRow = Math.abs(row - rectangleRow) == 1;
        boolean isAdjacentCol = Math.abs(col - rectangleCol) == 1;

        return (isSameRow && isAdjacentCol) || (isSameCol && isAdjacentRow);
    }

    /**
     * Checks if a given rectangle overlaps with any other rectangles on the specified cell position on a GridPane.
     *
     * @param gridPane  The GridPane object to search for overlapping rectangles.
     * @param rectangle The Rectangle object to check for overlaps.
     * @param newCol    The column index of the new position to check for overlaps.
     * @param newRow    The row index of the new position to check for overlaps.
     * @return True if the given rectangle overlaps with any other rectangles on the specified cell position, false otherwise.
     */
    public static boolean overlaps(GridPane gridPane, Rectangle rectangle, int newCol, int newRow) {

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

    /**
     * Creates a list of rectangles based on the components passed as argument.
     *
     * @param components a list of Components objects from which to create the rectangles.
     * @return a list of Rectangle objects created from the Components objects.
     * @throws IllegalArgumentException if the components list is null.
     */
    public static List<Rectangle> createRectangle(List<klotski_ids.models.Components> components) {
        List<Rectangle> rectangleList = new ArrayList<>();

        if (components == null) {
            throw new IllegalArgumentException("Component list cant be null");
        }
        for (klotski_ids.models.Components element : components) {
            Rectangle rectangle = new Rectangle(element.getWidth(), element.getHeight());
            rectangle.setId(element.getId());
            rectangleList.add(rectangle);
        }
        return rectangleList;
    }


    /**
     * Adds to the grid the rectangles associated with the specified components, positioning them according to the information
     * contained in their respective Components objects. If the number of components or rectangles is greater than the other,
     * only the first n pairs will be considered, where n is the minimum between the two numbers.
     *
     * @param gridPane   the grid to which the rectangles are added
     * @param components the list of components
     * @param rectangles the list of rectangles
     */
    public static void setGridPaneElements(GridPane gridPane, List<klotski_ids.models.Components> components, List<Rectangle> rectangles) {
        gridPane.getChildren().clear();
        int size = Math.min(components.size(), rectangles.size());

        for (int i = 0; i < size; i++) {
            Components component = components.get(i);
            Rectangle rectangle = rectangles.get(i);
            GridPane.setConstraints(rectangle, component.getCol(), component.getRow(), component.getColSpan(), component.getRowSpan());
            gridPane.getChildren().add(rectangle);
        }
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
     * Checks if two lists of Components are the same.
     *
     * @param componentsList1 the first list of Components
     * @param componentsList2 the second list of Components
     * @return true if the lists are the same, false otherwise
     */
    public static boolean isSameComponentsList(List<Components> componentsList1, List<Components> componentsList2) {
        if (componentsList1.size() != componentsList2.size()) {
            return false;
        }

        for (int i = 0; i < componentsList1.size(); i++) {
            Components comp1 = componentsList1.get(i);
            Components comp2 = componentsList2.get(i);

            if (!comp1.equals(comp2)) {
                return false;
            }
        }

        return true;

    }

    /**
     * Checks if Python is installed on the system.
     *
     * @return true if Python is installed, false otherwise.
     * @throws IOException if an I/O error occurs.
     */
    public static boolean PythonInstallationChecker() throws IOException {
        boolean isPythoninstalled = false;
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python", "--version");
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = reader.readLine();

            if (output != null && output.contains("Python")) {

                isPythoninstalled = true;

            } else {
                System.out.println("python not found");
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return isPythoninstalled;
    }

    /**
     * Executes a Python script as a separate process.
     *
     * @param pythonScriptPath the path to the Python script
     */
    public static void executePythonProcess(String pythonScriptPath) {
        try {
            ProcessBuilder pb = new ProcessBuilder("python", pythonScriptPath);
            Process process = pb.start();

            // Read the output stream
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Read the error stream
            InputStream errorStream = process.getErrorStream();
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                System.err.println(errorLine);
            }

            int exitCode = process.waitFor();
            System.out.println("Python program exited with code: " + exitCode);

        } catch (IOException | InterruptedException e) {

            MyAlerts pythonAllert = new MyAlerts("Can't find python :/");
            pythonAllert.missingPythonAlert();

        }
    }

    /**
     * Converts a list of Components into a string representation of a level.
     *
     * @param rectangles the list of Components representing the level
     * @return the string representation of the level
     */
    public static String levelToString(List<Components> rectangles) {
        StringBuilder levelToStringBuilder = new StringBuilder();

        for (Components rectangle : rectangles) {
            int rectangleHeight = rectangle.getHeight();
            int rectangleWidth = rectangle.getWidth();

            int rectangleAreaDimension = (rectangleWidth * rectangleHeight) / 100;

            String shape;
            if (rectangleAreaDimension == 100) {
                shape = "B";
            } else if (rectangleAreaDimension == 50 && rectangleWidth == 100) {
                shape = "H";
            } else if (rectangleAreaDimension == 50 && rectangleHeight == 100) {
                shape = "V";
            } else {
                shape = "S";
            }

            levelToStringBuilder.append(String.format("%s%n%s", shape, coordsToString(rectangle)));
        }

        return levelToStringBuilder.toString();
    }

    /**
     * Converts the coordinates of a rectangle into a string representation.
     *
     * @param rectangle the rectangle
     * @return the string representation of the rectangle's coordinates
     */
    public static String coordsToString(Components rectangle) {
        String X = Integer.toString(rectangle.getCol());
        String Y = Integer.toString(rectangle.getRow());
        return X + " " + Y + "\n";
    }

    /**
     * Writes content to a file.
     *
     * @param filename the name of the file
     * @param content  the content to write
     * @throws IOException if an I/O error occurs while writing the file
     */
    public static void writeToFile(String filename, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(content);
        writer.close();
    }

    /**
     * Separates the numeric value and action in a list of move strings.
     *
     * @param movesStrings the list of move strings
     * @return a list of pairs, each containing the numeric value and action
     */
    public static List<Pair<Integer, String>> separateNumericValue(List<String> movesStrings) {
        List<Pair<Integer, String>> separatedMoves = new ArrayList<>();

        for (String movesString : movesStrings) {
            String[] parts = movesString.split(" ", 2);
            int number = Integer.parseInt(parts[0]);
            String action = parts[1];

            separatedMoves.add(new Pair<>(number, action));
        }

        return separatedMoves;
    }

    /**
     * Retrieves the move strings from a file.
     *
     * @param filename the name of the file
     * @return a list of move strings
     */
    private static List<String> getMovesStringsFromFile(String filename) {
        List<String> movesStrings = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Pair<Integer, String> move = parseMove(line);
                int number = move.getKey();
                String action = move.getValue();

                if (action.contains(" ")) {
                    String[] splitActions = action.split(" ");
                    for (String splitAction : splitActions) {
                        movesStrings.add(number + " " + splitAction);
                    }
                } else {
                    movesStrings.add(number + " " + action);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return movesStrings;
    }

    /**
     * Parses a move string and returns a pair of numeric value and action.
     *
     * @param line the move string to parse
     * @return a pair containing the numeric value and action
     */
    public static Pair<Integer, String> parseMove(String line) {
        String[] parts = line.trim().split(" ", 2);
        int number = Integer.parseInt(parts[0].substring(1));
        String action = parts[1].trim();
        return new Pair<>(number, action);
    }

    /**
     * Generates the solution file name based on the level name.
     *
     * @param levelName the name of the level
     * @return the solution file name
     */
    public static String getSolutionFileName(String levelName) {
        return switch (levelName) {
            case "Level 1" -> "SolutionsLevel1.txt";
            case "Level 2" -> "SolutionsLevel2.txt";
            case "Level 3" -> "SolutionsLevel3.txt";
            case "Level 4" -> "SolutionsLevel4.txt";
            default -> "";
        };
    }

    /**
     * Retrieves the separated moves from a solution file.
     *
     * @param solutionFileName the name of the solution file
     * @return a list of separated moves
     */
    public static List<Pair<Integer, String>> getSeparatedMoves(String solutionFileName) {
        String filePath = "src/main/resources/klotski_ids/data/levelSolutions/" + solutionFileName;
        List<String> movesStrings = Helper.getMovesStringsFromFile(filePath);
        return Helper.separateNumericValue(movesStrings);
    }

    /**
     * Performs a move action on a list of Components.
     *
     * @param move       the move action to perform
     * @param components the list of Components
     * @return the updated list of Components after the move
     */
    public static List<Components> performMoveAction(Pair<Integer, String> move, List<Components> components) {
        int rectangleNumber = move.getKey();
        String action = move.getValue();
        List<Components> componentsList = new ArrayList<>(components);
        Components component = componentsList.get(rectangleNumber);
        int currentRow = component.getRow();
        int currentCol = component.getCol();

        switch (action) {
            case "UP" -> {
                component.setRow(currentRow - 1);
            }
            case "DOWN" -> {
                component.setRow(currentRow + 1);
            }
            case "RIGHT" -> {
                component.setCol(currentCol + 1);
            }
            case "LEFT" -> {
                component.setCol(currentCol - 1);
            }
        }

        return componentsList;
    }

    /**
     * Checks if the win condition is met based on the provided list of components.
     *
     * @param componentsList the list of components to check for the win condition
     * @return true if the win condition is met, false otherwise
     */
    public static boolean winCondition(List<Components> componentsList) {
        for (Components elem : componentsList) {
            if (elem.getWidth() == 100 && elem.getHeight() == 100) {
                if (elem.getCol() == 1 && elem.getRow() == 3) {
                    MyAlerts winAlert = new MyAlerts("LEVEL COMPLETE!");
                    winAlert.winAlert();
                    return true;
                }
            }
        }
        return false;
    }

}
