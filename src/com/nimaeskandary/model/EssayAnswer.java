package com.nimaeskandary.model;

import java.io.Serializable;

public class EssayAnswer extends Answer implements Serializable {
    private String answerData;

    public EssayAnswer(String answerData) {
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
        EssayAnswer otherEssayAnswer = (EssayAnswer) other;
        return this.answerData.equals(otherEssayAnswer.answerData);
    }
}
