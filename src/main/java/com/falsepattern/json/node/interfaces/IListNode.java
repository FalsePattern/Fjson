package com.falsepattern.json.node.interfaces;

import com.falsepattern.json.node.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.List;

public interface IListNode extends ISizedNode, INode {
    /**
     * Retrieves the value at the given index.
     * @param index The index to retrieve the value from.
     * @return The value at the given index.
     * @throws UnsupportedOperationException If this node is not a {@link ListNode}.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    @Contract(pure = true)
    @NotNull JsonNode get(int index);

    /**
     * Retrieves an unmodifiable view of the values in this node.
     * @return An unmodifiable {@link List} view of the values in this node.
     * @throws UnsupportedOperationException If this node is not a {@link ListNode}.
     */
    @Contract(pure = true)
    @NotNull @UnmodifiableView List<@NotNull JsonNode> getJavaList();

    /**
     * Checks if the given index is within the bounds of this node.
     * @param index The index to check.
     * @return True if the index is within the bounds of this node.
     * @throws UnsupportedOperationException If this node is not a {@link ListNode}.
     */
    @Contract(pure = true)
    boolean containsIndex(int index);

    /**
     * Sets the value at the given index.
     * @param index The index to set the value at.
     * @param value The value to set.
     * @throws UnsupportedOperationException If this node is not a {@link ListNode}.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    @Contract(mutates = "this")
    void set(int index, @NotNull JsonNode value);

    /**
     * Adds a value to the end of this node.
     * @param value The value to add.
     * @throws UnsupportedOperationException If this node is not a {@link ListNode}.
     */
    @Contract(mutates = "this")
    void add(@NotNull JsonNode value);

    /**
     * Removes the value from the given index.
     * @param key The index to remove the value from.
     * @return The value that was removed.
     * @throws UnsupportedOperationException If this node is not a {@link ListNode}.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    @Contract(mutates = "this")
    @NotNull JsonNode remove(int key);

    /**
     * @return This node as a {@link ListNode}.
     * @throws UnsupportedOperationException If this node is not a {@link ListNode}.
     */
    @Contract(value = "-> this", pure = true)
    @NotNull ListNode asListNode();

    /**
     * See {@link #get(int)}.
     * @param index The index to get the value from.
     * @return The value at the given index as an <code>int</code>.
     * @throws UnsupportedOperationException If this node is not a {@link ListNode}.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     * @throws UnsupportedOperationException If the value at the given index is not an {@link IntNode} or a {@link FloatNode}.
     */
    @Contract(pure = true)
    default int getInt(int index) {
        return get(index).intValue();
    }

    /**
     * See {@link #get(int)}.
     * @param index The index to get the value from.
     * @return The value at the given index as a <code>float</code>.
     * @throws UnsupportedOperationException If this node is not a {@link ListNode}.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     * @throws UnsupportedOperationException If the value at the given index is not a {@link FloatNode} or an {@link IntNode}.
     */
    @Contract(pure = true)
    default float getFloat(int index) {
        return get(index).floatValue();
    }

    /**
     * See {@link #get(int)}.
     * @param index The index to get the value from.
     * @return The value at the given index as a <code>boolean</code>.
     * @throws UnsupportedOperationException If this node is not a {@link ListNode}.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     * @throws UnsupportedOperationException If the value at the given index is not a {@link BoolNode}.
     */
    @Contract(pure = true)
    default boolean getBool(int index) {
        return get(index).boolValue();
    }

    /**
     * See {@link #get(int)}.
     * @param index The index to get the value from.
     * @return The value at the given index as a <code>String</code>.
     * @throws UnsupportedOperationException If this node is not a {@link ListNode}.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     * @throws UnsupportedOperationException If the value at the given index is not a {@link StringNode}.
     */
    @Contract(pure = true)
    default String getString(int index) {
        return get(index).toString();
    }

    /**
     * Retrieves the value at the given index, or the default value if the index is out of bounds. See {@link #containsIndex(int)}.
     * @param index The index to get the value from.
     * @param defaultValue The default value to return if the index is out of bounds.
     * @return The value at the given index, or the default value if the index is out of bounds.
     * @throws UnsupportedOperationException If this node is not a {@link ListNode}.
     */
    @Contract(pure = true)
    default @NotNull JsonNode getOrDefault(int index, @NotNull JsonNode defaultValue) {
        return containsIndex(index) ? get(index) : defaultValue;
    }

    /**
     * See {@link #getOrDefault(int, JsonNode)}.
     * @param index The index to get the value from.
     * @param defaultValue The default value to return if the index is out of bounds.
     * @return The value at the given index, as an <code>int</code>, or the default value if the index is out of bounds.
     * @throws UnsupportedOperationException If this node is not a {@link ListNode}.
     * @throws UnsupportedOperationException If the value at the given index, if present, is not an {@link IntNode} or a {@link FloatNode}.
     */
    @Contract(pure = true)
    default int getIntOrDefault(int index, int defaultValue) {
        return containsIndex(index) ? getInt(index) : defaultValue;
    }

