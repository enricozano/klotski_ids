package klotski_ids.models;

import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The SolutionHandler class provides utility methods for handling solutions in the Klotski game.
 * It includes methods to read solution files, parse solution data, and retrieve solutions as a list of moves.
 */
public class SolutionHandler {

    /**
     * Reads the solutions for the specified level from a file and returns them as a list of move pairs.
     *
     * @param levelName the name of the level to read solutions for
     * @return a list of move pairs representing the solutions for the level
     */
    public static List<Pair<Integer, String>> readSolutions(String levelName) {
        String solutionFileName = getSolutionFileName(levelName);
        String solutionPath = solutionFileName.equals("Solutions.txt")
                ? "src/main/resources/klotski_ids/data/levelSolutions/"
                : "/klotski_ids/data/levelSolutions/";

        return getSolutionsFromFile(solutionPath + solutionFileName);
    }


    /**
     * Retrieves the move strings from a file.
     *
     * @param filePath path of the file
     * @return a list of move strings
     */
    private static List<Pair<Integer, String>> getSolutionsFromFile(String filePath) {
        List<Pair<Integer, String>> movesStrings = new ArrayList<>();

        try (BufferedReader reader = createBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                List<Pair<Integer, String>> moves = parseSolution(line);
                movesStrings.addAll(moves);
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
    private static List<Pair<Integer, String>> parseSolution(String line) {
        String[] parts = line.trim().split(" ");
        int number = Integer.parseInt(parts[0].substring(1));
        return Arrays.stream(parts).skip(1).map(s -> new Pair<>(number, s.trim())).collect(Collectors.toList());
    }

    /**
     * Creates a BufferedReader based on the file path.
     *
     * @param filePath the file path
     * @return the created BufferedReader
     * @throws IOException if an error occurs while creating the BufferedReader
     */
    private static BufferedReader createBufferedReader(String filePath) throws IOException {

        if (filePath.contains("Solutions.txt")) {
            return new BufferedReader(new FileReader(filePath));
        }
        InputStream inputStream = SolutionHandler.class.getResourceAsStream(filePath);
        assert inputStream != null;
        InputStreamReader reader = new InputStreamReader(inputStream);
        return new BufferedReader(reader);
    }

    /**
     * Generates the solution file name based on the level name.
     *
     * @param levelName the name of the level
     * @return the solution file name
     */
    private static String getSolutionFileName(String levelName) {
        return switch (levelName) {
            case "Level 1" -> "SolutionsLevel1.txt";
            case "Level 2" -> "SolutionsLevel2.txt";
            case "Level 3" -> "SolutionsLevel3.txt";
            case "Level 4" -> "SolutionsLevel4.txt";
            default -> "Solutions.txt";
        };
    }


}