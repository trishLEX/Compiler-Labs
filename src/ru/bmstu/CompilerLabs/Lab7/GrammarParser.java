package ru.bmstu.CompilerLabs.Lab7;

import ru.bmstu.CompilerLabs.Lab7.Lexer.AbstractScanner;
import ru.bmstu.CompilerLabs.Lab7.Parser.Parser;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Symbol;
import ru.bmstu.CompilerLabs.Lab7.Symbols.SymbolType;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.*;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Variables.*;

import java.util.HashMap;

public class GrammarParser extends Parser {
    static final int[][][] table = {
            {{-1},    {-1},     {1, 2},                    {-1},        {-1}, {-1}},
            {{-1},    {-1},     {10, 12, 10, 13, 11, 11},  {-1},        {-1}, {-1}},
            {{-1},    {-1},     {4, 3},                    {-1},        {-1}, {-1}},
            {{-1},    {-1},     {4, 3},                    {-1},        {-1}, {15}},
            {{-1},    {-1},     {10, 13, 5, 11},           {-1},        {-1}, {-1}},
            {{-1},    {-1},     {7, 6},                    {-1},        {-1}, {-1}},
            {{-1},    {-1},     {7, 6},                    {15},        {-1}, {15}},
            {{-1},    {-1},     {10, 8, 11},               {-1},        {-1}, {-1}},
            {{9, 8},  {9, 8},   {-1},                      {15},        {-1}, {15}},
            {{13},    {14},     {-1},                      {-1},        {-1}, {-1}}
    };


    GrammarParser(AbstractScanner scanner) {
        super(new SVar(), table, scanner);
    }

    @Override
    public Symbol makeSymbol(int number) {
        switch (number) {
            case 0: return new SVar();
            case 1: return new AxiomVar();
            case 2: return new RulesVar();
            case 3: return new Rules1Var();
            case 4: return new RuleVar();
            case 5: return new ProductsVar();
            case 6: return new Products1Var();
            case 7: return new ProductVar();
            case 8: return new SymbolsVar();
            case 9: return new SymbolVar();
            case 10: return new LBracketToken();
            case 11: return new RBracketToken();
            case 12: return new KeywordToken();
            case 13: return new NonTermToken();
            case 14: return new TermToken();
            case 15: return new EpsToken();
            default:
                throw new RuntimeException("Invalid number: " + number);
        }
    }

    @Override
    public HashMap<SymbolType, Integer> getTokenMap() {
        HashMap<SymbolType, Integer> tokenMap = new HashMap<>();
        tokenMap.put(TokenTag.LBRACKET, 2);
        tokenMap.put(TokenTag.RBRACKET, 3);
        tokenMap.put(TokenTag.KEYWORD, 4);
        tokenMap.put(TokenTag.NONTERMINAL, 0);
        tokenMap.put(TokenTag.TERMINAL, 1);
        tokenMap.put(TokenTag.END_OF_PROGRAM, 5);
        return tokenMap;
    }

    @Override
    public HashMap<SymbolType, Integer> getVarMap() {
        HashMap<SymbolType, Integer> varMap = new HashMap<>();
        varMap.put(VarTag.S, 0);
        varMap.put(VarTag.AXIOM,  1);
        varMap.put(VarTag.RULES,  2);
        varMap.put(VarTag.RULES1, 3);
        varMap.put(VarTag.RULE,   4);
        varMap.put(VarTag.PRODUCTS,  5);
        varMap.put(VarTag.PRODUCTS1, 6);
        varMap.put(VarTag.PRODUCT, 7);
        varMap.put(VarTag.SYMBOLS, 8);
        varMap.put(VarTag.SYMBOL, 9);
        return varMap;
    }
}
