package ru.bmstu.CompilerLabs.Lab7.Symbols;

import java.util.ArrayList;

public abstract class Symbol<T> {
    private SymbolType type;
    private T value;
    private Fragment coords;
    private ArrayList<Symbol> symbols;

}
