package com.falsepattern.json.schema.subrules;

import com.falsepattern.json.node.JsonNode;
import com.falsepattern.json.schema.Rule;
import lombok.val;

import java.util.ArrayList;
import java.util.List;

public class Schema implements Rule {
    public final List<Rule> rules = new ArrayList<>();

    public boolean validate(JsonNode json) {
        return rules.stream().allMatch((rule) -> rule.validate(json));
    }

    public static Rule parse(JsonNode json) {
        val schema = new Schema();
        if (json.containsKey("type")) {
            schema.rules.add(Type.parse(json.remove("type")));
        }
        if (json.containsKey("enum")) {
            schema.rules.add(Enum.parse(json.remove("enum")));
        }
        return schema;
    }
}
