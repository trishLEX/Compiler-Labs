package ru.bmstu.CompilerLabs.Lab5;

public class Fragment {
    private Position start, follow;

    public Fragment(Position start, Position follow) {
        this.start = start;
        this.follow = follow;
    }

    public Position getFollow() {
        return follow;
    }

    public Position getStart() {
        return start;
    }

    @Override
    public String toString() {
        return start.toString() + "-" + follow.toString();
    }
}
