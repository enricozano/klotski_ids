package klotski_ids.models;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class MyAlerts {

        public boolean confermationAlert(String s) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(s);
            alert.setHeaderText("");
            alert.setContentText("Are you sure you want to continue? \nAll progress will be lost");

            ButtonType confirmButton = new ButtonType("Confirm");
            ButtonType cancelButton = new ButtonType("Cancel");

            alert.getButtonTypes().setAll(confirmButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            return result.isPresent() && result.get() == confirmButton;
        }

}
