package com.example.chat_viewer;


/**
 * Class for the implementation of the BLANK_LINE token type.
 */
public class Emptylinetoken extends Token {

    public Emptylinetoken() {
        super(tokenType.EMPTY_LINE, "\n\n");
    }

}