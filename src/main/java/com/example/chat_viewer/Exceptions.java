package com.example.chat_viewer;


import java.util.LinkedList;
import java.util.Scanner;



public class Exceptions  {
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

    /**
     * Examining the content of the conversation ("msg") file
     *
     * @param input The scanner object to read the conversation content from.
     * @return A list of chat tokens.
     * @throws Exception If any errors occur
     */

    public LinkedList<Token> tokenize(Scanner input) throws Exception {
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

    /**
     * Processes a single line of the conversation content.
     *
     * @param currentLine The current line to process.
     * @throws Exception If any errors occur
     */
    private void processLine(String currentLine) throws Exception {
        line = currentLine;
        lineNumber++;
// Switch statement to handle different states
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

    private void handleState0() throws Exception {
        //Find the index of the colon ":" in the line
        tokenTypeEnds = line.indexOf(":");
        // Extract the substring before the colon, which represents the token type
        currentTokenType = extractSubstring(line, tokenTypeEnds, "Invalid Time input" + lineNumber);

        // Extract the substring after the colon, which represents the current timestamp
        currentTimestamp = line.substring(tokenTypeEnds + 1);
        if (isValidTime(currentTokenType, currentTimestamp)) {
            // Add a new TimeStamp token to the chatTokens list
            chatTokens.add(new TimeStamp(currentTimestamp));
            state = 1;// Move to the next state
        } else {
            throw new Exception("Invalid Time input" + lineNumber);
        }
    }

    private void handleState1() throws Exception {
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
            chatTokens.add(new Nickname(currentNickname));
            state = 2;
        } else {
            throw new Exception("Invalid Name input" + lineNumber);
        }
    }

    private void handleState2() throws Exception {
        tokenTypeEnds = line.indexOf(":");
        currentTokenType = extractSubstring(line, tokenTypeEnds, "Invalid Message" + lineNumber);

        if (currentTokenType.equals("Message")) {
            currentMessage = line.substring(tokenTypeEnds + 1);
            chatTokens.addAll(tokenizeMessage(currentMessage));
            state = 3;
        } else {
            throw new Exception("Invalid Message input" + lineNumber);
        }
    }

    private void handleState3() throws Exception {
        if (line.isEmpty()) {
            chatTokens.add(new Emptyline());
            state = 0;
        } else {
            throw new Exception("Missing empty lines between messages" + lineNumber);
        }
    }

    private String extractSubstring(String line, int endIndex, String errorMessage) throws Exception {
        try {
            return line.substring(0, endIndex);
        } catch (Exception e) {
            throw new Exception(errorMessage);
        }
    }

    private boolean isValidTime(String tokenType, String timestamp) {
        return tokenType.equals("Time") && !timestamp.isBlank();
    }

    private boolean isValidName(String tokenType, String nickname) {
        return tokenType.equals("Name") && !nickname.isBlank();
    }

    private LinkedList<Token> tokenizeMessage(String message) throws Exception {
        int inputPosition = 0;
        LinkedList<Token> messageTokens = new LinkedList<>();
        String currentString = "";

        if (message.isEmpty()) {
            messageTokens.add(new Content(""));
        } else {
            while (true) {
                try {
                    Character character = message.charAt(inputPosition);
                    inputPosition++;

                    if (character.equals(':') && message.charAt(inputPosition) == ')') {
                        if (!currentString.isEmpty()) {
                            messageTokens.add(new Content(currentString));
                            currentString = "";
                        }
                        messageTokens.add(new Smile(":)"));
                        inputPosition += 1;
                    } else if (character.equals(':') && message.charAt(inputPosition) == '(') {
                        if (!currentString.isEmpty()) {
                            messageTokens.add(new Content(currentString));
                            currentString = "";
                        }
                        messageTokens.add(new Smile(":("));
                        inputPosition += 1;
                    } else {
                        currentString += character;
                    }
                } catch (IndexOutOfBoundsException e) {
                    if (!currentString.isEmpty()) {
                        messageTokens.add(new Content(currentString));
                    }
                    break;
                }
            }
        }

        return messageTokens;
    }

    private void validateChatTokens() throws Exception {
        System.out.println(chatTokens);
        if (chatTokens.getLast().getType() == tokenType.EMPTY_LINE) {
            throw new Exception("Empty line at the end of the input");
        }
    }
}
