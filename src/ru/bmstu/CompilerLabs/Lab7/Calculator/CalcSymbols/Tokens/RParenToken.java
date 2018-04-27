package ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Tokens;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Position;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.Token;

public class RParenToken extends Token<Character> {
    public RParenToken(Position start, Position follow) {
        super(TokenTag.RPAREN, start, follow, ')');
    }

    public RParenToken() {
        super(TokenTag.RPAREN);
    }
}
