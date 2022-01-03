package com.falsepattern.json.parsing;

import com.falsepattern.json.parsing.token.Token;

public class InvalidSyntaxException extends RuntimeException {
    public InvalidSyntaxException(String expected, Token token) {
        super("Invalid syntax at line: " + token.line + ", column: " + token.col + ".\n" +
              "Expected: " + expected + "\n" +
              "Got:      '" + token.text + "'");
    }
}
