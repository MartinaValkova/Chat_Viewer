package com.example.chat_viewer;


import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Chat_ViewerController {
    @FXML
    public TextFlow Window;
    @FXML
    public Label Path;
    @FXML
    public Button Button;
    public Exceptions tokenizer = new Exceptions();
    public LinkedList<Token> tokens;

    public void initialize() {
        tokenizer = new Exceptions();
    }

    /**
     * Method called when the Open button is clicked. Receiving and reading user input
     *
     * @param actionEvent the event triggered by the Open button
     */

    @FXML
    public void openFileChooser(ActionEvent actionEvent) {
        //Happy and sad smiles
        Image smileHappy = new Image(getClass().getResource("smile_happy.gif").toExternalForm());
        Image smileSad = new Image(getClass().getResource("smile_sad.gif").toExternalForm());

        // Chat window cleaned before opening a file
        Window.getChildren().clear();

        //Create a file chooser dialog
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(Main.stage);
        if (file == null) {
            return;
        }

        // Get the file name and extension
        String fileName = file.getName();
        String fileExtension = "";
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            fileExtension = fileName.substring(dotIndex + 1);
        }
        // Check if the file extension is valid
        if (!fileExtension.equals("msg")) {
            showErrorAlert("Wrong file type, please open .msg file ", "Wrong file type");
            return;
        }

        try {
            // Read the file and tokenize its content
            Scanner scanner = new Scanner(new FileInputStream(file));
            tokens = tokenizer.tokenize(scanner);
            scanner.close();

        } catch (FileNotFoundException e) {
            showErrorAlert(e.getMessage(), "File is not found");
            return;
        } catch (Exception e) {
            showErrorAlert(e.getMessage(), "Input is not valid");
            return;
        }
        // Process the tokens and display them in the chat window
        for (Token token : tokens) {
            if (token.getType() == tokenType.SMILE) {
                // Add a smiley image to the chat window
                ImageView smileImage = new ImageView(token.getValue().equals(":)") ? smileHappy : smileSad);
                Window.getChildren().add(smileImage);
            } else {
                // Add a text token to the chat window
                Text tempText = new Text();
                tempText.setText(token.getValue());
                setTokenStyle(tempText, token.getType());
                Window.getChildren().add(tempText);
            }
        }

        String filePath = file.getAbsolutePath();
        Path.setText(filePath);
    }


    public void setTokenStyle(Text text, tokenType type) {
        // Set the style of the text based on the token type
        switch (type) {
            case NICK_NAME:
                text.setText(text.getText() + ":");
                text.setFill(Color.BLUE);
                text.setFont(Font.font("Arial", FontWeight.BOLD, 15));
                break;
            case CONTENT:
                text.setFill(Color.BLACK);
                text.setFont(Font.font("Arial", FontWeight.BOLD, 15));
                break;
            case TIME_STAMP:
                text.setFill(Color.BLACK);
                text.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
                text.setText("[" + text.getText() + "]");
                break;
            default:
                break;
        }
    }

    /**
     * Show an error alert with the given content and header text.
     *
     * @param content    the content of the error message
     * @param headerText the header text of the error alert
     */
    public void showErrorAlert(String content, String headerText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(content);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
}





