package com.falsepattern.json.node;

import com.falsepattern.json.parsing.ASTNode;
import com.falsepattern.json.parsing.Parser;

import java.util.Arrays;
import java.util.stream.Collectors;

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
        switch (node.type) {
            case "obj":
                return ObjectNode.translate(node);
            case "arr":
                return ListNode.translate(node);
            case "string":
                return StringNode.translate(node);
            case "int":
                return IntNode.translate(node);
            case "float":
                return FloatNode.translate(node);
            case "null":
                return NullNode.Null;
            case "false":
            case "true":
                return BoolNode.translate(node);
            default:
                throw new UnsupportedOperationException("Could not translate AST to json:\n" + node.toString(4));
        }
    }

    public static JsonNode parse(String text) {
        return translate(new Parser(text).value());
    }

    public abstract String toString();

    public abstract String prettyPrint(int indentDepth);

    protected String indent(String stuff, int depth) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            res.append(' ');
        }
        String indent = res.toString();
        return Arrays.stream(stuff.split("\n")).map((line) -> indent + line).collect(Collectors.joining("\n"));
    }
}
