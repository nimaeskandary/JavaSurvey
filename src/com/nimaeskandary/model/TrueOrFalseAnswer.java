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
    boolean equals(Answer other) {
        return false;
    }
}
