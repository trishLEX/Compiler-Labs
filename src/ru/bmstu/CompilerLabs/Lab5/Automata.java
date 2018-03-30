package ru.bmstu.CompilerLabs.Lab5;

import java.util.ArrayList;

public class Automata {
    public static final int[][] table = {
                        /* g  o  t  s  u  b  num  \  ()  ws? othLetter otherSymb \n */
            /*START */    {1, 7, 7, 7, 7, 7, 8,   9, -1,  0, 7,       -1, 0},
            /*ID_1  */    {7, 2, 7, 7, 7, 7, 7,   9, -1,  0, 7,       -1, 0},
            /*ID_2  */    {7, 7, 3, 4, 7, 7, 7,   9, -1,  0, 7,       -1, 0},
            /*ID_3  */    {7, 6, 7, 7, 7, 7, 7,   9, -1,  0, 7,       -1, 0},
            /*ID_4  */    {7, 7, 7, 7, 5, 7, 7,   9, -1,  0, 7,       -1, 0},
            /*ID_5  */    {7, 7, 7, 7, 7, 6, 7,   9, -1,  0, 7,       -1, 0},
            /*KEY_6 */    {7, 7, 7, 7, 7, 7, 7,   9, -1,  0, 7,       -1, 0},
            /*ID_7  */    {7, 7, 7, 7, 7, 7, 7,   9, -1,  0, 7,       -1, 0},
            /*NUM_8 */    {-1,-1,-1,-1,-1,-1,8,   9, -1,  0,-1,       -1, 0},
            /*_9    */    {-1,-1,-1,-1,-1,-1,-1, 11, 10, -1,-1,       -1, 0},
            /*OP_10 */    {1, 7, 7, 7, 7, 7, 8,   9, -1,  0, 7,       -1, 0},
            /*\\_11 */    {11,11,11,11,11,11,11, 11, 11, 11, 11,      -1, 0}
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
        System.out.println("c = " + c + " curState: " + currentState + " charCode: " + getCharCode(c) + " nextState: " + table[currentState][getCharCode(c)]);
        return table[currentState][getCharCode(c)];
    }

    private Token getToken(String word, Position start, Position follow) throws CloneNotSupportedException {
        if (currentState >= 1 && currentState < 6 || currentState == 7)
            return new IdentToken(word, start, follow);
        else if (currentState == 6)
            return new KeyWordToken(word, start, follow);
        else if (currentState == 8)
            return new NumberToken(word, start, follow);
        else if (currentState == 10)
            return new OpToken(word, start, follow);
        else if (currentState == 11)
            return new CommentToken(word, start, follow);
        else if (currentState == 9)
            throw new RuntimeException("Trying to return token from not final state");
        else
            throw new RuntimeException("Trying to return empty token");
    }

    private Token getTokenNew(String word, Position start, Position follow, int state) throws CloneNotSupportedException {
        System.out.println(currentState + " " + state);
        if (state >= 1 && state < 6 || state == 7)
            return new IdentToken(word, start, follow);
        else if (state == 6)
            return new KeyWordToken(word, start, follow);
        else if (state == 8)
            return new NumberToken(word, start, follow);
        else if (state == 10)
            return new OpToken(word, start, follow);
        else if (state == 11)
            return new CommentToken(word, start, follow);
        else if (state == 9)
            return new OpToken(word, start, follow);
        else
            throw new RuntimeException("Trying to return empty token");
    }

    public void tokenizeNew() throws CloneNotSupportedException{
        String word = "";
        boolean wasWS = false;
        Position start = (Position) cur.clone();
        int prevState;
        for (; cur.getChar() != (char) 0xFFFFFFFF; cur.nextCp()) {
            char c = cur.getChar();
            prevState = currentState;
            currentState = getNextState(c);

            if (currentState == 0) {
                tokens.add(getTokenNew(word, start, new Position(word, start.getLine(), start.getPos() + word.length()), prevState));
                word = "";
                start = (Position) cur.clone();
            } else if ((currentState == 7 || currentState == 8 ||
                    currentState == 1 || currentState == 2 || currentState == 3 || currentState == 4 || currentState == 5)
                    && prevState == 10) {
                tokens.add(getTokenNew(word, start, new Position(word, start.getLine(), start.getPos() + word.length()), prevState));
                word = "";
                start = (Position) cur.clone();
            } else if (prevState != 0 && currentState == 9) {
                tokens.add(getTokenNew(word, start, new Position(word, start.getLine(), start.getPos() + word.length()), prevState));
                word = "";
                start = (Position) cur.clone();
            } else if (currentState == -1) {
                messages.add(new Message(true, (Position) start.clone(), "error"));
                currentState = 0;
                start = (Position) cur.clone();
            }

//            if (start.getIndex() == cur.getIndex() && Character.isWhitespace(c))
//                start.nextCp();

            while (Character.isWhitespace(c) && currentState != 11) {
                cur.nextCp();
                c = cur.getChar();
                currentState = getNextState(c);
                start.nextCp();
            }

            if (currentState != 11 && !Character.isWhitespace(c))
                word += c;
            else if (currentState == 11)
                word += c;

        }

        if (word != "" && currentState != 9)
            tokens.add(getTokenNew(word, start, (Position) cur.clone(), currentState));
        else
            messages.add(new Message(true, (Position) start.clone(), "error"));
    }
}
