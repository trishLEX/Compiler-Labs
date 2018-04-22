package ru.bmstu.CompilerLabs.Lab7;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Symbol;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.EpsToken;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.NonTermToken;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.Token;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.TokenTag;

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

    private HashMap<NonTermToken, ArrayList<ArrayList<Symbol>>> rules = new HashMap<>();

    public void fill(Token s) {
        // *<* T <P> <P> >
        System.out.print(s.getTag() + " ");
        if (s.getTag() == TokenTag.LBRACKET && !isFirstLBracket) {
            isFirstLBracket = true;

        //< *T* <P><P> >
        } else if (!isFilling && isFirstLBracket && s.getTag() == TokenTag.NONTERMINAL) {
            if (isFirstLine) {
                axiomValue = s.getValue().toString();
                isFirstLine = false;
            } else {
                if (s.getValue().equals(axiomValue)) {
                    System.out.println("AAAAAAAAAAAAAAAAAAAA " + s);
                    ((NonTermToken) s).setAxiom(true);
                }
                isFillingBracket = true;
                current = (NonTermToken) s;



                boolean isContained = false;
                for (NonTermToken t: rules.keySet()) {
//                    System.out.println("CURRENT: " + current + " " + current.getValue());
//                    System.out.println("T: " + t + " " + t.getValue());
//                    System.out.println(t + " " + current + " " + (t.getValue().equals(s.getValue())));
//                    String tStr = t.getValue();
//                    String curStr = current.getValue();
//                    System.out.println("DEBUG: " + (tStr.equals(curStr)));
                    if (t.getValue().equals(s.getValue())) {
                        isContained = true;
                        current = t;
                        break;
                    }
                }

                if (!isContained)
                    rules.put(current, new ArrayList<>());
            }

        //< T *<*P><P> >
        } else if (isFillingBracket && s.getTag() == TokenTag.LBRACKET) {
            currentProdIndex++;
            rules.get(current).add(new ArrayList<>());
            isFilling = true;

        //<T <P*>*<P> >
        } else if (isFilling && s.getTag() == TokenTag.RBRACKET) {
            ArrayList<Symbol> list = rules.get(current).get(currentProdIndex);
            if (list.isEmpty())
                list.add(new EpsToken());

            current.addProductions(rules.get(current).get(currentProdIndex));
            isFilling = false;

        //< T <*P*><P> >
        } else if (isFilling) {
            ArrayList<ArrayList<Symbol>> productions = rules.get(current);

            boolean isContained = false;
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
                productions.get(currentProdIndex).add(s);

                if (s.getTag() == TokenTag.NONTERMINAL)
                    rules.put((NonTermToken) s, new ArrayList<>());
            }

            if (s.getValue().equals(axiomValue)) {
                System.out.println("AAAAAAAAAAAAAAAAAAAA " + s);
                ((NonTermToken) s).setAxiom(true);
            }

        //<T <P><P> *>*
        } else if (!isFilling && s.getTag() == TokenTag.RBRACKET) {
            isFirstLBracket = false;
            isFillingBracket = false;
            isFilling = false;

            currentProdIndex = -1;
        }
    }

    public HashMap<NonTermToken, ArrayList<ArrayList<Symbol>>> getRules() {
        return rules;
    }
}
