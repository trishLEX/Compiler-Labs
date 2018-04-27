package ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Tokens;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Position;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.Token;

public class AddToken extends Token<Character> {
    public AddToken(char sign, Position start, Position follow) {
        super(TokenTag.ADD_TOKEN, start, follow, sign);
    }

    public AddToken() {
        super(TokenTag.ADD_TOKEN);
    }
}
