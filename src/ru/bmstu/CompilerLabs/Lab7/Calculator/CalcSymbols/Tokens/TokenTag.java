package ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Tokens;

import ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.SymbolType;

public enum TokenTag implements SymbolType {
    ADD_TOKEN,
    MUL_TOKEN,
    LPAREN,
    RPAREN,
    EPSILON,
    END_OF_PROGRAM,
    NUMBER
}
