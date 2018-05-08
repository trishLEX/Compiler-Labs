package ru.bmstu.CompilerLabs.Lab8.Parser;

import ru.bmstu.CompilerLabs.Lab8.Symbols.Symbol;
import ru.bmstu.CompilerLabs.Lab8.Symbols.Tokens.TokenTag;
import ru.bmstu.CompilerLabs.Lab8.Symbols.Variables.*;
import ru.bmstu.CompilerLabs.Lab8.Lexer.Scanner;
import ru.bmstu.CompilerLabs.Lab8.Symbols.Tokens.Token;

public class Parser {
    private Scanner scanner;
    private Var start;
    private Token sym;

    public Parser(Scanner scanner) {
        this.scanner = scanner;
        this.start = new SVar();
    }

    public Var parse() throws CloneNotSupportedException {
        sym = scanner.nextToken();
        parseS(start);
        return start;
    }

    //S           ::= RULES
    private void parseS(Var s) throws CloneNotSupportedException {
        RulesVar rules = new RulesVar();
        s.addSymbol(rules);
        parseRules(rules);
    }

    //RULES      ::= RULE RULES'
    private void parseRules(RulesVar rules) throws CloneNotSupportedException {
        RuleVar rule = new RuleVar();
        rules.addSymbol(rule);
        parseRule(rule);

        Rules1Var rules1 = new Rules1Var();
        rules.addSymbol(rules1);
        parseRules1(rules1);
    }

    //RULES'     ::= RULE RULES' | .
    private void parseRules1(Rules1Var rules1) throws CloneNotSupportedException {
        if (sym.getTag() == TokenTag.LBRACKET) {
            RuleVar rule = new RuleVar();
            rules1.addSymbol(rule);
            parseRule(rule);

            Rules1Var rules11 = new Rules1Var();
            rules1.addSymbol(rules11);
            parseRules1(rules11);
        }
    }

    //RULE       ::= <NONTERMINAL PRODUCTS>
    private void parseRule(RuleVar rule) throws CloneNotSupportedException {
        rule.addSymbol(sym);
        parse(TokenTag.LBRACKET);

        rule.addSymbol(sym);
        parse(TokenTag.NONTERMINAL);

        ProductsVar products = new ProductsVar();
        rule.addSymbol(products);
        parseProducts(products);

        rule.addSymbol(sym);
        parse(TokenTag.RBRACKET);
    }

    //PRODUCTS   ::= PRODUCT PRODUCTS'
    private void parseProducts(ProductsVar products) throws CloneNotSupportedException {
        ProductVar product = new ProductVar();
        products.addSymbol(product);
        parseProduct(product);

        Products1Var products1 = new Products1Var();
        products.addSymbol(products1);
        parseProducts1(products1);
    }

    //PRODUCTS'  ::= PRODUCT PRODUCTS' | .
    private void parseProducts1(Products1Var products1) throws CloneNotSupportedException {
        if (sym.getTag() == TokenTag.LBRACKET) {
            ProductVar product = new ProductVar();
            products1.addSymbol(product);
            parseProduct(product);

            Products1Var products11 = new Products1Var();
            products1.addSymbol(products11);
            parseProducts1(products11);
        }
    }

    //PRODUCT    ::= <ENTITIES>
    private void parseProduct(ProductVar product) throws CloneNotSupportedException {
        product.addSymbol(sym);
        parse(TokenTag.LBRACKET);

        EntitiesVar entities = new EntitiesVar();
        product.addSymbol(entities);
        parseEntities(entities);

        product.addSymbol(sym);
        parse(TokenTag.RBRACKET);
    }

    //ENTITIES   ::= ENTITY ENTITIES'
    private void parseEntities(EntitiesVar entities) throws CloneNotSupportedException {
        EntityVar entity = new EntityVar();
        entities.addSymbol(entity);
        parseEntity(entity);

        Entities1Var entities1 = new Entities1Var();
        entities.addSymbol(entities1);
        parseEntities1(entities1);
    }

    //ENTITIES'  ::= ENTITY ENTITIES' | .
    private void parseEntities1(Entities1Var entities1) throws CloneNotSupportedException {
        if (sym.getTag() == TokenTag.NONTERMINAL
                || sym.getTag() == TokenTag.TERMINAL
                || sym.getTag() == TokenTag.LBRACE
                || sym.getTag() == TokenTag.LBRACKET) {
            EntityVar entity = new EntityVar();
            entities1.addSymbol(entity);
            parseEntity(entity);

            Entities1Var entities11 = new Entities1Var();
            entities1.addSymbol(entities11);
            parseEntities1(entities11);
        }
    }

