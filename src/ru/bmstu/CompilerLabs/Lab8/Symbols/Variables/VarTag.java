package ru.bmstu.CompilerLabs.Lab8.Symbols.Variables;

import ru.bmstu.CompilerLabs.Lab8.Symbols.SymbolType;

public enum VarTag implements SymbolType {
    S,
    RULES,
    RULES1,
    RULE,
    PRODUCTS,
    PRODUCTS1,
    PRODUCT,
    SYMBOLS,
    SYMBOL,
    ENTITIES,
    ENTITIES1,
    ENTITY,
    ALT,
    ALTS,
    REPEAT,
    REPEATS,
    REPEATSYMBOLS,
    REPEATSYMBOL,
    CONTAINER;

    @Override
    public boolean isTokenTag() {
        return false;
    }
}
