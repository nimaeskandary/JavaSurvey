package com.nimaeskandary.model;

import com.nimaeskandary.model.Answer;

import java.io.Serializable;
import java.util.ArrayList;

public class MultipleChoiceAnswer extends Answer implements Serializable {
    private ArrayList<Integer> answerData;

    public MultipleChoiceAnswer(ArrayList<Integer> answerData) {
        this.answerData = answerData;
    }

    @Override
    public String toString() {
        String out = "";
        for (Integer answer: this.answerData) {
            out += answer + "\n";
        }
        return out;
    }

    @Override
    Answer makeCopy() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        MultipleChoiceAnswer otherAnswer = (MultipleChoiceAnswer) o;
        if (this.answerData.size() != otherAnswer.answerData.size()) {
            return false;
        }
        for (int j = 0; j < this.answerData.size(); j++) {
            boolean found = false;
            for (int k = 0; k < otherAnswer.answerData.size(); k++) {
                if (this.answerData.get(j).equals(otherAnswer.answerData.get(k))) {
                    found = true;
                    k = otherAnswer.answerData.size();
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return this.answerData.hashCode();
    }
}
