package com.falsepattern.json.node;

import com.falsepattern.json.parsing.ASTNode;
import com.falsepattern.json.parsing.TerminalNode;
import lombok.val;

import java.util.Objects;

public class StringNode extends JsonNode{
    private final String value;
    public StringNode(String value) {
        this.value = value;
    }

    @Override
    public String stringValue() {
        return value;
    }

    public static StringNode translate(ASTNode node) {
        if (!Objects.equals(node.type, "string")) throw new InvalidSemanticsException("StringNode", node);
        return new StringNode(deStringify(node));
    }

    static String deStringify(ASTNode node) {
        val txt = ((TerminalNode)node).text;
        return txt.substring(1, txt.length() - 1);
    }

    static String stringify(String s) {
        return s.replace("\\", "\\\\")
                .replace("\t", "\\t")
                .replace("\b", "\\b")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\f", "\\f")
                .replace("\"", "\\\"");
    }

    @Override
    public String toString() {
        //return escaped text wrapped in double quotes
        return "\"" + stringify(value) + "\"";
    }
}
