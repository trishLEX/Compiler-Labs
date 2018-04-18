package ru.bmstu.CompilerLabs.Lab7.Symbols.Variables;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Symbol;

import java.util.ArrayList;

public abstract class Var extends Symbol {
    private VarTag tag;
    private ArrayList<Symbol> symbols;

    public Var(VarTag tag) {
        this.tag = tag;
        this.symbols = new ArrayList<>();
    }

    @Override
    public VarTag getTag() {
        return tag;
    }
}
