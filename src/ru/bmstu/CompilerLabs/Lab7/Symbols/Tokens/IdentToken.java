package ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Position;

public class IdentToken extends Token<String>{
    public IdentToken(String value, Position start, Position follow) {
        super(DomainTag.IDENTIFIER, start, follow, value);
    }
}
