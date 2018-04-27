package ru.bmstu.CompilerLabs.Lab7.Calculator.Lexer;

import ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Tokens.*;
import ru.bmstu.CompilerLabs.Lab7.Lexer.AbstractScanner;
import ru.bmstu.CompilerLabs.Lab7.Lexer.Message;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Position;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.Token;

import java.util.ArrayList;

public class Scanner extends AbstractScanner{
    private Position cur;
    private ArrayList<Message> messages;

    public Scanner(String program)  {
        this.cur = new Position(program);
        this.messages = new ArrayList<>();
    }

    public Token nextToken() throws CloneNotSupportedException {
        while (cur.getChar() != (char) 0xFFFFFFFF) {
            while (cur.isWhiteSpace())
                cur.nextCp();

            //Position start = (Position) cur.clone();

            switch (cur.getChar()) {
                case '+': {
                    Position start = (Position) cur.clone();
                    cur.nextCp();
                    Position follow = (Position) cur.clone();
                    return new AddToken('+', start, follow);
                }
                case '-' : {
                    Position start = (Position) cur.clone();
                    cur.nextCp();
                    Position follow = (Position) cur.clone();
                    return new AddToken('-', start, follow);
                }
                case '*': {
                    Position start = (Position) cur.clone();
                    cur.nextCp();
                    Position follow = (Position) cur.clone();
                    return new MulToken('*', start, follow);
                }
                case '/': {
                    Position start = (Position) cur.clone();
                    cur.nextCp();
                    Position follow = (Position) cur.clone();
                    return new MulToken('/', start, follow);
                }
                case '(': {
                    Position start = (Position) cur.clone();
                    cur.nextCp();
                    Position follow = (Position) cur.clone();
                    return new LParenToken(start, follow);
                }
                case ')': {
                    Position start = (Position) cur.clone();
                    cur.nextCp();
                    Position follow = (Position) cur.clone();
                    return new RParenToken(start, follow);
                }
                default: {
                    String value = "";
                    Position start = (Position) cur.clone();
                    if (cur.isDecimalDigit()) {
                        while (!cur.isWhiteSpace()
                                && cur.getChar() != '*'
                                && cur.getChar() != '/'
                                && cur.getChar() != '+'
                                && cur.getChar() != '-'
                                && cur.getChar() != '('
                                && cur.getChar() != ')') {

                            value += cur.getChar();
                            cur.nextCp();
                        }

                        return new NumberToken(Integer.parseInt(value), start, (Position) cur.clone());
                    } else {
                        messages.add(new Message(true, (Position) cur.clone(), "Unexpected token"));
                    }
                }
            }
        }

        return new EndOfProgram(cur, cur);
    }
}
