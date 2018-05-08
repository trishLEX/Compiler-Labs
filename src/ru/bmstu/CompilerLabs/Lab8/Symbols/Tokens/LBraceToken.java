package ru.bmstu.CompilerLabs.Lab8.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab8.Service.Position;

public class LBraceToken extends Token<Character> {
    public LBraceToken(Position start, Position follow) {
        super(TokenTag.LBRACE, start, follow, '{');
    }

    public LBraceToken() {
        super(TokenTag.LBRACE, '{');
    }
}
