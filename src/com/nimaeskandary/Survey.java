package com.nimaeskandary;

import java.io.Serializable;
import java.util.ArrayList;

public class Survey implements Serializable {
    public String title;
    public String takerName;
    public ArrayList<SurveyQuestion> questions;

    public Survey(String title, String takerName) {
        this.title = title;
        this.takerName = takerName;
        this.questions = new ArrayList<SurveyQuestion>(1);
    }

    Survey(Survey survey) {
        title = survey.title;
        takerName = survey.takerName;
        questions = this.deepCopyQuestions();
    }

    private ArrayList<SurveyQuestion> deepCopyQuestions() {
        ArrayList<SurveyQuestion> currentQuestions = this.questions;
        ArrayList<SurveyQuestion> copiedQuestions = new ArrayList<SurveyQuestion>(currentQuestions.size());
        for (SurveyQuestion q: currentQuestions) {
            copiedQuestions.add(new SurveyQuestion(q));
        }
        return copiedQuestions;
    }

    public void addQuestion(SurveyQuestion question) {
        this.questions.add(question);
    }

    public void answerQuestion(int index) {

    }

    public SurveyResults getResults() {
        return new SurveyResults();
    }
}
