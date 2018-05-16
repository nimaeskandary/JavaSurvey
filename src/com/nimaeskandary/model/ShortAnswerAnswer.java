package com.nimaeskandary.model;

import com.nimaeskandary.model.Answer;

import java.io.Serializable;

public class ShortAnswerAnswer extends Answer implements Serializable {
    private String answerData;

    public ShortAnswerAnswer(String answerData) {
        this.answerData = answerData;
    }

    @Override
    public String toString() {
        return this.answerData;
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
