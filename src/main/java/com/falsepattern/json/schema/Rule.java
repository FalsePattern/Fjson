package com.falsepattern.json.schema;

import com.falsepattern.json.node.JsonNode;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface Rule {
    /**
     * A rule is satisfied if the given node is valid according to the rule. A rule may freely mutate the node,
     * so it is recommended that the passed node is a copy of the original node.
     * @param node The node to validate.
     * @return True if the node is valid according to the rule.
     */
    @Contract(mutates = "param1")
    boolean validate(@NotNull JsonNode node);
}
