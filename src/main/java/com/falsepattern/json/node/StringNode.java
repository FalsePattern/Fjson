package com.falsepattern.json.node;

import com.falsepattern.json.parsing.ASTNode;
import com.falsepattern.json.parsing.TerminalNode;
import lombok.NonNull;
import lombok.val;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Objects;

@Unmodifiable
public class StringNode extends JsonNode{
    private final String value;
    private static final StringNode EMPTY_STRING = new StringNode("");
    private StringNode(String value) {
        this.value = value;
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        //return escaped text wrapped in double quotes
        return "\"" + stringify(value) + "\"";
    }

    @Contract(pure = true)
    @Override
    public @NotNull String stringValue() {
        return value;
    }

    @Contract(pure = true)
    @Override
    public boolean isString() {
        return true;
    }

    public static @NotNull StringNode of(@NotNull @NonNull String value) {
        if (value.isEmpty()) {
            return EMPTY_STRING;
        }
        return new StringNode(value);
    }

    public static @NotNull StringNode translate(@NotNull @NonNull ASTNode node) {
        if (!Objects.equals(node.type, "string")) throw new InvalidSemanticsException("StringNode", node);
        val text = ((TerminalNode)node).text;
        return new StringNode(deStringify(text.substring(1, text.length() - 1)));
    }

    static @NotNull String deStringify(@NotNull @NonNull String text) {
        return text.replace("\\\\", "\\")
                   .replace("\\t", "\t")
                   .replace("\\b", "\b")
                   .replace("\\n", "\n")
                   .replace("\\r", "\r")
                   .replace("\\f", "\f")
                   .replace("\\\"", "\"");
    }

    static @NotNull String stringify(@NotNull @NonNull String s) {
        return s.replace("\\", "\\\\")
                .replace("\t", "\\t")
                .replace("\b", "\\b")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\f", "\\f")
                .replace("\"", "\\\"");
    }
}
