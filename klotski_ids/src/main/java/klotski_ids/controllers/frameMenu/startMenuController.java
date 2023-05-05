package klotski_ids.controllers.frameMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import klotski_ids.mainApplication;

import java.io.IOException;

public class startMenuController {
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

    public void switchToSelectLevelScene(ActionEvent actionEvent) throws IOException {
        String css = this.getClass().getResource("/klotski_ids/assets/styles/levelMenuStyle.css").toExternalForm();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/klotski_ids/views/frameMenu/levelMenu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
