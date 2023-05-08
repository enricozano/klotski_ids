package klotski_ids.controllers.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class Level{
    private int maxWidth;
    private int maxHeight;
    private int minWidth;
    private int minHeight;
    private List<Components> rectangles;

    public List<Components> getRectangles() {
        return rectangles;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public int getMinWidth() {
        return minWidth;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    public Level readJson(String dir) {
        Level level = null;
        try {
            File file = new File(getClass().getResource(dir).getFile());
            level = JsonUtil.readValue(file, Level.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return level;
    }

}
