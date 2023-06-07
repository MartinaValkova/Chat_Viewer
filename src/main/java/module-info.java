module com.example.chat_viewer {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.chat_viewer to javafx.fxml;
    exports com.example.chat_viewer;
}