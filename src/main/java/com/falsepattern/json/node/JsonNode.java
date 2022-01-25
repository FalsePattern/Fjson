package com.falsepattern.json.node;

import com.falsepattern.json.parsing.ASTNode;
import com.falsepattern.json.parsing.Parser;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class JsonNode {
    public JsonNode get(String key) {
        throw new UnsupportedOperationException(expect("get(String)", this.getClass(), ObjectNode.class));
    }

    public int getInt(String key) {
        return get(key).intValue();
    }

    public float getFloat(String key) {
        return get(key).floatValue();
    }

    public boolean getBool(String key) {
        return get(key).boolValue();
    }

    public String getString(String key) {
        return get(key).stringValue();
    }

    public JsonNode getOrDefault(String key, JsonNode defaultValue) {
        return containsKey(key) ? get(key) : defaultValue;
    }

    public int getIntOrDefault(String key, int defaultValue) {
        return containsKey(key) ? getInt(key) : defaultValue;
    }

    public float getFloatOrDefault(String key, float defaultValue) {
        return containsKey(key) ? getFloat(key) : defaultValue;
    }

    public boolean getBoolOrDefault(String key, boolean defaultValue) {
        return containsKey(key) ? getBool(key) : defaultValue;
    }

    public String getStringOrDefault(String key, String defaultValue) {
        return containsKey(key) ? getString(key) : defaultValue;
    }

    public JsonNode get(int index) {
        throw new UnsupportedOperationException(expect("get(int)", this.getClass(), ListNode.class));
    }

    public int getInt(int index) {
        return get(index).intValue();
    }

    public float getFloat(int index) {
        return get(index).floatValue();
    }

    public boolean getBool(int index) {
        return get(index).boolValue();
    }

    public String getString(int index) {
        return get(index).toString();
    }

    public JsonNode getOrDefault(int index, JsonNode defaultValue) {
        return containsIndex(index) ? get(index) : defaultValue;
    }

    public int getIntOrDefault(int index, int defaultValue) {
        return containsIndex(index) ? getInt(index) : defaultValue;
    }

    public float getFloatOrDefault(int index, float defaultValue) {
        return containsIndex(index) ? getFloat(index) : defaultValue;
    }

    public boolean getBoolOrDefault(int index, boolean defaultValue) {
        return containsIndex(index) ? getBool(index) : defaultValue;
    }

    public String getStringOrDefault(int index, String defaultValue) {
        return containsIndex(index) ? getString(index) : defaultValue;
    }

    public JsonNode set(String key, JsonNode value) {
        throw new UnsupportedOperationException(expect("set(String, JsonNode)", this.getClass(), ObjectNode.class));
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

    public JsonNode set(String key, String value) {
        return set(key, new StringNode(value));
    }

    public JsonNode set(int index, JsonNode value) {
        throw new UnsupportedOperationException(expect("set(int, JsonNode)", this.getClass(), ListNode.class));
    }

    public JsonNode set(int index, int value) {
        return set(index, new IntNode(value));
    }

    public JsonNode set(int index, float value) {
        return set(index, new FloatNode(value));
    }

    public JsonNode set(int index, boolean value) {
        return set(index, BoolNode.of(value));
    }

    public JsonNode set(int index, String value) {
        return set(index, new StringNode(value));
    }

    public void add(JsonNode value) {
        throw new UnsupportedOperationException(expect("add(JsonNode)", this.getClass(), ListNode.class));
    }

    public boolean containsKey(String key) {
        throw new UnsupportedOperationException(expect("containsKey(String)", this.getClass(), ObjectNode.class));
    }

    public boolean containsIndex(int index) {
        throw new UnsupportedOperationException(expect("containsIndex(int)", this.getClass(), ListNode.class));
    }

    public int size() {
        throw new UnsupportedOperationException(expect("size()", this.getClass(), ListNode.class, ObjectNode.class));
    }

    public JsonNode remove(String key) {
        throw new UnsupportedOperationException(expect("remove(String)", this.getClass(), ObjectNode.class));
    }

    public JsonNode remove(int key) {
        throw new UnsupportedOperationException(expect("remove(int)", this.getClass(), ListNode.class));
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
        throw new UnsupportedOperationException(expect("stringValue()", this.getClass(), StringNode.class));
    }

    public int intValue() {
        throw new UnsupportedOperationException(expect("intValue()", this.getClass(), IntNode.class));
    }

    public float floatValue() {
        throw new UnsupportedOperationException(expect("floatValue()", this.getClass(), FloatNode.class));
    }

    public boolean boolValue() {
        throw new UnsupportedOperationException(expect("boolValue()", this.getClass(), BoolNode.class));
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

    private static String expect(String method, Class<? extends JsonNode> thisType, Class<?>... requiredTypes) {
        return "Cannot call " + method + " on " + thisType.getSimpleName() + ". Must be " + Arrays.stream(requiredTypes).map(Class::getSimpleName).collect(Collectors.joining(" or ")) + ".";
    }
}
