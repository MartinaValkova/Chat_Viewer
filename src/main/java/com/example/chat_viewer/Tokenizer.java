package com.example.chat_viewer;
import java.util.LinkedList;
import java.util.Scanner;


/** Tokenizer implementation
 */
public class Tokenizer  {
    private int state;
    private String line;
    private int lineNumber;
    private String currentTokenType;
    private String currentTimestamp;
    private String currentMessage;
    private String currentNickname;
    private String previousNickname;
    private int tokenTypeEnds;
    private LinkedList<Token> chatTokens;
    /***
     * Analyzing content of the conversation ("msg") file and returning a corresponding list of tokens.
     */
    public LinkedList<Token> tokenize(Scanner input) throws TokenizerException {
        initializeVariables();

        while (input.hasNextLine()) {
            processLine(input.nextLine());
        }

        validateChatTokens();

        return chatTokens;
    }

    private void initializeVariables() {
        state = 0;
        lineNumber = 0;
        currentTokenType = "";
        currentTimestamp = "";
        currentMessage = "";
        currentNickname = "";
        previousNickname = "";
        chatTokens = new LinkedList<>();
    }

    private void processLine(String currentLine) throws TokenizerException {
        line = currentLine;
        lineNumber++;

        switch (state) {
            case 0:
                handleState0();
                break;
            case 1:
                handleState1();
                break;
            case 2:
                handleState2();
                break;
            case 3:
                handleState3();
                break;
        }
    }

    private void handleState0() throws TokenizerException {
        tokenTypeEnds = line.indexOf(":");
        currentTokenType = extractSubstring(line, tokenTypeEnds, "Invalid Time input" + lineNumber);

        currentTimestamp = line.substring(tokenTypeEnds + 1);
        if (isValidTime(currentTokenType, currentTimestamp)) {
            chatTokens.add(new Timestoken(currentTimestamp));
            state = 1;
        } else {
            throw new TokenizerException("Invalid Time input" + lineNumber);
        }
    }

    private void handleState1() throws TokenizerException {
        tokenTypeEnds = line.indexOf(":");
        currentTokenType = extractSubstring(line, tokenTypeEnds, "Invalid Name input" + lineNumber);

        currentNickname = line.substring(tokenTypeEnds + 1);
        if (isValidName(currentTokenType, currentNickname)) {
            if (currentNickname.equals(previousNickname)) {
                previousNickname = currentNickname;
                currentNickname = " . . .   ";
            } else {
                previousNickname = currentNickname;
            }
            chatTokens.add(new Nicknametoken(currentNickname));
            state = 2;
        } else {
            throw new TokenizerException("Invalid Name input" + lineNumber);
        }
    }

    private void handleState2() throws TokenizerException {
        tokenTypeEnds = line.indexOf(":");
        currentTokenType = extractSubstring(line, tokenTypeEnds, "Invalid Message" + lineNumber);

        if (currentTokenType.equals("Message")) {
            currentMessage = line.substring(tokenTypeEnds + 1);
            chatTokens.addAll(tokenizeMessage(currentMessage));
            state = 3;
        } else {
            throw new TokenizerException("Invalid Message input" + lineNumber);
        }
    }

    private void handleState3() throws TokenizerException {
        if (line.isEmpty()) {
            chatTokens.add(new Blanklinetoken());
            state = 0;
        } else {
            throw new TokenizerException("Missing empty lines between messages" + lineNumber);
        }
    }

    private String extractSubstring(String line, int endIndex, String errorMessage) throws TokenizerException {
        try {
            return line.substring(0, endIndex);
        } catch (Exception e) {
            throw new TokenizerException(errorMessage);
        }
    }

    private boolean isValidTime(String tokenType, String timestamp) {
        return tokenType.equals("Time") && !timestamp.isBlank();
    }

    private boolean isValidName(String tokenType, String nickname) {
        return tokenType.equals("Name") && !nickname.isBlank();
    }

    private LinkedList<Token> tokenizeMessage(String message) throws TokenizerException {
        int inputPosition = 0;
        LinkedList<Token> messageTokens = new LinkedList<>();
        String currentString = "";

        if (message.isEmpty()) {
            messageTokens.add(new Insidetoken(""));
        } else {
            while (true) {
                try {
                    Character character = message.charAt(inputPosition);
                    inputPosition++;

                    if (character.equals(':') && message.charAt(inputPosition) == ')') {
                        if (!currentString.isEmpty()) {
                            messageTokens.add(new Insidetoken(currentString));
                            currentString = "";
                        }
                        messageTokens.add(new Smilestoken(":)"));
                        inputPosition += 1;
                    } else if (character.equals(':') && message.charAt(inputPosition) == '(') {
                        if (!currentString.isEmpty()) {
                            messageTokens.add(new Insidetoken(currentString));
                            currentString = "";
                        }
                        messageTokens.add(new Smilestoken(":("));
                        inputPosition += 1;
                    } else {
                        currentString += character;
                    }
                } catch (IndexOutOfBoundsException e) {
                    if (!currentString.isEmpty()) {
                        messageTokens.add(new Insidetoken(currentString));
                    }
                    break;
                }
            }
        }

        return messageTokens;
    }

    private void validateChatTokens() throws TokenizerException {
        System.out.println(chatTokens);
        if (chatTokens.getLast().getType() == tokenType.BLANK_LINE) {
            throw new TokenizerException("Blank line at the end of the input");
        }
    }
}
