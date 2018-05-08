package ru.bmstu.CompilerLabs.Lab8;

import ru.bmstu.CompilerLabs.Lab8.Parser.Parser;
import ru.bmstu.CompilerLabs.Lab8.Symbols.Symbol;
import ru.bmstu.CompilerLabs.Lab8.Symbols.Tokens.EpsToken;
import ru.bmstu.CompilerLabs.Lab8.Symbols.Tokens.NonTermToken;
import ru.bmstu.CompilerLabs.Lab8.Lexer.Scanner;
import ru.bmstu.CompilerLabs.Lab8.Symbols.VarContainer;
import ru.bmstu.CompilerLabs.Lab8.Symbols.Variables.Var;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    private static final String PATH = "E:\\Sorry\\Documents\\IdeaProjects\\CompilerLabs\\src\\ru\\bmstu\\CompilerLabs\\Lab8\\TestFile.txt";

    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        String program = new String(Files.readAllBytes(Paths.get(PATH)));

//        Scanner scanner = new Scanner(program);
//
//        ArrayList<Token> tokens = new ArrayList<>();
//
//        Token t;
//
//        do {
//            t = scanner.nextToken();
//            tokens.add(t);
//        }
//        while (t.getTag() != TokenTag.END_OF_PROGRAM);
//
//        for (Token token: tokens)
//            System.out.println(token);
//
//        for (Message m: scanner.getMessages())
//            System.out.println("ERROR: " + m);

        Parser parser = new Parser(new Scanner(program));
        Var start = parser.parse();

        makeRules((Var) start.getSymbols().get(0));

        Var.printTree(start);

        System.out.println("GOOD");
    }

    private static HashMap<NonTermToken, ArrayList<Symbol>> rules = new HashMap<>();
    private static NonTermToken nt;
    private static ArrayList<Symbol> products = new ArrayList<>();
    private static VarContainer repeatContainer = new VarContainer();
    private static VarContainer altContainer = new VarContainer();
    private static boolean inRepeat = false;
    private static boolean inAlt = false;

    private static void makeRules(Var s) throws CloneNotSupportedException {
        switch (s.getTag()) {
            case S:
                makeRules((Var) s.get(0));
                break;
            case RULES:
                makeRules((Var) s.get(0));
                makeRules((Var) s.get(1));
                break;
            case RULES1:
                if (s.getSymbols().size() > 0) {
                    makeRules((Var) s.get(0));
                    makeRules((Var) s.get(1));
                } else if (products.size() > 0) {
                    rules.put(nt, products);
                    products = new ArrayList<>();
                }
                break;
            case RULE:
                //мб по длине products
                if (nt != null) {
                    rules.put(nt, products);
                    products = new ArrayList<>();
                }
                nt = (NonTermToken) s.get(1);
                makeRules((Var) s.get(2));
                break;
            case PRODUCTS:
                makeRules((Var) s.get(0));
                makeRules((Var) s.get(1));
                break;
            case PRODUCTS1:
                if (s.getSymbols().size() > 0) {
                    makeRules((Var) s.get(0));
                    makeRules((Var) s.get(1));
                }
                break;
            case PRODUCT:
                makeRules((Var) s.get(1));
                break;
            case ENTITIES:
                makeRules((Var) s.get(0));
                makeRules((Var) s.get(1));
                break;
            case ENTITIES1:
                if (s.getSymbols().size() > 0) {
                    makeRules((Var) s.get(0));
                    makeRules((Var) s.get(1));
                }
                break;
            case ENTITY:
                makeRules((Var) s.get(0));
                break;
            case ALTS:
                inAlt = true;
                if (s.getSymbols().size() > 0) {
                    makeRules((Var) s.get(0));
                    makeRules((Var) s.get(1));
                } else {
                    products.add(altContainer.clone());
                    altContainer = new VarContainer();
                    inAlt = false;
                }
                break;
            case ALT:
                makeRules((Var) s.get(1));
                break;
            case REPEATS:
                inRepeat = true;
                if (s.getSymbols().size() > 0) {
                    makeRules((Var) s.get(0));
                    makeRules((Var) s.get(1));
                } else {
                    products.add(repeatContainer.clone());
                    repeatContainer = new VarContainer();
                    inRepeat = false;
                }
                break;
            case REPEAT:
                makeRules((Var) s.get(1));
                break;
            case REPEATSYMBOLS:
                if (s.getSymbols().size() > 0) {
                    makeRules((Var) s.get(0));
                    makeRules((Var) s.get(1));
                }
                break;
            case REPEATSYMBOL:
                makeRules((Var) s.get(0));
                break;
            case SYMBOLS:
                if (s.getSymbols().size() > 0) {
                    makeRules((Var) s.get(0));
                    makeRules((Var) s.get(1));
                } else if (products.size() == 0) {
                        products.add(new EpsToken());
                }
                break;
            case SYMBOL:
                if (inRepeat) {
                    repeatContainer.addElement(s.get(0));
                } else if (inAlt) {
                    altContainer.addElement(s.get(0));
                } else
                    products.add(s.get(0));
                break;
        }
    }
}
