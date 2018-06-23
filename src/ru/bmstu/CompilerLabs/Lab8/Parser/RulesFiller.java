package ru.bmstu.CompilerLabs.Lab8.Parser;

import ru.bmstu.CompilerLabs.Lab8.Symbols.AltContainer;
import ru.bmstu.CompilerLabs.Lab8.Symbols.Symbol;
import ru.bmstu.CompilerLabs.Lab8.Symbols.Tokens.*;
import ru.bmstu.CompilerLabs.Lab8.Symbols.RepeatContainer;
import ru.bmstu.CompilerLabs.Lab8.Symbols.Variables.*;

import java.util.ArrayList;
import java.util.HashMap;

public class RulesFiller {
    private HashMap<NonTermToken, ArrayList<ArrayList<Symbol>>> rules;
    private ArrayList<NonTermToken> nonterminals;
    private ArrayList<TermToken> terminals;
    private ArrayList<Symbol> eps;
    private boolean isInFirstSymbols;

    public RulesFiller() {
        rules = new HashMap<>();
        nonterminals = new ArrayList<>();
        terminals = new ArrayList<>();
        eps = new ArrayList<>();
        eps.add(new EpsToken());
        isInFirstSymbols = true;
    }

    //S             ::= RULE+
    public void makeRules1(SVar s) {
        for (Symbol rule: s.getSymbols())
            RULE((RuleVar) rule);
    }

    //RULE          ::= <NONTERMINAL PRODUCT+>
    private void RULE(RuleVar rule) {
        NonTermToken nt = (NonTermToken) rule.get(1);

        ArrayList<ArrayList<Symbol>> products = new ArrayList<>();

        for (int i = 2; i < rule.getSymbols().size() - 1; i++)
            products.add(PRODUCT((ProductVar) rule.get(i)));

        put(nt, products);
    }

    //PRODUCT       ::= <ENTITY+>
    private ArrayList<Symbol> PRODUCT(ProductVar product) {
        ArrayList<Symbol> entities = new ArrayList<>();
        for (int i = 1; i < product.getSymbols().size() - 1; i++)
            entities.add(ENTITY((EntityVar) product.get(i)));

        return entities;
    }

    //ENTITY        ::= SYMBOL | <PRODUCT+> | { ENTITY+ }
    private Symbol ENTITY(EntityVar entity) {
        if (entity.get(0).getTag() == TokenTag.LBRACKET) {
            AltContainer container = new AltContainer();
            for (int i = 1; i < entity.getSymbols().size() - 1; i++)
                container.addElement(PRODUCT((ProductVar) entity.get(i)));

            return container;
        }
        else if (entity.get(0).getTag() == TokenTag.LBRACE) {
            RepeatContainer container = new RepeatContainer();
            for (int i = 1; i < entity.getSymbols().size() - 1; i++)
                container.addElement(ENTITY((EntityVar) entity.get(i)));

            return container;
        }
        else
            return SYMBOL((SymbolVar) entity.get(0));
    }

    //SYMBOL        ::= NONTERMINAL | TERMINAL
    private Symbol SYMBOL(SymbolVar symbol) {
        return symbol.get(0);
    }

    private void put(NonTermToken nt, ArrayList<ArrayList<Symbol>> products) {
        int index = nonterminals.indexOf(nt);

        if (index == -1) {
            nonterminals.add(nt);
            rules.put(nt, products);
        } else {
            rules.get(nonterminals.get(index)).addAll(products);
        }
    }

    public HashMap<NonTermToken, ArrayList<ArrayList<Symbol>>> getRules() {
        return rules;
    }
}
