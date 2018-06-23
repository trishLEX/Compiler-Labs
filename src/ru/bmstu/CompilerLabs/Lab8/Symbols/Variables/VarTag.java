package ru.bmstu.CompilerLabs.Lab8.Symbols.Variables;

import ru.bmstu.CompilerLabs.Lab8.Symbols.SymbolType;

public enum VarTag implements SymbolType {
    S,
    RULE,
    PRODUCT,
    SYMBOL,
    ENTITY,
    REPEATCONTAINER,
    ALTCONTAINER;

    @Override
    public boolean isTokenTag() {
        return false;
    }
}
