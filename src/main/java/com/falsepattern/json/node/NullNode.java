package com.falsepattern.json.node;

public class NullNode extends JsonNode {
    public static final NullNode Null = new NullNode();
    private NullNode(){}
    @Override
    public boolean isNull() {
        return true;
    }

    @Override
    public String toString() {
        return "null";
    }
}
