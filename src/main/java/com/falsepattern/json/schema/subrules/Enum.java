package com.falsepattern.json.schema.subrules;

import com.falsepattern.json.node.JsonNode;
import com.falsepattern.json.schema.Rule;

import java.util.ArrayList;
import java.util.List;

public class Enum implements Rule{
    private final List<JsonNode> values = new ArrayList<>();

    @Override
    public boolean validate(JsonNode node) {
        return values.stream().anyMatch(node::equals);
    }

    public static Rule parse(JsonNode json) {
        Enum rule = new Enum();
        rule.values.addAll(json.getJavaList());
        return rule;
    }
}
