package ru.bmstu.CompilerLabs.Lab7.Symbols;

import ru.bmstu.CompilerLabs.Lab7.Symbols.Position;

public class Fragment {
    private Position start, follow;

    public Fragment(Position start, Position follow) {
        this.start = start;
        this.follow = follow;
    }

    @Override
    public String toString() {
        return start.toString() + "-" + follow.toString();
    }
}
