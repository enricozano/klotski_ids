package klotski_ids.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.LinkedHashMap;

/**
 * The Helper class provides utility methods for various operations.
 */
public class LevelManager {
    /**
     * Reads a JSON file from the specified file path.
     *
     * @param filePath the file path of the JSON file
     * @return the deserialized Level object
     * @throws IOException if an I/O error occurs while reading the file
     */
    public static Level getLevel(String filePath) throws IOException, JSONException {
        File levelFile = new File(filePath);

        if (levelFile.isAbsolute())
            return getLevelAbs(filePath);

        return getLevelRel(filePath);
    }

    private static Level getLevelRel(String filePath) throws IOException, JSONException {
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
     * Reads a JSON file from the absolute file path.
     *
     * @param filePath the absolute file path of the JSON file
     * @return the deserialized Level object
     * @throws FileNotFoundException if the JSON file is not found
     * @throws IOException           if an I/O error occurs while reading the file
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


}