package ru.bmstu.CompilerLabs.Lab7.Parser;

import javafx.scene.chart.ValueAxis;
import ru.bmstu.CompilerLabs.Lab7.Lexer.Scanner;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Symbol;
import ru.bmstu.CompilerLabs.Lab7.Symbols.SymbolType;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.Token;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.TokenTag;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Variables.SVar;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Variables.Var;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Variables.VarTag;

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
    private HashMap<SymbolType, Integer> numMap;

    public Parser(String program) {
        this.input = new Stack<>();
        this.scanner = new Scanner(program);
        this.errors = new ArrayList<>();
        this.stack = new Stack<>();

        this.numMap = new HashMap<>();
        numMap.put(VarTag.S, 0);
        numMap.put(VarTag.AXIOM,  1);
        numMap.put(VarTag.RULES,  2);
        numMap.put(VarTag.RULES1, 3);
        numMap.put(VarTag.RULE,   4);
        numMap.put(VarTag.PRODUCTS,  5);
        numMap.put(VarTag.PRODUCTS1, 6);
        numMap.put(VarTag.PRODUCT, 7);
        numMap.put(VarTag.SYMBOLS, 8);
        numMap.put(VarTag.SYMBOL, 9);
        numMap.put(TokenTag.LBRACKET, 10);
        numMap.put(TokenTag.RBRACKET, 11);
        numMap.put(TokenTag.KEYWORD, 12);
        numMap.put(TokenTag.IDENTIFIER, 13);
        numMap.put(TokenTag.GENERAL_SYMBOL, 14);
    }

    public ArrayList<Error> getErrors() {
        return errors;
    }

    private void reportError(String error) {
        this.errors.add(new Error(error));
    }

    public void parse() throws CloneNotSupportedException {

    }
}
