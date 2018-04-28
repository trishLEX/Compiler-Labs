package ru.bmstu.CompilerLabs.Lab7;

import ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.CalcParser;
import ru.bmstu.CompilerLabs.Lab7.Calculator.Lexer.Scanner;
import ru.bmstu.CompilerLabs.Lab7.Parser.Parser;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Symbol;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.Token;

public class CalcMain {
    public static void main(String[] args) throws CloneNotSupportedException {
        String program1 = "2*(3-1)/(1-2)";

        Parser parser1 = new CalcParser(new Scanner(program1));
        Symbol start = parser1.TopDownParse();
        System.out.println(countE(start));
    }

    public static int countE(Symbol E) {
        return countE1(countT(E.getSymbols().get(0)), E.getSymbols().get(1));
    }

    public static int countE1(int t, Symbol E1) {
        if (E1.getSymbols().get(0).getTag() == ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Tokens.TokenTag.EPSILON)
            return t;
        else {
            if ((char)((Token)E1.getSymbols().get(0)).getValue() == '+')
                return t + countE1(countT(E1.getSymbols().get(1)), E1.getSymbols().get(2));
            else
                return t - countE1(countT(E1.getSymbols().get(1)), E1.getSymbols().get(2));
        }
    }

    public static int countT(Symbol T) {
        return countT1(countF(T.getSymbols().get(0)), T.getSymbols().get(1));
    }

    public static int countT1(int t, Symbol T1) {
        if (T1.getSymbols().get(0).getTag() == ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Tokens.TokenTag.EPSILON)
            return t;
        else {
            if ((char)((Token)T1.getSymbols().get(0)).getValue() == '*')
                return t * countT1(countF(T1.getSymbols().get(1)), T1.getSymbols().get(2));
            else
                return t / countT1(countF(T1.getSymbols().get(1)), T1.getSymbols().get(2));
        }
    }

    public static int countF(Symbol F) {
        if (F.getSymbols().get(0).getTag() == ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Tokens.TokenTag.NUMBER) {
            return (int) ((Token)F.getSymbols().get(0)).getValue();
        } else {
            return countE(F.getSymbols().get(1));
        }
    }
}
