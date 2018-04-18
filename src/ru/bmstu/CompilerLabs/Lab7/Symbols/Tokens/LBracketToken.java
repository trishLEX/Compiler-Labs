package ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Position;

public class LBracketToken extends Token<Character>{
    public LBracketToken (Character value, Position start, Position follow) {
        super(DomainTag.LBRACKET, start, follow, value);
    }
}
