package klotski_ids.controllers.util;

import java.util.List;

public class Level{
    private int maxWidth;
    private int maxHeight;
    private int minWidth;
    private int minHeight;

    private final List<Components> rectangles;

    public Level(List<Components> rectangles, int maxWidth, int maxHeight, int minWidth, int minHeight) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.rectangles = rectangles;
    }

    public Level(List<Components> rectangles) {
        this.rectangles = rectangles;
    }

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



}
