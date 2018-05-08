package ru.bmstu.CompilerLabs.Lab8.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab8.Service.Position;

import java.util.ArrayList;

public class NonTermToken extends SymbolToken<String> {
    private ArrayList<ArrayList<SymbolToken>> productions;
    private ArrayList<SymbolToken> follow;

    public NonTermToken(String value, Position start, Position follow) {
        super(TokenTag.NONTERMINAL, start, follow, value);
        this.productions = new ArrayList<>();
        this.follow = new ArrayList<>();
    }

    public NonTermToken() {
        super(TokenTag.NONTERMINAL);
        this.follow = new ArrayList<>();
        this.productions = new ArrayList<>();
    }
}
