package ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Position;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Symbol;

import java.util.ArrayList;

public class NonTermToken extends Token<String>{
    private ArrayList<ArrayList<Symbol>> productions = new ArrayList<>();
    private boolean isAxiom = false;

    public NonTermToken(String value, Position start, Position follow) {
        super(TokenTag.NONTERMINAL, start, follow, value);
    }

    public NonTermToken() {
        super(TokenTag.NONTERMINAL);
    }

    public void addProductions(ArrayList<Symbol> prods) {
        this.productions.add(prods);
    }

    public void setAxiom(boolean flag) {
        this.addFollow(new EpsToken());
        this.isAxiom = flag;
    }

    public boolean isAxiom() {
        return this.isAxiom;
    }
}
