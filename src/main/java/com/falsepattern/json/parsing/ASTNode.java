package com.falsepattern.json.parsing;

import com.falsepattern.json.parsing.token.Token;
import lombok.val;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ASTNode {
    private final List<ASTNode> children = new ArrayList<>();
    public final String type;
    public ASTNode(String type, ASTNode... initialChildren) {
        this.type = type;
        children.addAll(Arrays.asList(initialChildren));
    }

    public void addChild(ASTNode node) {
        children.add(node);
    }

    public void addChild(Token token) {
        children.add(new TerminalNode(token));
    }

    public List<ASTNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public String toString() {
        return toString(0);
    }

    public String toString(int indent) {
        val result = new StringBuilder();
        result.append(type).append(" {\n");
        children.forEach((child) -> result.append(child.toString(indent)));
        return result.append('}').toString().indent(indent);
    }

}
