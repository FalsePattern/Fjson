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
public class FloatNode extends JsonNode {
    private final BigDecimal value;
    private BigInteger integerForm;
    private FloatNode(BigDecimal value) {
        this.value = value;
    }

    @Override
    public boolean equals(@NotNull JsonNode other) {
        return other.isNumber() && other.bigDecimalValue().equals(value);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return value.toPlainString();
    }

    @Override
    public @NotNull JsonNode clone() {
        return this;
    }

    @Contract(pure = true)
    @Override
    public boolean isFloat() {
        return true;
    }

    @Override
    public boolean isInt() {
        try {
            //noinspection ResultOfMethodCallIgnored
            bigIntValue();
            return true;
        } catch (ArithmeticException e) {
            return false;
        }
    }

    @Override
    public @NotNull BigInteger bigIntValue() {
        //Lazy initialization
        return integerForm == null ? integerForm = value.toBigIntegerExact() : integerForm;
    }

    @Override
    public @NotNull BigDecimal bigDecimalValue() {
        return value;
    }

    @Contract(pure = true)
    public static @NotNull FloatNode of(float value) {
        return new FloatNode(BigDecimal.valueOf(value));
    }

    public static @NotNull FloatNode of(double value) {
        return new FloatNode(BigDecimal.valueOf(value));
    }

    public static @NotNull FloatNode of(String value) {
        return new FloatNode(new BigDecimal(value));
    }

    public static @NotNull FloatNode of(BigDecimal value) {
        return new FloatNode(value);
    }

    @Contract(pure = true)
    public static @NotNull FloatNode translate(@NotNull @NonNull ASTNode node) {
        if (!Objects.equals(node.type, "float")) throw new InvalidSemanticsException("FloatNode", node);
        return new FloatNode(new BigDecimal(((TerminalNode) node).text));
    }
}
