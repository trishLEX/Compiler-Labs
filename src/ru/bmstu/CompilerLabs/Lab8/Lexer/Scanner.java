package ru.bmstu.CompilerLabs.Lab8.Lexer;

import ru.bmstu.CompilerLabs.Lab8.Service.Position;
import ru.bmstu.CompilerLabs.Lab8.Symbols.Tokens.*;

import javax.xml.stream.events.Characters;
import java.util.ArrayList;

public class Scanner {
    private Position cur;
    private ArrayList<Message> messages;

    public Scanner(String program) {
        this.cur = new Position(program);
        this.messages = new ArrayList<>();
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public Token nextToken() throws CloneNotSupportedException {
        while (cur.getChar()!= (char) 0xFFFFFFFF) {
            while (cur.isWhiteSpace())
                cur.nextCp();

            Position start = (Position) cur.clone();

            switch (cur.getChar()) {
                case '<':
                    cur.nextCp();
                    return new LBracketToken(start, (Position) cur.clone());
                case '>':
                    cur.nextCp();
                    return new RBracketToken(start, (Position) cur.clone());
                case '{':
                    cur.nextCp();
                    return new LBraceToken(start, (Position) cur.clone());
                case '}':
                    cur.nextCp();
                    return new RBraceToken(start, (Position) cur.clone());
                case '(':
                case ')':
                case '+':
                case '-':
                case '*':
                case '/':
                    String value = Character.toString(cur.getChar());
                    cur.nextCp();
                    return new TermToken(value, start, (Position) cur.clone());
                default:
                    if (cur.isLetter() && cur.isUpperCase())
                        return getNonTerminal();
                    else if (cur.isLetter() && cur.isLowerCase())
                        return getTerminal();
                    else {
                        messages.add(new Message(true, (Position) cur.clone(), "unexpected character"));
                    }
            }
        }

        return new EndOfProgramToken(cur, cur);
    }

    private NonTermToken getNonTerminal() throws CloneNotSupportedException{
        String value = "";
        Position start = (Position) cur.clone();

        while (cur.getChar() != (char) 0xFFFFFFFF
                && !Character.isWhitespace(cur.getChar())
                && cur.isUpperCase()) {
            value += cur.getChar();
            cur.nextCp();
        }

//        if (cur.getChar() == '\'') {
//            value += cur.getChar();
//            cur.nextCp();
//        }

        if (cur.getChar() != (char) 0xFFFFFFFF
                && !Character.isWhitespace(cur.getChar())
                && cur.getChar() != '>'
                && cur.getChar() != '<'
                && cur.getChar() != '}'
                && cur.getChar() != '{'
                && cur.getChar() != '('
                && cur.getChar() != ')'
                && cur.getChar() != '+'
                && cur.getChar() != '-'
                && cur.getChar() != '*'
                && cur.getChar() != '/'
                )
            messages.add(new Message(true, (Position) cur.clone(), "' ' expected"));

        return new NonTermToken(value, start, (Position) cur.clone());
    }

    private TermToken getTerminal() throws CloneNotSupportedException {
        String value = "";
        Position start = (Position) cur.clone();

        while (cur.getChar() != (char) 0xFFFFFFFF
                && !Character.isWhitespace(cur.getChar())
                && cur.isLowerCase()
                ) {
            value += cur.getChar();
            cur.nextCp();
        }

        if (cur.getChar() != (char) 0xFFFFFFFF
                && !Character.isWhitespace(cur.getChar())
                && cur.getChar() != '>'
                && cur.getChar() != '<'
                && cur.getChar() != '{'
                && cur.getChar() != '}'
                && cur.getChar() != '('
                && cur.getChar() != ')'
                && cur.getChar() != '+'
                && cur.getChar() != '-'
                && cur.getChar() != '*'
                && cur.getChar() != '/'
                )

            messages.add(new Message(true, (Position) cur.clone(), "' ' expected"));

        return new TermToken(value, start, (Position) cur.clone());
    }
}
