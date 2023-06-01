package klotski_ids;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * The main application class that extends the JavaFX Application class.
 */
public class mainApplication extends Application {

    /**
     * The instance of the main application.
     */
    private static Application mInstance;

    /**
     * Starts the JavaFX application.
     *
     * @param stage the primary stage for the application.
     */
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

    /**
     * Returns the instance of the main application.
     *
     * @return the instance of the main application.
     */
    public static Application getInstance() {
        return mInstance;
    }

    /**
     * The main method that launches the JavaFX application.
     *
     * @param args the command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }



}

