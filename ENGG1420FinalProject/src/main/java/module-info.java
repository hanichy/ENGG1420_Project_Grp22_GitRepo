module com.example.engg1420finalproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.engg1420finalproject to javafx.fxml;
    exports com.example.engg1420finalproject;
}