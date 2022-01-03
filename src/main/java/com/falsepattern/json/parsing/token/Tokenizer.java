package com.falsepattern.json.parsing.token;

import lombok.val;

public class Tokenizer {
    private String string;
    private Token nextToken = null;
    public Tokenizer(String string) {
        this.string = string;
    }

    public boolean hasNextToken() {
        if (nextToken == null) {
            parseNextToken();
        }
        return nextToken != null;
    }

    public Token popNextToken() {
        if (nextToken == null) {
            parseNextToken();
        }
        val ret = nextToken;
        nextToken = null;
        return ret;
    }

    public Token peekNextToken() {
        if (nextToken == null) {
            parseNextToken();
        }
        return nextToken;
    }

    private int col = 0;
    private int row = 0;

    private void parseNextToken() {
        val types = Token.Type.values();
        outer:
        while (string.length() > 0) {
            for (val type : types) {
                val matcher = type.regex.matcher(string);
                if (matcher.lookingAt()) {
                    string = string.substring(matcher.end());
                    if (type == Token.Type.WS) {
                        col += matcher.end();
                        continue outer;
                    }
                    if (type == Token.Type.Newline) {
                        col = 0;
                        row++;
                        continue outer;
                    }
                    nextToken = new Token(type, matcher.group(), row, col);
                    col += matcher.end();
                    return;
                }
            }
            throw new IllegalArgumentException("Failed to parse JSON! Remaining: " + string);
        }
    }
}
