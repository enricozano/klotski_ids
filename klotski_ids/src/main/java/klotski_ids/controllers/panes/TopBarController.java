package klotski_ids.controllers.panes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import klotski_ids.models.MyAlerts;

import java.io.IOException;
import java.util.Objects;

/**
 * Controller class for the top bar in the user interface.
 */
public class TopBarController {

    /**
     * Action event handler for the "Go Home" button.
     * Navigates to the home view.
     *
     * @param actionEvent the action event triggered by the button
     * @throws IOException if an I/O error occurs during navigation
     */
    @FXML
    public void goHome(ActionEvent actionEvent) throws IOException {

        MyAlerts homeAlert = new MyAlerts("Go Home");
        if (homeAlert.confirmationAlert()) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/klotski_ids/mainView.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Action event handler for the "Go Back" button.
     * Navigates to the previous view.
     *
     * @param actionEvent the action event triggered by the button
     * @throws IOException if an I/O error occurs during navigation
     */
    @FXML
    public void goBack(ActionEvent actionEvent) throws IOException {
        MyAlerts goBackAlert = new MyAlerts("Go Back");
        if (goBackAlert.confirmationAlert()) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/klotski_ids/views/frameMenu/levelMenu.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }
}
