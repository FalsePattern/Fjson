package com.falsepattern.json.node;

import com.falsepattern.json.parsing.ASTNode;
import com.falsepattern.json.parsing.TerminalNode;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Objects;

@Unmodifiable
public class IntNode extends JsonNode {
    private final int value;
    private static final int LOW_VALUES = 256;
    private static final int LOW_VALUES_MIN = -LOW_VALUES;
    private static final int LOW_VALUES_MAX = LOW_VALUES - 1;
    private static final IntNode[] LOW_VALUES_POS = new IntNode[LOW_VALUES];
    private static final IntNode[] LOW_VALUES_NEG = new IntNode[LOW_VALUES];
    static {
        for (int i = 0; i < LOW_VALUES; i++) {
            LOW_VALUES_POS[i] = new IntNode(i);
            LOW_VALUES_NEG[i] = new IntNode(-i - 1);
        }
    }
    private IntNode(int value) {
        this.value = value;
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return Integer.toString(value);
    }

    @Contract(pure = true)
    @Override
    public boolean isInt() {
        return true;
    }

    @Contract(pure = true)
    @Override
    public boolean isNumber() {
        return true;
    }

    @Contract(pure = true)
    @Override
    public int intValue() {
        return value;
    }

    @Contract(pure = true)
    @Override
    public float floatValue() {
        return value;
    }

    @Contract(pure = true)
    public static @NotNull IntNode of(int value) {
        if (value >= LOW_VALUES_MIN && value <= LOW_VALUES_MAX) {
            return value >= 0 ? LOW_VALUES_POS[value] : LOW_VALUES_NEG[-value - 1];
        }
        return new IntNode(value);
    }

    @Contract(pure = true)
    public static @NotNull IntNode translate(@NotNull @NonNull ASTNode node) {
        if (!Objects.equals(node.type, "int")) throw new InvalidSemanticsException("IntNode", node);
        return new IntNode(Integer.parseInt(((TerminalNode)node).text));
    }
}
