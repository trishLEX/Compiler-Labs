package ru.bmstu.CompilerLabs.Lab8.Parser;

import ru.bmstu.CompilerLabs.Lab8.Symbols.AltContainer;
import ru.bmstu.CompilerLabs.Lab8.Symbols.Symbol;
import ru.bmstu.CompilerLabs.Lab8.Symbols.Tokens.EpsToken;
import ru.bmstu.CompilerLabs.Lab8.Symbols.Tokens.NonTermToken;
import ru.bmstu.CompilerLabs.Lab8.Symbols.RepeatContainer;
import ru.bmstu.CompilerLabs.Lab8.Symbols.Variables.*;

import java.util.ArrayList;
import java.util.HashMap;

public class RulesFiller {
    private HashMap<NonTermToken, ArrayList<ArrayList<Symbol>>> rules;
    private boolean isInFirstSymbols;

    public RulesFiller() {
        rules = new HashMap<>();
        isInFirstSymbols = true;
    }

    public void makeRules1(SVar s) {
        RULES((RulesVar) s.get(0));
    }

    private void RULES(RulesVar rules) {
        RULE((RuleVar) rules.get(0));
        RULES1((Rules1Var) rules.get(1));
    }

    private void RULES1(Rules1Var rules1) {
        if (rules1.getSymbols().size() > 0) {
            RULE((RuleVar) rules1.get(0));
            RULES1((Rules1Var) rules1.get(1));
        }
    }

    private void RULE(RuleVar rule) {
        NonTermToken nt = (NonTermToken) rule.get(1);

        ArrayList<ArrayList<Symbol>> products = PRODUCTS((ProductsVar)rule.get(2));

        rules.put(nt, products);
        isInFirstSymbols = true;
    }

    private ArrayList<ArrayList<Symbol>> PRODUCTS(ProductsVar products) {
        ArrayList<ArrayList<Symbol>> prods = new ArrayList<>();
        prods.add(PRODUCT((ProductVar) products.get(0)));
        prods.addAll(PRODUCTS1((Products1Var) products.get(1)));
        return prods;
    }

    private ArrayList<ArrayList<Symbol>> PRODUCTS1(Products1Var products1) {
        if (products1.getSymbols().size() > 0) {
            ArrayList<ArrayList<Symbol>> prods = new ArrayList<>();
            prods.add(PRODUCT((ProductVar) products1.get(0)));
            prods.addAll(PRODUCTS1((Products1Var) products1.get(1)));
            return prods;
        } else
            return new ArrayList<>();
    }

    private ArrayList<Symbol> PRODUCT(ProductVar product) {
        return ENTITIES((EntitiesVar) product.get(1));
    }

    private ArrayList<Symbol> ENTITIES(EntitiesVar entities) {
        ArrayList<Symbol> symbols = new ArrayList<>();
        symbols.addAll(ENTITY((EntityVar) entities.get(0)));
        symbols.addAll(ENTITIES1((Entities1Var) entities.get(1)));
        return symbols;
    }

    private ArrayList<Symbol> ENTITIES1(Entities1Var entities1) {
        if (entities1.getSymbols().size() > 0) {
            ArrayList<Symbol> symbols = new ArrayList<>();
            symbols.addAll(ENTITY((EntityVar) entities1.get(0)));
            symbols.addAll(ENTITIES1((Entities1Var) entities1.get(1)));
            return symbols;
        } else
            return new ArrayList<>();
    }

    private ArrayList<Symbol> ENTITY(EntityVar entity) {
        if (entity.get(0).getTag() == VarTag.SYMBOLS) {
            return SYMBOLS((SymbolsVar) entity.get(0));
        } else if (entity.get(0).getTag() == VarTag.ALTS) {
            return ALTS((AltsVar) entity.get(0));
        } else {
            return REPEATS((RepeatsVar) entity.get(0));
        }
    }

    private ArrayList<Symbol> ALTS(AltsVar alts) {
        if (alts.getSymbols().size() > 0) {
            ArrayList<Symbol> altsArray = new ArrayList<>();
            altsArray.add(ALT((AltVar) alts.get(0)));
            altsArray.addAll(ALTS((AltsVar) alts.get(1)));
            return altsArray;
        } else {
            return new ArrayList<>();
        }
    }

    private AltContainer ALT(AltVar alt) {
        AltContainer container = new AltContainer();
        container.addAllElements(PRODUCTS((ProductsVar) alt.get(1)));
        return container;
    }

    private ArrayList<Symbol> REPEATS(RepeatsVar repeats) {
        if (repeats.getSymbols().size() > 0) {
            ArrayList<Symbol> repeatsArray = new ArrayList<>();
            repeatsArray.add(REPEAT((RepeatVar) repeats.get(0)));
            repeatsArray.addAll(REPEATS((RepeatsVar) repeats.get(1)));
            return repeatsArray;
        } else {
            return new ArrayList<>();
        }
    }

    private RepeatContainer REPEAT(RepeatVar repeat) {
        RepeatContainer container = new RepeatContainer();
        container.addAllElements(REPEATSYMBOLS((RepeatSymbolsVar) repeat.get(1)));
        return container;
    }

    private ArrayList<ArrayList<Symbol>> REPEATSYMBOLS(RepeatSymbolsVar repeatSymbols) {
        if (repeatSymbols.getSymbols().size() > 0) {
            ArrayList<ArrayList<Symbol>> repeatSymbolsArray = new ArrayList<>();
            repeatSymbolsArray.add(REPEATSYMBOL((RepeatSymbolVar) repeatSymbols.get(0)));
            repeatSymbolsArray.addAll(REPEATSYMBOLS((RepeatSymbolsVar) repeatSymbols.get(1)));
            return repeatSymbolsArray;
        } else
            return new ArrayList<>();
    }

    private ArrayList<Symbol> REPEATSYMBOL(RepeatSymbolVar repeatSymbol) {
        return ENTITY((EntityVar) repeatSymbol.get(0));
    }

    private ArrayList<Symbol> SYMBOLS(SymbolsVar symbols) {
        if (symbols.getSymbols().size() > 0) {
            ArrayList<Symbol> symbolsArray = new ArrayList<>();
            isInFirstSymbols = false;
            symbolsArray.add(SYMBOL((SymbolVar) symbols.get(0)));
            symbolsArray.addAll(SYMBOLS((SymbolsVar) symbols.get(1)));
            return symbolsArray;
        } else {
            if (isInFirstSymbols) {
                ArrayList<Symbol> eps = new ArrayList<>();
                eps.add(new EpsToken());
                isInFirstSymbols = false;
                return eps;
            } else {
                isInFirstSymbols = false;
                return new ArrayList<>();
            }
        }
    }

    private Symbol SYMBOL(SymbolVar symbol) {
        return symbol.get(0);
    }
}
