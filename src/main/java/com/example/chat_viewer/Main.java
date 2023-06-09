package com.example.chat_viewer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.IOException;

/**
 * Launches the application
 */

public class Main extends Application {
    public static Window stage;

    /**
     * Loads FXML document, specifies application sizes
     * @param stage The primary stage for the application
     * @throws IOException if an error occurs while loading the FXML document
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Create an instance of FXMLLoader and load the FXML document
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 550);
        // Set the loaded scene to the primary stage
        stage.setScene(scene);
        // Adjust the stage size to fit the content
        stage.sizeToScene();
        stage.show();
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
        stage.setTitle("ChatViewer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();// Launch the JavaFX application
    }
}