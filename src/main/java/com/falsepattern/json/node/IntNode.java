package com.falsepattern.json.node;

import com.falsepattern.json.parsing.ASTNode;
import com.falsepattern.json.parsing.TerminalNode;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

@Unmodifiable
public class IntNode extends JsonNode {
    private final BigInteger value;
    private BigDecimal decimalForm;
    private static final int LOW_VALUES = 256;
    private static final int LOW_VALUES_MIN = -LOW_VALUES;
    private static final int LOW_VALUES_MAX = LOW_VALUES - 1;
    private static final IntNode[] LOW_VALUES_POS = new IntNode[LOW_VALUES];
    private static final IntNode[] LOW_VALUES_NEG = new IntNode[LOW_VALUES];
    static {
        for (int i = 0; i < LOW_VALUES; i++) {
            LOW_VALUES_POS[i] = new IntNode(BigInteger.valueOf(i));
            LOW_VALUES_NEG[i] = new IntNode(BigInteger.valueOf(-i - 1));
        }
    }
    private IntNode(BigInteger value) {
        this.value = value;
    }

    @Override
    public boolean equals(@NotNull JsonNode other) {
        return other.isNumber() && other.bigIntValue().equals(value);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return value.toString();
    }

    @Override
    public @NotNull JsonNode clone() {
        return this;
    }

    @Contract(pure = true)
    @Override
    public boolean isInt() {
        return true;
    }

    @Override
    public @NotNull BigInteger bigIntValue() {
        return value;
    }

    @Override
    public @NotNull BigDecimal bigDecimalValue() {
        //Lazy initialization
        return decimalForm == null ? decimalForm = new BigDecimal(value) : decimalForm;
    }

    @Contract(pure = true)
    public static @NotNull IntNode of(int value) {
        if (value >= LOW_VALUES_MIN && value <= LOW_VALUES_MAX) {
            return value >= 0 ? LOW_VALUES_POS[value] : LOW_VALUES_NEG[-value - 1];
        }
        return new IntNode(BigInteger.valueOf(value));
    }

    @Contract(pure = true)
    public static @NotNull IntNode of(long value) {
        if (value >= LOW_VALUES_MIN && value <= LOW_VALUES_MAX) {
            return value >= 0 ? LOW_VALUES_POS[(int) value] : LOW_VALUES_NEG[(int) (-value - 1)];
        }
        return new IntNode(BigInteger.valueOf(value));
    }

    @Contract(pure = true)
    public static @NotNull IntNode of(String value) {
        return new IntNode(new BigInteger(value));
    }

    @Contract(pure = true)
    public static @NotNull IntNode of(BigInteger value) {
        return new IntNode(value);
    }

    @Contract(pure = true)
    public static @NotNull IntNode translate(@NotNull @NonNull ASTNode node) {
        if (!Objects.equals(node.type, "int")) throw new InvalidSemanticsException("IntNode", node);
        return new IntNode(new BigInteger(((TerminalNode) node).text));
    }
}
