package com.nimaeskandary;

public class ShortAnswerAnswer extends Answer {
    private String answerData;

    public ShortAnswerAnswer(String answerData) {
        this.answerData = answerData;
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
