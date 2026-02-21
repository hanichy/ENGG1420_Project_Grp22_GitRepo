module com.example.finalproject1of2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.finalproject1of2 to javafx.fxml;
    exports com.example.finalproject1of2;
}