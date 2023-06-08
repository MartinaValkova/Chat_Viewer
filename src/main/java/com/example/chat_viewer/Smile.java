package com.example.chat_viewer;


import javafx.scene.image.Image;

/**
 * Class for the implementation of the SMILE token.
 */
public class Smile extends Token {
    //Smiles
    Image smileHappy = new Image(getClass().getResource("smile_happy.gif").toExternalForm());
    Image smileSad = new Image(getClass().getResource("smile_sad.gif").toExternalForm());
    private final String value;

    public Smile(String value) {
        super(tokenType.SMILE, value);
        this.value = value;
    }
}
