package klotski_ids.controllers.frameMenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LevelMenuController {
    @FXML
    Button btnLevel1;
    @FXML
    Button btnLevel2;
    @FXML
    Button btnLevel3;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private GameController gameController;

    @FXML
    private void goToLevel(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();
        String text = button.getText();
        loadGameScene();
        setGameTitle(text);
        setStageWindow(button);
    }

    private void loadGameScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/klotski_ids/views/frameMenu/game.fxml"));
        root = loader.load();
        gameController = loader.getController();
    }

    private void setGameTitle(String text) {
        gameController.setTitle(text);
    }


    private void setStageWindow(Button button) {
        stage = (Stage) button.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
