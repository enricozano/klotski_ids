package klotski_ids.controllers.frameMenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LevelMenuController {
    @FXML
    Button level_1;
    @FXML
    Button level_2;
    @FXML
    Button level_3;
    private Parent root;

    private GameController gameController;

    @FXML
    private void goToLevel(ActionEvent actionEvent) throws IOException {
        Button button = (Button) actionEvent.getSource();
        String text = button.getText();
        loadGameScene();
        gameController.setTitle(text);
        String levelName = button.getId();
        System.out.println(levelName);
        gameController.initialize("/klotski_ids/data/" + levelName + ".json");
        setStageWindow(button);
    }

    private void loadGameScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/klotski_ids/views/frameMenu/game.fxml"));
        root = loader.load();
        gameController = loader.getController();
    }


    private void setStageWindow(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
