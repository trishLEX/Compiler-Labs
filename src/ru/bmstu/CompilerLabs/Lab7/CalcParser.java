package ru.bmstu.CompilerLabs.Lab7;

import ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Tokens.*;
import ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Tokens.EpsToken;
import ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Vars.*;
import ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Vars.VarTag;
import ru.bmstu.CompilerLabs.Lab7.Lexer.AbstractScanner;
import ru.bmstu.CompilerLabs.Lab7.Parser.Parser;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Symbol;
import ru.bmstu.CompilerLabs.Lab7.Symbols.SymbolType;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.*;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Variables.*;

import java.util.HashMap;

public class CalcParser extends Parser {
    static final int[][][] table = {
            {{-1}, {-1}, {-1}, {1, 0}, {1, 0}, {-1}},
            {{-1}, {-1}, {-1}, {2, 3}, {2, 3}, {-1}},
            {{5}, {6, 1, 0}, {-1}, {-1}, {-1}, {5}},
            {{-1}, {-1}, {-1}, {8}, {9, 4, 10}, {-1}},
            {{5}, {5}, {7, 2, 3}, {-1}, {-1}, {5}}
    };


    CalcParser(AbstractScanner scanner) {
        super(new EVar(), table, scanner);
    }

    @Override
    public Symbol makeSymbol(int number) {
        switch (number) {
            case 7: return new MulToken();
            case 0: return new E1Var();
            case 5: return new EpsToken();
            case 8: return new NumberToken();
            case 10: return new RParenToken();
            case 1: return new TVar();
            case 2: return new FVar();
            case 3: return new T1Var();
            case 4: return new EVar();
            case 6: return new AddToken();
            case 9: return new LParenToken();
            default: throw new RuntimeException();
        }
    }

    @Override
    public HashMap<SymbolType, Integer> getTokenMap() {
        HashMap<SymbolType, Integer> tokenMap = new HashMap<>();
        tokenMap.put(ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Tokens.TokenTag.MUL_TOKEN, 2);
        tokenMap.put(ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Tokens.TokenTag.END_OF_PROGRAM, 0);
        tokenMap.put(ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Tokens.TokenTag.NUMBER, 3);
        tokenMap.put(ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Tokens.TokenTag.RPAREN, 5);
        tokenMap.put(ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Tokens.TokenTag.ADD_TOKEN, 1);
        tokenMap.put(ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Tokens.TokenTag.LPAREN, 4);
        return tokenMap;
    }

    @Override
    public HashMap<SymbolType, Integer> getVarMap() {
        HashMap<SymbolType, Integer> varMap = new HashMap<>();
        varMap.put(ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Vars.VarTag.E1, 2);
        varMap.put(ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Vars.VarTag.T, 1);
        varMap.put(ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Vars.VarTag.F, 3);
        varMap.put(ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Vars.VarTag.T1, 4);
        varMap.put(VarTag.E, 0);
        return varMap;
    }
}
