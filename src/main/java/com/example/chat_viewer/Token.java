
package com.example.chat_viewer;


/** Token Types
 * */

enum tokenType {
    TIME_STAMP,
    CONTENT,
    SMILE,
    EMPTY_LINE,
    NICK_NAME,
}


public abstract class Token {

    private final tokenType type;
    private final String value;

    public Token(tokenType type, String value)
    {
        this.type = type;
        this.value = value;
    }

    /**
     * Type of the token
     */
    public tokenType getType() {
        return type;
    }

    /**
     * Value of the token
     */
    public String getValue() {
        return value;
    }
}
