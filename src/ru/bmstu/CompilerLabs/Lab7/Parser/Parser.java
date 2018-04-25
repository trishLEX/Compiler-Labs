package ru.bmstu.CompilerLabs.Lab7.Parser;

import ru.bmstu.CompilerLabs.Lab7.Lexer.Scanner;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Symbol;
import ru.bmstu.CompilerLabs.Lab7.Symbols.SymbolType;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.*;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Variables.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Parser {
    private static final int[][][] table = {
                              /*NonTerm  Term         <                         >          axiom   $ */
          /*0  S         */    {{-1},    {-1},     {1, 2},                    {-1},        {-1}, {-1}},
          /*1  AXIOM     */    {{-1},    {-1},     {10, 12, 10, 13, 11, 11},  {-1},        {-1}, {-1}},
          /*2  RULES     */    {{-1},    {-1},     {4, 3},                    {-1},        {-1}, {-1}},
          /*3  RULES'    */    {{-1},    {-1},     {4, 3},                    {-1},        {-1}, {15}},
          /*4  RULE      */    {{-1},    {-1},     {10, 13, 5, 11},           {-1},        {-1}, {-1}},
          /*5  PRODUCTS  */    {{-1},    {-1},     {7, 6},                    {-1},        {-1}, {-1}},
          /*6  PRODUCTS' */    {{-1},    {-1},     {7, 6},                    {15},        {-1}, {15}},
          /*7  PRODUCT   */    {{-1},    {-1},     {10, 8, 11},               {-1},        {-1}, {-1}},
          /*8  SYMBOLS   */    {{9, 8},  {9, 8},   {-1},                      {15},        {-1}, {15}},
          /*9  SYMBOL    */    {{13},    {14},     {-1},                      {-1},        {-1}, {-1}}
          /*10 <         */
          /*11 >         */
          /*12 axiom     */
          /*13 NonTerm   */
          /*14 Term      */
          /*15 ε         */
    };

    private Stack<Token> input;
    private Scanner scanner;
    private Stack<Symbol> stack;
    //private ArrayList<Symbol> result;
    private Symbol start;
    private HashMap<SymbolType, Integer> varMap;
    private HashMap<SymbolType, Integer> tokenMap;
    private Filler filler;
    private FFSelecter selecter;
    //private ArrayList<Token> termsAndNonTerms = new ArrayList<>();
    private ArrayList<Integer>[][] generatedTable;

    public Parser(String program) {
        this.input = new Stack<>();
        this.scanner = new Scanner(program);
        this.stack = new Stack<>();

        this.varMap = new HashMap<>();
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

        this.tokenMap = new HashMap<>();
        tokenMap.put(TokenTag.LBRACKET, 2);
        tokenMap.put(TokenTag.RBRACKET, 3);
        tokenMap.put(TokenTag.KEYWORD, 4);
        tokenMap.put(TokenTag.NONTERMINAL, 0);
        tokenMap.put(TokenTag.TERMINAL, 1);
        tokenMap.put(TokenTag.END_OF_PROGRAM, 5);

        //this.result = new ArrayList<>();
        this.start = new SVar();

        this.filler = new Filler();
    }

    private void parse() throws CloneNotSupportedException {
        Symbol X = stack.peek();
        if (tokenMap.containsKey(X.getTag())) {
            if (X.getTag() == input.peek().getTag()) {
                Token s = (Token) stack.pop();
                s.setToken(input.peek().getCoords(), input.peek().getValue());

//                if (s.getTag() == TokenTag.NONTERMINAL || s.getTag() == TokenTag.TERMINAL) {
//                    termsAndNonTerms.add(s);
//                }

                filler.fill(s);

                input.push(scanner.nextToken());
            } else throw new RuntimeException(X.getTag() + " expected, got " + input.peek());
        } else {
            if (table[varMap.get(X.getTag())][tokenMap.get(input.peek().getTag())][0] != -1) {
                Symbol s = stack.pop();
                ArrayList<Symbol> symbols = new ArrayList<>();
                for (int num: table[varMap.get(X.getTag())][tokenMap.get(input.peek().getTag())]) {
                    Symbol symbol = makeSymbol(num);
                    symbols.add(symbol);
                }

                s.addSymbols(symbols);

                for (int i = symbols.size() - 1; i >= 0; i--) {
                    if (symbols.get(i).getTag() != TokenTag.EPSILON)
                        stack.push(symbols.get(i));
                }
            } else
                throw new RuntimeException(X.getTag() + " expected, got " + input.peek());
        }
    }

    public void TopDownParse() throws CloneNotSupportedException {
        stack.push(start);
        input.push(scanner.nextToken());

        do {
            parse();
        } while (!stack.isEmpty());

        //Symbol.printTree(start, 0);
        HashMap<Symbol, Integer> fillerTable = filler.getTable();

        selecter = new FFSelecter(filler.getRules());

        for (NonTermToken t: filler.getRules().keySet()) {
            selecter.selectFIRST(t);
        }

        selecter.selectFOLLOW();

        if (!stack.isEmpty())
            System.out.println("stack is not empty");

        HashMap<NonTermToken, Integer> nonterms = filler.getNonterminals();
        HashMap<SymbolToken, Integer> terms = filler.getTerminals();

        generatedTable = new ArrayList[filler.getNonterminals().keySet().size()][filler.getTerminals().keySet().size()];
        for (int i = 0; i < filler.getNonterminals().keySet().size(); i++) {
            for (int j = 0; j < filler.getTerminals().keySet().size(); j++) {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(-1);
                generatedTable[i][j] = list;
            }
        }

        for (NonTermToken x: filler.getRules().keySet()) {
            for (ArrayList<SymbolToken> u: filler.getRules().get(x)) {
                //ArrayList<SymbolToken> list = u.get(0).getFirst();
                for (SymbolToken a: (ArrayList<SymbolToken>) u.get(0).getFirst()) {
                    Integer error = -1;
                    generatedTable[filler.getNonterminals().get(x)][filler.getTerminals().get(a)].remove(error);
                    ArrayList<Integer> res = new ArrayList<>();
                    for (Symbol y: u)
                        res.add(fillerTable.get(y));

                    generatedTable[filler.getNonterminals().get(x)][filler.getTerminals().get(a)].addAll(res);

                    if (a.getTag() == TokenTag.EPSILON) {
                        for (Token b: x.getFollow()) {
                            for (Symbol z: u) {
                                if (!generatedTable[nonterms.get(x)][terms.get(b)].contains(fillerTable.get(z)))
                                    generatedTable[nonterms.get(x)][terms.get(b)].add(fillerTable.get(z));
                            }

                            generatedTable[filler.getNonterminals().get(x)][filler.getTerminals().get(b)].remove(error);
                        }
                    }
                }
            }
        }

        System.out.println(generatedTable);
    }

    private Symbol makeSymbol(int number) {
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
}
