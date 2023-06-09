package klotski_ids.controllers.frameMenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import klotski_ids.models.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the level menu in the user interface.
 * Implements the Initializable interface.
 */
public class LevelMenuController implements Initializable {
    /**
     * The button for navigating to the next page.
     */
    @FXML
    public Button nextButton;

    /**
     * The button for navigating to the previous page.
     */
    @FXML
    public Button previousButton;

    /**
     * The button for selecting level 1.
     */
    @FXML
    Button level_1;

    /**
     * The button for selecting level 2.
     */
    @FXML
    Button level_2;

    /**
     * The button for selecting level 3.
     */
    @FXML
    Button level_3;

    /**
     * The button for selecting level 4.
     */
    @FXML
    Button level_4;

    /**
     * The anchor pane for level 1.
     */
    @FXML
    private AnchorPane level1AnchorPane;

    /**
     * The anchor pane for level 2.
     */
    @FXML
    private AnchorPane level2AnchorPane;

    /**
     * The anchor pane for level 3.
     */
    @FXML
    private AnchorPane level3AnchorPane;

    /**
     * The anchor pane for level 4.
     */
    @FXML
    private AnchorPane level4AnchorPane;

    /**
     * The root parent element of the scene.
     */
    private Parent root;
    /**
     * The GameController instance responsible for managing the game logic and user interactions.
     */
    private GameController gameController;

    /**
     * The current level number or identifier.
     */
    private int currentLevel = 1;

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
     * Action event handler for navigating to a specific level.
     * Loads the game scene, reads the level data from the corresponding JSON file, and initializes the game controller.
     *
     * @param actionEvent the action event triggered by the button click
     * @throws IOException if an I/O error occurs during loading the scene
     */
    @FXML
    private void goToLevel(ActionEvent actionEvent) throws IOException {
        Button button = (Button) actionEvent.getSource();
        loadGameScene();

        String levelFileName = button.getId();
        String filePath = "/klotski_ids/data/" + levelFileName + ".json";


        if (!PythonHandler.isPythonInstalled()) {
            MyAlerts pythonAlert = new MyAlerts("Can't find python :/");
            pythonAlert.missingPythonAlert();
        }

        Level level = LevelManager.getLevel(filePath);

        if (level != null) {
            gameController.initialize(level, filePath, false);
            setStageWindow(button);
        } else {
            System.err.println("Error loading the selected file.");
        }

    }


    /**
     * Initializes the level menu.
     * Shows the initial level based on the currentLevel variable.
     *
     * @param url            the URL location of the FXML file
     * @param resourceBundle the resource bundle used by the FXML file
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showLevel(currentLevel);
    }

    /**
     * Displays the previous level in the level menu.
     * Decrements the currentLevel variable and updates the visibility of level anchor panes and navigation buttons.
     */
    public void showPreviousLevel() {
        if (currentLevel > 1) {
            currentLevel--;
            showLevel(currentLevel);
        }
    }

    /**
     * Displays the next level in the level menu.
     * Increments the currentLevel variable and updates the visibility of level anchor panes and navigation buttons.
     */
    public void showNextLevel() {
        if (currentLevel < 4) {
            currentLevel++;
            showLevel(currentLevel);
        }
    }

    /**
     * Shows the specified level in the level menu.
     * Sets the visibility of level anchor panes and enables/disables navigation buttons accordingly.
     *
     * @param level the level to be shown
     */
    private void showLevel(int level) {
        level1AnchorPane.setVisible(level == 1);
        level2AnchorPane.setVisible(level == 2);
        level3AnchorPane.setVisible(level == 3);
        level4AnchorPane.setVisible(level == 4);

        previousButton.setDisable(level == 1);
        nextButton.setDisable(level == 4);
    }
}
