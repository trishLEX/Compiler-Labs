package ru.bmstu.CompilerLabs.Lab7.Calculator.Parser;

import ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Symbol;
import ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.SymbolType;
import ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Tokens.*;
import ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Vars.*;
import ru.bmstu.CompilerLabs.Lab7.Calculator.Lexer.Scanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Parser {
    private Stack<Token> input;
    private Scanner scanner;
    private Stack<Symbol> stack;
    private Symbol start;
    private HashMap<SymbolType, Integer> varMap;
    private HashMap<SymbolType, Integer> tokenMap;

    private static final int[][][] table = {
            {{-1}, {-1}, {-1}, {1, 0}, {1, 0}, {-1}},
            {{-1}, {-1}, {-1}, {2, 3}, {2, 3}, {-1}},
            {{5}, {6, 1, 0}, {-1}, {-1}, {-1}, {5}},
            {{-1}, {-1}, {-1}, {8}, {9, 4, 10}, {-1}},
            {{5}, {5}, {7, 2, 3}, {-1}, {-1}, {5}}
    };

    public Parser(String program) {
        this.input = new Stack<>();
        this.scanner = new Scanner(program);
        this.stack = new Stack<>();

        this.varMap = new HashMap<>();
        varMap.put(VarTag.E1, 2);
        varMap.put(VarTag.T, 1);
        varMap.put(VarTag.F, 3);
        varMap.put(VarTag.T1, 4);
        varMap.put(VarTag.E, 0);
        this.tokenMap = new HashMap<>();
        tokenMap.put(TokenTag.MUL_TOKEN, 2);
        tokenMap.put(TokenTag.END_OF_PROGRAM, 0);
        tokenMap.put(TokenTag.NUMBER, 3);
        tokenMap.put(TokenTag.RPAREN, 5);
        tokenMap.put(TokenTag.ADD_TOKEN, 1);
        tokenMap.put(TokenTag.LPAREN, 4);

        this.start = new EVar();
    }

    private Symbol makeSymbol(int number) {
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

    private void parse() throws CloneNotSupportedException {
        Symbol X = stack.peek();
        if (tokenMap.containsKey(X.getTag())) {
            if (X.getTag() == input.peek().getTag()) {
                Token s = (Token) stack.pop();
                s.setToken(input.peek().getCoords(), input.peek().getValue());

//                if (s.getTag() == TokenTag.NONTERMINAL || s.getTag() == TokenTag.TERMINAL) {
//                    termsAndNonTerms.add(s);
//                }

                input.push(scanner.nextToken());
            } else {
                throw new RuntimeException(X.getTag() + " expected, got " + input.peek());
            }
        } else {
            if (table[varMap.get(X.getTag())]
                    [tokenMap.get(input.peek().getTag())][0] != -1) {
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
        System.out.println(Symbol.countE(start));
    }
}
