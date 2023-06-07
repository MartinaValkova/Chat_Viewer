package com.example.chat_viewer;


/**
 * Class for the implementation of the BLANK_LINE token type.
 */
public class Blanklinetoken extends Token {

    public Blanklinetoken() {
        super(tokenType.BLANK_LINE, "\n\n");
    }

}