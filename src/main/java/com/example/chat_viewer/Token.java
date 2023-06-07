
package com.example.chat_viewer;


/** Token Types*/

enum tokenType {
    TIME_STAMP,
    CONTENT,
    SMILE,
    EMPTY_LINE,
    NICK_NAME,
}

/**
 * Abstract class all token types inherit from.
 */
public abstract class Token {

    private final tokenType type;
    private final String TokenValue;

    public Token(tokenType type, String TokenValue)
    {
        this.type = type;
        this.TokenValue = TokenValue;
    }

    /**
     * Get a type of the token
     */
    tokenType getType(){
        return type;
    }

    /**
     *Gets a value of the token
     */
    public String getValue() {
        return TokenValue;
    }
}
