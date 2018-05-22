package com.nimaeskandary.model;

import com.nimaeskandary.model.Answer;

import java.io.Serializable;
import java.util.ArrayList;

public class ShortAnswerAnswer extends Answer implements Serializable {
    private ArrayList<String> answerData;

    public ShortAnswerAnswer(ArrayList<String> answerData) {
        this.answerData = answerData;
    }

    @Override
    public String toString() {
        String out = "";
        for (String s: this.answerData) {
            out += s + "\n";
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
