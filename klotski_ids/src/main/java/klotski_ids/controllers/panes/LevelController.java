package klotski_ids.controllers.panes;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import klotski_ids.controllers.util.Components;
import klotski_ids.controllers.util.Level;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class LevelController implements Initializable {

    @FXML
    private GridPane gridPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Level level_1 = new Level().readJson("/klotski_ids/data/level_1.json");

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