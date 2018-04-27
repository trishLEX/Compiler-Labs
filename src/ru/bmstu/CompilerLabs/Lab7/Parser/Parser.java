package ru.bmstu.CompilerLabs.Lab7.Parser;

import ru.bmstu.CompilerLabs.Lab7.Lexer.AbstractScanner;
import ru.bmstu.CompilerLabs.Lab7.Lexer.Scanner;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Symbol;
import ru.bmstu.CompilerLabs.Lab7.Symbols.SymbolType;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.*;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Variables.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public abstract class Parser {
    private int[][][] table;

    private Stack<Token> input;
    private AbstractScanner scanner;
    private Stack<Symbol> stack;
    //private ArrayList<Symbol> result;
    private Symbol start;
    private HashMap<SymbolType, Integer> varMap;
    private HashMap<SymbolType, Integer> tokenMap;
    private Filler filler;
    private FFSelecter selecter;
    //private ArrayList<Token> termsAndNonTerms = new ArrayList<>();
    private ArrayList<Integer>[][] generatedTable;

    public Parser(Symbol start, int[][][] table, AbstractScanner scanner) {
        varMap = getVarMap();
        tokenMap = getTokenMap();
        this.input = new Stack<>();
        //this.scanner = new Scanner(program);
        this.scanner = scanner;
        this.stack = new Stack<>();

        this.varMap = varMap;
        this.tokenMap = tokenMap;

        //this.result = new ArrayList<>();
        this.start = start;
        this.table = table;

        this.filler = new Filler();
    }

    public abstract Symbol makeSymbol(int number);
    public abstract HashMap<SymbolType, Integer> getVarMap();
    public abstract HashMap<SymbolType, Integer> getTokenMap();

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
            } else {
                System.out.println(X + " " + input.peek());
                if (X.getTag() == TokenTag.KEYWORD)
                    throw new RuntimeException("NO AXIOM");
                else
                    throw new RuntimeException(X.getTag() + " expected, got " + input.peek());
            }
        } else {
            System.out.println(varMap.get(X.getTag()) + " " + tokenMap.get(input.peek().getTag()));
            System.out.println(table[varMap.get(X.getTag())][tokenMap.get(input.peek().getTag())][0]);
            if (table[varMap.get(X.getTag())][tokenMap.get(input.peek().getTag())][0] != -1) {
                Symbol s = stack.pop();
                ArrayList<Symbol> symbols = new ArrayList<>();
                for (int num: table[varMap.get(X.getTag())][tokenMap.get(input.peek().getTag())]) {
                    Symbol symbol = makeSymbol(num);
                    symbols.add(symbol);
                }

                s.addSymbols(symbols);

                for (int i = symbols.size() - 1; i >= 0; i--) {
                    if (symbols.get(i).getTag() != TokenTag.EPSILON && symbols.get(i).getTag() != ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Tokens.TokenTag.EPSILON)
                        stack.push(symbols.get(i));
                }
            } else
                throw new RuntimeException(X.getTag() + " expected, got " + input.peek());
        }
    }

    public Symbol TopDownParse() throws CloneNotSupportedException {
        stack.push(start);
        input.push(scanner.nextToken());

        do {
            parse();
        } while (!stack.isEmpty());

        return start;
    }

    public Filler getFiller() {
        return filler;
    }
}
