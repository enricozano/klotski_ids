package klotski_ids.controllers.panes;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import klotski_ids.controllers.util.Components;
import klotski_ids.controllers.util.Level;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.zip.GZIPInputStream;


public class LevelController implements Initializable {

    @FXML
    private GridPane gridPane;
    private final int GRIDCOLUMNS = 4;
    private final int GRIDROWS = 5;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Level level_1 = new Level().readJson("/klotski_ids/data/level_1.json");
        if (level_1 == null) {
            System.err.println("Errore durante la lettura del file JSON");
            return;
        }
        List<Components> components = level_1.getRectangles();

        level_1.setGridPaneElements(gridPane, components);

    }
}