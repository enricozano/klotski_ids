package klotski_ids.controllers.panes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import klotski_ids.mainApplication;

public class BottomBarController {

    @FXML
    public Button wikiButton;
    @FXML
    public Button gitHubButton;

    
    public void goToWiki(ActionEvent actionEvent) {
        mainApplication.getInstance().getHostServices().showDocument("https://enricozano.github.io/klotski_ids/");
    }

    public void goToGithub(ActionEvent actionEvent) {
        mainApplication.getInstance().getHostServices().showDocument("https://github.com/enricozano/klotski_ids");
    }

}
