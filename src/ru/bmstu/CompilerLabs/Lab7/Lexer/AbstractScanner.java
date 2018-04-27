package ru.bmstu.CompilerLabs.Lab7.Lexer;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Symbol;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.Token;

public abstract class AbstractScanner {
    public abstract Token nextToken() throws CloneNotSupportedException;
}
