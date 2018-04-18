package ru.bmstu.CompilerLabs.Lab7.Symbols.Variables;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Symbol;

import java.util.ArrayList;

public class AxiomVar extends Var {
    public AxiomVar(ArrayList<Symbol> symbols) {
        super(VarTag.AXIOM, symbols);
    }
}
