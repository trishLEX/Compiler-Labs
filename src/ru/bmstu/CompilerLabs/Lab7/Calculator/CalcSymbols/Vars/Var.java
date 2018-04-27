package ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Vars;

import ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Symbol;

import java.util.ArrayList;

public class Var extends Symbol {
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

    @Override
    public String toString() {
        return tag.toString();
    }
}
