package ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Fragment;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Position;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Symbol;

public abstract class Token<T> extends Symbol{
    private TokenTag tag;
    private Fragment coords;
    private T value;

    protected Token(TokenTag tag, Position start, Position follow, T value) {
        this.tag = tag;
        this.coords = new Fragment(start, follow);
        this.value = value;
    }

    protected Token(TokenTag tag) {
        this.tag = tag;
    }

    public TokenTag getTag() {
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
}

