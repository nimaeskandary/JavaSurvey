package com.nimaeskandary;

import java.util.ArrayList;
import java.util.Map;

public class RankingAnswer extends Answer {
    private ArrayList<Map<String, Integer>> answerData;

    public RankingAnswer(ArrayList<Map<String, Integer>> answerData) {
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
