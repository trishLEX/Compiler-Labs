package ru.bmstu.CompilerLabs.Lab4;

enum DomainTag {
    IDENT,
    STRING,
    NUMBER,
    END_OF_PROGRAM
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

    public Fragment getCoords() {
        return coords;
    }

    public String getValue() {
        return value;
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

class StringToken extends Token {
    public StringToken(String value, Position start, Position follow) {
        super(DomainTag.STRING, start, follow, value);
    }
}

class EndOfProgram extends Token {
    public EndOfProgram(Position start, Position follow) {
        super(DomainTag.END_OF_PROGRAM, start, follow, String.valueOf((char) 0xFFFFFFFF));
    }
}
