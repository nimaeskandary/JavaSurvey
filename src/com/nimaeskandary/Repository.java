package com.nimaeskandary;

abstract public class Repository {
    abstract void putSurveyRubric(Survey survey);
    abstract Survey getSurveyRubric(String surveyName);
    abstract void putUserSurvey(Survey survey);
    abstract Survey getUserSurvey(String userName);
}
