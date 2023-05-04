package klotski_ids.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Hyperlink;

import klotski_ids.mainApplication;

public class mainController{
    @FXML
    private HBox bottomBar;
    @FXML
    private Button gitHubButton;
    @FXML
    private Button wikiButton;

    public void goToWiki(ActionEvent actionEvent) {
        wikiButton.setOnAction(e -> mainApplication.getInstance().getHostServices().showDocument("https://github.com/enricozano/klotski_ids"));
    }

    public void goToGithub(ActionEvent actionEvent) {
        gitHubButton.setOnAction(e -> mainApplication.getInstance().getHostServices().showDocument("https://github.com/enricozano/klotski_ids"));
    }


}
