 module klotski_ids {
         // Requires
         requires javafx.controls;
         requires javafx.fxml;


     // Opens
         opens klotski_ids.controllers to javafx.fxml;
         opens klotski_ids.controllers.frameMenu to javafx.fxml;
         opens klotski_ids.controllers.panes to javafx.fxml;

         // Exports
         exports klotski_ids;

        }