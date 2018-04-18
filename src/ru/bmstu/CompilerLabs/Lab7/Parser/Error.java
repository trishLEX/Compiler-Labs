package ru.bmstu.CompilerLabs.Lab7.Parser;

public class Error {
    private String text;

    Error(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
