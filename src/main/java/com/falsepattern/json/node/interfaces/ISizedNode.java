package com.falsepattern.json.node.interfaces;

import org.jetbrains.annotations.Contract;

public interface ISizedNode {
    /**
     * @return The size of the node.
     * @throws UnsupportedOperationException If this node is neither a {@link com.falsepattern.json.node.ObjectNode} nor a {@link com.falsepattern.json.node.ListNode}.
     */
    @Contract(pure = true)
    int size();
}
