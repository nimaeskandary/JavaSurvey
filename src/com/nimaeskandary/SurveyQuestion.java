package com.nimaeskandary;

public class SurveyQuestion {
    enum QuestionType {
        TrueOrFalse, MultipleChoice, ShortAnswer, EssayAnswer, Matching, Ranking
    }

    public QuestionType type;
    public Prompt prompt;
    public Answer userAnswer;

    public SurveyQuestion(QuestionType type, Prompt prompt) {
        this.type = type;
        this.prompt = prompt;
    }

    SurveyQuestion(SurveyQuestion surveyQuestion) {
        type = surveyQuestion.type;
        prompt = new Prompt(surveyQuestion.prompt);
        userAnswer = surveyQuestion.userAnswer.makeCopy();
    }
}
