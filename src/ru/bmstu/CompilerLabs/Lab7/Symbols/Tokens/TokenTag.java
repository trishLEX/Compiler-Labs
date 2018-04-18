package ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab7.Symbols.SymbolType;

public enum TokenTag implements SymbolType{
    IDENTIFIER,
    GENERAL_SYMBOL,
    LBRACKET,
    RBRACKET,
    KEYWORD,
    END_OF_PROGRAM
}
