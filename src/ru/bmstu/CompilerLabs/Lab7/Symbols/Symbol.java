package ru.bmstu.CompilerLabs.Lab7.Symbols;

import java.util.ArrayList;

public abstract class Symbol {
    private SymbolType tag;
    //private T value;
    //private Fragment coords;
    //private ArrayList<Symbol> symbols;


    public SymbolType getTag() {
        return tag;
    }
}
