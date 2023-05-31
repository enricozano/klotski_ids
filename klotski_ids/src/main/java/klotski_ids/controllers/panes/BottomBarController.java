package klotski_ids.controllers.panes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import klotski_ids.mainApplication;

/**
 * Controller class for the bottom bar in the user interface.
 */
public class BottomBarController {

    @FXML
    public Button wikiButton;
    @FXML
    public Button gitHubButton;

    /**
     * Action event handler for the "Go to Wiki" button.
     * Opens the Klotski IDS wiki page in the default web browser.
     */
    public void goToWiki() {
        mainApplication.getInstance().getHostServices().showDocument("https://enricozano.github.io/klotski_ids/");
    }

    /**
     * Action event handler for the "Go to GitHub" button.
     * Opens the Klotski IDS GitHub repository page in the default web browser.
     */
    public void goToGithub() {
        mainApplication.getInstance().getHostServices().showDocument("https://github.com/enricozano/klotski_ids");
    }

}
