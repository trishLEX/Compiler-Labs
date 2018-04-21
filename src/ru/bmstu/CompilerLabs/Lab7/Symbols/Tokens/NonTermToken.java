package ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Position;

public class NonTermToken extends Token<String>{
    public NonTermToken(String value, Position start, Position follow) {
        super(TokenTag.NONTERMINAL, start, follow, value);
    }

    public NonTermToken() {
        super(TokenTag.NONTERMINAL);
    }
}
