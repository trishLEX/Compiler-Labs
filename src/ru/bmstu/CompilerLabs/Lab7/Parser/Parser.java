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
                              /*IDENTF   GEN_SYMBOL   <                         >          axiom   $ */
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
          /*13 IDENTF    */
          /*14 GEN_SYMBOL*/
          /*15 ε         */
    };

    private Stack<Symbol> input;
    private Scanner scanner;
    private ArrayList<Error> errors;
    private Stack<Symbol> stack;
    private ArrayList<Symbol> result;
    private HashMap<SymbolType, Integer> varMap;
    private HashMap<SymbolType, Integer> tokenMap;

    public Parser(String program) {
        this.input = new Stack<>();
        this.scanner = new Scanner(program);
        this.errors = new ArrayList<>();
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
        tokenMap.put(TokenTag.IDENTIFIER, 0);
        tokenMap.put(TokenTag.GENERAL_SYMBOL, 1);
        tokenMap.put(TokenTag.END_OF_PROGRAM, 5);

        this.result = new ArrayList<>();
    }

    public ArrayList<Error> getErrors() {
        return errors;
    }

    private void reportError(String error) {
        this.errors.add(new Error(error));
    }

    private void parse() throws CloneNotSupportedException {
        Symbol X = stack.peek();
        if (tokenMap.containsKey(X.getTag())) {
            if (X.getTag() == input.peek().getTag()) {
                stack.pop();
                input.push(scanner.nextToken());
            } else reportError("error");
        } else {
            System.out.print(X);
            System.out.println(" " + X.getTag() + " " + input.peek().getTag() + " " + varMap.get(X.getTag()) + " " + tokenMap.get(input.peek().getTag()));
            if (table[varMap.get(X.getTag())][tokenMap.get(input.peek().getTag())][0] != -1) {
                stack.pop();
                ArrayList<Symbol> symbols = new ArrayList<>();
                for (int num: table[varMap.get(X.getTag())][tokenMap.get(input.peek().getTag())]) {
                    Symbol symbol = makeSymbol(num);
                    symbols.add(symbol);
                    result.add(symbol);
                }

                for (int i = symbols.size() - 1; i >= 0; i--) {
                    if (symbols.get(i).getTag() != VarTag.EPSILON)
                        stack.push(symbols.get(i));
                }
            } else reportError("error");
        }
    }

    public ArrayList<Symbol> TopDownParse() throws CloneNotSupportedException {
        ////input.push(tokens.pop());
        stack.push(new SVar());
        input.push(scanner.nextToken());

        do {
            parse();
        } while (!stack.isEmpty() && input.peek().getTag() != TokenTag.END_OF_PROGRAM);
        //Без второго условия при неправильной программе уходит в вечный цикл

        parse();

        if (!stack.isEmpty())
            reportError("Stack is not empty");

        return result;
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
            case 13: return new IdentToken();
            case 14: return new GenSymbolToken();
            case 15: return new EpsVar();
            default:
                throw new RuntimeException("Invalid number: " + number);
        }
    }
}
