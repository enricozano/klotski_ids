package klotski_ids.controllers.panes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import klotski_ids.mainApplication;

public class bottomBarController {
    @FXML
    private HBox myHbox;
    @FXML
    private Button gitHubButton;
    @FXML
    private Button wikiButton;

    public void goToWiki(ActionEvent actionEvent) {

        wikiButton.setOnAction(e -> mainApplication.getInstance().getHostServices().showDocument("https://github.com/enricozano/klotski_ids"));
    }

    public void goToGithub(ActionEvent actionEvent) {
        gitHubButton.setOnAction(e -> mainApplication.getInstance().getHostServices().showDocument("https://github.com/enricozano/klotski_ids"));
    }

}
