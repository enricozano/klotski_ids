package klotski_ids.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

/**
 The LevelManager class is responsible for managing levels in the Klotski game.
 It provides methods to read and retrieve level data from JSON files.
 */
public class LevelManager {
    /**
     * Retrieves the Level object from the specified file path.
     *
     * @param filePath the path of the level file
     * @return the Level object parsed from the file
     * @throws IOException   if there is an error reading the file
     * @throws JSONException if there is an error parsing the JSON data
     */
    public static Level getLevel(String filePath) throws IOException, JSONException {
        File levelFile = new File(filePath);

        if (levelFile.isAbsolute())
            return getLevelAbs(filePath);

        return getLevelRelative(filePath);
    }


    /**
     * Retrieves the Level object from the specified relative file path.
     *
     * @param filePath the relative path of the level file
     * @return the Level object parsed from the file
     * @throws IOException   if there is an error reading the file
     * @throws JSONException if there is an error parsing the JSON data
     */
    private static Level getLevelRelative(String filePath) throws IOException, JSONException {
        Level level;

        try (InputStream inputStream = LevelManager.class.getResourceAsStream(filePath)) {
            level = readJsonFromStream(inputStream);
        } catch (JSONException e) {
            MyAlerts jsonError = new MyAlerts("Error reading Json");
            jsonError.errorAlert();
            return null;
        }

        return level;
    }


    /**
     * Retrieves the Level object from the specified absolute file path.
     *
     * @param filePath the absolute path of the level file
     * @return the Level object parsed from the file
     * @throws FileNotFoundException if the file specified by the filePath does not exist
     * @throws IOException           if there is an error reading the file
     * @throws JSONException         if there is an error parsing the JSON data
     */
    private static Level getLevelAbs(String filePath) throws FileNotFoundException, IOException, JSONException {
        Level level;
        try (InputStream inputStream = new FileInputStream(filePath)) {
            level = readJsonFromStream(inputStream);
        } catch (JSONException e) {
            MyAlerts jsonError = new MyAlerts("Error reading Json");
            jsonError.errorAlert();
            return null;
        }

        return level;
    }

    /**
     * Reads and parses the JSON data from the provided input stream into a Level object.
     *
     * @param inputStream the input stream containing the JSON data
     * @return the Level object parsed from the JSON data
     * @throws IOException   if there is an error reading the input stream
     * @throws JSONException if there is an error parsing the JSON data
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
            Component rectangle = new Component();
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


}