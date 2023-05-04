package klotski_ids;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
//import trustedlist.views.panes.ErrorPane;

public class mainApplication extends Application {
    private static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        mInstance = this;
        scene = new Scene(loadFXML("mainView"), 700, 800);
        stage.setTitle("Klotski");
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(mainApplication.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private static Application mInstance;
    public static Application getInstance() {
        return mInstance;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
