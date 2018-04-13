package ru.bmstu.CompilerLabs.Lab7.Lexer;

public class Position implements Comparable<Position>, Cloneable{
    private String text;
    private Integer line, pos, index;

    public Position(String text) {
        this.text = text;
        line = pos = 1;
        index = 0;
    }

    public int getLine() {
        return line;
    }

    public int getPos() {
        return pos;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public int compareTo(Position other) {
        return index.compareTo(other.index);
    }

    @Override
    public String toString() {
        return " ( " + line + " , " + pos + " ) ";
    }

    public char getChar() {
        return (index == text.length()) ? (char) 0xFFFFFFFF : text.charAt(index);
    }

    public char nextChar() {
        return (index + 1 == text.length()) ? (char) 0xFFFFFFFF : text.charAt(index + 1);
    }

    public boolean isWhiteSpace() {
        return index != text.length() && Character.isWhitespace(text.charAt(index));
    }

    public boolean isLetter() {
        return index != text.length() && Character.isLetter(text.charAt(index));
    }

    public boolean isLetterOrDigit() {
        return index != text.length() && Character.isLetterOrDigit(text.charAt(index));
    }

    public boolean isDecimalDigit() {
        return index != text.length() && text.charAt(index) >= '0' && text.charAt(index) <= '9';
    }

    public boolean isNewLine() {
        if (index == text.length())
            return true;

        if (text.charAt(index) == '\r' && index + 1 < text.length())
            return text.charAt(index + 1) == '\n';

        return text.charAt(index) == '\n';
    }

    public Position nextCp() {
        if (index < text.length()) {
            if (isNewLine()) {
                if (text.charAt(index) == '\r')
                    index++;
                line++;
                pos = 1;
            } else {
                if (Character.isHighSurrogate(text.charAt(index)))
                    index++;
                pos++;
            }
            index++;
        }
        return this; //may be this.clone() here
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
