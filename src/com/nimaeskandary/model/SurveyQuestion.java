package com.nimaeskandary.model;

import java.io.Serializable;
import java.util.List;

public class SurveyQuestion implements Serializable {
    public enum QuestionType {
        TrueOrFalse, MultipleChoice, ShortAnswer, EssayAnswer, Matching, Ranking
    }

    public QuestionType type;
    public PromptList promptList;
    public Answer userAnswer;
    public Integer allowedNumChoices;


    public SurveyQuestion(QuestionType type, PromptList promptList) {
        this.type = type;
        this.promptList = promptList;
        this.allowedNumChoices = 1;
    }

    public SurveyQuestion(QuestionType type, PromptList promptList, Integer allowedNumChoices) {
        this.type = type;
        this.promptList = promptList;
        this.allowedNumChoices = allowedNumChoices;
    }

    SurveyQuestion(SurveyQuestion surveyQuestion) {
        type = surveyQuestion.type;
        promptList = surveyQuestion.promptList;
        userAnswer = surveyQuestion.userAnswer.makeCopy();
    }

    @Override
    public String toString() {
        String question = "Question Type: " + this.type.name() + "\n";
        question += this.promptList.toString() + "\n";
        return question;
    }
}
