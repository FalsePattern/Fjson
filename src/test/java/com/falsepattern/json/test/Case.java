package com.falsepattern.json.test;

import com.falsepattern.json.node.JsonNode;
import com.falsepattern.json.schema.Rule;
import com.falsepattern.json.schema.subrules.Schema;
import lombok.val;
import org.junit.jupiter.api.Assertions;

public class Case {
    public String description;
    public JsonNode data;
    public boolean valid;
    public Case(JsonNode json) {
        description = json.getString("description");
        data = json.get("data");
        valid = json.getBool("valid");
    }

    public void validateAgainst(Rule rule) {
        Assertions.assertEquals(valid, rule.validate(data));
    }
}
