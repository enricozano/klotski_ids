package klotski_ids.models;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.io.*;
import java.util.*;

public class Helper {
    private static final int CELL_WIDTH = 50;
    private static final int CELL_HEIGHT = 50;


    /**
     * Reads the JSON file from the specified path and returns a deserialized Level object.
     *
     * @param filePath The path of the JSON file to read
     * @return A Level object deserialized from the JSON file
     * @throws FileNotFoundException if the specified file does not exist
     * @throws IOException           if an error occurs while reading the JSON file
     * @throws JsonSyntaxException   if the JSON file is not formatted correctly
     */
    public static Level readJson(String filePath) throws FileNotFoundException, IOException, JsonSyntaxException {
        System.out.println("file path: " + filePath);
        Level level = null;
        try (InputStream inputStream = Helper.class.getResourceAsStream(filePath)) {
            assert inputStream != null;
            try (InputStreamReader reader = new InputStreamReader(inputStream)) {
                Gson gson = new Gson();
                JsonReader jsonReader = new JsonReader(reader);
                jsonReader.setLenient(true);
                level = gson.fromJson(jsonReader, Level.class);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
            throw e;
        } catch (IOException | JsonSyntaxException e) {
            System.err.println("Error while reading JSON file: " + e.getMessage());
            throw e;
        }
        return level;
    }

    public static Level readJsonAbsolutePath(String filePath) throws FileNotFoundException, IOException, JsonSyntaxException {
        Level level = null;
        try (InputStream inputStream = new FileInputStream(filePath)) {
            try (InputStreamReader reader = new InputStreamReader(inputStream)) {
                Gson gson = new Gson();
                JsonReader jsonReader = new JsonReader(reader);
                jsonReader.setLenient(true);
                level = gson.fromJson(jsonReader, Level.class);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
            throw e;
        } catch (IOException | JsonSyntaxException e) {
            System.err.println("Error while reading JSON file: " + e.getMessage());
            throw e;
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
            throw new IllegalArgumentException("La lista components non può essere null.");
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

    public static List<Components> copyComponentsList(List<Components> originalList) {
        List<Components> copyList = new ArrayList<>();
        for (Components component : originalList) {
            Components copy = new Components(component.getId(), component.getRow(), component.getCol(), component.getColSpan(), component.getRowSpan(), component.getWidth(), component.getHeight());
            copyList.add(copy);
        }
        return copyList;
    }

    public static boolean isSameComponentsList(List<Components> componentsList1, List<Components> componentsList2){
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

            MyAlerts pythonAllert = new MyAlerts("Missing python?");
            pythonAllert.missingPythonAlert();

        }
    }


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

    public static String coordsToString(Components rectangle){
        String X = Integer.toString(rectangle.getCol());
        String Y = Integer.toString(rectangle.getRow());
        return X + " " + Y + "\n";
    }


    public static void writeToFile(String filename, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(content);
        writer.close();
    }


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

    public static List<String> getMovesStringsFromFile(String filename) {
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

    public static Pair<Integer, String> parseMove(String line) {
        String[] parts = line.trim().split(" ", 2);
        int number = Integer.parseInt(parts[0].substring(1));
        String action = parts[1].trim();
        return new Pair<>(number, action);
    }

    public static String getSolutionFileName(String levelName) {
        String solutionFileName = "";
        switch (levelName) {
            case "Level 1":
                solutionFileName = "SolutionsLevel1.txt";
                break;
            case "Level 2":
                solutionFileName = "SolutionsLevel2.txt";
                break;
            case "Level 3":
                solutionFileName = "SolutionsLevel3.txt";
                break;
            case "Level 4":
                solutionFileName = "SolutionsLevel4.txt";
                break;
        }
        return solutionFileName;
    }

    public static List<Pair<Integer, String>> getSeparatedMoves(String solutionFileName) {
        List<String> movesStrings = Helper.getMovesStringsFromFile("src/main/resources/klotski_ids/data/levelSolutions/" + solutionFileName);
        return Helper.separateNumericValue(movesStrings);
    }

    public static List<Components> performMoveAction(Pair<Integer, String> move, List<Components> components) {
        int rectangleNumber = move.getKey();
        String action = move.getValue();
        List<Components> componentsList = new ArrayList<>(components);
        Components component = componentsList.get(rectangleNumber);
        int currentRow = component.getRow();
        int currentCol = component.getCol();

        System.out.println("Rectangle ID: " + component.getId() + "  Number: " + rectangleNumber + ", Action: " + action);

        switch (action) {
            case "UP":
                System.out.println("up");
                System.out.println("current Row " + currentRow + " current col " + currentCol);
                component.setRow(currentRow - 1);
                break;
            case "DOWN":
                System.out.println("down");
                System.out.println("current Row " + currentRow + " current col " + currentCol);
                component.setRow(currentRow + 1);
                break;
            case "RIGHT":
                System.out.println("right");
                System.out.println("current Row " + currentRow + " current col " + currentCol);
                component.setCol(currentCol + 1);
                break;
            case "LEFT":
                System.out.println("left");
                System.out.println("current Row " + currentRow + " current col " + currentCol);
                component.setCol(currentCol - 1);
                break;
        }

        return componentsList;
    }

}
