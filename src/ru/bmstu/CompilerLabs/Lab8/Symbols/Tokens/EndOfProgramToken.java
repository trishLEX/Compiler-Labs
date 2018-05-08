package ru.bmstu.CompilerLabs.Lab8.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab8.Service.Position;

public class EndOfProgramToken extends Token {
    public EndOfProgramToken (Position start, Position follow) {
        super(TokenTag.END_OF_PROGRAM, start, follow, (char)0xFFFFFFFF);
    }
}
