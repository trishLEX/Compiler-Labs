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
    private static final String PATH = "C:\\Users\\alexe\\IdeaProjects\\CompilerLabs\\src\\ru\\bmstu\\CompilerLabs\\Lab7\\TestFile.txt";

    public static void main(String[] args) throws CloneNotSupportedException, IOException {
        String program = new String(Files.readAllBytes(Paths.get(PATH)));

        String program1 = "2*(3-1)/(1-2)";

        Parser parser = new Parser(program);
        parser.TopDownParse();

//        ru.bmstu.CompilerLabs.Lab7.Calculator.Parser.Parser parser1 = new ru.bmstu.CompilerLabs.Lab7.Calculator.Parser.Parser(program1);
//        parser1.TopDownParse();
    }
}
