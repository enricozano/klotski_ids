package klotski_ids.controllers.frameMenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import klotski_ids.models.Helper;
import klotski_ids.models.Level;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LevelMenuController implements Initializable{
    @FXML
    public Button nextButton;
    @FXML
    public Button previousButton;
    @FXML
    Button level_1;

    @FXML
    Button level_2;

    @FXML
    Button level_3;

    @FXML
    Button level_4;

    @FXML
    private GridPane gridPane;

    @FXML
    private AnchorPane level1AnchorPane;

    @FXML
    private AnchorPane level2AnchorPane;

    @FXML
    private AnchorPane level3AnchorPane;

    @FXML
    private AnchorPane level4AnchorPane;

    private Parent root;

    private GameController gameController;

    private void setStageWindow(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    private void loadGameScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/klotski_ids/views/frameMenu/game.fxml"));
        root = loader.load();
        gameController = loader.getController();
    }

    @FXML
    private void goToLevel(ActionEvent actionEvent) throws IOException {
        Button button = (Button) actionEvent.getSource();
        String levelTitle = button.getText();
        loadGameScene();

        String levelFileName = button.getId();
        String filePath = "/klotski_ids/data/" + levelFileName + ".json";

        Level level = Helper.readJson(filePath);

        gameController.initialize(level, levelTitle, filePath);
        setStageWindow(button);
    }

    private int currentLevel = 1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        showLevel(currentLevel);
    }

    public void showPreviousLevel(ActionEvent actionEvent) {
        if (currentLevel > 1) {
            currentLevel--;
            showLevel(currentLevel);
        }
    }

    public void showNextLevel(ActionEvent actionEvent) {
        if (currentLevel < 4) {
            currentLevel++;
            showLevel(currentLevel);
        }
    }

    private void showLevel(int level) {
        level1AnchorPane.setVisible(level == 1);
        level2AnchorPane.setVisible(level == 2);
        level3AnchorPane.setVisible(level == 3);
        level4AnchorPane.setVisible(level == 4);

        previousButton.setDisable(level == 1);
        nextButton.setDisable(level == 4);
    }
}
