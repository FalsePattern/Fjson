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
        val sb = new StringBuilder();
        val len = text.length();
        for (int i = 0; i < len; i++) {
            char c = text.charAt(i);
            if (c == '\\') {
                if (++i < len) {
                    char next = text.charAt(i);
                    switch (next) {
                        case '"':
                            sb.append('"');
                            break;
                        case '\\':
                            sb.append('\\');
                            break;
                        case '/':
                            sb.append('/');
                            break;
                        case 't':
                            sb.append('\t');
                            break;
                        case 'b':
                            sb.append('\b');
                            break;
                        case 'n':
                            sb.append('\n');
                            break;
                        case 'r':
                            sb.append('\r');
                            break;
                        case 'f':
                            sb.append('\f');
                            break;
                        case 'u':
                            if (i + 4 < len) {
                                String hex = text.substring(i + 1, i + 5);
                                try {
                                    sb.append((char)Integer.parseInt(hex, 16));
                                } catch (NumberFormatException e) {
                                    throw new IllegalArgumentException("Invalid unicode escape sequence: \\u" + hex);
                                }
                                i += 4;
                            } else {
                                throw new IllegalArgumentException("Incomplete unicode escape sequence: \\u" + text.substring(i));
                            }
                    }
                } else {
                    throw new IllegalArgumentException("Incomplete escape sequence: \\");
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    static @NotNull String stringify(@NotNull @NonNull String text) {
        val sb = new StringBuilder();
        val len = text.length();
        for (int i = 0; i < len; i++) {
            char c = text.charAt(i);
            switch (c) {
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\r");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\"':
                    sb.append("\\\"");
                    break;
                default:
                    if (c < 32) {
                        sb.append("\\u").append(String.format("%04x", (int)c));
                    } else {
                        sb.append(c);
                    }
            }

        }
        return sb.toString();
    }
}
