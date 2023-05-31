package klotski_ids.controllers.frameMenu;

import com.google.gson.JsonSyntaxException;
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
import klotski_ids.models.MyAlerts;


import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class StartMenuController {
    @FXML
    public Button resumeGame;
    private GameController gameController;
    private Parent root;

    MyAlerts alerts;
    @FXML
    public void switchToSelectLevelScene(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/klotski_ids/views/frameMenu/levelMenu.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void resumeGame(ActionEvent actionEvent) {
        alerts = new MyAlerts("Error");

        Button button = (Button) actionEvent.getSource();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Carica file");
        fileChooser.setInitialFileName("level_SAVE.json");

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("JSON Files", "*.json");
        fileChooser.getExtensionFilters().add(extensionFilter);

        try {
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                String filePath = selectedFile.getPath();
                loadGameScene();
                System.out.println("File path: " + filePath);
                Level level = Helper.readJsonAbsolutePath(filePath);

                if (level != null) {
                    System.out.println("Level: " + level.getLevelTitle());
                    gameController.initialize(level, level.getLevelTitle(), filePath);
                    setStageWindow(button);
                } else {
                    System.err.println("Error loading the selected file.");
                    alerts.errorAlert();
                }
            }
        } catch (IOException e) {
            System.err.println("IOException occurred: " + e.getMessage());
            e.printStackTrace();
            alerts.errorAlert();
        } catch (JsonSyntaxException e) {
            System.err.println("JsonSyntaxException occurred: " + e.getMessage());
            e.printStackTrace();
            alerts.errorAlert();
        } catch (IllegalStateException e){
            System.err.println("IllegalStateException occurred: " + e.getMessage());
            e.printStackTrace();
            alerts.errorAlert();
        } catch (IllegalArgumentException e){
            System.err.println("IllegalArgumentException occurred: " + e.getMessage());
            e.printStackTrace();
            alerts.errorAlert();
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