package ru.bmstu.CompilerLabs.Lab8.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab8.Service.Position;

public class RBraceToken extends Token<Character> {
    public RBraceToken(Position start, Position follow) {
        super(TokenTag.RBRACE, start, follow, '}');
    }

    public RBraceToken() {
        super(TokenTag.RBRACE, '}');
    }
}
