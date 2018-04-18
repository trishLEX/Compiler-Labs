package ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Position;

public class KeywordToken extends Token<String> {
    public KeywordToken(String value, Position start, Position follow) {
        super(TokenTag.KEYWORD, start, follow, value);
    }

    public KeywordToken() {
        super(TokenTag.KEYWORD);
    }
}
