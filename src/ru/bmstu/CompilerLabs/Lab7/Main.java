package ru.bmstu.CompilerLabs.Lab7;

import ru.bmstu.CompilerLabs.Lab7.Lexer.Scanner;
import ru.bmstu.CompilerLabs.Lab7.Parser.Parser;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.*;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Variables.SVar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    private static final String PATH = "E:\\Sorry\\Documents\\IdeaProjects\\CompilerLabs\\src\\ru\\bmstu\\CompilerLabs\\Lab7\\TestFile.txt";

    public static void main(String[] args) throws CloneNotSupportedException, IOException {
        String program = new String(Files.readAllBytes(Paths.get(PATH)));
        Scanner scanner = new Scanner(program);

        ArrayList<Token> tokens = new ArrayList<>();

        Token t = scanner.nextToken();
        do {
            tokens.add(t);
            t = scanner.nextToken();
        } while (t.getTag() != TokenTag.END_OF_PROGRAM);

//        for (Token token: tokens)
//            System.out.println(token);
//
//        for (Message msg: scanner.getMessages())
//            System.out.println(msg);

        Parser parser = new Parser(program);
        parser.TopDownParse();

//        LBracketToken lb = new LBracketToken();
//        System.out.println(lb.getTag().ordinal());
//        Filler filler = new Filler();
//        filler.fill(lb);
//
//        NonTermToken nt = new NonTermToken();
//        filler.fill(nt);
//
//        LBracketToken lb2 = new LBracketToken();
//        filler.fill(lb2);
//
//        TermToken tt = new TermToken();
//        filler.fill(tt);
//        NonTermToken nt2 = new NonTermToken();
//        filler.fill(nt2);
//
//        RBracketToken rb2 = new RBracketToken();
//        filler.fill(rb2);
//
//        LBracketToken lb3 = new LBracketToken();
//        filler.fill(lb3);
//
//        NonTermToken nt3 = new NonTermToken();
//        filler.fill(nt3);
//
//        RBracketToken rb3 = new RBracketToken();
//        filler.fill(rb3);
//
//        RBracketToken rb = new RBracketToken();
//        filler.fill(rb);
    }
}
