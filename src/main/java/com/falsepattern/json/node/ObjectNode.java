package com.falsepattern.json.node;

import com.falsepattern.json.Util;
import com.falsepattern.json.parsing.ASTNode;
import lombok.NonNull;
import lombok.val;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class ObjectNode extends JsonNode {
    private final Map<@NotNull String, @NotNull JsonNode> values = new HashMap<>();
    private Comparator<@NotNull String> keySorter = Comparator.naturalOrder();

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "{" + values.entrySet().stream().sorted(Map.Entry.comparingByKey(keySorter)).map((entry) -> "\"" + StringNode.stringify(entry.getKey()) + "\":" + entry.getValue()).collect(Collectors.joining(",")) + "}";
    }

    @Contract(pure = true)
    @Override
    public @NotNull String prettyPrint(int indentDepth) {
        return "{\n" + values.entrySet().stream().sorted(Map.Entry.comparingByKey(keySorter)).map((entry) -> {
                                 val key = entry.getKey();
                                 val value = entry.getValue().prettyPrint(indentDepth);
                                 return stringifyEntry(key, value);
                             })
                             .map((entry) -> indent(entry, indentDepth))
                             .collect(Collectors.joining(",\n")) + "\n}";
    }

    @Contract(pure = true)
    @Override
    public @NotNull JsonNode get(@NotNull String key) {
        return Util.orElseThrow(values.get(key), () -> new NoSuchElementException("No such key: " + key + " in json object"));
    }

    @Contract(pure = true)
    @Override
    public @NotNull Map<String, JsonNode> getJavaMap() {
        return Collections.unmodifiableMap(values);
    }

    @Contract(mutates = "this")
    @Override
    public void set(@NotNull @NonNull String key, @NotNull @NonNull JsonNode value) {
        values.put(key, value);
    }

    @Contract(mutates = "this")
    @Override
    public @NotNull JsonNode remove(@NotNull @NonNull String key) {
        return values.remove(key);
    }

    @Contract(pure = true)
    @Override
    public boolean isObject() {
        return true;
    }

    @Contract(pure = true)
    @Override
    public int size() {
        return values.size();
    }

    @Contract(pure = true)
    @Override
    public boolean containsKey(@NotNull @NonNull String key) {
        return values.containsKey(key);
    }

    @Contract(mutates = "this")
    public void setSortingRule(@NotNull @NonNull Comparator<String> rule) {
        keySorter = rule;
    }

    @Contract(pure = true)
    public static @NotNull ObjectNode translate(@NotNull @NonNull ASTNode node) {
        if (!Objects.equals(node.type, "obj")) throw new InvalidSemanticsException("ObjectNode", node);
        val result = new ObjectNode();
        val children = node.getChildren();
        for (val child: children) {
            val parts = child.getChildren();
            result.set(StringNode.translate(parts.get(0)).stringValue(), JsonNode.translate(parts.get(1)));
        }
        return result;
    }

    @Contract(pure = true)
    private static @NotNull String stringifyEntry(@NotNull String key, @NotNull String value) {
        return "\"" + StringNode.stringify(key) + "\": " + value;
    }
}
