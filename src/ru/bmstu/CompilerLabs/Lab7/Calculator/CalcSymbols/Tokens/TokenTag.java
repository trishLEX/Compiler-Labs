package ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Tokens;

import ru.bmstu.CompilerLabs.Lab7.Symbols.SymbolType;

public enum TokenTag implements SymbolType {
    ADD_TOKEN,
    MUL_TOKEN,
    LPAREN,
    RPAREN,
    EPSILON,
    END_OF_PROGRAM,
    NUMBER;

    @Override
    public boolean isTokenTag() {
        return true;
    }
}
