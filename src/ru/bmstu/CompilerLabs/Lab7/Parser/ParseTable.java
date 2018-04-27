package ru.bmstu.CompilerLabs.Lab7.Parser;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Symbol;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.NonTermToken;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.SymbolToken;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.Token;

import java.util.ArrayList;
import java.util.HashMap;

public class ParseTable {
    private ArrayList<Integer>[][] table;
    private HashMap<NonTermToken, Integer> nonTerminals;
    private HashMap<SymbolToken, Integer> terminals;
    private HashMap<SymbolToken, Integer> symbols;
    private HashMap<Integer, String> tags;
    private HashMap<Character, String> symbolToClass;

    public ParseTable(ArrayList<Integer>[][] table, HashMap<NonTermToken, Integer> nonTerminals,
                      HashMap<SymbolToken, Integer> terminals, HashMap<SymbolToken, Integer> symbols) {
        this.table = table;
        this.nonTerminals = nonTerminals;
        this.terminals = terminals;
        this.symbols = symbols;

        this.tags = new HashMap<>();
        //this.symbolToClass = new HashMap<>();

        System.out.println(symbols);
        for (SymbolToken s: symbols.keySet()) {
            System.out.println(s);
            if (s.getValue() != null)
                tags.put(symbols.get(s), s.getValue().toString());
            else
                tags.put(symbols.get(s), "ε");
        }
    }

    public void printTable() {
        System.out.println("private static final int[][][] table = {");
        for (int i = 0; i < table.length; i++) {
            System.out.print("{");

            for (int j = 0; j < table[i].length; j++) {
                //System.out.print("{");

                System.out.print(table[i][j].toString().replace("[", "{").replace("]", "}"));

                if (j == (table[i].length - 1))
                    System.out.print("");
                else
                    System.out.print(", ");
            }

            if (i == (table.length - 1))
                System.out.println("}");
            else
                System.out.println("},");
        }
        System.out.println("};");
    }

    public void printVarMap() {
        System.out.println("this.varMap = new HashMap<>();");
        for (NonTermToken nt: nonTerminals.keySet()) {
            System.out.println(String.format("varMap.put(VarTag.%s, %d);", nt.getValue(), nonTerminals.get(nt)).replace("'", "1"));
        }
    }

    public void printTokenMap() {
        System.out.println("this.tokenMap = new HashMap<>();");
        for (SymbolToken t: terminals.keySet()) {
            System.out.println(String.format("tokenMap.put(TokenTag.%s, %d);", t.getValue(), terminals.get(t)));
        }
    }

    public void printMakeSymbolFn() {
        System.out.println("private Symbol makeSymbol(int number) {\n   switch (number) {");
        for (Symbol s: symbols.keySet()) {
            System.out.println(String.format("      case %d: return new %s();", symbols.get(s), ((Token) s).getValue()));
        }
        System.out.println("    }");
        System.out.println("}");
    }

    public void printTagTable() {
        System.out.println("private static final int[][][] table = {");
        for (int i = 0; i < table.length; i++) {
            System.out.print("{");

            for (int j = 0; j < table[i].length; j++) {
                //System.out.print("{");

                ArrayList<String> res = new ArrayList<>();
                for (int k: table[i][j])
                    res.add(tags.get(k));

                System.out.print(res.toString().replace("[", "{").replace("]", "}"));


                if (j == (table[i].length - 1))
                    System.out.print("");
                else
                    System.out.print(", ");
            }

            if (i == (table.length - 1))
                System.out.println("}");
            else
                System.out.println("},");
        }
        System.out.println("};");
    }
}
