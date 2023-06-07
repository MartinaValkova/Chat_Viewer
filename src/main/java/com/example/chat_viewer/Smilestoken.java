package com.example.chat_viewer;


/**
 * Class for the implementation of the SMILE token.
 */
public class Smilestoken extends Token {
    private final String value;

    public Smilestoken(String value) {
        super(tokenType.SMILE, value);
        this.value = value;
    }
}
