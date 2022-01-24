package com.falsepattern.json.node;

import com.falsepattern.json.parsing.ASTNode;
import com.falsepattern.json.parsing.TerminalNode;

import java.util.Objects;

public class FloatNode extends JsonNode {
    private final float value;
    public FloatNode(float value) {
        this.value = value;
    }
    @Override
    public boolean isFloat() {
        return true;
    }

    @Override
    public float floatValue() {
        return value;
    }

    @Override
    public int intValue() {
        return (int) value;
    }

    public static FloatNode translate(ASTNode node) {
        if (!Objects.equals(node.type, "float")) throw new InvalidSemanticsException("FloatNode", node);
        return new FloatNode(Float.parseFloat(((TerminalNode)node).text));
    }

    @Override
    public String toString() {
        return Float.toString(value).replace("NaN", "null").replace("-Infinity", "null").replace("Infinity", "null");
    }

    @Override
    public String prettyPrint(int indentDepth) {
        return toString();
    }
}
