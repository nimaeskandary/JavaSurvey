package com.nimaeskandary;

import java.util.ArrayList;

public class MultipleChoiceAnswer extends Answer {
    private ArrayList<Integer> answerData;

    public MultipleChoiceAnswer(ArrayList<Integer> answerData) {
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
