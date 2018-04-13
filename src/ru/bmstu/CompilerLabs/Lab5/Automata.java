package ru.bmstu.CompilerLabs.Lab5;

import java.util.ArrayList;

public class Automata {
    public static final int[][] table = {
                        /* g  o  t  s  u  b  num  \  ()  ws? othLetter otherSymb \n eof*/
            /*START */    {1, 7, 7, 7, 7, 7, 8,   9, 13,  12, 7,       13,      12, -1},
            /*ID_1  */    {7, 2, 7, 7, 7, 7, 7,  -1, -1,  -1, 7,       13,      -1, -1},
            /*ID_2  */    {7, 7, 3, 4, 7, 7, 7,  -1, -1,  -1, 7,       13,      -1, -1},
            /*ID_3  */    {7, 6, 7, 7, 7, 7, 7,  -1, -1,  -1, 7,       13,      -1, -1},
            /*ID_4  */    {7, 7, 7, 7, 5, 7, 7,  -1, -1,  -1, 7,       13,      -1, -1},
            /*ID_5  */    {7, 7, 7, 7, 7, 6, 7,  -1, -1,  -1, 7,       13,      -1, -1},
            /*KEY_6 */    {7, 7, 7, 7, 7, 7, 7,  -1, -1,  -1, 7,       13,      -1, -1},
            /*ID_7  */    {7, 7, 7, 7, 7, 7, 7,  -1, -1,  -1, 7,       13,      -1, -1},
            /*NUM_8 */    {-1,-1,-1,-1,-1,-1,8,  -1, -1,  -1,-1,       13,      -1, -1},
            /*_9    */    {-1,-1,-1,-1,-1,-1,-1, 11, 10,  -1,-1,       13,      -1, -1},
            /*OP_10 */    {-1,-1,-1,-1,-1,-1,-1, -1, -1,  -1, -1,      -1,      -1, -1},
            /*\\_11 */    {11,11,11,11,11,11,11, 11, 11,  11, 11,      11,      -1, -1},
            /* WS_12*/    {-1,-1,-1,-1,-1,-1,-1,-1, -1,   12, -1,      -1,      12, -1},
            /* UNEXP*/    {-1,-1,-1,-1,-1,-1,-1,-1, -1,   -1, -1,      13,      -1, -1}
    };


    private String program;
    private int currentState;
    private Position cur;
    private ArrayList<Message> messages;
    private ArrayList<Token> tokens;

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public Automata(String program) {
        this.program = program;
        this.currentState = 0;
        this.cur = new Position(program);
        this.messages = new ArrayList<>();
        this.tokens = new ArrayList<>();
    }

    private int getCharCode(char c) {
        switch (c) {
            case 'g' : return 0;
            case 'o' : return 1;
            case 't' : return 2;
            case 's' : return 3;
            case 'u' : return 4;
            case 'b' : return 5;
            case '\\': return 7;
            case ')' : return 8;
            case '(' : return 8;
            case '\t': return 9;
            case '\r': return 12;
            case '\n': return 12;
            case ' ' : return 9;
            case (char) 0xFFFFFFFF: return 13;
            default:
                if (Character.isDigit(c))
                    return 6;
                else if (Character.isLetter(c))
                    return 10;
                else
                    return 11;
        }
    }

    private int getNextState(char c) {
        return table[currentState][getCharCode(c)];
    }

    private void addTokenNew(String word, Position start, Position follow, int state) throws CloneNotSupportedException {
        System.out.println(currentState + " " + state);
        if (state >= 1 && state < 6 || state == 7)
            tokens.add(new IdentToken(word, start, follow));
        else if (state == 6)
            tokens.add(new KeyWordToken(word, start, follow));
        else if (state == 8)
            tokens.add(new NumberToken(word, start, follow));
        else if (state == 10)
            tokens.add(new OpToken(word, start, follow));
        else if (state == 11)
            tokens.add(new CommentToken(word, start, follow));
        else if (state == 12)
            tokens.add(new WhitespaceToken(word, start, follow));
        else if (state == 13)
            messages.add(new Message(true, start, "error"));
        else
            messages.add(new Message(true, start, "error"));
    }

    public void tokenizeNew() throws CloneNotSupportedException{
        String word = "";
        Position start = (Position) cur.clone();
        int prevState = 0;
        for (; cur.getChar() != (char) 0xFFFFFFFF; cur.nextCp()) {
            char c = cur.getChar();
            prevState = currentState;
            currentState = getNextState(c);

            if (currentState == -1) {
                addTokenNew(word, start, (Position) cur.clone(), prevState);
                word = "";
                start = (Position) cur.clone();
                currentState = 0;
                currentState = getNextState(c);
            }

            word += c;
        }

        prevState = currentState;
        currentState = getNextState(cur.getChar());
        System.out.println(currentState + " " + prevState);
        if (currentState == -1) {
            addTokenNew(word, start, (Position) cur.clone(), prevState);
        }
    }
}
