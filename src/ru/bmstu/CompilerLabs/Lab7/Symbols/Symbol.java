package ru.bmstu.CompilerLabs.Lab7.Symbols;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.Token;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.TokenTag;

import java.util.ArrayList;

public abstract class Symbol {
    private SymbolType tag;
    //private T value;
    //private Fragment coords;
    private ArrayList<Symbol> symbols = new ArrayList<>();
    private ArrayList<Token> first = new ArrayList<>();
    private ArrayList<Token> follow = new ArrayList<>();

    public void addSymbols(ArrayList<Symbol> symbols) {
        this.symbols = symbols;
    }

    public ArrayList<Symbol> getSymbols() {
        return this.symbols;
    }

    public static void printTree(Symbol symbol, int offset) {
        System.out.print("[" + symbol);
        for (Symbol s: symbol.symbols) {
            printMul(" ", offset);
            //System.out.println(symbol);
            printTree(s, offset + 1);
        }
        System.out.println("]");
    }

    private static void printMul(String s, int offset) {
        for (int i = 0; i < offset; i++)
            System.out.print(s);
    }

    public SymbolType getTag() {
        return tag;
    }

    public void addFirst(Token symbol) {
        first.add(symbol);
    }

    public void addFirstAll(ArrayList<Token> tokens) {
        first.addAll(tokens);
    }

    public ArrayList<Token> getFirst() {
        return first;
    }

    public ArrayList<Token> getFirstWithoutEps() {
        ArrayList<Token> res = new ArrayList<>();
        for (Token s: first) {
            if (s.getTag() != TokenTag.EPSILON)
                res.add(s);
        }

        return res;
    }

    public boolean isEpsIn() {
        for (Symbol s: first) {
            if (s.getTag() == TokenTag.EPSILON)
                return true;
        }

        return false;
    }

    public boolean addFollowAll(ArrayList<Token> tokens) {
        ArrayList<Token> add = new ArrayList<>();

        for (Token t: tokens) {
            if (!follow.contains(t))
                add.add(t);
        }

        if (add.size() == 0)
            return false;
        else {
            follow.addAll(add);
            return true;
        }
    }

    public void addFollow(Token token) {
        this.follow.add(token);
    }

    public ArrayList<Token> getFollow() {
        return follow;
    }
}
