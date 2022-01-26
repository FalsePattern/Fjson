package com.falsepattern.json.node;

import com.falsepattern.json.node.interfaces.IListNode;
import com.falsepattern.json.node.interfaces.INode;
import com.falsepattern.json.node.interfaces.IObjectNode;
import com.falsepattern.json.node.interfaces.ISizedNode;
import com.falsepattern.json.parsing.ASTNode;
import com.falsepattern.json.parsing.Parser;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class JsonNode implements INode, ISizedNode, IObjectNode, IListNode {

    @Contract(pure = true)
    @Override
    public abstract boolean equals(@NotNull JsonNode other);

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof JsonNode) {
            return equals((JsonNode) obj);
        }
        return false;
    }

    @Contract(pure = true)
    @Override
    public abstract @NotNull String toString();


    @Contract(pure = true)
    public abstract @NotNull JsonNode clone();

    @Contract(pure = true)
    @Override
    public @NotNull String prettyPrint(int indentDepth) {
        return toString();
    }

    @Contract(pure = true)
    @Override
    public @NotNull JsonNode get(@NotNull String key) {
        throw new UnsupportedOperationException(expect("get(String)", this.getClass(), ObjectNode.class));
    }

    @Contract(pure = true)
    @Override
    public @NotNull @UnmodifiableView Map<@NotNull String, @NotNull JsonNode> getJavaMap() {
        throw new UnsupportedOperationException(expect("asMap()", this.getClass(), ObjectNode.class));
    }

    @Contract(pure = true)
    @Override
    public boolean containsKey(@NotNull String key) {
        throw new UnsupportedOperationException(expect("containsKey(String)", this.getClass(), ObjectNode.class));
    }

    @Override
    public void set(@NotNull String key, @NotNull JsonNode value) {
        throw new UnsupportedOperationException(expect("set(String, JsonNode)", this.getClass(), ObjectNode.class));
    }

    @Override
    public @NotNull JsonNode remove(@NotNull String key) {
        throw new UnsupportedOperationException(expect("remove(String)", this.getClass(), ObjectNode.class));
    }

    @Override
    public void setSortingRule(@NotNull @NonNull Comparator<@NotNull String> rule) {
        throw new UnsupportedOperationException(expect("setSortingRule(Comparator<String>)", this.getClass(), ObjectNode.class));
    }

    @Contract(pure = true)
    @Override
    public @NotNull JsonNode get(int index) {
        throw new UnsupportedOperationException(expect("get(int)", this.getClass(), ListNode.class));
    }

    @Contract(pure = true)
    @Override
    public @NotNull @UnmodifiableView List<@NotNull JsonNode> getJavaList() {
        throw new UnsupportedOperationException(expect("asList()", this.getClass(), ListNode.class));
    }

    @Contract(pure = true)
    @Override
    public boolean containsIndex(int index) {
        throw new UnsupportedOperationException(expect("containsIndex(int)", this.getClass(), ListNode.class));
    }

    @Override
    public void set(int index, @NotNull JsonNode value) {
        throw new UnsupportedOperationException(expect("set(int, JsonNode)", this.getClass(), ListNode.class));
    }

    @Override
    public void add(@NotNull JsonNode value) {
        throw new UnsupportedOperationException(expect("add(JsonNode)", this.getClass(), ListNode.class));
    }

    @NotNull
    @Override
    public JsonNode remove(int key) {
        throw new UnsupportedOperationException(expect("remove(int)", this.getClass(), ListNode.class));
    }

    @Contract(pure = true)
    @Override
    public int size() {
        throw new UnsupportedOperationException(expect("size()", this.getClass(), ListNode.class, ObjectNode.class));
    }

    @Contract(pure = true)
    @Override
    public boolean isObject() {
        return false;
    }

    @Contract(pure = true)
    @Override
    public boolean isList() {
        return false;
    }

    @Contract(pure = true)
    @Override
    public boolean isString() {
        return false;
    }

    @Contract(pure = true)
    @Override
    public boolean isInt() {
        return false;
    }

    @Contract(pure = true)
    @Override
    public boolean isFloat() {
        return false;
    }

    @Contract(pure = true)
    @Override
    public boolean isNumber() {
        return isInt() || isFloat();
    }

    @Contract(pure = true)
    @Override
    public boolean isBoolean() {
        return false;
    }

    @Contract(pure = true)
    @Override
    public boolean isNull() {
        return false;
    }

    @Contract(value = "-> this", pure = true)
    @Override
    public @NotNull ObjectNode asObjectNode() {
        throw new UnsupportedOperationException(expect("asObjectNode()", this.getClass(), ObjectNode.class));
    }

    @Contract(value = "-> this", pure = true)
    @Override
    public @NotNull ListNode asListNode() {
        throw new UnsupportedOperationException(expect("asListNode()", this.getClass(), ListNode.class));
    }

    @Contract(pure = true)
    @Override
    public @NotNull String stringValue() {
        throw new UnsupportedOperationException(expect("stringValue()", this.getClass(), StringNode.class));
    }

    @Contract(pure = true)
    @Override
    public @NotNull BigInteger bigIntValue() {
        throw new UnsupportedOperationException(expect("bigIntValue()", this.getClass(), IntNode.class, FloatNode.class));
    }

    @Contract(pure = true)
    @Override
    public @NotNull BigDecimal bigDecimalValue() {
        throw new UnsupportedOperationException(expect("bigDecimalValue()", this.getClass(), FloatNode.class, IntNode.class));
    }

    @Contract(pure = true)
    @Override
    public boolean boolValue() {
        throw new UnsupportedOperationException(expect("boolValue()", this.getClass(), BoolNode.class));
    }

    public static @NotNull JsonNode translate(@NotNull @NonNull ASTNode node) {
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

    public static @NotNull JsonNode parse(@NotNull @NonNull String text) {
        return translate(new Parser(text).value());
    }

    protected @NotNull String indent(@NotNull @NonNull String stuff, int depth) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            res.append(' ');
        }
        String indent = res.toString();
        return Arrays.stream(stuff.split("\n")).map((line) -> indent + line).collect(Collectors.joining("\n"));
    }

    private static @NotNull String expect(@NotNull @NonNull String method, @NotNull @NonNull Class<? extends JsonNode> thisType, @NotNull @NonNull Class<?>... requiredTypes) {
        return "Cannot call " + method + " on " + thisType.getSimpleName() + ". Must be " + Arrays.stream(requiredTypes).map(Class::getSimpleName).collect(Collectors.joining(" or ")) + ".";
    }
}
