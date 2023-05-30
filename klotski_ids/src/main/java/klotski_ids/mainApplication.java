package klotski_ids;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class mainApplication extends Application {
    @Override
    public void start(Stage stage) {

        mInstance = this;
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainView.fxml")));
            Scene scene = new Scene(root);
            stage.setTitle("Klotski");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static Application mInstance;

    public static Application getInstance() {
        return mInstance;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
