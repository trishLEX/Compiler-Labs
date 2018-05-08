package ru.bmstu.CompilerLabs.Lab8.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab8.Service.Fragment;
import ru.bmstu.CompilerLabs.Lab8.Service.Position;
import ru.bmstu.CompilerLabs.Lab8.Symbols.Symbol;

public abstract class Token<T> extends Symbol {
    private Fragment coords;
    private T value;

    public Token(TokenTag tag, Position start, Position follow, T value) {
        this.setTag(tag);
        this.coords = new Fragment(start, follow);
        this.value = value;
    }

    public Token(TokenTag tag) {
        this.setTag(tag);
    }

    public Token(TokenTag tag, T value) {
        this.setTag(tag);
        this.value = value;
    }

//    @Override
//    public String toString() {
//        return this.getTag() + " " + coords + ": " + value;
//    }

    @Override
    public String toString() {
        return value.toString();
    }
}
