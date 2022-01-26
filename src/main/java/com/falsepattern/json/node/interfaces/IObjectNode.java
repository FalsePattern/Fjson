package com.falsepattern.json.node.interfaces;

import com.falsepattern.json.node.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Comparator;
import java.util.Map;

public interface IObjectNode extends ISizedNode, INode {
    /**
     * Retrieves the value of the given property.
     * @param key The property to get the value of.
     * @return The value of the property.
     * @throws UnsupportedOperationException If this node is not an {@link ObjectNode}.
     * @throws java.util.NoSuchElementException If the property does not exist. See {@link #containsKey(String)}.
     */
    @Contract(pure = true)
    @NotNull JsonNode get(@NotNull String key);

    /**
     * Checks if the given property exists.
     * @param key The property to check for.
     * @return True if this node contains the given property.
     * @throws UnsupportedOperationException If this node is not an {@link ObjectNode}.
     */
    @Contract(pure = true)
    boolean containsKey(@NotNull String key);

    /**
     * Sets the value of the given property.
     * @param key The property to set.
     * @param value The value to set the property to.
     * @throws UnsupportedOperationException If this node is not an {@link ObjectNode}.
     */
    @Contract(mutates = "this")
    void set(@NotNull String key, @NotNull JsonNode value);

    /**
     * Retrieves an unmodifiable view of the properties of this node.
     * @return An unmodifiable {@link Map} of the properties of this node.
     * @throws UnsupportedOperationException If this node is not an {@link ObjectNode}.
     */
    @Contract(pure = true)
    @NotNull @UnmodifiableView Map<@NotNull String, @NotNull JsonNode> getJavaMap();

    /**
     * @param key The property to remove.
     * @return The value of the property that was removed.
     * @throws UnsupportedOperationException If this node is not an {@link ObjectNode}.
     * @throws java.util.NoSuchElementException If the property does not exist. See {@link #containsKey(String)}.
     */
    @Contract(mutates = "this")
    @NotNull JsonNode remove(@NotNull String key);

    /**
     * @return This node as an {@link ObjectNode}.
     * @throws UnsupportedOperationException If this node is not an {@link ObjectNode}.
     */
    @Contract(value = "-> this", pure = true)
    @NotNull ObjectNode asObjectNode();

    /**
     * Sets the sort order used for the keys of this node when converting to a json string.
     * @throws UnsupportedOperationException If this node is not an {@link ObjectNode}.
     */
    @Contract(mutates = "this")
    void setSortingRule(@NotNull Comparator<@NotNull String> rule);

    /**
     * @param key The property to get the value of.
     * @return The value of the property, converted to an <code>int</code>.
     * @throws UnsupportedOperationException If this node is not an {@link ObjectNode}.
     * @throws java.util.NoSuchElementException If the property does not exist. See {@link #containsKey(String)}.
     * @throws UnsupportedOperationException If the property is not an {@link IntNode} nor a {@link FloatNode}.
     */
    @Contract(pure = true)
    default int getInt(@NotNull String key) {
        return get(key).intValue();
    }

    /**
     * @param key The property to get the value of.
     * @return The value of the property, converted to a <code>float</code>.
     * @throws UnsupportedOperationException If this node is not an {@link ObjectNode}.
     * @throws java.util.NoSuchElementException If the property does not exist. See {@link #containsKey(String)}.
     * @throws UnsupportedOperationException If the property is not a {@link FloatNode} nor an {@link IntNode}.
     */
    @Contract(pure = true)
    default float getFloat(@NotNull String key) {
        return get(key).floatValue();
    }

    /**
     * @param key The property to get the value of.
     * @return The value of the property, converted to a <code>boolean</code>.
     * @throws UnsupportedOperationException If this node is not an {@link ObjectNode}.
     * @throws java.util.NoSuchElementException If the property does not exist. See {@link #containsKey(String)}.
     * @throws UnsupportedOperationException If the property is not a {@link BoolNode}.
     */
    @Contract(pure = true)
    default boolean getBool(@NotNull String key) {
        return get(key).boolValue();
    }

    /**
     * @param key The property to get the value of.
     * @return The value of the property, converted to a {@link String}.
     * @throws UnsupportedOperationException If this node is not an {@link ObjectNode}.
     * @throws java.util.NoSuchElementException If the property does not exist. See {@link #containsKey(String)}.
     * @throws UnsupportedOperationException If the property is not a {@link StringNode}.
     */
    @Contract(pure = true)
    default @NotNull String getString(@NotNull String key) {
        return get(key).stringValue();
    }

