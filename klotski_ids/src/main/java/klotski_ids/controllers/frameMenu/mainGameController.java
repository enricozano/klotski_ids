package klotski_ids.controllers.frameMenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class mainGameController {
    @FXML
    private Label titlelabel;

    public void setTitle(String text) {
        titlelabel.setText(text);
    }

    public Stage setMainGameTitle(ActionEvent actionEvent, String text) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/klotski_ids/views/frameMenu/mainGame.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        mainGameController mainGameController = loader.getController();
        mainGameController.setTitle(text);
        return stage;
    }

}
