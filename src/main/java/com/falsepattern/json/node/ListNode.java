package com.falsepattern.json.node;

import com.falsepattern.json.parsing.ASTNode;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ListNode extends JsonNode {
    private final List<JsonNode> nodes = new ArrayList<>();

    @Override
    public JsonNode get(int index) {
        return nodes.get(index);
    }

    @Override
    public JsonNode set(int index, JsonNode value) {
        return nodes.set(index, value);
    }

    @Override
    public void add(JsonNode value) {
        nodes.add(value);
    }

    @Override
    public JsonNode remove(int key) {
        return nodes.remove(key);
    }

    @Override
    public int size() {
        return nodes.size();
    }

    @Override
    public boolean isList() {
        return true;
    }



    public static ListNode translate(ASTNode node) {
        if (!Objects.equals(node.type, "arr")) throw new InvalidSemanticsException("ListNode", node);
        val result = new ListNode();
        val children = node.getChildren();
        for (val child: children) {
            result.add(JsonNode.translate(child));
        }
        return result;
    }

    @Override
    public String toString() {
        return "[" + nodes.stream().map(JsonNode::toString).collect(Collectors.joining(",")) + "]";
    }
}