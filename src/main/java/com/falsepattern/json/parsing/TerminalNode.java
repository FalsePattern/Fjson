package com.falsepattern.json.parsing;

import com.falsepattern.json.parsing.token.Token;

public class TerminalNode extends ASTNode {
    public final String text;

    public TerminalNode(String type, String text) {
        super(type);
        this.text = text;
    }

    public TerminalNode(Token token) {
        super(token.tokenType.name().toLowerCase());
        this.text = token.text;
    }

    @Override
    public void addChild(ASTNode node) {
        throw new UnsupportedOperationException("Cannot add a child node to a terminal!");
    }

    @Override
    public String toString(int indent) {
        return (type + ": " + text).indent(indent);
    }
}
