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

    public void tokenize() throws CloneNotSupportedException{
        String word = "";
        Boolean isError = false;
        Position start = (Position) cur.clone();
        for (; cur.getChar() != (char) 0xFFFFFFFF; cur.nextCp()) {
            char c = cur.getChar();
            int nextState = getNextState(c);

            if (currentState == 11)
                word += c;
            else if (!Character.isWhitespace(c) && c != '\\')
                word += c;

            //если следующий символ новая строка, или пробел из индета или из ключевого слова
            if (currentState > 0 && currentState != 9 && nextState == 0) {
                tokens.add(getToken(word, start, (Position) cur.clone()));
                word = "";
                start = (Position) cur.clone();
            //если после числа идёт комментраий или операция
            } else if (currentState == 8 && (nextState == 10 || nextState == 11)) {
                tokens.add(getToken(word, start, (Position) cur.clone()));
                word = "";
                start = (Position) cur.clone();
            //если в идентификаторе или ключевом слове, а следующий это операция или комментарий
            } else if ((currentState >= 1 && currentState <= 7) && (nextState == 10 || nextState == 11 || nextState == 9)) {
                tokens.add(getToken(word, start, (Position) cur.clone()));
                word = "";
                start = (Position) cur.clone();
            //если после операции идёт идент или ключевое слово
            } else if (currentState == 10 && (nextState >= 1 && nextState <= 7 || nextState == 9)) {
                tokens.add(getToken(word, start, (Position) cur.clone()));
                word = "";
                start = (Position) cur.clone();
            } else if (nextState == -1) {
                messages.add(new Message(true, (Position) start.clone(), "error"));
                nextState = 0;
                start = (Position) cur.clone();
            }

            if (start.getIndex() == cur.getIndex() && Character.isWhitespace(c))
                start.nextCp();

            currentState = nextState;
        }

        if (word != "")
            tokens.add(getToken(word, start, (Position) cur.clone()));
    }

    public void tokenizeNew() throws CloneNotSupportedException{
        String word = "";
        Position start = (Position) cur.clone();
        int prevState;
        for (; cur.getChar() != (char) 0xFFFFFFFF; cur.nextCp()) {
            char c = cur.getChar();
            prevState = currentState;
            currentState = getNextState(c);

            if (currentState == 0) {
                tokens.add(getTokenNew(word, start, (Position) cur.clone(), prevState));
                word = "";
                start = (Position) cur.clone();
            } else if ((currentState == 7 || currentState == 8) && prevState == 10) {
                tokens.add(getTokenNew(word, start, (Position) cur.clone(), prevState));
                word = "";
                start = (Position) cur.clone();
            } else if (prevState != 0 && currentState == 9) {
                tokens.add(getTokenNew(word, start, (Position) cur.clone(), prevState));
                word = "";
                start = (Position) cur.clone();
            } else if (currentState == -1) {
                messages.add(new Message(true, (Position) start.clone(), "error"));
                currentState = 0;
                start = (Position) cur.clone();
            }

            if (start.getIndex() == cur.getIndex() && Character.isWhitespace(c))
                start.nextCp();

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
