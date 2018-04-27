package ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Tokens;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Position;

public class MulToken extends Token<Character> {
    public MulToken(char sign, Position start, Position follow) {
        super(TokenTag.MUL_TOKEN, start, follow, sign);
    }

    public MulToken() {
        super(TokenTag.MUL_TOKEN);
    }
}
