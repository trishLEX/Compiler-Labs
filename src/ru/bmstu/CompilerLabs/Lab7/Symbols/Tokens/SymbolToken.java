package ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Position;

import java.util.ArrayList;

public abstract class SymbolToken<T> extends Token<T>{
    private ArrayList<SymbolToken> first;

    public SymbolToken(TokenTag tag, Position start, Position follow, T value) {
        super(tag, start, follow, value);
        this.first = new ArrayList<>();
    }

    public SymbolToken(TokenTag tag) {
        super(tag);
        this.first = new ArrayList<>();
    }

    public void addFirst(SymbolToken symbol) {
        if (first.contains(symbol))
            throw new RuntimeException("Not LL(1)");

        first.add(symbol);
    }

    public void addFirstAll(ArrayList<SymbolToken> tokens) {
        for (SymbolToken s: tokens)
            if (first.contains(s))
                throw new RuntimeException("Not LL(1)");

        first.addAll(tokens);
    }

    public ArrayList<SymbolToken> getFirst() {
        return first;
    }

    public ArrayList<SymbolToken> getFirstWithoutEps() {
        ArrayList<SymbolToken> res = new ArrayList<>();
        for (SymbolToken s: first) {
            if (s.getTag() != TokenTag.EPSILON)
                res.add(s);
        }

        return res;
    }

    public boolean isEpsIn() {
        for (SymbolToken s: first) {
            if (s.getTag() == TokenTag.EPSILON)
                return true;
        }

        return false;
    }
}
