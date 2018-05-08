package ru.bmstu.CompilerLabs.Lab8.Symbols;

public abstract class Symbol {
    private SymbolType tag;

    public SymbolType getTag() {
        return tag;
    }

    public void setTag(SymbolType tag) {
        this.tag = tag;
    }
}