    /**
     * See {@link #getOrDefault(int, JsonNode)}.
     * @param index The index to get the value from.
     * @param defaultValue The default value to return if the index is out of bounds.
     * @return The value at the given index, as a <code>float</code>, or the default value if the index is out of bounds.
     * @throws UnsupportedOperationException If this node is not a {@link ListNode}.
     * @throws UnsupportedOperationException If the value at the given index, if present, is not a {@link FloatNode} or an {@link IntNode}.
     */
    @Contract(pure = true)
    default float getFloatOrDefault(int index, float defaultValue) {
        return containsIndex(index) ? getFloat(index) : defaultValue;
    }

    /**
     * See {@link #getOrDefault(int, JsonNode)}.
     * @param index The index to get the value from.
     * @param defaultValue The default value to return if the index is out of bounds.
     * @return The value at the given index, as a <code>boolean</code>, or the default value if the index is out of bounds.
     * @throws UnsupportedOperationException If this node is not a {@link ListNode}.
     * @throws UnsupportedOperationException If the value at the given index, if present, is not a {@link BoolNode}.
     */
    @Contract(pure = true)
    default boolean getBoolOrDefault(int index, boolean defaultValue) {
        return containsIndex(index) ? getBool(index) : defaultValue;
    }

    /**
     * See {@link #getOrDefault(int, JsonNode)}.
     * @param index The index to get the value from.
     * @param defaultValue The default value to return if the index is out of bounds.
     * @return The value at the given index, as a {@link String}, or the default value if the index is out of bounds.
     * @throws UnsupportedOperationException If this node is not a {@link ListNode}.
     * @throws UnsupportedOperationException If the value at the given index, if present, is not a {@link StringNode}.
     */
    @Contract(pure = true)
    default @NotNull String getStringOrDefault(int index, @NotNull String defaultValue) {
        return containsIndex(index) ? getString(index) : defaultValue;
    }

    /**
     * See {@link #set(int, JsonNode)}.
     * @param index The index to set the value at.
     * @param value The value to set.
     * @throws UnsupportedOperationException If this node is not a {@link ListNode}.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    @Contract(mutates = "this")
    default void set(int index, int value) {
        set(index, IntNode.of(value));
    }

    /**
     * See {@link #set(int, JsonNode)}.
     * @param index The index to set the value at.
     * @param value The value to set.
     * @throws UnsupportedOperationException If this node is not a {@link ListNode}.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    @Contract(mutates = "this")
    default void set(int index, float value) {
        set(index, FloatNode.of(value));
    }

    /**
     * See {@link #set(int, JsonNode)}.
     * @param index The index to set the value at.
     * @param value The value to set.
     * @throws UnsupportedOperationException If this node is not a {@link ListNode}.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    @Contract(mutates = "this")
    default void set(int index, boolean value) {
        set(index, BoolNode.of(value));
    }

    /**
     * See {@link #set(int, JsonNode)}.
     * @param index The index to set the value at.
     * @param value The value to set.
     * @throws UnsupportedOperationException If this node is not a {@link ListNode}.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    @Contract(mutates = "this")
    default void set(int index, @NotNull String value) {
        set(index, StringNode.of(value));
    }

    /**
     * Sets the value at the given index to a {@link NullNode}.
     * @param index The index to set the value at.
     * @throws UnsupportedOperationException If this node is not a {@link ListNode}.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    @Contract(mutates = "this")
    default void setNull(int index) {
        set(index, NullNode.Null);
    }

    /**
     * See {@link #add(JsonNode)}.
     * @param value The value to add.
     * @throws UnsupportedOperationException If this node is not a {@link ListNode}.
     */
    @Contract(mutates = "this")
    default void add(int value) {
        add(IntNode.of(value));
    }

    /**
     * See {@link #add(JsonNode)}.
     * @param value The value to add.
     * @throws UnsupportedOperationException If this node is not a {@link ListNode}.
     */
    @Contract(mutates = "this")
    default void add(float value) {
        add(FloatNode.of(value));
    }

    /**
     * See {@link #add(JsonNode)}.
     * @param value The value to add.
     * @throws UnsupportedOperationException If this node is not a {@link ListNode}.
     */
    @Contract(mutates = "this")
    default void add(boolean value) {
        add(BoolNode.of(value));
    }

    /**
     * See {@link #add(JsonNode)}.
     * @param value The value to add.
     * @throws UnsupportedOperationException If this node is not a {@link ListNode}.
     */
    @Contract(mutates = "this")
    default void add(@NotNull String value) {
        add(StringNode.of(value));
    }

    /**
     * Adds a {@link NullNode} to the end of this list.
     * @throws UnsupportedOperationException If this node is not a {@link ListNode}.
     */
    @Contract(mutates = "this")
    default void addNull() {
        add(NullNode.Null);
    }
}
