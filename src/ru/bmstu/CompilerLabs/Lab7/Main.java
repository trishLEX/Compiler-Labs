package ru.bmstu.CompilerLabs.Lab7;

import ru.bmstu.CompilerLabs.Lab7.Parser.Error;
import ru.bmstu.CompilerLabs.Lab7.Parser.Parser;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Symbol;
import ru.bmstu.CompilerLabs.Lab7.Lexer.Scanner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    private static final String PATH = "E:\\Sorry\\Documents\\IdeaProjects\\CompilerLabs\\src\\ru\\bmstu\\CompilerLabs\\Lab7\\TestFile.txt";

    public static void main(String[] args) throws CloneNotSupportedException, IOException {
        String program = new String(Files.readAllBytes(Paths.get(PATH)));
        Scanner scanner = new Scanner(program);

//        ArrayList<Token> tokens = new ArrayList<>();
//
//        Token t = scanner.nextToken();
//        do {
//            tokens.add(t);
//            t = scanner.nextToken();
//        } while (t.getTag() != TokenTag.END_OF_PROGRAM);
//
//        for (Token token: tokens)
//            System.out.println(token);
//
//        for (Message msg: scanner.getMessages())
//            System.out.println(msg);
        //System.out.println(TokenTag.GENERAL_SYMBOL.ordinal());

        Parser parser = new Parser(program);
        ArrayList<Symbol> symbols = parser.TopDownParse();

        for (Symbol s: symbols)
            System.out.println(s.getTag());
    }
}
