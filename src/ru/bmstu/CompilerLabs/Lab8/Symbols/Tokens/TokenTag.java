package ru.bmstu.CompilerLabs.Lab8.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab8.Symbols.SymbolType;

public enum TokenTag implements SymbolType {
    NONTERMINAL,
    TERMINAL,
    LBRACKET,
    RBRACKET,
    LBRACE,
    RBRACE,
    //LPAREN,
    //RPAREN,
    //OPERATION,
    EPSILON,
    END_OF_PROGRAM;

    @Override
    public boolean isTokenTag() {
        return true;
    }
}
