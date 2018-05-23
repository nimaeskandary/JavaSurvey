package com.nimaeskandary.model;

import java.io.Serializable;

public class EssayAnswer extends Answer implements Serializable {
    private String answerData;

    public EssayAnswer(String answerData) {
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
    public boolean equals(Object o) {
        EssayAnswer otherEssayAnswer = (EssayAnswer) o;
        return this.answerData.equals(otherEssayAnswer.answerData);
    }

    @Override
    public int hashCode() {
        return this.answerData.hashCode();
    }
}
