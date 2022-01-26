package com.falsepattern.json.schema;

import com.falsepattern.json.node.JsonNode;

public interface Rule {
    boolean validate(JsonNode node);
}
