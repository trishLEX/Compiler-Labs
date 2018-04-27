package ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Vars;

import ru.bmstu.CompilerLabs.Lab7.Symbols.SymbolType;

public enum  VarTag implements SymbolType {
    E,
    T,
    E1,
    T1,
    F;

    @Override
    public boolean isTokenTag() {
        return false;
    }
}
