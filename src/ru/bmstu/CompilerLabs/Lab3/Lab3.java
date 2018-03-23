package ru.bmstu.CompilerLabs.Lab3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lab3 {
    public static final String CONSONANTS = "[ptksmnljw]";
    public static final String VOWELS = "[aeiou]";
    public static final String SYLLABLE = "(?!ji|ti|wo|wu)"+CONSONANTS + VOWELS +"([n])|(?!ji|ti|wo|wu)"+CONSONANTS + VOWELS;
    public static final String WORD = "("+SYLLABLE + ")+ |"+VOWELS+"((" + SYLLABLE + ")*) ";
    public static final String WHITESPACE = " +";
    public static final String PATTERN = "(^" + WHITESPACE + ")|" + "(^"+WORD+")";

    public static void main(String[] args) {
        File file = new File("C:\\Users\\alexe\\IdeaProjects\\CompilerLabs\\src\\ru\\bmstu\\Lab3\\TestFile.txt");
        System.out.println(PATTERN);
        tokenize(file);

//        Pattern pattern = Pattern.compile(NUMBER);
//        Matcher m = pattern.matcher("0");
//        if (m.find())
//            System.out.println("GOOD");
//        else
//            System.out.println("error");
    }

    private static void test_match(String text) {
        String ident = "\\p{L}[\\p{L}0-9]*";
        String number = "[0-9]+";
        String pattern = "(^"+ident+")|(^"+number+")";

        Pattern p = Pattern.compile(pattern);

        Matcher m = p.matcher(text);

        if (m.find()) {
            if (m.group(1) != null) {
                System.out.println("Identifier " + m.group(1));
            } else if (m.group(2) != null) {
                System.out.println("Number " + m.group(2));
            } else System.out.println("ERROR");
        } else System.out.println("ERROR");
    }

    private static void tokenize(File file) {
        Pattern p = Pattern.compile(PATTERN);

        try {
            Scanner sc = new Scanner(file);

            int nLine = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                tokenizeLine(line, p, nLine);
                nLine++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void tokenizeLine(String line, Pattern p, int nLine) {
        int pos = 1;
        //boolean beginStr = true;
        boolean error = false;
        while (line.length() > 0 && !error) {
            //System.out.println("LINE: " + line);
            Matcher m = p.matcher(line);
            //System.out.println("DO: " + line);
            String lexem = "";
            if (m.find()) {
                if (m.group(1) != null) {
                    lexem = m.group(1);
                    //System.out.println("WS (" + nLine + ", " + pos +"): " + lexem);
                } else if (m.group(2) != null) {
                    lexem = m.group(2);
                    System.out.println("WORD (" + nLine + ", " + pos +"): " + lexem);
                } else {
                    System.out.println("BAD");
                }
                line = line.substring(lexem.length());
                pos += lexem.length();
                //System.out.println("LEXEM: " + lexem);
                //System.out.println("POSLE: " + line);
            } else {
                System.out.println("ERROR (" + nLine + ", " + pos + ")");
                error = true;
            }
        }
    }
}
