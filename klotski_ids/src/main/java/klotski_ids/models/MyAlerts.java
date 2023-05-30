package klotski_ids.models;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class MyAlerts {

        private String allertType;
        public MyAlerts(String allertType){
            this.allertType = allertType;
        }

    public boolean confermationAlert() {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(allertType);
            alert.setHeaderText("");
            alert.setContentText("Are you sure you want to continue? \nAll progress will be lost");

            ButtonType confirmButton = new ButtonType("Confirm");
            ButtonType cancelButton = new ButtonType("Cancel");

            alert.getButtonTypes().setAll(confirmButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            return result.isPresent() && result.get() == confirmButton;
        }

        public void errorAlert(){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(allertType);
            alert.setHeaderText("");
            alert.setContentText("Something went wrong");

            alert.showAndWait();
        }
}
