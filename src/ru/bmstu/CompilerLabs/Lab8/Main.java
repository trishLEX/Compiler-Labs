package ru.bmstu.CompilerLabs.Lab8;

import ru.bmstu.CompilerLabs.Lab8.Symbols.Tokens.Token;
import ru.bmstu.CompilerLabs.Lab8.Lexer.Message;
import ru.bmstu.CompilerLabs.Lab8.Lexer.Scanner;
import ru.bmstu.CompilerLabs.Lab8.Symbols.Tokens.TokenTag;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    private static final String PATH = "E:\\Sorry\\Documents\\IdeaProjects\\CompilerLabs\\src\\ru\\bmstu\\CompilerLabs\\Lab8\\TestFile.txt";

    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        String program = new String(Files.readAllBytes(Paths.get(PATH)));

        Scanner scanner = new Scanner(program);

        ArrayList<Token> tokens = new ArrayList<>();

        Token t;

        do {
            t = scanner.nextToken();
            tokens.add(t);
        }
        while (t.getTag() != TokenTag.END_OF_PROGRAM);

        for (Token token: tokens)
            System.out.println(token);

        for (Message m: scanner.getMessages())
            System.out.println("ERROR: " + m);
    }
}
