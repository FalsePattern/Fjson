package com.falsepattern.json.node;

import com.falsepattern.json.parsing.ASTNode;
import com.falsepattern.json.parsing.TerminalNode;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Objects;

@Unmodifiable
public class FloatNode extends JsonNode {
    private final float value;
    private FloatNode(float value) {
        this.value = value;
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return Float.toString(value).replace("NaN", "null").replace("-Infinity", "null").replace("Infinity", "null");
    }

    @Contract(pure = true)
    @Override
    public boolean isFloat() {
        return true;
    }

    @Contract(pure = true)
    @Override
    public boolean isNumber() {
        return true;
    }

    @Contract(pure = true)
    @Override
    public float floatValue() {
        return value;
    }

    @Contract(pure = true)
    @Override
    public int intValue() {
        return (int) value;
    }

    @Contract(pure = true)
    public static @NotNull FloatNode of(float value) {
        return new FloatNode(value);
    }

    @Contract(pure = true)
    public static @NotNull FloatNode translate(@NotNull @NonNull ASTNode node) {
        if (!Objects.equals(node.type, "float")) throw new InvalidSemanticsException("FloatNode", node);
        return new FloatNode(Float.parseFloat(((TerminalNode)node).text));
    }
}
