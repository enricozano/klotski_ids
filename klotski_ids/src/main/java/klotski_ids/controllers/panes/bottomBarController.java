package klotski_ids.controllers.panes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import klotski_ids.mainApplication;

public class bottomBarController {

    public void goToWiki(ActionEvent actionEvent) {
        mainApplication.getInstance().getHostServices().showDocument("https://github.com/enricozano/klotski_ids");
    }

    public void goToGithub(ActionEvent actionEvent) {
        mainApplication.getInstance().getHostServices().showDocument("https://github.com/enricozano/klotski_ids");
    }

}
