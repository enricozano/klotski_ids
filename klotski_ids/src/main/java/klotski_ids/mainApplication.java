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
    public void start(Stage stage){
        mInstance = this;
        try{
            Parent root =FXMLLoader.load(getClass().getResource("mainView.fxml"));
            scene = new Scene(root);
            stage.setTitle("Klotski");
            String css = this.getClass().getResource("assets/styles/mainViewStyle.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /*private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(mainApplication.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }*/

    private static Application mInstance;
    public static Application getInstance() {
        return mInstance;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
