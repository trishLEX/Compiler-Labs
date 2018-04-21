package ru.bmstu.CompilerLabs.Lab7.Lexer;

import ru.bmstu.CompilerLabs.Lab7.Symbols.*;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.*;

import java.util.ArrayList;

public class Scanner {
    private String program;
    private Position cur;
    private ArrayList<Message> messages;

    public Scanner(String program) {
        this.program = program;
        this.cur = new Position(program);
        this.messages = new ArrayList<>();
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public Token nextToken() throws CloneNotSupportedException {
        while (cur.getChar() != (char) 0xFFFFFFFF) {
            while (cur.isWhiteSpace())
                cur.nextCp();

            switch (cur.getChar()) {
                case '\\': {
                    Position start = (Position) cur.clone();
                    String value = "\\";
                    cur.nextCp();
                    if (cur.getChar() == '<' || cur.getChar() == '>') {
                        value += cur.getChar();
                        cur.nextCp();
                    } else {
                        while (cur.getChar() != '\\' && !cur.isWhiteSpace()) {
                            value += cur.getChar();
                            cur.nextCp();
                        }
                    }
                    Position follow = (Position) cur.clone();
                    return new TermToken(value, start, follow);
                }
                case '<': {
                    Position start = (Position) cur.clone();
                    cur.nextCp();
                    Position follow = (Position) cur.clone();
                    return new LBracketToken('<', start, follow);
                }
                case '>': {
                    Position start = (Position) cur.clone();
                    cur.nextCp();
                    Position follow = (Position) cur.clone();
                    return new RBracketToken('>', start, follow);
                }
                case 'a': {
                    Position start = (Position) cur.clone();
                    String value = "a";
                    cur.nextCp();
                    if (cur.getChar() == 'x') {
                        value += "x";
                        cur.nextCp();
                        if (cur.getChar() == 'i') {
                            value += "i";
                            cur.nextCp();
                            if (cur.getChar() == 'o') {
                                value += "o";
                                cur.nextCp();
                                if (cur.getChar() == 'm') {
                                    value += "m";
                                    cur.nextCp();
                                    if (!cur.isLetter())
                                        return new KeywordToken(value, start, (Position) cur.clone());
                                }
                            }
                        }
                    }

                    return getGenSymbol(cur, start, value);
                }
                default:
                    if (cur.isLetter() && cur.isUpperCase())
                        return getIdent(cur);
                    else
                        return getGenSymbol(cur);

//                    if (cur.getChar() != (char) 0xFFFFFFFF)
//                        messages.add(new Message(true, (Position) cur.clone(), "unexpected character"));
//                    cur.nextCp();
//                    break;
            }
        }

        return new EndOfProgram(cur, cur);
    }

    private NonTermToken getIdent(Position cur) throws CloneNotSupportedException {
        String value = "";
        Position start = (Position) cur.clone();

        if (!Character.isLetter(cur.getChar())) {
            throw new RuntimeException(cur.getChar() + " is not a letter");
        }

        while (cur.getChar() != (char) 0xFFFFFFFF
                && !Character.isWhitespace(cur.getChar())
                && cur.getChar() != '<'
                && cur.getChar() != '>'
                && cur.getChar() != '\\'
                && cur.getChar() != '\'') {
            value += cur.getChar();
            cur.nextCp();
        }

        if (cur.getChar() == '\'') {
            value += cur.getChar();
            cur.nextCp();
        }

        if (cur.getChar() != (char) 0xFFFFFFFF
                && !Character.isWhitespace(cur.getChar())
                && cur.getChar() != '>'
                && cur.getChar() != '<')
            messages.add(new Message(true, (Position) cur.clone(), "' ' expected0"));

        return new NonTermToken(value, start, (Position) cur.clone());
    }

    private TermToken getGenSymbol(Position cur) throws CloneNotSupportedException {
        String value = "";
        Position start = (Position) cur.clone();

        while (cur.getChar() != (char) 0xFFFFFFFF
                && !Character.isWhitespace(cur.getChar())
                && cur.getChar() != '<'
                && cur.getChar() != '>'
                && cur.getChar() != '\'') {
            value += cur.getChar();
            cur.nextCp();
        }

        if (cur.getChar() != (char) 0xFFFFFFFF
                && !Character.isWhitespace(cur.getChar())
                && cur.getChar() != '>'
                && cur.getChar() != '<')

            messages.add(new Message(true, (Position) cur.clone(), "' ' expected1"));

        return new TermToken(value, start, (Position) cur.clone());
    }

    private TermToken getGenSymbol(Position cur, Position start, String value) throws CloneNotSupportedException {
        while (cur.getChar() != (char) 0xFFFFFFFF
                && !Character.isWhitespace(cur.getChar())
                && cur.getChar() != '<'
                && cur.getChar() != '>'
                && cur.getChar() != '\'') {
            value += cur.getChar();
            cur.nextCp();
        }


        if (cur.getChar() != (char) 0xFFFFFFFF
                && !Character.isWhitespace(cur.getChar())
                && cur.getChar() != '>'
                && cur.getChar() != '<')

            messages.add(new Message(true, (Position) cur.clone(), "' ' expected2"));

        return new TermToken(value, start, (Position) cur.clone());
    }
}
