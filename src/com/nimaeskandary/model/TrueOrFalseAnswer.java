package com.nimaeskandary.model;

import com.nimaeskandary.model.Answer;

import java.io.Serializable;

public class TrueOrFalseAnswer extends Answer implements Serializable {
    private String tOrF;

    public TrueOrFalseAnswer(String tOrF) {
        this.tOrF = tOrF;
    }

    @Override
    public String toString() {
        return this.tOrF;
    }

    @Override
    Answer makeCopy() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        TrueOrFalseAnswer otherAnswer = (TrueOrFalseAnswer) o;
        return this.tOrF.equals(otherAnswer.tOrF);
    }

    @Override
    public int hashCode() {
        return this.tOrF.hashCode();
    }
}
