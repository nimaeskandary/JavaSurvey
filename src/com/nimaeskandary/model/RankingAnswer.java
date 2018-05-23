package com.nimaeskandary.model;

import com.nimaeskandary.model.Answer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class RankingAnswer extends Answer implements Serializable {
    protected HashMap<String, Integer> answerData;

    public RankingAnswer(HashMap<String, Integer> answerData) {
        this.answerData = answerData;
    }

    @Override
    public String toString() {
        String out = "";
        for (String key: this.answerData.keySet()) {
            out += String.format("%s:%d\n", key, this.answerData.get(key));
        }
        return out;
    }

    @Override
    Answer makeCopy() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        RankingAnswer otherAnswer = (RankingAnswer) o;
        return this.answerData.equals(otherAnswer.answerData);
    }

    @Override
    public int hashCode() {
        return this.answerData.hashCode();
    }
}
