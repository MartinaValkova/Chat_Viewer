package com.example.chat_viewer;


/**
 * Exception thrown by the Tokenizer interface.
 */

public class TokenizerException extends Throwable {
    public TokenizerException(String errorMessage) {
        super(errorMessage);
    }
}
