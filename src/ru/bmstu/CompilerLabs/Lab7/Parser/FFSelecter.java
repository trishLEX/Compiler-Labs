package ru.bmstu.CompilerLabs.Lab7.Parser;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.NonTermToken;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.SymbolToken;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.TokenTag;

import java.util.ArrayList;
import java.util.HashMap;

public class FFSelecter {
    private HashMap<NonTermToken, ArrayList<ArrayList<SymbolToken>>> rules;

    public FFSelecter(HashMap<NonTermToken, ArrayList<ArrayList<SymbolToken>>> rules) {
        this.rules = rules;
    }


    private ArrayList<SymbolToken> getFirst(SymbolToken s) {
        ArrayList<SymbolToken> res = new ArrayList<>();
        if (s.getTag() == TokenTag.TERMINAL || s.getTag() == TokenTag.EPSILON) {
            res.add(s);
            return res;
        } else {
            for (ArrayList<SymbolToken> symbols: rules.get(s))
                res.addAll(getFirst(symbols.get(0)));

            return res;
        }
    }

    public void selectFIRST(SymbolToken s) {
        for (ArrayList<SymbolToken> symbols: rules.get(s)) {
            if (symbols.get(0).getTag() == TokenTag.TERMINAL) {
                s.addFirst(symbols.get(0));
            } else {
                s.addFirstAll(getFirst(symbols.get(0)));
            }
        }
    }

    public void selectFOLLOW() {
        for (NonTermToken x: rules.keySet()) {
            for (ArrayList<SymbolToken> rule: rules.get(x)) {
                for (int i = 1; i < rule.size() - 1; i++) {
                    if (rule.get(i).getTag() == TokenTag.NONTERMINAL) {
                        ((NonTermToken) rule.get(i)).addFollowAll(rule.get(i + 1).getFirstWithoutEps());
                    }
                }
            }
        }


        boolean isChanged = true;
        int count = getFollowCount();
        while (isChanged) {
            for (NonTermToken x: rules.keySet()) {
                for (ArrayList<SymbolToken> rule: rules.get(x)) {
                    for (int i = 1; i < rule.size() - 1; i++) {
                        if (rule.get(i).getTag() == TokenTag.NONTERMINAL && rule.get(i + 1).isEpsIn()) {
                            ((NonTermToken)rule.get(i)).addFollowAll(x.getFollow());
                        }
                    }

                    if (rule.get(rule.size() - 1).getTag() == TokenTag.NONTERMINAL) {
                        ((NonTermToken)rule.get(rule.size() - 1)).addFollowAll(x.getFollow());
                    }
                }
            }

            int oldCount = count;
            count = getFollowCount();
            if (oldCount == count)
                isChanged = false;
        }
    }

    private int getFollowCount() {
        int sum = 0;
        for (NonTermToken x: rules.keySet())
            sum += x.getFollow().size();

        return sum;
    }
}
