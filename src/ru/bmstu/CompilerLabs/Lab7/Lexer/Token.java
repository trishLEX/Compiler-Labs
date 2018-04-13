package ru.bmstu.CompilerLabs.Lab7.Lexer;

public abstract class Token<T> {
    private DomainTag tag;
    private Fragment coords;
    private T value;

    protected Token(DomainTag tag, Position start, Position follow, T value) {
        this.tag = tag;
        this.coords = new Fragment(start, follow);
        this.value = value;
    }

    public DomainTag getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return tag + " " + coords + ": " + value;
    }
}

class NumberToken extends Token<Character> {
    public NumberToken (Character value, Position start, Position follow) {
        super(DomainTag.NUMBER, start, follow, value);
    }
}

class LParenToken extends Token<Character> {
    public LParenToken (Character value, Position start, Position follow) {
        super(DomainTag.LPAREN, start, follow, value);
    }
}

class RParenToken extends Token<Character> {
    public RParenToken (Character value, Position start, Position follow) {
        super(DomainTag.RPAREN, start, follow, value);
    }
}

class PlusToken extends Token<Character> {
    public PlusToken (Character value, Position start, Position follow) {
        super(DomainTag.PLUS, start, follow, value);
    }
}

class MulToken extends Token<Character> {
    public MulToken (Character value, Position start, Position follow) {
        super(DomainTag.MUL, start, follow, value);
    }
}

class EndOfProgram extends Token<Character> {
    public EndOfProgram (Position start, Position follow) {
        super(DomainTag.END_OF_PROGRAM, start, follow, (char)0xFFFFFFFF);
    }
}