    /**
     * @param key The property to get the value of.
     * @param defaultValue The default value to return if the property does not exist.
     * @return The value of the property, or the default value if the property does not exist.
     * @throws UnsupportedOperationException If this node is not an {@link ObjectNode}.
     */
    @Contract(pure = true)
    default @NotNull JsonNode getOrDefault(@NotNull String key, @NotNull JsonNode defaultValue) {
        return containsKey(key) ? get(key) : defaultValue;
    }

    /**
     * @param key The property to get the value of.
     * @param defaultValue The default value to return if the property does not exist.
     * @return The value of the property, converted to an <code>int</code> or the default value if the property does not exist.
     * @throws UnsupportedOperationException If this node is not an {@link ObjectNode}.
     * @throws UnsupportedOperationException If the property exists, but it's not an {@link IntNode} nor a {@link FloatNode}.
     */
    @Contract(pure = true)
    default int getIntOrDefault(@NotNull String key, int defaultValue) {
        return containsKey(key) ? getInt(key) : defaultValue;
    }

    /**
     * @param key The property to get the value of.
     * @param defaultValue The default value to return if the property does not exist.
     * @return The value of the property, converted to a <code>float</code> or the default value if the property does not exist.
     * @throws UnsupportedOperationException If this node is not an {@link ObjectNode}.
     * @throws UnsupportedOperationException If the property exists, but it's not a {@link FloatNode} nor an {@link IntNode}.
     */
    @Contract(pure = true)
    default float getFloatOrDefault(@NotNull String key, float defaultValue) {
        return containsKey(key) ? getFloat(key) : defaultValue;
    }

    /**
     * @param key The property to get the value of.
     * @param defaultValue The default value to return if the property does not exist.
     * @return The value of the property, converted to a <code>boolean</code> or the default value if the property does not exist.
     * @throws UnsupportedOperationException If this node is not an {@link ObjectNode}.
     * @throws UnsupportedOperationException If the property exists, but it's not a {@link BoolNode}.
     */
    @Contract(pure = true)
    default boolean getBoolOrDefault(@NotNull String key, boolean defaultValue) {
        return containsKey(key) ? getBool(key) : defaultValue;
    }

    /**
     * @param key The property to get the value of.
     * @param defaultValue The default value to return if the property does not exist.
     * @return The value of the property, converted to a {@link String} or the default value if the property does not exist.
     * @throws UnsupportedOperationException If this node is not an {@link ObjectNode}.
     * @throws UnsupportedOperationException If the property exists, but it's not a {@link StringNode}.
     */
    @Contract(pure = true)
    default @NotNull String getStringOrDefault(@NotNull String key, @NotNull String defaultValue) {
        return containsKey(key) ? getString(key) : defaultValue;
    }

    /**
     * Sets the value of the given property.
     * @param key The property to set the value of.
     * @param value The value to set the property to.
     * @throws UnsupportedOperationException If this node is not an {@link ObjectNode}.
     */
    @Contract(mutates = "this")
    default void set(@NotNull String key, int value) {
        set(key, IntNode.of(value));
    }

    /**
     * Sets the value of the given property.
     * @param key The property to set the value of.
     * @param value The value to set the property to.
     * @throws UnsupportedOperationException If this node is not an {@link ObjectNode}.
     */
    @Contract(mutates = "this")
    default void set(@NotNull String key, float value) {
        set(key, FloatNode.of(value));
    }

    /**
     * Sets the value of the given property.
     * @param key The property to set the value of.
     * @param value The value to set the property to.
     * @throws UnsupportedOperationException If this node is not an {@link ObjectNode}.
     */
    @Contract(mutates = "this")
    default void set(@NotNull String key, boolean value) {
        set(key, BoolNode.of(value));
    }

    /**
     * Sets the value of the given property.
     * @param key The property to set the value of.
     * @param value The value to set the property to.
     * @throws UnsupportedOperationException If this node is not an {@link ObjectNode}.
     */
    @Contract(mutates = "this")
    default void set(@NotNull String key, @NotNull String value) {
        set(key, StringNode.of(value));
    }

    /**
     * Sets the value of the given property to a {@link NullNode}.
     * @param key The property to set the value of.
     * @throws UnsupportedOperationException If this node is not an {@link ObjectNode}.
     */
    @Contract(mutates = "this")
    default void setNull(@NotNull String key) {
        set(key, NullNode.Null);
    }

}
