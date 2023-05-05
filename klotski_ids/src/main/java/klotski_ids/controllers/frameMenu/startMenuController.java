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


    public void switchToSelectLevelScene(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/klotski_ids/views/frameMenu/levelMenu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void resumeGame(ActionEvent actionEvent) throws  IOException{
        System.out.println("DIOBUBU SARÁ da fare cosa con upload da file server merda");
    }
}
