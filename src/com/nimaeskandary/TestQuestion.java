package com.nimaeskandary;

public class TestQuestion extends SurveyQuestion {
    public Answer correctAnswer;

    public TestQuestion(QuestionType type, Prompt prompt, Answer correctAnswer) {
        super(type, prompt);
        this.correctAnswer = correctAnswer;
    }

    TestQuestion(TestQuestion testQuestion) {
        super(testQuestion);
        correctAnswer = testQuestion.correctAnswer.makeCopy();
    }
}
