package ru.bmstu.CompilerLabs.Lab8;

import ru.bmstu.CompilerLabs.Lab8.Parser.FirstSelector;
import ru.bmstu.CompilerLabs.Lab8.Parser.Parser;
import ru.bmstu.CompilerLabs.Lab8.Parser.RulesFiller;
import ru.bmstu.CompilerLabs.Lab8.Lexer.Scanner;
import ru.bmstu.CompilerLabs.Lab8.Symbols.Variables.SVar;
import ru.bmstu.CompilerLabs.Lab8.Symbols.Variables.Var;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    private static final String PATH = "C:\\Users\\alexe\\IdeaProjects\\CompilerLabs\\src\\ru\\bmstu\\CompilerLabs\\Lab8\\TestFile.txt";

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
        SVar start = parser.parse();
//        Var.printTree(start);

        RulesFiller filler = new RulesFiller();
        filler.makeRules1(start);

//        Var.printTree(start);
        FirstSelector selector = new FirstSelector(filler.getRules());
        selector.selectFirst();
        selector.printFirst();

        System.out.println("GOOD");
    }
}
