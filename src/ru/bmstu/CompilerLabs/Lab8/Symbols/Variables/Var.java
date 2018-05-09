package ru.bmstu.CompilerLabs.Lab8.Symbols.Variables;

import ru.bmstu.CompilerLabs.Lab8.Symbols.Symbol;
import ru.bmstu.CompilerLabs.Lab8.Symbols.SymbolType;

import java.util.ArrayList;

public abstract class Var extends Symbol{
    private ArrayList<Symbol> symbols;

    public Var(VarTag tag) {
        this.setTag(tag);
        this.symbols = new ArrayList<>();
    }

    public void addSymbol(Symbol s) {
        this.symbols.add(s);
    }

    public ArrayList<Symbol> getSymbols() {
        return symbols;
    }

    public Symbol get(int i) {
        return symbols.get(i);
    }

    @Override
    public VarTag getTag() {
        return (VarTag) super.getTag();
    }

    @Override
    public String toString() {
        return getTag().toString();
    }

    public static void printTree(Symbol symbol) {
        System.out.println("[" + symbol);
        if (!symbol.getTag().isTokenTag()) {
            for (Symbol s : ((Var) symbol).symbols) {
                printTree(s);
            }
        }
        System.out.println("]");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || obj.getClass() != this.getClass())
            return false;

        Var other = (Var) obj;
        return this.getTag() == other.getTag();
    }

    @Override
    public int hashCode() {
        return getTag().hashCode();
    }
}
