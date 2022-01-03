package com.falsepattern.json.node;

import com.falsepattern.json.parsing.ASTNode;

public abstract class JsonNode {
    public JsonNode get(String key) {
        throw new UnsupportedOperationException();
    }

    public JsonNode get(int index) {
        throw new UnsupportedOperationException();
    }

    public JsonNode set(String key, JsonNode value) {
        throw new UnsupportedOperationException();
    }

    public JsonNode set(String key, int value) {
        return set(key, new IntNode(value));
    }

    public JsonNode set(String key, float value) {
        return set(key, new FloatNode(value));
    }

    public JsonNode set(String key, boolean value) {
        return set(key, BoolNode.of(value));
    }

    public JsonNode set(int index, JsonNode value) {
        throw new UnsupportedOperationException();
    }

    public boolean containsKey(String key) {
        throw new UnsupportedOperationException();
    }

    public int size() {
        throw new UnsupportedOperationException();
    }

    public void add(JsonNode value) {
        throw new UnsupportedOperationException();
    }

    public JsonNode remove(String key) {
        throw new UnsupportedOperationException();
    }

    public JsonNode remove(int key) {
        throw new UnsupportedOperationException();
    }

    public boolean isObject() {
        return false;
    }

    public boolean isList() {
        return false;
    }

    public boolean isString() {
        return false;
    }

    public boolean isInt() {
        return false;
    }

    public boolean isFloat() {
        return false;
    }

    public boolean isBoolean() {
        return false;
    }

    public boolean isNull() {
        return false;
    }

    public ObjectNode asObjectNode() {
        return (ObjectNode) this;
    }

    public ListNode asListNode() {
        return (ListNode) this;
    }

    public String stringValue() {
        throw new UnsupportedOperationException();
    }

    public int intValue() {
        throw new UnsupportedOperationException();
    }

    public float floatValue() {
        throw new UnsupportedOperationException();
    }

    public boolean boolValue() {
        throw new UnsupportedOperationException();
    }

    public static JsonNode translate(ASTNode node) {
        return switch (node.type) {
            case "obj" -> ObjectNode.translate(node);
            case "arr" -> ListNode.translate(node);
            case "string" -> StringNode.translate(node);
            case "int" -> IntNode.translate(node);
            case "float" -> FloatNode.translate(node);
            case "null" -> NullNode.Null;
            case "false", "true" -> BoolNode.translate(node);
            default -> throw new UnsupportedOperationException("Could not translate AST to json:\n" + node.toString(4));
        };
    }
}
