package com.example.chat_viewer;

/**
 * Class for the implementation of the TIME_STAMP token.
 */
public class Timestoken extends Token {

    public Timestoken(String timestamp) {
        super(tokenType.TIME_STAMP, timestamp);
    }

}