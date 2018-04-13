package ru.bmstu.CompilerLabs.Lab7;

import ru.bmstu.CompilerLabs.Lab7.Lexer.DomainTag;
import ru.bmstu.CompilerLabs.Lab7.Lexer.Message;
import ru.bmstu.CompilerLabs.Lab7.Lexer.Scanner;
import ru.bmstu.CompilerLabs.Lab7.Lexer.Token;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    private static final String PATH = "C:\\Users\\alexe\\IdeaProjects\\CompilerLabs\\src\\ru\\bmstu\\CompilerLabs\\Lab7\\TestFile.txt";

    public static void main(String[] args) throws CloneNotSupportedException, IOException {
        String program = new String(Files.readAllBytes(Paths.get(PATH)));
        Scanner scanner = new Scanner(program);

        ArrayList<Token> tokens = new ArrayList<>();

        Token t = scanner.nextToken();
        do {
            tokens.add(t);
            t = scanner.nextToken();
        } while (t.getTag() != DomainTag.END_OF_PROGRAM);

        for (Token token: tokens)
            System.out.println(token);

        for (Message msg: scanner.getMessages())
            System.out.println(msg);
    }
}
