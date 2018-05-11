package ru.bmstu.CompilerLabs.Lab8.Parser;

import ru.bmstu.CompilerLabs.Lab8.Symbols.AltContainer;
import ru.bmstu.CompilerLabs.Lab8.Symbols.RepeatContainer;
import ru.bmstu.CompilerLabs.Lab8.Symbols.Symbol;
import ru.bmstu.CompilerLabs.Lab8.Symbols.Tokens.*;
import ru.bmstu.CompilerLabs.Lab8.Symbols.Variables.VarTag;

import java.util.ArrayList;
import java.util.HashMap;

public class FirstSelector {
    private HashMap<NonTermToken, ArrayList<ArrayList<Symbol>>> rules;

    public FirstSelector(HashMap<NonTermToken, ArrayList<ArrayList<Symbol>>> rules) {
        this.rules = rules;
    }

    public void selectFirst() {
        for (NonTermToken nt: rules.keySet()) {
            for (ArrayList<Symbol> product: rules.get(nt)) {
                if (product.get(0).getTag() == TokenTag.TERMINAL)
                    nt.addFirst(product.get(0));
                else if (product.get(0).getTag() == TokenTag.EPSILON) {
                    nt.addFirst(product.get(0));
                    //nt.addFirstAll(getFirst(product.get(1)));
                } else if (product.get(0).getTag() == VarTag.ALTCONTAINER) {
                    for (ArrayList<Symbol> alt: ((AltContainer) product.get(0)).getElements())
                        nt.addFirstAll(getFirst(alt.get(0)));
                } else if (product.get(0).getTag() == VarTag.REPEATCONTAINER) {
                    nt.addFirstAll(getFirst(((RepeatContainer) product.get(0)).getElements().get(0)));
                    if (product.size() == 1)
                        nt.addFirst(new EpsToken()); //check
                    else
                        nt.addFirstAll(getFirst(product.get(1)));
                } else {
                    ArrayList<Symbol> first = getFirst(product.get(0));
                    for (Symbol s: first)
                        if (s.getTag() == TokenTag.EPSILON)
                            nt.addFirstAll(getFirst(product.get(1)));

                    nt.addFirstAll(getFirst(product.get(0)));
                }
            }
        }
    }

    private ArrayList<Symbol> getFirst(Symbol s) {
        ArrayList<Symbol> res = new ArrayList<>();
        if (s.getTag() == TokenTag.TERMINAL || s.getTag() == TokenTag.EPSILON) {
            res.add(s);
            return res;
        } else if (s.getTag() == VarTag.ALTCONTAINER) {
            for (ArrayList<Symbol> alt : ((AltContainer) s).getElements())
                res.addAll(getFirst(alt.get(0)));

            return res;
        } else if (s.getTag() == VarTag.REPEATCONTAINER) {
            res.add(new EpsToken());
            res.addAll(getFirst(((RepeatContainer) s).getElements().get(0)));

            return res;
        } else {
            for (ArrayList<Symbol> product: rules.get(s)) {
                if (product.get(0).getTag() == TokenTag.EPSILON) {
                    res.add(product.get(0));
                    res.addAll(getFirst(product.get(1)));
                } else
                    res.addAll(getFirst(product.get(0)));
            }

            return res;
        }
    }

    public void printFirst() {
        for (NonTermToken nt: rules.keySet()) {
            System.out.println(nt + " FIRST: " + nt.getFirst());
        }
    }
}
