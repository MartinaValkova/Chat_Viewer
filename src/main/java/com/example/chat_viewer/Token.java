
package com.example.chat_viewer;


/**
 * Token Types
 * Enumerates the different types of tokens used in the chat viewer.
 */

enum tokenType {
    TIME_STAMP,
    CONTENT,
    SMILE,
    EMPTY_LINE,
    NICK_NAME,
}
/**
 * Token
 * An abstract class representing a token in the chat viewer.
 */

public abstract class Token {

    private final tokenType type;
    private final String value;

    /**
     * Constructs a new Token with the given type and value.
     *
     * @param type  The type of the token.
     * @param value The value of the token.
     */

    public Token(tokenType type, String value)
    {
        this.type = type;
        this.value = value;
    }

    /**
     * Retrieves the type of the token.
     *
     * @return The type of the token.
     */
    public tokenType getType() {
        return type;
    }

    /**
     * Retrieves the value of the token.
     *
     * @return The value of the token.
     */
    public String getValue() {
        return value;
    }
}
