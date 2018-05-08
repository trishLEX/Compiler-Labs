package ru.bmstu.CompilerLabs.Lab8.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab8.Service.Position;

public class RParenToken extends SymbolToken<Character> {
    public RParenToken(Position start, Position follow) {
        super(TokenTag.RPAREN, start, follow, ')');
    }

    public RParenToken() {
        super(TokenTag.RPAREN, ')');
    }
}
