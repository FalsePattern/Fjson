package com.falsepattern.json.node;

import com.falsepattern.json.parsing.ASTNode;
import com.falsepattern.json.parsing.TerminalNode;

import java.util.Objects;

public class IntNode extends JsonNode {
    private final int value;
    public IntNode(int value) {
        this.value = value;
    }

    @Override
    public boolean isInt() {
        return true;
    }

    @Override
    public int intValue() {
        return value;
    }


    public static IntNode translate(ASTNode node) {
        if (!Objects.equals(node.type, "int")) throw new InvalidSemanticsException("IntNode", node);
        return new IntNode(Integer.parseInt(((TerminalNode)node).text));
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
