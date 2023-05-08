module klotski_ids {
    // Requires
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;

    // Opens
    opens klotski_ids.controllers to javafx.fxml;
    opens klotski_ids.controllers.frameMenu to javafx.fxml;
    opens klotski_ids.controllers.panes to javafx.fxml, com.fasterxml.jackson.databind;
    opens klotski_ids.controllers.util to com.fasterxml.jackson.databind, javafx.fxml;

    // Exports
    exports klotski_ids;
    exports klotski_ids.controllers.panes to com.fasterxml.jackson.databind;
    exports klotski_ids.controllers.util to com.fasterxml.jackson.databind;


}