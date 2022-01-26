package com.falsepattern.json.schema.subrules;

import com.falsepattern.json.node.JsonNode;
import com.falsepattern.json.schema.Rule;
import lombok.val;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Type implements Rule {
    private final List<Rule> rules = new ArrayList<>();
    @Override
    public boolean validate(JsonNode node) {
        return rules.stream().anyMatch((rule) -> rule.validate(node));
    }

    public static Rule parse(JsonNode node) {
        val type = new Type();
        if (node.isList()) {
            node.getJavaList()
                .stream()
                .map(JsonNode::stringValue)
                .map(MatchingType::getTypeMatcher)
                .forEach(type.rules::add);
        } else {
            type.rules.add(MatchingType.getTypeMatcher(node.stringValue()));
        }
        return type;
    }

    private enum MatchingType implements Rule {
        Integer((jsonNode -> jsonNode.isInt())),
        Number((jsonNode -> jsonNode.isInt() || jsonNode.isFloat())),
        String((jsonNode -> jsonNode.isString())),
        Object((jsonNode -> jsonNode.isObject())),
        Array((jsonNode -> jsonNode.isList())),
        Boolean((jsonNode -> jsonNode.isBoolean())),
        Null((jsonNode -> jsonNode.isNull())),
        Any((jsonNode -> jsonNode != null))

        ;
        public final Function<JsonNode, Boolean> matcher;
        MatchingType(Function<JsonNode, Boolean> matcher) {
            this.matcher = matcher;
        }

        @Override
        public boolean validate(JsonNode node) {
            return matcher.apply(node);
        }

        public static MatchingType getTypeMatcher(String str) {
            str = str.toLowerCase();
            String finalStr = str;
            return Arrays.stream(values()).filter((value) -> value.name().toLowerCase().equals(finalStr)).findFirst().orElseThrow(() -> new IllegalArgumentException("Unknown type: " + finalStr));
        }
    }
}
