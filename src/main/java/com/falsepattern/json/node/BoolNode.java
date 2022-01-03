package com.falsepattern.json.node;

import com.falsepattern.json.parsing.ASTNode;

import java.util.Objects;

public class BoolNode extends JsonNode {
    private static final BoolNode True = new BoolNode(true);
    private static final BoolNode False = new BoolNode(false);

    private final boolean value;
    private BoolNode(boolean value) {
        this.value = value;
    }

    @Override
    public boolean isBoolean() {
        return true;
    }

    @Override
    public boolean boolValue() {
        return value;
    }

    public static BoolNode of(boolean value) {
        return value ? BoolNode.True : BoolNode.False;
    }

    public static BoolNode translate(ASTNode node) {
        if (Objects.equals(node.type, "true")) return BoolNode.True;
        if (Objects.equals(node.type, "false")) return BoolNode.False;
        throw new InvalidSemanticsException("BoolNode", node);
    }

    @Override
    public String toString() {
        return value ? "true" : "false";
    }
}
