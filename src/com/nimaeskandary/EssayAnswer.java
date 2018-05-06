package com.nimaeskandary;

public class EssayAnswer extends Answer {
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
