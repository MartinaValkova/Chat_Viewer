package com.example.chat_viewer;

/**
 * Class for the implementation of the NICK_NAME token type.
 */
public class Nickname extends Token {

    public Nickname(String nickname) {
        super(tokenType.NICK_NAME, nickname);
    }

}
