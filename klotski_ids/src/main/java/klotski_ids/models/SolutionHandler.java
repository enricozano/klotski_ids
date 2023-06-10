package klotski_ids.models;

import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Helper class provides utility methods for various operations.
 */
public class SolutionHandler {

    /**
     * Retrieves the separated moves from a solution file.
     *
     * @param solutionFileName the name of the solution file
     * @return a list of separated moves
     */
    public static List<Pair<Integer, String>> readSolutions(String solutionFileName) {
        String solutionPath = solutionFileName.equals("Solutions.txt")
                ? "src/main/resources/klotski_ids/data/levelSolutions/"
                : "/klotski_ids/data/levelSolutions/" ;

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
     * Separates the numeric value and action in a list of move strings.
     *
     * @param movesStrings the list of move strings
     * @return a list of pairs, each containing the numeric value and action
     */
    private static List<Pair<Integer, String>> separateNumericValue(List<String> movesStrings) {
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
     * Parses a move string and returns a pair of numeric value and action.
     *
     * @param line the move string to parse
     * @return a pair containing the numeric value and action
     */
    private static List<Pair<Integer, String>> parseSolution(String line) {
        String[] parts = line.trim().split(" ");
        int number = Integer.parseInt(parts[0].substring(1));
        return Arrays.stream(parts).skip(1).map(s -> new Pair<>(number,s.trim())).collect(Collectors.toList());
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


}