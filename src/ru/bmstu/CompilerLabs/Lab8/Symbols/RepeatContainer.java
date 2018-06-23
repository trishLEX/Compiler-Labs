package ru.bmstu.CompilerLabs.Lab8.Symbols;

import ru.bmstu.CompilerLabs.Lab8.Symbols.Variables.Var;
import ru.bmstu.CompilerLabs.Lab8.Symbols.Variables.VarTag;

import java.util.ArrayList;

public class RepeatContainer extends Var implements Cloneable {
    private ArrayList<Symbol> elements;

    public RepeatContainer() {
        super(VarTag.REPEATCONTAINER);
        elements = new ArrayList<>();
    }

    public ArrayList<Symbol> getElements() {
        return elements;
    }

    public void addElement(Symbol e) {
        elements.add(e);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
