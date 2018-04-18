package ru.bmstu.CompilerLabs.Lab7.Symbols.Variables;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Symbol;

import java.util.ArrayList;

public class RulesVar extends Var {
    RulesVar(ArrayList<Symbol> symbols) {
        super(VarTag.RULES, symbols);
    }
}
