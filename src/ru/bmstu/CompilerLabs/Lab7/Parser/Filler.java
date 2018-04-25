package ru.bmstu.CompilerLabs.Lab7.Parser;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Symbol;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Filler {
    private boolean isFirstLBracket = false;
    private boolean isFillingBracket = false;
    private boolean isFilling = false;
    private boolean isFirstLine = true;
    private String axiomValue = "";

    private NonTermToken current;
    private int currentProdIndex = -1;

    private HashMap<NonTermToken, ArrayList<ArrayList<SymbolToken>>> rules = new HashMap<>();
    private HashMap<SymbolToken, Integer> terminals = new HashMap<>();
    private HashMap<NonTermToken, Integer> nonterminals = new HashMap<>();
    private EpsToken epsilon = new EpsToken();
    private int termCount = 0;
    private int nonTermCount = 0;

    public Filler() {
        //epsilon.addFirst(epsilon);
        terminals.put(epsilon, termCount++);
    }

    public void fill(Token s) {
        // *<* T <P> <P> >
        //System.out.print(s.getTag() + " ");
        if (s.getTag() == TokenTag.LBRACKET && !isFirstLBracket) {
            isFirstLBracket = true;

        //< *T* <P><P> >
        } else if (!isFilling && isFirstLBracket && s.getTag() == TokenTag.NONTERMINAL) {
            if (isFirstLine) {
                axiomValue = s.getValue().toString();
                isFirstLine = false;
            } else {
                if (s.getValue().equals(axiomValue)) {
                    ((NonTermToken) s).setAxiom(true, epsilon);
                }
                isFillingBracket = true;
                current = (NonTermToken) s;

                boolean isContained = false;
                for (NonTermToken t: rules.keySet()) {
                    if (t.getValue().equals(s.getValue())) {
                        isContained = true;
                        current = t;
                        break;
                    }
                }

                if (!isContained) {
                    rules.put(current, new ArrayList<>());
                    nonterminals.put(current, nonTermCount++);
                }
            }

        //< T *<*P><P> >
        } else if (isFillingBracket && s.getTag() == TokenTag.LBRACKET) {
            currentProdIndex++;
            rules.get(current).add(new ArrayList<>());
            isFilling = true;

        //<T <P*>*<P> >
        } else if (isFilling && s.getTag() == TokenTag.RBRACKET) {
            ArrayList<SymbolToken> list = rules.get(current).get(currentProdIndex);
            if (list.isEmpty())
                list.add(epsilon);

            current.addProductions(rules.get(current).get(currentProdIndex));
            isFilling = false;

        //< T <*P*><P> >
        } else if (isFilling) {
            ArrayList<ArrayList<SymbolToken>> productions = rules.get(current);

            boolean isContained = false;
            TermToken terminal = null;
            if (s.getTag() == TokenTag.TERMINAL) {
                for (Symbol t: terminals.keySet()) {
                    if (t.getTag() != TokenTag.EPSILON && ((TermToken) t).getValue().equals(s.getValue())) {
                        isContained = true;
                        terminal = (TermToken) t;
                        break;
                    }
                }

                if (!isContained) {
                    terminals.put((TermToken) s, termCount++);
                    //s.addFirst(s);
                } else {
                    s = terminal;
                }
            }

            isContained = false;
            NonTermToken token = null;
            for (NonTermToken t: rules.keySet()) {
                if (t.getValue().equals(s.getValue())) {
                    isContained = true;
                    token = t;
                    break;
                }
            }

            if (isContained)
                productions.get(currentProdIndex).add(token);
            else {
                productions.get(currentProdIndex).add((SymbolToken) s);

                if (s.getTag() == TokenTag.NONTERMINAL) {
                    rules.put((NonTermToken) s, new ArrayList<>());
                    nonterminals.put((NonTermToken) s, nonTermCount++);
                }
            }

            if (s.getValue().equals(axiomValue)) {
                ((NonTermToken) s).setAxiom(true, epsilon);
            }

        //<T <P><P> *>*
        } else if (!isFilling && s.getTag() == TokenTag.RBRACKET) {
            isFirstLBracket = false;
            isFillingBracket = false;
            isFilling = false;

            currentProdIndex = -1;
        }
    }

    public HashMap<NonTermToken, ArrayList<ArrayList<SymbolToken>>> getRules() {
        return rules;
    }

    public HashMap<SymbolToken, Integer> getTable() {
        HashMap<SymbolToken, Integer> table = new HashMap<>();
        int nonTermCount = 0;
        for (NonTermToken t: rules.keySet()) {
            table.put(t, nonTermCount++);
        }

        for (SymbolToken t: terminals.keySet()) {
            table.put(t, terminals.get(t) + nonTermCount);
        }

        return table;
    }

    public HashMap<SymbolToken, Integer> getTerminals() {
        return terminals;
    }

    public HashMap<NonTermToken, Integer> getNonterminals() {
        return nonterminals;
    }
}
