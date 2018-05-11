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
        //isInFirstSymbols = true;
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

//    private void RULES(RulesVar rules) {
//        RULE((RuleVar) rules.get(0));
//        RULES1((Rules1Var) rules.get(1));
//    }
//
//    private void RULES1(Rules1Var rules1) {
//        if (rules1.getSymbols().size() > 0) {
//            RULE((RuleVar) rules1.get(0));
//            RULES1((Rules1Var) rules1.get(1));
//        }
//    }
//
//    private void RULE(RuleVar rule) {
//        NonTermToken nt = (NonTermToken) rule.get(1);
//
//        ArrayList<ArrayList<Symbol>> products = PRODUCTS((ProductsVar)rule.get(2));
//
//        //rules.put(nt, products);
//        put(nt, products);
//        isInFirstSymbols = true;
//    }
//
//    private ArrayList<ArrayList<Symbol>> PRODUCTS(ProductsVar products) {
//        ArrayList<ArrayList<Symbol>> prods = new ArrayList<>();
//        prods.add(PRODUCT((ProductVar) products.get(0)));
//        prods.addAll(PRODUCTS1((Products1Var) products.get(1)));
//
//        return prods;
//    }
//
//    private ArrayList<ArrayList<Symbol>> PRODUCTS1(Products1Var products1) {
//        if (products1.getSymbols().size() > 0) {
//            ArrayList<ArrayList<Symbol>> prods = new ArrayList<>();
//            prods.add(PRODUCT((ProductVar) products1.get(0)));
//            prods.addAll(PRODUCTS1((Products1Var) products1.get(1)));
//
//            return prods;
//        } else
//            return new ArrayList<>();
//    }
//
//    private ArrayList<Symbol> PRODUCT(ProductVar product) {
//        return ENTITIES((EntitiesVar) product.get(1));
//    }
//
//    private ArrayList<Symbol> ENTITIES(EntitiesVar entities) {
//        ArrayList<Symbol> symbols = new ArrayList<>();
//        symbols.addAll(ENTITY((EntityVar) entities.get(0)));
//        symbols.addAll(ENTITIES1((Entities1Var) entities.get(1)));
//
//        return symbols;
//    }
//
//    private ArrayList<Symbol> ENTITIES1(Entities1Var entities1) {
//        if (entities1.getSymbols().size() > 0) {
//            ArrayList<Symbol> symbols = new ArrayList<>();
//            symbols.addAll(ENTITY((EntityVar) entities1.get(0)));
//            symbols.addAll(ENTITIES1((Entities1Var) entities1.get(1)));
//
//            return symbols;
//        } else
//            return new ArrayList<>();
//    }
//
//    private ArrayList<Symbol> ENTITY(EntityVar entity) {
//        if (entity.get(0).getTag() == VarTag.SYMBOLS) {
//            return SYMBOLS((SymbolsVar) entity.get(0));
//        } else if (entity.get(0).getTag() == VarTag.ALTS) {
//            return ALTS((AltsVar) entity.get(0));
//        } else {
//            return REPEATS((RepeatsVar) entity.get(0));
//        }
//    }
//
//    private ArrayList<Symbol> ALTS(AltsVar alts) {
//        if (alts.getSymbols().size() > 0) {
//            ArrayList<Symbol> altsArray = new ArrayList<>();
//            altsArray.add(ALT((AltVar) alts.get(0)));
//            altsArray.addAll(ALTS((AltsVar) alts.get(1)));
//
//            return altsArray;
//        } else {
//            return new ArrayList<>();
//        }
//    }
//
//    private AltContainer ALT(AltVar alt) {
//        AltContainer container = new AltContainer();
//        container.addAllElements(PRODUCTS((ProductsVar) alt.get(1)));
//
//        return container;
//    }
//
//    private ArrayList<Symbol> REPEATS(RepeatsVar repeats) {
//        if (repeats.getSymbols().size() > 0) {
//            ArrayList<Symbol> repeatsArray = new ArrayList<>();
//            repeatsArray.add(REPEAT((RepeatVar) repeats.get(0)));
//            repeatsArray.addAll(REPEATS((RepeatsVar) repeats.get(1)));
//
//            return repeatsArray;
//        } else {
//            return new ArrayList<>();
//        }
//    }
//
//    private RepeatContainer REPEAT(RepeatVar repeat) {
//        RepeatContainer container = new RepeatContainer();
//        container.addAllElements(REPEATSYMBOLS((RepeatSymbolsVar) repeat.get(1)));
//
//        return container;
//    }
//
//    private ArrayList<ArrayList<Symbol>> REPEATSYMBOLS(RepeatSymbolsVar repeatSymbols) {
//        if (repeatSymbols.getSymbols().size() > 0) {
//            ArrayList<ArrayList<Symbol>> repeatSymbolsArray = new ArrayList<>();
//            repeatSymbolsArray.add(REPEATSYMBOL((RepeatSymbolVar) repeatSymbols.get(0)));
//            repeatSymbolsArray.addAll(REPEATSYMBOLS((RepeatSymbolsVar) repeatSymbols.get(1)));
//
//            return repeatSymbolsArray;
//        } else
//            return new ArrayList<>();
//    }
//
//    private ArrayList<Symbol> REPEATSYMBOL(RepeatSymbolVar repeatSymbol) {
//        return ENTITY((EntityVar) repeatSymbol.get(0));
//    }
//
//    private ArrayList<Symbol> SYMBOLS(SymbolsVar symbols) {
//        if (symbols.getSymbols().size() > 0) {
//            ArrayList<Symbol> symbolsArray = new ArrayList<>();
//
//            isInFirstSymbols = false;
//
//            symbolsArray.add(SYMBOL((SymbolVar) symbols.get(0)));
//            symbolsArray.addAll(SYMBOLS((SymbolsVar) symbols.get(1)));
//
//            return symbolsArray;
//        } else {
//            if (isInFirstSymbols) {
//                isInFirstSymbols = false;
//
//                return eps;
//            } else {
//                isInFirstSymbols = false;
//
//                return new ArrayList<>();
//            }
//        }
//    }
//
//    private Symbol SYMBOL(SymbolVar symbol) {
//        if (symbol.get(0).getTag() == TokenTag.TERMINAL) {
//            TermToken term = (TermToken) symbol.get(0);
//            int index = terminals.indexOf(term);
//
//            if (index == -1) {
//                terminals.add(term);
//                return term;
//            } else {
//                return terminals.get(index);
//            }
//        } else {
//            NonTermToken nonterm = (NonTermToken) symbol.get(0);
//            int index = nonterminals.indexOf(nonterm);
//
//            if (index == -1) {
//                nonterminals.add(nonterm);
//                rules.put(nonterm, new ArrayList<>());
//
//                return nonterm;
//            } else {
//                return nonterminals.get(index);
//            }
//        }
//    }

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
