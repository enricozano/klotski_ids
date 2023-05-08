package klotski_ids.controllers.util;

import com.google.gson.Gson;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class Level {
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

            InputStream inputStream = getClass().getResourceAsStream(dir);
            if (inputStream == null) {
                throw new FileNotFoundException("File non trovato: " + dir);
            }

            Gson gson = new Gson();
            InputStreamReader reader = new InputStreamReader(inputStream);
            level = gson.fromJson(reader, Level.class);
        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file JSON: " + e.getMessage());
            e.printStackTrace();
        }
        return level;
    }


    private void setMouseEvent(Rectangle rectangle, GridPane gridPane) {
        rectangle.setOnMouseDragged(event -> {
            System.out.println("DIOCANE MUOVITI");
        });
    }



    private Rectangle createRectangle(Components component) {
        Rectangle rectangle = new Rectangle(component.getWidth(), component.getHeight());
        rectangle.setId(component.getId());
        return rectangle;
    }

    public void setGridPane(GridPane gridPane, List<Components> components) {
        if (components == null) {
            throw new IllegalArgumentException("La lista components non può essere null.");
        }
        for (Components component : components) {
            Rectangle rectangle = createRectangle(component);
            GridPane.setConstraints(rectangle, component.getCol(), component.getRow(), component.getColSpan(), component.getRowSpan());
            gridPane.getChildren().add(rectangle);
            setMouseEvent(rectangle,gridPane);
        }
    }
}
