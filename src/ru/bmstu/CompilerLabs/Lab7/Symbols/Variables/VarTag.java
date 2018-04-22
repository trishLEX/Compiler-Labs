package ru.bmstu.CompilerLabs.Lab7.Symbols.Variables;

import ru.bmstu.CompilerLabs.Lab7.Symbols.SymbolType;

public enum VarTag implements SymbolType{
    S,
    AXIOM,
    RULES,
    RULES1,
    RULE,
    PRODUCTS,
    PRODUCTS1,
    PRODUCT,
    SYMBOLS,
    SYMBOL,
    EPSILON;

    @Override
    public boolean isTokenTag() {
        return false;
    }
}
