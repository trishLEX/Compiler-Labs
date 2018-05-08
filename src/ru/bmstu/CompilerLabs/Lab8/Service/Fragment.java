package ru.bmstu.CompilerLabs.Lab8.Service;

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
