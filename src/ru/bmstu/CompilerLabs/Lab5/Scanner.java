package ru.bmstu.CompilerLabs.Lab5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class Scanner {
    public static final String KEYWORDS = "goto|gosub";
    public static final String NUMBERS = "\\d+";
    public static final String OPERATIONS = "\\\\\\(|\\\\\\)";
    public static final String IDENTS = "[a-zA-Z][a-zA-Z0-9]*";
    public static final String COMMENTS = "//.*\\n?";
    public static final String REGEX = KEYWORDS + "|" + NUMBERS + "|" + OPERATIONS + "|" + IDENTS + "|" + COMMENTS;

    public static final String PATH = "C:\\Users\\alexe\\IdeaProjects\\CompilerLabs\\src\\ru\\bmstu\\CompilerLabs\\Lab5\\TestFile.txt";

    public static void main(String[] args) throws IOException, CloneNotSupportedException{
        Pattern p = Pattern.compile(REGEX);

        String program = new String(Files.readAllBytes(Paths.get(args[0])));
        Automata automata = new Automata(program);

        automata.tokenizeNew();

        for (Token t: automata.getTokens())
            //if (t.getTag() != DomainTag.WHITESPACE)
                System.out.println(t);

        for (Message m: automata.getMessages())
            System.out.println(m);
    }
}
