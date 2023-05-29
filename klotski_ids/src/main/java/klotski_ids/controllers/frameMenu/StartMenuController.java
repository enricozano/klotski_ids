package klotski_ids.controllers.frameMenu;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import klotski_ids.models.Helper;
import klotski_ids.models.Level;


import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class StartMenuController {
    @FXML
    public Button resumeGame;
    private GameController gameController;
    private Parent root;

    @FXML
    public void switchToSelectLevelScene(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/klotski_ids/views/frameMenu/levelMenu.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //TODO capire come aggiornare la direcory per la lettura del file
    @FXML
    private void resumeGame(ActionEvent actionEvent) throws IOException {
        Helper helper = new Helper();
        Button button = (Button) actionEvent.getSource();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Carica file");
        fileChooser.setInitialFileName("level_SAVE.json");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String filePath = selectedFile.getPath();
            loadGameScene();
            System.out.println("File path: " + filePath);
            Level level = helper.readJsonAbsolutePath(filePath);

            if (level != null) {
                System.out.println("Level: " + level.getLevelTitle());
                gameController.initialize(level, level.getLevelTitle());
                setStageWindow(button);
            } else {
                System.err.println("Error loading the selected file.");
            }
        } else {
            String filePath = "/klotski_ids/data/resume/level_SAVE.json";
            loadGameScene();
            System.out.println("File path: " + filePath);
            Level level = helper.readJson(filePath);

            if (level != null) {
                gameController.initialize(level, level.getLevelTitle());
                setStageWindow(button);
            } else {
                System.err.println("Error loading the default file.");
            }
        }
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