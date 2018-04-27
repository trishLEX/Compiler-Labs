package ru.bmstu.CompilerLabs.Lab7.Calculator.CalcSymbols.Tokens;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Position;
import ru.bmstu.CompilerLabs.Lab7.Symbols.Tokens.Token;

public class EndOfProgram extends Token<Character> {
    public EndOfProgram(Position start, Position follow) {
        super(TokenTag.END_OF_PROGRAM, start, follow, (char)0xFFFFFFFF);
    }
}
