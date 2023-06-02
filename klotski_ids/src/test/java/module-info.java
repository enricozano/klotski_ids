module klotski.ids {
    requires org.junit.platform.commons;
    requires org.junit.jupiter.api;
    requires klotski_ids;
    requires com.google.gson;
    requires javafx.graphics;

    opens klotski_ids_test.controllers.frameMenu;
    opens klotski_ids_test.models;
}