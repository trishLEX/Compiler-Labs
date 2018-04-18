package ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Position;

public class RBracketToken extends Token<Character>{
    public RBracketToken(Character value, Position start, Position follow) {
        super(DomainTag.RBRACKET, start, follow, value);
    }
}
