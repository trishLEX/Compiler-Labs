package ru.bmstu.CompilerLabs.Lab8.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab8.Service.Position;
import ru.bmstu.CompilerLabs.Lab8.Symbols.Symbol;

import java.util.ArrayList;

public abstract class SymbolToken<T> extends Token<T> {
    private ArrayList<Symbol> first;

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

    public void addFirst(Symbol t) {
        this.first.add(t);
    }

    public void addFirstAll(ArrayList<Symbol> tokens) {
        for (Symbol s: tokens)
            if ((first.contains(s) || (tokens.size() > 1) ) && s.getTag() == TokenTag.EPSILON)
                tokens.remove(s);

        first.addAll(tokens);
    }

    public ArrayList<Symbol> getFirst() {
        return first;
    }
}
