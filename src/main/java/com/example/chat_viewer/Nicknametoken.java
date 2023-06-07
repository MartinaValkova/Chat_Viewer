package com.example.chat_viewer;


/**
 * Class for the implementation of the NICK_NAME token type.
 */
public class Nicknametoken extends Token {

    public Nicknametoken(String nickname) {
        super(tokenType.NICK_NAME, nickname);
    }

}
