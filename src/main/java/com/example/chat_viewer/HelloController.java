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



public class HelloController {
    @FXML
    public TextFlow Window;
    @FXML
    public Label Path;
    @FXML
    public Button Button;
    public Tokenizer tokenizer = new Tokenizer();
    public LinkedList<Token> tokens;

    public void initialize() {
        tokenizer = new Tokenizer();
    }


    /**actionEvent event is specified*/
    /**
     * Method called Open button is clicked. Receiving and reading user input.
     */
    @FXML
    public void onOpenButtonClick(ActionEvent actionEvent) {
        Image smileHappy = new Image(getClass().getResource("smile_happy copy.gif").toExternalForm());
        Image smileSad = new Image(getClass().getResource("smile_sad copy.gif").toExternalForm());

        Window.getChildren().clear();

        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(HelloApplication.stage);
        if (file == null) {
            return;
        }

        String fileName = file.getName();
        String fileExtension = "";
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            fileExtension = fileName.substring(dotIndex + 1);
        }

        if (!fileExtension.equals("msg")) {
            showErrorAlert("Wrong file type", "Wrong file type");
            return;
        }

        try {
            Scanner scanner = new Scanner(new FileInputStream(file));
            tokens = tokenizer.tokenize(scanner);
            scanner.close();

        } catch (FileNotFoundException e) {
            showErrorAlert(e.getMessage(), "File is not found");
            return;
        } catch (TokenizerException e) {
            showErrorAlert(e.getMessage(), "Input is not valid");
            return;
        }

        for (Token token : tokens) {
            if (token.getType() == tokenType.SMILE) {
                ImageView smileImage = new ImageView(token.getValue().equals(":)") ? smileHappy : smileSad);
                Window.getChildren().add(smileImage);
            } else {
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
        switch (type) {
            case NICK_NAME:
                text.setText(text.getText() + ":");
                text.setFill(Color.RED);
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

    public void showErrorAlert(String content, String headerText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(content);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
}


