package ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Tokens;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Position;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.Token;

public class LParenToken extends Token<Character> {
    public LParenToken(Position start, Position follow) {
        super(TokenTag.LPAREN, start, follow, '(');
    }

    public LParenToken() {
        super(TokenTag.LPAREN);
    }
}
