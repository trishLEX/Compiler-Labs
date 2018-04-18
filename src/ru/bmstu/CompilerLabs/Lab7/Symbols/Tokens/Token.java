package ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Fragment;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Position;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Symbol;

public abstract class Token<T> extends Symbol<T>{
    private DomainTag tag;
    private Fragment coords;
    private T value;

    protected Token(DomainTag tag, Position start, Position follow, T value) {
        this.tag = tag;
        this.coords = new Fragment(start, follow);
        this.value = value;
    }

    public DomainTag getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return tag + " " + coords + ": " + value;
    }
}

