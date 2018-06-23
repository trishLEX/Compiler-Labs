package ru.bmstu.CompilerLabs.Lab8.Symbols;

import ru.bmstu.CompilerLabs.Lab8.Symbols.Variables.Var;
import ru.bmstu.CompilerLabs.Lab8.Symbols.Variables.VarTag;

import java.util.ArrayList;

public class AltContainer extends Var implements Cloneable {
    private ArrayList<ArrayList<Symbol>> elements;

    public AltContainer() {
        super(VarTag.ALTCONTAINER);
        elements = new ArrayList<>();
    }

    public ArrayList<ArrayList<Symbol>> getElements() {
        return elements;
    }

    public void addElement(ArrayList<Symbol> e) {
        elements.add(e);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
