package ru.bmstu.CompilerLabs.Lab7;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Symbol;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.NonTermToken;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.Token;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.TokenTag;

import java.util.ArrayList;
import java.util.HashMap;

public class FFSelecter {
    private HashMap<NonTermToken, ArrayList<ArrayList<Symbol>>> rules = new HashMap<>();

    public FFSelecter(HashMap<NonTermToken, ArrayList<ArrayList<Symbol>>> rules) {
        this.rules = rules;
    }

//    private ArrayList<Token> F(ArrayList<Symbol> symbols) {
//        if (symbols.get(0).getTag().isTokenTag()) {
//            ArrayList<Token> res = new ArrayList<>();
//            res.add((Token) symbols.get(0));
//            return res;
//        } else if (!symbols.get(0).getTag().isTokenTag()) {
//            if (symbols.get(0).isEpsIn()) {
//                ArrayList<Token> res = new ArrayList<>(symbols.get(0).getFirstWithoutEps());
//                ArrayList<Symbol> slice = new ArrayList<>();
//                for (int i = 1; i < symbols.size(); i++)
//                    slice.add(symbols.get(i));
//                res.addAll(F(slice));
//                return res;
//            } else {
//                return symbols.get(0).getFirst();
//            }
//        } else
//            throw new RuntimeException("ERROR PIZDEC");
//    }

    private ArrayList<Token> getFirst(Symbol s) {
        ArrayList<Token> res = new ArrayList<>();
        if (s.getTag() == TokenTag.TERMINAL || s.getTag() == TokenTag.EPSILON) {
            res.add((Token) s);
            return res;
        } else {
            for (ArrayList<Symbol> symbols: rules.get(s))
                res.addAll(getFirst(symbols.get(0)));

            return res;
        }

        //return res;
    }

    public void selectFIRST(Symbol s) {
        for (ArrayList<Symbol> symbols: rules.get(s)) {
            if (symbols.get(0).getTag() == TokenTag.TERMINAL) {
                s.addFirst((Token)symbols.get(0));
            } else {
                s.addFirstAll(getFirst(symbols.get(0)));
            }
        }
    }

    public void selectFOLLOW(Symbol s) {
        for (ArrayList<Symbol> symbols: rules.get(s)) {
            for (int i = 1; i < symbols.size() - 1; i++) {
                if (symbols.get(i).getTag() == TokenTag.NONTERMINAL)
                    symbols.get(i).addFollowAll(symbols.get(i + 1).getFirstWithoutEps());
            }
        }
    }

    public void follow() {
        boolean isChanged = true;
        int count = 0;
        while (isChanged && count != 2 * rules.keySet().size()) {
//            for (Token s : rules.keySet()) {
//                if (((NonTermToken) s).getValue().equals("F")) {
//                    System.out.println(s);
//                }
//                for (ArrayList<Symbol> symbols : rules.get(s)) {
//                    //TODO возможно стоит проверить, что symbols.size() == 1 (правило бесполезное)
//                    //S → uY
//                    if (symbols.get(symbols.size() - 1).getTag() == TokenTag.NONTERMINAL) {
//                        System.out.println("ХУЙ " + symbols + " " + (symbols.size() - 1) + " " + symbols.get(symbols.size() - 1));
//                        isChanged = symbols.get(symbols.size() - 1).addFollowAll(s.getFollow());
//                    } else
//                        isChanged = false;
//
//                    //S → uYv
//                    for (int i = 1; i < symbols.size() - 1; i++) {
//                        if (symbols.get(i).getTag() == TokenTag.NONTERMINAL && symbols.get(i + 1).isEpsIn())
//                            isChanged = symbols.get(symbols.size() - 1).addFollowAll(s.getFollow());
//                        else
//                            isChanged = false;
//                    }
//                }
//            }

            for (NonTermToken s: rules.keySet()) {
                for (ArrayList<Symbol> symbols : rules.get(s)) {
                    for (int i = 0; i < symbols.size() - 1; i++) {
                        Symbol nt = symbols.get(i);
                        if (nt.getTag() == TokenTag.NONTERMINAL) {
                            isChanged = nt.addFollowAll(symbols.get(i + 1).getFirst());
                            if (symbols.get(i + 1).isEpsIn()) {
                                isChanged = nt.addFollowAll(s.getFollow());
                            }
                            if (isChanged)
                                count = 0;
                            else
                                count++;
                        }
                    }
                }
            }
        }
    }
}
