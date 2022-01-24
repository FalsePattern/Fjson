package com.falsepattern.json.node;

import com.falsepattern.json.parsing.ASTNode;
import lombok.val;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ObjectNode extends JsonNode{
    private final Map<String, JsonNode> values = new HashMap<>();
    private Comparator<String> keySorter = Comparator.naturalOrder();

    public JsonNode get(String key) {
        return values.get(key);
    }

    @Override
    public JsonNode set(String key, JsonNode value) {
        return values.put(key, value);
    }

    @Override
    public JsonNode remove(String key) {
        return values.remove(key);
    }

    @Override
    public boolean isObject() {
        return true;
    }

    public void setSortingRule(Comparator<String> rule) {
        keySorter = rule;
    }

    @Override
    public int size() {
        return values.size();
    }

    public static ObjectNode translate(ASTNode node) {
        if (!Objects.equals(node.type, "obj")) throw new InvalidSemanticsException("ObjectNode", node);
        val result = new ObjectNode();
        val children = node.getChildren();
        for (val child: children) {
            val parts = child.getChildren();
            result.set(StringNode.deStringify(parts.get(0)), JsonNode.translate(parts.get(1)));
        }
        return result;
    }

    @Override
    public String toString() {
        return "{" + values.entrySet().stream().sorted(Map.Entry.comparingByKey(keySorter)).map((entry) -> "\"" + StringNode.stringify(entry.getKey()) + "\":" + entry.getValue().toString()).collect(Collectors.joining(",")) + "}";
    }

    @Override
    public String prettyPrint(int indentDepth) {
        return "{\n" + values.entrySet().stream().sorted(Map.Entry.comparingByKey(keySorter)).map((entry) -> {
            val key = entry.getKey();
            val value = entry.getValue().prettyPrint(indentDepth);
            return stringifyEntry(key, value);
        })
                .map((entry) -> indent(entry, indentDepth))
                .collect(Collectors.joining(",\n")) + "\n}";
    }

    private static String stringifyEntry(String key, String value) {
        return "\"" + StringNode.stringify(key) + "\": " + value;
    }

    @Override
    public boolean containsKey(String key) {
        return values.containsKey(key);
    }
}
