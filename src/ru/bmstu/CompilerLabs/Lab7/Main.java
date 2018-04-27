package ru.bmstu.CompilerLabs.Lab7;

import ru.bmstu.CompilerLabs.Lab7.Lexer.Scanner;
import ru.bmstu.CompilerLabs.Lab7.Parser.FFSelecter;
import ru.bmstu.CompilerLabs.Lab7.Parser.Filler;
import ru.bmstu.CompilerLabs.Lab7.Parser.ParseTable;
import ru.bmstu.CompilerLabs.Lab7.Parser.Parser;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Symbol;
import ru.bmstu.CompilerLabs.Lab7.Symbols.SymbolType;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.*;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Variables.SVar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    private static final String PATH = "C:\\Users\\alexe\\IdeaProjects\\CompilerLabs\\src\\ru\\bmstu\\CompilerLabs\\Lab7\\TestFile.txt";

    public static void main(String[] args) throws CloneNotSupportedException, IOException {
        String program = new String(Files.readAllBytes(Paths.get(PATH)));

        Parser parser = new GrammarParser(new Scanner(program));
        parser.TopDownParse();
        printTable(parser.getFiller());
    }

    public static void printTable(Filler filler) {
        HashMap<SymbolToken, Integer> fillerTable = filler.getTable();

        for (SymbolToken t : filler.getRules().keySet())
            if (filler.getRules().get(t).size() == 0)
                throw new RuntimeException(t.getValue() + " is not defined");

        FFSelecter selecter = new FFSelecter(filler.getRules());

        for (NonTermToken t : filler.getRules().keySet()) {
            selecter.selectFIRST(t);
        }

        selecter.selectFOLLOW();

        HashMap<NonTermToken, Integer> nonterms = filler.getNonterminals();
        HashMap<SymbolToken, Integer> terms = filler.getTerminals();

        ArrayList<Integer>[][] generatedTable = new ArrayList[filler.getNonterminals().keySet().size()][filler.getTerminals().keySet().size()];
        for (int i = 0; i < filler.getNonterminals().keySet().size(); i++) {
            for (int j = 0; j < filler.getTerminals().keySet().size(); j++) {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(-1);
                generatedTable[i][j] = list;
            }
        }

        for (NonTermToken x : filler.getRules().keySet()) {
            for (ArrayList<SymbolToken> u : filler.getRules().get(x)) {
                //ArrayList<SymbolToken> list = u.get(0).getFirst();
                for (SymbolToken a : (ArrayList<SymbolToken>) u.get(0).getFirst()) {
                    Integer error = -1;
                    generatedTable[nonterms.get(x)][terms.get(a)].remove(error);
                    ArrayList<Integer> res = new ArrayList<>();
                    for (Symbol y : u)
                        res.add(fillerTable.get(y));

                    if (generatedTable[nonterms.get(x)][terms.get(a)].size() > 0
                            && generatedTable[nonterms.get(x)][terms.get(a)].get(0) != -1)
                        throw new RuntimeException("Not LL(1)");

                    generatedTable[filler.getNonterminals().get(x)][filler.getTerminals().get(a)].addAll(res);

                    if (a.getTag() == TokenTag.EPSILON) {
                        for (Token b : x.getFollow()) {
                            for (Symbol z : u) {
                                if (!generatedTable[nonterms.get(x)][terms.get(b)].contains(fillerTable.get(z)))
                                    generatedTable[nonterms.get(x)][terms.get(b)].add(fillerTable.get(z));
                            }

                            generatedTable[nonterms.get(x)][terms.get(b)].remove(error);
                        }
                    }
                }
            }
        }

        System.out.println(generatedTable);
        ParseTable t = new ParseTable(generatedTable, nonterms, terms, fillerTable);
        t.printTagTable();
        t.printTable();
        t.printVarMap();
        t.printTokenMap();
        t.printMakeSymbolFn();
    }
}
