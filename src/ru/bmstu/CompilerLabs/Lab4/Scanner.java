package ru.bmstu.CompilerLabs.Lab4;

import java.util.ArrayList;

//6 Variant
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
                case '"':
                    return findString(cur);
                case '_':
                    return findIdent(cur);
                default:
                    if (Character.isDigit(cur.getChar())) {
                        return findNumber(cur);
                    } else if (cur.getChar() == '_' || Character.isLetter(cur.getChar())) {
                        return findIdent(cur);
                    }
                    else
                        messages.add(new Message(true, (Position) cur.nextCp().clone(), "unexpected character"));
                    break;
            }
        }

        return new EndOfProgram(cur, cur);
    }

    private IdentToken findIdent(Position cur) throws CloneNotSupportedException {
        String value = "";
        Position start = (Position) cur.clone();

        if (!Character.isLetter(cur.getChar()) && cur.getChar() != '_') {
            throw new RuntimeException(cur.getChar() + " is not a letter or '_'");
        }

        value += cur.getChar();
        cur.nextCp();

        while (Character.isLetterOrDigit(cur.getChar()) || cur.getChar() == '_') {
            value += cur.getChar();
            cur.nextCp();
        }

        if (cur.getChar() != (char) 0xFFFFFFFF && !Character.isWhitespace(cur.getChar()))
            messages.add(new Message(true, (Position) cur.clone(), "' ' expected3"));

        return new IdentToken(value, start, (Position) cur.clone());
    }

    private NumberToken findNumber(Position cur) throws CloneNotSupportedException {
        String value = "";
        Position start = (Position) cur.clone();

        if (!Character.isDigit(cur.getChar())) {
            throw new RuntimeException(cur.getChar() + " is not a number");
        }

        int digitCount = 1;
        //boolean beforeFirstPoint = true;

        while (Character.isDigit(cur.getChar()) && digitCount <= 3) {
            digitCount++;
            value += cur.getChar();
            cur.nextCp();
        }

//        if (Character.isDigit(cur.getChar()))
//            messages.add(new Message(true, (Position) cur.clone(), "' ' expected"));

        while (cur.getChar() == '.') {
            value += cur.getChar();
            cur.nextCp();
            if (Character.isDigit(cur.getChar())) {
                value += cur.getChar();
                cur.nextCp();
                if (Character.isDigit(cur.getChar())) {
                    value += cur.getChar();
                    cur.nextCp();
                    if (Character.isDigit(cur.getChar())) {
                        value += cur.getChar();
                        cur.nextCp();
                    } else {
                        messages.add(new Message(true, (Position) cur.clone(), "digit expected"));
                        break;
                    }
                } else {
                    messages.add(new Message(true, (Position) cur.clone(), "digit expected"));
                    break;
                }
            } else {
                messages.add(new Message(true, (Position) cur.clone(), "digit expected"));
                break;
            }
        }

        //System.out.println("HERE IS: " + cur.getChar() + " " + cur.nextChar());

        if (cur.getChar() != (char) 0xFFFFFFFF && !Character.isWhitespace(cur.getChar()))
            messages.add(new Message(true, (Position) cur.clone(), "' ' expected1"));

        return new NumberToken(value, start, (Position) cur.clone());
    }

    private StringToken findString(Position cur) throws CloneNotSupportedException {
        //boolean wasQuoted = false;
        String value = "";
        Position start = (Position) cur.clone();

        if (cur.getChar() != '\"') {
            System.out.println(cur.getChar());
            throw new RuntimeException("cur is not a \"");
        }
        else {
            cur.nextCp();
            value += '"';
        }
        //System.out.println(value);

        while (cur.getChar() != '"') {
            if (cur.getChar() == (char) 0xFFFFFFFF) {
                messages.add(new Message(true, (Position) cur.clone(), "\" expected"));
                break;
            }

            if (cur.getChar() == '\n' || cur.getChar() == '\r') {
                messages.add(new Message(true, (Position) cur.clone(), "string has new line"));
                break;
            }

            if (cur.getChar() == '\\') {
                switch (cur.nextChar()) {
                    case 'n' : value += "\\"; cur.nextCp(); break;
                    case 't' : value += "\\"; cur.nextCp(); break;
                    case '"' : value += "\\"; cur.nextCp(); break;
                    case '\\': value += "\\"; cur.nextCp(); break;
                    default:
                        messages.add(new Message(true, (Position) cur.clone(), "Wrong escape sequence"));
                }
            }

            value += cur.getChar();
            cur.nextCp();
        }

        value += cur.getChar();
        cur.nextCp();
        //System.out.println("HERE: " + cur.getChar()  + " " + cur.nextChar());
        if (cur.getChar() != (char) 0xFFFFFFFF && !Character.isWhitespace(cur.getChar()))
            messages.add(new Message(true, (Position) cur.clone(), "' ' expected2"));
        return new StringToken(value, start, (Position) cur.clone());
    }
}
