package klotski_ids.controllers.panes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import klotski_ids.models.Allerts;
import klotski_ids.models.Helper;

import java.io.IOException;
import java.util.Objects;

public class TopBarController {
    Allerts allerts=new Allerts();
    @FXML
    public void goHome(ActionEvent actionEvent) throws IOException {
        if(allerts.confermationAllert()){
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/klotski_ids/mainView.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }


    //TODO mettere solo nei livelli l'allert non anche nella selezione
    @FXML
    public void goBack(ActionEvent actionEvent) throws IOException {
        if(allerts.confermationAllert()){
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/klotski_ids/views/frameMenu/levelMenu.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }
}
