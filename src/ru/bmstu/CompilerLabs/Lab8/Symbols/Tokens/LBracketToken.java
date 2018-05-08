package ru.bmstu.CompilerLabs.Lab8.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab8.Service.Position;

public class LBracketToken extends Token<Character> {
    public LBracketToken (Position start, Position follow) {
        super(TokenTag.LBRACKET, start, follow, '<');
    }

    public LBracketToken() {
        super(TokenTag.LBRACKET, '<');
    }
}
