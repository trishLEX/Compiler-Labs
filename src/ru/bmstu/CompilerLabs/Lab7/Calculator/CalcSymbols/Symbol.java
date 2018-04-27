package ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols;

import ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Tokens.Token;
import ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Tokens.TokenTag;
import ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Vars.Var;
import ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Vars.VarTag;

import java.util.ArrayList;

public abstract class Symbol {
    private SymbolType tag;
    private ArrayList<Symbol> symbols = new ArrayList<>();

    public void addSymbols(ArrayList<Symbol> symbols) {
        this.symbols = symbols;
    }

    public ArrayList<Symbol> getSymbols() {
        return this.symbols;
    }

    public static void printTree(Symbol symbol, int offset) {
        System.out.print("[" + symbol);
        for (Symbol s: symbol.symbols) {
            printMul(" ", offset);
            //System.out.println(symbol);
            printTree(s, offset + 1);
        }
        System.out.println("]");
    }

    private static void printMul(String s, int offset) {
        for (int i = 0; i < offset; i++)
            System.out.print(s);
    }

    public SymbolType getTag() {
        return tag;
    }

    public void setTag(SymbolType tag) {
        this.tag = tag;
    }

//    public static int countTree(Symbol symbol) {
//        if (symbol.getTag() == VarTag.E) {
//            int res = countTree(symbol.getSymbols().get(0));
//            Var E1 = (Var) symbol.getSymbols().get(1);
//            int t = 0;
//            while (E1.getSymbols().get(0).getTag() != TokenTag.EPSILON) {
//                char sign = (char)((Token) E1.getSymbols().get(0)).getValue();
//                if (sign == '+') {
//                    res += t;
//                } else {
//                    res -= t;
//                }
//
//                sign
//            }
//        }
//    }
    public static int countE(Symbol E) {
        return countE1(countT(E.symbols.get(0)), E.symbols.get(1));
    }

    public static int countE1(int t, Symbol E1) {
        if (E1.symbols.get(0).tag == TokenTag.EPSILON)
            return t;
        else {
            if ((char)((Token)E1.symbols.get(0)).getValue() == '+')
                return t + countE1(countT(E1.symbols.get(1)), E1.symbols.get(2));
            else
                return t - countE1(countT(E1.symbols.get(1)), E1.symbols.get(2));
        }
    }

    public static int countT(Symbol T) {
        return countT1(countF(T.symbols.get(0)), T.symbols.get(1));
    }

    public static int countT1(int t, Symbol T1) {
        if (T1.symbols.get(0).tag == TokenTag.EPSILON)
            return t;
        else {
            if ((char)((Token)T1.symbols.get(0)).getValue() == '*')
                return t * countT1(countF(T1.symbols.get(1)), T1.symbols.get(2));
            else
                return t / countT1(countF(T1.symbols.get(1)), T1.symbols.get(2));
        }
    }

    public static int countF(Symbol F) {
        if (F.symbols.get(0).tag == TokenTag.NUMBER) {
            return (int) ((Token)F.symbols.get(0)).getValue();
        } else {
            return countE(F.symbols.get(1));
        }
    }
}
