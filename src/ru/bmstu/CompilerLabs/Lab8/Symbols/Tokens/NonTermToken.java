package ru.bmstu.CompilerLabs.Lab8.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab8.Service.Position;

import java.util.ArrayList;

public class NonTermToken extends SymbolToken<String> {
    private ArrayList<ArrayList<SymbolToken>> productions;

    public NonTermToken(String value, Position start, Position follow) {
        super(TokenTag.NONTERMINAL, start, follow, value);
        this.productions = new ArrayList<>();
    }

    public NonTermToken() {
        super(TokenTag.NONTERMINAL);
        this.productions = new ArrayList<>();
    }
}
