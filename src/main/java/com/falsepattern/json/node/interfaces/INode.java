package com.falsepattern.json.node.interfaces;

import com.falsepattern.json.node.JsonNode;
import com.falsepattern.json.schema.ValidationStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface INode extends Cloneable{
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
     * @return The {@link BigInteger} value of this node.
     * @throws UnsupportedOperationException If this node is not a {@link com.falsepattern.json.node.IntNode} or {@link com.falsepattern.json.node.FloatNode}.
     * @throws ArithmeticException If the node is a {@link com.falsepattern.json.node.FloatNode} and the value has a non-zero fractional part.
     */
    @Contract(pure = true)
    @NotNull BigInteger bigIntValue();

    /**
     * @return the truncated integer value of this node. See {@link BigInteger#intValue()} for details.
     * @throws UnsupportedOperationException If this node is not a {@link com.falsepattern.json.node.IntNode} or {@link com.falsepattern.json.node.FloatNode}.
     * @throws ArithmeticException If the node is a {@link com.falsepattern.json.node.FloatNode} and the value has a non-zero fractional part.
     */
    @Contract(pure = true)
    default int intValue() {
        return bigIntValue().intValue();
    }

    /**
     * @return The exact integer value of this node. See {@link BigDecimal#intValueExact()} for details.
     * @throws UnsupportedOperationException If this node is not a {@link com.falsepattern.json.node.IntNode} or {@link com.falsepattern.json.node.FloatNode}.
     * @throws ArithmeticException If this node does not fit into an {@code int}.
     * @throws ArithmeticException If the node is a {@link com.falsepattern.json.node.FloatNode} and the value has a non-zero fractional part.
     */
    @Contract(pure = true)
    default int intValueExact() {
        return bigIntValue().intValueExact();
    }

    /**
     * @return The truncated long value of this node. See {@link BigInteger#longValue()} for details.
     * @throws UnsupportedOperationException If this node is not a {@link com.falsepattern.json.node.IntNode} or {@link com.falsepattern.json.node.FloatNode}.
     * @throws ArithmeticException If the node is a {@link com.falsepattern.json.node.FloatNode} and the value has a non-zero fractional part.
     */
    @Contract(pure = true)
    default long longValue() {
        return bigIntValue().longValue();
    }

    /**
     * @return The exact long value of this node. See {@link BigDecimal#longValueExact()} for details.
     * @throws UnsupportedOperationException If this node is not a {@link com.falsepattern.json.node.IntNode} or {@link com.falsepattern.json.node.FloatNode}.
     * @throws ArithmeticException If this node does not fit into a {@code long}.
     * @throws ArithmeticException If the node is a {@link com.falsepattern.json.node.FloatNode} and the value has a non-zero fractional part.
     */
    @Contract(pure = true)
    default long longValueExact() {
        return bigIntValue().longValueExact();
    }

    /**
     * @return The decimal value of this node.
     * @throws UnsupportedOperationException If this node is not a {@link com.falsepattern.json.node.FloatNode} or {@link com.falsepattern.json.node.IntNode}.
     */
    @Contract(pure = true)
    @NotNull BigDecimal bigDecimalValue();

    /**
     * @return The float value of this node. May lose precision.
     * @throws UnsupportedOperationException If this node is not a {@link com.falsepattern.json.node.FloatNode} or {@link com.falsepattern.json.node.IntNode}.
     */
    @Contract(pure = true)
    default float floatValue() {
        return bigDecimalValue().floatValue();
    }

    /**
     * @return The double value of this node. May lose precision.
     * @throws UnsupportedOperationException If this node is not a {@link com.falsepattern.json.node.FloatNode} or {@link com.falsepattern.json.node.IntNode}.
     */
    @Contract(pure = true)
    default double doubleValue() {
        return bigDecimalValue().doubleValue();
    }

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

    /**
     * @return The schema validation result for this node.
     */
    @Contract(pure = true)
    @NotNull ValidationStatus getValidationStatus();

    /**
     * @param status The schema validation result for this node.
     */
    @Contract(mutates = "this")
    void setValidationStatus(@NotNull ValidationStatus status);

    /**
     * Recursively validates this node and all its children.
     * @return The validation result for this tree.
     */
    @Contract(pure = true)
    boolean isValidated();
}
