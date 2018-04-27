package ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Position;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Symbol;

import java.util.ArrayList;

public class NonTermToken extends SymbolToken<String>{
    private ArrayList<ArrayList<SymbolToken>> productions;
    private boolean isAxiom = false;
    //private ArrayList<SymbolToken> first;
    private ArrayList<SymbolToken> follow;

    public NonTermToken(String value, Position start, Position follow) {
        super(TokenTag.NONTERMINAL, start, follow, value);
        this.productions = new ArrayList<>();
        this.isAxiom = false;
        //this.first = new ArrayList<>();
        this.follow = new ArrayList<>();
    }

    public NonTermToken() {
        super(TokenTag.NONTERMINAL);
        //this.first = new ArrayList<>();
        this.follow = new ArrayList<>();
        this.productions = new ArrayList<>();
    }

    public void addProductions(ArrayList<SymbolToken> prods) {
        this.productions.add(prods);
    }

    public void setAxiom(boolean flag, SymbolToken t) {
        //this.addFollow(new EndOfProgram(Position.dummy(), Position.dummy()));
        this.addFollow(t);
        this.isAxiom = flag;
    }

    public boolean isAxiom() {
        return this.isAxiom;
    }

    public boolean addFollowAll(ArrayList<SymbolToken> tokens) {
        ArrayList<SymbolToken> add = new ArrayList<>();

        for (SymbolToken t: tokens) {
            if (!follow.contains(t))
                add.add(t);
        }

        if (add.size() == 0)
            return false;
        else {
            follow.addAll(add);
            return true;
        }
    }

    public void addFollow(SymbolToken token) {
        this.follow.add(token);
    }

    public ArrayList<SymbolToken> getFollow() {
        return follow;
    }
}
