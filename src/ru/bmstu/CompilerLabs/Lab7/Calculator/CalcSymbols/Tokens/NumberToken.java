package ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Tokens;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Position;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.Token;

public class NumberToken extends Token<Integer> {
    public NumberToken(int value, Position start, Position follow) {
        super(TokenTag.NUMBER, start, follow, value);
    }

    public NumberToken() {
        super(TokenTag.NUMBER);
    }
}
