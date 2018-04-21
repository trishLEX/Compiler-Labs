package ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab7.Symbols.SymbolType;

public enum TokenTag implements SymbolType{
    NONTERMINAL,
    TERMINAL,
    LBRACKET,
    RBRACKET,
    KEYWORD,
    END_OF_PROGRAM
}
