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
        Button button = (Button) actionEvent.getSource();

        String folderPath = "/klotski_ids/data/resume/";
        File folder = new File(getClass().getResource(folderPath).getFile());
        File[] files = folder.listFiles();

        if (files != null && files.length == 1) {
            String fileName = files[0].getName();
            String filePath = folderPath + fileName;

            if (gameController == null) {
                loadGameScene();
            }
            gameController.initialize(filePath, gameController.getLevelTitle());
            setStageWindow(button);
        } else {
            System.out.println("No or multiple files found in the directory.");
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