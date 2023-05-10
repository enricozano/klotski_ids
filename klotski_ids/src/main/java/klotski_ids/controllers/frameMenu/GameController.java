package klotski_ids.controllers.frameMenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import klotski_ids.controllers.util.Components;
import klotski_ids.controllers.util.Level;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private GridPane gridPane;

    @FXML
    private Label titlelabel;

    public void setTitle(String text) {
        titlelabel.setText(text);
    }

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