    //ENTITY        ::= SYMBOLS | ALTS | REPEATS
    private void parseEntity(EntityVar entity) throws CloneNotSupportedException {
        if (sym.getTag() == TokenTag.NONTERMINAL || sym.getTag() == TokenTag.TERMINAL) {
            SymbolsVar symbols = new SymbolsVar();
            entity.addSymbol(symbols);
            parseSymbols(symbols);
        } else if (sym.getTag() == TokenTag.LBRACKET) {
            AltsVar alts = new AltsVar();
            entity.addSymbol(alts);
            parseAlts(alts);
        } else if (sym.getTag() == TokenTag.LBRACE){
            RepeatsVar repeats = new RepeatsVar();
            entity.addSymbol(repeats);
            parseRepeats(repeats);
        } else {
            throw new RuntimeException("unexpected token: " + sym);
        }
    }

    //ALTS          ::= ALT ALTS | .
    private void parseAlts(AltsVar alts) throws CloneNotSupportedException {
        if (sym.getTag() == TokenTag.LBRACKET) {
            AltVar alt = new AltVar();
            alts.addSymbol(alt);
            parseAlt(alt);

            AltsVar alts1 = new AltsVar();
            alts.addSymbol(alts1);
            parseAlts(alts1);
        }
    }

    //ALT           ::= <PRODUCTS>
    private void parseAlt(AltVar alt) throws CloneNotSupportedException {
        alt.addSymbol(sym);
        parse(TokenTag.LBRACKET);

        ProductsVar products = new ProductsVar();
        alt.addSymbol(products);
        parseProducts(products);

        alt.addSymbol(sym);
        parse(TokenTag.RBRACKET);
    }

    //REPEATS       ::= REPEAT REPEATS | .
    private void parseRepeats(RepeatsVar repeats) throws CloneNotSupportedException {
        if (sym.getTag() == TokenTag.LBRACE) {
            RepeatVar repeat = new RepeatVar();
            repeats.addSymbol(repeat);
            parseRepeat(repeat);

            RepeatsVar repeats1 = new RepeatsVar();
            repeats.addSymbol(repeats1);
            parseRepeats(repeats1);
        }
    }

    //REPEAT        ::= { REPEATSYMBOLS }
    private void parseRepeat(RepeatVar repeat) throws CloneNotSupportedException {
        repeat.addSymbol(sym);
        parse(TokenTag.LBRACE);

        RepeatSymbolsVar repeatSymbols = new RepeatSymbolsVar();
        repeat.addSymbol(repeatSymbols);
        parseRepeatSymbols(repeatSymbols);

        repeat.addSymbol(sym);
        parse(TokenTag.RBRACE);
    }

    //REPEATSYMBOLS ::= REPEATSYMBOL REPEATSYMBOLS | .
    private void parseRepeatSymbols(RepeatSymbolsVar repeatSymbols) throws CloneNotSupportedException {
        if (sym.getTag() == TokenTag.NONTERMINAL
                || sym.getTag() == TokenTag.TERMINAL
                || sym.getTag() == TokenTag.LBRACKET
                || sym.getTag() == TokenTag.LBRACE) {
            RepeatSymbolVar repeatSymbol = new RepeatSymbolVar();
            repeatSymbols.addSymbol(repeatSymbol);
            parseRepeatSymbol(repeatSymbol);

            RepeatSymbolsVar repeatSymbols1 = new RepeatSymbolsVar();
            repeatSymbols.addSymbol(repeatSymbols1);
            parseRepeatSymbols(repeatSymbols1);
        }
    }

    //REPEATSYMBOL  ::= ENTITY
    private void parseRepeatSymbol(RepeatSymbolVar repeatSymbol) throws CloneNotSupportedException {
        EntityVar entity = new EntityVar();
        repeatSymbol.addSymbol(entity);
        parseEntity(entity);
    }

    //SYMBOLS    ::= SYMBOL SYMBOLS | .
    private void parseSymbols(SymbolsVar symbols) throws CloneNotSupportedException {
        if (sym.getTag() == TokenTag.NONTERMINAL || sym.getTag() == TokenTag.TERMINAL) {
            SymbolVar symbol = new SymbolVar();
            symbols.addSymbol(symbol);
            parseSymbol(symbol);

            SymbolsVar symbols1 = new SymbolsVar();
            symbols.addSymbol(symbols1);
            parseSymbols(symbols1);
        }
    }

    //SYMBOL     ::= NONTERMINAL | TERMINAL
    private void parseSymbol(SymbolVar symbol) throws CloneNotSupportedException {
        if (sym.getTag() == TokenTag.NONTERMINAL) {
            symbol.addSymbol(sym);
            parse(TokenTag.NONTERMINAL);
        } else if (sym.getTag() == TokenTag.TERMINAL) {
            symbol.addSymbol(sym);
            parse(TokenTag.TERMINAL);
        } else {
            throw new RuntimeException("unexpected token: " + sym);
        }
    }

    private void parse(TokenTag tag) throws CloneNotSupportedException {
        if (sym.getTag() == tag)
            sym = scanner.nextToken();
        else
            throw new RuntimeException(tag + " expected, got " + sym);
    }
}
