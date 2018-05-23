package com.nimaeskandary.model;

import com.nimaeskandary.model.Answer;

import java.io.Serializable;
import java.util.HashMap;

public class MatchingAnswer extends RankingAnswer implements Serializable {
    public MatchingAnswer(HashMap<String, Integer> answerData) {
        super(answerData);
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
