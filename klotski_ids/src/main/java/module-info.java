/**
 * The klotski_ids module contains classes and resources for the Klotski game.
 */
module klotski_ids {
    // Requires
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires javafx.graphics;
    requires java.desktop;

    // Opens
    opens klotski_ids.controllers.frameMenu to javafx.fxml;
    opens klotski_ids.controllers.panes to javafx.fxml,com.google.gson;
    opens klotski_ids.models to com.google.gson, javafx.fxml;


    // Exports
    exports klotski_ids;
    exports klotski_ids.controllers.panes;
    exports klotski_ids.models;
}