package klotski_ids.controllers.panes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import klotski_ids.controllers.frameMenu.GameController;
import klotski_ids.models.MyAlerts;
import klotski_ids.models.Helper;

import java.io.IOException;
import java.util.Objects;

public class TopBarController {

    @FXML
    public void goHome(ActionEvent actionEvent) throws IOException {
        MyAlerts HomeAlert = new MyAlerts("Go Home");
        if(HomeAlert.confermationAlert()){
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/klotski_ids/mainView.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void goBack(ActionEvent actionEvent) throws IOException {
        MyAlerts goBackAlert =  new MyAlerts("Go Back");
        if(goBackAlert.confermationAlert()){
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/klotski_ids/views/frameMenu/levelMenu.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }
}
