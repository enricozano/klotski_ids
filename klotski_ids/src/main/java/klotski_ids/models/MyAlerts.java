package klotski_ids.models;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * This class provides utility methods for displaying custom alert dialogs.
 */
public class MyAlerts {
    /**
     * The type of alert to be displayed.
     */
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
        Alert alert = new Alert(Alert.AlertType.ERROR);
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
        Alert alert = new Alert(Alert.AlertType.WARNING);
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

    /**
     * Displays an alert to notify the user that they have completed the level.
     * The alert includes a message indicating the completion of the level and
     * informs the user that they will be taken back to the main screen.
     */
    public void winAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(alertType);
        alert.setHeaderText("");
        alert.setContentText("\t\t\t\tGood job! \n\t\t\t\tlevel complete :)\n\nStart a new game!");

        ButtonType confirmButton = new ButtonType("Confirm");

        alert.getButtonTypes().setAll(confirmButton);

        alert.showAndWait();

    }


}

