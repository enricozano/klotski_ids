/**
 * The klotski_ids module contains classes and resources for the Klotski game.
 */
module klotski_ids {
    // Requires
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires javafx.graphics;
    requires java.desktop;

    // Opens
    opens klotski_ids.controllers.frameMenu;
    opens klotski_ids.controllers.panes;
    opens klotski_ids.models;


    // Exports
    exports klotski_ids;
    exports klotski_ids.controllers.panes;
    exports klotski_ids.models;
}