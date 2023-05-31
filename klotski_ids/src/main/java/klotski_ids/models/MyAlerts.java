package klotski_ids.models;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * This class provides utility methods for displaying custom alert dialogs.
 */
public class MyAlerts {

    private final String alertType;

    /**
     * Constructs a new instance of MyAlerts with the specified alert type.
     *
     * @param alertType the type of the alert dialogs.
     */
    public MyAlerts(String alertType) {
        this.alertType = alertType;
    }

    /**
     * Displays a confirmation alert dialog with the specified message.
     *
     * @return {@code true} if the user confirms the action, {@code false} otherwise.
     */
    public boolean confirmationAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(alertType);
        alert.setHeaderText("");
        alert.setContentText("Are you sure you want to continue? \nAll progress will be lost");

        ButtonType confirmButton = new ButtonType("Confirm");
        ButtonType cancelButton = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(confirmButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == confirmButton;
    }

    /**
     * Displays an error alert dialog with a generic error message.
     */
    public void errorAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(alertType);
        alert.setHeaderText("");
        alert.setContentText("Something went wrong");

        alert.showAndWait();
    }

    /**
     * Displays a missing Python alert dialog with options to open documentation or the Python website.
     * If the user clicks on the documentation button, it opens the documentation link in a web browser.
     * If the user clicks on the Python website button, it opens the Python website link in a web browser.
     */
    public void missingPythonAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(alertType);
        alert.setHeaderText("");
        alert.setContentText("Check the documentation and download Python!");

        ButtonType documentation = new ButtonType("Documentation");
        ButtonType python = new ButtonType("Python website");
        ButtonType cancelButton = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(documentation, python, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            ButtonType buttonClicked = result.get();
            if (buttonClicked == documentation) {
                String documentationUrl = "https://enricozano.github.io/klotski_ids/";
                openWebLink(documentationUrl);
            } else if (buttonClicked == python) {
                String pythonWebsiteUrl = "https://www.python.org/downloads/";
                openWebLink(pythonWebsiteUrl);
            }
        }
    }

    /**
     * Opens the specified URL in a web browser.
     *
     * @param url the URL to open.
     */
    private void openWebLink(String url) {
        try {
            java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

