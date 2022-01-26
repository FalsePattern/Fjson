package com.falsepattern.json.node;

import com.falsepattern.json.parsing.ASTNode;
import lombok.NonNull;
import lombok.val;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.*;
import java.util.stream.Collectors;

public class ListNode extends JsonNode {
    private final List<@NotNull JsonNode> nodes = new ArrayList<>();

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "[" + nodes.stream().map(JsonNode::toString).collect(Collectors.joining(",")) + "]";
    }

    @Contract(pure = true)
    @Override
    public @NotNull String prettyPrint(int indentDepth) {
        return "[\n" + nodes.stream()
                            .map((node) -> node.prettyPrint(indentDepth))
                            .map((block) -> indent(block, indentDepth))
                            .collect(Collectors.joining(",\n")) + "\n]";
    }

    @Contract(pure = true)
    @Override
    public @NotNull JsonNode get(int index) {
        return nodes.get(index);
    }

    @Contract(pure = true)
    @Override
    public @NotNull @UnmodifiableView List<JsonNode> getJavaList() {
        return Collections.unmodifiableList(nodes);
    }

    @Contract(mutates = "this")
    @Override
    public void set(int index, @NotNull @NonNull JsonNode value) {
        nodes.set(index, value);
    }

    @Contract(mutates = "this")
    @Override
    public void add(@NotNull @NonNull JsonNode value) {
        nodes.add(value);
    }

    @Contract(mutates = "this")
    @Override
    public @NotNull JsonNode remove(int key) {
        return nodes.remove(key);
    }

    @Contract(pure = true)
    @Override
    public boolean containsIndex(int index) {
        return index >= 0 && index < nodes.size();
    }

    @Contract(pure = true)
    @Override
    public int size() {
        return nodes.size();
    }

    @Contract(pure = true)
    @Override
    public boolean isList() {
        return true;
    }

    @Contract(pure = true)
    public static @NotNull ListNode translate(@NotNull @NonNull ASTNode node) {
        if (!Objects.equals(node.type, "arr")) throw new InvalidSemanticsException("ListNode", node);
        val result = new ListNode();
        val children = node.getChildren();
        for (val child: children) {
            result.add(JsonNode.translate(child));
        }
        return result;
    }
}
