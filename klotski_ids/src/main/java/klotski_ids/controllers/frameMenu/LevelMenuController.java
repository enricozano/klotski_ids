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

public class LevelMenuController implements Initializable {

    @FXML
    Button level_1;

    @FXML
    Button level_2;

    @FXML
    Button level_3;

    @FXML
    Button level_4;

    @FXML
    private ScrollPane scrollPane;

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
        Helper helper = new Helper();
        Button button = (Button) actionEvent.getSource();
        String levelTitle = button.getText();
        loadGameScene();

        String levelFileName = button.getId();
        String filePath = "/klotski_ids/data/" + levelFileName + ".json";

        Level level = helper.readJson(filePath);

        gameController.initialize(level, levelTitle, filePath);
        setStageWindow(button);
    }


    private void updateAnchorPanePositions(double scrollPosition) {
        double anchorPaneWidth = 200.0;
        int numLevels = 4;

        double gridPaneWidth = anchorPaneWidth * numLevels + 40;
        gridPane.setPrefWidth(gridPaneWidth);
        gridPane.setVgap(10);

        int level1ColumnIndex = 0;
        int level2ColumnIndex = 1;
        int level3ColumnIndex = 2;
        int level4ColumnIndex = 3;

        GridPane.setColumnIndex(level1AnchorPane, level1ColumnIndex);
        GridPane.setColumnIndex(level2AnchorPane, level2ColumnIndex);
        GridPane.setColumnIndex(level3AnchorPane, level3ColumnIndex);
        GridPane.setColumnIndex(level4AnchorPane, level4ColumnIndex);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrollPane.hvalueProperty().addListener((observable, oldValue, newValue) -> updateAnchorPanePositions(newValue.doubleValue()));
        updateAnchorPanePositions(scrollPane.getHvalue());
    }

}
