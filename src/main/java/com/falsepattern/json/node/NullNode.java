package com.falsepattern.json.node;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

@Unmodifiable
public class NullNode extends JsonNode {
    public static final NullNode Null = new NullNode();
    private NullNode(){}

    @Contract(pure = true)
    @Override
    public boolean isNull() {
        return true;
    }

    @Override
    public boolean equals(@NotNull JsonNode other) {
        return other.isNull();
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "null";
    }

    @Override
    public @NotNull JsonNode clone() {
        return this;
    }
}
