package ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Position;

public class TermToken extends Token<String> {
    public TermToken(String value, Position start, Position follow) {
        super(TokenTag.TERMINAL, start, follow, value);
    }

    public TermToken() {
        super(TokenTag.TERMINAL);
    }
}
