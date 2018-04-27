package ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Fragment;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Position;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Symbol;
import ru.bmstu.CompilerLabs.Lab7.Symbols.SymbolType;

public abstract class Token<T> extends Symbol{
    private SymbolType tag;
    private Fragment coords;
    private T value;

    protected Token(SymbolType tag, Position start, Position follow, T value) {
        this.tag = tag;
        this.coords = new Fragment(start, follow);
        this.value = value;
    }

    protected Token(SymbolType tag) {
        this.tag = tag;
    }

    public SymbolType getTag() {
        return tag;
    }

    public Fragment getCoords() {
        return coords;
    }

    public void setToken(Fragment coords, T value) {
        this.coords = coords;
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return tag + " " + coords + ": " + value;
    }

//    @Override
//    public boolean equals(Object obj) {
//        return this.value.equals(((Token) obj).value);
//    }
}

