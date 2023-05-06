package klotski_ids.controllers.frameMenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class levelMenuController extends mainGameController{

@FXML
    private void goToLevel1(ActionEvent actionEvent) throws IOException {
        Stage stage = setMainGameTitle(actionEvent,"livello n 1");
        stage.show();
    }

    @FXML
    private void goToLevel2(ActionEvent actionEvent) throws IOException {
        Stage stage = setMainGameTitle(actionEvent,"livelloooo 2");
        stage.show();
    }

    @FXML
    private void goToLevel3(ActionEvent actionEvent) throws IOException {
        Stage stage = setMainGameTitle(actionEvent,"treeeeee");
        stage.show();
    }

}
