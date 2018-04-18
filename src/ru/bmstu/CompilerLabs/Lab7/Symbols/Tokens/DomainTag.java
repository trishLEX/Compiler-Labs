package ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab7.Symbols.SymbolType;

public enum DomainTag implements SymbolType{
    IDENTIFIER,
    LBRACKET,
    RBRACKET,
    GENERAL_SYMBOL,
    KEYWORD,
    END_OF_PROGRAM
}
