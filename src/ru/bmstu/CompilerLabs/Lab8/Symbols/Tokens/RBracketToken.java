package ru.bmstu.CompilerLabs.Lab8.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab8.Service.Position;

public class RBracketToken extends Token<Character> {
    public RBracketToken (Position start, Position follow) {
        super(TokenTag.RBRACKET, start, follow, '>');
    }

    public RBracketToken() {
        super(TokenTag.RBRACKET, '>');
    }
}
