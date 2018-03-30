package ru.bmstu.CompilerLabs.Lab5;

enum DomainTag {
    IDENT,
    COMMENT,
    NUMBER,
    OPERATION,
    WHITESPACE,
    KEYWORD
}

abstract class Token {
    private DomainTag tag;
    private String value;
    private Fragment coords;

    protected Token(DomainTag tag, Position start, Position follow, String value) {
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

class IdentToken extends Token {
    public IdentToken(String value, Position start, Position follow) {
        super(DomainTag.IDENT, start, follow, value);
    }
}

class NumberToken extends Token {
    public NumberToken(String value, Position start, Position follow) {
        super(DomainTag.NUMBER, start, follow, value);
    }
}

class OpToken extends Token {
    public OpToken(String value, Position start, Position follow) {
        super(DomainTag.OPERATION, start, follow, value);
    }
}

class CommentToken extends Token {
    public CommentToken(String value, Position start, Position follow) {
        super(DomainTag.COMMENT, start, follow, value);
    }
}

class KeyWordToken extends Token {
    public KeyWordToken(String value, Position start, Position follow) {
        super(DomainTag.KEYWORD, start, follow, value);
    }
}

class WhitespaceToken extends Token {
    public WhitespaceToken(String value, Position start, Position follow) {
        super(DomainTag.WHITESPACE, start, follow, value);
    }
}
