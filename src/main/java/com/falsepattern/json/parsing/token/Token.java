package com.falsepattern.json.parsing.token;

import lombok.val;

import java.util.Objects;
import java.util.regex.Pattern;

public class Token {
    public final Type tokenType;
    public final String text;
    public final int line;
    public final int col;

    public Token(Type type, String text, int row, int col) {
        this.tokenType = type;
        this.text = text;
        this.line = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        val that = (Token) obj;
        return Objects.equals(this.tokenType, that.tokenType) &&
               Objects.equals(this.text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenType, text);
    }

    public enum Type {
        LBrace("\\{"),
        RBrace("}"),
        Comma(","),
        LBracket("\\["),
        RBracket("]"),
        Colon(":"),
        True("true"),
        False("false"),
        Null("null"),
        WS("[ \\t]+"),
        Newline("(?:\\r\\n|\\r|\\n)"),
        Float("-?" + Token.INT + "(?:(?:\\.[0-9]+)|" + EXP + ")"),
        Int("-?" + Token.INT),
        String("\"(" + ESC + "|" + SAFECODEPOINT + ")*\"");
        public final Pattern regex;

        Type(String regex) {
            this.regex = Pattern.compile(regex);
        }
    }

    private static final String INT = "(?:0|[1-9][0-9]*)";
    private static final String EXP = "(?:[Ee][+\\-]?" + INT + ")";
    private static final String SAFECODEPOINT = "(?:[^\"\\u0000-\\u001F])";
    private static final String HEX = "(?:[0-9a-fA-F])";
    private static final String UNICODE = "(?:u" + HEX + HEX + HEX + HEX + ")";
    private static final String ESC = "(?:\\\\(?:[\"\\\\/bfnrt] | " + UNICODE + "))";
}
