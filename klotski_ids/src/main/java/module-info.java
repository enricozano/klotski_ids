 module klotski_ids {
         // Requires
         requires javafx.controls;
         requires javafx.fxml;

     // Opens
         opens klotski_ids.controllers to javafx.fxml;

         // Exports
         exports klotski_ids;
}