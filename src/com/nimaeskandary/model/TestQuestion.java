package com.nimaeskandary.model;

import java.io.Serializable;

public class TestQuestion extends SurveyQuestion implements Serializable {
    public Answer correctAnswer;

    public TestQuestion(QuestionType type, PromptList promptList) {
        super(type, promptList);
    }

    public TestQuestion(QuestionType type, PromptList promptList, Integer allowedNumChoices) {
        super(type, promptList, allowedNumChoices);
    }

    TestQuestion(TestQuestion testQuestion) {
        super(testQuestion);
        correctAnswer = testQuestion.correctAnswer.makeCopy();
    }

    @Override
    public String toString() {
        String question = "Question Type: " + this.type.name() + "\n";
        question += this.promptList.toString() + "\n";
        question += "correct answer:\n";
        question += this.correctAnswer.toString() + "\n\n";
        return question;
    }
}
