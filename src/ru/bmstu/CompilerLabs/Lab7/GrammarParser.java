package ru.bmstu.CompilerLabs.Lab7;

import ru.bmstu.CompilerLabs.Lab7.Lexer.AbstractScanner;
import ru.bmstu.CompilerLabs.Lab7.Parser.Parser;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Symbol;
import ru.bmstu.CompilerLabs.Lab7.Symbols.SymbolType;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.*;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Variables.*;

import java.util.HashMap;

public class GrammarParser extends Parser {
    private static final int[][][] table = {
            {{-1}, {5, 2}, {-1}, {-1}, {-1}, {-1}},
            {{-1}, {11, 12, 11, 13, 14, 14}, {-1}, {-1}, {-1}, {-1}},
            {{-1}, {3, 9}, {-1}, {-1}, {-1}, {-1}},
            {{-1}, {11, 13, 4, 14}, {-1}, {-1}, {-1}, {-1}},
            {{10}, {3, 9}, {-1}, {-1}, {-1}, {-1}},
            {{-1}, {7, 0}, {-1}, {-1}, {-1}, {-1}},
            {{-1}, {11, 1, 14}, {-1}, {-1}, {-1}, {-1}},
            {{10}, {7, 0}, {-1}, {-1}, {10}, {-1}},
            {{10}, {-1}, {-1}, {6, 1}, {10}, {6, 1}},
            {{-1}, {-1}, {-1}, {13}, {-1}, {15}}
    };

    GrammarParser(AbstractScanner scanner) {
        super(new SVar(), table, scanner);
    }

    @Override
    public Symbol makeSymbol(int number) {
        switch (number) {
            case 14: return new RBracketToken();
            case 2: return new RulesVar();
            case 3: return new RuleVar();
            case 6: return new SymbolVar();
            case 12: return new KeywordToken();
            case 7: return new ProductVar();
            case 13: return new NonTermToken();
            case 0: return new Products1Var();
            case 1: return new SymbolsVar();
            case 10: return new EpsToken();
            case 4: return new ProductsVar();
            case 5: return new AxiomVar();
            case 15: return new TermToken();
            case 8: return new SVar();
            case 11: return new LBracketToken();
            case 9: return new Rules1Var();
            default:
                throw new RuntimeException("Invalid number: " + number);
        }
    }

    @Override
    public HashMap<SymbolType, Integer> getTokenMap() {
        HashMap<SymbolType, Integer> tokenMap = new HashMap<>();
        tokenMap.put(TokenTag.RBRACKET, 4);
        tokenMap.put(TokenTag.END_OF_PROGRAM, 0);
        tokenMap.put(TokenTag.KEYWORD, 2);
        tokenMap.put(TokenTag.TERMINAL, 5);
        tokenMap.put(TokenTag.NONTERMINAL, 3);
        tokenMap.put(TokenTag.LBRACKET, 1);
        return tokenMap;
    }

    @Override
    public HashMap<SymbolType, Integer> getVarMap() {
        HashMap<SymbolType, Integer> varMap = new HashMap<>();
        varMap.put(VarTag.PRODUCTS1, 7);
        varMap.put(VarTag.SYMBOLS, 8);
        varMap.put(VarTag.RULES, 2);
        varMap.put(VarTag.RULE, 3);
        varMap.put(VarTag.PRODUCTS, 5);
        varMap.put(VarTag.AXIOM, 1);
        varMap.put(VarTag.SYMBOL, 9);
        varMap.put(VarTag.PRODUCT, 6);
        varMap.put(VarTag.S, 0);
        varMap.put(VarTag.RULES1, 4);
        return varMap;
    }
}
