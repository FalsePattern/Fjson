package com.falsepattern.json.node;

import com.falsepattern.json.parsing.ASTNode;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Objects;

@Unmodifiable
public class BoolNode extends JsonNode {
    private static final BoolNode True = new BoolNode(true);
    private static final BoolNode False = new BoolNode(false);

    private final boolean value;
    private BoolNode(boolean value) {
        this.value = value;
    }

    @Contract(pure = true)
    @Override
    public boolean isBoolean() {
        return true;
    }

    @Contract(pure = true)
    @Override
    public boolean boolValue() {
        return value;
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return value ? "true" : "false";
    }

    @Contract(pure = true)
    public static @NotNull BoolNode of(boolean value) {
        return value ? BoolNode.True : BoolNode.False;
    }

    @Contract(pure = true)
    public static @NotNull BoolNode translate(@NotNull @NonNull ASTNode node) {
        if (Objects.equals(node.type, "true")) return BoolNode.True;
        if (Objects.equals(node.type, "false")) return BoolNode.False;
        throw new InvalidSemanticsException("BoolNode", node);
    }
}
