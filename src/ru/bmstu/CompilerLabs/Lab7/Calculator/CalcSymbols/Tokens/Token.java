package ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Tokens;

import ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Symbol;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Fragment;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Position;

public abstract class Token<T> extends Symbol {
    private Fragment coords;
    private T value;

    protected Token(TokenTag tag, Position start, Position follow, T value) {
        this.setTag(tag);
        this.coords = new Fragment(start, follow);
        this.value = value;
    }

    protected Token(TokenTag tag) {
        this.setTag(tag);
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
        return getTag() + " " + coords + ": " + value;
    }
}
