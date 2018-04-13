package ru.bmstu.CompilerLabs.Lab7.Lexer;

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
                case '+': {
                    Position start = (Position) cur.clone();
                    cur.nextCp();
                    Position follow = (Position) cur.clone();
                    return new PlusToken('+', start, follow);
                }
                case '*': {
                    Position start = (Position) cur.clone();
                    cur.nextCp();
                    Position follow = (Position) cur.clone();
                    return new MulToken('*', start, follow);
                }
                case '(': {
                    Position start = (Position) cur.clone();
                    cur.nextCp();
                    Position follow = (Position) cur.clone();
                    return new LParenToken('(', start, follow);
                }
                case ')': {
                    Position start = (Position) cur.clone();
                    cur.nextCp();
                    Position follow = (Position) cur.clone();
                    return new RParenToken(')', start, follow);
                }
                case 'n': {
                    Position start = (Position) cur.clone();
                    cur.nextCp();
                    Position follow = (Position) cur.clone();
                    return new NumberToken('n', start, follow);
                }
                default:
                    messages.add(new Message(true, (Position) cur.clone(), "unexpected character"));
                    cur.nextCp();
                    break;
            }
        }

        return new EndOfProgram(cur, cur);
    }

}
