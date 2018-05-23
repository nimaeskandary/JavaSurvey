package com.nimaeskandary.model;

import com.nimaeskandary.model.Answer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class MatchingAnswer extends Answer implements Serializable {
    private ArrayList<Map<Integer, String>> answerData;

    public MatchingAnswer(ArrayList<Map<Integer, String>> answerData) {
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
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return this.answerData.hashCode();
    }
}
