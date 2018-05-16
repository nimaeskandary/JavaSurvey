package com.nimaeskandary.model;

import com.nimaeskandary.model.Answer;

import java.io.Serializable;
import java.util.ArrayList;

public class MultipleChoiceAnswer extends Answer implements Serializable {
    private ArrayList<Integer> answerData;

    public MultipleChoiceAnswer(ArrayList<Integer> answerData) {
        this.answerData = answerData;
    }

    @Override
    public String toString() {
        String out = "";
        for (Integer answer: this.answerData) {
            out += answer + "\n";
        }
        return out;
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
