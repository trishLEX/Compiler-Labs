package ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Position;

public class GenSymbolToken extends Token<String> {
    public GenSymbolToken(String value, Position start, Position follow) {
        super(TokenTag.GENERAL_SYMBOL, start, follow, value);
    }
}
