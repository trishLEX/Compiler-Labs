package ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Position;

import java.util.ArrayList;

public class TermToken extends SymbolToken<String> {
    public TermToken(String value, Position start, Position follow) {
        super(TokenTag.TERMINAL, start, follow, value);
        this.addFirst(this);
    }

    public TermToken() {
        super(TokenTag.TERMINAL);
        this.addFirst(this);
    }
}
