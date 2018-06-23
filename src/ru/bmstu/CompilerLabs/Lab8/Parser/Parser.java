package ru.bmstu.CompilerLabs.Lab8.Parser;

import ru.bmstu.CompilerLabs.Lab8.Symbols.Tokens.*;
import ru.bmstu.CompilerLabs.Lab8.Symbols.Variables.*;
import ru.bmstu.CompilerLabs.Lab8.Lexer.Scanner;

public class Parser {
    private Scanner scanner;
    private SVar start;
    private Token sym;


    public Parser(Scanner scanner) {
        this.scanner = scanner;
        this.start = new SVar();
    }

    public SVar parse() throws CloneNotSupportedException {
        sym = scanner.nextToken();
        parseS(start);
        return start;
    }

    //S             ::= RULE+
    private void parseS(SVar s) throws CloneNotSupportedException {
        do {
            RuleVar rule = new RuleVar();
            s.addSymbol(rule);
            parseRule(rule);
        } while (sym.getTag() == TokenTag.LBRACKET);
    }

    //RULE          ::= <NONTERMINAL PRODUCT+>
    private void parseRule(RuleVar rule) throws CloneNotSupportedException {
        rule.addSymbol(sym);
        parse(TokenTag.LBRACKET);

        rule.addSymbol(sym);
        parse(TokenTag.NONTERMINAL);

        do {
            ProductVar product = new ProductVar();
            rule.addSymbol(product);
            parseProduct(product);
        } while (sym.getTag() == TokenTag.LBRACKET);

        rule.addSymbol(sym);
        parse(TokenTag.RBRACKET);
    }

    //PRODUCT       ::= <ENTITY+>
    private void parseProduct(ProductVar product) throws CloneNotSupportedException {
        product.addSymbol(sym);
        parse(TokenTag.LBRACKET);

        do {
            EntityVar entity = new EntityVar();
            product.addSymbol(entity);
            parseEntity(entity);
        } while (sym.getTag() == TokenTag.TERMINAL
                || sym.getTag() == TokenTag.NONTERMINAL
                || sym.getTag() == TokenTag.LBRACKET
                || sym.getTag() == TokenTag.LBRACE);

        product.addSymbol(sym);
        parse(TokenTag.RBRACKET);
    }

    //ENTITY        ::= SYMBOL | <PRODUCT+> | { ENTITY+ }
    private void parseEntity(EntityVar entity) throws CloneNotSupportedException {
        if (sym.getTag() == TokenTag.TERMINAL || sym.getTag() == TokenTag.NONTERMINAL) {
            SymbolVar symbol = new SymbolVar();
            entity.addSymbol(symbol);
            parseSymbol(symbol);
        } else if (sym.getTag() == TokenTag.LBRACKET) {
            entity.addSymbol(sym);
            parse(TokenTag.LBRACKET);

            do {
                ProductVar product = new ProductVar();
                entity.addSymbol(product);
                parseProduct(product);
            } while (sym.getTag() == TokenTag.LBRACKET);

            entity.addSymbol(sym);
            parse(TokenTag.RBRACKET);
        } else {
            entity.addSymbol(sym);
            parse(TokenTag.LBRACE);

            do {
                EntityVar entity1 = new EntityVar();
                entity.addSymbol(entity1);
                parseEntity(entity1);
            } while (sym.getTag() == TokenTag.TERMINAL
                    || sym.getTag() == TokenTag.NONTERMINAL
                    || sym.getTag() == TokenTag.LBRACKET
                    || sym.getTag() == TokenTag.LBRACE);

            entity.addSymbol(sym);
            parse(TokenTag.RBRACE);
        }
    }

    //SYMBOL        ::= NONTERMINAL | TERMINAL
    private void parseSymbol(SymbolVar symbol) throws CloneNotSupportedException {
        if (sym.getTag() == TokenTag.TERMINAL) {
            symbol.addSymbol(sym);
            parse(TokenTag.TERMINAL);
        } else {
            symbol.addSymbol(sym);
            parse(TokenTag.NONTERMINAL);
        }
    }

    private void parse(TokenTag tag) throws CloneNotSupportedException {
        if (sym.getTag() == tag)
            sym = scanner.nextToken();
        else
            throw new RuntimeException(tag + " expected, got " + sym);
    }
}
