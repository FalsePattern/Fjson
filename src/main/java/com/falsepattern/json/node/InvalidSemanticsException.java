package com.falsepattern.json.node;

import com.falsepattern.json.parsing.ASTNode;

public class InvalidSemanticsException extends RuntimeException{
    public InvalidSemanticsException(String targetType, ASTNode node) {
        super("Could not parse AST node as " + targetType + ":\n" + node.toString(4));
    }
}
