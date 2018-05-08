package ru.bmstu.CompilerLabs.Lab8.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab8.Service.Position;

public class OpToken extends SymbolToken<Character> {
    public OpToken (char value, Position start, Position follow) {
        super(TokenTag.OPERATION, start, follow, value);
        //this.addFirst(this);
    }

    public OpToken() {
        super(TokenTag.OPERATION);
        //this.addFirst(this);
    }
}
