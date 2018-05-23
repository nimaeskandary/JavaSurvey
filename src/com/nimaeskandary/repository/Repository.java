package com.nimaeskandary.repository;

import com.nimaeskandary.model.Survey;

import java.util.List;

abstract public class Repository {
    abstract public void putSurveyRubric(Survey survey, Boolean isTest);
    abstract public List<String> listSurveyNames(Boolean isTest);
    abstract public Survey getSurvey(String surveyName, Boolean isTest);
    abstract public void putUserSurvey(Survey survey, Boolean isTest);
}
