package ru.bmstu.CompilerLabs.Lab8.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab8.Service.Position;

import java.util.ArrayList;

public abstract class SymbolToken<T> extends Token<T> {
    private ArrayList<SymbolToken> first;

    public SymbolToken(TokenTag tag, Position start, Position follow, T value) {
        super(tag, start, follow, value);
        this.first = new ArrayList<>();
    }

    public SymbolToken(TokenTag tag) {
        super(tag);
        this.first = new ArrayList<>();
    }

    public SymbolToken(TokenTag tag, T value) {
        super(tag, value);
        this.first = new ArrayList<>();
    }

    public void addFirst(SymbolToken t) {
        this.first.add(t);
    }
}
