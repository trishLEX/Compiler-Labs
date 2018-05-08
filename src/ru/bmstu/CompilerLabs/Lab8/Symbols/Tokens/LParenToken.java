package ru.bmstu.CompilerLabs.Lab8.Symbols.Tokens;

import ru.bmstu.CompilerLabs.Lab8.Service.Position;

public class LParenToken extends SymbolToken<Character> {
    public LParenToken (Position start, Position follow) {
        super(TokenTag.LPAREN, start, follow, '(');
        //this.addFirst(this);
    }

    public LParenToken() {
        super(TokenTag.LPAREN, '(');
        //this.addFirst(this);
    }
}
