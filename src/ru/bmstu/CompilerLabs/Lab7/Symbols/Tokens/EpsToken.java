package ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens;

import java.util.ArrayList;

public class EpsToken extends SymbolToken{
    public EpsToken() {
        super(TokenTag.EPSILON);
        this.addFirst(this);
    }
}
