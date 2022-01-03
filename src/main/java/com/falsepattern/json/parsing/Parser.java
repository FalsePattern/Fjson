package com.falsepattern.json.parsing;

import com.falsepattern.json.node.JsonNode;
import com.falsepattern.json.parsing.token.Token;
import com.falsepattern.json.parsing.token.Tokenizer;
import lombok.val;

public class Parser {
    private final Tokenizer tokenizer;
    public Parser(String text) {
        this.tokenizer = new Tokenizer(text);
    }

    public ASTNode term_string() throws InvalidSyntaxException {
        val token = tokenizer.popNextToken();
        if (token.tokenType != Token.Type.String) throw new InvalidSyntaxException("string", token);
        return new TerminalNode(token);
    }

    public ASTNode term_int() throws InvalidSyntaxException {
        val token = tokenizer.popNextToken();
        if (token.tokenType != Token.Type.Int) throw new InvalidSyntaxException("int", token);
        return new TerminalNode(token);
    }

    public ASTNode term_float() throws InvalidSyntaxException {
        val token = tokenizer.popNextToken();
        if (token.tokenType != Token.Type.Float) throw new InvalidSyntaxException("float", token);
        return new TerminalNode(token);

    }

    public ASTNode term_bool() throws InvalidSyntaxException {
        val token = tokenizer.popNextToken();
        if (token.tokenType != Token.Type.True && token.tokenType != Token.Type.False) throw new InvalidSyntaxException("'true','false'", token);
        return new TerminalNode(token);

    }

    public ASTNode term_null() throws InvalidSyntaxException {
        val token = tokenizer.popNextToken();
        if (token.tokenType != Token.Type.Null) throw new InvalidSyntaxException("'null'", token);
        return new TerminalNode(token);

    }

    public ASTNode value() throws InvalidSyntaxException {
        val token = tokenizer.peekNextToken();
        return switch (token.tokenType) {
            case String -> term_string();
            case Int -> term_int();
            case Float -> term_float();
            case True, False -> term_bool();
            case Null -> term_null();
            case LBrace -> obj();
            case LBracket -> arr();
            default -> throw new InvalidSyntaxException("string, int, float, 'true', 'false', 'null', '{', '['", token);
        };
    }

    public ASTNode obj() throws InvalidSyntaxException {
        val node = new ASTNode("obj");
        var token = tokenizer.popNextToken();
        if (token.tokenType != Token.Type.LBrace) throw new InvalidSyntaxException("'{'", token);
        token = tokenizer.peekNextToken();
        if (token.tokenType == Token.Type.RBrace) {
            return node;
        }
        while (true) {
            node.addChild(pair());
            token = tokenizer.popNextToken();
            if (token.tokenType == Token.Type.Comma) {
                continue;
            }
            if (token.tokenType == Token.Type.RBrace) {
                return node;
            }
            throw new InvalidSyntaxException("',', '}'", token);
        }
    }

    public ASTNode pair() throws InvalidSyntaxException {
        val node = new ASTNode("pair");
        var token = tokenizer.popNextToken();
        if (token.tokenType != Token.Type.String) throw new InvalidSyntaxException("string", token);
        node.addChild(token);
        token = tokenizer.popNextToken();
        if (token.tokenType != Token.Type.Colon) throw new InvalidSyntaxException("':'", token);
        node.addChild(value());
        return node;
    }

    public ASTNode arr() throws InvalidSyntaxException {
        val node = new ASTNode("arr");
        var token = tokenizer.popNextToken();
        if (token.tokenType != Token.Type.LBracket) throw new InvalidSyntaxException("'['", token);
        token = tokenizer.peekNextToken();
        if (token.tokenType == Token.Type.RBracket) {
            return node;
        }
        while (true) {
            node.addChild(value());
            token = tokenizer.popNextToken();
            if (token.tokenType == Token.Type.Comma) {
                continue;
            }
            if (token.tokenType == Token.Type.RBracket) {
                return node;
            }
            throw new InvalidSyntaxException("',', ']'", token);

        }
    }
}
