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
import klotski_ids.models.MyAlerts;


import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Controller class for the start menu in the user interface.
 */
public class StartMenuController {
    /**
     * The button for resuming the game.
     */
    @FXML
    public Button resumeGame;

    /**
     * The GameController instance responsible for managing the game logic and user interactions.
     */
    private GameController gameController;

    /**
     * The root parent element of the scene.
     */
    private Parent root;

    /**
     * The MyAlerts instance used for displaying alert messages in the game.
     */
    MyAlerts alerts;


    /**
     * Action event handler for switching to the select level scene.
     * Loads the level menu scene and switches to it.
     *
     * @param actionEvent the action event triggered by the button click
     * @throws IOException if an I/O error occurs during loading the scene
     */
    @FXML
    public void switchToSelectLevelScene(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/klotski_ids/views/frameMenu/levelMenu.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Action event handler for resuming a saved game.
     * Prompts the user to select a saved game file, loads the game scene, and initializes the game controller.
     *
     * @param actionEvent the action event triggered by the button click
     */
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
                Level level = Helper.readJsonAbsolutePath(filePath);

                if (level != null) {
                    System.out.println("Level: " + level.getLevelTitle());
                    gameController.initialize(level, filePath, true);
                    setStageWindow(button);
                } else {
                    System.err.println("Error loading the selected file.");
                }
            }
        } catch (IOException e) {
            System.err.println("IOException occurred: " + e.getMessage());
            e.printStackTrace();
            alerts.errorAlert();
        }  catch (IllegalStateException e) {
            System.err.println("IllegalStateException occurred: " + e.getMessage());
            e.printStackTrace();
            alerts.errorAlert();
        } catch (IllegalArgumentException e) {
            System.err.println("IllegalArgumentException occurred: " + e.getMessage());
            e.printStackTrace();
            alerts.errorAlert();
        }
    }


    /**
     * Loads the game scene and initializes the game controller.
     *
     * @throws IOException if an I/O error occurs during loading the scene
     */
    private void loadGameScene() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/klotski_ids/views/frameMenu/game.fxml"));
        root = loader.load();
        gameController = loader.getController();
    }

    /**
     * Sets the stage window to display the loaded game scene.
     *
     * @param button the button that triggered the action event
     */
    private void setStageWindow(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}