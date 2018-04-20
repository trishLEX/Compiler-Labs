package ru.bmstu.CompilerLabs.Lab7.Symbols;

import java.util.ArrayList;

public abstract class Symbol {
    private SymbolType tag;
    //private T value;
    //private Fragment coords;
    private ArrayList<Symbol> symbols = new ArrayList<>();

    public void addSymbols(ArrayList<Symbol> symbols) {
        this.symbols = symbols;
    }

    public ArrayList<Symbol> getSymbols() {
        return this.symbols;
    }

    public static void printTree(Symbol symbol, int offset) {
        System.out.println(symbol);
        for (Symbol s: symbol.symbols) {
            printMul(" ", offset);
            //System.out.println(symbol);
            printTree(s, offset + 1);
        }
    }

    private static void printMul(String s, int offset) {
        for (int i = 0; i < offset; i++)
            System.out.print(s);
    }

    public SymbolType getTag() {
        return tag;
    }
}
