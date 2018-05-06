package com.nimaeskandary;

public class TrueOrFalseAnswer extends Answer {
    private String tOrF;

    public TrueOrFalseAnswer(String tOrF) {
        this.tOrF = tOrF;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    Answer makeCopy() {
        return null;
    }

    @Override
    boolean equals(Answer other) {
        return false;
    }
}
