package com.falsepattern.json.node.interfaces;

import com.falsepattern.json.node.JsonNode;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface INode {
    /**
     * @return The compact JSON representation of this node.
     */
    @Contract(pure = true)
    @NotNull String toString();

    /**
     * @param indentDepth The number of spaces to indent the output.
     * @return The formatted JSON representation of this node, with the given indent depth.
     */
    @Contract(pure = true)
    @NotNull String prettyPrint(int indentDepth);

    /**
     * @return True if this node is an {@link com.falsepattern.json.node.ObjectNode}, false otherwise.
     */
    @Contract(pure = true)
    boolean isObject();

    /**
     * @return True if this node is a {@link com.falsepattern.json.node.ListNode}, false otherwise.
     */
    @Contract(pure = true)
    boolean isList();

    /**
     * @return True if this node is a {@link com.falsepattern.json.node.StringNode}, false otherwise.
     */
    @Contract(pure = true)
    boolean isString();

    /**
     * @return True if this node is a {@link com.falsepattern.json.node.IntNode}, false otherwise.
     */
    @Contract(pure = true)
    boolean isInt();

    /**
     * @return True if this node is a {@link com.falsepattern.json.node.FloatNode}, false otherwise.
     */
    @Contract(pure = true)
    boolean isFloat();

    /**
     * @return True if this node is either a {@link com.falsepattern.json.node.FloatNode} or {@link com.falsepattern.json.node.IntNode}, false otherwise.
     */
    @Contract(pure = true)
    boolean isNumber();

    /**
     * @return True if this node is a {@link com.falsepattern.json.node.BoolNode}, false otherwise.
     */
    @Contract(pure = true)
    boolean isBoolean();

    /**
     * @return True if this node is a {@link com.falsepattern.json.node.NullNode}, false otherwise.
     */
    @Contract(pure = true)
    boolean isNull();

    /**
     * @return The string contained in this node.
     * @throws UnsupportedOperationException If this node is not a {@link com.falsepattern.json.node.StringNode}.
     */
    @Contract(pure = true)
    @NotNull String stringValue();

    /**
     * @return the integer value of this node.
     * @throws UnsupportedOperationException If this node is not a {@link com.falsepattern.json.node.IntNode} or {@link com.falsepattern.json.node.FloatNode}.
     */
    @Contract(pure = true)
    int intValue();

    /**
     * @return The float value of this node.
     * @throws UnsupportedOperationException If this node is not a {@link com.falsepattern.json.node.FloatNode} or {@link com.falsepattern.json.node.IntNode}.
     */
    @Contract(pure = true)
    float floatValue();

    /**
     * @return The boolean value of this node.
     * @throws UnsupportedOperationException If this node is not a {@link com.falsepattern.json.node.BoolNode}.
     */
    @Contract(pure = true)
    boolean boolValue();

    /**
     * @param other The node to compare to.
     * @return True if this node is equal to the given node, false otherwise.
     */
    @Contract(pure = true)
    boolean equals(@NotNull JsonNode other);
}
