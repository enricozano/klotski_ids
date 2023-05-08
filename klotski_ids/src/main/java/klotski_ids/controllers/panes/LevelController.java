package klotski_ids.controllers.panes;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


public class LevelController implements Initializable {

    @FXML
    private GridPane gridPane;

    private Level readJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        Level level = null;
        try {
            ClassLoader classLoader = getClass().getClassLoader();

            File file = new File(Objects.requireNonNull(classLoader.getResource("level_1.json")).getFile());
            level = objectMapper.readValue(file, Level.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return level;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Level level_1 = readJson();

        List<Components> rectangles = level_1.getRectangles();

        for (Components rectangle : rectangles) {
            System.out.println(rectangle.getId());
        }

        //creare costruttore di rectangle con id, width, height, row and col, rowSpan, colSpan
        Rectangle rectangle_1 = new Rectangle(rectangles.get(0).getWidth(),rectangles.get(0).getHeight());
        rectangle_1.setId(rectangles.get(0).getId());

        gridPane.add(rectangle_1,rectangles.get(0).getCol(),rectangles.get(0).getRow(),rectangles.get(0).getColSpan(),rectangles.get(0).getRowSpan());
    }
}